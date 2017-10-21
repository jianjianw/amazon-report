package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalerCommissionCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5229548356091328838L;
	private String saler; //销售员
	private Integer branchNum;
	private String branchName; //门店名称
	private Integer cardUserCount; //推荐发卡数
	private BigDecimal cardDepositCashSum; //卡付款金额
	private BigDecimal cardDepositMoneySum; //卡存款金额
	private BigDecimal cardConsumeMoneySum; //卡消费金额
	
	public String getSaler() {
		return saler;
	}
	
	public void setSaler(String saler) {
		this.saler = saler;
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
	
	public Integer getCardUserCount() {
		return cardUserCount;
	}
	
	public void setCardUserCount(Integer cardUserCount) {
		this.cardUserCount = cardUserCount;
	}
	
	public BigDecimal getCardDepositCashSum() {
		return cardDepositCashSum;
	}
	
	public void setCardDepositCashSum(BigDecimal cardDepositCashSum) {
		this.cardDepositCashSum = cardDepositCashSum;
	}
	
	public BigDecimal getCardDepositMoneySum() {
		return cardDepositMoneySum;
	}
	
	public void setCardDepositMoneySum(BigDecimal cardDepositMoneySum) {
		this.cardDepositMoneySum = cardDepositMoneySum;
	}
	
	public BigDecimal getCardConsumeMoneySum() {
		return cardConsumeMoneySum;
	}
	
	public void setCardConsumeMoneySum(BigDecimal cardConsumeMoneySum) {
		this.cardConsumeMoneySum = cardConsumeMoneySum;
	}
}
