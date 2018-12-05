package com.sekai.base.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sekai.base.model.Warehouse;

public interface WarehouseMapper {
	void addWarehouse(@Param("wh") Warehouse wh);
	void deleteWarehouse(@Param("whId") Integer whId);
	void updateWarehouse(@Param("wh") Warehouse wh);
	Integer getMaxId();
	Warehouse getWarehouse(@Param("whId") Integer whId);
	List<Warehouse> getWarehouseList(Map<String, Object> map);
}
