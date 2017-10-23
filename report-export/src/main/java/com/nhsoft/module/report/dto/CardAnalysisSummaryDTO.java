package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardAnalysisSummaryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8169379766664191390L;
	private BigDecimal totalPaymentMoney;// 累计付款
	private BigDecimal cardBalance;// 当前余额
	private BigDecimal lastCardBalance;// 上期余额
	private BigDecimal paymentMoney;// 本期付款金额
	private BigDecimal depositMoney;// 本期存款金额
	private BigDecimal consumeMoney;// 本期消费
	private BigDecimal balanceMoney;// 期末余额
	private BigDecimal revokeMoney;// 本期卡回收金额

	public BigDecimal getRevokeMoney() {
		return revokeMoney;
	}

	public void setRevokeMoney(BigDecimal revokeMoney) {
		this.revokeMoney = revokeMoney;
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
