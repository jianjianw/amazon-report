package com.nhsoft.report.dto;

public class APIException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4107007196097854648L;

	public APIException(String message) {
		super(message);
	}
	
	public APIException(String message, Throwable e) {
		super(message, e);
	}
}
