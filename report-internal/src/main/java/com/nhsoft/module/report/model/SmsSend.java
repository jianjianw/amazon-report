package com.nhsoft.module.report.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * SmsSend entity. @author MyEclipse Persistence Tools
 */

public class SmsSend implements java.io.Serializable {
	
	public static int SMS_TYPE_SALE = 0;//营销类
	public static int SMS_TYPE_NOTICE = 1;//通知类
	

	private static final long serialVersionUID = 3537017856770433484L;
	private String smsSendId;
	private String systemBookCode;
	private Integer branchNum;
	private String smsSendNumber;
	private String smsSendCustomer;
	private String smsSendContext;
	private String smsSendGroup;
	private Date smsSendDate;
	private Integer smsSendState;
	private String smsSendOperator;
	private Date smsSendOperateTime;
	private String smsSendUuid;
	private String smsSendDeliveryStatusCode;
	private String smsSendDeliveryStatus;
	private Integer smsSendCount;	
	private Integer smsSendType = SMS_TYPE_SALE;
	

	public Integer getSmsSendType() {
		return smsSendType;
	}

	public void setSmsSendType(Integer smsSendType) {
		this.smsSendType = smsSendType;
	}

	public Integer getSmsSendCount() {
		return smsSendCount;
	}

	public void setSmsSendCount(Integer smsSendCount) {
		this.smsSendCount = smsSendCount;
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

	public Date getSmsSendDate() {
		return smsSendDate;
	}

	public void setSmsSendDate(Date smsSendDate) {
		this.smsSendDate = smsSendDate;
	}

	public Date getSmsSendOperateTime() {
		return smsSendOperateTime;
	}

	public void setSmsSendOperateTime(Date smsSendOperateTime) {
		this.smsSendOperateTime = smsSendOperateTime;
	}

	public String getSmsSendId() {
		return this.smsSendId;
	}

	public void setSmsSendId(String smsSendId) {
		this.smsSendId = smsSendId;
	}

	public String getSmsSendNumber() {
		return this.smsSendNumber;
	}

	public void setSmsSendNumber(String smsSendNumber) {
		this.smsSendNumber = smsSendNumber;
	}

	public String getSmsSendCustomer() {
		return this.smsSendCustomer;
	}

	public void setSmsSendCustomer(String smsSendCustomer) {
		this.smsSendCustomer = smsSendCustomer;
	}

	public String getSmsSendContext() {
		return this.smsSendContext;
	}

	public void setSmsSendContext(String smsSendContext) {
		this.smsSendContext = smsSendContext;
	}

	public String getSmsSendGroup() {
		return this.smsSendGroup;
	}

	public void setSmsSendGroup(String smsSendGroup) {
		this.smsSendGroup = smsSendGroup;
	}

	public Integer getSmsSendState() {
		return this.smsSendState;
	}

	public void setSmsSendState(Integer smsSendState) {
		this.smsSendState = smsSendState;
	}

	public String getSmsSendOperator() {
		return this.smsSendOperator;
	}

	public void setSmsSendOperator(String smsSendOperator) {
		this.smsSendOperator = smsSendOperator;
	}

	public void setSmsSendOperateTime(Timestamp smsSendOperateTime) {
		this.smsSendOperateTime = smsSendOperateTime;
	}

	public void setSmsSendUuid(String smsSendUuid) {
		this.smsSendUuid = smsSendUuid;
	}

	public void setSmsSendDeliveryStatusCode(String smsSendDeliveryStatusCode) {
		this.smsSendDeliveryStatusCode = smsSendDeliveryStatusCode;
	}

	public void setSmsSendDeliveryStatus(String smsSendDeliveryStatus) {
		this.smsSendDeliveryStatus = smsSendDeliveryStatus;
	}

	public String getSmsSendUuid() {
		return smsSendUuid;
	}

	public String getSmsSendDeliveryStatusCode() {
		return smsSendDeliveryStatusCode;
	}

	public String getSmsSendDeliveryStatus() {
		return smsSendDeliveryStatus;
	}

}