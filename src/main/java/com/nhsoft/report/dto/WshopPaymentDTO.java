package com.nhsoft.report.dto;

import java.math.BigDecimal;
import java.util.Date;

public class WshopPaymentDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4078381271347205230L;
	private String wshopPaymentId;
	private String wshopPaymentBookCode;
	private String wshopPaymentBookName;
	private String wshopPaymentOperator;
	private Date wshopPaymentOperateTime;
	private String wshopPaymentPaytype;
	private BigDecimal wshopPaymentMoney;
	private Date wshopPaymentOldDeadline;
	private Date wshopPaymentNewDeadline;
	private String wshopPaymentMemo;
	private String wshopPaymentBillNo;
	private Integer wshopPaymentStateCode;
	private String wshopPaymentStateName;
	private String wshopPaymentBillModule;

	public String getWshopPaymentId() {
		return wshopPaymentId;
	}

	public void setWshopPaymentId(String wshopPaymentId) {
		this.wshopPaymentId = wshopPaymentId;
	}

	public String getWshopPaymentBookCode() {
		return wshopPaymentBookCode;
	}

	public void setWshopPaymentBookCode(String wshopPaymentBookCode) {
		this.wshopPaymentBookCode = wshopPaymentBookCode;
	}

	public String getWshopPaymentBookName() {
		return wshopPaymentBookName;
	}

	public void setWshopPaymentBookName(String wshopPaymentBookName) {
		this.wshopPaymentBookName = wshopPaymentBookName;
	}

	public String getWshopPaymentOperator() {
		return wshopPaymentOperator;
	}

	public void setWshopPaymentOperator(String wshopPaymentOperator) {
		this.wshopPaymentOperator = wshopPaymentOperator;
	}

	public Date getWshopPaymentOperateTime() {
		return wshopPaymentOperateTime;
	}

	public void setWshopPaymentOperateTime(Date wshopPaymentOperateTime) {
		this.wshopPaymentOperateTime = wshopPaymentOperateTime;
	}

	public String getWshopPaymentPaytype() {
		return wshopPaymentPaytype;
	}

	public void setWshopPaymentPaytype(String wshopPaymentPaytype) {
		this.wshopPaymentPaytype = wshopPaymentPaytype;
	}

	public BigDecimal getWshopPaymentMoney() {
		return wshopPaymentMoney;
	}

	public void setWshopPaymentMoney(BigDecimal wshopPaymentMoney) {
		this.wshopPaymentMoney = wshopPaymentMoney;
	}

	public Date getWshopPaymentOldDeadline() {
		return wshopPaymentOldDeadline;
	}

	public void setWshopPaymentOldDeadline(Date wshopPaymentOldDeadline) {
		this.wshopPaymentOldDeadline = wshopPaymentOldDeadline;
	}

	public Date getWshopPaymentNewDeadline() {
		return wshopPaymentNewDeadline;
	}

	public void setWshopPaymentNewDeadline(Date wshopPaymentNewDeadline) {
		this.wshopPaymentNewDeadline = wshopPaymentNewDeadline;
	}

	public String getWshopPaymentMemo() {
		return wshopPaymentMemo;
	}

	public void setWshopPaymentMemo(String wshopPaymentMemo) {
		this.wshopPaymentMemo = wshopPaymentMemo;
	}

	public String getWshopPaymentBillNo() {
		return wshopPaymentBillNo;
	}

	public void setWshopPaymentBillNo(String wshopPaymentBillNo) {
		this.wshopPaymentBillNo = wshopPaymentBillNo;
	}

	public Integer getWshopPaymentStateCode() {
		return wshopPaymentStateCode;
	}

	public void setWshopPaymentStateCode(Integer wshopPaymentStateCode) {
		this.wshopPaymentStateCode = wshopPaymentStateCode;
	}

	public String getWshopPaymentStateName() {
		return wshopPaymentStateName;
	}

	public void setWshopPaymentStateName(String wshopPaymentStateName) {
		this.wshopPaymentStateName = wshopPaymentStateName;
	}

	public String getWshopPaymentBillModule() {
		return wshopPaymentBillModule;
	}

	public void setWshopPaymentBillModule(String wshopPaymentBillModule) {
		this.wshopPaymentBillModule = wshopPaymentBillModule;
	}

}
