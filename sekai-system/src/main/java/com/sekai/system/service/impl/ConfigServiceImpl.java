package com.sekai.system.service.impl;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.system.mapper.ConfigMapper;
import com.sekai.system.model.Config;
import com.sekai.system.service.ConfigService;
@Service
@Transactional
public class ConfigServiceImpl implements ConfigService {  
    @Resource
    private ConfigMapper mapper;
    public void addConfig(Config config){
    	this.mapper.addConfig(config);
    }
    public void deleteConfig(Integer id){
    	this.mapper.deleteConfig(id);
    }
    public void updateConfig(Config config){
    	this.mapper.updateConfig(config);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public Config getConfig(Integer id){
        return this.mapper.getConfig(id);
    }
    public Config getConfigByCode(String cfCode) {
    	return this.mapper.getConfigByCode(cfCode);
    }
    public List<Config> getConfigList(Map<String,Object> map){
    	return this.mapper.getConfigList(map);
    }
    public List<Config> getAllConfigs(){
    	return this.mapper.getAllConfigs();
    }
}  
