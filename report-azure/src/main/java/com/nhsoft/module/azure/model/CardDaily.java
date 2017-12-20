package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity
public class CardDaily implements Serializable {

    @Id
    private String systemBookCode;
    @Id
    private Integer branchNum;
    @Id
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardDaily CardDaily = (CardDaily) o;

        if (systemBookCode != null ? !systemBookCode.equals(CardDaily.systemBookCode) : CardDaily.systemBookCode != null)
            return false;
        if (branchNum != null ? !branchNum.equals(CardDaily.branchNum) : CardDaily.branchNum != null) return false;
        if (shiftTableBizday != null ? !shiftTableBizday.equals(CardDaily.shiftTableBizday) : CardDaily.shiftTableBizday != null)
            return false;
        if (shiftTableDate != null ? !shiftTableDate.equals(CardDaily.shiftTableDate) : CardDaily.shiftTableDate != null)
            return false;
        if (cardDeliverCount != null ? !cardDeliverCount.equals(CardDaily.cardDeliverCount) : CardDaily.cardDeliverCount != null)
            return false;
        if (cardReturnCount != null ? !cardReturnCount.equals(CardDaily.cardReturnCount) : CardDaily.cardReturnCount != null)
            return false;
        if (cardDeliverTarget != null ? !cardDeliverTarget.equals(CardDaily.cardDeliverTarget) : CardDaily.cardDeliverTarget != null)
            return false;
        if (cardDepositCash != null ? !cardDepositCash.equals(CardDaily.cardDepositCash) : CardDaily.cardDepositCash != null)
            return false;
        if (cardDepositMoney != null ? !cardDepositMoney.equals(CardDaily.cardDepositMoney) : CardDaily.cardDepositMoney != null)
            return false;
        if (cardDepositTarget != null ? !cardDepositTarget.equals(CardDaily.cardDepositTarget) : CardDaily.cardDepositTarget != null)
            return false;
        return cardConsumeMoney != null ? cardConsumeMoney.equals(CardDaily.cardConsumeMoney) : CardDaily.cardConsumeMoney == null;
    }

    @Override
    public int hashCode() {
        int result = systemBookCode != null ? systemBookCode.hashCode() : 0;
        result = 31 * result + (branchNum != null ? branchNum.hashCode() : 0);
        result = 31 * result + (shiftTableBizday != null ? shiftTableBizday.hashCode() : 0);
        result = 31 * result + (shiftTableDate != null ? shiftTableDate.hashCode() : 0);
        result = 31 * result + (cardDeliverCount != null ? cardDeliverCount.hashCode() : 0);
        result = 31 * result + (cardReturnCount != null ? cardReturnCount.hashCode() : 0);
        result = 31 * result + (cardDeliverTarget != null ? cardDeliverTarget.hashCode() : 0);
        result = 31 * result + (cardDepositCash != null ? cardDepositCash.hashCode() : 0);
        result = 31 * result + (cardDepositMoney != null ? cardDepositMoney.hashCode() : 0);
        result = 31 * result + (cardDepositTarget != null ? cardDepositTarget.hashCode() : 0);
        result = 31 * result + (cardConsumeMoney != null ? cardConsumeMoney.hashCode() : 0);
        return result;
    }
}
