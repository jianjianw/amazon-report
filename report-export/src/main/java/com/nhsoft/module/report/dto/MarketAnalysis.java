package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarketAnalysis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5007175608921935071L;
	private String actionId;
	private Integer couponAmount;
	private Integer usedAmount;
	private BigDecimal actualIncome; // 实收金额
	private BigDecimal couponMoney; // 发送券总额
	private BigDecimal usedCoupon; // 使用券额
	private BigDecimal consumeMoney; // 消费额
	private BigDecimal presentTicketOrderMoney;//赠券单据金额
	private BigDecimal discount;//让利比
	private BigDecimal preDiscount;//让利比 (包含赠券单据金额)

	public MarketAnalysis(){
	    couponAmount = Integer.valueOf(0);
	    usedAmount = Integer.valueOf(0);
	    actualIncome = BigDecimal.ZERO;
	    couponMoney = BigDecimal.ZERO;
	    usedCoupon = BigDecimal.ZERO;
	    consumeMoney = BigDecimal.ZERO;
	    presentTicketOrderMoney = BigDecimal.ZERO;
	    discount = BigDecimal.ZERO;
	    preDiscount = BigDecimal.ZERO;
	}
	
	public BigDecimal getPreDiscount() {
		return preDiscount;
	}

	public void setPreDiscount(BigDecimal preDiscount) {
		this.preDiscount = preDiscount;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public Integer getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Integer couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Integer getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(Integer usedAmount) {
		this.usedAmount = usedAmount;
	}

	public BigDecimal getActualIncome() {
		return actualIncome;
	}

	public void setActualIncome(BigDecimal actualIncome) {
		this.actualIncome = actualIncome;
	}

	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public BigDecimal getUsedCoupon() {
		return usedCoupon;
	}

	public void setUsedCoupon(BigDecimal usedCoupon) {
		this.usedCoupon = usedCoupon;
	}

	public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public BigDecimal getPresentTicketOrderMoney() {
		return presentTicketOrderMoney;
	}

	public void setPresentTicketOrderMoney(BigDecimal presentTicketOrderMoney) {
		this.presentTicketOrderMoney = presentTicketOrderMoney;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

}
