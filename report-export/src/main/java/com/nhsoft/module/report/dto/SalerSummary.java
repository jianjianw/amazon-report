package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalerSummary implements Serializable {

    private BigDecimal amount;
    private BigDecimal money;
    private BigDecimal commission;


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

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
}
