package com.sekai.system.mapper;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sekai.system.model.Role;

public interface RoleMapper {
	void addRole(@Param("role")Role role);
	void deleteRole(@Param("roleId")Integer roleId);
	void updateRole(@Param("role")Role role);
	Integer getMaxId();
	Role getRole(@Param("roleId")Integer roleId);
	List<Role> getRoleList(Map<String,Object> map);
	List<Role> getAllRoles();
	List<Role> getRolesByUserName(@Param("userName")String userName);
	
}
