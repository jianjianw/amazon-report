package com.nhsoft.report.dto;

import java.io.Serializable;

public class AppUserToDoDTO implements Serializable {

	private static final long serialVersionUID = 2069776011682869449L;
	private Boolean unReceivePurchase;// 未收货采购单
	private Boolean unInOutOrder;//未调入的调出单
	private Boolean unSaleBookOrder;//未处理的批发订单
	private Boolean negativeInventoryItems;// 负库存商品
	private Boolean suggestSupplyItems;// 建议补货商品
	private Boolean unOutRequestOrder;//未处理的要货单
	private Boolean unPrintWholesaleOrders;// 一周内未打印的批发销售单
	private Boolean expireItems; // 过期催销商品
	private Boolean customerBirthRemind;//会员生日提醒
	private Boolean unAuditPriceAdjust;//一周内未审核的商品调价单
	private Boolean posClientExtraMoney;//客户超额提醒

	public Boolean getPosClientExtraMoney() {
		return posClientExtraMoney;
	}

	public void setPosClientExtraMoney(Boolean posClientExtraMoney) {
		this.posClientExtraMoney = posClientExtraMoney;
	}

	public Boolean getUnInOutOrder() {
		return unInOutOrder;
	}

	public void setUnInOutOrder(Boolean unInOutOrder) {
		this.unInOutOrder = unInOutOrder;
	}

	public Boolean getUnSaleBookOrder() {
		return unSaleBookOrder;
	}

	public void setUnSaleBookOrder(Boolean unSaleBookOrder) {
		this.unSaleBookOrder = unSaleBookOrder;
	}

	public Boolean getUnOutRequestOrder() {
		return unOutRequestOrder;
	}

	public void setUnOutRequestOrder(Boolean unOutRequestOrder) {
		this.unOutRequestOrder = unOutRequestOrder;
	}

	public Boolean getCustomerBirthRemind() {
		return customerBirthRemind;
	}

	public void setCustomerBirthRemind(Boolean customerBirthRemind) {
		this.customerBirthRemind = customerBirthRemind;
	}

	public Boolean getExpireItems() {
		return expireItems;
	}

	public void setExpireItems(Boolean expireItems) {
		this.expireItems = expireItems;
	}

	public Boolean getUnReceivePurchase() {
		return unReceivePurchase;
	}

	public void setUnReceivePurchase(Boolean unReceivePurchase) {
		this.unReceivePurchase = unReceivePurchase;
	}

	public Boolean getNegativeInventoryItems() {
		return negativeInventoryItems;
	}

	public void setNegativeInventoryItems(Boolean negativeInventoryItems) {
		this.negativeInventoryItems = negativeInventoryItems;
	}

	public Boolean getSuggestSupplyItems() {
		return suggestSupplyItems;
	}

	public void setSuggestSupplyItems(Boolean suggestSupplyItems) {
		this.suggestSupplyItems = suggestSupplyItems;
	}

	public Boolean getUnPrintWholesaleOrders() {
		return unPrintWholesaleOrders;
	}

	public void setUnPrintWholesaleOrders(Boolean unPrintWholesaleOrders) {
		this.unPrintWholesaleOrders = unPrintWholesaleOrders;
	}

	public Boolean getUnAuditPriceAdjust() {
		return unAuditPriceAdjust;
	}

	public void setUnAuditPriceAdjust(Boolean unAuditPriceAdjust) {
		this.unAuditPriceAdjust = unAuditPriceAdjust;
	}

}
