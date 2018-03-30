package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CardReportPageDTO implements Serializable {
    private static final long serialVersionUID = 7742646343885423494L;


    private Integer count;
    private List<PosOrderDTO> data;
    private BigDecimal paymentMoneySum;
    private BigDecimal discountMoneySum;
    private BigDecimal pointSum;
    private BigDecimal mgrDiscountSum;
    private BigDecimal couponMoneySum;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PosOrderDTO> getData() {
        return data;
    }

    public void setData(List<PosOrderDTO> data) {
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

    public BigDecimal getMgrDiscountSum() {
        return mgrDiscountSum;
    }

    public void setMgrDiscountSum(BigDecimal mgrDiscountSum) {
        this.mgrDiscountSum = mgrDiscountSum;
    }

    public BigDecimal getCouponMoneySum() {
        return couponMoneySum;
    }

    public void setCouponMoneySum(BigDecimal couponMoneySum) {
        this.couponMoneySum = couponMoneySum;
    }
}
