package com.nhsoft.report.dto;


import com.nhsoft.report.model.Branch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MobileUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2001077379635526815L;
	private String systemBookCode;
	private String appUserCode;
	private String appUserName;
	private Boolean isQueryNotActive;
	private String appUserPassword;
	private Integer centerBranchNum;
	private Branch branch;
	private List<Branch> branchs = new ArrayList<Branch>();
	private List<Branch> notActiveBranchs = new ArrayList<Branch>();
	private String appUserPhoneProperty;
	private String branchRegionNum;
	private Integer appUserNum;
	private String appUserPhone;
	private List<String> privileges = new ArrayList<String>();

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

	public Boolean getIsQueryNotActive() {
		return isQueryNotActive;
	}

	public void setIsQueryNotActive(Boolean isQueryNotActive) {
		this.isQueryNotActive = isQueryNotActive;
	}

	public String getAppUserPassword() {
		return appUserPassword;
	}

	public void setAppUserPassword(String appUserPassword) {
		this.appUserPassword = appUserPassword;
	}

	public Integer getCenterBranchNum() {
		return centerBranchNum;
	}

	public void setCenterBranchNum(Integer centerBranchNum) {
		this.centerBranchNum = centerBranchNum;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<Branch> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Branch> branchs) {
		this.branchs = branchs;
	}

	public List<Branch> getNotActiveBranchs() {
		return notActiveBranchs;
	}

	public void setNotActiveBranchs(List<Branch> notActiveBranchs) {
		this.notActiveBranchs = notActiveBranchs;
	}

	public String getAppUserPhoneProperty() {
		return appUserPhoneProperty;
	}

	public void setAppUserPhoneProperty(String appUserPhoneProperty) {
		this.appUserPhoneProperty = appUserPhoneProperty;
	}

	public String getBranchRegionNum() {
		return branchRegionNum;
	}

	public void setBranchRegionNum(String branchRegionNum) {
		this.branchRegionNum = branchRegionNum;
	}

	public List<String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}

	public Integer getAppUserNum() {
		return appUserNum;
	}

	public void setAppUserNum(Integer appUserNum) {
		this.appUserNum = appUserNum;
	}

	public String getAppUserPhone() {
		return appUserPhone;
	}

	public void setAppUserPhone(String appUserPhone) {
		this.appUserPhone = appUserPhone;
	}

}
