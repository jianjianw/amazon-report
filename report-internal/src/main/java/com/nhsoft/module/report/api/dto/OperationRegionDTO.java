package com.nhsoft.module.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OperationRegionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2185244162837737684L;
	private String area;                          //区域
	private String areaBranchNums;                //本区域所包含的分店号
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
	private BigDecimal realizeRate3;              //卡储值完成率
	private BigDecimal cartStorageConsume;        //卡储值消费金额
	private BigDecimal storageConsumeOccupy;      //储值消费占比
	private BigDecimal growthOf;                  //环比增长

	//新加临时属性
	private BigDecimal saleMoneyGoal;				//营业额目标
	private BigDecimal beforeSaleMoney;			//上一期营业额
	private BigDecimal transferOutMoney;			//配送金额
	private Integer billNums;					//客单量
	private BigDecimal memberSaleMoney;			//会员销售额
	private BigDecimal bigDay;						//日期之差，计算日均客单量
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAreaBranchNums() {
		return areaBranchNums;
	}
	public void setAreaBranchNums(String areaBranchNums) {
		this.areaBranchNums = areaBranchNums;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
