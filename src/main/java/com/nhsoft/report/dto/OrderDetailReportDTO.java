package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1745406293073353028L;
	private Integer branchNum;
	private Integer itemNum;
	private Integer itemMatrixNum;
	private Integer stateCode;// 状态
	private String bizday; // 营业日
	private String bizMonth; // 营业月
	private BigDecimal discount; // 折扣金额
	private BigDecimal paymentMoney; // 实付金额
	private BigDecimal profit; // 毛利
	private BigDecimal amount; // 数量
	private BigDecimal assistAmount; // 辅助数量
	private BigDecimal commission; // 提成
	private BigDecimal cost; // 成本
	private Integer itemCount;// 商品数
	private Integer branchCount;// 分店数

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public String getBizday() {
		return bizday;
	}

	public void setBizday(String bizday) {
		this.bizday = bizday;
	}

	public String getBizMonth() {
		return bizMonth;
	}

	public void setBizMonth(String bizMonth) {
		this.bizMonth = bizMonth;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAssistAmount() {
		return assistAmount;
	}

	public void setAssistAmount(BigDecimal assistAmount) {
		this.assistAmount = assistAmount;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Integer getBranchCount() {
		return branchCount;
	}

	public void setBranchCount(Integer branchCount) {
		this.branchCount = branchCount;
	}

}
