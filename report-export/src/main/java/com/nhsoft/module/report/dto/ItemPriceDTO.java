package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPriceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2601175792929147463L;
	private Integer itemNum;
	private String itemLotNumber;
	private BigDecimal itemTransferPrice;
	private BigDecimal itemTransferGross;
	private Boolean itemTransferFixedGross;
	private BigDecimal itemWholesalePrice;
	
	public BigDecimal getItemWholesalePrice() {
		return itemWholesalePrice;
	}
	
	public void setItemWholesalePrice(BigDecimal itemWholesalePrice) {
		this.itemWholesalePrice = itemWholesalePrice;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemLotNumber() {
		return itemLotNumber;
	}

	public void setItemLotNumber(String itemLotNumber) {
		this.itemLotNumber = itemLotNumber;
	}

	public BigDecimal getItemTransferPrice() {
		return itemTransferPrice;
	}

	public void setItemTransferPrice(BigDecimal itemTransferPrice) {
		this.itemTransferPrice = itemTransferPrice;
	}

	public BigDecimal getItemTransferGross() {
		return itemTransferGross;
	}

	public void setItemTransferGross(BigDecimal itemTransferGross) {
		this.itemTransferGross = itemTransferGross;
	}

	public Boolean getItemTransferFixedGross() {
		return itemTransferFixedGross;
	}

	public void setItemTransferFixedGross(Boolean itemTransferFixedGross) {
		this.itemTransferFixedGross = itemTransferFixedGross;
	}

}
