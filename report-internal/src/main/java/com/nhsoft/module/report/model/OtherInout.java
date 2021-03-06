package com.nhsoft.module.report.model;

import com.nhsoft.module.report.query.State;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * OtherInout generated by hbm2java
 */
@Entity
public class OtherInout implements java.io.Serializable {

	private static final long serialVersionUID = -7625934632344125863L;
	@Id
	private String otherInoutBillNo;
	private String systemBookCode;
	private Integer branchNum;
	private Boolean otherInoutFlag;
	private String otherInoutKind;
	private String otherInoutDepartment;
	private Date otherInoutDate;
	private String otherInoutPaymentType;
	private BigDecimal otherInoutMoney;
	private String otherInoutOperator;
	private String otherInoutRefBill;
	private String otherInoutCreator;
	private Date otherInoutCreateTime;
	private String otherInoutAuditMan;
	private Date otherInoutAuditTime;
	@Embedded
	@AttributeOverrides( {
		 			@AttributeOverride(name="stateCode", column = @Column(name="otherInoutStateCode")), 
		@AttributeOverride(name="stateName", column = @Column(name="otherInoutStateName")) } )
	private State state;
	private String otherInoutMemo;
	private Integer accountBankNum;
	private Boolean otherInoutSyncFlag;
	private Integer otherInoutShiftTableNum;
	private String otherInoutBizday;
	private String otherInoutMachine;
	private Integer supplierNum;
	private Integer innerBranchNum;
	private String clientFid;
	private Boolean otherInoutReadFlag;
	private BigDecimal otherInoutDueMoney;
	private BigDecimal otherInoutDiscountMoney;
	private BigDecimal otherInoutPaidMoney;
	private Date otherInoutLastPaymentDate;

	public OtherInout() {
	}

	public String getOtherInoutBillNo() {
		return otherInoutBillNo;
	}

	public void setOtherInoutBillNo(String otherInoutBillNo) {
		this.otherInoutBillNo = otherInoutBillNo;
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

	public Boolean getOtherInoutFlag() {
		return otherInoutFlag;
	}

	public void setOtherInoutFlag(Boolean otherInoutFlag) {
		this.otherInoutFlag = otherInoutFlag;
	}

	public String getOtherInoutKind() {
		return otherInoutKind;
	}

	public void setOtherInoutKind(String otherInoutKind) {
		this.otherInoutKind = otherInoutKind;
	}

	public String getOtherInoutDepartment() {
		return otherInoutDepartment;
	}

	public void setOtherInoutDepartment(String otherInoutDepartment) {
		this.otherInoutDepartment = otherInoutDepartment;
	}

	public Date getOtherInoutDate() {
		return otherInoutDate;
	}

	public void setOtherInoutDate(Date otherInoutDate) {
		this.otherInoutDate = otherInoutDate;
	}

	public String getOtherInoutPaymentType() {
		return otherInoutPaymentType;
	}

	public void setOtherInoutPaymentType(String otherInoutPaymentType) {
		this.otherInoutPaymentType = otherInoutPaymentType;
	}

	public BigDecimal getOtherInoutMoney() {
		return otherInoutMoney;
	}

	public void setOtherInoutMoney(BigDecimal otherInoutMoney) {
		this.otherInoutMoney = otherInoutMoney;
	}

	public String getOtherInoutOperator() {
		return otherInoutOperator;
	}

	public void setOtherInoutOperator(String otherInoutOperator) {
		this.otherInoutOperator = otherInoutOperator;
	}

	public String getOtherInoutRefBill() {
		return otherInoutRefBill;
	}

	public void setOtherInoutRefBill(String otherInoutRefBill) {
		this.otherInoutRefBill = otherInoutRefBill;
	}

	public String getOtherInoutCreator() {
		return otherInoutCreator;
	}

	public void setOtherInoutCreator(String otherInoutCreator) {
		this.otherInoutCreator = otherInoutCreator;
	}

	public Date getOtherInoutCreateTime() {
		return otherInoutCreateTime;
	}

	public void setOtherInoutCreateTime(Date otherInoutCreateTime) {
		this.otherInoutCreateTime = otherInoutCreateTime;
	}

	public String getOtherInoutAuditMan() {
		return otherInoutAuditMan;
	}

	public void setOtherInoutAuditMan(String otherInoutAuditMan) {
		this.otherInoutAuditMan = otherInoutAuditMan;
	}

	public Date getOtherInoutAuditTime() {
		return otherInoutAuditTime;
	}

	public void setOtherInoutAuditTime(Date otherInoutAuditTime) {
		this.otherInoutAuditTime = otherInoutAuditTime;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getOtherInoutMemo() {
		return otherInoutMemo;
	}

	public void setOtherInoutMemo(String otherInoutMemo) {
		this.otherInoutMemo = otherInoutMemo;
	}

	public Integer getAccountBankNum() {
		return accountBankNum;
	}

	public void setAccountBankNum(Integer accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public Boolean getOtherInoutSyncFlag() {
		return otherInoutSyncFlag;
	}

	public void setOtherInoutSyncFlag(Boolean otherInoutSyncFlag) {
		this.otherInoutSyncFlag = otherInoutSyncFlag;
	}

	public Integer getOtherInoutShiftTableNum() {
		return otherInoutShiftTableNum;
	}

	public void setOtherInoutShiftTableNum(Integer otherInoutShiftTableNum) {
		this.otherInoutShiftTableNum = otherInoutShiftTableNum;
	}

	public String getOtherInoutBizday() {
		return otherInoutBizday;
	}

	public void setOtherInoutBizday(String otherInoutBizday) {
		this.otherInoutBizday = otherInoutBizday;
	}

	public String getOtherInoutMachine() {
		return otherInoutMachine;
	}

	public void setOtherInoutMachine(String otherInoutMachine) {
		this.otherInoutMachine = otherInoutMachine;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public Integer getInnerBranchNum() {
		return innerBranchNum;
	}

	public void setInnerBranchNum(Integer innerBranchNum) {
		this.innerBranchNum = innerBranchNum;
	}

	public String getClientFid() {
		return clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public Boolean getOtherInoutReadFlag() {
		return otherInoutReadFlag;
	}

	public void setOtherInoutReadFlag(Boolean otherInoutReadFlag) {
		this.otherInoutReadFlag = otherInoutReadFlag;
	}

	public BigDecimal getOtherInoutDueMoney() {
		return otherInoutDueMoney;
	}

	public void setOtherInoutDueMoney(BigDecimal otherInoutDueMoney) {
		if(otherInoutDueMoney != null){
			otherInoutDueMoney = otherInoutDueMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.otherInoutDueMoney = otherInoutDueMoney;
	}

	public BigDecimal getOtherInoutDiscountMoney() {
		return otherInoutDiscountMoney;
	}

	public void setOtherInoutDiscountMoney(BigDecimal otherInoutDiscountMoney) {
		if(otherInoutDiscountMoney != null){
			otherInoutDiscountMoney = otherInoutDiscountMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.otherInoutDiscountMoney = otherInoutDiscountMoney;
	}

	public BigDecimal getOtherInoutPaidMoney() {
		return otherInoutPaidMoney;
	}

	public void setOtherInoutPaidMoney(BigDecimal otherInoutPaidMoney) {
		if(otherInoutPaidMoney != null){
			otherInoutPaidMoney = otherInoutPaidMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.otherInoutPaidMoney = otherInoutPaidMoney;
	}

	public Date getOtherInoutLastPaymentDate() {
		return otherInoutLastPaymentDate;
	}

	public void setOtherInoutLastPaymentDate(Date otherInoutLastPaymentDate) {
		this.otherInoutLastPaymentDate = otherInoutLastPaymentDate;
	}

}
