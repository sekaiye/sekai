package com.sekai.base.model;

public class Department {
    private Integer deptId;
	private String deptCode;
    private String deptName;
    private Integer parentId;
	private Department parent;
    private Integer forbid;

	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
    public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
    public Integer getForbid() {
		return forbid;
	}
	public void setForbid(Integer forbid) {
		this.forbid = forbid;
	}

}
