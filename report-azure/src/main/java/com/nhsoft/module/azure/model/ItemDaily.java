package com.nhsoft.module.azure.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品日销售汇总
 * */
public class ItemDaily implements Serializable {

    private String systemBookCode;
    private Integer branchNum;
    private String bizday;
    private Integer itemNum;
    private Date date;
    private BigDecimal money;
    private BigDecimal amout;   //销售数量

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

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

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
    }
}
