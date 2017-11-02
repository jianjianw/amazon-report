package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class SupplierBranchQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4929397470384141156L;

	private Integer branchNum;
	private Date dateFrom;
	private Date dateTo;
	private List<Integer> supplierNums;
	private List<Integer> branchNums;
	private String checkString;
	private List<String> listStrings;
	private List<Integer> itemNums;
	
	public SupplierBranchQuery(){}
	
	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public SupplierBranchQuery(String systemBookCode){
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

	public String getCheckString() {
		return checkString;
	}

	public void setCheckString(String checkString) {
		this.checkString = checkString;
	}

	public List<String> getListStrings() {
		return listStrings;
	}

	public void setListStrings(List<String> listStrings) {
		this.listStrings = listStrings;
	}

}
