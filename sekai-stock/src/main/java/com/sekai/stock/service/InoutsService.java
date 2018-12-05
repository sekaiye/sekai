package com.sekai.stock.service;

import java.util.List;
import java.util.Map;
import com.sekai.stock.model.Inouts;

public interface InoutsService {  
    void addInouts(Inouts inouts);
    void deleteInouts(Integer id);
    void updateInouts(Inouts inouts);
    Integer getMaxId();
    Inouts getInouts(Integer id);
    List<Inouts> getInoutsByBillId(Integer id);
} 
