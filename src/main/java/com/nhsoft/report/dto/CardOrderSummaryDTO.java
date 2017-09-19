package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardOrderSummaryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5184389558778821479L;
	private BigDecimal cardOrderCashSum;
	private BigDecimal cardOrderMoneySum;
	private BigDecimal cardOrderInvoiceSum;
	private Integer cardOrderAmount;
	private Integer cardOrderCount; //记录条数
	
	public CardOrderSummaryDTO() {
		setCardOrderCashSum(BigDecimal.ZERO);
		setCardOrderMoneySum(BigDecimal.ZERO);
		setCardOrderInvoiceSum(BigDecimal.ZERO);
		cardOrderAmount = 0;
		cardOrderCount = 0;
	}
	
	public BigDecimal getCardOrderCashSum() {
		return cardOrderCashSum;
	}
	public BigDecimal getCardOrderMoneySum() {
		return cardOrderMoneySum;
	}
	public BigDecimal getCardOrderInvoiceSum() {
		return cardOrderInvoiceSum;
	}
	public Integer getCardOrderAmount() {
		return cardOrderAmount;
	}
	public Integer getCardOrderCount() {
		return cardOrderCount;
	}
	public void setCardOrderCashSum(BigDecimal cardOrderCashSum) {
		this.cardOrderCashSum = cardOrderCashSum;
	}
	public void setCardOrderMoneySum(BigDecimal cardOrderMoneySum) {
		this.cardOrderMoneySum = cardOrderMoneySum;
	}
	public void setCardOrderInvoiceSum(BigDecimal cardOrderInvoiceSum) {
		this.cardOrderInvoiceSum = cardOrderInvoiceSum;
	}
	public void setCardOrderAmount(Integer cardOrderAmount) {
		this.cardOrderAmount = cardOrderAmount;
	}
	public void setCardOrderCount(Integer cardOrderCount) {
		this.cardOrderCount = cardOrderCount;
	}
}
