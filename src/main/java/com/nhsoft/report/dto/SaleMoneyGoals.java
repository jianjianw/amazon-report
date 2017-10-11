package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleMoneyGoals implements Serializable {

    private Integer branchNum;
    private BigDecimal saleMoney;

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
}
