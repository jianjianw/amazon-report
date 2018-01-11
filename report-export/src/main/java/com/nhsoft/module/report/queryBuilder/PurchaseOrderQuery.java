package com.nhsoft.module.report.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class PurchaseOrderQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8439708771586165958L;
	
	private List<Integer> branchNums;
	private Integer supplierNum;
	private String queryFid;
	private List<Integer> selectItemNums;
	private Date dateFrom;
	private Date dateTo;
	private Boolean isNew;
	private Integer status;
	private Boolean queryRequestOrders;
	
	private String sortField;
	private String sortType;
	
	public Boolean getQueryRequestOrders() {
		return queryRequestOrders;
	}
	
	public void setQueryRequestOrders(Boolean queryRequestOrders) {
		this.queryRequestOrders = queryRequestOrders;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public String getQueryFid() {
		return queryFid;
	}

	public void setQueryFid(String queryFid) {
		this.queryFid = queryFid;
	}

	public List<Integer> getSelectItemNums() {
		return selectItemNums;
	}

	public void setSelectItemNums(List<Integer> selectItemNums) {
		this.selectItemNums = selectItemNums;
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

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
