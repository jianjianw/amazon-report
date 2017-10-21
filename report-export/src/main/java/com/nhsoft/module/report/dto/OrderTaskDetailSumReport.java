package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderTaskDetailSumReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101426155102436389L;
	private Integer userNum;
	private String userName;
	private Integer itemNum;
	private String itemName;
	private String itemCode;
	private String itemSpec;
	private String itemPurchaseUnit;
	private BigDecimal theoryValue;
	private BigDecimal actualValue;
	private BigDecimal tareMoney;
	private BigDecimal purchaseTare;
	private BigDecimal actualTare;
	
	public OrderTaskDetailSumReport() {
		setTheoryValue(BigDecimal.ZERO);
		setActualValue(BigDecimal.ZERO);
		setTareMoney(BigDecimal.ZERO);
		setPurchaseTare(BigDecimal.ZERO);
		setActualTare(BigDecimal.ZERO);
	}
	
	public String getItemPurchaseUnit() {
		return itemPurchaseUnit;
	}

	public void setItemPurchaseUnit(String itemPurchaseUnit) {
		this.itemPurchaseUnit = itemPurchaseUnit;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getTheoryValue() {
		return theoryValue;
	}
	public void setTheoryValue(BigDecimal theoryValue) {
		this.theoryValue = theoryValue;
	}
	public BigDecimal getActualValue() {
		return actualValue;
	}
	public void setActualValue(BigDecimal actualValue) {
		this.actualValue = actualValue;
	}
	public BigDecimal getTareMoney() {
		return tareMoney;
	}
	public void setTareMoney(BigDecimal tareMoney) {
		this.tareMoney = tareMoney;
	}
	public BigDecimal getPurchaseTare() {
		return purchaseTare;
	}
	public void setPurchaseTare(BigDecimal purchaseTare) {
		this.purchaseTare = purchaseTare;
	}
	public BigDecimal getActualTare() {
		return actualTare;
	}
	public void setActualTare(BigDecimal actualTare) {
		this.actualTare = actualTare;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}
}
