package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitByClientAndItemSummary implements Serializable {

    private String clientFid;
    private Integer itemNum;
    private int matrixNum;
    private BigDecimal profit;
    private BigDecimal amount;
    private BigDecimal money;
    private BigDecimal cost;

    public String getClientFid() {
        return clientFid;
    }

    public void setClientFid(String clientFid) {
        this.clientFid = clientFid;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public int getMatrixNum() {
        return matrixNum;
    }

    public void setMatrixNum(int matrixNum) {
        this.matrixNum = matrixNum;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
