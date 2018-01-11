package com.nhsoft.module.report.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class TransferProfitQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -320433567359039994L;
	private Integer branchNum;
	private Date dtFrom;
	private Date dtTo;
	private List<Integer> itemNums; // 商品项目
	private List<String> itemTypeNums; // 商品类别
	private List<Integer> responseBranchNums;// 调往分店
	private List<Integer> distributionBranchNums;// 配送分店
	private String unitType;// 商品单位
	private boolean isEnableAssist = false;
	private boolean filterPolicyItems = false; // 不统计特价商品
	private Integer storehouseNum;
	private Integer selectSupplierNum;// 按商品汇总时用到
	private List<String> categoryCodes;
	private List<String> itemDepartments; // 商品部门
	private String itemBrand;

	
	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public List<String> getItemDepartments() {
		return itemDepartments;
	}

	public void setItemDepartments(List<String> itemDepartments) {
		this.itemDepartments = itemDepartments;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Date getDtFrom() {
		return dtFrom;
	}

	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}

	public Date getDtTo() {
		return dtTo;
	}

	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public List<String> getItemTypeNums() {
		return itemTypeNums;
	}

	public void setItemTypeNums(List<String> itemTypeNums) {
		this.itemTypeNums = itemTypeNums;
	}

	public List<Integer> getResponseBranchNums() {
		return responseBranchNums;
	}

	public void setResponseBranchNums(List<Integer> responseBranchNums) {
		this.responseBranchNums = responseBranchNums;
	}

	public List<Integer> getDistributionBranchNums() {
		return distributionBranchNums;
	}

	public void setDistributionBranchNums(List<Integer> distributionBranchNums) {
		this.distributionBranchNums = distributionBranchNums;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public boolean isEnableAssist() {
		return isEnableAssist;
	}

	public void setEnableAssist(boolean isEnableAssist) {
		this.isEnableAssist = isEnableAssist;
	}

	public boolean isFilterPolicyItems() {
		return filterPolicyItems;
	}

	public void setFilterPolicyItems(boolean filterPolicyItems) {
		this.filterPolicyItems = filterPolicyItems;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public Integer getSelectSupplierNum() {
		return selectSupplierNum;
	}

	public void setSelectSupplierNum(Integer selectSupplierNum) {
		this.selectSupplierNum = selectSupplierNum;
	}


}
