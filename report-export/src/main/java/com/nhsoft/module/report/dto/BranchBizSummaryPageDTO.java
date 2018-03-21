package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BranchBizSummaryPageDTO implements Serializable {

    private static final long serialVersionUID = 1509119781532455927L;
    private Integer count;
    private List<BranchBizSummary> data;

    private BigDecimal costSum;
    private BigDecimal profitSum;
    private BigDecimal profitRateSum;
    private BigDecimal moneySum;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<BranchBizSummary> getData() {
        return data;
    }

    public void setData(List<BranchBizSummary> data) {
        this.data = data;
    }


    public BigDecimal getCostSum() {
        return costSum;
    }

    public void setCostSum(BigDecimal costSum) {
        this.costSum = costSum;
    }

    public BigDecimal getProfitSum() {
        return profitSum;
    }

    public void setProfitSum(BigDecimal profitSum) {
        this.profitSum = profitSum;
    }

    public BigDecimal getProfitRateSum() {
        return profitRateSum;
    }

    public void setProfitRateSum(BigDecimal profitRateSum) {
        this.profitRateSum = profitRateSum;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }
}
