package com.nhsoft.module.report.dto;


import java.io.Serializable;
import java.math.BigDecimal;

public class TransferOutMoney implements Serializable {
    private Integer branchNum;
    private BigDecimal OutMoney;//配送额
    private String biz;//营业日或营业月

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

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }
}
