package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierLianYing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6418554883161643101L;
	private Integer supplierNum;
	private String supplierCode;
	private String supplierName;
	private Integer itemNum;
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String itemSpec;// 商品规格
	private String categoryCode;
	private String categoryName;
	private BigDecimal saleAmount;
	private BigDecimal saleMoney;
	private BigDecimal pointMoney;
	private BigDecimal payMoney;
	
	public SupplierLianYing() {
		setSaleAmount(BigDecimal.ZERO);
		setSaleMoney(BigDecimal.ZERO);
		setPointMoney(BigDecimal.ZERO);
		setPayMoney(BigDecimal.ZERO);
	}
	
	public Integer getSupplierNum() {
		return supplierNum;
	}
	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemSpec() {
		return itemSpec;
	}
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}
	public BigDecimal getPointMoney() {
		return pointMoney;
	}
	public void setPointMoney(BigDecimal pointMoney) {
		this.pointMoney = pointMoney;
	}
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
}
