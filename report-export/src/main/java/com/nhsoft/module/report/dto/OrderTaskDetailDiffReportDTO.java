package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yangqin on 2017/9/1.
 */
public class OrderTaskDetailDiffReportDTO implements Serializable{
	
	
	private static final long serialVersionUID = -7638971599975027610L;
	private Integer itemNum;
	private String itemName;
	private String itemCode;
	private String itemTransferUnit;
	private BigDecimal money;
	private Integer appUserNum; //暂不赋值
	private String appUserName;
	private BigDecimal purchaseQty;
	private BigDecimal purchasePrice;
	private BigDecimal purchaseTare;
	private BigDecimal receiveQty;
	private BigDecimal receivePrice;
	private BigDecimal receiveTare;
	private BigDecimal diffQty;
	private BigDecimal diffTare;
	
	public String getItemTransferUnit() {
		return itemTransferUnit;
	}
	
	public void setItemTransferUnit(String itemTransferUnit) {
		this.itemTransferUnit = itemTransferUnit;
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
	
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public Integer getAppUserNum() {
		return appUserNum;
	}
	
	public void setAppUserNum(Integer appUserNum) {
		this.appUserNum = appUserNum;
	}
	
	public String getAppUserName() {
		return appUserName;
	}
	
	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}
	
	public BigDecimal getPurchaseQty() {
		return purchaseQty;
	}
	
	public void setPurchaseQty(BigDecimal purchaseQty) {
		this.purchaseQty = purchaseQty;
	}
	
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	public BigDecimal getPurchaseTare() {
		return purchaseTare;
	}
	
	public void setPurchaseTare(BigDecimal purchaseTare) {
		this.purchaseTare = purchaseTare;
	}
	
	public BigDecimal getReceiveQty() {
		return receiveQty;
	}
	
	public void setReceiveQty(BigDecimal receiveQty) {
		this.receiveQty = receiveQty;
	}
	
	public BigDecimal getReceivePrice() {
		return receivePrice;
	}
	
	public void setReceivePrice(BigDecimal receivePrice) {
		this.receivePrice = receivePrice;
	}
	
	public BigDecimal getReceiveTare() {
		return receiveTare;
	}
	
	public void setReceiveTare(BigDecimal receiveTare) {
		this.receiveTare = receiveTare;
	}
	
	public BigDecimal getDiffQty() {
		return diffQty;
	}
	
	public void setDiffQty(BigDecimal diffQty) {
		this.diffQty = diffQty;
	}
	
	public BigDecimal getDiffTare() {
		return diffTare;
	}
	
	public void setDiffTare(BigDecimal diffTare) {
		this.diffTare = diffTare;
	}
}
