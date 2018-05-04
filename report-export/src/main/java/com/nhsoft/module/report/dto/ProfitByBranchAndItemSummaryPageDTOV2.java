package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ProfitByBranchAndItemSummaryPageDTOV2 implements Serializable {

    private static final long serialVersionUID = 6467172586041938503L;
    private Integer count;
    private List<ProfitByBranchAndItemDTO> data;
    private BigDecimal profitSum;
    private BigDecimal profitRateSum;
    private BigDecimal costSum;
    private BigDecimal moneySum;
    private BigDecimal amountSum;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProfitByBranchAndItemDTO> getData() {
        return data;
    }

    public void setData(List<ProfitByBranchAndItemDTO> data) {
        this.data = data;
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

    public BigDecimal getCostSum() {
        return costSum;
    }

    public void setCostSum(BigDecimal costSum) {
        this.costSum = costSum;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }

    public BigDecimal getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(BigDecimal amountSum) {
        this.amountSum = amountSum;
    }
}
