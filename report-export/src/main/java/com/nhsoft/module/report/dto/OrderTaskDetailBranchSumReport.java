package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderTaskDetailBranchSumReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5671896797385220220L;
	private Integer branchNum;
	private String branchName;
	private String branchCode;
	private BigDecimal theoryValue;
	private BigDecimal actualValue;
	private BigDecimal tareMoney;
	private BigDecimal actualTare;

	public OrderTaskDetailBranchSumReport() {
		setTheoryValue(BigDecimal.ZERO);
		setActualValue(BigDecimal.ZERO);
		setTareMoney(BigDecimal.ZERO);
		setActualTare(BigDecimal.ZERO);
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getTheoryValue() {
		return theoryValue;
	}

	public void setTheoryValue(BigDecimal theoryValue) {
		this.theoryValue = theoryValue;
	}

	public BigDecimal getActualValue() {
		return actualValue;
	}

	public void setActualValue(BigDecimal actualValue) {
		this.actualValue = actualValue;
	}

	public BigDecimal getTareMoney() {
		return tareMoney;
	}

	public void setTareMoney(BigDecimal tareMoney) {
		this.tareMoney = tareMoney;
	}

	public BigDecimal getActualTare() {
		return actualTare;
	}

	public void setActualTare(BigDecimal actualTare) {
		this.actualTare = actualTare;
	}

}
