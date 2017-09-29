package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaseReceiveCountMoneyByDateSummary implements Serializable {

    private String auditTime;
    private Integer amount;
    private BigDecimal money;


    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
