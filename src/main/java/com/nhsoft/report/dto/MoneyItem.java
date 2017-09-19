package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MoneyItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2419141730094302422L;

	private String moneyType;	//金额类型
	private BigDecimal money;   //金额
	private BigDecimal rate;
	
	
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	
}
