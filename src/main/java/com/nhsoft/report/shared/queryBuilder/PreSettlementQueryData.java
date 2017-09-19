package com.nhsoft.report.shared.queryBuilder;

import java.util.Date;
import java.util.List;

public class PreSettlementQueryData extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4690424503139632711L;
	private Integer branchNum;
	private String dateType;
	private Date dateFrom;
	private Date dateTo;
	private String orderFid;
	private Integer accountBankNum;
	private String paymentType;
	private Integer stateCode;
	private Integer payerNum;
	private List<String> clentFids;
	private Boolean preSettleSelf; //客户自付， 门店自付
	private Integer settleBranchNum; //结算门店
	private List<Integer> branchNums;
	private String creator;
	private String auditor;
	private boolean paging = true; //分页标记
	
	private String sortField;
	private String sortType;

	public PreSettlementQueryData(){
		
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public Integer getSettleBranchNum() {
		return settleBranchNum;
	}

	public void setSettleBranchNum(Integer settleBranchNum) {
		this.settleBranchNum = settleBranchNum;
	}

	public PreSettlementQueryData(String systemBookCode){
		this.systemBookCode = systemBookCode;
	}
	
	@Override
	public boolean checkQueryBuild() {
		return true;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
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

	public String getOrderFid() {
		return orderFid;
	}

	public void setOrderFid(String orderFid) {
		this.orderFid = orderFid;
	}

	public Integer getAccountBankNum() {
		return accountBankNum;
	}

	public void setAccountBankNum(Integer accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getPayerNum() {
		return payerNum;
	}

	public void setPayerNum(Integer payerNum) {
		this.payerNum = payerNum;
	}

	public List<String> getClentFids() {
		return clentFids;
	}

	public void setClentFids(List<String> clentFids) {
		this.clentFids = clentFids;
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

	public Boolean getPreSettleSelf() {
		return preSettleSelf;
	}

	public void setPreSettleSelf(Boolean preSettleSelf) {
		this.preSettleSelf = preSettleSelf;
	}
}
