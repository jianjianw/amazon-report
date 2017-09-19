package com.nhsoft.report.dto;

import java.io.Serializable;

public class CloudTransferItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2911481691082059361L;
	private String systemBookCode;
	private String scItemId;
	private String scItemTitle;
	private String shopOwnerId;
	private String scItemPinyin;

	public String getSystemBookCode() {
		return systemBookCode;
	}
	public String getScItemId() {
		return scItemId;
	}
	public String getScItemTitle() {
		return scItemTitle;
	}
	public String getShopOwnerId() {
		return shopOwnerId;
	}
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	public void setScItemId(String scItemId) {
		this.scItemId = scItemId;
	}
	public void setScItemTitle(String scItemTitle) {
		this.scItemTitle = scItemTitle;
	}
	public void setShopOwnerId(String shopOwnerId) {
		this.shopOwnerId = shopOwnerId;
	}
	public String getScItemPinyin() {
		return scItemPinyin;
	}
	public void setScItemPinyin(String scItemPinyin) {
		this.scItemPinyin = scItemPinyin;
	}
}
