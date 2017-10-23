package com.nhsoft.module.report.dto;

public class WeixinException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 968184768876186668L;
	public WeixinException(String message) {
		super(message);
	}
	
	public WeixinException(String message, Throwable e) {
		super(message, e);
	}
	

}
