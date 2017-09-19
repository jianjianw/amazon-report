package com.nhsoft.report.dto;

import com.nhsoft.unionpay.request.BaseParam;
import com.nhsoft.unionpay.response.BaseResponse;

import java.io.Serializable;

public class UnionPayDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3154971508226794339L;
	private BaseParam param;
	private BaseResponse response;
	public BaseParam getParam() {
		return param;
	}
	public BaseResponse getResponse() {
		return response;
	}
	public void setParam(BaseParam param) {
		this.param = param;
	}
	public void setResponse(BaseResponse response) {
		this.response = response;
	}
	
	
}
