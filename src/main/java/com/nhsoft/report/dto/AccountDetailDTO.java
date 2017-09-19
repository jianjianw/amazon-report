package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6787230944924866537L;
	private Integer branchNum;
	private String branchName;
	private String accountFid; 
	private Date accountDate; 
	private String accountMemo;
	private String accountType;
	private BigDecimal accountAddMoney; //	
	private BigDecimal accountDelMoney; //
	private BigDecimal accountTotalMoney;
	private BigDecimal accountDicountMoney;
	
	public AccountDetailDTO(){
		accountAddMoney = BigDecimal.ZERO;
		accountDelMoney = BigDecimal.ZERO;
		accountTotalMoney = BigDecimal.ZERO;
		accountDicountMoney = BigDecimal.ZERO;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public BigDecimal getAccountTotalMoney() {
		return accountTotalMoney;
	}

	public void setAccountTotalMoney(BigDecimal accountTotalMoney) {
		this.accountTotalMoney = accountTotalMoney;
	}

	public BigDecimal getAccountDicountMoney() {
		return accountDicountMoney;
	}

	public void setAccountDicountMoney(BigDecimal accountDicountMoney) {
		this.accountDicountMoney = accountDicountMoney;
	}
	
	
}
