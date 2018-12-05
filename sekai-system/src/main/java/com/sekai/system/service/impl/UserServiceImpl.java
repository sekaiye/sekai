package com.sekai.system.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.system.mapper.UserMapper;
import com.sekai.system.model.User;
import com.sekai.system.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {  
    @Resource
    private UserMapper mapper;
    public void addUser(User user){
    	this.mapper.addUser(user);
    }
    public void deleteUser(Integer userId){
    	this.mapper.deleteUser(userId);
    }
    public void updateUser(User user){
    	this.mapper.updateUser(user);
    }
    public void updatePwd(Integer userId, String pwd){
    	this.mapper.updatePassword(userId, pwd);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public User getUser(Integer userId){
        return this.mapper.getUser(userId);
    }
    public List<User> getUserList(Map<String,Object> map){
    	return this.mapper.getUserList(map);
    }
    public User getUserByUserName(String userName) {
    	return this.mapper.getUserByUserName(userName);
    }
    public Integer checkExistUserName(String userName) {
    	return this.mapper.checkExistUserName(userName);
    }
    public Integer checkUserLogin(String userName, String pwd) {
    	String newPwd = new com.sekai.system.utils.EncryptUtils().Encode(pwd);
    	//System.out.println(userName+" "+newPwd);
    	return this.mapper.checkUserLogin(userName, newPwd);
    }
}  
