package com.sekai.system.redis;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class SessionUtilRedis implements SessionUtil{

	@Autowired
	public RedisUtil redisUtil;
	private String sessionIdPrefix="sid-";
	/*spring注入*/
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
	/*设置session*/
	public void set(String key,Object value) {
		String sessionId=SecurityUtils.getSubject().getSession().getId().toString();
    	String sessionKey=sessionIdPrefix+sessionId+"-"+key;
    	redisUtil.set(sessionKey, value);
    }
    /*获取session*/
    public Object get(String key) {
		String sessionId=SecurityUtils.getSubject().getSession().getId().toString();
    	String sessionKey=sessionIdPrefix+sessionId+"-"+key;
    	return redisUtil.get(sessionKey);
    }
}
