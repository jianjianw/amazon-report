package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchDepositReport implements Serializable {

    private Integer branchNum;
    private BigDecimal depositCash;//收款金额
    private BigDecimal deposit;//卡存款


    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getDepositCash() {
        return depositCash;
    }

    public void setDepositCash(BigDecimal depositCash) {
        this.depositCash = depositCash;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }
}
