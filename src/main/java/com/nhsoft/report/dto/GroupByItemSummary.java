package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class GroupByItemSummary implements Serializable {


    private Integer itemNum;
    private Integer matrixNum;
    private BigDecimal grossProfit;
    private BigDecimal amount;
    private BigDecimal paymentMoney;
    private BigDecimal cost;
    private BigDecimal assistAmount;

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Integer getMatrixNum() {
        return matrixNum;
    }

    public void setMatrixNum(Integer matrixNum) {
        this.matrixNum = matrixNum;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getAssistAmount() {
        return assistAmount;
    }

    public void setAssistAmount(BigDecimal assistAmount) {
        this.assistAmount = assistAmount;
    }
}
