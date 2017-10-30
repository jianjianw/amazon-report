package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * StoreMatrixId entity. @author MyEclipse Persistence Tools
 */

@Embeddable
public class StoreMatrixId implements java.io.Serializable {

	private static final long serialVersionUID = 3360287048272420339L;
	private String systemBookCode;
	private Integer itemNum;
	private Integer branchNum;

	public StoreMatrixId(){
		
	}
	
	public StoreMatrixId(String systemBookCode, Integer branchNum, Integer itemNum){
		this.systemBookCode = systemBookCode;
		this.branchNum = branchNum;
		this.itemNum = itemNum;
	}
	
	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchNum == null) ? 0 : branchNum.hashCode());
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
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
		StoreMatrixId other = (StoreMatrixId) obj;
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
		if (systemBookCode == null) {
			if (other.systemBookCode != null)
				return false;
		} else if (!systemBookCode.equals(other.systemBookCode))
			return false;
		return true;
	}

}