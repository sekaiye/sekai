package com.sekai.system.service.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.system.mapper.MenuMapper;
import com.sekai.system.model.Menu;
import com.sekai.system.service.MenuService;
import com.sekai.system.service.RolePermissionService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {  
    @Resource
    private MenuMapper mapper;
    @Resource
    private RolePermissionService rolePermissionService;
    public Menu getMenu(Integer menuId){
        return this.mapper.getMenu(menuId);
    }
    public List<Menu> getAllMenus(String parentCode){
    	return this.mapper.getAllMenus(parentCode);
    }
    public String getSystemMenu(){
    	String systemMenu="";
    	List<Menu> menus = getAllMenus("root");
    	for(Menu menu : menus) {
    		String menuCode=menu.getMenuCode();
    		String menuName=menu.getMenuName();
    		String jsonMenu="{'id':'"+menuCode+"','name':'"+menuName+"','parentCode':'root','url':'','icon':'&#xe610;','order':'1','isHeader':'0','childMenus':[\n";
    		List<Menu> subMenus = getAllMenus(menuCode);
    		
    		String jsonSubMenu="";
    		int count=0;
    		//System.out.println(subMenus.size());
    		for(Menu subMenu : subMenus) {
    			
    			String subMenuCode=subMenu.getMenuCode();
    			String subMenuName=subMenu.getMenuName();
    			String subPermissionCode=subMenu.getPermissionCode();
    			String subUrl=subMenu.getUrl();
    			//boolean hasPermission=rolePermissionService.checkLoginUserHasPermission(session, subPermissionCode);
    			boolean hasPermission = SecurityUtils.getSubject().isPermitted(subPermissionCode);
    			//boolean hasPermission=true;
    			//System.out.println(subMenu.getPermissionCode()+" | "+subPermissionCode+String.valueOf(hasPermission));
    			if(!hasPermission)
    				continue;
    			jsonSubMenu+="{'id':'"+subMenuCode+"','name':'"+subMenuName+"','parentCode':'"+menuCode+"','url':'"+subUrl+"','icon':'&#xe602;','order':'1','isHeader':'0','childMenus':''},\n";
    			count++;
    		}
    		
    		if(jsonSubMenu.length()>0)
    			jsonSubMenu=jsonSubMenu.substring(0, jsonSubMenu.length()-1);
    		jsonMenu+=jsonSubMenu;
    		jsonMenu+="]},";
    		if(count==0)
    			continue;
    		systemMenu+=jsonMenu;
    	}
		if(systemMenu.length()>0)
			systemMenu=systemMenu.substring(0, systemMenu.length()-1);
    	return systemMenu;
    }
}  
