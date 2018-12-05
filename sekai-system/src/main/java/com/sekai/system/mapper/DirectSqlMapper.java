package com.sekai.system.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface DirectSqlMapper {
	List<Map<String, Object>> getList(@Param("sqlScript")String sqlScript);
}
