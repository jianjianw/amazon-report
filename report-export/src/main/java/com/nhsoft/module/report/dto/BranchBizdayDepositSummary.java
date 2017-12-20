package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchBizdayDepositSummary implements Serializable{

    private Integer branchNum;
    private String bizday;  //营业日
    private BigDecimal depositCash;//付款金额
    private BigDecimal deposit;//存款金额

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getBizday() {
        return bizday;
    }

    public void setBizday(String bizday) {
        this.bizday = bizday;
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