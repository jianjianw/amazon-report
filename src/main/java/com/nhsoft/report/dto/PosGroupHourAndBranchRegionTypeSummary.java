package com.nhsoft.report.dto;

import java.math.BigDecimal;

public class PosGroupHourAndBranchRegionTypeSummary {

    private int orderTimeChar;
    private BigDecimal money;
    private Integer amount;

    public int getOrderTimeChar() {
        return orderTimeChar;
    }

    public void setOrderTimeChar(int orderTimeChar) {
        this.orderTimeChar = orderTimeChar;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
