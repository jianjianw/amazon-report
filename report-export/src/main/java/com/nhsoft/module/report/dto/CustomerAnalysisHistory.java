package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerAnalysisHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6013469615282716718L;
	private String posMachineName;//pos机名称
	private String posMachineTerminalId;//pos机标示符
	private String shiftTableDate;//营业日
	private BigDecimal totalMoney;//总金额
	private BigDecimal moneyRate;//金额占比
	private BigDecimal customerNums;//客单量
	private BigDecimal customerRate;//客单占比
	private BigDecimal customerAvePrice;//平均客单价
	private Integer branchNum;//分店编号
	private String branchName;//分店名称
	private Integer branchRegionNum;//分店区域

	public Integer getBranchRegionNum() {
		return branchRegionNum;
	}

	public void setBranchRegionNum(Integer branchRegionNum) {
		this.branchRegionNum = branchRegionNum;
	}

	public String getPosMachineName() {
		return posMachineName;
	}

	public void setPosMachineName(String posMachineName) {
		this.posMachineName = posMachineName;
	}

	public String getPosMachineTerminalId() {
		return posMachineTerminalId;
	}

	public void setPosMachineTerminalId(String posMachineTerminalId) {
		this.posMachineTerminalId = posMachineTerminalId;
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

	public BigDecimal getCustomerRate() {
		return customerRate;
	}

	public void setCustomerRate(BigDecimal customerRate) {
		this.customerRate = customerRate;
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

}
