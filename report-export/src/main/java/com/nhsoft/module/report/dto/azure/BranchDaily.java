package com.nhsoft.module.report.dto.azure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分店日销售汇总表
 * */
public class BranchDaily implements Serializable {

    public BranchDaily() {
        this.money = BigDecimal.ZERO;
        this.qty = BigDecimal.ZERO;
        this.price = BigDecimal.ZERO;
    }

    private String systemBookCode;
    private Integer branchNum;
    private String bizday;                  //营业日
    private Date date;                      //营业日期（和营业日一致）
    private BigDecimal money;               //营业额
    private BigDecimal qty;                 //客单量
    private BigDecimal price;               //客单价
    private BigDecimal targertMoney;       //营业额目标


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

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTargertMoney() {
        return targertMoney;
    }

    public void setTargertMoney(BigDecimal targertMoney) {
        this.targertMoney = targertMoney;
    }
}
