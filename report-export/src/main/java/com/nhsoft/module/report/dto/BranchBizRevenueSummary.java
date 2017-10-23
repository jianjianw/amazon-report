package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchBizRevenueSummary implements Serializable {

    private String biz;  //营业日或营业月
    private BigDecimal bizMoney;//营业额
    private Integer orderCount;//客单量
    private BigDecimal profit;//毛利


    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public BigDecimal getBizMoney() {
        return bizMoney;
    }

    public void setBizMoney(BigDecimal bizMoney) {
        this.bizMoney = bizMoney;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
