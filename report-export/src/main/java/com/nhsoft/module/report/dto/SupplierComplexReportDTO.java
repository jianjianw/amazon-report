package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierComplexReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1497665931543846516L;
	private Integer supplierNum;
	private String supplierCode;// 供应商代码
	private String supplierName;// 供应商名称

	private Integer branchNum;
	private String branchName;
	private String branchCode;

	private Integer itemNum;
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String itemSpec;// 商品规格
	private String categoryCode;// 类别代码
	private String categoryName;// 类别名称

	private BigDecimal inQty;
	private BigDecimal inMoney;

	private BigDecimal saleQty;// 销售数量
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal saleCost;// 销售成本
	private BigDecimal saleProfit;// 毛利
	private BigDecimal saleProfitRate;// 毛利率

	private BigDecimal stockQty;// 库存数量

	private BigDecimal roundRate; //周转率
	
	public SupplierComplexReportDTO() {
		setInQty(BigDecimal.ZERO);
		setInMoney(BigDecimal.ZERO);
		setSaleQty(BigDecimal.ZERO);
		setSaleMoney(BigDecimal.ZERO);
		setSaleCost(BigDecimal.ZERO);
		setSaleProfit(BigDecimal.ZERO);
		setSaleProfitRate(BigDecimal.ZERO);
		setStockQty(BigDecimal.ZERO);
		setRoundRate(BigDecimal.ZERO);
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public BigDecimal getInQty() {
		return inQty;
	}

	public void setInQty(BigDecimal inQty) {
		this.inQty = inQty;
	}

	public BigDecimal getInMoney() {
		return inMoney;
	}

	public void setInMoney(BigDecimal inMoney) {
		this.inMoney = inMoney;
	}

	public BigDecimal getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(BigDecimal saleQty) {
		this.saleQty = saleQty;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public BigDecimal getSaleProfit() {
		return saleProfit;
	}

	public void setSaleProfit(BigDecimal saleProfit) {
		this.saleProfit = saleProfit;
	}

	public BigDecimal getSaleProfitRate() {
		return saleProfitRate;
	}

	public void setSaleProfitRate(BigDecimal saleProfitRate) {
		this.saleProfitRate = saleProfitRate;
	}

	public BigDecimal getStockQty() {
		return stockQty;
	}

	public void setStockQty(BigDecimal stockQty) {
		this.stockQty = stockQty;
	}

	public BigDecimal getRoundRate() {
		return roundRate;
	}

	public void setRoundRate(BigDecimal roundRate) {
		this.roundRate = roundRate;
	}

}
