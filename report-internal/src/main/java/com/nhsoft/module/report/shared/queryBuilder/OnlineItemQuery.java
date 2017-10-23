package com.nhsoft.module.report.shared.queryBuilder;


import com.nhsoft.module.report.query.State;

import java.util.Date;
import java.util.List;

public class OnlineItemQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3700878263242772447L;
	private List<Integer> branchNums;
	private Date dateFrom;
	private Date dateTo;
	private List<Integer> weixinItemNums;
	private List<Integer> itemNums;
	private List<String> categoryCodes;
	private List<State> states;
	private String onlineOrderDeliver;
	private String paymentType;
	private Integer orderType; // ONLINE_ORDER_TYPE_NORMAL 是普通订单
								// ONLINE_ORDER_TYPE_TEAM是团购订单

	@Override
	public boolean checkQueryBuild() {

		return false;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public String getOnlineOrderDeliver() {
		return onlineOrderDeliver;
	}

	public void setOnlineOrderDeliver(String onlineOrderDeliver) {
		this.onlineOrderDeliver = onlineOrderDeliver;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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

	public List<Integer> getWeixinItemNums() {
		return weixinItemNums;
	}

	public void setWeixinItemNums(List<Integer> weixinItemNums) {
		this.weixinItemNums = weixinItemNums;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

}
