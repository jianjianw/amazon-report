package com.nhsoft.module.report.shared.queryBuilder;

import java.util.Date;
import java.util.List;

public class ShoppingQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3675616927062269391L;
	private Integer branchNum;
	private String clientFid;
	private String systemBookCode;
	private Date dtFrom;
	private Date dtTo;
	private Integer appUserNum;
	private String wholesaleUserId;
	private List<Integer> itemNums;
	private List<String> categoryCodes;
	private Integer itemMatrixNum;
	private String unitType;
	private Integer state;
	private List<Integer> regionNums;
	private String openId;
	
	private String sortField;
	private String sortType;
	private boolean isPaging = true;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getClientFid() {
		return clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
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

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
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

	public Integer getAppUserNum() {
		return appUserNum;
	}

	public void setAppUserNum(Integer appUserNum) {
		this.appUserNum = appUserNum;
	}

	public String getWholesaleUserId() {
		return wholesaleUserId;
	}

	public void setWholesaleUserId(String wholesaleUserId) {
		this.wholesaleUserId = wholesaleUserId;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	@Override
	public boolean checkQueryBuild() {
		return false;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<Integer> getRegionNums() {
		return regionNums;
	}

	public void setRegionNums(List<Integer> regionNums) {
		this.regionNums = regionNums;
	}
	
}
