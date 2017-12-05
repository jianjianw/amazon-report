package com.nhsoft.module.report.dto.azure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品日时段销售汇总
 * */
public class ItemDailyDetail implements Serializable {

    private Integer branchNum;
    private String bizday;      //营业日期
    private Integer itemNum;
    private String systemBookCode;
    private String period;      //时段
    private Date date;          //营业日期
    private BigDecimal amout;   //销售数量
    private BigDecimal money;   //销售金额
    private String source;      //销售来源

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

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
