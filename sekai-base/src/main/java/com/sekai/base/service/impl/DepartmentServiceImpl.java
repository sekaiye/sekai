package com.sekai.base.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.base.mapper.DepartmentMapper;
import com.sekai.base.model.Department;
import com.sekai.base.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {  
    @Resource
    private DepartmentMapper mapper;
    public void addDepartment(Department Department){
    	this.mapper.addDepartment(Department);
    }
    public void deleteDepartment(Integer deptId){
    	this.mapper.deleteDepartment(deptId);
    }
    public void updateDepartment(Department Department){
    	this.mapper.updateDepartment(Department);
    }
    public Integer getMaxId(){
    	return mapper.getMaxId();
    }
    public Department getDepartment(Integer deptId){
        return this.mapper.getDepartment(deptId);
    }
    public List<Department> getDepartmentList(Map<String,Object> map){
    	return this.mapper.getDepartmentList(map);
    }
}  
