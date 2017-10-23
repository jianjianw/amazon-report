package com.nhsoft.module.report.shared.queryBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PosOrderQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3105327136840843916L;
	private String systemBookCode;
	private List<Integer> branchNums;
	private Date dateFrom;
	private Date dateTo;
	private String orderNo;
	private String paymentType;
	private String clientFid;
	private String saleType;
	private String orderOperator;
	private String orderState;
	private String orderExternalNo;
	private String orderRefBillno;
	private String sortField;
	private String sortType;
	private boolean page = true;

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getClientFid() {
		return clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getOrderOperator() {
		return orderOperator;
	}

	public void setOrderOperator(String orderOperator) {
		this.orderOperator = orderOperator;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrderExternalNo() {
		return orderExternalNo;
	}

	public void setOrderExternalNo(String orderExternalNo) {
		this.orderExternalNo = orderExternalNo;
	}

	public String getOrderRefBillno() {
		return orderRefBillno;
	}

	public void setOrderRefBillno(String orderRefBillno) {
		this.orderRefBillno = orderRefBillno;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

}
