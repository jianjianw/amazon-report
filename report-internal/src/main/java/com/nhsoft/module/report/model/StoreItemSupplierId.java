package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * StoreItemSupplierId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class StoreItemSupplierId implements java.io.Serializable {

	private static final long serialVersionUID = -2154770438624450952L;
	private String systemBookCode;
	private Integer supplierNum;
	private Integer itemNum;
	private Integer branchNum;

	public StoreItemSupplierId(){
		
	}
	
	public StoreItemSupplierId(String systemBookCode,  Integer branchNum, Integer supplierNum, Integer itemNum){
		this.systemBookCode = systemBookCode;
		this.supplierNum = supplierNum;
		this.itemNum = itemNum;
		this.branchNum = branchNum;
	}
	
	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return this.branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchNum == null) ? 0 : branchNum.hashCode());
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
		result = prime * result
				+ ((supplierNum == null) ? 0 : supplierNum.hashCode());
		result = prime * result
				+ ((systemBookCode == null) ? 0 : systemBookCode.hashCode());
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
		StoreItemSupplierId other = (StoreItemSupplierId) obj;
		if (branchNum == null) {
			if (other.branchNum != null)
				return false;
		} else if (!branchNum.equals(other.branchNum))
			return false;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		if (supplierNum == null) {
			if (other.supplierNum != null)
				return false;
		} else if (!supplierNum.equals(other.supplierNum))
			return false;
		if (systemBookCode == null) {
			if (other.systemBookCode != null)
				return false;
		} else if (!systemBookCode.equals(other.systemBookCode))
			return false;
		return true;
	}

}