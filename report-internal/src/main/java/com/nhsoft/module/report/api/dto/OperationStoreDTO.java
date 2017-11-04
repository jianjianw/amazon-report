package com.nhsoft.module.report.api.dto;

import java.math.BigDecimal;

public class OperationStoreDTO {


	public OperationStoreDTO() {
		this.branchNum = 0;
		this.revenue = BigDecimal.ZERO;
		this.realizeRate1 = BigDecimal.ZERO;
		this.memberSalesRealizeRate = BigDecimal.ZERO;
		this.memeberRevenueOccupy = BigDecimal.ZERO;
		this.aveBillNums = BigDecimal.ZERO;
		this.allBillRealizeRate = BigDecimal.ZERO;
		this.memberBillNums = 0;
		this.bill = BigDecimal.ZERO;
		this.memberBill = BigDecimal.ZERO;
		this.distributionDifferent = BigDecimal.ZERO;
		this.destroyDefferent = BigDecimal.ZERO;
		this.adjustAmount = BigDecimal.ZERO;
		this.grossProfit = BigDecimal.ZERO;
		this.grossProfitRate = BigDecimal.ZERO;
		this.incressedMember = 0;
		this.realizeRate2 = BigDecimal.ZERO;
		this.cardStorage = BigDecimal.ZERO;
		this.realizeRate3 = BigDecimal.ZERO;
		this.cartStorageConsume = BigDecimal.ZERO;
		this.storageConsumeOccupy = BigDecimal.ZERO;
		this.growthOf = BigDecimal.ZERO;
		this.areaEfficiency = BigDecimal.ZERO;
		this.test = BigDecimal.ZERO;
		this.peel = BigDecimal.ZERO;
		this.breakage = BigDecimal.ZERO;
		this.other = BigDecimal.ZERO;
		this.saleMoneyGoal = BigDecimal.ZERO;
		this.beforeSaleMoney = BigDecimal.ZERO;
		this.transferOutMoney = BigDecimal.ZERO;
		this.billNums = 0;
		this.memberSaleMoney = BigDecimal.ZERO;
	}

	private Integer branchNum;                    //分店编号
	private String branchName;                    //分店
	private BigDecimal revenue;                   //营业额
	private BigDecimal realizeRate1;              //营业额完成率
	private BigDecimal memberSalesRealizeRate;    //会员销售额完成率
	private BigDecimal memeberRevenueOccupy;      //会员消费占比
	private BigDecimal aveBillNums;                  //日均客单量
	private BigDecimal allBillRealizeRate;        //总客单完成率
	private Integer memberBillNums;               //会员客单量
	private BigDecimal bill;                      //客单价
	private BigDecimal memberBill;                //会员客单价
	private BigDecimal distributionDifferent;     //配销差额
	private BigDecimal destroyDefferent;          //报损金额
	private BigDecimal adjustAmount;              //盘损金额
	private BigDecimal grossProfit;               //毛利金额
	private BigDecimal grossProfitRate;           //毛利完成率
	private Integer incressedMember;              //新增会员数
	private BigDecimal realizeRate2;              //新增会员数完成率
	private BigDecimal cardStorage;               //卡储值金额
	private BigDecimal realizeRate3;              //卡储值金额完成率
	private BigDecimal cartStorageConsume;        //卡储值消费金额
	private BigDecimal storageConsumeOccupy;      //储值消费占比
	private BigDecimal growthOf;                  //环比增长
	private BigDecimal areaEfficiency;            //坪效
	private BigDecimal test;                      //试吃
	private BigDecimal peel;                      //去皮
	private BigDecimal breakage;                  //报损
	private BigDecimal other;                     //其他

	//新加临时属性
	private BigDecimal saleMoneyGoal;				//营业额目标
	private BigDecimal beforeSaleMoney;			//上一期营业额
	private BigDecimal transferOutMoney;			//配送金额
	private Integer billNums;					//客单量
	private BigDecimal memberSaleMoney;			//会员销售额
	private BigDecimal bigDay;						//日期之差，计算日均客单量


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
	public Integer getMemberBillNums() {
		return memberBillNums;
	}
	public void setMemberBillNums(Integer memberBillNums) {
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
	public Integer getIncressedMember() {
		return incressedMember;
	}
	public void setIncressedMember(Integer incressedMember) {
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


	public BigDecimal getSaleMoneyGoal() {
		return saleMoneyGoal;
	}

	public void setSaleMoneyGoal(BigDecimal saleMoneyGoal) {
		this.saleMoneyGoal = saleMoneyGoal;
	}

	public BigDecimal getBeforeSaleMoney() {
		return beforeSaleMoney;
	}

	public void setBeforeSaleMoney(BigDecimal beforeSaleMoney) {
		this.beforeSaleMoney = beforeSaleMoney;
	}

	public BigDecimal getTransferOutMoney() {
		return transferOutMoney;
	}

	public void setTransferOutMoney(BigDecimal transferOutMoney) {
		this.transferOutMoney = transferOutMoney;
	}

	public Integer getBillNums() {
		return billNums;
	}

	public void setBillNums(Integer billNums) {
		this.billNums = billNums;
	}

	public BigDecimal getMemberSaleMoney() {
		return memberSaleMoney;
	}

	public void setMemberSaleMoney(BigDecimal memberSaleMoney) {
		this.memberSaleMoney = memberSaleMoney;
	}

	public BigDecimal getBigDay() {
		return bigDay;
	}

	public void setBigDay(BigDecimal bigDay) {
		this.bigDay = bigDay;
	}
}
