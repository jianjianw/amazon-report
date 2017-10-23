package com.nhsoft.module.report.shared.queryBuilder;

import java.util.Date;
import java.util.List;

public class CheckQueryCondition extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3178476679265048660L;
	private Integer branchNum;
	private String dateType;
	private Date dateFrom;
	private Date dateTo;
	private Integer storehouseNum;
	private List<Integer> storeItemNums;
	private List<String> itemCategoryIds;
	private String queryType;

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

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public List<Integer> getStoreItemNums() {
		return storeItemNums;
	}

	public void setStoreItemNums(List<Integer> storeItemNums) {
		this.storeItemNums = storeItemNums;
	}

	public List<String> getItemCategoryIds() {
		return itemCategoryIds;
	}

	public void setItemCategoryIds(List<String> itemCategoryIds) {
		this.itemCategoryIds = itemCategoryIds;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	@Override
	public boolean checkQueryBuild() {
		
		return false;
	}

}
