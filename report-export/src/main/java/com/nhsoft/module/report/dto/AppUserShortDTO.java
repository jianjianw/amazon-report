package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class AppUserShortDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378325723564229919L;
	private Integer appUserNum;
	private String systemBookCode;
	private String appUserCode;
	private String appUserName;
	private Integer branchNum;

	public Integer getAppUserNum() {
		return appUserNum;
	}

	public void setAppUserNum(Integer appUserNum) {
		this.appUserNum = appUserNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getAppUserCode() {
		return appUserCode;
	}

	public void setAppUserCode(String appUserCode) {
		this.appUserCode = appUserCode;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

}
