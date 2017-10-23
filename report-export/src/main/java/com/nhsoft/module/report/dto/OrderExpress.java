package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class OrderExpress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1829570171303131742L;
	private String expressId;
	private String expressName;
	
	public OrderExpress(){
		
	}
	
	public OrderExpress(String expressId, String expressName){
		this.expressId = expressId;
		this.expressName = expressName;
	}
	
	public OrderExpress(Integer expressId, String expressName){
		this.expressId = expressId.toString();
		this.expressName = expressName;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

}
