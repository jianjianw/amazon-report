package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SettlementTypeDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -423499342143850382L;
	private String paymentType;
	private BigDecimal money = BigDecimal.ZERO;
	private BigDecimal discount = BigDecimal.ZERO;
	
	public String getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public BigDecimal getDiscount() {
		return discount;
	}
	
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
}
