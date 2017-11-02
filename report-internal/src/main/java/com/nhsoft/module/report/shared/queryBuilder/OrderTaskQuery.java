package com.nhsoft.module.report.shared.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class OrderTaskQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6869885573387520608L;
	private Integer branchNum;
	private Date dateFrom;
	private Date dateTo;
	private List<Integer> itemNums;
	private String orderTaskType;
	private Integer orderTaskUserNum;
	private Integer stateCode;
	private Boolean orderTaskSelfTag;
	private String orderTaskBizday;
	private boolean withDetails = false;
	private Boolean queryPurchased = false; //查询已采购过的任务
	private Boolean queryReceived; //查询是否以收货任务
	private String departmentName;
	private String categoryCode; //商品类别
	private Boolean isAssign; //是否已指派
	private Integer eqStateCode;
	private List<Integer> stateCodes;
	private Boolean directTransfer;
	private Boolean useSql = false;
	private Integer itemType;
	private String keyword;
	private Boolean filterZeroUseQty; //是否过滤0实采数量
	private Boolean queryZeroPurchaseQty;//是否查询计划采购数量为0的记录 false时查询计划采购数量>0的记录 默认为null

	
	private Boolean unSend;//未发车
	
	public Boolean getQueryZeroPurchaseQty() {
		return queryZeroPurchaseQty;
	}
	
	public void setQueryZeroPurchaseQty(Boolean queryZeroPurchaseQty) {
		this.queryZeroPurchaseQty = queryZeroPurchaseQty;
	}
	
	public Boolean getFilterZeroUseQty() {
		return filterZeroUseQty;
	}
	
	public void setFilterZeroUseQty(Boolean filterZeroUseQty) {
		this.filterZeroUseQty = filterZeroUseQty;
	}
	
	public Boolean getUnSend() {
		return unSend;
	}
	
	public void setUnSend(Boolean unSend) {
		this.unSend = unSend;
	}
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Boolean getUseSql() {
		return useSql;
	}

	public void setUseSql(Boolean useSql) {
		this.useSql = useSql;
	}

	public Boolean getDirectTransfer() {
		return directTransfer;
	}

	public void setDirectTransfer(Boolean directTransfer) {
		this.directTransfer = directTransfer;
	}

	public List<Integer> getStateCodes() {
		return stateCodes;
	}

	public void setStateCodes(List<Integer> stateCodes) {
		this.stateCodes = stateCodes;
	}

	public Integer getEqStateCode() {
		return eqStateCode;
	}

	public void setEqStateCode(Integer eqStateCode) {
		this.eqStateCode = eqStateCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Boolean getQueryPurchased() {
		return queryPurchased;
	}

	public void setQueryPurchased(Boolean queryPurchased) {
		this.queryPurchased = queryPurchased;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
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

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public String getOrderTaskType() {
		return orderTaskType;
	}

	public void setOrderTaskType(String orderTaskType) {
		this.orderTaskType = orderTaskType;
	}

	public Integer getOrderTaskUserNum() {
		return orderTaskUserNum;
	}

	public void setOrderTaskUserNum(Integer orderTaskUserNum) {
		this.orderTaskUserNum = orderTaskUserNum;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public Boolean getOrderTaskSelfTag() {
		return orderTaskSelfTag;
	}

	public void setOrderTaskSelfTag(Boolean orderTaskSelfTag) {
		this.orderTaskSelfTag = orderTaskSelfTag;
	}

	public String getOrderTaskBizday() {
		return orderTaskBizday;
	}

	public void setOrderTaskBizday(String orderTaskBizday) {
		this.orderTaskBizday = orderTaskBizday;
	}

	public boolean isWithDetails() {
		return withDetails;
	}

	public void setWithDetails(boolean withDetails) {
		this.withDetails = withDetails;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Boolean getQueryReceived() {
		return queryReceived;
	}

	public void setQueryReceived(Boolean queryReceived) {
		this.queryReceived = queryReceived;
	}

	public Boolean getIsAssign() {
		return isAssign;
	}

	public void setIsAssign(Boolean isAssign) {
		this.isAssign = isAssign;
	}
	

}
