package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class SupplierSaleQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4929397470384141156L;
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
	public String getKey() {
		StringBuffer sb = new StringBuffer();
		sb.append(systemBookCode);
		sb.append(dateFrom.toString());
		sb.append(dateTo.toString());

		if(supplierNums != null){
			sb.append("supplierNums:");
			for(int i = 0;i < supplierNums.size();i++){
				sb.append(supplierNums.get(i));
			}
		}
		if(itemNums != null){
			sb.append("itemNums:");
			for(int i = 0;i < itemNums.size();i++){
				sb.append(itemNums.get(i));
			}
		}
		if(categoryCodes != null){
			sb.append("categoryCodes:");
			for(int i = 0;i < categoryCodes.size();i++){
				sb.append(categoryCodes.get(i));
			}
		}
		if(branchNums != null){
			sb.append("branchNums:");
			for(int i = 0;i < branchNums.size();i++){
				sb.append(branchNums.get(i));
			}
		}
		if(checkList != null){
			sb.append("checkList:");
			for(int i = 0;i < checkList.size();i++){
				sb.append(checkList.get(i));
			}
		}
		return sb.toString();
	}


}
