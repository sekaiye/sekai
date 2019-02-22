package com.sekai.system.redis;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class SessionUtil {
	private String sessionId;
	@Autowired
	private RedisUtil redisUtil;
	private String sessionIdPrefix="sid-";
	public SessionUtil() {
		
		sessionId=SecurityUtils.getSubject().getSession().getId().toString();
		//redisUtil=new RedisUtil();
	}
	public void set(String key,Object value) {
    	
    	String sessionKey=sessionIdPrefix+sessionId+"-"+key;
    	redisUtil.set(sessionKey, value);
    }
    public Object get(String key) {
    	String sessionKey=sessionIdPrefix+sessionId+"-"+key;
    	System.out.println("sessionKey:"+sessionKey);
    	return redisUtil.get(sessionKey);
    }
}
