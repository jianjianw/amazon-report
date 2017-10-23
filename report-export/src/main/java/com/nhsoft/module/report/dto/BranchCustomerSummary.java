package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchCustomerSummary implements Serializable {

    private Integer branchNum;
    private BigDecimal paymentMoney;
    private Integer orderNoCount;
    private BigDecimal conponMoney;
    private BigDecimal mgrDiscount;
    private BigDecimal grossProfit;
    private Integer itemCount;
    private Integer userAmount;
    private BigDecimal userMoney;
    private Integer validOrderNo;  //sum(order_detail_item_count)

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public Integer getOrderNoCount() {
        return orderNoCount;
    }

    public void setOrderNoCount(Integer orderNoCount) {
        this.orderNoCount = orderNoCount;
    }

    public BigDecimal getConponMoney() {
        return conponMoney;
    }

    public void setConponMoney(BigDecimal conponMoney) {
        this.conponMoney = conponMoney;
    }

    public BigDecimal getMgrDiscount() {
        return mgrDiscount;
    }

    public void setMgrDiscount(BigDecimal mgrDiscount) {
        this.mgrDiscount = mgrDiscount;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(Integer userAmount) {
        this.userAmount = userAmount;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }

    public Integer getValidOrderNo() {
        return validOrderNo;
    }

    public void setValidOrderNo(Integer validOrderNo) {
        this.validOrderNo = validOrderNo;
    }
}
