package com.nhsoft.report.dao.impl;


import java.io.Serializable;
import java.math.BigDecimal;

public class TransferOutMoney implements Serializable {

    private Integer branchNum;
    private BigDecimal OutMoney;//配送额

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getOutMoney() {
        return OutMoney;
    }

    public void setOutMoney(BigDecimal outMoney) {
        OutMoney = outMoney;
    }
}
