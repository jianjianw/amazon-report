package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountPayDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -789975247969151811L;
	private String branchCode;  //分店编号
	private String branchName;  //分店名称
	private String clientCode;  //客户代码
	private String clientName;
	private String clientFid;  //客户编号
	
	private String seller;//销售员
	
	private BigDecimal lastDueMoney; //上期应收
	private BigDecimal dueMoney;  //应收金额
	private BigDecimal actualDueMoney; //已收金额
	private BigDecimal discountMoney; //优惠金额
	private BigDecimal notDueMoney; //未收金额
	private BigDecimal totalDueMoney; //应收累计
	private BigDecimal balanceMoney; //余额
	private BigDecimal currentTotalDueMoney; //截止到当前应收金额

	
	public AccountPayDTO(){
		dueMoney = BigDecimal.ZERO;
		actualDueMoney = BigDecimal.ZERO;
		discountMoney = BigDecimal.ZERO;
		notDueMoney = BigDecimal.ZERO;
		totalDueMoney = BigDecimal.ZERO;
		balanceMoney = BigDecimal.ZERO;
		currentTotalDueMoney = BigDecimal.ZERO;
		lastDueMoney = BigDecimal.ZERO;
	}

	public BigDecimal getLastDueMoney() {
		return lastDueMoney;
	}

	public void setLastDueMoney(BigDecimal lastDueMoney) {
		this.lastDueMoney = lastDueMoney;
	}

	public BigDecimal getCurrentTotalDueMoney() {
		return currentTotalDueMoney;
	}

	public void setCurrentTotalDueMoney(BigDecimal currentTotalDueMoney) {
		this.currentTotalDueMoney = currentTotalDueMoney;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientFid() {
		return clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getDueMoney() {
		return dueMoney;
	}

	public void setDueMoney(BigDecimal dueMoney) {
		this.dueMoney = dueMoney;
	}

	public BigDecimal getActualDueMoney() {
		return actualDueMoney;
	}

	public void setActualDueMoney(BigDecimal actualDueMoney) {
		this.actualDueMoney = actualDueMoney;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public BigDecimal getNotDueMoney() {
		return notDueMoney;
	}

	public void setNotDueMoney(BigDecimal notDueMoney) {
		this.notDueMoney = notDueMoney;
	}

	public BigDecimal getTotalDueMoney() {
		return totalDueMoney;
	}

	public void setTotalDueMoney(BigDecimal totalDueMoney) {
		this.totalDueMoney = totalDueMoney;
	}

	public BigDecimal getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(BigDecimal balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	
	
	
	
}
