package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerSummary implements Serializable {


    private BigDecimal money;
    private Long orderNo;
    private BigDecimal profit;
    private Long shiftCount;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Long getShiftCount() {
        return shiftCount;
    }

    public void setShiftCount(Long shiftCount) {
        this.shiftCount = shiftCount;
    }
}
