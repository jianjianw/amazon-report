package com.nhsoft.module.report.dto;

// Generated 2016-7-8 17:55:43 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * ErrorLog generated by hbm2java
 */
public class ErrorLogDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7379312209600870483L;
	private Integer errorLogNum;
	private String errorLogIp;
	private String errorLogMethod;
	private Date errorLogDate;
	private String errorLogText;
	private String errorLogType;

	public ErrorLogDTO() {
	}

	public ErrorLogDTO(String errorLogIp) {
		this.errorLogIp = errorLogIp;
	}

	public ErrorLogDTO(String errorLogIp, String errorLogMethod, Date errorLogDate, String errorLogText) {
		this.errorLogIp = errorLogIp;
		this.errorLogMethod = errorLogMethod;
		this.errorLogDate = errorLogDate;
		this.errorLogText = errorLogText;
		this.errorLogType = "POS3Server";
	}

	public Integer getErrorLogNum() {
		return this.errorLogNum;
	}

	public void setErrorLogNum(Integer errorLogNum) {
		this.errorLogNum = errorLogNum;
	}

	public String getErrorLogIp() {
		return this.errorLogIp;
	}

	public void setErrorLogIp(String errorLogIp) {
		this.errorLogIp = errorLogIp;
	}

	public String getErrorLogMethod() {
		return this.errorLogMethod;
	}

	public void setErrorLogMethod(String errorLogMethod) {
		this.errorLogMethod = errorLogMethod;
	}

	public Date getErrorLogDate() {
		return this.errorLogDate;
	}

	public void setErrorLogDate(Date errorLogDate) {
		this.errorLogDate = errorLogDate;
	}

	public String getErrorLogText() {
		return this.errorLogText;
	}

	public void setErrorLogText(String errorLogText) {
		this.errorLogText = errorLogText;
	}

	public String getErrorLogType() {
		return this.errorLogType;
	}

	public void setErrorLogType(String errorLogType) {
		this.errorLogType = errorLogType;
	}

}
