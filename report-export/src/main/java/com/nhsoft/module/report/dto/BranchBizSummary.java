package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yangqin on 2017/9/26.
 */
public class BranchBizSummary implements Serializable {
	
	
	private static final long serialVersionUID = 8383410875646067620L;
	private int branchNum;
	private String biz;//营业日或月
	
	private BigDecimal money;
	private int count;
	
	private BigDecimal profit;
	private BigDecimal cost;
	
	public BigDecimal getProfit() {
		return profit;
	}
	
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	
	public BigDecimal getCost() {
		return cost;
	}
	
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getBranchNum() {
		return branchNum;
	}
	
	public void setBranchNum(int branchNum) {
		this.branchNum = branchNum;
	}
	
	public String getBiz() {
		return biz;
	}
	
	public void setBiz(String biz) {
		this.biz = biz;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}
