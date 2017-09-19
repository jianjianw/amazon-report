package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerAnalysisRange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5843503090721456109L;
	private String customerRange;//客单范围
	private BigDecimal customerNums;//客单量
	private BigDecimal totalMoney;//金额小计
	private BigDecimal customerAvePrice;//平均客单价
	private BigDecimal numRate;//数量占比
	private BigDecimal moneyRate;//金额占比

	public String getCustomerRange() {
		return customerRange;
	}

	public void setCustomerRange(String customerRange) {
		this.customerRange = customerRange;
	}

	public BigDecimal getCustomerNums() {
		return customerNums;
	}

	public void setCustomerNums(BigDecimal customerNums) {
		this.customerNums = customerNums;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getCustomerAvePrice() {
		return customerAvePrice;
	}

	public void setCustomerAvePrice(BigDecimal customerAvePrice) {
		this.customerAvePrice = customerAvePrice;
	}

	public BigDecimal getNumRate() {
		return numRate;
	}

	public void setNumRate(BigDecimal numRate) {
		this.numRate = numRate;
	}

	public BigDecimal getMoneyRate() {
		return moneyRate;
	}

	public void setMoneyRate(BigDecimal moneyRate) {
		this.moneyRate = moneyRate;
	}

}
