package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * BranchResourceId entity. @author MyEclipse Persistence Tools
 */

@Embeddable
public class BranchResourceId implements java.io.Serializable {

	private static final long serialVersionUID = -2413785279761027072L;
	private String systemBookCode;
	private Integer branchNum;
	private String branchResourceName;

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchResourceName() {
		return branchResourceName;
	}

	public void setBranchResourceName(String branchResourceName) {
		this.branchResourceName = branchResourceName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchNum == null) ? 0 : branchNum.hashCode());
		result = prime
				* result
				+ ((branchResourceName == null) ? 0 : branchResourceName
						.hashCode());
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
		BranchResourceId other = (BranchResourceId) obj;
		if (branchNum == null) {
			if (other.branchNum != null)
				return false;
		} else if (!branchNum.equals(other.branchNum))
			return false;
		if (branchResourceName == null) {
			if (other.branchResourceName != null)
				return false;
		} else if (!branchResourceName.equals(other.branchResourceName))
			return false;
		if (systemBookCode == null) {
			if (other.systemBookCode != null)
				return false;
		} else if (!systemBookCode.equals(other.systemBookCode))
			return false;
		return true;
	}

	
	
}