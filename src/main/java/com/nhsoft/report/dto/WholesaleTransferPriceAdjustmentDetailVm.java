package com.nhsoft.report.dto;

import java.io.Serializable;

public class WholesaleTransferPriceAdjustmentDetailVm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7953723097403245175L;
	private String itemCode;
	private String itemName;
	private String itemUnit;
	private String adjustmentDetailOriTransferPrice;
	private String adjustmentDetailTransferPrice;
	private String adjustmentDetailOriWholesalePrice;
	private String adjustmentDetailWholesalePrice;

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

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getAdjustmentDetailOriTransferPrice() {
		return adjustmentDetailOriTransferPrice;
	}

	public void setAdjustmentDetailOriTransferPrice(String adjustmentDetailOriTransferPrice) {
		this.adjustmentDetailOriTransferPrice = adjustmentDetailOriTransferPrice;
	}

	public String getAdjustmentDetailTransferPrice() {
		return adjustmentDetailTransferPrice;
	}

	public void setAdjustmentDetailTransferPrice(String adjustmentDetailTransferPrice) {
		this.adjustmentDetailTransferPrice = adjustmentDetailTransferPrice;
	}

	public String getAdjustmentDetailOriWholesalePrice() {
		return adjustmentDetailOriWholesalePrice;
	}

	public void setAdjustmentDetailOriWholesalePrice(String adjustmentDetailOriWholesalePrice) {
		this.adjustmentDetailOriWholesalePrice = adjustmentDetailOriWholesalePrice;
	}

	public String getAdjustmentDetailWholesalePrice() {
		return adjustmentDetailWholesalePrice;
	}

	public void setAdjustmentDetailWholesalePrice(String adjustmentDetailWholesalePrice) {
		this.adjustmentDetailWholesalePrice = adjustmentDetailWholesalePrice;
	}

}
