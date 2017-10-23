package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierAccountDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7114238194403711382L;
	private Integer supplierNum;
	private Integer branchNum;
	private String accountFid; 
	private Date accountDate; 
	private String accountMemo;
	private String accountType;
	private BigDecimal accountAddMoney; //	
	private BigDecimal accountDelMoney; //
	private BigDecimal accountTotalMoney;
	private BigDecimal accountDicountMoney;
	private Date paidDate;
	private Date lastPaidDate;
	
	public SupplierAccountDetail(){
		accountAddMoney = BigDecimal.ZERO;
		accountDelMoney = BigDecimal.ZERO;
	}
	
	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public String getAccountFid() {
		return accountFid;
	}

	public void setAccountFid(String accountFid) {
		this.accountFid = accountFid;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public String getAccountMemo() {
		return accountMemo;
	}

	public void setAccountMemo(String accountMemo) {
		this.accountMemo = accountMemo;
	}

	public BigDecimal getAccountTotalMoney() {
		return accountTotalMoney;
	}

	public void setAccountTotalMoney(BigDecimal accountTotalMoney) {
		this.accountTotalMoney = accountTotalMoney;
	}

	
	public BigDecimal getAccountAddMoney() {
		return accountAddMoney;
	}

	public void setAccountAddMoney(BigDecimal accountAddMoney) {
		this.accountAddMoney = accountAddMoney;
	}

	public BigDecimal getAccountDelMoney() {
		return accountDelMoney;
	}

	public void setAccountDelMoney(BigDecimal accountDelMoney) {
		this.accountDelMoney = accountDelMoney;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getAccountDicountMoney() {
		return accountDicountMoney;
	}

	public void setAccountDicountMoney(BigDecimal accountDicountMoney) {
		this.accountDicountMoney = accountDicountMoney;
	}

	public Date getPaidTimeDate() {
		return paidDate;
	}

	public void setPaidTimeDate(Date paidTimeDate) {
		this.paidDate = paidTimeDate;
	}

	public Date getLastPaidDate() {
		return lastPaidDate;
	}

	public void setLastPaidDate(Date lastPaidDate) {
		this.lastPaidDate = lastPaidDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountFid == null) ? 0 : accountFid.hashCode());
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
		SupplierAccountDetail other = (SupplierAccountDetail) obj;
		if (accountFid == null) {
			if (other.accountFid != null)
				return false;
		} else if (!accountFid.equals(other.accountFid))
			return false;
		return true;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
}
