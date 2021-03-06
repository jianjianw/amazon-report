package com.nhsoft.module.report.api.dto;

import java.math.BigDecimal;

public class TrendDailyDTO {


	public TrendDailyDTO() {
		this.revenue = BigDecimal.ZERO;
		this.memberRevenue = BigDecimal.ZERO;
		this.distributionMoney = BigDecimal.ZERO;
		this.billNums = 0;
		this.memberBillNums = 0;
	}

	private String day;                           // 日期
	private BigDecimal revenue;                   // 营业额
	private BigDecimal memberRevenue;             // 会员营业额
	private BigDecimal distributionMoney;         // 配送额
	private Integer billNums;                     // 客单数
	private Integer memberBillNums;               // 会员客单数
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getMemberRevenue() {
		return memberRevenue;
	}
	public void setMemberRevenue(BigDecimal memberRevenue) {
		this.memberRevenue = memberRevenue;
	}
	public BigDecimal getDistributionMoney() {
		return distributionMoney;
	}
	public void setDistributionMoney(BigDecimal distributionMoney) {
		this.distributionMoney = distributionMoney;
	}
	public Integer getBillNums() {
		return billNums;
	}
	public void setBillNums(Integer billNums) {
		this.billNums = billNums;
	}
	public Integer getMemberBillNums() {
		return memberBillNums;
	}
	public void setMemberBillNums(Integer memberBillNums) {
		this.memberBillNums = memberBillNums;
	}
	
}
