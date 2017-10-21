package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchHealthReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7239391862617982794L;
	private String branchHealthCategory;
	private String branchHealthName;
	private BigDecimal currentValue = BigDecimal.ZERO;
	private BigDecimal lastMonthValue = BigDecimal.ZERO;
	private BigDecimal lastYearValue = BigDecimal.ZERO;
	
	//仅ireport折线图用  一线城市
	private BigDecimal currentOneValue = BigDecimal.ZERO;
	private BigDecimal lastMonthOneValue = BigDecimal.ZERO;
	private BigDecimal lastYearOneValue = BigDecimal.ZERO;	
	//二线城市
	private BigDecimal currentTwoValue = BigDecimal.ZERO;
	private BigDecimal lastMonthTwoValue = BigDecimal.ZERO;
	private BigDecimal lastYearTwoValue = BigDecimal.ZERO;	
	//三线城市
	private BigDecimal currentThreeValue = BigDecimal.ZERO;
	private BigDecimal lastMonthThreeValue = BigDecimal.ZERO;
	private BigDecimal lastYearThreeValue = BigDecimal.ZERO;
	
	public BranchHealthReportDTO(){
		
	}
	
	public BranchHealthReportDTO(String branchHealthCategory, String branchHealthName){
		this.branchHealthCategory = branchHealthCategory;
		this.branchHealthName = branchHealthName;
	}
	
	public BranchHealthReportDTO(String branchHealthName){
		this.branchHealthCategory = "";
		this.branchHealthName = branchHealthName;
	}

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
		this.currentValue = currentValue.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public void setCurrentIntValue(BigDecimal currentValue) {
		this.currentValue = currentValue.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getLastMonthValue() {
		return lastMonthValue;
	}

	public void setLastMonthValue(BigDecimal lastMonthValue) {
		this.lastMonthValue = lastMonthValue.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public void setLastMonthIntValue(BigDecimal lastMonthValue) {
		this.lastMonthValue = lastMonthValue.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getLastYearValue() {
		return lastYearValue;
	}

	public void setLastYearValue(BigDecimal lastYearValue) {
		this.lastYearValue = lastYearValue.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public void setLastYearIntValue(BigDecimal lastYearValue) {
		this.lastYearValue = lastYearValue.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getCurrentOneValue() {
		return currentOneValue;
	}

	public void setCurrentOneValue(BigDecimal currentOneValue) {
		this.currentOneValue = currentOneValue;
	}

	public BigDecimal getLastYearOneValue() {
		return lastYearOneValue;
	}

	public void setLastYearOneValue(BigDecimal lastYearOneValue) {
		this.lastYearOneValue = lastYearOneValue;
	}

	public BigDecimal getCurrentTwoValue() {
		return currentTwoValue;
	}

	public void setCurrentTwoValue(BigDecimal currentTwoValue) {
		this.currentTwoValue = currentTwoValue;
	}

	public BigDecimal getLastMonthOneValue() {
		return lastMonthOneValue;
	}

	public void setLastMonthOneValue(BigDecimal lastMonthOneValue) {
		this.lastMonthOneValue = lastMonthOneValue;
	}

	public BigDecimal getLastMonthTwoValue() {
		return lastMonthTwoValue;
	}

	public void setLastMonthTwoValue(BigDecimal lastMonthTwoValue) {
		this.lastMonthTwoValue = lastMonthTwoValue;
	}

	public BigDecimal getLastMonthThreeValue() {
		return lastMonthThreeValue;
	}

	public void setLastMonthThreeValue(BigDecimal lastMonthThreeValue) {
		this.lastMonthThreeValue = lastMonthThreeValue;
	}

	public BigDecimal getLastYearTwoValue() {
		return lastYearTwoValue;
	}

	public void setLastYearTwoValue(BigDecimal lastYearTwoValue) {
		this.lastYearTwoValue = lastYearTwoValue;
	}

	public BigDecimal getCurrentThreeValue() {
		return currentThreeValue;
	}

	public void setCurrentThreeValue(BigDecimal currentThreeValue) {
		this.currentThreeValue = currentThreeValue;
	}

	public BigDecimal getLastYearThreeValue() {
		return lastYearThreeValue;
	}

	public void setLastYearThreeValue(BigDecimal lastYearThreeValue) {
		this.lastYearThreeValue = lastYearThreeValue;
	}


}
