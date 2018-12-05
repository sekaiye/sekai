package com.sekai.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.system.mapper.RoleMapper;
import com.sekai.system.model.Role;
import com.sekai.system.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {  
    @Resource
    private RoleMapper mapper;
    public void addRole(Role role){
    	this.mapper.addRole(role);
    }
    public void deleteRole(Integer roleId){
    	this.mapper.deleteRole(roleId);
    }
    public void updateRole(Role role){
    	this.mapper.updateRole(role);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public Role getRole(Integer roleId){
        return this.mapper.getRole(roleId);
    }
    public List<Role> getRoleList(Map<String,Object> map){
    	return this.mapper.getRoleList(map);
    }
    public List<Role> getAllRoles(){
    	return this.mapper.getAllRoles();
    }
    public List<Role> getRolesByUserName(String userName){
    	return this.mapper.getRolesByUserName(userName);
    }
}  
