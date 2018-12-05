package com.sekai.base.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.base.mapper.MaterialMapper;
import com.sekai.base.model.Material;
import com.sekai.base.service.MaterialService;

@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {  
    @Resource
    private MaterialMapper mapper;
    public void addMaterial(Material Material){
    	this.mapper.addMaterial(Material);
    }
    public void deleteMaterial(Integer matId){
    	this.mapper.deleteMaterial(matId);
    }
    public void updateMaterial(Material Material){
    	this.mapper.updateMaterial(Material);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public Material getMaterial(Integer matId){
        return this.mapper.getMaterial(matId);
    }
    public List<Material> getMaterialList(Map<String,Object> map){
    	return this.mapper.getMaterialList(map);
    }
}  
