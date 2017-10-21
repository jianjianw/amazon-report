package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MobileBusinessDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5187465479406982177L;
	private String branchName;
	private Integer branchNum;
	private String detailType;
	private BigDecimal detailValue;
	
	public MobileBusinessDetailDTO(){
		setDetailValue(BigDecimal.ZERO);
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public BigDecimal getDetailValue() {
		return detailValue;
	}

	public void setDetailValue(BigDecimal detailValue) {
		this.detailValue = detailValue;
	}

}
