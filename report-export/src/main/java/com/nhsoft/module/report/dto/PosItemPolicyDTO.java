package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosItemPolicyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 260496814855152756L;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private String itemSpec;
	private String itemUnit;
	private String itemLotNumber;
	private BigDecimal itemPrice;
	private BigDecimal itemPolicyPrice;

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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemLotNumber() {
		return itemLotNumber;
	}

	public void setItemLotNumber(String itemLotNumber) {
		this.itemLotNumber = itemLotNumber;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getItemPolicyPrice() {
		return itemPolicyPrice;
	}

	public void setItemPolicyPrice(BigDecimal itemPolicyPrice) {
		this.itemPolicyPrice = itemPolicyPrice;
	}

}
