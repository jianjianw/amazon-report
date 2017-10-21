package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchBizSaleSummary implements Serializable {


    private Integer branchNum;
    private String bizday;
    private int stateCode;
    private BigDecimal amount;
    private BigDecimal paymentMoney;
    private BigDecimal assistAmount;
    private Integer itemNumAmount;


    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getBizday() {
        return bizday;
    }

    public void setBizday(String bizday) {
        this.bizday = bizday;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public BigDecimal getAssistAmount() {
        return assistAmount;
    }

    public void setAssistAmount(BigDecimal assistAmount) {
        this.assistAmount = assistAmount;
    }

    public Integer getItemNumAmount() {
        return itemNumAmount;
    }

    public void setItemNumAmount(Integer itemNumAmount) {
        this.itemNumAmount = itemNumAmount;
    }
}
