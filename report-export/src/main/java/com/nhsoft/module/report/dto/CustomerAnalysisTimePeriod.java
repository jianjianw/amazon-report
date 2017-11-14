package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerAnalysisTimePeriod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3509640026742344443L;
	private String timePeroid;//时段范围
	private BigDecimal customerNums;//客单量
	private BigDecimal totalMoney;//金额小计
	private BigDecimal customerAvePrice;//平均客单价
	private BigDecimal numRate;//数量占比
	private BigDecimal moenyRate;//金额占比
	private Integer branchNum;

	public String getTimePeroid() {
		return timePeroid;
	}

	public void setTimePeroid(String timePeroid) {
		this.timePeroid = timePeroid;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getNumRate() {
		return numRate;
	}

	public void setNumRate(BigDecimal numRate) {
		this.numRate = numRate;
	}

	public BigDecimal getMoenyRate() {
		return moenyRate;
	}

	public void setMoenyRate(BigDecimal moenyRate) {
		this.moenyRate = moenyRate;
	}

	public BigDecimal getCustomerNums() {
		return customerNums;
	}

	public void setCustomerNums(BigDecimal customerNums) {
		this.customerNums = customerNums;
	}

	public BigDecimal getCustomerAvePrice() {
		return customerAvePrice;
	}

	public void setCustomerAvePrice(BigDecimal customerAvePrice) {
		this.customerAvePrice = customerAvePrice;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
}
