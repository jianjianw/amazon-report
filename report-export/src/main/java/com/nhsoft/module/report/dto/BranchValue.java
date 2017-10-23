package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5949200287665319546L;

	private String branchName;
	private Integer branchNum;
	private BigDecimal value;
	private BigDecimal value2;
	private BigDecimal value3;
	private BigDecimal value4;
	
	public BigDecimal getValue3() {
		return value3;
	}
	public void setValue3(BigDecimal value3) {
		this.value3 = value3;
	}
	public BigDecimal getValue4() {
		return value4;
	}
	public void setValue4(BigDecimal value4) {
		this.value4 = value4;
	}
	public BigDecimal getValue2() {
		return value2;
	}
	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
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
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result
				+ ((branchNum == null) ? 0 : branchNum.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BranchValue other = (BranchValue) obj;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (branchNum == null) {
			if (other.branchNum != null)
				return false;
		} else if (!branchNum.equals(other.branchNum))
			return false;
		return true;
	}
	
}
