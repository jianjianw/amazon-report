package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchDepositReport implements Serializable {

    private Integer branchNum;
    private BigDecimal deposit;

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }
}