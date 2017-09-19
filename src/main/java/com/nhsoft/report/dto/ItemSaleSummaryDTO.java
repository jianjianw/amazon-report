package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wangqianwen on 2017/7/10.
 */
public class ItemSaleSummaryDTO implements Serializable {
    private static final long serialVersionUID = 73363481953538694L;
    private Integer itemNum;
    private Integer itemMatrixNum;
    private BigDecimal itemSaleAmount;
    private BigDecimal itemSaleMoney;
    private Integer itemSaleTimes;
    private BigDecimal itemSaleAssistAmount;
    private BigDecimal itemDiscountMoney;
    private Integer itemStateCode;

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Integer getItemMatrixNum() {
        return itemMatrixNum;
    }

    public void setItemMatrixNum(Integer itemMatrixNum) {
        this.itemMatrixNum = itemMatrixNum;
    }

    public BigDecimal getItemSaleAmount() {
        return itemSaleAmount;
    }

    public void setItemSaleAmount(BigDecimal itemSaleAmount) {
        this.itemSaleAmount = itemSaleAmount;
    }

    public BigDecimal getItemSaleMoney() {
        return itemSaleMoney;
    }

    public void setItemSaleMoney(BigDecimal itemSaleMoney) {
        this.itemSaleMoney = itemSaleMoney;
    }

    public void setItemSaleTimes(Integer itemSaleTimes) {
        this.itemSaleTimes = itemSaleTimes;
    }

    public BigDecimal getItemSaleAssistAmount() {
        return itemSaleAssistAmount;
    }

    public void setItemSaleAssistAmount(BigDecimal itemSaleAssistAmount) {
        this.itemSaleAssistAmount = itemSaleAssistAmount;
    }

    public BigDecimal getItemDiscountMoney() {
        return itemDiscountMoney;
    }

    public void setItemDiscountMoney(BigDecimal itemDiscountMoney) {
        this.itemDiscountMoney = itemDiscountMoney;
    }

    public Integer getItemSaleTimes() {
        return itemSaleTimes;
    }

    public Integer getItemStateCode() {
        return itemStateCode;
    }

    public void setItemStateCode(Integer itemStateCode) {
        this.itemStateCode = itemStateCode;
    }
}
