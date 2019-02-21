package com.sekai.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sekai.file.utils.POIUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekai.system.model.User;
import com.sekai.system.service.ConfigService;
import com.sekai.system.service.UserService;
import com.sekai.system.utils.EncryptUtils;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.LoginContext;
import com.sekai.system.utils.Page;

@Controller
public class UserController {
	String exportId = "export_user_list";
	@Resource
	UserService userService;
	@Resource
	ConfigService configService;
    @RequestMapping("/system/user/delete")
    @RequiresPermissions("user_delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	for(Integer id : ids) {
    		try{
    			userService.deleteUser(id);
    		}catch(Exception e){
    			return new JsonResult(0, e.getMessage());
    		}
    	}
		return new JsonResult(1, "");
    }
    @RequestMapping("/system/user/setStatus")
    @RequiresPermissions("user_modify")
    @ResponseBody
    public JsonResult setStatus(HttpServletRequest request, Integer[] ids, Integer status){
    	
    	for(Integer id : ids) {
    		try{
    			User u = userService.getUser(id);
    			u.setForbid(status);
    			userService.updateUser(u);
    		}catch(Exception e){
    			return new JsonResult(0, e.getMessage());
    		}
    	}
		return new JsonResult(1, "");
    }
    @RequestMapping("/system/user/edit")
    @RequiresPermissions("user_view")
    public String edit(HttpServletRequest request, Model model, Integer id){
    	User user = userService.getUser(id);
    	model.addAttribute("user",user);
		return "/system/user/user_edit";
    }
    @RequestMapping("/system/user/save")
    @RequiresPermissions("user_modify")
    public String save(Model model, HttpServletRequest request, @ModelAttribute("user") User user){

    	String url = "/system/user/user_edit";
    	if(user.getUserName().trim().equals("")) {
    		model.addAttribute("action_result", "请输入用户名！");
    		return url;
    	}
    	
    	if(user.getUserId() == null) {
    		if(userService.checkExistUserName(user.getUserName().trim()) > 0) {
    			model.addAttribute("action_result", "用户名"+user.getUserName()+"已存在！");
        		return url;
    		}
    	}
		if(user.getPwd() != null && !user.getPwd().equals("")) {
			String enPwd = new EncryptUtils().Encode(user.getPwd());
			user.setPwd(enPwd);
		}
    	if(user.getUserId() == null){
    		if(user.getPwd().trim().equals("")){
    			model.addAttribute("action_result", "新增用户必须输入初始密码！");
        		return url;	
    		}
    		user.setForbid(0);
    		userService.addUser(user);
    		//user.setUserId(userService.getMaxId());

    		model.addAttribute("action", "add");	
    	}else{
    		//保存前的用户
    		User user0 = userService.getUser(user.getUserId());
    		//如果修改了用户名
    		if(!user0.getUserName().equals(user.getUserName())) {
    			if(userService.checkExistUserName(user.getUserName().trim()) > 0) {
        			model.addAttribute("action_result", "用户名"+user.getUserName()+"已存在，不允许使用！");
            		return url;
        		}
    		}
    		userService.updateUser(user);
    	}

    	model.addAttribute("action_result", "success");
    	return url;
    }
    @RequestMapping("/system/user/list")
    @RequiresPermissions(value={"user_view"}, logical= Logical.AND)
    public String list(){
        return "system/user/user_list";
    }
    @RequestMapping("/system/user/getUserList")
    @ResponseBody
    public String getUserList(
    		@RequestParam(required=true) Integer pageNumber,
    		@RequestParam(required=true) Integer pageSize,
    		@RequestParam(required=false) String sortOrder,
    		@RequestParam(required=false) String sortName,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request, HttpSession session
    		) throws Exception{
		Page<User> page = new Page<User>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if(sortName !=null) { 
			page.setSortName(sortName);
			page.setSortOrder(sortOrder);
		}
		Map<String,Object> mapPage = new HashMap<>();
		mapPage.put("page", page);
		mapPage.put("keyword", keyword);
		List<User> list = userService.getUserList(mapPage);
		Map<String,Object> mapExport = new HashMap<>();
		mapExport.put("sql", page.getSql());
		//设置要导出的列
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("userName", "用户名");
		fields.put("nickName", "昵称");
		fields.put("email", "电子邮箱");
		fields.put("cellphone", "手机");
		fields.put("forbid", "禁用状态");
		ExportInfo export = new ExportInfo(fields, page.getSql());
		session.setAttribute(exportId, export);

		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("total", page.getTotalRecord());
		mapJson.put("pageSize", pageSize);
		mapJson.put("rows", list);
		return new ObjectMapper().writeValueAsString(mapJson);
    }
    @RequestMapping("/system/user/changeMyPwd")
    public String changeMyPwd(){
        return "system/user/change_my_pwd";
    }
    @RequestMapping("/system/user/changeMyPwdSave")
    public String changeMyPwdSave(Model model, HttpServletRequest request,HttpSession session) throws Exception{
    	
    	String newPassword = String.valueOf(request.getParameter("newPassword").trim());
    	String newPassword2 = String.valueOf(request.getParameter("newPassword2").trim());
    	String url = "system/user/change_my_pwd";
    	model.addAttribute("newPassword", newPassword);
    	model.addAttribute("newPassword2", newPassword2);
    	if(newPassword.equals("")) {
    		model.addAttribute("action_result","密码不允许为空！");
    		return url;
    	}
    	if(!newPassword.equals(newPassword2)) {
    		model.addAttribute("action_result","两次密码输入不一致！");
    		return url;
    	}
    	Integer userId = new LoginContext().getUserId();
    	if(userId == null || userId == 0) {
    		model.addAttribute("action_result","获取用户Id失败！");
    		return url;
    	}
    	String enPwd = new EncryptUtils().Encode(newPassword);
    	userService.updatePwd(userId, enPwd);
    	model.addAttribute("action_result", "success");
        return url;
    }
	@RequestMapping("/system/user/exportToExcel")
	public String exportToExcel() {
		return "redirect:/system/excel/excelExport?exportId=" + exportId;
	}
    @RequestMapping("/system/user/importExcel")
    public String  importExcel(Model model, HttpServletRequest request,
							   @RequestParam("file") MultipartFile file) throws IOException {
    	String url = "/file/import_excel/import_result";
		String fileName=file.getOriginalFilename();
		EncryptUtils encryptUtils = new EncryptUtils();
		POIUtil u=new POIUtil();
		List<Map<String,String>> list =  u.readExcel(file.getInputStream(),fileName);
		int rowIndex=0;
		String msg="";
		int successCount=0;
		int failCount=0;
		for(Map<String,String> row : list){
			System.out.println(String.valueOf(rowIndex));
			if(rowIndex==0) {
				rowIndex++;
				System.out.println(String.valueOf("continue:"+rowIndex));
				continue;
			}

			System.out.println(String.valueOf("new User:"+rowIndex));
			User user = new User();
			try{
				String errMsg="";
				for (Map.Entry<String,String> entry : row.entrySet()) {
					String key=entry.getKey();
					String value=entry.getValue().trim();
					//System.out.println("key:"+key+" V:"+value);

					if(key.equals("userName")){
						if(userService.checkExistUserName(value)>0){
							errMsg="第"+rowIndex+"行失败：\n用户名"+value+"已存在！\n";
							break;
						}
						user.setUserName(value);
					}else if(key.equals("nickName")){
						user.setNickName(value);
					}else if(key.equals("pwd")){
						String md5Pwd=encryptUtils.Encode(value);
						user.setPwd(md5Pwd);
					}else if(key.equals("email")){
						user.setEmail(value);
					}else if(key.equals("cellphone")){
						user.setCellphone(value);
					}

				}
				if(!errMsg.equals("")){
					failCount++;
					msg+=errMsg;
				}else {
					user.setForbid(0);
					userService.addUser(user);
					successCount++;
				}

			}catch(Exception e) {
				msg+="第"+rowIndex+"行失败：\n"+e.toString()+"\n";
				failCount++;
			}
			rowIndex++;
		}
		String shortMsg="导入成功"+String.valueOf(successCount)+"行\n"
			+"导入失败"+String.valueOf(failCount)+"行";

		model.addAttribute("action_result", "success");
		model.addAttribute("shortMsg", shortMsg);
		model.addAttribute("msg", msg);
    	return url;
	}

}