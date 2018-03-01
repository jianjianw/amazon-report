package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardSummaryDTO implements Serializable {


    private String printedNum;
    private String cardType;
    private String cardUserName;
    private Integer cardUserNum;
    private BigDecimal paymentMoney;
    private BigDecimal discount;
    private BigDecimal point;
    private BigDecimal mgrMoney;
    private BigDecimal couponMoney;


    public String getPrintedNum() {
        return printedNum;
    }

    public void setPrintedNum(String printedNum) {
        this.printedNum = printedNum;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardUserName() {
        return cardUserName;
    }

    public void setCardUserName(String cardUserName) {
        this.cardUserName = cardUserName;
    }

    public Integer getCardUserNum() {
        return cardUserNum;
    }

    public void setCardUserNum(Integer cardUserNum) {
        this.cardUserNum = cardUserNum;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public BigDecimal getMgrMoney() {
        return mgrMoney;
    }

    public void setMgrMoney(BigDecimal mgrMoney) {
        this.mgrMoney = mgrMoney;
    }

    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }
}
