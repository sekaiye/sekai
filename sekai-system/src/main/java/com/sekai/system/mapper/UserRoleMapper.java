package com.sekai.system.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sekai.system.model.UserRole;

public interface UserRoleMapper {
	void addUserRole(@Param("userRole")UserRole userRole);
	void deleteUserRole(@Param("urId")Integer urId);
	Integer getMaxId();
	UserRole getUserRole(@Param("urId")Integer urId);
	List<UserRole> getUserRoleList(Map<String,Object> map);
	Integer checkExist(@Param("userId")Integer userId,@Param("roleId")Integer roleId);
}
