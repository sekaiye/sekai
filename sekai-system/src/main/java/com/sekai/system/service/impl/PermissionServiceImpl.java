package com.sekai.system.service.impl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.system.mapper.PermissionMapper;
import com.sekai.system.model.Permission;
import com.sekai.system.service.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {  
    @Resource
    private PermissionMapper mapper;
    public List<Permission> getAllPermissions(){
    	return this.mapper.getAllPermissions();
    }
    public List<Permission> getPermissionsByUserId(Integer userId){
    	return this.mapper.getPermissionsByUserId(userId);
    }
}  
