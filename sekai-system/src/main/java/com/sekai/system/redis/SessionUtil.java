package com.sekai.system.redis;

public interface SessionUtil {
	void set(String key,Object value);
    Object get(String key);
}
