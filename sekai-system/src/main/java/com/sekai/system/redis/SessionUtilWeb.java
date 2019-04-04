package com.sekai.system.redis;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class SessionUtilWeb implements SessionUtil{
	@Autowired
	private HttpSession session;
	/*设置session*/
	public void set(String key,Object value) {
		session.setAttribute(key,value);
    }
    /*获取session*/
    public Object get(String key) {
    	return session.getAttribute(key);
    }
}
