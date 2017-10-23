package com.nhsoft.module.report.model;

import java.math.BigDecimal;

/**
 * CardBalance entity. @author MyEclipse Persistence Tools
 */

public class CardBalance implements java.io.Serializable {

	private static final long serialVersionUID = -8146906751552283559L;
	private Integer cardUserNum;
	private BigDecimal cardBalanceTotalDeposit;
	private BigDecimal cardBalanceTotalConsume;
	private BigDecimal cardBalanceMoney;
	private BigDecimal cardChangeMoney;

	public Integer getCardUserNum() {
		return this.cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public BigDecimal getCardBalanceTotalDeposit() {
		return cardBalanceTotalDeposit;
	}

	public void setCardBalanceTotalDeposit(BigDecimal cardBalanceTotalDeposit) {
		this.cardBalanceTotalDeposit = cardBalanceTotalDeposit;
	}

	public BigDecimal getCardBalanceTotalConsume() {
		return cardBalanceTotalConsume;
	}

	public void setCardBalanceTotalConsume(BigDecimal cardBalanceTotalConsume) {
		this.cardBalanceTotalConsume = cardBalanceTotalConsume;
	}

	public BigDecimal getCardBalanceMoney() {
		return cardBalanceMoney;
	}

	public void setCardBalanceMoney(BigDecimal cardBalanceMoney) {
		this.cardBalanceMoney = cardBalanceMoney;
	}

	public BigDecimal getCardChangeMoney() {
		return cardChangeMoney;
	}

	public void setCardChangeMoney(BigDecimal cardChangeMoney) {
		this.cardChangeMoney = cardChangeMoney;
	}

}