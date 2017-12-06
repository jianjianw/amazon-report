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
}
