package com.sekai.system.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.sekai.system.model.Menu;

public interface MenuService {  
    Menu getMenu(Integer moduleId);
    List<Menu> getAllMenus(String parentCode);
    String getSystemMenu();
} 
