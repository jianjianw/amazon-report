package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleBusinessItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3421798203252348767L;
	private Integer itemNum;
	private String itemName;
	private BigDecimal wholesaleAmount;
	private BigDecimal wholesaleMoney;
	
	public WholesaleBusinessItemDTO() {
		setWholesaleAmount(BigDecimal.ZERO);
		setWholesaleMoney(BigDecimal.ZERO);
	}
	
	public Integer getItemNum() {
		return itemNum;
	}
	public String getItemName() {
		return itemName;
	}
	public BigDecimal getWholesaleAmount() {
		return wholesaleAmount;
	}
	public BigDecimal getWholesaleMoney() {
		return wholesaleMoney;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setWholesaleAmount(BigDecimal wholesaleAmount) {
		this.wholesaleAmount = wholesaleAmount;
	}
	public void setWholesaleMoney(BigDecimal wholesaleMoney) {
		this.wholesaleMoney = wholesaleMoney;
	}
}
