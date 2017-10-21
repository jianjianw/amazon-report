package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalerCommissionDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3127802969554740872L;
	private String saler;// 销售员
	private String categoryCode;// 商品类别
	private String categoryName;// 商品名称
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String spec;// 规格
	private BigDecimal saleNums;// 销售数量
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal saleCommission;// 销售提成
	private Integer itemNum;
	private String branchName;//所属门店（销售员对应门店）
	private Integer branchNum;
	private BigDecimal saleCost;
	private BigDecimal saleProfit;
	
	
	public BigDecimal getSaleProfit() {
		return saleProfit;
	}

	public void setSaleProfit(BigDecimal saleProfit) {
		this.saleProfit = saleProfit;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public BigDecimal getSaleNums() {
		return saleNums;
	}

	public void setSaleNums(BigDecimal saleNums) {
		this.saleNums = saleNums;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public BigDecimal getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(BigDecimal saleCommission) {
		this.saleCommission = saleCommission;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

}
