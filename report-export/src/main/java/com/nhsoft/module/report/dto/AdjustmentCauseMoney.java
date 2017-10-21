package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AdjustmentCauseMoney implements Serializable {

    private Integer branchNum;
    private BigDecimal tryEat;//试吃
    private BigDecimal faly;//去皮
    private BigDecimal loss;  //报损
    private BigDecimal other;   //其它

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getTryEat() {
        return tryEat;
    }

    public void setTryEat(BigDecimal tryEat) {
        this.tryEat = tryEat;
    }

    public BigDecimal getFaly() {
        return faly;
    }

    public void setFaly(BigDecimal faly) {
        this.faly = faly;
    }

    public BigDecimal getLoss() {
        return loss;
    }

    public void setLoss(BigDecimal loss) {
        this.loss = loss;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }
}