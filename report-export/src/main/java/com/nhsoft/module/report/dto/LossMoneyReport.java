package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LossMoneyReport implements Serializable {

    private Integer branchNum;
    private BigDecimal money;//报损金额

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
