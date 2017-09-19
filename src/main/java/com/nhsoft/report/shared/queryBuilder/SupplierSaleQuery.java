package com.nhsoft.report.shared.queryBuilder;

import java.util.Date;
import java.util.List;

public class SupplierSaleQuery extends QueryBuilder{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4929397470384141156L;

	private String systemBookCode;
	private Integer branchNum;
	private Date dateFrom;
	private Date dateTo;
	private List<Integer> supplierNums;
	private List<Integer> itemNums;
	private List<String> categoryCodes;
	private List<Integer> branchNums;
	private List<String> checkList;
	
	public SupplierSaleQuery(){}
	
	public SupplierSaleQuery(String systemBookCode){
		this.systemBookCode = systemBookCode;
	}
	
	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
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

	public List<Integer> getSupplierNums() {
		return supplierNums;
	}

	public void setSupplierNums(List<Integer> supplierNums) {
		this.supplierNums = supplierNums;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public List<String> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<String> checkList) {
		this.checkList = checkList;
	}

	@Override
	public boolean checkQueryBuild() {
		return true;
	}

}
