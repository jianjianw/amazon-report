package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 配送订单（待配货订单和带发车订单）
 * @author Administrator
 *
 */
public class TransferGoods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1585144919483091235L;
	private String orderNum;					//订单号
	private String orderType;						//订单类型
	private String receiver;						//收货信息
	private String transferSate;					//配送状态
	private BigDecimal settlementMoney;	//款项
	private boolean isSettlement;				//是否结算
	
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTransferSate() {
		return transferSate;
	}
	public void setTransferSate(String transferSate) {
		this.transferSate = transferSate;
	}
	public BigDecimal getSettlementMoney() {
		return settlementMoney;
	}
	public void setSettlementMoney(BigDecimal settlementMoney) {
		this.settlementMoney = settlementMoney;
	}
	public boolean isSettlement() {
		return isSettlement;
	}
	public void setSettlement(boolean isSettlement) {
		this.isSettlement = isSettlement;
	}
	
	
}
