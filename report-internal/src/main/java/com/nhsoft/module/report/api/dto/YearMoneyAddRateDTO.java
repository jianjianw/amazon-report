package com.nhsoft.module.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 同比增长率DTO
 * */
public class YearMoneyAddRateDTO implements Serializable {

    private String branchName;                      //分店名
    private Integer branchNum;                      //分店号
    private BigDecimal saleMoney;                   //营业额
    private BigDecimal beforeSaleMoney;             //同期营业额
    private BigDecimal saleMoneyAddRate;            //营业额同比增长率
    private BigDecimal billNum;                      //客单量
    private BigDecimal beforeBillNums;              //同期客单量
    private BigDecimal billNumAddRate;              //客单量同比增长率
    private BigDecimal billMoney;                   //客单价
    private BigDecimal beforebillMoney;             //同期客单价
    private BigDecimal billMoneyAddRate;            //客单价同比增长率
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

    public BigDecimal getBillNum() {
        return billNum;
    }

    public void setBillNum(BigDecimal billNum) {
        this.billNum = billNum;
    }

    public BigDecimal getBeforeBillNums() {
        return beforeBillNums;
    }

    public void setBeforeBillNums(BigDecimal beforeBillNums) {
        this.beforeBillNums = beforeBillNums;
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

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
