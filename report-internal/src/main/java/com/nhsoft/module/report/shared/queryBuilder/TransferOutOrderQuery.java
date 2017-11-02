package com.nhsoft.module.report.shared.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;

public class TransferOutOrderQuery extends QueryBuilder {

	private static final long serialVersionUID = -1490590318253607897L;
	
	private Integer centerBranchNum;
	private Integer branchNum;
	private Date outDateFrom;
	private Date outDateTo;
	private Date paymentDateFrom;
	private Date paymentDateTo;
	private String outOrderFid;
	private String paymentState;
	private String sortField;
	private String sortName;
	private Boolean isSort = true;
	private boolean isStarted; //是否发货
	private boolean isExceed; //订单是否过期

	public TransferOutOrderQuery(){

	}

	public boolean isExceed() {
		return isExceed;
	}
	public void setExceed(boolean isExceed) {
		this.isExceed = isExceed;
	}
	public boolean isStarted() {
		return isStarted;
	}

	public void setIsStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}
	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Boolean getIsSort() {
		return isSort;
	}

	public void setIsSort(Boolean isSort) {
		this.isSort = isSort;
	}

	public TransferOutOrderQuery(String systemBookCode){
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Date getOutDateFrom() {
		return outDateFrom;
	}

	public void setOutDateFrom(Date outDateFrom) {
		this.outDateFrom = outDateFrom;
	}

	public Date getOutDateTo() {
		return outDateTo;
	}

	public void setOutDateTo(Date outDateTo) {
		this.outDateTo = outDateTo;
	}

	public Date getPaymentDateFrom() {
		return paymentDateFrom;
	}

	public void setPaymentDateFrom(Date paymentDateFrom) {
		this.paymentDateFrom = paymentDateFrom;
	}

	public Date getPaymentDateTo() {
		return paymentDateTo;
	}

	public void setPaymentDateTo(Date paymentDateTo) {
		this.paymentDateTo = paymentDateTo;
	}

	public String getOutOrderFid() {
		return outOrderFid;
	}

	public void setOutOrderFid(String outOrderFid) {
		this.outOrderFid = outOrderFid;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public Integer getCenterBranchNum() {
		return centerBranchNum;
	}

	public void setCenterBranchNum(Integer centerBranchNum) {
		this.centerBranchNum = centerBranchNum;
	}


}
