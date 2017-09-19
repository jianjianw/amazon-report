package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchBusinessSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4967672315281665552L;
	private String branchName;
	private Integer branchNum;
	private Integer cardCount;
	private BigDecimal consumeMoney;
	private BigDecimal depositCash;
	private BigDecimal depositMoney;
	
	/**
	 * 总合计 返回列表的第一个对象赋值
	 */
	private Integer cardCountSum;
	private BigDecimal consumeMoneySum;
	private BigDecimal depositCashSum;
	private BigDecimal depositMoneySum;
	
	public BranchBusinessSummary(){
		cardCount = 0;
		consumeMoney = BigDecimal.ZERO;
		depositCash = BigDecimal.ZERO;
		depositMoney = BigDecimal.ZERO;
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

	public Integer getCardCount() {
		return cardCount;
	}

	public void setCardCount(Integer cardCount) {
		this.cardCount = cardCount;
	}

	public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public BigDecimal getDepositCash() {
		return depositCash;
	}

	public void setDepositCash(BigDecimal depositCash) {
		this.depositCash = depositCash;
	}

	public BigDecimal getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}

	public Integer getCardCountSum() {
		return cardCountSum;
	}

	public void setCardCountSum(Integer cardCountSum) {
		this.cardCountSum = cardCountSum;
	}

	public BigDecimal getConsumeMoneySum() {
		return consumeMoneySum;
	}

	public void setConsumeMoneySum(BigDecimal consumeMoneySum) {
		this.consumeMoneySum = consumeMoneySum;
	}

	public BigDecimal getDepositCashSum() {
		return depositCashSum;
	}

	public void setDepositCashSum(BigDecimal depositCashSum) {
		this.depositCashSum = depositCashSum;
	}

	public BigDecimal getDepositMoneySum() {
		return depositMoneySum;
	}

	public void setDepositMoneySum(BigDecimal depositMoneySum) {
		this.depositMoneySum = depositMoneySum;
	}

}
