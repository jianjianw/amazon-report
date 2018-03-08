package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleByCategorySummary implements Serializable {

    private Integer itemNum;
    private int stateCode;
    private BigDecimal amount;
    private BigDecimal paymentMoney;
    private BigDecimal assistAmount;
    private Integer itemCount;
    private BigDecimal discount;

    private Integer merchantNum;
    private Integer stallNum;
    private Boolean isPolicy;

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public BigDecimal getAssistAmount() {
        return assistAmount;
    }

    public void setAssistAmount(BigDecimal assistAmount) {
        this.assistAmount = assistAmount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(Integer merchantNum) {
        this.merchantNum = merchantNum;
    }

    public Integer getStallNum() {
        return stallNum;
    }

    public void setStallNum(Integer stallNum) {
        this.stallNum = stallNum;
    }

    public Boolean getPolicy() {
        return isPolicy;
    }

    public void setPolicy(Boolean policy) {
        isPolicy = policy;
    }
}
