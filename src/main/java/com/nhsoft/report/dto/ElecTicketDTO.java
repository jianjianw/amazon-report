package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ElecTicketDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4787363246397260427L;
	private Integer branchNum;
	private Date paymentDate; // 收款时间
	private String shopUser; // 收银员
	private BigDecimal billMoney; // 账单金额
	private String couponType; // 团购券类型
	private String ticketNos; // 团购券号
	private Integer ticketUseNum; // 团购券使用数量
	private BigDecimal couponMoney; // 团购券实际抵用金额
	private String orderNo;

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getShopUser() {
		return shopUser;
	}

	public void setShopUser(String shopUser) {
		this.shopUser = shopUser;
	}

	public BigDecimal getBillMoney() {
		return billMoney;
	}

	public void setBillMoney(BigDecimal billMoney) {
		this.billMoney = billMoney;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getTicketNos() {
		return ticketNos;
	}

	public void setTicketNos(String ticketNos) {
		this.ticketNos = ticketNos;
	}

	public Integer getTicketUseNum() {
		return ticketUseNum;
	}

	public void setTicketUseNum(Integer ticketUseNum) {
		this.ticketUseNum = ticketUseNum;
	}

	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
