package com.sekai.base.service;

import java.util.List;
import java.util.Map;
import com.sekai.base.model.Material;

public interface MaterialService {  
    void addMaterial(Material Material);
    void deleteMaterial(Integer matId);
    void updateMaterial(Material Material);
    Integer getMaxId();
    Material getMaterial(Integer matId);
    List<Material> getMaterialList(Map<String, Object> map);
} 
