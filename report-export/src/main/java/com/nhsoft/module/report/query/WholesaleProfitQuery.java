package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class WholesaleProfitQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4786044473108826944L;
	private Integer banchNum;
	private List<String> clientFids;						//客户fid List
	private List<Integer> posItemNums;				//商品Num List
	private Date dateFrom;
	private Date dateTo;
	private List<String> categorys;
	private String orderFid; //单据号
	private Boolean isCategroyChild;   // 包含子类别
	private boolean isPaging = true;
	private Integer status;  // 1：待审核，2：已审核，3：已配货，4：已发货，5：已中止
	private List<Integer> regionNums;
	private String innerNo;
	private Integer storehouseNum;
	private String auditor;
	private String method;
	private String dateType;
	private String unitType;
	private List<Integer> branchNums;
	private List<String> sellers;
	
	private String sortField;
	private String sortType;

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public List<Integer> getRegionNums() {
		return regionNums;
	}

	public void setRegionNums(List<Integer> regionNums) {
		this.regionNums = regionNums;
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

	public Integer getBanchNum() {
		return banchNum;
	}

	public void setBanchNum(Integer banchNum) {
		this.banchNum = banchNum;
	}

	public List<String> getClientFids() {
		return clientFids;
	}

	public void setClientFids(List<String> clientFids) {
		this.clientFids = clientFids;
	}

	public List<Integer> getPosItemNums() {
		return posItemNums;
	}

	public void setPosItemNums(List<Integer> posItemNums) {
		this.posItemNums = posItemNums;
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

	public List<String> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<String> categorys) {
		this.categorys = categorys;
	}

	public Boolean getIsCategroyChild() {
		return isCategroyChild;
	}

	public void setIsCategroyChild(Boolean isCategroyChild) {
		this.isCategroyChild = isCategroyChild;
	}

	public String getOrderFid() {
		return orderFid;
	}

	public void setOrderFid(String orderFid) {
		this.orderFid = orderFid;
	}

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInnerNo() {
		return innerNo;
	}

	public void setInnerNo(String innerNo) {
		this.innerNo = innerNo;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<String> getSellers() {
		return sellers;
	}

	public void setSellers(List<String> sellers) {
		this.sellers = sellers;
	}
	
}
