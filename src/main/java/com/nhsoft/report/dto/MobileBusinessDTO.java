package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MobileBusinessDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2417512723235127304L;
	private BigDecimal businessMoney; // 营业额
	private BigDecimal discountMoney; // 折扣金额
	private Integer receiptCount; // 客单量
	private BigDecimal cashTotal;// 现金累计
	private BigDecimal cardDeposit;// 卡存款
	private BigDecimal businessCardMoney; // 会员销售额
	private Integer receiptCardCount; // 会员客单量
	private Integer cardAddedCount;// 新增会员数
	private BigDecimal receiptAvgMoney; // 客单价
	private BigDecimal receiptCardAvgMoney; // 会员客单价
	private BigDecimal receiptUnCardAvgMoney; // 非会员客单价

	private Integer receiptUnCardCount; // 非会员客单量

	
	private Integer branchNum;
	private String branchName;

	public MobileBusinessDTO() {
		businessMoney = BigDecimal.ZERO;
		discountMoney = BigDecimal.ZERO;
		receiptCount = 0;
		cashTotal = BigDecimal.ZERO;
		cardDeposit = BigDecimal.ZERO;
		businessCardMoney = BigDecimal.ZERO;
		receiptCardCount = 0;
		cardAddedCount = 0;
	}
	
	public BigDecimal getReceiptUnCardAvgMoney() {
		return receiptUnCardAvgMoney;
	}

	public void setReceiptUnCardAvgMoney(BigDecimal receiptUnCardAvgMoney) {
		this.receiptUnCardAvgMoney = receiptUnCardAvgMoney;
	}

	public BigDecimal getReceiptCardAvgMoney() {
		return receiptCardAvgMoney;
	}

	public void setReceiptCardAvgMoney(BigDecimal receiptCardAvgMoney) {
		this.receiptCardAvgMoney = receiptCardAvgMoney;
	}

	/**
	 * 按支付方式汇总
	 */
	private List<NameAndValueDTO> paymentList = new ArrayList<NameAndValueDTO>();

	public Integer getReceiptUnCardCount() {
		return receiptUnCardCount;
	}

	public void setReceiptUnCardCount(Integer receiptUnCardCount) {
		this.receiptUnCardCount = receiptUnCardCount;
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

	public List<NameAndValueDTO> getPaymentList() {
		return paymentList;
	}

	public BigDecimal getReceiptAvgMoney() {
		return receiptAvgMoney;
	}

	public void setReceiptAvgMoney(BigDecimal receiptAvgMoney) {
		this.receiptAvgMoney = receiptAvgMoney;
	}

	public void setPaymentList(List<NameAndValueDTO> paymentList) {
		this.paymentList = paymentList;
	}

	public BigDecimal getBusinessMoney() {
		return businessMoney;
	}

	public void setBusinessMoney(BigDecimal businessMoney) {
		this.businessMoney = businessMoney;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public BigDecimal getCashTotal() {
		return cashTotal;
	}

	public void setCashTotal(BigDecimal cashTotal) {
		this.cashTotal = cashTotal;
	}

	public BigDecimal getCardDeposit() {
		return cardDeposit;
	}

	public void setCardDeposit(BigDecimal cardDeposit) {
		this.cardDeposit = cardDeposit;
	}

	public BigDecimal getBusinessCardMoney() {
		return businessCardMoney;
	}

	public void setBusinessCardMoney(BigDecimal businessCardMoney) {
		this.businessCardMoney = businessCardMoney;
	}

	public Integer getCardAddedCount() {
		return cardAddedCount;
	}

	public void setCardAddedCount(Integer cardAddedCount) {
		this.cardAddedCount = cardAddedCount;
	}

	public Integer getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(Integer receiptCount) {
		this.receiptCount = receiptCount;
	}

	public Integer getReceiptCardCount() {
		return receiptCardCount;
	}

	public void setReceiptCardCount(Integer receiptCardCount) {
		this.receiptCardCount = receiptCardCount;
	}

}
