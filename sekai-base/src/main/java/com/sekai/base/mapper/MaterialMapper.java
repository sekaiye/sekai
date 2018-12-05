package com.sekai.base.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sekai.base.model.Material;

public interface MaterialMapper {
	void addMaterial(@Param("inv") Material Material);
	void deleteMaterial(@Param("matId") Integer matId);
	void updateMaterial(@Param("inv") Material Material);
	Integer getMaxId();
	Material getMaterial(@Param("matId") Integer matId);
	List<Material> getMaterialList(Map<String, Object> map);
}
