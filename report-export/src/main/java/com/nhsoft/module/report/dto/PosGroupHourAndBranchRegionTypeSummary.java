package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosGroupHourAndBranchRegionTypeSummary implements Serializable{

    private int hour;
    private String branchType;
    private BigDecimal orderMoney;
    private Integer orderCount;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }


    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
}
