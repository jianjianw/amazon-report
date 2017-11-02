package com.nhsoft.module.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleMoneyMonthDTO implements Serializable {
    private String month;//月份
    private BigDecimal saleMoney;       //营业额
    private BigDecimal saleMoneyGoal;   //营业额目标
    private BigDecimal finishMoneyRate;     //营业额完成率
    private BigDecimal yearAddRate;         //营业额年度增长率


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public BigDecimal getSaleMoneyGoal() {
        return saleMoneyGoal;
    }

    public void setSaleMoneyGoal(BigDecimal saleMoneyGoal) {
        this.saleMoneyGoal = saleMoneyGoal;
    }

    public BigDecimal getFinishMoneyRate() {
        return finishMoneyRate;
    }

    public void setFinishMoneyRate(BigDecimal finishMoneyRate) {
        this.finishMoneyRate = finishMoneyRate;
    }

    public BigDecimal getYearAddRate() {
        return yearAddRate;
    }

    public void setYearAddRate(BigDecimal yearAddRate) {
        this.yearAddRate = yearAddRate;
    }
}
