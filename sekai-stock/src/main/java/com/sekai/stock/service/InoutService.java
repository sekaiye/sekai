package com.sekai.stock.service;

import java.util.List;
import java.util.Map;
import com.sekai.stock.model.Inout;

public interface InoutService {  
    void addInout(Inout inout);
    void deleteInout(Integer id);
    void updateInout(Inout inout);
    Integer getMaxId();
    Inout getInout(Integer id);
    List<Inout> getInoutList(Map<String, Object> map);
} 
