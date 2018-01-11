package com.nhsoft.module.report.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.List;

public class InventoryQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9016463180537738272L;
	private List<Integer> branchNums;
	private List<Integer> itemNums;// 商品编号
	private List<String> itemCategorys; // 类别代码
	private Integer storehouseNum;
	private Boolean notShowZero;
	private Boolean itemEliminative;
	private boolean isPaging = true;
	private String itemBrand;
	private String unitType;//单位类型
	private String var;
	private String itemMthod;
	private Integer supplierNum;
	private String supplierType;
	private Integer branchNum;
	private boolean showSaleCeaseItem = false;//仅显示停售商品 
	private String queryId;

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

	public List<String> getItemCategorys() {
		return itemCategorys;
	}

	public void setItemCategorys(List<String> itemCategorys) {
		this.itemCategorys = itemCategorys;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public Boolean getNotShowZero() {
		return notShowZero;
	}

	public void setNotShowZero(Boolean notShowZero) {
		this.notShowZero = notShowZero;
	}

	public Boolean getItemEliminative() {
		return itemEliminative;
	}

	public void setItemEliminative(Boolean itemEliminative) {
		this.itemEliminative = itemEliminative;
	}

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

    public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getItemMthod() {
		return itemMthod;
	}

	public void setItemMthod(String itemMthod) {
		this.itemMthod = itemMthod;
	}
	
	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public boolean isShowSaleCeaseItem() {
		return showSaleCeaseItem;
	}

	public void setShowSaleCeaseItem(boolean showSaleCeaseItem) {
		this.showSaleCeaseItem = showSaleCeaseItem;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

}
