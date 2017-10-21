package com.nhsoft.report.api.dto;

import java.math.BigDecimal;

public class DashBoardTopItemDTO {

	private Integer branchNum3;            // 门店编号
	private String branchName3;            // 门店名称
	private BigDecimal revenue3;           // 营业额
	public Integer getBranchNum3() {
		return branchNum3;
	}
	public void setBranchNum3(Integer branchNum3) {
		this.branchNum3 = branchNum3;
	}
	public String getBranchName3() {
		return branchName3;
	}
	public void setBranchName3(String branchName3) {
		this.branchName3 = branchName3;
	}
	public BigDecimal getRevenue3() {
		return revenue3;
	}
	public void setRevenue3(BigDecimal revenue3) {
		this.revenue3 = revenue3;
	}
	
}
