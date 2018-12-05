package com.sekai.system.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sekai.system.mapper.DirectSqlMapper;
import com.sekai.system.service.DirectSqlService;


@Service
public class DirectSqlServiceImpl implements DirectSqlService {
	@Resource
	private DirectSqlMapper mapper;
    
    public List<Map<String, Object>> getList(String sqlScript){
    	return this.mapper.getList(sqlScript);
    }
}  
