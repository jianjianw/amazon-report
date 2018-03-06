package com.nhsoft.module.report.query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UnsalableQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6528468639188043162L;
	private Integer branchNum; // 当前店
	private List<Integer> branchNums;
	private List<Integer> supplierNums; // 供应商
	private List<String> categoryCodeList; // 商品类别
	private String unit; // 统计单位
	private Integer days; // 滞销定义天数
	private BigDecimal itemAmount; // 滞销定义商品数
	private Boolean isFilterInAndOut; // 是否过滤从未出入库的商品
	private Boolean isFilterStoreZero; // 是否过滤库存为0的商品
	private Boolean isFilterInFifteen; // 是否过滤15天内有入库的商品
	private Boolean isFilterStopSale; // 是否过滤停售商品
	private Boolean isFilterStopPurchase; // 是否过滤停购商品
	private boolean isTransfer = false; //是否配送中心
	private Date dateFrom;
	private Date dateTo;
	private Integer itemTransferDayMultiple = 1;//动销天数倍数
	private Date receiveDate;//收货日期
	private boolean filterOutGTInventory;// 过滤调出大于当前库存的

	public Integer getItemTransferDayMultiple() {
		return itemTransferDayMultiple;
	}

	public void setItemTransferDayMultiple(Integer itemTransferDayMultiple) {
		this.itemTransferDayMultiple = itemTransferDayMultiple;
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

	public List<String> getCategoryCodeList() {
		return categoryCodeList;
	}

	public void setCategoryCodeList(List<String> categoryCodeList) {
		this.categoryCodeList = categoryCodeList;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public BigDecimal getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}

	public List<Integer> getSupplierNums() {
		return supplierNums;
	}

	public void setSupplierNums(List<Integer> supplierNums) {
		this.supplierNums = supplierNums;
	}

	public Boolean isFilterInAndOut() {
		return isFilterInAndOut;
	}

	public void setFilterInAndOut(Boolean isFilterInAndOut) {
		this.isFilterInAndOut = isFilterInAndOut;
	}

	public Boolean isFilterStoreZero() {
		return isFilterStoreZero;
	}

	public void setFilterStoreZero(Boolean isFilterStoreZero) {
		this.isFilterStoreZero = isFilterStoreZero;
	}

	public Boolean isFilterInFifteen() {
		return isFilterInFifteen;
	}

	public void setFilterInFifteen(Boolean isFilterInFifteen) {
		this.isFilterInFifteen = isFilterInFifteen;
	}

	public Boolean isFilterStopSale() {
		return isFilterStopSale;
	}

	public void setFilterStopSale(Boolean isFilterStopSale) {
		this.isFilterStopSale = isFilterStopSale;
	}

	public boolean isTransfer() {
		return isTransfer;
	}

	public void setTransfer(boolean isTransfer) {
		this.isTransfer = isTransfer;
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

	public Boolean getIsFilterInAndOut() {
		return isFilterInAndOut;
	}

	public void setIsFilterInAndOut(Boolean isFilterInAndOut) {
		this.isFilterInAndOut = isFilterInAndOut;
	}

	public Boolean getIsFilterStoreZero() {
		return isFilterStoreZero;
	}

	public void setIsFilterStoreZero(Boolean isFilterStoreZero) {
		this.isFilterStoreZero = isFilterStoreZero;
	}

	public Boolean getIsFilterInFifteen() {
		return isFilterInFifteen;
	}

	public void setIsFilterInFifteen(Boolean isFilterInFifteen) {
		this.isFilterInFifteen = isFilterInFifteen;
	}

	public Boolean getIsFilterStopSale() {
		return isFilterStopSale;
	}

	public void setIsFilterStopSale(Boolean isFilterStopSale) {
		this.isFilterStopSale = isFilterStopSale;
	}

	public Boolean getIsFilterStopPurchase() {
		return isFilterStopPurchase;
	}

	public void setIsFilterStopPurchase(Boolean isFilterStopPurchase) {
		this.isFilterStopPurchase = isFilterStopPurchase;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public boolean isFilterOutGTInventory() {
		return filterOutGTInventory;
	}

	public void setFilterOutGTInventory(boolean filterOutGTInventory) {
		this.filterOutGTInventory = filterOutGTInventory;
	}
}