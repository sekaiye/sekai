package com.sekai.system.model;

public class Config {
    private Integer id;
    private String cfCode;
    private String cfName;
    private String cfValue;
    private Integer isSystem;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCfCode() {
		return cfCode;
	}
	public void setCfCode(String cfCode) {
		this.cfCode = cfCode;
	}
	public String getCfName() {
		return cfName;
	}
	public void setCfName(String cfName) {
		this.cfName = cfName;
	}
	public String getCfValue() {
		return cfValue;
	}
	public void setCfValue(String cfValue) {
		this.cfValue = cfValue;
	}
	public Integer getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
}
