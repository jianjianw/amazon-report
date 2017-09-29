package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RebatesDetailSummary implements Serializable {

    private Integer orderDetailBranchNum;
    private String orderDetailBizday;
    private String orderNo;
    private Integer itemNum;
    private BigDecimal orderDetailStdPrice;
    private BigDecimal orderDetailPrice;
    private BigDecimal orderDetailAmount;
    private BigDecimal orderDetailPaymentMoney;
    private BigDecimal orderDetailDiscount;
    private int orderDetailPromotionType;
    private int orderDetailStateCode;

    public Integer getOrderDetailBranchNum() {
        return orderDetailBranchNum;
    }

    public void setOrderDetailBranchNum(Integer orderDetailBranchNum) {
        this.orderDetailBranchNum = orderDetailBranchNum;
    }

    public String getOrderDetailBizday() {
        return orderDetailBizday;
    }

    public void setOrderDetailBizday(String orderDetailBizday) {
        this.orderDetailBizday = orderDetailBizday;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getOrderDetailStdPrice() {
        return orderDetailStdPrice;
    }

    public void setOrderDetailStdPrice(BigDecimal orderDetailStdPrice) {
        this.orderDetailStdPrice = orderDetailStdPrice;
    }

    public BigDecimal getOrderDetailPrice() {
        return orderDetailPrice;
    }

    public void setOrderDetailPrice(BigDecimal orderDetailPrice) {
        this.orderDetailPrice = orderDetailPrice;
    }

    public BigDecimal getOrderDetailAmount() {
        return orderDetailAmount;
    }

    public void setOrderDetailAmount(BigDecimal orderDetailAmount) {
        this.orderDetailAmount = orderDetailAmount;
    }

    public BigDecimal getOrderDetailPaymentMoney() {
        return orderDetailPaymentMoney;
    }

    public void setOrderDetailPaymentMoney(BigDecimal orderDetailPaymentMoney) {
        this.orderDetailPaymentMoney = orderDetailPaymentMoney;
    }

    public BigDecimal getOrderDetailDiscount() {
        return orderDetailDiscount;
    }

    public void setOrderDetailDiscount(BigDecimal orderDetailDiscount) {
        this.orderDetailDiscount = orderDetailDiscount;
    }

    public int getOrderDetailPromotionType() {
        return orderDetailPromotionType;
    }

    public void setOrderDetailPromotionType(int orderDetailPromotionType) {
        this.orderDetailPromotionType = orderDetailPromotionType;
    }

    public int getOrderDetailStateCode() {
        return orderDetailStateCode;
    }

    public void setOrderDetailStateCode(int orderDetailStateCode) {
        this.orderDetailStateCode = orderDetailStateCode;
    }
}
