package com.nhsoft.module.report.query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CustomReportQuery implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5081656248352401300L;
	private String systemBookCode;
	private List<Integer> centerBranchNums;
	private List<Integer> branchNums;
	private Date dateFrom;
	private Date dateTo;
	private String dateType;
	private List<String> itemCategoryCodes;
	private String summaries;
	
	private boolean queryPos = false;// 是否查询前台销售
	private boolean queryCardPos = false;// 是否查询会员前台销售
	private boolean queryChain = false;// 是否查询配送金额 (调出 - 调入)
	private boolean queryGoals = false;// 是否查询目标 包括(销售、毛利、配销差额目标 )
	private boolean queryNewCard = false;// 是否查询新增会员
	private boolean queryRevokeCard = false;// 是否查询退卡数
	private boolean queryCardDeposit = false;// 是否查询卡存款
	private boolean queryCardConsume = false;// 是否查询卡消费
	private boolean queryAdjust = false; //是否查询损益统计
	
	public String getSystemBookCode() {
		return systemBookCode;
	}
	
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	
	public List<Integer> getCenterBranchNums() {
		return centerBranchNums;
	}
	
	public void setCenterBranchNums(List<Integer> centerBranchNums) {
		this.centerBranchNums = centerBranchNums;
	}
	
	public List<Integer> getBranchNums() {
		return branchNums;
	}
	
	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}
	
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	public Date getDateTo() {
		return dateTo;
	}
	
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public String getDateType() {
		return dateType;
	}
	
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
	public boolean isQueryPos() {
		return queryPos;
	}
	
	public void setQueryPos(boolean queryPos) {
		this.queryPos = queryPos;
	}
	
	public boolean isQueryCardPos() {
		return queryCardPos;
	}
	
	public void setQueryCardPos(boolean queryCardPos) {
		this.queryCardPos = queryCardPos;
	}
	
	public boolean isQueryChain() {
		return queryChain;
	}
	
	public void setQueryChain(boolean queryChain) {
		this.queryChain = queryChain;
	}
	
	public boolean isQueryGoals() {
		return queryGoals;
	}
	
	public void setQueryGoals(boolean queryGoals) {
		this.queryGoals = queryGoals;
	}
	
	public boolean isQueryNewCard() {
		return queryNewCard;
	}
	
	public void setQueryNewCard(boolean queryNewCard) {
		this.queryNewCard = queryNewCard;
	}
	
	public boolean isQueryRevokeCard() {
		return queryRevokeCard;
	}
	
	public void setQueryRevokeCard(boolean queryRevokeCard) {
		this.queryRevokeCard = queryRevokeCard;
	}
	
	public boolean isQueryCardDeposit() {
		return queryCardDeposit;
	}
	
	public void setQueryCardDeposit(boolean queryCardDeposit) {
		this.queryCardDeposit = queryCardDeposit;
	}
	
	public boolean isQueryCardConsume() {
		return queryCardConsume;
	}
	
	public void setQueryCardConsume(boolean queryCardConsume) {
		this.queryCardConsume = queryCardConsume;
	}

	public boolean isQueryAdjust() {
		return queryAdjust;
	}

	public void setQueryAdjust(boolean queryAdjust) {
		this.queryAdjust = queryAdjust;
	}

	public String getSummaries() {
		return summaries;
	}

	public void setSummaries(String summaries) {
		this.summaries = summaries;
	}

	public List<String> getItemCategoryCodes() {
		return itemCategoryCodes;
	}

	public void setItemCategoryCodes(List<String> itemCategoryCodes) {
		this.itemCategoryCodes = itemCategoryCodes;
	}
}
