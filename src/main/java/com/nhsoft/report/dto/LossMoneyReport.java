package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LossMoneyReport implements Serializable {

    private String systemBookCode;
    private BigDecimal money;//报损金额

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
