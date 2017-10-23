package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SupplierBranchGroupByDate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8049120107149912348L;

	private Integer supplierNum;
	private String supplierCode;
	private String supplierName;
	private String saleDate;
	private BigDecimal totalValue;
	private List<BranchValue> branchValues;
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public BigDecimal getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
	public List<BranchValue> getBranchValues() {
		return branchValues;
	}
	public void setBranchValues(List<BranchValue> branchValues) {
		this.branchValues = branchValues;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((saleDate == null) ? 0 : saleDate.hashCode());
		result = prime * result
				+ ((supplierCode == null) ? 0 : supplierCode.hashCode());
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
		SupplierBranchGroupByDate other = (SupplierBranchGroupByDate) obj;
		if (saleDate == null) {
			if (other.saleDate != null)
				return false;
		} else if (!saleDate.equals(other.saleDate))
			return false;
		if (supplierCode == null) {
			if (other.supplierCode != null)
				return false;
		} else if (!supplierCode.equals(other.supplierCode))
			return false;
		return true;
	}
	public Integer getSupplierNum() {
		return supplierNum;
	}
	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}
	
}
