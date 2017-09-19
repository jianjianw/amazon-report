package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardAnalysisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3053171328841203383L;
	private Integer cardUserNum;// 卡编号
	private String cardPrintId;// 表面卡号
	private Integer cardType;// 消费卡类型
	private String cardTypeName;// 消费卡类型名称
	private BigDecimal totalPaymentMoney;// 累计付款
	private BigDecimal cardBalance;// 当前余额
	private BigDecimal lastCardBalance;// 上期余额
	private BigDecimal paymentMoney;// 本期付款金额
	private BigDecimal depositMoney;// 本期存款金额
	private BigDecimal consumeMoney;// 本期消费
	private BigDecimal balanceMoney;// 期末余额

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public String getCardPrintId() {
		return cardPrintId;
	}

	public void setCardPrintId(String cardPrintId) {
		this.cardPrintId = cardPrintId;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public BigDecimal getTotalPaymentMoney() {
		return totalPaymentMoney;
	}

	public void setTotalPaymentMoney(BigDecimal totalPaymentMoney) {
		this.totalPaymentMoney = totalPaymentMoney;
	}

	public BigDecimal getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(BigDecimal cardBalance) {
		this.cardBalance = cardBalance;
	}

	public BigDecimal getLastCardBalance() {
		return lastCardBalance;
	}

	public void setLastCardBalance(BigDecimal lastCardBalance) {
		this.lastCardBalance = lastCardBalance;
	}

	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public BigDecimal getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}

	public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public BigDecimal getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(BigDecimal balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

}
