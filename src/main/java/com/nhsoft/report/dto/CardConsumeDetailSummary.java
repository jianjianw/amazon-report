package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardConsumeDetailSummary implements Serializable {

    private Integer cardUserNum;
    private Integer cardUserPrintedNum;
    private String cardUserCustdName;
    private BigDecimal paymentMoney;

    public Integer getCardUserNum() {
        return cardUserNum;
    }

    public void setCardUserNum(Integer cardUserNum) {
        this.cardUserNum = cardUserNum;
    }

    public Integer getCardUserPrintedNum() {
        return cardUserPrintedNum;
    }

    public void setCardUserPrintedNum(Integer cardUserPrintedNum) {
        this.cardUserPrintedNum = cardUserPrintedNum;
    }

    public String getCardUserCustdName() {
        return cardUserCustdName;
    }

    public void setCardUserCustdName(String cardUserCustdName) {
        this.cardUserCustdName = cardUserCustdName;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }
}
