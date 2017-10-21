package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class CloudBookDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5564860043438753758L;
	private String systemBookCode;
	private String bookName;
	private String shopOwnerId;
	public String getSystemBookCode() {
		return systemBookCode;
	}
	public String getBookName() {
		return bookName;
	}
	public String getShopOwnerId() {
		return shopOwnerId;
	}
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public void setShopOwnerId(String shopOwnerId) {
		this.shopOwnerId = shopOwnerId;
	}
}
