package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CustomerAnalysisHistoryPageDTO implements Serializable {

    private Integer count;
    private List<CustomerAnalysisHistory> data;

    private BigDecimal totalMoneySum;
    private BigDecimal customerSum;
    private BigDecimal customerAvgPriceSum;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CustomerAnalysisHistory> getData() {
        return data;
    }

    public void setData(List<CustomerAnalysisHistory> data) {
        this.data = data;
    }


    public BigDecimal getTotalMoneySum() {
        return totalMoneySum;
    }

    public void setTotalMoneySum(BigDecimal totalMoneySum) {
        this.totalMoneySum = totalMoneySum;
    }

    public BigDecimal getCustomerSum() {
        return customerSum;
    }

    public void setCustomerSum(BigDecimal customerSum) {
        this.customerSum = customerSum;
    }

    public BigDecimal getCustomerAvgPriceSum() {
        return customerAvgPriceSum;
    }

    public void setCustomerAvgPriceSum(BigDecimal customerAvgPriceSum) {
        this.customerAvgPriceSum = customerAvgPriceSum;
    }
}
