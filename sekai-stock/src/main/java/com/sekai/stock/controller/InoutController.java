package com.sekai.stock.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sekai.system.redis.SessionUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekai.stock.model.Inout;
import com.sekai.stock.model.Inouts;
import com.sekai.stock.service.InoutsService;
import com.sekai.stock.service.InoutService;
import com.sekai.stock.service.InoutsService;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.Page;

@Controller
public class InoutController {
	String exportId = "export_role_list";
	@Autowired
	private SessionUtil sessionUtil;
	@Resource
	private InoutService inoutService;
	@Resource
	private InoutsService inoutsService;
    @RequestMapping("/stock/inout/delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	for(Integer id : ids) {
    		try{
    			inoutService.deleteInout(id);
    		}catch(Exception e){
    			return new JsonResult(0, e.getMessage());
    		}
    	}
    	
		return new JsonResult(1, "");
    }
    @RequestMapping("/stock/inout/edit")
    public String edit(HttpServletRequest request, Model model, Integer id) throws Exception{
    	Inout inout = inoutService.getInout(id);
    	List<Inouts> inouts = inoutsService.getInoutsByBillId(id);
    	model.addAttribute("inout",inout);
    	String json = new ObjectMapper().writeValueAsString(inouts);
    	model.addAttribute("inout",inout);
    	model.addAttribute("inouts",json);
    	
		return "/stock/inout/inout_edit";
    }
    @RequestMapping("/stock/inout/save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("Inout") Inout Inout,
    		@ModelAttribute("json") String json) throws Exception{
    	ObjectMapper mapper = new ObjectMapper();
        List<Inouts> list = mapper.readValue(json,new TypeReference<List<Inouts>>(){});
        for(Inouts inouts : list) {
        	inoutsService.updateInouts(inouts);
        }
    	return new JsonResult(1, "");
        //66
    	/*
    	if(Inout.getId().toString().equals("")) {
    		//model.addAttribute("action_result", "请输入单据号！");
    		return "请输入单据号";
    	}
    	
    	if(Inout.getId() == null){
    		inoutService.addInout(Inout);
    		Inout.setId(inoutService.getMaxId());
    	}else{
    		inoutService.updateInout(Inout);
    	}
    	//model.addAttribute("action_result", "success");
    	return "success";*/
    }
    @RequestMapping("/stock/inout/list")
    public String list(Model model, HttpServletRequest request){
        return "stock/inout/inout_list";
    }
    @RequestMapping("/stock/inout/getInoutList")
    @ResponseBody
    public String getInoutList(
    		@RequestParam(required=true,defaultValue="1") Integer pageNumber,
    		@RequestParam(required=false,defaultValue="100") Integer pageSize,
    		@RequestParam(required=false) String sortOrder,
    		@RequestParam(required=false) String sortName,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request
    		) throws Exception{
		Page<Inout> page = new Page<Inout>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if(sortName !=null) { 
			page.setSortName(sortName);
			page.setSortOrder(sortOrder);
		}
		Map<String,Object> mapPage = new HashMap<>();
		mapPage.put("page", page);
		mapPage.put("keyword", keyword);
		List<Inout> list = inoutService.getInoutList(mapPage);
		Map<String,Object> mapExport = new HashMap<>();
		mapExport.put("sql", page.getSql());
		//设置要导出的列
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("billNum", "单据号");
		fields.put("createDate", "创建日期");
		fields.put("whName", "仓库名称");
		ExportInfo export = new ExportInfo(fields, page.getSql());
		sessionUtil.set(exportId, export);

		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("total", page.getTotalRecord());
		mapJson.put("pageSize", pageSize);
		mapJson.put("rows", list);
		
		ObjectMapper jackson = new ObjectMapper();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		jackson.setDateFormat(fmt);
		return jackson.writeValueAsString(mapJson);
    }

}