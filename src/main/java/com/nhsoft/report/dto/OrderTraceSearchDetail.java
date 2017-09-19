package com.nhsoft.report.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderTraceSearchDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3172787155635209044L;
	private Date detailStatusTime;
	private String detailStatusDesc;

	public Date getDetailStatusTime() {
		return detailStatusTime;
	}

	public void setDetailStatusTime(Date detailStatusTime) {
		this.detailStatusTime = detailStatusTime;
	}

	public String getDetailStatusDesc() {
		return detailStatusDesc;
	}

	public void setDetailStatusDesc(String detailStatusDesc) {
		this.detailStatusDesc = detailStatusDesc;
	}

}
