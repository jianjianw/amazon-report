package com.nhsoft.report.model;

import java.io.Serializable;

public class OrderQueue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5002374217343476384L;
	private String orderFid;
	private String orderType;
	
	public OrderQueue(){
		
	}
	
	public OrderQueue(String orderFid, String orderType){
		this.orderFid = orderFid;
		this.orderType = orderType;
	}
	
	public String getOrderFid() {
		return orderFid;
	}

	public void setOrderFid(String orderFid) {
		this.orderFid = orderFid;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	

}
