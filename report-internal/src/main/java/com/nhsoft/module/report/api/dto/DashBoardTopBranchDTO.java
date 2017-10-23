package com.nhsoft.module.report.api.dto;

import java.math.BigDecimal;

public class DashBoardTopBranchDTO {

	private Integer branchNum;            // 门店编号
	private String branchName;            // 门店名称
	private BigDecimal revenue;           // 营业额
	
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
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	
}
