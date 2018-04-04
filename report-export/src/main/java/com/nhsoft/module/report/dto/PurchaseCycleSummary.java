package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaseCycleSummary implements Serializable {


    private static final long serialVersionUID = -7194922117602909624L;


    private String bizDay;
    private BigDecimal dayMoney;
    private BigDecimal actualInMoney;
    private BigDecimal finishRate;
    private BigDecimal outTotalMoney;
    private BigDecimal currentInventoryMoney;
    private BigDecimal inOutRate;
    private BigDecimal inQty;
    private BigDecimal outQty;
    private BigDecimal currentInventoryQty;


    public String getBizDay() {
        return bizDay;
    }

    public void setBizDay(String bizDay) {
        this.bizDay = bizDay;
    }

    public BigDecimal getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(BigDecimal dayMoney) {
        this.dayMoney = dayMoney;
    }

    public BigDecimal getActualInMoney() {
        return actualInMoney;
    }

    public void setActualInMoney(BigDecimal actualInMoney) {
        this.actualInMoney = actualInMoney;
    }

    public BigDecimal getFinishRate() {
        return finishRate;
    }

    public void setFinishRate(BigDecimal finishRate) {
        this.finishRate = finishRate;
    }

    public BigDecimal getOutTotalMoney() {
        return outTotalMoney;
    }

    public void setOutTotalMoney(BigDecimal outTotalMoney) {
        this.outTotalMoney = outTotalMoney;
    }

    public BigDecimal getCurrentInventoryMoney() {
        return currentInventoryMoney;
    }

    public void setCurrentInventoryMoney(BigDecimal currentInventoryMoney) {
        this.currentInventoryMoney = currentInventoryMoney;
    }

    public BigDecimal getInOutRate() {
        return inOutRate;
    }

    public void setInOutRate(BigDecimal inOutRate) {
        this.inOutRate = inOutRate;
    }

    public BigDecimal getInQty() {
        return inQty;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getOutQty() {
        return outQty;
    }

    public void setOutQty(BigDecimal outQty) {
        this.outQty = outQty;
    }

    public BigDecimal getCurrentInventoryQty() {
        return currentInventoryQty;
    }

    public void setCurrentInventoryQty(BigDecimal currentInventoryQty) {
        this.currentInventoryQty = currentInventoryQty;
    }
}
