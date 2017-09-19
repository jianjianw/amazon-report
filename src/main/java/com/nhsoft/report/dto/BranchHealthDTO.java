package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchHealthDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3410592010609290552L;
	private String branchHealthCategory;
	private String branchHealthName;
	private BigDecimal currentValue = BigDecimal.ZERO;
	private BigDecimal lastMonthValue = BigDecimal.ZERO;
	private BigDecimal lastYearValue = BigDecimal.ZERO;
	public String getBranchHealthCategory() {
		return branchHealthCategory;
	}
	public void setBranchHealthCategory(String branchHealthCategory) {
		this.branchHealthCategory = branchHealthCategory;
	}
	public String getBranchHealthName() {
		return branchHealthName;
	}
	public void setBranchHealthName(String branchHealthName) {
		this.branchHealthName = branchHealthName;
	}
	public BigDecimal getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(BigDecimal currentValue) {
		this.currentValue = currentValue;
	}
	public BigDecimal getLastMonthValue() {
		return lastMonthValue;
	}
	public void setLastMonthValue(BigDecimal lastMonthValue) {
		this.lastMonthValue = lastMonthValue;
	}
	public BigDecimal getLastYearValue() {
		return lastYearValue;
	}
	public void setLastYearValue(BigDecimal lastYearValue) {
		this.lastYearValue = lastYearValue;
	}
	
	
	
}
