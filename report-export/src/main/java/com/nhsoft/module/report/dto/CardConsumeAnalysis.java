package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardConsumeAnalysis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3718143177059204832L;
	private String rang; // 消费金额范围
	private Integer cardUserNum;// 会员数
	private BigDecimal consumeMoney;// 消费金额
	private BigDecimal consumeRate;// 金额占比（消费金额/总会员消费金额）
	private BigDecimal busiRate;// 营业占比（消费金额/门店营业额）
	private BigDecimal moneyFrom;
	private BigDecimal moneyTo;

	public String getRang() {
		return rang;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public BigDecimal getConsumeRate() {
		return consumeRate;
	}

	public void setConsumeRate(BigDecimal consumeRate) {
		this.consumeRate = consumeRate;
	}

	public BigDecimal getBusiRate() {
		return busiRate;
	}

	public void setBusiRate(BigDecimal busiRate) {
		this.busiRate = busiRate;
	}

	public BigDecimal getMoneyFrom() {
		return moneyFrom;
	}

	public void setMoneyFrom(BigDecimal moneyFrom) {
		this.moneyFrom = moneyFrom;
	}

	public BigDecimal getMoneyTo() {
		return moneyTo;
	}

	public void setMoneyTo(BigDecimal moneyTo) {
		this.moneyTo = moneyTo;
	}


}
