package com.nhsoft.report.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * RelatCard generated by hbm2java
 */
@Entity
public class RelatCard implements java.io.Serializable {

	private static final long serialVersionUID = 172705076540392826L;
	@Id
	private String relatCardFid;
	private String systemBookCode;
	private Integer branchNum;
	private Integer shiftTableNum;
	private String shiftTableBizday;
	private Integer relatCardCustNum;
	private String relatCardPrintedNum;
	private String relatCardPhysicalNum;
	private Date relatCardOldDeadline;
	private Date relatCardNewDeadline;
	private String relatCardOperator;
	private Integer relatCardPaymentType;
	private String relatCardPaymentTypeName;
	private String relatCardBankName;
	private String relatCardBillref;
	private BigDecimal relatCardMoney;
	private String relatCardMemo;
	private Date relatCardOperateTime;
	private String relatCardBranchName;
	private Integer relatCardCardType;
	private String relatCardCustName;
	private Integer accountBankNum;
	private String relatCardMachine;

	public RelatCard() {
	}

	public String getRelatCardFid() {
		return this.relatCardFid;
	}

	public void setRelatCardFid(String relatCardFid) {
		this.relatCardFid = relatCardFid;
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

	public Integer getShiftTableNum() {
		return this.shiftTableNum;
	}

	public void setShiftTableNum(Integer shiftTableNum) {
		this.shiftTableNum = shiftTableNum;
	}

	public String getShiftTableBizday() {
		return this.shiftTableBizday;
	}

	public void setShiftTableBizday(String shiftTableBizday) {
		this.shiftTableBizday = shiftTableBizday;
	}

	public Integer getRelatCardCustNum() {
		return this.relatCardCustNum;
	}

	public void setRelatCardCustNum(Integer relatCardCustNum) {
		this.relatCardCustNum = relatCardCustNum;
	}

	public String getRelatCardPrintedNum() {
		return this.relatCardPrintedNum;
	}

	public void setRelatCardPrintedNum(String relatCardPrintedNum) {
		this.relatCardPrintedNum = relatCardPrintedNum;
	}

	public String getRelatCardPhysicalNum() {
		return this.relatCardPhysicalNum;
	}

	public void setRelatCardPhysicalNum(String relatCardPhysicalNum) {
		this.relatCardPhysicalNum = relatCardPhysicalNum;
	}

	public Date getRelatCardOldDeadline() {
		return this.relatCardOldDeadline;
	}

	public void setRelatCardOldDeadline(Date relatCardOldDeadline) {
		this.relatCardOldDeadline = relatCardOldDeadline;
	}

	public Date getRelatCardNewDeadline() {
		return this.relatCardNewDeadline;
	}

	public void setRelatCardNewDeadline(Date relatCardNewDeadline) {
		this.relatCardNewDeadline = relatCardNewDeadline;
	}

	public String getRelatCardOperator() {
		return this.relatCardOperator;
	}

	public void setRelatCardOperator(String relatCardOperator) {
		this.relatCardOperator = relatCardOperator;
	}

	public Integer getRelatCardPaymentType() {
		return this.relatCardPaymentType;
	}

	public void setRelatCardPaymentType(Integer relatCardPaymentType) {
		this.relatCardPaymentType = relatCardPaymentType;
	}

	public String getRelatCardPaymentTypeName() {
		return this.relatCardPaymentTypeName;
	}

	public void setRelatCardPaymentTypeName(String relatCardPaymentTypeName) {
		this.relatCardPaymentTypeName = relatCardPaymentTypeName;
	}

	public String getRelatCardBankName() {
		return this.relatCardBankName;
	}

	public void setRelatCardBankName(String relatCardBankName) {
		this.relatCardBankName = relatCardBankName;
	}

	public String getRelatCardBillref() {
		return this.relatCardBillref;
	}

	public void setRelatCardBillref(String relatCardBillref) {
		this.relatCardBillref = relatCardBillref;
	}

	public BigDecimal getRelatCardMoney() {
		return this.relatCardMoney;
	}

	public void setRelatCardMoney(BigDecimal relatCardMoney) {
		this.relatCardMoney = relatCardMoney;
	}

	public String getRelatCardMemo() {
		return this.relatCardMemo;
	}

	public void setRelatCardMemo(String relatCardMemo) {
		this.relatCardMemo = relatCardMemo;
	}

	public Date getRelatCardOperateTime() {
		return this.relatCardOperateTime;
	}

	public void setRelatCardOperateTime(Date relatCardOperateTime) {
		this.relatCardOperateTime = relatCardOperateTime;
	}

	public String getRelatCardBranchName() {
		return this.relatCardBranchName;
	}

	public void setRelatCardBranchName(String relatCardBranchName) {
		this.relatCardBranchName = relatCardBranchName;
	}

	public Integer getRelatCardCardType() {
		return this.relatCardCardType;
	}

	public void setRelatCardCardType(Integer relatCardCardType) {
		this.relatCardCardType = relatCardCardType;
	}

	public String getRelatCardCustName() {
		return this.relatCardCustName;
	}

	public void setRelatCardCustName(String relatCardCustName) {
		this.relatCardCustName = relatCardCustName;
	}

	public Integer getAccountBankNum() {
		return this.accountBankNum;
	}

	public void setAccountBankNum(Integer accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public String getRelatCardMachine() {
		return this.relatCardMachine;
	}

	public void setRelatCardMachine(String relatCardMachine) {
		this.relatCardMachine = relatCardMachine;
	}

}
