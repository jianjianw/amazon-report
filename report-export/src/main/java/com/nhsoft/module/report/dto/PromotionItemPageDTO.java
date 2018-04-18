package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PromotionItemPageDTO implements Serializable {
    private static final long serialVersionUID = -2990539320223080465L;


    private Integer count;
    private List<PromotionItemDTO> data;
    //汇总
    private BigDecimal saleAmountSum; // 销售数量
    private BigDecimal saleMoneySum; // 销售金额
    private BigDecimal saleCostSum; // 销售成本
    private BigDecimal allowProfitMoneySum; // 让利金额


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PromotionItemDTO> getData() {
        return data;
    }

    public void setData(List<PromotionItemDTO> data) {
        this.data = data;
    }

    public BigDecimal getSaleAmountSum() {
        return saleAmountSum;
    }

    public void setSaleAmountSum(BigDecimal saleAmountSum) {
        this.saleAmountSum = saleAmountSum;
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

    public BigDecimal getAllowProfitMoneySum() {
        return allowProfitMoneySum;
    }

    public void setAllowProfitMoneySum(BigDecimal allowProfitMoneySum) {
        this.allowProfitMoneySum = allowProfitMoneySum;
    }
}


