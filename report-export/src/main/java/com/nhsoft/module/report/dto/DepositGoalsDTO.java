package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DepositGoalsDTO implements Serializable{


    private Integer branchNum;
    private String bizday;          //日期
    private BigDecimal depositGoals;   //存款目标


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

    public BigDecimal getDepositGoals() {
        return depositGoals;
    }

    public void setDepositGoals(BigDecimal depositGoals) {
        this.depositGoals = depositGoals;
    }
}
