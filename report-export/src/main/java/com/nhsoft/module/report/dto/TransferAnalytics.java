package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 月销售报表
 * @author Administrator
 *
 */
public class TransferAnalytics  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3171813405161852019L;
	private String date;
	private Integer branchNum;	
	private BigDecimal transferGoal;
	private BigDecimal transferFinish;	

	public TransferAnalytics(){
		
	}

	public Integer getBranchNum() {
		return branchNum;
	}
	
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public BigDecimal getTransferGoal() {
		return transferGoal;
	}

	public void setTransferGoal(BigDecimal transferGoal) {
		this.transferGoal = transferGoal;
	}

	public BigDecimal getTransferFinish() {
		return transferFinish;
	}

	public void setTransferFinish(BigDecimal transferFinish) {
		this.transferFinish = transferFinish;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchNum == null) ? 0 : branchNum.hashCode());
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
		TransferAnalytics other = (TransferAnalytics) obj;
		if (branchNum == null) {
			if (other.branchNum != null)
				return false;
		} else if (!branchNum.equals(other.branchNum))
			return false;
		return true;
	}

}
