package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DifferenceMoney implements Serializable {
    private Integer branchNum;//配销差额
    private BigDecimal money;

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
