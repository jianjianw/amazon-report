package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PromotionItemPageDTO implements Serializable {
    private static final long serialVersionUID = -2990539320223080465L;


    private Integer count;
    private List<PromotionItemDTO> data;
    //汇总
    private BigDecimal moneySum;       //销售金额
    private BigDecimal discountSum;    //让利金额
    private BigDecimal amountSum;      //销售数量
    private BigDecimal costMoneySum;   //销售成本


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

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }

    public BigDecimal getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(BigDecimal discountSum) {
        this.discountSum = discountSum;
    }

    public BigDecimal getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(BigDecimal amountSum) {
        this.amountSum = amountSum;
    }

    public BigDecimal getCostMoneySum() {
        return costMoneySum;
    }

    public void setCostMoneySum(BigDecimal costMoneySum) {
        this.costMoneySum = costMoneySum;
    }
}
