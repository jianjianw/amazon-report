package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class SmsSendSumDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 829000515332339179L;
	private Integer branchNum;
	private String branchName;
	private Integer totalCount;
	private Integer successCount;
	private Integer failCount;
	private Integer waitCount;
	
	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public Integer getWaitCount() {
		return waitCount;
	}

	public void setWaitCount(Integer waitCount) {
		this.waitCount = waitCount;
	}

}
