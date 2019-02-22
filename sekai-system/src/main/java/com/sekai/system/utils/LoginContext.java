package com.sekai.system.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.sekai.system.model.User;
import com.sekai.system.redis.SessionUtil;

public class LoginContext implements Serializable {
	/**
	 * 登录用户上下文
	 */
	//private static final long serialVersionUID = -6691954500023444723L;
	SessionUtil sessionUtil;
	public LoginContext() {
		sessionUtil =new SessionUtil(); 
	}
	public void createContext(User user) {
		sessionUtil.set("loginUser", user);
	}
	public void logOut() {
		sessionUtil.set("loginUser", null);
	}
	public User getUser() {
		Object obj=sessionUtil.get("loginUser");
		if(obj!=null)
			return (User)obj;
		return null;
		
	}
	public Boolean isAdmin() {
		if(this.getUser().getUserName().equals("root"))
			return true;
		return false;
	}
}
