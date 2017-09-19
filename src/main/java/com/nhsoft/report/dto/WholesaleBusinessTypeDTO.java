package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleBusinessTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 737836517888460189L;
	private String paymentType;
	private BigDecimal totalMoney;
	private BigDecimal rate;
	
	public WholesaleBusinessTypeDTO() {
		setTotalMoney(BigDecimal.ZERO);
		setRate(BigDecimal.ZERO);
	}
	public String getPaymentType() {
		return paymentType;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
}
