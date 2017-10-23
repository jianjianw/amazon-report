package com.nhsoft.module.report.model;


/**
 * Branch entity. @author MyEclipse Persistence Tools
 */

public class BranchTransferGoalsId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2413548471235586859L;
	private String systemBookCode;
	private Integer branchNum;
	private String branchTransferInterval;
	
	public BranchTransferGoalsId(){
		
	}
	
	public BranchTransferGoalsId(String systemBookCode, Integer branchNum, String branchTransferInterval){
		this.systemBookCode = systemBookCode;
		this.branchNum = branchNum;
		this.branchTransferInterval = branchTransferInterval;
	}

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

	public String getBranchTransferInterval() {
		return branchTransferInterval;
	}

	public void setBranchTransferInterval(String branchTransferInterval) {
		this.branchTransferInterval = branchTransferInterval;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchNum == null) ? 0 : branchNum.hashCode());
		result = prime
				* result
				+ ((branchTransferInterval == null) ? 0
						: branchTransferInterval.hashCode());
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
		BranchTransferGoalsId other = (BranchTransferGoalsId) obj;
		if (branchNum == null) {
			if (other.branchNum != null)
				return false;
		} else if (!branchNum.equals(other.branchNum))
			return false;
		if (branchTransferInterval == null) {
			if (other.branchTransferInterval != null)
				return false;
		} else if (!branchTransferInterval.equals(other.branchTransferInterval))
			return false;
		if (systemBookCode == null) {
			if (other.systemBookCode != null)
				return false;
		} else if (!systemBookCode.equals(other.systemBookCode))
			return false;
		return true;
	}

}