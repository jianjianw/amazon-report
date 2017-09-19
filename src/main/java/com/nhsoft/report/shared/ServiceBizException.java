package com.nhsoft.report.shared;

public class ServiceBizException extends RuntimeException {

	private static final long serialVersionUID = 5606069912575197385L;
	
	public ServiceBizException(String message) {
		super(message, null, true, false);
	}
	
	public ServiceBizException(String message, Throwable e) {
		super(message, e, true, false);
	}

}
