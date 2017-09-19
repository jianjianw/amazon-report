package com.nhsoft.report.dto;

import java.io.Serializable;

public class EleBranch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2992961024243326915L;
	private Long shopId;
	private String shopName;
	private Integer branchNum;
	public Long getShopId() {
		return shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getBranchNum() {
		return branchNum;
	}
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
}
