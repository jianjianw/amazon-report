package com.nhsoft.module.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 门店每日完成率排名
 */
public class BranchFinishRateTopDTO implements Serializable {

    public BranchFinishRateTopDTO() {
        this.saleMoney = BigDecimal.ZERO;
        this.saleMoneyGoal = BigDecimal.ZERO;
        this.orderCount = 0;
        this.profit = BigDecimal.ZERO;
        this.saleMoneyFinishRate = BigDecimal.ZERO;
        this.monthSaleMoneyGoal = BigDecimal.ZERO;
        this.avgSaleMoneyGoal = BigDecimal.ZERO;
        this.friSaleMoneyGoal = BigDecimal.ZERO;
        this.satSaleMoneyGoal = BigDecimal.ZERO;
        this.sunSaleMoneyGoal = BigDecimal.ZERO;
        this.monSaleMoneyGoal = BigDecimal.ZERO;

    }

    private String branchName;                        //分店名
    private Integer branchNum;                        //分店号
    private BigDecimal saleMoney;                     //当天营业额
    private BigDecimal saleMoneyGoal;                //当天营业额目标
    private Integer orderCount;                   //客单量
    private BigDecimal profit;                        //毛利
    private BigDecimal saleMoneyFinishRate;         //营业额完成率
    private BigDecimal monthSaleMoneyGoal;          //月营业额目标
    private BigDecimal avgSaleMoneyGoal;            //日均营业额目标
    private BigDecimal friSaleMoneyGoal;            //星期五营业额目标
    private BigDecimal satSaleMoneyGoal;            //星期六营业额目标
    private BigDecimal sunSaleMoneyGoal;            //星期天营业额目标
    private BigDecimal monSaleMoneyGoal;            //星期一到星期四营业额目标
    private String date;                             //返回日期和星期几
    private int top;                                //排名


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public BigDecimal getSaleMoneyGoal() {
        return saleMoneyGoal;
    }

    public void setSaleMoneyGoal(BigDecimal saleMoneyGoal) {
        this.saleMoneyGoal = saleMoneyGoal;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getSaleMoneyFinishRate() {
        return saleMoneyFinishRate;
    }

    public void setSaleMoneyFinishRate(BigDecimal saleMoneyFinishRate) {
        this.saleMoneyFinishRate = saleMoneyFinishRate;
    }

    public BigDecimal getMonthSaleMoneyGoal() {
        return monthSaleMoneyGoal;
    }

    public void setMonthSaleMoneyGoal(BigDecimal monthSaleMoneyGoal) {
        this.monthSaleMoneyGoal = monthSaleMoneyGoal;
    }

    public BigDecimal getAvgSaleMoneyGoal() {
        return avgSaleMoneyGoal;
    }

    public void setAvgSaleMoneyGoal(BigDecimal avgSaleMoneyGoal) {
        this.avgSaleMoneyGoal = avgSaleMoneyGoal;
    }

    public BigDecimal getFriSaleMoneyGoal() {
        return friSaleMoneyGoal;
    }

    public void setFriSaleMoneyGoal(BigDecimal friSaleMoneyGoal) {
        this.friSaleMoneyGoal = friSaleMoneyGoal;
    }

    public BigDecimal getSatSaleMoneyGoal() {
        return satSaleMoneyGoal;
    }

    public void setSatSaleMoneyGoal(BigDecimal satSaleMoneyGoal) {
        this.satSaleMoneyGoal = satSaleMoneyGoal;
    }

    public BigDecimal getSunSaleMoneyGoal() {
        return sunSaleMoneyGoal;
    }

    public void setSunSaleMoneyGoal(BigDecimal sunSaleMoneyGoal) {
        this.sunSaleMoneyGoal = sunSaleMoneyGoal;
    }

    public BigDecimal getMonSaleMoneyGoal() {
        return monSaleMoneyGoal;
    }

    public void setMonSaleMoneyGoal(BigDecimal monSaleMoneyGoal) {
        this.monSaleMoneyGoal = monSaleMoneyGoal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
