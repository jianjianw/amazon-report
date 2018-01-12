package com.nhsoft.module.report.queryBuilder;

import java.io.Serializable;
import java.util.List;

public class TaskRequestQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2965508656724382087L;
	private String systemBookCode;
	private Integer branchNum;
	private List<Integer> itemNums;
	private String taskRequestBizday;
	private Integer taskRequestType;
	private List<String> categoryCodes;
	private Boolean needTaskRequestBranchCount;
	private Integer itemType;
	private Boolean filterMaterial;//过滤原料
	private String itemDepartments;
	private Boolean queryDetails;
	
	public Boolean getQueryDetails() {
		return queryDetails;
	}
	
	public void setQueryDetails(Boolean queryDetails) {
		this.queryDetails = queryDetails;
	}
	
	public String getItemDepartments() {
		return itemDepartments;
	}
	
	public void setItemDepartments(String itemDepartments) {
		this.itemDepartments = itemDepartments;
	}
	
	public Boolean getFilterMaterial() {
		return filterMaterial;
	}
	
	public void setFilterMaterial(Boolean filterMaterial) {
		this.filterMaterial = filterMaterial;
	}
	
	public Integer getItemType() {
		return itemType;
	}
	
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	
	public String getSystemBookCode() {
		return systemBookCode;
	}
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	public Integer getBranchNum() {
		return branchNum;
	}
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	public List<Integer> getItemNums() {
		return itemNums;
	}
	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}
	public String getTaskRequestBizday() {
		return taskRequestBizday;
	}
	public void setTaskRequestBizday(String taskRequestBizday) {
		this.taskRequestBizday = taskRequestBizday;
	}
	public Integer getTaskRequestType() {
		return taskRequestType;
	}
	public void setTaskRequestType(Integer taskRequestType) {
		this.taskRequestType = taskRequestType;
	}
	public Boolean getNeedTaskRequestBranchCount() {
		return needTaskRequestBranchCount;
	}
	public void setNeedTaskRequestBranchCount(Boolean needTaskRequestBranchCount) {
		this.needTaskRequestBranchCount = needTaskRequestBranchCount;
	}
	public List<String> getCategoryCodes() {
		return categoryCodes;
	}
	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}
	
}
