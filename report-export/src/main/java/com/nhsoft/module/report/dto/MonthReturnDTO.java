package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthReturnDTO implements Serializable {

    private String Bizday;
    private BigDecimal totalMoney;


    public String getBizday() {
        return Bizday;
    }

    public void setBizday(String bizday) {
        Bizday = bizday;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
