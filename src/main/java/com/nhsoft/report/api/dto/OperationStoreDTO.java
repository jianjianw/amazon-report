package com.nhsoft.report.api.dto;

import java.math.BigDecimal;

public class OperationStoreDTO {

	private Integer branchNum;                    //分店编号
	private String branchName;                    //分店
	private BigDecimal revenue;                   //营业额
	private BigDecimal realizeRate1;              //完成率
	private BigDecimal memberSalesRealizeRate;    //会员销售额完成率
	private BigDecimal memeberRevenueOccupy;      //会员消费占比
	private BigDecimal aveBillNums;               //日均客单量
	private BigDecimal allBillRealizeRate;        //总客单完成率
	private BigDecimal memberBillNums;            //会员客单量
	private BigDecimal bill;                      //客单价
	private BigDecimal memberBill;                //会员客单价
	private BigDecimal distributionDifferent;     //配销差额
	private BigDecimal destroyDefferent;          //报损差额
	private BigDecimal adjustAmount;              //盘损金额
	private BigDecimal grossProfit;               //毛利金额
	private BigDecimal grossProfitRate;           //毛利完成率
	private BigDecimal incressedMember;           //新增会员数
	private BigDecimal realizeRate2;              //完成率
	private BigDecimal cardStorage;               //卡储值金额
	private BigDecimal realizeRate3;              //完成率
	private BigDecimal cartStorageConsume;        //卡储值消费金额
	private BigDecimal storageConsumeOccupy;      //储值消费占比
	private BigDecimal growthOf;                  //环比增长
	private BigDecimal areaEfficiency;            //坪效
	private BigDecimal test;                      //试吃
	private BigDecimal peel;                      //去皮
	private BigDecimal breakage;                  //报损
	private BigDecimal other;                     //其他
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getRealizeRate1() {
		return realizeRate1;
	}
	public void setRealizeRate1(BigDecimal realizeRate1) {
		this.realizeRate1 = realizeRate1;
	}
	public BigDecimal getMemberSalesRealizeRate() {
		return memberSalesRealizeRate;
	}
	public void setMemberSalesRealizeRate(BigDecimal memberSalesRealizeRate) {
		this.memberSalesRealizeRate = memberSalesRealizeRate;
	}
	public BigDecimal getMemeberRevenueOccupy() {
		return memeberRevenueOccupy;
	}
	public void setMemeberRevenueOccupy(BigDecimal memeberRevenueOccupy) {
		this.memeberRevenueOccupy = memeberRevenueOccupy;
	}
	public BigDecimal getAveBillNums() {
		return aveBillNums;
	}
	public void setAveBillNums(BigDecimal aveBillNums) {
		this.aveBillNums = aveBillNums;
	}
	public BigDecimal getAllBillRealizeRate() {
		return allBillRealizeRate;
	}
	public void setAllBillRealizeRate(BigDecimal allBillRealizeRate) {
		this.allBillRealizeRate = allBillRealizeRate;
	}
	public BigDecimal getMemberBillNums() {
		return memberBillNums;
	}
	public void setMemberBillNums(BigDecimal memberBillNums) {
		this.memberBillNums = memberBillNums;
	}
	public BigDecimal getBill() {
		return bill;
	}
	public void setBill(BigDecimal bill) {
		this.bill = bill;
	}
	public BigDecimal getMemberBill() {
		return memberBill;
	}
	public void setMemberBill(BigDecimal memberBill) {
		this.memberBill = memberBill;
	}
	public BigDecimal getDistributionDifferent() {
		return distributionDifferent;
	}
	public void setDistributionDifferent(BigDecimal distributionDifferent) {
		this.distributionDifferent = distributionDifferent;
	}
	public BigDecimal getDestroyDefferent() {
		return destroyDefferent;
	}
	public void setDestroyDefferent(BigDecimal destroyDefferent) {
		this.destroyDefferent = destroyDefferent;
	}
	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}
	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}
	public BigDecimal getGrossProfit() {
		return grossProfit;
	}
	public void setGrossProfit(BigDecimal grossProfit) {
		this.grossProfit = grossProfit;
	}
	public BigDecimal getGrossProfitRate() {
		return grossProfitRate;
	}
	public void setGrossProfitRate(BigDecimal grossProfitRate) {
		this.grossProfitRate = grossProfitRate;
	}
	public BigDecimal getIncressedMember() {
		return incressedMember;
	}
	public void setIncressedMember(BigDecimal incressedMember) {
		this.incressedMember = incressedMember;
	}
	public BigDecimal getRealizeRate2() {
		return realizeRate2;
	}
	public void setRealizeRate2(BigDecimal realizeRate2) {
		this.realizeRate2 = realizeRate2;
	}
	public BigDecimal getCardStorage() {
		return cardStorage;
	}
	public void setCardStorage(BigDecimal cardStorage) {
		this.cardStorage = cardStorage;
	}
	public BigDecimal getRealizeRate3() {
		return realizeRate3;
	}
	public void setRealizeRate3(BigDecimal realizeRate3) {
		this.realizeRate3 = realizeRate3;
	}
	public BigDecimal getCartStorageConsume() {
		return cartStorageConsume;
	}
	public void setCartStorageConsume(BigDecimal cartStorageConsume) {
		this.cartStorageConsume = cartStorageConsume;
	}
	public BigDecimal getStorageConsumeOccupy() {
		return storageConsumeOccupy;
	}
	public void setStorageConsumeOccupy(BigDecimal storageConsumeOccupy) {
		this.storageConsumeOccupy = storageConsumeOccupy;
	}
	public BigDecimal getGrowthOf() {
		return growthOf;
	}
	public void setGrowthOf(BigDecimal growthOf) {
		this.growthOf = growthOf;
	}
	public BigDecimal getAreaEfficiency() {
		return areaEfficiency;
	}
	public void setAreaEfficiency(BigDecimal areaEfficiency) {
		this.areaEfficiency = areaEfficiency;
	}
	public BigDecimal getTest() {
		return test;
	}
	public void setTest(BigDecimal test) {
		this.test = test;
	}
	public BigDecimal getPeel() {
		return peel;
	}
	public void setPeel(BigDecimal peel) {
		this.peel = peel;
	}
	public BigDecimal getBreakage() {
		return breakage;
	}
	public void setBreakage(BigDecimal breakage) {
		this.breakage = breakage;
	}
	public BigDecimal getOther() {
		return other;
	}
	public void setOther(BigDecimal other) {
		this.other = other;
	}
	public Integer getBranchNum() {
		return branchNum;
	}
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
}
