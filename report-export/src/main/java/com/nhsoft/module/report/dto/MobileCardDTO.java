package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MobileCardDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5582483632190061458L;
	private Integer validCardCount;// 可用卡总量
	private BigDecimal cardBalanceMoney;// 剩余储值额
	
	private BigDecimal depositTotalMoney;          // 存款总额
    private BigDecimal consumeTotalMoney;        // 消费总额
    private Integer newCardCount;             // 新增会员数
    
    private List<NameAndValueDTO> depositDateMoneyDetails = new ArrayList<NameAndValueDTO>(0);    //存款金额线形图
    private List<NameAndValueDTO> consumeDateMoneyDetails = new ArrayList<NameAndValueDTO>(0);    //消费金额线形图


	public BigDecimal getDepositTotalMoney() {
		return depositTotalMoney;
	}

	public void setDepositTotalMoney(BigDecimal depositTotalMoney) {
		this.depositTotalMoney = depositTotalMoney;
	}

	public BigDecimal getConsumeTotalMoney() {
		return consumeTotalMoney;
	}

	public void setConsumeTotalMoney(BigDecimal consumeTotalMoney) {
		this.consumeTotalMoney = consumeTotalMoney;
	}

	public Integer getNewCardCount() {
		return newCardCount;
	}

	public void setNewCardCount(Integer newCardCount) {
		this.newCardCount = newCardCount;
	}

	public List<NameAndValueDTO> getDepositDateMoneyDetails() {
		return depositDateMoneyDetails;
	}

	public void setDepositDateMoneyDetails(List<NameAndValueDTO> depositDateMoneyDetails) {
		this.depositDateMoneyDetails = depositDateMoneyDetails;
	}

	public List<NameAndValueDTO> getConsumeDateMoneyDetails() {
		return consumeDateMoneyDetails;
	}

	public void setConsumeDateMoneyDetails(List<NameAndValueDTO> consumeDateMoneyDetails) {
		this.consumeDateMoneyDetails = consumeDateMoneyDetails;
	}

	public Integer getValidCardCount() {
		return validCardCount;
	}

	public void setValidCardCount(Integer validCardCount) {
		this.validCardCount = validCardCount;
	}

	public BigDecimal getCardBalanceMoney() {
		return cardBalanceMoney;
	}

	public void setCardBalanceMoney(BigDecimal cardBalanceMoney) {
		this.cardBalanceMoney = cardBalanceMoney;
	}

}
