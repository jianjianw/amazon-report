package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarketActionAnalyseDTO implements Serializable{
	
	private String actionName;
	private Integer actionCustomerCount;	//参加营销活动人数 
	private Integer responseCustomerCount;	//响应人数
	private BigDecimal sendTicketValueSum; // 发送券总额
	private BigDecimal useTicketValueSum; // 使用券总额
	private BigDecimal paymentMoneySum; // 账单实收
	private BigDecimal actionProfit; // 拉动消费额 （账单实收-使用券总额）
	
	public MarketActionAnalyseDTO(){
		actionCustomerCount = 0;
		responseCustomerCount = 0;
		sendTicketValueSum = BigDecimal.ZERO;
		useTicketValueSum = BigDecimal.ZERO;
		paymentMoneySum = BigDecimal.ZERO;
		actionProfit = BigDecimal.ZERO;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Integer getActionCustomerCount() {
		return actionCustomerCount;
	}

	public void setActionCustomerCount(Integer actionCustomerCount) {
		this.actionCustomerCount = actionCustomerCount;
	}

	public Integer getResponseCustomerCount() {
		return responseCustomerCount;
	}

	public void setResponseCustomerCount(Integer responseCustomerCount) {
		this.responseCustomerCount = responseCustomerCount;
	}

	public BigDecimal getSendTicketValueSum() {
		return sendTicketValueSum;
	}

	public void setSendTicketValueSum(BigDecimal sendTicketValueSum) {
		this.sendTicketValueSum = sendTicketValueSum;
	}

	public BigDecimal getUseTicketValueSum() {
		return useTicketValueSum;
	}

	public void setUseTicketValueSum(BigDecimal useTicketValueSum) {
		this.useTicketValueSum = useTicketValueSum;
	}

	public BigDecimal getPaymentMoneySum() {
		return paymentMoneySum;
	}

	public void setPaymentMoneySum(BigDecimal paymentMoneySum) {
		this.paymentMoneySum = paymentMoneySum;
	}

	public BigDecimal getActionProfit() {
		return actionProfit;
	}

	public void setActionProfit(BigDecimal actionProfit) {
		this.actionProfit = actionProfit;
	}
	
	
	
	
}
