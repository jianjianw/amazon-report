package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RetailDetailPageSummary implements Serializable{
    private static final long serialVersionUID = 7756523058798339875L;


    private Integer count;
    private List<RetailDetail> data;
    private BigDecimal amountSum;                 // 数量
    private BigDecimal saleMoneySum;             // 实际销售额
    private BigDecimal saleCostSum;              //成本
    private BigDecimal saleProfitSum;            //毛利
    private BigDecimal saleProfitRateSum;       //毛利率
    private BigDecimal discountMoneySum;        //折扣金额
    private BigDecimal saleCommissionSum;       //提成


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<RetailDetail> getData() {
        return data;
    }

    public void setData(List<RetailDetail> data) {
        this.data = data;
    }

    public BigDecimal getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(BigDecimal amountSum) {
        this.amountSum = amountSum;
    }

    public BigDecimal getSaleMoneySum() {
        return saleMoneySum;
    }

    public void setSaleMoneySum(BigDecimal saleMoneySum) {
        this.saleMoneySum = saleMoneySum;
    }

    public BigDecimal getSaleCostSum() {
        return saleCostSum;
    }

    public void setSaleCostSum(BigDecimal saleCostSum) {
        this.saleCostSum = saleCostSum;
    }

    public BigDecimal getSaleProfitSum() {
        return saleProfitSum;
    }

    public void setSaleProfitSum(BigDecimal saleProfitSum) {
        this.saleProfitSum = saleProfitSum;
    }

    public BigDecimal getSaleProfitRateSum() {
        return saleProfitRateSum;
    }

    public void setSaleProfitRateSum(BigDecimal saleProfitRateSum) {
        this.saleProfitRateSum = saleProfitRateSum;
    }

    public BigDecimal getDiscountMoneySum() {
        return discountMoneySum;
    }

    public void setDiscountMoneySum(BigDecimal discountMoneySum) {
        this.discountMoneySum = discountMoneySum;
    }

    public BigDecimal getSaleCommissionSum() {
        return saleCommissionSum;
    }

    public void setSaleCommissionSum(BigDecimal saleCommissionSum) {
        this.saleCommissionSum = saleCommissionSum;
    }
}
