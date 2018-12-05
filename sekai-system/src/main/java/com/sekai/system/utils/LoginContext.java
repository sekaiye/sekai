package com.sekai.system.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.sekai.system.model.User;

public class LoginContext {
	public void createContext(HttpSession session, User user) {
    	session.setAttribute("userId", user.getUserId());
    	session.setAttribute("userName", user.getUserName());
    	session.setAttribute("nickName", user.getNickName());
    	session.setAttribute("loginTime", new Date());
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	session.setAttribute("loginDate", sdf.format(new Date()));
    	if(user.getUserName().equals("root")) {
    		session.setAttribute("isAdmin", "1");
    	}
    	else {
    		session.setAttribute("isAdmin", null);
    	}
	}
	public Integer getUserId(HttpSession session) {
		return Integer.valueOf(session.getAttribute("userId").toString());
	}
	public String getnickName(HttpSession session) {
		return session.getAttribute("nickName").toString();
	}
	public String getLoginTime(HttpSession session) {
		return session.getAttribute("loginTime").toString();
	}
	public String getLoginDate(HttpSession session) {
		return session.getAttribute("loginDate").toString();
	}
	public Boolean isAdmin(HttpSession session) {
		return Boolean.valueOf(session.getAttribute("isAdmin").toString());
	}
}
