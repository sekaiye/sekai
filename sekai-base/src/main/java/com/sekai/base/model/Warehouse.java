package com.sekai.base.model;

public class Warehouse {
    private Integer whId;
    private String whCode;
    private String whName;
	private Integer forbid;
	private Integer isCtrlStock;
	public Integer getWhId() {
		return whId;
	}
	public void setWhId(Integer whId) {
		this.whId = whId;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}
	public Integer getForbid() {
		return forbid;
	}
	public void setForbid(Integer forbid) {
		this.forbid = forbid;
	}
	public Integer getIsCtrlStock() {
		return isCtrlStock;
	}
	public void setIsCtrlStock(Integer isCtrlStock) {
		this.isCtrlStock = isCtrlStock;
	}
}
