package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerSummary implements Serializable {


    private BigDecimal money;
    private Integer orderNo;
    private BigDecimal profit;
    private Integer shiftCount;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Integer getShiftCount() {
        return shiftCount;
    }

    public void setShiftCount(Integer shiftCount) {
        this.shiftCount = shiftCount;
    }
}
