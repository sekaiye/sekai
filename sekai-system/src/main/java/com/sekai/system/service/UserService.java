package com.sekai.system.service;

import java.util.List;
import java.util.Map;
import com.sekai.system.model.User;

public interface UserService {  
    void addUser(User user);
    void deleteUser(Integer userId);
    void updateUser(User user);
    void updatePwd(Integer userId, String pwd);
    Integer getMaxId();
    User getUser(Integer userId);
    List<User> getUserList(Map<String,Object> map);
    User getUserByUserName(String userName);
    Integer checkExistUserName(String userName);
    Integer checkUserLogin(String userName, String pwd);
} 
