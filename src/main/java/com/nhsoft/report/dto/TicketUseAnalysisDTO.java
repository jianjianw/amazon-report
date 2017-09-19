package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TicketUseAnalysisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -147337630042623631L;
	private Integer branchNum;// 分店
	private String branchName;// 分店
	private Integer useTicketOrders;// 使用消费券单据数:使用此类型消费券的单据数
	private Integer useTicketQty;// 使用张数:使用此类型消费券的张数
	private BigDecimal useTicketMoney;// 抵扣金额:使用此类型消费券的抵用的金额
	private Integer pushOrders;// 拉动消费笔数:使用此类型消费券的单据，有额外支付的单据数
	private BigDecimal pushMoney;// 拉动消费额:使用此类型消费券的单据，额外支付的金额（payment或pos_order里的payment_money）
	private BigDecimal pushRate;// 拉动消费比例:拉动消费笔数/使用消费券单据数
	private BigDecimal orderTotalMoney;//单据总额
	private BigDecimal pushMoneyRate;//拉动消费额比=拉动消费/单据总额

	public TicketUseAnalysisDTO(){
		this.useTicketOrders = 0;
		this.useTicketQty = 0;
		this.pushOrders = 0;
		this.useTicketMoney = BigDecimal.ZERO;
		this.pushMoney = BigDecimal.ZERO;
		this.pushRate = BigDecimal.ZERO;
		this.orderTotalMoney = BigDecimal.ZERO;
		this.pushMoneyRate = BigDecimal.ZERO;
		
	}
	
	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getUseTicketOrders() {
		return useTicketOrders;
	}

	public void setUseTicketOrders(Integer useTicketOrders) {
		this.useTicketOrders = useTicketOrders;
	}

	public Integer getUseTicketQty() {
		return useTicketQty;
	}

	public void setUseTicketQty(Integer useTicketQty) {
		this.useTicketQty = useTicketQty;
	}

	public BigDecimal getUseTicketMoney() {
		return useTicketMoney;
	}

	public void setUseTicketMoney(BigDecimal useTicketMoney) {
		this.useTicketMoney = useTicketMoney;
	}

	public Integer getPushOrders() {
		return pushOrders;
	}

	public void setPushOrders(Integer pushOrders) {
		this.pushOrders = pushOrders;
	}

	public BigDecimal getPushMoney() {
		return pushMoney;
	}

	public void setPushMoney(BigDecimal pushMoney) {
		this.pushMoney = pushMoney;
	}

	public BigDecimal getPushRate() {
		return pushRate;
	}

	public void setPushRate(BigDecimal pushRate) {
		this.pushRate = pushRate;
	}

	public BigDecimal getOrderTotalMoney() {
		return orderTotalMoney;
	}

	public void setOrderTotalMoney(BigDecimal orderTotalMoney) {
		this.orderTotalMoney = orderTotalMoney;
	}

	public BigDecimal getPushMoneyRate() {
		return pushMoneyRate;
	}

	public void setPushMoneyRate(BigDecimal pushMoneyRate) {
		this.pushMoneyRate = pushMoneyRate;
	}

}
