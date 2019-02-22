package com.sekai.system.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.sekai.system.model.User;
import com.sekai.system.redis.SessionUtil;

public class LoginContext implements Serializable {
	SessionUtil sessionUtil;
	public LoginContext() {
		sessionUtil =new SessionUtil(); 
	}
	public void createContext(User user) {
		sessionUtil.set("userId", user.getUserId());
		sessionUtil.set("userName", user.getUserName());
		sessionUtil.set("nickName", user.getNickName());
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	sessionUtil.set("loginDate", sdf.format(new Date()));
    	if(user.getUserName().equals("root")) {
    		sessionUtil.set("isAdmin", "1");
    	}
    	else {
    		sessionUtil.set("isAdmin", null);
    	}
	}
	public Integer getUserId() {
		return Integer.valueOf(sessionUtil.get("userId").toString());
	}
	public String getNickName() {
		return sessionUtil.get("nickName").toString();
	}
	public Boolean isAdmin() {
		return Boolean.valueOf(sessionUtil.get("isAdmin").toString());
	}
}
