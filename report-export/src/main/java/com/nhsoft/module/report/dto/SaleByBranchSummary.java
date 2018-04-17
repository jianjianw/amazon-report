package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleByBranchSummary implements Serializable {


    private Integer branchNum;
    private int stateCode;
    private BigDecimal amount;
    private BigDecimal money;
    private BigDecimal assistAmount;
    private Integer itemNum;


    public SaleByBranchSummary() {
        this.amount = BigDecimal.ZERO;
        this.money = BigDecimal.ZERO;
        this.assistAmount = BigDecimal.ZERO;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAssistAmount() {
        return assistAmount;
    }

    public void setAssistAmount(BigDecimal assistAmount) {
        this.assistAmount = assistAmount;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }
}
