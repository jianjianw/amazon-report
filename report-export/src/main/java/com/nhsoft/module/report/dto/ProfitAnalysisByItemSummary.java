package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitAnalysisByItemSummary implements Serializable {

    private Integer itemNum;
    private BigDecimal profit;
    private BigDecimal payment;
    private BigDecimal cost;
    private BigDecimal amount;

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
