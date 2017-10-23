package com.nhsoft.module.report.query;

import com.nhsoft.module.report.dto.QueryBuilder;

import java.util.Date;

public class LogQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3276044809632648492L;
	private Date dateFrom;
	private Date dateTo;
	private String operator;
	private String summary;
	private String operateType;
	private Integer appUserNum;
	private Integer supplierUserNum;
	private String wholesaleUserId;
	private String logMemo;
	private String logItem;
	private String logSystem;
	
	private String sortField;
	private String sortType;
	private boolean isPaging = true;
	//查询web_log时用到
	private int queryType = 0;//0 只查原表 1 只查历史表  2 都查
	private Date limitDate;
	

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Integer getSupplierUserNum() {
		return supplierUserNum;
	}

	public void setSupplierUserNum(Integer supplierUserNum) {
		this.supplierUserNum = supplierUserNum;
	}

	public String getWholesaleUserId() {
		return wholesaleUserId;
	}

	public void setWholesaleUserId(String wholesaleUserId) {
		this.wholesaleUserId = wholesaleUserId;
	}

	public String getLogMemo() {
		return logMemo;
	}

	public void setLogMemo(String logMemo) {
		this.logMemo = logMemo;
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

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	public Integer getAppUserNum() {
		return appUserNum;
	}

	public void setAppUserNum(Integer appUserNum) {
		this.appUserNum = appUserNum;
	}

	public String getLogItem() {
		return logItem;
	}

	public void setLogItem(String logItem) {
		this.logItem = logItem;
	}

	public String getLogSystem() {
		return logSystem;
	}

	public void setLogSystem(String logSystem) {
		this.logSystem = logSystem;
	}

	@Override
	public boolean checkQueryBuild() {
		
		return false;
	}

}
