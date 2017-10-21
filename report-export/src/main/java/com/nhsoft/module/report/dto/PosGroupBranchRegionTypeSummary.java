package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosGroupBranchRegionTypeSummary implements Serializable {

    private String regionType;
    private BigDecimal money;
    private Integer amount;

    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
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
