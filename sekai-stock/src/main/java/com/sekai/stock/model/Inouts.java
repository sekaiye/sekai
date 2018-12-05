package com.sekai.stock.model;

import java.math.BigDecimal;
import java.util.Date;

import com.sekai.base.model.Material;
import com.sekai.base.model.Warehouse;

public class Inouts {
	private Integer ids;
	private Integer id;
	private Integer matId;
	private Material material;
	private BigDecimal qty;
	private BigDecimal price;
	private BigDecimal taxprice;
	private BigDecimal amount;
	private BigDecimal taxamount;
	private BigDecimal tax;
	public Integer getIds() {
		return ids;
	}
	public void setIds(Integer ids) {
		this.ids = ids;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMatId() {
		return matId;
	}
	public void setMatId(Integer matId) {
		this.matId = matId;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTaxprice() {
		return taxprice;
	}
	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getTaxamount() {
		return taxamount;
	}
	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String remark;
}
