package com.nhsoft.module.report.dto;

import java.io.Serializable;

/**
 * 供应商共享门店
 * 
 * @author nhsoft
 * 
 */
public class SupplierShareBranchDTO implements Serializable {

	/**
	 * 
	 */
	

	private static final long serialVersionUID = 6392390498546654429L;
	private Integer supplierNum;
	private String systemBookCode;
	private Integer branchNum;

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

}
