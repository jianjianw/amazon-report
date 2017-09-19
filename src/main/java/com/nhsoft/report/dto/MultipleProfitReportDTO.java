package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MultipleProfitReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7239172621048230962L;
	private String categoryCode;
	private String categoryName;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private String itemSpec;
	private String itemUnit;

	private BigDecimal tranferQty;
	private BigDecimal tranferCost;
	private BigDecimal tranferMoney;
	private BigDecimal tranferProfit;
	private BigDecimal tranferProfitRate;

	private BigDecimal wholesaleQty;
	private BigDecimal wholesaleCost;
	private BigDecimal wholesaleMoney;
	private BigDecimal wholesaleProfit;
	private BigDecimal wholesaleProfitRate;

	private BigDecimal posSaleQty;
	private BigDecimal posSaleCost;
	private BigDecimal posSaleMoney;
	private BigDecimal posSaleProfit;
	private BigDecimal posSaleProfitRate;

	private BigDecimal totalProfit;
	private BigDecimal totalMoney;
	private BigDecimal totalProfitRate;

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

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public BigDecimal getTranferQty() {
		return tranferQty;
	}

	public void setTranferQty(BigDecimal tranferQty) {
		this.tranferQty = tranferQty;
	}

	public BigDecimal getTranferCost() {
		return tranferCost;
	}

	public void setTranferCost(BigDecimal tranferCost) {
		this.tranferCost = tranferCost;
	}

	public BigDecimal getTranferMoney() {
		return tranferMoney;
	}

	public void setTranferMoney(BigDecimal tranferMoney) {
		this.tranferMoney = tranferMoney;
	}

	public BigDecimal getTranferProfit() {
		return tranferProfit;
	}

	public void setTranferProfit(BigDecimal tranferProfit) {
		this.tranferProfit = tranferProfit;
	}

	public BigDecimal getTranferProfitRate() {
		return tranferProfitRate;
	}

	public void setTranferProfitRate(BigDecimal tranferProfitRate) {
		this.tranferProfitRate = tranferProfitRate;
	}

	public BigDecimal getWholesaleQty() {
		return wholesaleQty;
	}

	public void setWholesaleQty(BigDecimal wholesaleQty) {
		this.wholesaleQty = wholesaleQty;
	}

	public BigDecimal getWholesaleCost() {
		return wholesaleCost;
	}

	public void setWholesaleCost(BigDecimal wholesaleCost) {
		this.wholesaleCost = wholesaleCost;
	}

	public BigDecimal getWholesaleMoney() {
		return wholesaleMoney;
	}

	public void setWholesaleMoney(BigDecimal wholesaleMoney) {
		this.wholesaleMoney = wholesaleMoney;
	}

	public BigDecimal getWholesaleProfit() {
		return wholesaleProfit;
	}

	public void setWholesaleProfit(BigDecimal wholesaleProfit) {
		this.wholesaleProfit = wholesaleProfit;
	}

	public BigDecimal getWholesaleProfitRate() {
		return wholesaleProfitRate;
	}

	public void setWholesaleProfitRate(BigDecimal wholesaleProfitRate) {
		this.wholesaleProfitRate = wholesaleProfitRate;
	}

	public BigDecimal getPosSaleQty() {
		return posSaleQty;
	}

	public void setPosSaleQty(BigDecimal posSaleQty) {
		this.posSaleQty = posSaleQty;
	}

	public BigDecimal getPosSaleCost() {
		return posSaleCost;
	}

	public void setPosSaleCost(BigDecimal posSaleCost) {
		this.posSaleCost = posSaleCost;
	}

	public BigDecimal getPosSaleMoney() {
		return posSaleMoney;
	}

	public void setPosSaleMoney(BigDecimal posSaleMoney) {
		this.posSaleMoney = posSaleMoney;
	}

	public BigDecimal getPosSaleProfit() {
		return posSaleProfit;
	}

	public void setPosSaleProfit(BigDecimal posSaleProfit) {
		this.posSaleProfit = posSaleProfit;
	}

	public BigDecimal getPosSaleProfitRate() {
		return posSaleProfitRate;
	}

	public void setPosSaleProfitRate(BigDecimal posSaleProfitRate) {
		this.posSaleProfitRate = posSaleProfitRate;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getTotalProfitRate() {
		return totalProfitRate;
	}

	public void setTotalProfitRate(BigDecimal totalProfitRate) {
		this.totalProfitRate = totalProfitRate;
	}

	public MultipleProfitReportDTO() {
		super();
		this.categoryCode = "";
		this.categoryName = "";
		this.itemNum = 0;
		this.itemCode = "";
		this.itemName = "";
		this.itemSpec = "";
		this.itemUnit = "";
		this.tranferQty = BigDecimal.ZERO;
		this.tranferCost = BigDecimal.ZERO;
		this.tranferMoney = BigDecimal.ZERO;
		this.tranferProfit = BigDecimal.ZERO;
		this.tranferProfitRate = BigDecimal.ZERO;
		this.wholesaleQty = BigDecimal.ZERO;
		this.wholesaleCost = BigDecimal.ZERO;
		this.wholesaleMoney = BigDecimal.ZERO;
		this.wholesaleProfit = BigDecimal.ZERO;
		this.wholesaleProfitRate = BigDecimal.ZERO;
		this.posSaleQty = BigDecimal.ZERO;
		this.posSaleCost = BigDecimal.ZERO;
		this.posSaleMoney = BigDecimal.ZERO;
		this.posSaleProfit = BigDecimal.ZERO;
		this.posSaleProfitRate = BigDecimal.ZERO;
		this.totalProfit = BigDecimal.ZERO;
		this.totalMoney = BigDecimal.ZERO;
		this.totalProfitRate = BigDecimal.ZERO;
	}

	
}
