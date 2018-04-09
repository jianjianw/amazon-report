package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemInventoryDTO implements Serializable {
    private static final long serialVersionUID = -4762182612020966086L;


    private Integer itemNum;
    private BigDecimal amount;  //商品库存
    private BigDecimal money;   //商品金额


    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
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
}
