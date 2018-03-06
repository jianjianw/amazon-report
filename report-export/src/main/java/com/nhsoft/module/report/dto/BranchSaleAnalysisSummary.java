package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchSaleAnalysisSummary implements Serializable {

    private Integer branchNum;
    private String bizDate;                    // 日 或 月
    private BigDecimal data;                // 根据type返回数据    0营业额  1日均客单量  2客单价  3毛利  4毛利率
    private BigDecimal memberData;         //  会员数据


    //type = 4 时，需要返回下面的数据
    private BigDecimal saleMoney;
    private BigDecimal memberSaleMoney;

    private BigDecimal profit;
    private BigDecimal memberProfit;

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public BigDecimal getData() {
        return data;
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }

    public BigDecimal getMemberData() {
        return memberData;
    }

    public void setMemberData(BigDecimal memberData) {
        this.memberData = memberData;
    }


    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public BigDecimal getMemberSaleMoney() {
        return memberSaleMoney;
    }

    public void setMemberSaleMoney(BigDecimal memberSaleMoney) {
        this.memberSaleMoney = memberSaleMoney;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getMemberProfit() {
        return memberProfit;
    }

    public void setMemberProfit(BigDecimal memberProfit) {
        this.memberProfit = memberProfit;
    }
}
