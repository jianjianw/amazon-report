package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245461954705614534L;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private BigDecimal amount;
	private BigDecimal money;
	public Integer getItemNum() {
		return itemNum;
	}
	public String getItemCode() {
		return itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}
