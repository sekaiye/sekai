package com.sekai.stock.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sekai.stock.model.Inout;

public interface InoutMapper {
	void addInout(@Param("inout") Inout inout);
	void deleteInout(@Param("id") Integer id);
	void updateInout(@Param("inout") Inout inout);
	Integer getMaxId();
	Inout getInout(@Param("id") Integer id);
	List<Inout> getInoutList(Map<String, Object> map);
}
