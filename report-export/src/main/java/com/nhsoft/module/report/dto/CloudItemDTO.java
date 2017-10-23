package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.Date;

public class CloudItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2040456449462820878L;
	private String itemId;
	private String leftCatId;
	private Integer itemNum;
	private String itemCode;
	private String itemBarcode;
	private String itemName;
	private String storeItemPinyin;
	private Integer itemType;
	private String itemUnit;
	private Boolean itemDelTag;
	private Integer itemSequence;
	private Date itemCreateTime;
	private Date itemDelTime;
	private Date itemLastEditTime;
	private String systemBookCode;
	private boolean custom;
	private String picUrl;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getLeftCatId() {
		return leftCatId;
	}

	public void setLeftCatId(String leftCatId) {
		this.leftCatId = leftCatId;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemBarcode() {
		return itemBarcode;
	}

	public void setItemBarcode(String itemBarcode) {
		this.itemBarcode = itemBarcode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getStoreItemPinyin() {
		return storeItemPinyin;
	}

	public void setStoreItemPinyin(String storeItemPinyin) {
		this.storeItemPinyin = storeItemPinyin;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public Boolean isItemDelTag() {
		return itemDelTag;
	}

	public void setItemDelTag(Boolean itemDelTag) {
		this.itemDelTag = itemDelTag;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	public Date getItemCreateTime() {
		return itemCreateTime;
	}

	public void setItemCreateTime(Date itemCreateTime) {
		this.itemCreateTime = itemCreateTime;
	}

	public Date getItemDelTime() {
		return itemDelTime;
	}

	public void setItemDelTime(Date itemDelTime) {
		this.itemDelTime = itemDelTime;
	}

	public Date getItemLastEditTime() {
		return itemLastEditTime;
	}

	public void setItemLastEditTime(Date itemLastEditTime) {
		this.itemLastEditTime = itemLastEditTime;
	}

	public boolean isCustom() {
		return custom;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
