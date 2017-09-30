package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleByCategoryBranchSummary implements Serializable {

    private Integer orderDetailBranchNum;
    private String itemCategory;
    private int itemCategoryCode;
    private int orderDetailStateCode;
    private BigDecimal orderDetailAmount;
    private BigDecimal orderDetailPaymentMoney;
    private BigDecimal orderDetailAssistAmount;
    private Integer itemNum;

    public Integer getOrderDetailBranchNum() {
        return orderDetailBranchNum;
    }

    public void setOrderDetailBranchNum(Integer orderDetailBranchNum) {
        this.orderDetailBranchNum = orderDetailBranchNum;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getItemCategoryCode() {
        return itemCategoryCode;
    }

    public void setItemCategoryCode(int itemCategoryCode) {
        this.itemCategoryCode = itemCategoryCode;
    }

    public int getOrderDetailStateCode() {
        return orderDetailStateCode;
    }

    public void setOrderDetailStateCode(int orderDetailStateCode) {
        this.orderDetailStateCode = orderDetailStateCode;
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

    public BigDecimal getOrderDetailAssistAmount() {
        return orderDetailAssistAmount;
    }

    public void setOrderDetailAssistAmount(BigDecimal orderDetailAssistAmount) {
        this.orderDetailAssistAmount = orderDetailAssistAmount;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }
}
