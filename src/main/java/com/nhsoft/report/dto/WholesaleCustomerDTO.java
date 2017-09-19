package com.nhsoft.report.dto;

import java.io.Serializable;

public class WholesaleCustomerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4137481286693544681L;
	private String clientFid;
	private String clientCode;
	private String clientName;
	private String clientType;
	private String clientPhone;

	public String getClientFid() {
		return clientFid;
	}

	public String getClientCode() {
		return clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientType() {
		return clientType;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

}
