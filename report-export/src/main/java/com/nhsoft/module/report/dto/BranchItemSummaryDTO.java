package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchItemSummaryDTO implements Serializable {


    public BranchItemSummaryDTO() {
        this.amount = BigDecimal.ZERO;
        this.money = BigDecimal.ZERO;
        this.profit = BigDecimal.ZERO;
        this.saleCount = 0;
        this.cost = BigDecimal.ZERO;
    }


    private Integer branchNum;
    private Integer itemNum;
    private BigDecimal amount;
    private BigDecimal money;
    private BigDecimal profit;
    private Integer saleCount;
    private BigDecimal cost;

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
