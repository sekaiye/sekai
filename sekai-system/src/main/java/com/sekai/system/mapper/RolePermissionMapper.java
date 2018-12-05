package com.sekai.system.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.sekai.system.model.RolePermission;

public interface RolePermissionMapper {
	void addRolePermission(@Param("rolePermission")RolePermission rolePermission);
	RolePermission getRolePermission(@Param("rpId")Integer rpId);
	void deleteRolePermissionByRoleId(@Param("roleId")Integer roleId);
	void deleteRolePermission(@Param("rpId")Integer rpId);
	Integer checkUserHasPermission(@Param("userId")Integer userId,  
			@Param("permissionCode")String permissionCode);
	List<Map<String,Object>> getRolePermissionList(@Param("roleId")Integer roleId);
}
