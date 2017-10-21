package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class CloudBranchDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5493057029083278475L;
	private String systemBookCode;
	private String storeCode;
	private String storeName;
	private String provName;
	private String cityName;
	private String areaName;
	private String address;
	private Integer branchNum;
	public String getSystemBookCode() {
		return systemBookCode;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public Integer getBranchNum() {
		return branchNum;
	}
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	public String getStoreName() {
		return storeName;
	}
	public String getProvName() {
		return provName;
	}
	public String getCityName() {
		return cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public String getAddress() {
		return address;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
