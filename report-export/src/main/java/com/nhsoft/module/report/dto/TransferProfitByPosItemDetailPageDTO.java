package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class TransferProfitByPosItemDetailPageDTO implements Serializable{
    private static final long serialVersionUID = 2511530237290680328L;


    private Integer count;
    private List<TransferProfitByPosItemDetailDTO> data;

    private BigDecimal outMoneySum;
    private BigDecimal costUnitPriceSum;
    private BigDecimal profitMoneySum;
    private BigDecimal outAmountSum;
    private BigDecimal baseAmountSum;
    private BigDecimal outAmountPrSum;
    private BigDecimal baseAmountPrSum;
    private BigDecimal outAmountPrCostMoneySum;
    private BigDecimal outAmountPrTranferMoneySum;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<TransferProfitByPosItemDetailDTO> getData() {
        return data;
    }

    public void setData(List<TransferProfitByPosItemDetailDTO> data) {
        this.data = data;
    }

    public BigDecimal getOutMoneySum() {
        return outMoneySum;
    }

    public void setOutMoneySum(BigDecimal outMoneySum) {
        this.outMoneySum = outMoneySum;
    }

    public BigDecimal getCostUnitPriceSum() {
        return costUnitPriceSum;
    }

    public void setCostUnitPriceSum(BigDecimal costUnitPriceSum) {
        this.costUnitPriceSum = costUnitPriceSum;
    }

    public BigDecimal getProfitMoneySum() {
        return profitMoneySum;
    }

    public void setProfitMoneySum(BigDecimal profitMoneySum) {
        this.profitMoneySum = profitMoneySum;
    }

    public BigDecimal getOutAmountSum() {
        return outAmountSum;
    }

    public void setOutAmountSum(BigDecimal outAmountSum) {
        this.outAmountSum = outAmountSum;
    }

    public BigDecimal getBaseAmountSum() {
        return baseAmountSum;
    }

    public void setBaseAmountSum(BigDecimal baseAmountSum) {
        this.baseAmountSum = baseAmountSum;
    }

    public BigDecimal getOutAmountPrSum() {
        return outAmountPrSum;
    }

    public void setOutAmountPrSum(BigDecimal outAmountPrSum) {
        this.outAmountPrSum = outAmountPrSum;
    }

    public BigDecimal getBaseAmountPrSum() {
        return baseAmountPrSum;
    }

    public void setBaseAmountPrSum(BigDecimal baseAmountPrSum) {
        this.baseAmountPrSum = baseAmountPrSum;
    }

    public BigDecimal getOutAmountPrCostMoneySum() {
        return outAmountPrCostMoneySum;
    }

    public void setOutAmountPrCostMoneySum(BigDecimal outAmountPrCostMoneySum) {
        this.outAmountPrCostMoneySum = outAmountPrCostMoneySum;
    }

    public BigDecimal getOutAmountPrTranferMoneySum() {
        return outAmountPrTranferMoneySum;
    }

    public void setOutAmountPrTranferMoneySum(BigDecimal outAmountPrTranferMoneySum) {
        this.outAmountPrTranferMoneySum = outAmountPrTranferMoneySum;
    }

}
