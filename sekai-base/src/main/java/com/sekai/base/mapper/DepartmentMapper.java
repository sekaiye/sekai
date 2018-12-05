package com.sekai.base.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sekai.base.model.Department;

public interface DepartmentMapper {
	void addDepartment(@Param("dept") Department dept);
	void deleteDepartment(@Param("deptId") Integer deptId);
	void updateDepartment(@Param("dept") Department dept);
	Integer getMaxId();
	Department getDepartment(@Param("deptId") Integer deptId);
	List<Department> getDepartmentList(Map<String, Object> map);
}
