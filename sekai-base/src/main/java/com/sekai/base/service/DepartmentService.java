package com.sekai.base.service;
import java.util.List;
import java.util.Map;
import com.sekai.base.model.Department;

public interface DepartmentService {  
    void addDepartment(Department Department);
    void deleteDepartment(Integer deptId);
    void updateDepartment(Department Department);
    Integer getMaxId();
    Department getDepartment(Integer deptId);
    List<Department> getDepartmentList(Map<String, Object> map);
} 
