package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleProfitByClient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2097023513206718206L;
	private String fid;
	private String clientCode;
	private String name; // 客户名称
	private Integer branchNum;
	private BigDecimal wholesaleProfit; // 批发毛利
	private BigDecimal wholesaleProfitRate; // 批发毛利率
	private BigDecimal wholesaleMoney; // 批发金额 （批发金额 = 销售金额 - 退货金额）
	private BigDecimal retailPrice; // 零售金额
	private BigDecimal retailProfit; // 零售毛利
	private BigDecimal retailProfitRate; // 零售毛利率
	private BigDecimal wholesaleCost;
	private BigDecimal wholesaleQty;
	private BigDecimal wholesaleUseQty;
	private BigDecimal wholesaleBaseQty;
	private BigDecimal presentQty;
	private BigDecimal presentUseQty;
	private BigDecimal presentCostMoney;
	private BigDecimal presentMoney;


	public BigDecimal getPresentQty() {
		return presentQty;
	}

	public void setPresentQty(BigDecimal presentQty) {
		this.presentQty = presentQty;
	}

	public BigDecimal getPresentUseQty() {
		return presentUseQty;
	}

	public void setPresentUseQty(BigDecimal presentUseQty) {
		this.presentUseQty = presentUseQty;
	}

	public BigDecimal getPresentCostMoney() {
		return presentCostMoney;
	}

	public void setPresentCostMoney(BigDecimal presentCostMoney) {
		this.presentCostMoney = presentCostMoney;
	}

	public BigDecimal getPresentMoney() {
		return presentMoney;
	}

	public void setPresentMoney(BigDecimal presentMoney) {
		this.presentMoney = presentMoney;
	}

	public BigDecimal getWholesaleBaseQty() {
		return wholesaleBaseQty;
	}

	public void setWholesaleBaseQty(BigDecimal wholesaleBaseQty) {
		this.wholesaleBaseQty = wholesaleBaseQty;
	}

	public BigDecimal getWholesaleUseQty() {
		return wholesaleUseQty;
	}

	public void setWholesaleUseQty(BigDecimal wholesaleUseQty) {
		this.wholesaleUseQty = wholesaleUseQty;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BigDecimal getWholesaleMoney() {
		return wholesaleMoney;
	}

	public void setWholesaleMoney(BigDecimal wholesaleMoney) {
		this.wholesaleMoney = wholesaleMoney;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public BigDecimal getRetailProfit() {
		return retailProfit;
	}

	public void setRetailProfit(BigDecimal retailProfit) {
		this.retailProfit = retailProfit;
	}

	public BigDecimal getRetailProfitRate() {
		return retailProfitRate;
	}

	public void setRetailProfitRate(BigDecimal retailProfitRate) {
		this.retailProfitRate = retailProfitRate;
	}

	public BigDecimal getWholesaleCost() {
		return wholesaleCost;
	}

	public void setWholesaleCost(BigDecimal wholesaleCost) {
		this.wholesaleCost = wholesaleCost;
	}

	public BigDecimal getWholesaleQty() {
		return wholesaleQty;
	}

	public void setWholesaleQty(BigDecimal wholesaleQty) {
		this.wholesaleQty = wholesaleQty;
	}
}
