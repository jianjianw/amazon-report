package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransterOutDTO implements Serializable {

    private Integer itemNum;
    private BigDecimal qty;
    private BigDecimal money;
    private BigDecimal useQty;


    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
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

    public BigDecimal getUseQty() {
        return useQty;
    }

    public void setUseQty(BigDecimal useQty) {
        this.useQty = useQty;
    }
}
