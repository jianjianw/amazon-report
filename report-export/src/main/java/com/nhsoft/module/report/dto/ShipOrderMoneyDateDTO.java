package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShipOrderMoneyDateDTO implements Serializable {


    private String bizday;
    private BigDecimal feeMoney;
    private BigDecimal totalMoney;

    public String getBizday() {
        return bizday;
    }

    public void setBizday(String bizday) {
        this.bizday = bizday;
    }

    public BigDecimal getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(BigDecimal feeMoney) {
        this.feeMoney = feeMoney;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
