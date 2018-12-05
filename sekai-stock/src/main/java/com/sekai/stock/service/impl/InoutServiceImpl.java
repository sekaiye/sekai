package com.sekai.stock.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.stock.mapper.InoutMapper;
import com.sekai.stock.model.Inout;
import com.sekai.stock.service.InoutService;

@Service
@Transactional
public class InoutServiceImpl implements InoutService {  
    @Resource
    private InoutMapper mapper;
    public void addInout(Inout inout){
    	this.mapper.addInout(inout);
    }
    public void deleteInout(Integer id){
    	this.mapper.deleteInout(id);
    }
    public void updateInout(Inout inout){
    	this.mapper.updateInout(inout);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public Inout getInout(Integer id){
        return this.mapper.getInout(id);
    }
    public List<Inout> getInoutList(Map<String,Object> map){
    	return this.mapper.getInoutList(map);
    }
}  
