package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class OrderQueryDTO extends QueryBuilder{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1550982251448141706L;
	private String systemBookCode;
	private List<Integer> branchNums;
	private Date dateFrom;
	private Date dateTo;
	private List<Integer> itemNums;
	private List<Integer> detailStateCodes;// posOrderDetail状态
	private List<String> categoryCodes;
	private boolean queryKit = false; // 组合商品按明细查询
	private boolean queryMember = false; // 查询会员消费

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
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

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public List<Integer> getDetailStateCodes() {
		return detailStateCodes;
	}

	public void setDetailStateCodes(List<Integer> detailStateCodes) {
		this.detailStateCodes = detailStateCodes;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public boolean isQueryKit() {
		return queryKit;
	}

	public void setQueryKit(boolean queryKit) {
		this.queryKit = queryKit;
	}

	public boolean isQueryMember() {
		return queryMember;
	}

	public void setQueryMember(boolean queryMember) {
		this.queryMember = queryMember;
	}

}
