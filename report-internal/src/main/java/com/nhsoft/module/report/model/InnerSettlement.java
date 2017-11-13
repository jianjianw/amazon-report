package com.nhsoft.module.report.model;


import com.nhsoft.module.report.query.State;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * InnerSettlement generated by hbm2java
 */
@Entity
public class InnerSettlement implements java.io.Serializable {

	private static final long serialVersionUID = -8232930863337544685L;
	@Id
	private String innerSettlementNo;
	private String systemBookCode;
	private Integer centerBranchNum;
	private Integer branchNum;
	private Date innerSettlementDate;
	private String innerSettlementPaymentType;
	private String innerSettlementMemo;
	private String innerSettlementCreator;
	private Date innerSettlementCreateTime;
	private String innerSettlementAuditor;
	private Date innerSettlementAuditTime;
	@Embedded
	@AttributeOverrides( {
		 			@AttributeOverride(name="stateCode", column = @Column(name="innerSettlementStateCode")), 
		@AttributeOverride(name="stateName", column = @Column(name="innerSettlementStateName")) } )
	private State state;
	private String innerSettlementRefBillNo;
	private String innerSettlementAccountNo;
	private String innerSettlementBankName;
	private BigDecimal innerSettlementTotalMoney;
	private BigDecimal innerSettlementTotalDiscount;
	private Integer accountBankNum;
	private Boolean innerSettlementSynchFlag;
	private BigDecimal innerSettlementInvoiceMoney;
	private String innerSettlementInvoiceNo;
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "innerSettlementNo", updatable=false, insertable=false)
	private List<InnerSettlementDetail> innerSettlementDetails = new ArrayList<InnerSettlementDetail>(0);
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "innerSettlementNo", updatable=false, insertable=false)
	private List<InnerPreSettlementDetail> innerPreSettlementDetails = new ArrayList<InnerPreSettlementDetail>();

	public InnerSettlement() {
	}
	
	public BigDecimal getInnerSettlementInvoiceMoney() {
		return innerSettlementInvoiceMoney;
	}
	
	public void setInnerSettlementInvoiceMoney(BigDecimal innerSettlementInvoiceMoney) {
		this.innerSettlementInvoiceMoney = innerSettlementInvoiceMoney;
	}
	
	public String getInnerSettlementInvoiceNo() {
		return innerSettlementInvoiceNo;
	}
	
	public void setInnerSettlementInvoiceNo(String innerSettlementInvoiceNo) {
		this.innerSettlementInvoiceNo = innerSettlementInvoiceNo;
	}
	
	public Boolean getInnerSettlementSynchFlag() {
		return innerSettlementSynchFlag;
	}

	public void setInnerSettlementSynchFlag(Boolean innerSettlementSynchFlag) {
		this.innerSettlementSynchFlag = innerSettlementSynchFlag;
	}

	public String getInnerSettlementNo() {
		return this.innerSettlementNo;
	}

	public void setInnerSettlementNo(String innerSettlementNo) {
		this.innerSettlementNo = innerSettlementNo;
	}

	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return this.branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Date getInnerSettlementDate() {
		return this.innerSettlementDate;
	}

	public void setInnerSettlementDate(Date innerSettlementDate) {
		this.innerSettlementDate = innerSettlementDate;
	}

	public String getInnerSettlementPaymentType() {
		return this.innerSettlementPaymentType;
	}

	public void setInnerSettlementPaymentType(String innerSettlementPaymentType) {
		this.innerSettlementPaymentType = innerSettlementPaymentType;
	}

	public String getInnerSettlementMemo() {
		return this.innerSettlementMemo;
	}

	public void setInnerSettlementMemo(String innerSettlementMemo) {
		this.innerSettlementMemo = innerSettlementMemo;
	}

	public String getInnerSettlementCreator() {
		return this.innerSettlementCreator;
	}

	public void setInnerSettlementCreator(String innerSettlementCreator) {
		this.innerSettlementCreator = innerSettlementCreator;
	}

	public Date getInnerSettlementCreateTime() {
		return this.innerSettlementCreateTime;
	}

	public void setInnerSettlementCreateTime(Date innerSettlementCreateTime) {
		this.innerSettlementCreateTime = innerSettlementCreateTime;
	}

	public String getInnerSettlementAuditor() {
		return this.innerSettlementAuditor;
	}

	public void setInnerSettlementAuditor(String innerSettlementAuditor) {
		this.innerSettlementAuditor = innerSettlementAuditor;
	}

	public Date getInnerSettlementAuditTime() {
		return this.innerSettlementAuditTime;
	}

	public void setInnerSettlementAuditTime(Date innerSettlementAuditTime) {
		this.innerSettlementAuditTime = innerSettlementAuditTime;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getInnerSettlementRefBillNo() {
		return this.innerSettlementRefBillNo;
	}

	public void setInnerSettlementRefBillNo(String innerSettlementRefBillNo) {
		this.innerSettlementRefBillNo = innerSettlementRefBillNo;
	}

	public String getInnerSettlementAccountNo() {
		return this.innerSettlementAccountNo;
	}

	public void setInnerSettlementAccountNo(String innerSettlementAccountNo) {
		this.innerSettlementAccountNo = innerSettlementAccountNo;
	}

	public String getInnerSettlementBankName() {
		return this.innerSettlementBankName;
	}

	public void setInnerSettlementBankName(String innerSettlementBankName) {
		this.innerSettlementBankName = innerSettlementBankName;
	}

	public BigDecimal getInnerSettlementTotalMoney() {
		return this.innerSettlementTotalMoney;
	}

	public void setInnerSettlementTotalMoney(
			BigDecimal innerSettlementTotalMoney) {
		this.innerSettlementTotalMoney = innerSettlementTotalMoney;
	}

	public BigDecimal getInnerSettlementTotalDiscount() {
		return this.innerSettlementTotalDiscount;
	}

	public void setInnerSettlementTotalDiscount(
			BigDecimal innerSettlementTotalDiscount) {
		this.innerSettlementTotalDiscount = innerSettlementTotalDiscount;
	}

	public Integer getAccountBankNum() {
		return this.accountBankNum;
	}

	public void setAccountBankNum(Integer accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public List<InnerSettlementDetail> getInnerSettlementDetails() {
		return innerSettlementDetails;
	}

	public void setInnerSettlementDetails(
			List<InnerSettlementDetail> innerSettlementDetails) {
		this.innerSettlementDetails = innerSettlementDetails;
	}

	public List<InnerPreSettlementDetail> getInnerPreSettlementDetails() {
		return innerPreSettlementDetails;
	}

	public void setInnerPreSettlementDetails(
			List<InnerPreSettlementDetail> innerPreSettlementDetails) {
		this.innerPreSettlementDetails = innerPreSettlementDetails;
	}

	public Integer getCenterBranchNum() {
		return centerBranchNum;
	}

	public void setCenterBranchNum(Integer centerBranchNum) {
		this.centerBranchNum = centerBranchNum;
	}

	public boolean checkMoney(){
		if(innerPreSettlementDetails.size() == 0){
			return false;
		}
		BigDecimal preMoney = BigDecimal.ZERO;
		BigDecimal detailMoney = BigDecimal.ZERO;
		for(int i = 0;i < innerPreSettlementDetails.size();i++){
			InnerPreSettlementDetail innerPreSettlementDetail = innerPreSettlementDetails.get(i);
			preMoney = preMoney.add(innerPreSettlementDetail.getPreSettlementDetailMoney().setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
		for(int i = 0;i < innerSettlementDetails.size();i++){
			InnerSettlementDetail innerSettlementDetail = innerSettlementDetails.get(i);
			detailMoney = detailMoney.add(innerSettlementDetail.getInnerSettlementDetailMoney().setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		if(preMoney.compareTo(detailMoney) != 0){
			return true;
		}		
		return false;
	}
}
