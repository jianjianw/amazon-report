package com.nhsoft.module.report.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * AlipayLog generated by hbm2java
 */
@Entity
public class AlipayLog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2749559624149591086L;
	@Id
	private String alipayLogId;
	private String systemBookCode;
	private Integer branchNum;
	private String alipayLogOrderNo;
	private String alipayLogType;
	private boolean alipayLogTradeState;
	private String alipayLogTradeNo;
	private String alipayLogTradePartner;
	private String alipayLogTradeResult;
	private Date alipayLogStart;
	private Date alipayLogEnd;
	private String alipayLogOperator;
	private String alipayLogContext;
	private String alipayLogSellerId;
	private String alipayLogBuyerId;
	private BigDecimal alipayLogMoney;
	private BigDecimal alipayLogBuyerMoney;
	private BigDecimal alipayLogReceiptMoney;
	private Boolean alipayLogTradeValid;
	
	public AlipayLog() {
	}

	public String getAlipayLogId() {
		return this.alipayLogId;
	}

	public void setAlipayLogId(String alipayLogId) {
		this.alipayLogId = alipayLogId;
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

	public String getAlipayLogOrderNo() {
		return this.alipayLogOrderNo;
	}

	public void setAlipayLogOrderNo(String alipayLogOrderNo) {
		this.alipayLogOrderNo = alipayLogOrderNo;
	}

	public String getAlipayLogType() {
		return this.alipayLogType;
	}

	public void setAlipayLogType(String alipayLogType) {
		this.alipayLogType = alipayLogType;
	}

	public boolean isAlipayLogTradeState() {
		return this.alipayLogTradeState;
	}

	public void setAlipayLogTradeState(boolean alipayLogTradeState) {
		this.alipayLogTradeState = alipayLogTradeState;
	}

	public String getAlipayLogTradeNo() {
		return this.alipayLogTradeNo;
	}

	public void setAlipayLogTradeNo(String alipayLogTradeNo) {
		this.alipayLogTradeNo = alipayLogTradeNo;
	}

	public String getAlipayLogTradePartner() {
		return this.alipayLogTradePartner;
	}

	public void setAlipayLogTradePartner(String alipayLogTradePartner) {
		this.alipayLogTradePartner = alipayLogTradePartner;
	}

	public String getAlipayLogTradeResult() {
		return this.alipayLogTradeResult;
	}

	public void setAlipayLogTradeResult(String alipayLogTradeResult) {
		this.alipayLogTradeResult = alipayLogTradeResult;
	}

	public Date getAlipayLogStart() {
		return this.alipayLogStart;
	}

	public void setAlipayLogStart(Date alipayLogStart) {
		this.alipayLogStart = alipayLogStart;
	}

	public Date getAlipayLogEnd() {
		return this.alipayLogEnd;
	}

	public void setAlipayLogEnd(Date alipayLogEnd) {
		this.alipayLogEnd = alipayLogEnd;
	}

	public String getAlipayLogOperator() {
		return this.alipayLogOperator;
	}

	public void setAlipayLogOperator(String alipayLogOperator) {
		this.alipayLogOperator = alipayLogOperator;
	}

	public String getAlipayLogContext() {
		return this.alipayLogContext;
	}

	public void setAlipayLogContext(String alipayLogContext) {
		this.alipayLogContext = alipayLogContext;
	}

	public String getAlipayLogSellerId() {
		return alipayLogSellerId;
	}

	public void setAlipayLogSellerId(String alipayLogSellerId) {
		this.alipayLogSellerId = alipayLogSellerId;
	}

	public String getAlipayLogBuyerId() {
		return alipayLogBuyerId;
	}

	public void setAlipayLogBuyerId(String alipayLogBuyerId) {
		this.alipayLogBuyerId = alipayLogBuyerId;
	}

	public BigDecimal getAlipayLogMoney() {
		return alipayLogMoney;
	}

	public void setAlipayLogMoney(BigDecimal alipayLogMoney) {
		this.alipayLogMoney = alipayLogMoney;
	}

	public BigDecimal getAlipayLogBuyerMoney() {
		return alipayLogBuyerMoney;
	}

	public void setAlipayLogBuyerMoney(BigDecimal alipayLogBuyerMoney) {
		this.alipayLogBuyerMoney = alipayLogBuyerMoney;
	}

	public BigDecimal getAlipayLogReceiptMoney() {
		return alipayLogReceiptMoney;
	}

	public void setAlipayLogReceiptMoney(BigDecimal alipayLogReceiptMoney) {
		this.alipayLogReceiptMoney = alipayLogReceiptMoney;
	}

	public Boolean getAlipayLogTradeValid() {
		return alipayLogTradeValid;
	}

	public void setAlipayLogTradeValid(Boolean alipayLogTradeValid) {
		this.alipayLogTradeValid = alipayLogTradeValid;
	}

}