package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class CloudTransferItemMatchDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2545412281655440624L;
	private String systemBookCode;
	private Integer itemNum;
	private String scItemId;
	private boolean custom;
	public String getSystemBookCode() {
		return systemBookCode;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public String getScItemId() {
		return scItemId;
	}
	public boolean isCustom() {
		return custom;
	}
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public void setScItemId(String scItemId) {
		this.scItemId = scItemId;
	}
	public void setCustom(boolean custom) {
		this.custom = custom;
	}
}
