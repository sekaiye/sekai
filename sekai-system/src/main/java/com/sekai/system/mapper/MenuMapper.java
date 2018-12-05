package com.sekai.system.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sekai.system.model.Menu;

public interface MenuMapper {
	Menu getMenu(@Param("menuId")Integer menuId);
	List<Menu> getAllMenus(@Param("parentCode")String parentCode);
	List<Menu> getMenusByUser(@Param("userId")Integer userId);
	
}
