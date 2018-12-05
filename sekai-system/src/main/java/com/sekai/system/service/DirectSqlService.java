package com.sekai.system.service;

import java.util.List;
import java.util.Map;

public interface DirectSqlService {  
	List<Map<String, Object>> getList(String sqlScript);
} 
