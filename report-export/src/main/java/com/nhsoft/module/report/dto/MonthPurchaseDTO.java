package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthPurchaseDTO implements Serializable {
    private static final long serialVersionUID = 7240657582687245635L;

    private String bizday;
    private Integer itemNum;
    private BigDecimal subTotal;
    private BigDecimal otherMoney;

    public String getBizday() {
        return bizday;
    }

    public void setBizday(String bizday) {
        this.bizday = bizday;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getOtherMoney() {
        return otherMoney;
    }

    public void setOtherMoney(BigDecimal otherMoney) {
        this.otherMoney = otherMoney;
    }
}
