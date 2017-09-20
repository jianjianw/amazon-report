package com.nhsoft.report.dto;



public class UnionPayPayDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2245730464979614304L;
	private PayParam param;
	private PayResponse response;
	
	public PayParam getParam() {
		return param;
	}
	public PayResponse getResponse() {
		return response;
	}
	public void setParam(PayParam param) {
		this.param = param;
	}
	public void setResponse(PayResponse response) {
		this.response = response;
	}
	
}
