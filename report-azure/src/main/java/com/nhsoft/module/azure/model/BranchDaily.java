package com.nhsoft.module.azure.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分店日销售汇总表
 * */
@Entity
public class BranchDaily implements Serializable {

    public BranchDaily() {
        this.dailyMoney = BigDecimal.ZERO;
        this.dailyQty = 0;
        this.dailyPrice = BigDecimal.ZERO;
        this.targetMoney = BigDecimal.ZERO;
    }
    @Id
    private String systemBookCode;
    @Id
    private Integer branchNum;
    @Id
    private String shiftTableBizday;                  //营业日
    private Date shiftTableDate;                      //营业日期（和营业日一致）
    private BigDecimal dailyMoney;               //营业额
    private Integer dailyQty;                 //客单量
    private BigDecimal dailyPrice;               //客单价
    private BigDecimal targetMoney;       //营业额目标

    //新加字段
    private Integer dailyCount;  //客单购买数


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

    public BigDecimal getDailyMoney() {
        return dailyMoney;
    }

    public void setDailyMoney(BigDecimal dailyMoney) {
        this.dailyMoney = dailyMoney;
    }

    public Integer getDailyQty() {
        return dailyQty;
    }

    public void setDailyQty(Integer dailyQty) {
        this.dailyQty = dailyQty;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public BigDecimal getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(BigDecimal targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Integer getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(Integer dailyCount) {
        this.dailyCount = dailyCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchDaily that = (BranchDaily) o;

        if (systemBookCode != null ? !systemBookCode.equals(that.systemBookCode) : that.systemBookCode != null)
            return false;
        if (branchNum != null ? !branchNum.equals(that.branchNum) : that.branchNum != null) return false;
        if (shiftTableBizday != null ? !shiftTableBizday.equals(that.shiftTableBizday) : that.shiftTableBizday != null)
            return false;
        if (shiftTableDate != null ? !shiftTableDate.equals(that.shiftTableDate) : that.shiftTableDate != null)
            return false;
        if (dailyMoney != null ? !dailyMoney.equals(that.dailyMoney) : that.dailyMoney != null) return false;
        if (dailyQty != null ? !dailyQty.equals(that.dailyQty) : that.dailyQty != null) return false;
        if (dailyPrice != null ? !dailyPrice.equals(that.dailyPrice) : that.dailyPrice != null) return false;
        if (targetMoney != null ? !targetMoney.equals(that.targetMoney) : that.targetMoney != null) return false;
        return dailyCount != null ? dailyCount.equals(that.dailyCount) : that.dailyCount == null;
    }

    @Override
    public int hashCode() {
        int result = systemBookCode != null ? systemBookCode.hashCode() : 0;
        result = 31 * result + (branchNum != null ? branchNum.hashCode() : 0);
        result = 31 * result + (shiftTableBizday != null ? shiftTableBizday.hashCode() : 0);
        result = 31 * result + (shiftTableDate != null ? shiftTableDate.hashCode() : 0);
        result = 31 * result + (dailyMoney != null ? dailyMoney.hashCode() : 0);
        result = 31 * result + (dailyQty != null ? dailyQty.hashCode() : 0);
        result = 31 * result + (dailyPrice != null ? dailyPrice.hashCode() : 0);
        result = 31 * result + (targetMoney != null ? targetMoney.hashCode() : 0);
        result = 31 * result + (dailyCount != null ? dailyCount.hashCode() : 0);
        return result;
    }
}
