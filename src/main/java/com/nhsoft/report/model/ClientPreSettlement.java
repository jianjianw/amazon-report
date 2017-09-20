package com.nhsoft.report.model;


import com.nhsoft.report.shared.State;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClientPreSettlement entity. @author MyEclipse Persistence Tools
 */

public class ClientPreSettlement implements java.io.Serializable {

	private static final long serialVersionUID = 8658109095728267487L;
	private String preSettlementNo;
	private String systemBookCode;
	private Integer branchNum;
	private String clientFid;
	private String relationPreSettlementNo;
	private String preSettlementAddress;
	private String preSettlementBank;
	private String preSettlementClientName;
	private String preSettlementBankAccount;
	private BigDecimal preSettlementPaid;
	private BigDecimal preSettlementMoney;
	private String preSettlementRefBillNo;
	private String preSettlementCreator;
	private Date preSettlementCreateTime;
	private String preSettlementAuditor;
	private Date preSettlementAuditTime;
	private String preSettlementMemo;
	private Boolean preSettlementSyncFlag;
	private String preSettlementPaymentType;
	private Integer accountBankNum;
	private Date preSettlementDate;
	private State state;
	private Boolean preSettlementSelfFlag;
	
	private List<WholesaleBook> wholesaleBooks = new ArrayList<WholesaleBook>();

	public List<WholesaleBook> getWholesaleBooks() {
		return wholesaleBooks;
	}

	public void setWholesaleBooks(List<WholesaleBook> wholesaleBooks) {
		this.wholesaleBooks = wholesaleBooks;
	}

	public Boolean getPreSettlementSelfFlag() {
		return preSettlementSelfFlag;
	}

	public void setPreSettlementSelfFlag(Boolean preSettlementSelfFlag) {
		this.preSettlementSelfFlag = preSettlementSelfFlag;
	}

	public String getPreSettlementNo() {
		return preSettlementNo;
	}

	public void setPreSettlementNo(String preSettlementNo) {
		this.preSettlementNo = preSettlementNo;
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

	public String getClientFid() {
		return clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public String getRelationPreSettlementNo() {
		return relationPreSettlementNo;
	}

	public void setRelationPreSettlementNo(String relationPreSettlementNo) {
		this.relationPreSettlementNo = relationPreSettlementNo;
	}

	public String getPreSettlementAddress() {
		return preSettlementAddress;
	}

	public void setPreSettlementAddress(String preSettlementAddress) {
		this.preSettlementAddress = preSettlementAddress;
	}

	public String getPreSettlementBank() {
		return preSettlementBank;
	}

	public void setPreSettlementBank(String preSettlementBank) {
		this.preSettlementBank = preSettlementBank;
	}

	public String getPreSettlementClientName() {
		return preSettlementClientName;
	}

	public void setPreSettlementClientName(String preSettlementClientName) {
		this.preSettlementClientName = preSettlementClientName;
	}

	public String getPreSettlementBankAccount() {
		return preSettlementBankAccount;
	}

	public void setPreSettlementBankAccount(String preSettlementBankAccount) {
		this.preSettlementBankAccount = preSettlementBankAccount;
	}

	public BigDecimal getPreSettlementPaid() {
		return preSettlementPaid;
	}

	public void setPreSettlementPaid(BigDecimal preSettlementPaid) {
		if(preSettlementPaid != null){
			preSettlementPaid = preSettlementPaid.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.preSettlementPaid = preSettlementPaid;
	}

	public BigDecimal getPreSettlementMoney() {
		return preSettlementMoney;
	}

	public void setPreSettlementMoney(BigDecimal preSettlementMoney) {
		if(preSettlementMoney != null){
			preSettlementMoney = preSettlementMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.preSettlementMoney = preSettlementMoney;
	}

	public String getPreSettlementRefBillNo() {
		return preSettlementRefBillNo;
	}

	public void setPreSettlementRefBillNo(String preSettlementRefBillNo) {
		this.preSettlementRefBillNo = preSettlementRefBillNo;
	}

	public String getPreSettlementCreator() {
		return preSettlementCreator;
	}

	public void setPreSettlementCreator(String preSettlementCreator) {
		this.preSettlementCreator = preSettlementCreator;
	}

	public Date getPreSettlementCreateTime() {
		return preSettlementCreateTime;
	}

	public void setPreSettlementCreateTime(Date preSettlementCreateTime) {
		this.preSettlementCreateTime = preSettlementCreateTime;
	}

	public String getPreSettlementAuditor() {
		return preSettlementAuditor;
	}

	public void setPreSettlementAuditor(String preSettlementAuditor) {
		this.preSettlementAuditor = preSettlementAuditor;
	}

	public Date getPreSettlementAuditTime() {
		return preSettlementAuditTime;
	}

	public void setPreSettlementAuditTime(Date preSettlementAuditTime) {
		this.preSettlementAuditTime = preSettlementAuditTime;
	}

	public String getPreSettlementMemo() {
		return preSettlementMemo;
	}

	public void setPreSettlementMemo(String preSettlementMemo) {
		this.preSettlementMemo = preSettlementMemo;
	}

	public Boolean getPreSettlementSyncFlag() {
		return preSettlementSyncFlag;
	}

	public void setPreSettlementSyncFlag(Boolean preSettlementSyncFlag) {
		this.preSettlementSyncFlag = preSettlementSyncFlag;
	}

	public String getPreSettlementPaymentType() {
		return preSettlementPaymentType;
	}

	public void setPreSettlementPaymentType(String preSettlementPaymentType) {
		this.preSettlementPaymentType = preSettlementPaymentType;
	}

	public Integer getAccountBankNum() {
		return accountBankNum;
	}

	public void setAccountBankNum(Integer accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public Date getPreSettlementDate() {
		return preSettlementDate;
	}

	public void setPreSettlementDate(Date preSettlementDate) {
		this.preSettlementDate = preSettlementDate;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}