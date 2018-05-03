package com.nhsoft.module.report.dto;


import java.io.Serializable;

public class AppUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3988475184728432572L;
	private String appUserId;
	private String appUserCode;
	private String appUserName;
	private String appUserPhone;
	private String branchName;
	private String bookName;
	private Integer branchNum;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
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

	public String getAppUserPhone() {
		return appUserPhone;
	}

	public void setAppUserPhone(String appUserPhone) {
		this.appUserPhone = appUserPhone;
	}


}
