package com.sekai.base.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.base.mapper.WarehouseMapper;
import com.sekai.base.model.Warehouse;
import com.sekai.base.service.WarehouseService;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {  
    @Resource
    private WarehouseMapper mapper;
    public void addWarehouse(Warehouse Warehouse){
    	this.mapper.addWarehouse(Warehouse);
    }
    public void deleteWarehouse(Integer whId){
    	this.mapper.deleteWarehouse(whId);
    }
    public void updateWarehouse(Warehouse Warehouse){
    	this.mapper.updateWarehouse(Warehouse);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public Warehouse getWarehouse(Integer whId){
        return this.mapper.getWarehouse(whId);
    }
    public List<Warehouse> getWarehouseList(Map<String,Object> map){
    	return this.mapper.getWarehouseList(map);
    }
}  
