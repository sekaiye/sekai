package com.sekai.system.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sekai.system.model.Config;

public interface ConfigMapper {
	void addConfig(@Param("config")Config config);
	void deleteConfig(@Param("id")Integer id);
	void updateConfig(@Param("config")Config config);
	Integer getMaxId();
	Config getConfig(@Param("id")Integer id);
	Config getConfigByCode(@Param("cfCode")String cfCode);
	List<Config> getConfigList(Map<String,Object> map);
	List<Config> getAllConfigs();
}
