package com.nhsoft.module.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 同比增长率DTO
 * */
public class YearMoneyAddRateDTO implements Serializable {


    public YearMoneyAddRateDTO() {
        this.saleMoney = BigDecimal.ZERO;
        this.beforeSaleMoney = BigDecimal.ZERO;
        this.saleMoneyAddRate = BigDecimal.ZERO;
        this.billNum = 0;
        this.beforeBillNum = 0;
        this.billNumAddRate = BigDecimal.ZERO;
        this.billMoney = BigDecimal.ZERO;
        this.beforebillMoney = BigDecimal.ZERO;
        this.billMoneyAddRate = BigDecimal.ZERO;
        this.profit = BigDecimal.ZERO;
        this.beforeProfit = BigDecimal.ZERO;
        this.profitAddRate = BigDecimal.ZERO;
    }

    private String branchName;                      //分店名
    private Integer branchNum;                      //分店号
    private BigDecimal saleMoney;                   //营业额
    private BigDecimal beforeSaleMoney;             //同期营业额
    private BigDecimal saleMoneyAddRate;            //营业额同比增长率
    private Integer billNum;                      //客单量
    private Integer beforeBillNum;              //同期客单量
    private BigDecimal billNumAddRate;              //客单量同比增长率
    private BigDecimal billMoney;                   //客单价
    private BigDecimal beforebillMoney;             //同期客单价
    private BigDecimal billMoneyAddRate;            //客单价同比增长率
    private BigDecimal profit;                      //毛利
    private BigDecimal beforeProfit;                //同期毛利
    private BigDecimal profitAddRate;               //毛利同比增长率
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

    public BigDecimal getBeforeSaleMoney() {
        return beforeSaleMoney;
    }

    public void setBeforeSaleMoney(BigDecimal beforeSaleMoney) {
        this.beforeSaleMoney = beforeSaleMoney;
    }

    public BigDecimal getSaleMoneyAddRate() {
        return saleMoneyAddRate;
    }

    public void setSaleMoneyAddRate(BigDecimal saleMoneyAddRate) {
        this.saleMoneyAddRate = saleMoneyAddRate;
    }

    public Integer getBillNum() {
        return billNum;
    }

    public void setBillNum(Integer billNum) {
        this.billNum = billNum;
    }

    public Integer getBeforeBillNum() {
        return beforeBillNum;
    }

    public void setBeforeBillNum(Integer beforeBillNum) {
        this.beforeBillNum = beforeBillNum;
    }

    public BigDecimal getBillNumAddRate() {
        return billNumAddRate;
    }

    public void setBillNumAddRate(BigDecimal billNumAddRate) {
        this.billNumAddRate = billNumAddRate;
    }

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney;
    }

    public BigDecimal getBeforebillMoney() {
        return beforebillMoney;
    }

    public void setBeforebillMoney(BigDecimal beforebillMoney) {
        this.beforebillMoney = beforebillMoney;
    }

    public BigDecimal getBillMoneyAddRate() {
        return billMoneyAddRate;
    }

    public void setBillMoneyAddRate(BigDecimal billMoneyAddRate) {
        this.billMoneyAddRate = billMoneyAddRate;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getBeforeProfit() {
        return beforeProfit;
    }

    public void setBeforeProfit(BigDecimal beforeProfit) {
        this.beforeProfit = beforeProfit;
    }

    public BigDecimal getProfitAddRate() {
        return profitAddRate;
    }

    public void setProfitAddRate(BigDecimal profitAddRate) {
        this.profitAddRate = profitAddRate;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
