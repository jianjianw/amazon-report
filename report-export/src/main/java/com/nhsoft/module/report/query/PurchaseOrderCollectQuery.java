package com.nhsoft.module.report.query;

import com.nhsoft.module.report.dto.QueryBuilder;

import java.util.Date;
import java.util.List;

public class PurchaseOrderCollectQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5878438927912551321L;
	private Integer branchNum;
	private Date dtFrom;
	private Date dtTo;
	private List<Integer> itemNums;
	private List<String> itemCategoryCodes;
	private List<Integer> supplierNums;
	private String unitType;
	private Integer itemMatrix;
	private String operator;//操作员
	private Integer storehouseNum;
	private String itemBrand;
	private String itemDepartment;
	
	private boolean isExpired;//是否查询过期订单
	private String purchaseOrderFid; //订单号
	private List<Integer> branchNums;//收货分店
	private Boolean queryTopCategory; //是否按顶级类汇总

	@Override
	public boolean checkQueryBuild() {
		
		return false;
	}

	public String getItemDepartment() {
		return itemDepartment;
	}

	public void setItemDepartment(String itemDepartment) {
		this.itemDepartment = itemDepartment;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public Boolean getQueryTopCategory() {
		return queryTopCategory;
	}

	public void setQueryTopCategory(Boolean queryTopCategory) {
		this.queryTopCategory = queryTopCategory;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public List<String> getItemCategoryCodes() {
		return itemCategoryCodes;
	}

	public void setItemCategoryCodes(List<String> itemCategoryCodes) {
		this.itemCategoryCodes = itemCategoryCodes;
	}

	public List<Integer> getSupplierNums() {
		return supplierNums;
	}

	public void setSupplierNums(List<Integer> supplierNums) {
		this.supplierNums = supplierNums;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getPurchaseOrderFid() {
		return purchaseOrderFid;
	}

	public void setPurchaseOrderFid(String purchaseOrderFid) {
		this.purchaseOrderFid = purchaseOrderFid;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public Integer getItemMatrix() {
		return itemMatrix;
	}

	public void setItemMatrix(Integer itemMatrix) {
		this.itemMatrix = itemMatrix;
	}

}
