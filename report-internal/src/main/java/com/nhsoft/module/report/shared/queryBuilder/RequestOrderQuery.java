package com.nhsoft.module.report.shared.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class RequestOrderQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2637800785321031490L;
	private Date dateFrom;
	private Date dateTo;
	private Integer centerBranchNum;
	private Integer branchNum;
	private String orderNum;
	private List<String> itemCategorys;
	private List<Integer> itemNums;
	private Integer status;  // 1：待审核，2：已审核，3：已配货，4：已发货，5：已收货
	private List<Integer> branchNums;
	private String operator;
	private Integer storehouseBranchNum; //仓库对应的分店编号
	private Boolean queryCreateOrder = false;//是否查询制单状态订单
	private String unitType;
	
	private String sortField;
	private String sortType;
	private boolean queryDetais = false;
	private boolean queryPosItem = false;
	
	public RequestOrderQuery(){
		
	}
	
	public boolean isQueryPosItem() {
		return queryPosItem;
	}
	
	public void setQueryPosItem(boolean queryPosItem) {
		this.queryPosItem = queryPosItem;
	}
	
	public String getUnitType() {
		return unitType;
	}
	
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	public Boolean getQueryCreateOrder() {
		return queryCreateOrder;
	}

	public void setQueryCreateOrder(Boolean queryCreateOrder) {
		this.queryCreateOrder = queryCreateOrder;
	}

	public Integer getCenterBranchNum() {
		return centerBranchNum;
	}

	public void setCenterBranchNum(Integer centerBranchNum) {
		this.centerBranchNum = centerBranchNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public RequestOrderQuery(String systemBookCode){
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

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public List<String> getItemCategorys() {
		return itemCategorys;
	}

	public void setItemCategorys(List<String> itemCategorys) {
		this.itemCategorys = itemCategorys;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
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

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}


	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getStorehouseBranchNum() {
		return storehouseBranchNum;
	}

	public void setStorehouseBranchNum(Integer storehouseBranchNum) {
		this.storehouseBranchNum = storehouseBranchNum;
	}
	
	
	public boolean isQueryDetais() {
		return queryDetais;
	}
	
	public void setQueryDetais(boolean queryDetais) {
		this.queryDetais = queryDetais;
	}
}
