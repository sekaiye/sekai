package com.sekai.system.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sekai.system.model.User;

public interface UserMapper {
	void addUser(@Param("user")User user);
	void deleteUser(@Param("userId")Integer userId);
	void updateUser(@Param("user")User user);
	void updatePassword(@Param("userId")Integer userId, @Param("pwd")String pwd);
	Integer getMaxId();
	User getUser(@Param("userId")Integer userId);
	List<User> getUserList(Map<String,Object> map);
	User getUserByUserName(@Param("userName")String userName);
	Integer checkExistUserName(@Param("userName")String userName);
	Integer checkUserLogin(@Param("userName")String userName, @Param("pwd")String pwd);
}
