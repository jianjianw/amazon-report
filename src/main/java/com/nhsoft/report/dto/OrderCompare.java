package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderCompare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -181891491475593169L;

	private String categoryCode;
	private String categoryName;
	private BigDecimal lastMoney;
	private BigDecimal lastOutMoney;
	private BigDecimal lastWholeMoney;
	private BigDecimal lastSumMoney;
	private BigDecimal thisMoney;
	private BigDecimal thisOutMoney;
	private BigDecimal thisWholeMoney;
	private BigDecimal thisSumMoney;

	public OrderCompare() {
		lastMoney = BigDecimal.ZERO;
		lastOutMoney = BigDecimal.ZERO;
		lastWholeMoney = BigDecimal.ZERO;
		lastSumMoney = BigDecimal.ZERO;
		
		thisMoney = BigDecimal.ZERO;
		thisOutMoney = BigDecimal.ZERO;
		thisWholeMoney = BigDecimal.ZERO;
		thisSumMoney = BigDecimal.ZERO;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getLastMoney() {
		return lastMoney;
	}

	public void setLastMoney(BigDecimal lastMoney) {
		this.lastMoney = lastMoney;
	}

	public BigDecimal getThisMoney() {
		return thisMoney;
	}

	public void setThisMoney(BigDecimal thisMoney) {
		this.thisMoney = thisMoney;
	}

	public BigDecimal getLastOutMoney() {
		return lastOutMoney;
	}

	public void setLastOutMoney(BigDecimal lastOutMoney) {
		this.lastOutMoney = lastOutMoney;
	}

	public BigDecimal getLastWholeMoney() {
		return lastWholeMoney;
	}

	public void setLastWholeMoney(BigDecimal lastWholeMoney) {
		this.lastWholeMoney = lastWholeMoney;
	}

	public BigDecimal getLastSumMoney() {
		return lastSumMoney;
	}

	public void setLastSumMoney(BigDecimal lastSumMoney) {
		this.lastSumMoney = lastSumMoney;
	}

	public BigDecimal getThisOutMoney() {
		return thisOutMoney;
	}

	public void setThisOutMoney(BigDecimal thisOutMoney) {
		this.thisOutMoney = thisOutMoney;
	}

	public BigDecimal getThisWholeMoney() {
		return thisWholeMoney;
	}

	public void setThisWholeMoney(BigDecimal thisWholeMoney) {
		this.thisWholeMoney = thisWholeMoney;
	}

	public BigDecimal getThisSumMoney() {
		return thisSumMoney;
	}

	public void setThisSumMoney(BigDecimal thisSumMoney) {
		this.thisSumMoney = thisSumMoney;
	}

}
