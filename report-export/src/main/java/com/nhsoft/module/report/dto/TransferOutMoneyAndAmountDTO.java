package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferOutMoneyAndAmountDTO implements Serializable {
    private static final long serialVersionUID = 7081444700473149495L;

    private String biz;//营业日或营业月
    private BigDecimal outMoney;//调出金额
    private BigDecimal outQty;//调出数量


    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public BigDecimal getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(BigDecimal outMoney) {
        this.outMoney = outMoney;
    }

    public BigDecimal getOutQty() {
        return outQty;
    }

    public void setOutQty(BigDecimal outQty) {
        this.outQty = outQty;
    }
}
