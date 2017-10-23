package com.nhsoft.module.report.model;

import java.io.Serializable;

public class CustomReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4588484567304300016L;
	private Integer customReportNum;
	private String systemBookCode;
	private String customReportModule;
	private String customReportName;
	private String customReportCondition;
	private String customReportColumn;
	private String customReportContext;

	public Integer getCustomReportNum() {
		return customReportNum;
	}

	public void setCustomReportNum(Integer customReportNum) {
		this.customReportNum = customReportNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getCustomReportModule() {
		return customReportModule;
	}

	public void setCustomReportModule(String customReportModule) {
		this.customReportModule = customReportModule;
	}

	public String getCustomReportName() {
		return customReportName;
	}

	public void setCustomReportName(String customReportName) {
		this.customReportName = customReportName;
	}

	public String getCustomReportCondition() {
		return customReportCondition;
	}

	public void setCustomReportCondition(String customReportCondition) {
		this.customReportCondition = customReportCondition;
	}

	public String getCustomReportColumn() {
		return customReportColumn;
	}

	public void setCustomReportColumn(String customReportColumn) {
		this.customReportColumn = customReportColumn;
	}

	public String getCustomReportContext() {
		return customReportContext;
	}

	public void setCustomReportContext(String customReportContext) {
		this.customReportContext = customReportContext;
	}

}
