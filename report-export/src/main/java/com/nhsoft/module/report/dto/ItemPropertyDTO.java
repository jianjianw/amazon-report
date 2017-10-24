package com.nhsoft.module.report.dto;

// Generated 2016-8-18 13:47:14 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * ItemProperty generated by hbm2java
 */
public class ItemPropertyDTO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1724426861191490648L;

	private int itemPropertyNum;
	private int itemNum;
	private String itemPropertyName;
	private String itemPropertyType;
	private String itemPropertyTypeValue;
	private String itemPropertyMemo;
	private BigDecimal itemPropertyPercent;

	public ItemPropertyDTO() {
	}

	public int getItemPropertyNum() {
		return itemPropertyNum;
	}


	public void setItemPropertyNum(int itemPropertyNum) {
		this.itemPropertyNum = itemPropertyNum;
	}


	public int getItemNum() {
		return itemNum;
	}


	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}


	public String getItemPropertyName() {
		return this.itemPropertyName;
	}

	public void setItemPropertyName(String itemPropertyName) {
		this.itemPropertyName = itemPropertyName;
	}

	public String getItemPropertyType() {
		return this.itemPropertyType;
	}

	public void setItemPropertyType(String itemPropertyType) {
		this.itemPropertyType = itemPropertyType;
	}

	public String getItemPropertyTypeValue() {
		return this.itemPropertyTypeValue;
	}

	public void setItemPropertyTypeValue(String itemPropertyTypeValue) {
		this.itemPropertyTypeValue = itemPropertyTypeValue;
	}

	public String getItemPropertyMemo() {
		return this.itemPropertyMemo;
	}

	public void setItemPropertyMemo(String itemPropertyMemo) {
		this.itemPropertyMemo = itemPropertyMemo;
	}

	public BigDecimal getItemPropertyPercent() {
		return this.itemPropertyPercent;
	}

	public void setItemPropertyPercent(BigDecimal itemPropertyPercent) {
		this.itemPropertyPercent = itemPropertyPercent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itemPropertyNum;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPropertyDTO other = (ItemPropertyDTO) obj;
		if (itemPropertyNum != other.itemPropertyNum)
			return false;
		return true;
	}

}