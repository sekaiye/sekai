package com.sekai.system.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.system.mapper.UserRoleMapper;
import com.sekai.system.model.UserRole;
import com.sekai.system.service.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {  
    @Resource
    private UserRoleMapper mapper;
    public void addUserRole(UserRole userRole){
    	this.mapper.addUserRole(userRole);
    }
    public void deleteUserRole(Integer urId){
    	this.mapper.deleteUserRole(urId);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public UserRole getUserRole(Integer urId){
        return this.mapper.getUserRole(urId);
    }
    public List<UserRole> getUserRoleList(Map<String,Object> map){
    	return this.mapper.getUserRoleList(map);
    }
    public Integer checkExist(Integer userId,Integer roleId) {
    	return this.mapper.checkExist(userId,roleId);
    }
}  
