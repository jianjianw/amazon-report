package com.nhsoft.report.model;

import java.io.Serializable;
import java.util.Date;

public class ReceiveOrderMatrix implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3343170726390633691L;
	private String receiveOrderFid;
	private String systemBookCode;
	private Integer branchNum;
	private Date receiveOrderInvoiceDate;
	private Boolean receiveOrderInvoiceFlag;
	
	//临时属性
	private String receiveOrderInvoiceNo;

	public String getReceiveOrderInvoiceNo() {
		return receiveOrderInvoiceNo;
	}

	public void setReceiveOrderInvoiceNo(String receiveOrderInvoiceNo) {
		this.receiveOrderInvoiceNo = receiveOrderInvoiceNo;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getReceiveOrderFid() {
		return receiveOrderFid;
	}

	public Date getReceiveOrderInvoiceDate() {
		return receiveOrderInvoiceDate;
	}

	public void setReceiveOrderInvoiceDate(Date receiveOrderInvoiceDate) {
		this.receiveOrderInvoiceDate = receiveOrderInvoiceDate;
	}

	public Boolean getReceiveOrderInvoiceFlag() {
		return receiveOrderInvoiceFlag;
	}

	public void setReceiveOrderInvoiceFlag(Boolean receiveOrderInvoiceFlag) {
		this.receiveOrderInvoiceFlag = receiveOrderInvoiceFlag;
	}

	public void setReceiveOrderFid(String receiveOrderFid) {
		this.receiveOrderFid = receiveOrderFid;
	}


}
