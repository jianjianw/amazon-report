package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerAnalysisDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1035985260346977921L;
	private Integer branchNum;//分店编号
	private String branchName;//分店名称
	private String shiftTableDate;// 营业日
	private BigDecimal totalMoney;// 总金额
	private BigDecimal moneyRate;// 金额占比
	private BigDecimal customerNums;// 客单量
	private BigDecimal cusotmerRate;// 客单占比
	private BigDecimal customerAvePrice;// 平均客单价
	private BigDecimal customerItemRelatRate;//连带率
	private BigDecimal customerItemCount;
	private BigDecimal customerValidNums;// 连带率大于0的客单量
	
	private BigDecimal cusotmerVipNums;//会员客单数
	private BigDecimal customerVipMoney;//会员消费金额
	private Integer shiftTableNum;//班次
	private String shiftTableUser;//收银员
	private Date shiftTableStart;//接班时间
	private Date shiftTableEnd;//交班时间

	private Integer stallNum;
	private String stallCode;
	private String stallName;

	public BigDecimal getCustomerValidNums() {
		return customerValidNums;
	}

	public void setCustomerValidNums(BigDecimal customerValidNums) {
		this.customerValidNums = customerValidNums;
	}

	public BigDecimal getCustomerItemCount() {
		return customerItemCount;
	}

	public void setCustomerItemCount(BigDecimal customerItemCount) {
		this.customerItemCount = customerItemCount;
	}

	public BigDecimal getCustomerItemRelatRate() {
		return customerItemRelatRate;
	}

	public void setCustomerItemRelatRate(BigDecimal customerItemRelatRate) {
		this.customerItemRelatRate = customerItemRelatRate;
	}

	public String getShiftTableDate() {
		return shiftTableDate;
	}

	public void setShiftTableDate(String shiftTableDate) {
		this.shiftTableDate = shiftTableDate;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getMoneyRate() {
		return moneyRate;
	}

	public void setMoneyRate(BigDecimal moneyRate) {
		this.moneyRate = moneyRate;
	}

	public BigDecimal getCustomerNums() {
		return customerNums;
	}

	public void setCustomerNums(BigDecimal customerNums) {
		this.customerNums = customerNums;
	}

	public BigDecimal getCusotmerRate() {
		return cusotmerRate;
	}

	public void setCusotmerRate(BigDecimal cusotmerRate) {
		this.cusotmerRate = cusotmerRate;
	}

	public BigDecimal getCustomerAvePrice() {
		return customerAvePrice;
	}

	public void setCustomerAvePrice(BigDecimal customerAvePrice) {
		this.customerAvePrice = customerAvePrice;
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

	public BigDecimal getCusotmerVipNums() {
		return cusotmerVipNums;
	}

	public void setCusotmerVipNums(BigDecimal cusotmerVipNums) {
		this.cusotmerVipNums = cusotmerVipNums;
	}

	public BigDecimal getCustomerVipMoney() {
		return customerVipMoney;
	}

	public void setCustomerVipMoney(BigDecimal customerVipMoney) {
		this.customerVipMoney = customerVipMoney;
	}

	public Integer getShiftTableNum() {
		return shiftTableNum;
	}

	public void setShiftTableNum(Integer shiftTableNum) {
		this.shiftTableNum = shiftTableNum;
	}

	public String getShiftTableUser() {
		return shiftTableUser;
	}

	public void setShiftTableUser(String shiftTableUser) {
		this.shiftTableUser = shiftTableUser;
	}

	public Date getShiftTableStart() {
		return shiftTableStart;
	}

	public void setShiftTableStart(Date shiftTableStart) {
		this.shiftTableStart = shiftTableStart;
	}

	public Date getShiftTableEnd() {
		return shiftTableEnd;
	}

	public void setShiftTableEnd(Date shiftTableEnd) {
		this.shiftTableEnd = shiftTableEnd;
	}

	public Integer getStallNum() {
		return stallNum;
	}

	public void setStallNum(Integer stallNum) {
		this.stallNum = stallNum;
	}

	public String getStallCode() {
		return stallCode;
	}

	public void setStallCode(String stallCode) {
		this.stallCode = stallCode;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
}
