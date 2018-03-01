package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yangqin on 2017/9/25.
 */
public class BranchMonthReport implements Serializable {
	
	
	private static final long serialVersionUID = 5978813697825433532L;
	private int branchNum;
	private String month;
	private BigDecimal bizMoney;//营业额
	private int orderCount;//客单量
	private BigDecimal profit;//毛利
	private int bizdayCount;//有效营业天数
	private BigDecimal profitRate;//毛利率
	private boolean member;//是否会员
	
	public int getBranchNum() {
		return branchNum;
	}
	
	public void setBranchNum(int branchNum) {
		this.branchNum = branchNum;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public BigDecimal getBizMoney() {
		return bizMoney;
	}
	
	public void setBizMoney(BigDecimal bizMoney) {
		this.bizMoney = bizMoney;
	}
	
	public int getOrderCount() {
		return orderCount;
	}
	
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	
	public BigDecimal getProfit() {
		return profit;
	}
	
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	
	public int getBizdayCount() {
		return bizdayCount;
	}
	
	public void setBizdayCount(int bizdayCount) {
		this.bizdayCount = bizdayCount;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}
}
