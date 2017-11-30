package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchItemSummaryDTO implements Serializable {

    private Integer branchNum;
    private Integer itemNum;
    private BigDecimal amount;
    private BigDecimal money;
    private BigDecimal profit;
    private BigDecimal saleCount;
    private BigDecimal commission;
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

    public BigDecimal getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(BigDecimal saleCount) {
        this.saleCount = saleCount;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
