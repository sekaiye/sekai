package com.sekai.system.redis;

import org.apache.shiro.SecurityUtils;

public class SessionUtil {
	private String sessionId;
	private RedisUtil redisUtil;
	private String sessionIdPrefix="sid-";
	public SessionUtil() {
		sessionId="123-";
		//sessionId=SecurityUtils.getSubject().getSession().getId().toString();
		redisUtil=new RedisUtil();
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
