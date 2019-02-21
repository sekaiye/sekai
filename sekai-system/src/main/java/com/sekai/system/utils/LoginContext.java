package com.sekai.system.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.sekai.system.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class LoginContext implements Serializable {
	public void createContext(User user) {
		Subject subject = SecurityUtils.getSubject();
		org.apache.shiro.session.Session session=subject.getSession();
    	session.setAttribute("userId", user.getUserId());
    	session.setAttribute("userName", user.getUserName());
    	session.setAttribute("nickName", user.getNickName());
    	session.setAttribute("loginTime", new Date());
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	session.setAttribute("loginDate", sdf.format(new Date()));
    	if(user.getUserName().equals("root")) {
    		session.setAttribute("isAdmin", "admin");
    	}
    	else {
    		session.setAttribute("isAdmin", null);
    	}
	}
	public Integer getUserId() {
		return Integer.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId").toString());
	}
	public String getNickName() {
		return SecurityUtils.getSubject().getSession().getAttribute("nickName").toString();
	}
	public String getLoginTime() {
		return SecurityUtils.getSubject().getSession().getAttribute("loginTime").toString();
	}
	public String getLoginDate() {
		return SecurityUtils.getSubject().getSession().getAttribute("loginDate").toString();
	}
	public Boolean isAdmin() {
		return Boolean.valueOf(SecurityUtils.getSubject().getSession().getAttribute("isAdmin").toString());
	}
}
