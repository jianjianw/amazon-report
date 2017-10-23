package com.nhsoft.module.report.shared.queryBuilder;

import java.util.Date;
import java.util.List;

public class StoreQueryCondition extends QueryBuilder{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7226473118040279430L;
	
	private Integer branchNum;
	private Date dateStart;
	private Date dateEnd;
	private List<Integer> itemNums;
	private List<String> itemCategoryCodes;
	private String unitType; //单位类型
	private Integer storehouseNum;
	private String posItemLogSummary;
	private String adjustReason;
	private Boolean centerStorehouse;
	private String refBillNo;  //关联单据号
	private List<Integer> branchNums;
	private String posItemLogLotNumber;
	private Boolean filterElement; //非配送中心过滤成分商品
	private String itemDepartment;
	private Date exactDateEnd;
	
	private boolean isPaging = true;
	private String sortField;
	private String sortType;
	
	public StoreQueryCondition(){}
	
	public Date getExactDateEnd() {
		return exactDateEnd;
	}
	
	public void setExactDateEnd(Date exactDateEnd) {
		this.exactDateEnd = exactDateEnd;
	}
	
	public String getItemDepartment() {
		return itemDepartment;
	}

	public void setItemDepartment(String itemDepartment) {
		this.itemDepartment = itemDepartment;
	}

	public Boolean getFilterElement() {
		return filterElement;
	}

	public void setFilterElement(Boolean filterElement) {
		this.filterElement = filterElement;
	}

	public String getPosItemLogLotNumber() {
		return posItemLogLotNumber;
	}

	public void setPosItemLogLotNumber(String posItemLogLotNumber) {
		this.posItemLogLotNumber = posItemLogLotNumber;
	}

	public StoreQueryCondition(String systemBookCode){
		this.systemBookCode = systemBookCode;
	}
	
	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
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

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public List<String> getItemCategoryCodes() {
		return itemCategoryCodes;
	}

	public void setItemCategoryCodes(List<String> itemCategoryCodes) {
		this.itemCategoryCodes = itemCategoryCodes;
	}

	@Override
	public boolean checkQueryBuild() {
		return true;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getPosItemLogSummary() {
		return posItemLogSummary;
	}

	public void setPosItemLogSummary(String posItemLogSummary) {
		this.posItemLogSummary = posItemLogSummary;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public Boolean getCenterStorehouse() {
		return centerStorehouse;
	}

	public void setCenterStorehouse(Boolean centerStorehouse) {
		this.centerStorehouse = centerStorehouse;
	}

	public String getRefBillNo() {
		return refBillNo;
	}

	public void setRefBillNo(String refBillNo) {
		this.refBillNo = refBillNo;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

}
