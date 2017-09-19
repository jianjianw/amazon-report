package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierRemind implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4471083038014473571L;
	
	private String remindType;//类型（主题）
	private String remindFid;//单据号
	private String branchName;//来源（单据的对应门店名称）
	private Integer branchNum;//来源（单据的对应门店编号）
	private Date remindData;//操作时间
	private BigDecimal remindMoney;//单据金额
	private Boolean isRead;//是否已读
	
	public String getRemindType() {
		return remindType;
	}
	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}
	public String getRemindFid() {
		return remindFid;
	}
	public void setRemindFid(String remindFid) {
		this.remindFid = remindFid;
	}
	public Date getRemindData() {
		return remindData;
	}
	public void setRemindData(Date remindData) {
		this.remindData = remindData;
	}
	public BigDecimal getRemindMoney() {
		return remindMoney;
	}
	public void setRemindMoney(BigDecimal remindMoney) {
		this.remindMoney = remindMoney;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public Integer getBranchNum() {
		return branchNum;
	}
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((remindFid == null) ? 0 : remindFid.hashCode());
		result = prime * result
				+ ((remindType == null) ? 0 : remindType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplierRemind other = (SupplierRemind) obj;
		if (remindFid == null) {
			if (other.remindFid != null)
				return false;
		} else if (!remindFid.equals(other.remindFid))
			return false;
		if (remindType == null) {
			if (other.remindType != null)
				return false;
		} else if (!remindType.equals(other.remindType))
			return false;
		return true;
	}
	
}
