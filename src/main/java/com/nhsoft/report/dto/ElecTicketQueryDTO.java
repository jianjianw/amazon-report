package com.nhsoft.report.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ElecTicketQueryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 382141592277915631L;
	private String systemBookCode;
	private List<Integer> branchNums;
	private Date dtFrom; // 开始时间
	private Date dtTo; // 结束时间
	private String userId; // 收银员
	private String couponType; // 消费券类型
	private String ticketNo; // 消费券号
	private String orderNo;// 结账单号

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public Date getDtFrom() {
		return dtFrom;
	}

	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}

	public Date getDtTo() {
		return dtTo;
	}

	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
