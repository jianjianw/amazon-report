package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RebatesSumSummary implements Serializable {

    private BigDecimal money;
    private BigDecimal discount;
    private BigDecimal amount;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
