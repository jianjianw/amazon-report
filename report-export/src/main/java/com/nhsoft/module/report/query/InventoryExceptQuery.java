package com.nhsoft.module.report.query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InventoryExceptQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6988538743090661446L;

	private Date dateFrom;
	private Date dateTo;
	private String systemBookCode;
	private Integer branchNum;
	private List<String> itemCategorys; // 类别代码
	private Integer storehouseNum;
	private String unit;
	private BigDecimal inventoryHigh;  //高库存数量	
	private BigDecimal inventoryMoney;  //库存金额
 	private List<Integer> supplierNums;
 	private Integer receiveDay;
 	private List<Integer> branchNums;
 	private String posItemLogSummary;  
 	private Integer saleDay;
 	private Integer newItemDay;
 	private boolean filterInventory = false;//过滤有库存商品
 	private boolean isTransferCenter = false; //是否配送中心 配送中心不计算 销售量
 	private BigDecimal multiple = BigDecimal.ONE;
 	private String compare = "<=";

	public BigDecimal getMultiple() {
		return multiple;
	}

	public void setMultiple(BigDecimal multiple) {
		this.multiple = multiple;
	}

	public String getCompare() {
		return compare;
	}

	public void setCompare(String compare) {
		this.compare = compare;
	}

	public boolean isTransferCenter() {
		return isTransferCenter;
	}

	public void setTransferCenter(boolean isTransferCenter) {
		this.isTransferCenter = isTransferCenter;
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

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getInventoryHigh() {
		return inventoryHigh;
	}

	public void setInventoryHigh(BigDecimal inventoryHigh) {
		this.inventoryHigh = inventoryHigh;
	}

	public BigDecimal getInventoryMoney() {
		return inventoryMoney;
	}

	public void setInventoryMoney(BigDecimal inventoryMoney) {
		this.inventoryMoney = inventoryMoney;
	}

	public List<Integer> getSupplierNums() {
		return supplierNums;
	}

	public void setSupplierNums(List<Integer> supplierNums) {
		this.supplierNums = supplierNums;
	}

	public Integer getReceiveDay() {
		return receiveDay;
	}

	public void setReceiveDay(Integer receiveDay) {
		this.receiveDay = receiveDay;
	}

	public String getPosItemLogSummary() {
		return posItemLogSummary;
	}

	public void setPosItemLogSummary(String posItemLogSummary) {
		this.posItemLogSummary = posItemLogSummary;
	}

	public boolean isFilterInventory() {
		return filterInventory;
	}

	public void setFilterInventory(boolean filterInventory) {
		this.filterInventory = filterInventory;
	}

	public Integer getSaleDay() {
		return saleDay;
	}

	public void setSaleDay(Integer saleDay) {
		this.saleDay = saleDay;
	}

	public Integer getNewItemDay() {
		return newItemDay;
	}

	public void setNewItemDay(Integer newItemDay) {
		this.newItemDay = newItemDay;
	}

}
