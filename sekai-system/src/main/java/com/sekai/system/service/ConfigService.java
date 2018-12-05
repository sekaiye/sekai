package com.sekai.system.service;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sekai.system.model.Config;
public interface ConfigService {  
    void addConfig(Config config);
    void deleteConfig(Integer id);
    void updateConfig(Config config);
    Integer getMaxId();
    Config getConfig(Integer cfId);
    Config getConfigByCode(String cfCode);
    List<Config> getConfigList(Map<String,Object> map);
    List<Config> getAllConfigs();
} 
