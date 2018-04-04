package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleAmountAndMoneyDTO implements Serializable{


    private static final long serialVersionUID = 6045283977111332704L;
    private String biz;
    private BigDecimal qty;
    private BigDecimal money;


    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
