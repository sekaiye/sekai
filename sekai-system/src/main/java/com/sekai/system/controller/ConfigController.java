package com.sekai.system.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekai.system.model.Config;
import com.sekai.system.service.ConfigService;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.Page;
/*参数控制器1*/
@Controller
public class ConfigController {
	String exportId="export_config_list";
	@Resource
	ConfigService configService;
    @RequestMapping("/system/config/delete")
    @RequiresPermissions("config_modify")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	for(Integer id : ids) {
    		try{
    			configService.deleteConfig(id);
    		}catch(Exception e){
    			return new JsonResult(0, e.getMessage());
    		}
    	}
		return new JsonResult(1, "");
    }
    @RequestMapping("/system/config/edit")
    @RequiresPermissions("config_view")
    public String edit(HttpServletRequest request, Model model, Integer id){
    	if(id !=null) {
    		Config config = configService.getConfig(id);
    		model.addAttribute("config",config);
    	}
		return "/system/config/config_edit";
    }
    @RequestMapping("/system/config/save")
    @RequiresPermissions("config_modify")
    public String save(Model model, HttpServletRequest request, @ModelAttribute("config") Config config){
    	String url = "/system/config/config_edit";
    	if(config.getCfCode().trim().equals("")) {
    		model.addAttribute("action_result", "请输入参数编码！");
    		return url;
    	}
    	if(config.getCfName().trim().equals("")) {
    		model.addAttribute("action_result", "请输入参数名称！");
    		return url;
    	}
    	if(config.getId() == null){
    		config.setIsSystem(0);
    		configService.addConfig(config);
    		//config.setId(configService.getMaxId());
    	}else{
    		configService.updateConfig(config);
    	}
    	model.addAttribute("action_result", "success");
    	return url;
    }
    @RequestMapping("/system/config/list")
    @RequiresPermissions("config_view")
    public String list(Model model, HttpServletRequest request)throws Exception{
		System.out.println("shiro:");
		Subject subject = SecurityUtils.getSubject();
		System.out.println("shiro session:");
		System.out.println(subject.getSession().getAttribute("userName"));
        return "system/config/config_list";
    }
    @RequestMapping("/system/config/getConfigList")
    @ResponseBody
    public String getConfigList(
    		@RequestParam(required=true) Integer pageNumber,
    		@RequestParam(required=true) Integer pageSize,
    		@RequestParam(required=false) String sortOrder,
    		@RequestParam(required=false) String sortName,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request, HttpSession session
    		) throws Exception{
		Page<Config> page = new Page<Config>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if(sortName !=null) { 
			page.setSortName(sortName);
			page.setSortOrder(sortOrder);
		}
		Map<String,Object> mapPage = new HashMap<>();
		mapPage.put("page", page);
		mapPage.put("keyword", keyword);
		List<Config> list = configService.getConfigList(mapPage);
		Map<String,Object> mapExport = new HashMap<>();
		mapExport.put("sql", page.getSql());
		//设置要导出的列
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("cfCode", "参数编码");
		fields.put("cfName", "参数名称");
		fields.put("cfValue", "参数值");
		ExportInfo export = new ExportInfo(fields, page.getSql());
		session.setAttribute(exportId, export);
		//RedisClient redis =new RedisClient();
		//redis.set("fruit","banana");
		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("total", page.getTotalRecord());
		mapJson.put("pageSize", pageSize);
		mapJson.put("rows", list);
		return new ObjectMapper().writeValueAsString(mapJson);
    }
    @RequestMapping("/system/config/exportToExcel")
    public String exportToExcel() {
    	return "redirect:/system/excel/excelExport?exportId=" + exportId;
    }
}