package com.sekai.stock.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.stock.mapper.InoutsMapper;
import com.sekai.stock.model.Inouts;
import com.sekai.stock.service.InoutsService;

@Service
@Transactional
public class InoutsServiceImpl implements InoutsService {  
    @Resource
    private InoutsMapper mapper;
    public void addInouts(Inouts inouts){
    	this.mapper.addInouts(inouts);
    }
    public void deleteInouts(Integer id){
    	this.mapper.deleteInouts(id);
    }
    public void updateInouts(Inouts inouts){
    	this.mapper.updateInouts(inouts);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public Inouts getInouts(Integer id){
        return this.mapper.getInouts(id);
    }
    public List<Inouts> getInoutsByBillId(Integer id){
    	return this.mapper.getInoutsByBillId(id);
    }
}  
