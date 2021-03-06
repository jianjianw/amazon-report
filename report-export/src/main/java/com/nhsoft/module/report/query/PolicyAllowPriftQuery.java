package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class PolicyAllowPriftQuery extends QueryBuilder {

	/**
	 * 让利分析
	 */
	private static final long serialVersionUID = 8753198611805291922L;
	private Integer branchNum;
	private Date dtFrom;
	private Date dtTo;
	private List<Integer> branchNums;
	private List<Integer> itemNums;
	private String profitType; // 返利类型
	private String policyOrderFid;
	private List<String> categoryCodes;
	private List<String> orderSellers; //销售员
	private boolean isPromotion = true;

	private boolean page;
	private String sortType;
	private String sortField;
	private int offset;
	private int limit;

	private Integer max = 50000;

	public Integer getMax() {
		return max == null?50000:max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}



	public List<String> getOrderSellers() {
		return orderSellers;
	}


	public void setOrderSellers(List<String> orderSellers) {
		this.orderSellers = orderSellers;
	}


	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public String getPolicyOrderFid() {
		return policyOrderFid;
	}

	public void setPolicyOrderFid(String policyOrderFid) {
		this.policyOrderFid = policyOrderFid;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Date getDtFrom() {
		return dtFrom;
	}

	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}

	public Date getDtTo() {
		return dtTo;
	}

	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public String getProfitType() {
		return profitType;
	}

	public void setProfitType(String profitType) {
		this.profitType = profitType;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public void setPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public void clear() {
		systemBookCode = null;
		branchNum = null;
		dtFrom = null;
		dtTo = null;
		branchNums = null;
		itemNums = null;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
