package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosGroupHourSummary implements Serializable {

    private Integer orderTime;
    private BigDecimal money;
    private Integer amount;

    public Integer getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Integer orderTime) {
        this.orderTime = orderTime;
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
