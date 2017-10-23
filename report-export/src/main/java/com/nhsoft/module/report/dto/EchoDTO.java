package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.Date;

public class EchoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -939279114023219660L;
	private Date echoTime;
	private String echoVersion;
	private String echoJdbcUrl;
	private String echoJdbcUserName;
	private boolean isServer = false;
	
	public Date getEchoTime() {
		return echoTime;
	}

	public void setEchoTime(Date echoTime) {
		this.echoTime = echoTime;
	}

	public String getEchoVersion() {
		return echoVersion;
	}

	public void setEchoVersion(String echoVersion) {
		this.echoVersion = echoVersion;
	}

	public String getEchoJdbcUrl() {
		return echoJdbcUrl;
	}

	public void setEchoJdbcUrl(String echoJdbcUrl) {
		this.echoJdbcUrl = echoJdbcUrl;
	}

	public String getEchoJdbcUserName() {
		return echoJdbcUserName;
	}

	public void setEchoJdbcUserName(String echoJdbcUserName) {
		this.echoJdbcUserName = echoJdbcUserName;
	}

	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}



}
