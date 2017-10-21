package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderTraceSearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2292713915939898382L;
	private String orderDeliverNo;
	private String orderDeliverCompanyName;
	private String orderNo;
	private String orderDeliverStatus;
	private List<OrderTraceSearchDetail> orderTraceSearchDetails = new ArrayList<OrderTraceSearchDetail>();

	public String getOrderDeliverNo() {
		return orderDeliverNo;
	}

	public void setOrderDeliverNo(String orderDeliverNo) {
		this.orderDeliverNo = orderDeliverNo;
	}

	public String getOrderDeliverCompanyName() {
		return orderDeliverCompanyName;
	}

	public void setOrderDeliverCompanyName(String orderDeliverCompanyName) {
		this.orderDeliverCompanyName = orderDeliverCompanyName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDeliverStatus() {
		return orderDeliverStatus;
	}

	public void setOrderDeliverStatus(String orderDeliverStatus) {
		this.orderDeliverStatus = orderDeliverStatus;
	}

	public List<OrderTraceSearchDetail> getOrderTraceSearchDetails() {
		return orderTraceSearchDetails;
	}

	public void setOrderTraceSearchDetails(List<OrderTraceSearchDetail> orderTraceSearchDetails) {
		this.orderTraceSearchDetails = orderTraceSearchDetails;
	}

}
