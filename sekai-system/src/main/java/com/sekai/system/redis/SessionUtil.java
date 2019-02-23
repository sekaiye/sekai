package com.sekai.system.redis;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {
	///private String sessionId;
	@Autowired
	public RedisUtil redisUtil;
	private String sessionIdPrefix="sid-";
	public SessionUtil() {
		//sessionId="abc-";

	}
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
	public void set(String key,Object value) {
		String sessionId=SecurityUtils.getSubject().getSession().getId().toString();
    	String sessionKey=sessionIdPrefix+sessionId+"-"+key;
    	redisUtil.set(sessionKey, value);
    }
    public Object get(String key) {
		String sessionId=SecurityUtils.getSubject().getSession().getId().toString();
    	String sessionKey=sessionIdPrefix+sessionId+"-"+key;
    	System.out.println("sessionKey:"+sessionKey);
    	return redisUtil.get(sessionKey);
    }
}
