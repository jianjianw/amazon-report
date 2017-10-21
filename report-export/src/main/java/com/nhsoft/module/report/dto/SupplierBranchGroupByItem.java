package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SupplierBranchGroupByItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8049120107147112348L;

	private Integer supplierNum;
	private String supplierCode;
	private String supplierName;
	private String itemCode;
	private String itemName;
	private BigDecimal totalValue;
	private List<BranchValue> branchValues;
	
	private BigDecimal itemGrossRate;
	
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
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public Integer getSupplierNum() {
		return supplierNum;
	}
	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}
	public BigDecimal getItemGrossRate() {
		return itemGrossRate;
	}
	public void setItemGrossRate(BigDecimal itemGrossRate) {
		this.itemGrossRate = itemGrossRate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
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
		SupplierBranchGroupByItem other = (SupplierBranchGroupByItem) obj;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (supplierCode == null) {
			if (other.supplierCode != null)
				return false;
		} else if (!supplierCode.equals(other.supplierCode))
			return false;
		return true;
	}
	
}
