package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalesDiscountDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2566439095504241521L;
	private String itemCode;
	private String itemName;
	private BigDecimal itemPresented;// 赠
	private BigDecimal itemPresentedMoney;// 赠金额
	private BigDecimal itemCanceled;// 撤
	private BigDecimal itemCanceledMoney;// 撤金额
	private BigDecimal itemRemoved;// 退
	private BigDecimal itemRemovedMoney;// 退金额
	private BigDecimal itemDiscount;// 折扣
	private BigDecimal itemDiscountMoney;// 折扣金额

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

	public BigDecimal getItemPresented() {
		return itemPresented;
	}

	public void setItemPresented(BigDecimal itemPresented) {
		this.itemPresented = itemPresented;
	}

	public BigDecimal getItemPresentedMoney() {
		return itemPresentedMoney;
	}

	public void setItemPresentedMoney(BigDecimal itemPresentedMoney) {
		this.itemPresentedMoney = itemPresentedMoney;
	}

	public BigDecimal getItemCanceled() {
		return itemCanceled;
	}

	public void setItemCanceled(BigDecimal itemCanceled) {
		this.itemCanceled = itemCanceled;
	}

	public BigDecimal getItemCanceledMoney() {
		return itemCanceledMoney;
	}

	public void setItemCanceledMoney(BigDecimal itemCanceledMoney) {
		this.itemCanceledMoney = itemCanceledMoney;
	}

	public BigDecimal getItemRemoved() {
		return itemRemoved;
	}

	public void setItemRemoved(BigDecimal itemRemoved) {
		this.itemRemoved = itemRemoved;
	}

	public BigDecimal getItemRemovedMoney() {
		return itemRemovedMoney;
	}

	public void setItemRemovedMoney(BigDecimal itemRemovedMoney) {
		this.itemRemovedMoney = itemRemovedMoney;
	}

	public BigDecimal getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(BigDecimal itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public BigDecimal getItemDiscountMoney() {
		return itemDiscountMoney;
	}

	public void setItemDiscountMoney(BigDecimal itemDiscountMoney) {
		this.itemDiscountMoney = itemDiscountMoney;
	}

}
