package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShipOrderBranchDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5022302145289488695L;
	private String itemCode;
	private String itemName;
	private BigDecimal shipOrderItemCount;
	private BigDecimal shipOrderItemMoney;
	
	public ShipOrderBranchDetailDTO() {
		setShipOrderItemCount(BigDecimal.ZERO);
		setShipOrderItemMoney(BigDecimal.ZERO);
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

	public BigDecimal getShipOrderItemCount() {
		return shipOrderItemCount;
	}

	public void setShipOrderItemCount(BigDecimal shipOrderItemCount) {
		this.shipOrderItemCount = shipOrderItemCount;
	}

	public BigDecimal getShipOrderItemMoney() {
		return shipOrderItemMoney;
	}

	public void setShipOrderItemMoney(BigDecimal shipOrderItemMoney) {
		this.shipOrderItemMoney = shipOrderItemMoney;
	}
	
	
	
}
