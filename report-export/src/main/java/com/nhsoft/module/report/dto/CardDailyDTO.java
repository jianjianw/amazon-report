package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CardDailyDTO implements Serializable{


    private String systemBookCode;
    private Integer branchNum;
    private String shiftTableBizday;
    private Date shiftTableDate;
    private Integer cardDeliverCount;       //新发卡数
    private Integer cardReturnCount;        //退卡数
    private Integer cardDeliverTarget;      //发卡目标
    private BigDecimal cardDepositCash;     //付款金额
    private BigDecimal cardDepositMoney;    //存款金额
    private BigDecimal cardDepositTarget;   //存款目标
    private BigDecimal cardConsumeMoney;    //消费金额


    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getShiftTableBizday() {
        return shiftTableBizday;
    }

    public void setShiftTableBizday(String shiftTableBizday) {
        this.shiftTableBizday = shiftTableBizday;
    }

    public Date getShiftTableDate() {
        return shiftTableDate;
    }

    public void setShiftTableDate(Date shiftTableDate) {
        this.shiftTableDate = shiftTableDate;
    }

    public Integer getCardDeliverCount() {
        return cardDeliverCount;
    }

    public void setCardDeliverCount(Integer cardDeliverCount) {
        this.cardDeliverCount = cardDeliverCount;
    }

    public Integer getCardReturnCount() {
        return cardReturnCount;
    }

    public void setCardReturnCount(Integer cardReturnCount) {
        this.cardReturnCount = cardReturnCount;
    }

    public Integer getCardDeliverTarget() {
        return cardDeliverTarget;
    }

    public void setCardDeliverTarget(Integer cardDeliverTarget) {
        this.cardDeliverTarget = cardDeliverTarget;
    }

    public BigDecimal getCardDepositCash() {
        return cardDepositCash;
    }

    public void setCardDepositCash(BigDecimal cardDepositCash) {
        this.cardDepositCash = cardDepositCash;
    }

    public BigDecimal getCardDepositMoney() {
        return cardDepositMoney;
    }

    public void setCardDepositMoney(BigDecimal cardDepositMoney) {
        this.cardDepositMoney = cardDepositMoney;
    }

    public BigDecimal getCardDepositTarget() {
        return cardDepositTarget;
    }

    public void setCardDepositTarget(BigDecimal cardDepositTarget) {
        this.cardDepositTarget = cardDepositTarget;
    }

    public BigDecimal getCardConsumeMoney() {
        return cardConsumeMoney;
    }

    public void setCardConsumeMoney(BigDecimal cardConsumeMoney) {
        this.cardConsumeMoney = cardConsumeMoney;
    }
}
