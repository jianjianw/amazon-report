package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierItemOrderSummaryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7722521397787745568L;
	private Integer supplierNum;
	private String supplierName;
	private String supplierCode;

	private Integer itemNum;
	private String itemName;
	private String itemCode;

	private BigDecimal receiveAmount;
	private BigDecimal receiveAssistAmount;
	private BigDecimal receiveMoney;
	private BigDecimal returnAmount;
	private BigDecimal returnAssistAmount;
	private BigDecimal returnMoney;
	private BigDecimal transferInAmount;
	private BigDecimal transferInAssistAmount;
	private BigDecimal transferInMoney;
	private BigDecimal transferOutAmount;
	private BigDecimal transferOutAssistAmount;
	private BigDecimal transferOutMoney;
	private BigDecimal checkOrderAmount;
	private BigDecimal checkOrderAssistAmount;
	private BigDecimal checkOrderMoney;
	private BigDecimal posAmount;
	private BigDecimal posAssistAmount;
	private BigDecimal posMoney;
	private BigDecimal wholesaleOutAmount;
	private BigDecimal wholesaleOutAssistAmount;
	private BigDecimal wholesaleOutMoney;
	private BigDecimal wholesaleInAmount;
	private BigDecimal wholesaleInAssistAmount;
	private BigDecimal wholesaleInMoney;
	private BigDecimal adjusetmentOrderAmount;
	private BigDecimal adjusetmentOrderAssistAmount;
	private BigDecimal adjusetmentOrderMoney;
	private BigDecimal costAdjustmentMoney; // 成本调整金额

	public SupplierItemOrderSummaryDTO() {
		setReceiveAmount(BigDecimal.ZERO);
		setReceiveAssistAmount(BigDecimal.ZERO);
		setReceiveMoney(BigDecimal.ZERO);
		setReturnAmount(BigDecimal.ZERO);
		setReturnAssistAmount(BigDecimal.ZERO);
		setReturnMoney(BigDecimal.ZERO);
		setTransferInAmount(BigDecimal.ZERO);
		setTransferInAssistAmount(BigDecimal.ZERO);
		setTransferInMoney(BigDecimal.ZERO);
		setTransferOutAmount(BigDecimal.ZERO);
		setTransferOutAssistAmount(BigDecimal.ZERO);
		setTransferOutMoney(BigDecimal.ZERO);
		setCheckOrderAmount(BigDecimal.ZERO);
		setCheckOrderAssistAmount(BigDecimal.ZERO);
		setCheckOrderMoney(BigDecimal.ZERO);
		setPosAmount(BigDecimal.ZERO);
		setPosAssistAmount(BigDecimal.ZERO);
		setPosMoney(BigDecimal.ZERO);
		setWholesaleOutAmount(BigDecimal.ZERO);
		setWholesaleOutAssistAmount(BigDecimal.ZERO);
		setWholesaleOutMoney(BigDecimal.ZERO);
		setWholesaleInAmount(BigDecimal.ZERO);
		setWholesaleInAssistAmount(BigDecimal.ZERO);
		setWholesaleInMoney(BigDecimal.ZERO);
		setAdjusetmentOrderAmount(BigDecimal.ZERO);
		setAdjusetmentOrderAssistAmount(BigDecimal.ZERO);
		setAdjusetmentOrderMoney(BigDecimal.ZERO);
		setCostAdjustmentMoney(BigDecimal.ZERO);
	}
	
	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public BigDecimal getReceiveAssistAmount() {
		return receiveAssistAmount;
	}

	public void setReceiveAssistAmount(BigDecimal receiveAssistAmount) {
		this.receiveAssistAmount = receiveAssistAmount;
	}

	public BigDecimal getReceiveMoney() {
		return receiveMoney;
	}

	public void setReceiveMoney(BigDecimal receiveMoney) {
		this.receiveMoney = receiveMoney;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public BigDecimal getReturnAssistAmount() {
		return returnAssistAmount;
	}

	public void setReturnAssistAmount(BigDecimal returnAssistAmount) {
		this.returnAssistAmount = returnAssistAmount;
	}

	public BigDecimal getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}

	public BigDecimal getTransferInAmount() {
		return transferInAmount;
	}

	public void setTransferInAmount(BigDecimal transferInAmount) {
		this.transferInAmount = transferInAmount;
	}

	public BigDecimal getTransferInAssistAmount() {
		return transferInAssistAmount;
	}

	public void setTransferInAssistAmount(BigDecimal transferInAssistAmount) {
		this.transferInAssistAmount = transferInAssistAmount;
	}

	public BigDecimal getTransferInMoney() {
		return transferInMoney;
	}

	public void setTransferInMoney(BigDecimal transferInMoney) {
		this.transferInMoney = transferInMoney;
	}

	public BigDecimal getTransferOutAmount() {
		return transferOutAmount;
	}

	public void setTransferOutAmount(BigDecimal transferOutAmount) {
		this.transferOutAmount = transferOutAmount;
	}

	public BigDecimal getTransferOutAssistAmount() {
		return transferOutAssistAmount;
	}

	public void setTransferOutAssistAmount(BigDecimal transferOutAssistAmount) {
		this.transferOutAssistAmount = transferOutAssistAmount;
	}

	public BigDecimal getTransferOutMoney() {
		return transferOutMoney;
	}

	public void setTransferOutMoney(BigDecimal transferOutMoney) {
		this.transferOutMoney = transferOutMoney;
	}

	public BigDecimal getCheckOrderAmount() {
		return checkOrderAmount;
	}

	public void setCheckOrderAmount(BigDecimal checkOrderAmount) {
		this.checkOrderAmount = checkOrderAmount;
	}

	public BigDecimal getCheckOrderAssistAmount() {
		return checkOrderAssistAmount;
	}

	public void setCheckOrderAssistAmount(BigDecimal checkOrderAssistAmount) {
		this.checkOrderAssistAmount = checkOrderAssistAmount;
	}

	public BigDecimal getCheckOrderMoney() {
		return checkOrderMoney;
	}

	public void setCheckOrderMoney(BigDecimal checkOrderMoney) {
		this.checkOrderMoney = checkOrderMoney;
	}

	public BigDecimal getPosAmount() {
		return posAmount;
	}

	public void setPosAmount(BigDecimal posAmount) {
		this.posAmount = posAmount;
	}

	public BigDecimal getPosAssistAmount() {
		return posAssistAmount;
	}

	public void setPosAssistAmount(BigDecimal posAssistAmount) {
		this.posAssistAmount = posAssistAmount;
	}

	public BigDecimal getPosMoney() {
		return posMoney;
	}

	public void setPosMoney(BigDecimal posMoney) {
		this.posMoney = posMoney;
	}

	public BigDecimal getWholesaleOutAmount() {
		return wholesaleOutAmount;
	}

	public void setWholesaleOutAmount(BigDecimal wholesaleOutAmount) {
		this.wholesaleOutAmount = wholesaleOutAmount;
	}

	public BigDecimal getWholesaleOutAssistAmount() {
		return wholesaleOutAssistAmount;
	}

	public void setWholesaleOutAssistAmount(BigDecimal wholesaleOutAssistAmount) {
		this.wholesaleOutAssistAmount = wholesaleOutAssistAmount;
	}

	public BigDecimal getWholesaleOutMoney() {
		return wholesaleOutMoney;
	}

	public void setWholesaleOutMoney(BigDecimal wholesaleOutMoney) {
		this.wholesaleOutMoney = wholesaleOutMoney;
	}

	public BigDecimal getWholesaleInAmount() {
		return wholesaleInAmount;
	}

	public void setWholesaleInAmount(BigDecimal wholesaleInAmount) {
		this.wholesaleInAmount = wholesaleInAmount;
	}

	public BigDecimal getWholesaleInAssistAmount() {
		return wholesaleInAssistAmount;
	}

	public void setWholesaleInAssistAmount(BigDecimal wholesaleInAssistAmount) {
		this.wholesaleInAssistAmount = wholesaleInAssistAmount;
	}

	public BigDecimal getWholesaleInMoney() {
		return wholesaleInMoney;
	}

	public void setWholesaleInMoney(BigDecimal wholesaleInMoney) {
		this.wholesaleInMoney = wholesaleInMoney;
	}

	public BigDecimal getAdjusetmentOrderAmount() {
		return adjusetmentOrderAmount;
	}

	public void setAdjusetmentOrderAmount(BigDecimal adjusetmentOrderAmount) {
		this.adjusetmentOrderAmount = adjusetmentOrderAmount;
	}

	public BigDecimal getAdjusetmentOrderAssistAmount() {
		return adjusetmentOrderAssistAmount;
	}

	public void setAdjusetmentOrderAssistAmount(BigDecimal adjusetmentOrderAssistAmount) {
		this.adjusetmentOrderAssistAmount = adjusetmentOrderAssistAmount;
	}

	public BigDecimal getAdjusetmentOrderMoney() {
		return adjusetmentOrderMoney;
	}

	public void setAdjusetmentOrderMoney(BigDecimal adjusetmentOrderMoney) {
		this.adjusetmentOrderMoney = adjusetmentOrderMoney;
	}

	public BigDecimal getCostAdjustmentMoney() {
		return costAdjustmentMoney;
	}

	public void setCostAdjustmentMoney(BigDecimal costAdjustmentMoney) {
		this.costAdjustmentMoney = costAdjustmentMoney;
	}

}
