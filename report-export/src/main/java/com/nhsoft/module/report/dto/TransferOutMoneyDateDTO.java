package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferOutMoneyDateDTO implements Serializable {

    private String bizday;
    private BigDecimal totalMoney;
    private BigDecimal feeMoney;


    public String getBizday() {
        return bizday;
    }

    public void setBizday(String bizday) {
        this.bizday = bizday;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(BigDecimal feeMoney) {
        this.feeMoney = feeMoney;
    }
}
