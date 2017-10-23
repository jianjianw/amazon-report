package com.nhsoft.module.report.api.dto;

import java.math.BigDecimal;

public class DashBoardSalesTrendDTO {

	private Integer branchNum2;            // 门店编号
	private String branchName2;            // 门店名称
	private BigDecimal revenue2;           // 营业额
	public Integer getBranchNum2() {
		return branchNum2;
	}
	public void setBranchNum2(Integer branchNum2) {
		this.branchNum2 = branchNum2;
	}
	public String getBranchName2() {
		return branchName2;
	}
	public void setBranchName2(String branchName2) {
		this.branchName2 = branchName2;
	}
	public BigDecimal getRevenue2() {
		return revenue2;
	}
	public void setRevenue2(BigDecimal revenue2) {
		this.revenue2 = revenue2;
	}
	
	
}
