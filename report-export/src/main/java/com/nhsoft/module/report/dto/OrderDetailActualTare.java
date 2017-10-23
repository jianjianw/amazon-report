package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailActualTare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9093157704252889870L;
	private String orderFid;
	private Integer orderDetailNum;
	private BigDecimal actualTare;
	private BigDecimal actualGrossWeight;
	
	public BigDecimal getActualGrossWeight() {
		return actualGrossWeight;
	}

	public void setActualGrossWeight(BigDecimal actualGrossWeight) {
		this.actualGrossWeight = actualGrossWeight;
	}

	public BigDecimal getActualTare() {
		return actualTare;
	}

	public void setActualTare(BigDecimal actualTare) {
		this.actualTare = actualTare;
	}

	public String getOrderFid() {
		return orderFid;
	}

	public void setOrderFid(String orderFid) {
		this.orderFid = orderFid;
	}

	public Integer getOrderDetailNum() {
		return orderDetailNum;
	}

	public void setOrderDetailNum(Integer orderDetailNum) {
		this.orderDetailNum = orderDetailNum;
	}

}
