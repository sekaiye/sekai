package com.sekai.system.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sekai.system.model.Permission;

public interface PermissionMapper {
	List<Permission> getAllPermissions();
	List<Permission> getPermissionsByUserId(@Param("userId")Integer userId);
}
