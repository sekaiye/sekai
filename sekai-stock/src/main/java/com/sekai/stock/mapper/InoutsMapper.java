package com.sekai.stock.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sekai.stock.model.Inouts;

public interface InoutsMapper {
	void addInouts(@Param("inouts") Inouts inouts);
	void deleteInouts(@Param("id") Integer id);
	void updateInouts(@Param("inouts") Inouts inouts);
	Integer getMaxId();
	Inouts getInouts(@Param("id") Integer id);
	List<Inouts> getInoutsByBillId(@Param("id") Integer id);
}
