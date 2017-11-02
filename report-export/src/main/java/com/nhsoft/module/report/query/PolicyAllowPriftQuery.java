package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class PolicyAllowPriftQuery extends QueryBuilder {

	/**
	 * 让利分析
	 */
	private static final long serialVersionUID = 8753198611805291922L;
	private String systemBookCode;
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

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
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
}
