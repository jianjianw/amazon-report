package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PackageSumDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3791695044275148819L;
	private Integer itemNum;// 商品编号
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private Integer branchNum;
	private String branchName;
	private BigDecimal packagePrice;// 筐单价
	private BigDecimal packageInAmount;// 筐调入数量
	private BigDecimal packageInMoney;// 筐调入金额
	private BigDecimal packageOutAmount;// 筐调出数量
	private BigDecimal packageOutMoney;// 筐调出金额
	private BigDecimal packageInoutAmount;//筐损益数量
	private BigDecimal packageInoutMoney;//筐损益金额
	private BigDecimal supplierPackageInAmount;//供应商筐调入数量
	private BigDecimal supplierPackageInMoney;//供应商筐调入金额
	private BigDecimal centerPackageInAmount;//中心筐调入数量
	private BigDecimal centerPackageInMoney;//中心筐调入金额
	
	public PackageSumDTO(){
		setPackageInAmount(BigDecimal.ZERO);
		setPackageInMoney(BigDecimal.ZERO);
		setPackageOutAmount(BigDecimal.ZERO);
		setPackageOutMoney(BigDecimal.ZERO);
		setSupplierPackageInAmount(BigDecimal.ZERO);
		setSupplierPackageInMoney(BigDecimal.ZERO);
		setCenterPackageInAmount(BigDecimal.ZERO);
		setCenterPackageInMoney(BigDecimal.ZERO);
		setPackageInoutAmount(BigDecimal.ZERO);
		setPackageInoutMoney(BigDecimal.ZERO);
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getSupplierPackageInAmount() {
		return supplierPackageInAmount;
	}

	public void setSupplierPackageInAmount(BigDecimal supplierPackageInAmount) {
		this.supplierPackageInAmount = supplierPackageInAmount;
	}

	public BigDecimal getSupplierPackageInMoney() {
		return supplierPackageInMoney;
	}

	public void setSupplierPackageInMoney(BigDecimal supplierPackageInMoney) {
		this.supplierPackageInMoney = supplierPackageInMoney;
	}

	public BigDecimal getCenterPackageInAmount() {
		return centerPackageInAmount;
	}

	public void setCenterPackageInAmount(BigDecimal centerPackageInAmount) {
		this.centerPackageInAmount = centerPackageInAmount;
	}

	public BigDecimal getCenterPackageInMoney() {
		return centerPackageInMoney;
	}

	public void setCenterPackageInMoney(BigDecimal centerPackageInMoney) {
		this.centerPackageInMoney = centerPackageInMoney;
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

	public BigDecimal getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

	public BigDecimal getPackageInAmount() {
		return packageInAmount;
	}

	public void setPackageInAmount(BigDecimal packageInAmount) {
		this.packageInAmount = packageInAmount;
	}

	public BigDecimal getPackageInMoney() {
		return packageInMoney;
	}

	public void setPackageInMoney(BigDecimal packageInMoney) {
		this.packageInMoney = packageInMoney;
	}

	public BigDecimal getPackageOutAmount() {
		return packageOutAmount;
	}

	public void setPackageOutAmount(BigDecimal packageOutAmount) {
		this.packageOutAmount = packageOutAmount;
	}

	public BigDecimal getPackageOutMoney() {
		return packageOutMoney;
	}

	public void setPackageOutMoney(BigDecimal packageOutMoney) {
		this.packageOutMoney = packageOutMoney;
	}

	public BigDecimal getPackageInoutAmount() {
		return packageInoutAmount;
	}

	public void setPackageInoutAmount(BigDecimal packageInoutAmount) {
		this.packageInoutAmount = packageInoutAmount;
	}

	public BigDecimal getPackageInoutMoney() {
		return packageInoutMoney;
	}

	public void setPackageInoutMoney(BigDecimal packageInoutMoney) {
		this.packageInoutMoney = packageInoutMoney;
	}

}
