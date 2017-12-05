package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleMoneyGoals implements Serializable {

    private Integer branchNum;
    private BigDecimal saleMoney;//营业额目标
    private String date;        //日期
    private String systemBookCode;

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }
}
