package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardSummaryPageDTO implements Serializable{

    private Integer count;
    private List<CardSummaryDTO> data;

    BigDecimal paymentMoneySum;
    BigDecimal discountMoneySum;
    BigDecimal pointSum;
    BigDecimal mgrMoneySum;

    public BigDecimal getMgrMoneySum() {
        return mgrMoneySum;
    }

    public void setMgrMoneySum(BigDecimal mgrMoneySum) {
        this.mgrMoneySum = mgrMoneySum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CardSummaryDTO> getData() {
        return data;
    }

    public void setData(List<CardSummaryDTO> data) {
        this.data = data;
    }

    public BigDecimal getPaymentMoneySum() {
        return paymentMoneySum;
    }

    public void setPaymentMoneySum(BigDecimal paymentMoneySum) {
        this.paymentMoneySum = paymentMoneySum;
    }

    public BigDecimal getDiscountMoneySum() {
        return discountMoneySum;
    }

    public void setDiscountMoneySum(BigDecimal discountMoneySum) {
        this.discountMoneySum = discountMoneySum;
    }

    public BigDecimal getPointSum() {
        return pointSum;
    }

    public void setPointSum(BigDecimal pointSum) {
        this.pointSum = pointSum;
    }
}
