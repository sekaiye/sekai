package com.sekai.base.service;
import java.util.List;
import java.util.Map;
import com.sekai.base.model.Warehouse;

public interface WarehouseService {  
    void addWarehouse(Warehouse Warehouse);
    void deleteWarehouse(Integer whId);
    void updateWarehouse(Warehouse Warehouse);
    Integer getMaxId();
    Warehouse getWarehouse(Integer whId);
    List<Warehouse> getWarehouseList(Map<String, Object> map);
} 
