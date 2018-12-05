package com.sekai.stock.model;

import java.util.Date;

import com.sekai.base.model.Warehouse;
import com.sekai.system.model.User;

public class Inout {
	private Integer id;
	private String billNum;
	private Date billDate;
	private Integer createUserId;
	private Integer modifiedUserId;
	private Date createDate;
	private Date modifiedDate;
	private Integer whId;
	private User createUser;
	private User modifiedUser;
	private Warehouse warehouse;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getModifiedUserId() {
		return modifiedUserId;
	}
	public void setModifiedUserId(Integer modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getWhId() {
		return whId;
	}
	public void setWhId(Integer whId) {
		this.whId = whId;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public User getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(User modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}



}
