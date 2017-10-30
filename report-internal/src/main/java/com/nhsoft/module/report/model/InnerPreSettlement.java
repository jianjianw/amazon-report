package com.nhsoft.module.report.model;

import com.nhsoft.module.report.query.State;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class InnerPreSettlement implements java.io.Serializable {

	private static final long serialVersionUID = 3247515015167079736L;
	@Id
	private String preSettlementNo;
	private String systemBookCode;
	private Integer centerBranchNum;
	private Integer branchNum;
	private String relationPreSettlementNo;
	private Date preSettlementDate;
	private String preSettlementPaymentType;
	private BigDecimal preSettlementPaid;
	private BigDecimal preSettlementMoney;
	private String preSettlementMemo;
	private String preSettlementCreator;
	private Date preSettlementCreateTime;
	private String preSettlementAuditor;
	private Date preSettlementAuditTime;
	@Embedded
	@AttributeOverrides( {
		 			@AttributeOverride(name="stateCode", column = @Column(name="preSettlementStateCode")), 
		@AttributeOverride(name="stateName", column = @Column(name="preSettlementStateName")) } )
	private State state;
	private String preSettlementRefBillNo;
	private String preSettlementAccountNo;
	private String preSettlementBankName;
	private Integer accountBankNum;
	private Boolean preSettlementOnlineFlag;
	private Boolean preSettlementSelfFlag;
	private Boolean preSettlementSynchFlag;
	
	@ManyToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name="InnerSelfSettlementDetail", joinColumns={@JoinColumn(name="preSettlementNo")}, inverseJoinColumns={@JoinColumn(name="requestOrderFid", referencedColumnName="requestOrderFid")})
	private List<RequestOrder> requestOrders = new ArrayList<RequestOrder>();

	public Boolean getPreSettlementSynchFlag() {
		return preSettlementSynchFlag;
	}

	public void setPreSettlementSynchFlag(Boolean preSettlementSynchFlag) {
		this.preSettlementSynchFlag = preSettlementSynchFlag;
	}

	public List<RequestOrder> getRequestOrders() {
		return requestOrders;
	}

	public void setRequestOrders(List<RequestOrder> requestOrders) {
		this.requestOrders = requestOrders;
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

	public Integer getCenterBranchNum() {
		return centerBranchNum;
	}

	public void setCenterBranchNum(Integer centerBranchNum) {
		this.centerBranchNum = centerBranchNum;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getRelationPreSettlementNo() {
		return relationPreSettlementNo;
	}

	public void setRelationPreSettlementNo(String relationPreSettlementNo) {
		this.relationPreSettlementNo = relationPreSettlementNo;
	}

	public Date getPreSettlementDate() {
		return preSettlementDate;
	}

	public void setPreSettlementDate(Date preSettlementDate) {
		this.preSettlementDate = preSettlementDate;
	}

	public String getPreSettlementPaymentType() {
		return preSettlementPaymentType;
	}

	public void setPreSettlementPaymentType(String preSettlementPaymentType) {
		this.preSettlementPaymentType = preSettlementPaymentType;
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

	public String getPreSettlementMemo() {
		return preSettlementMemo;
	}

	public void setPreSettlementMemo(String preSettlementMemo) {
		this.preSettlementMemo = preSettlementMemo;
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

    public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getPreSettlementRefBillNo() {
		return preSettlementRefBillNo;
	}

	public void setPreSettlementRefBillNo(String preSettlementRefBillNo) {
		this.preSettlementRefBillNo = preSettlementRefBillNo;
	}

	public String getPreSettlementAccountNo() {
		return preSettlementAccountNo;
	}

	public void setPreSettlementAccountNo(String preSettlementAccountNo) {
		this.preSettlementAccountNo = preSettlementAccountNo;
	}

	public String getPreSettlementBankName() {
		return preSettlementBankName;
	}

	public void setPreSettlementBankName(String preSettlementBankName) {
		this.preSettlementBankName = preSettlementBankName;
	}

	public Integer getAccountBankNum() {
		return accountBankNum;
	}

	public void setAccountBankNum(Integer accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public Boolean getPreSettlementOnlineFlag() {
		return preSettlementOnlineFlag;
	}

	public void setPreSettlementOnlineFlag(Boolean preSettlementOnlineFlag) {
		this.preSettlementOnlineFlag = preSettlementOnlineFlag;
	}

}