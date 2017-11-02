package com.nhsoft.module.report.shared.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OssObjectQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8538352992364769748L;

	private Date dateFrom; //
	private Date dateTo; // 上传时间
	private String fidOrder; // 单据号， 模糊查询
	private List<String> fidTypes; // 类型。
	private String appUserName; // 上传人
	private String fileName; // 文件名
	private BigDecimal sizeFrom; // 文件大于
	private BigDecimal sizeTo; // 文件小于
	
	private String sortField;
	private String sortType;

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

	public String getFidOrder() {
		return fidOrder;
	}

	public void setFidOrder(String fidOrder) {
		this.fidOrder = fidOrder;
	}

	public List<String> getFidTypes() {
		return fidTypes;
	}

	public void setFidTypes(List<String> fidTypes) {
		this.fidTypes = fidTypes;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getSizeFrom() {
		return sizeFrom;
	}

	public void setSizeFrom(BigDecimal sizeFrom) {
		this.sizeFrom = sizeFrom;
	}

	public BigDecimal getSizeTo() {
		return sizeTo;
	}

	public void setSizeTo(BigDecimal sizeTo) {
		this.sizeTo = sizeTo;
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
	

}
