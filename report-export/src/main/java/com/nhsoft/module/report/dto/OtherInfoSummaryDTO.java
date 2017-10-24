package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OtherInfoSummaryDTO implements Serializable {

    private static final long serialVersionUID = -541589973169413891L;
    private Integer branchNum;
    private String branchName;
    private Integer cashBoxOpenCount = 0; //打开钱箱
    private Integer hangOrderCount = 0; //挂单
    private BigDecimal hangOrderMoney = BigDecimal.ZERO;
    private Integer orderCancelCount = 0; //整单取消
    private BigDecimal orderCancelMoney = BigDecimal.ZERO;
    private Integer deleteCount = 0; //删除
    private BigDecimal deleteMoney = BigDecimal.ZERO;
    private Integer returnCount = 0; //退货
    private BigDecimal returnMoney = BigDecimal.ZERO;
    private Integer antiOrderCount = 0; //反结账
    private BigDecimal antiOrderMoney = BigDecimal.ZERO;
    private Integer changePriceCount = 0; //修改价格
    private BigDecimal changePriceMoney = BigDecimal.ZERO;
    private Integer mgrDiscountCount = 0; //经理折扣
    private BigDecimal mgrDiscountMoney = BigDecimal.ZERO;

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getCashBoxOpenCount() {
        return cashBoxOpenCount;
    }

    public void setCashBoxOpenCount(Integer cashBoxOpenCount) {
        this.cashBoxOpenCount = cashBoxOpenCount;
    }

    public Integer getHangOrderCount() {
        return hangOrderCount;
    }

    public void setHangOrderCount(Integer hangOrderCount) {
        this.hangOrderCount = hangOrderCount;
    }

    public BigDecimal getHangOrderMoney() {
        return hangOrderMoney;
    }

    public void setHangOrderMoney(BigDecimal hangOrderMoney) {
        this.hangOrderMoney = hangOrderMoney;
    }

    public Integer getOrderCancelCount() {
        return orderCancelCount;
    }

    public void setOrderCancelCount(Integer orderCancelCount) {
        this.orderCancelCount = orderCancelCount;
    }

    public BigDecimal getOrderCancelMoney() {
        return orderCancelMoney;
    }

    public void setOrderCancelMoney(BigDecimal orderCancelMoney) {
        this.orderCancelMoney = orderCancelMoney;
    }

    public Integer getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(Integer deleteCount) {
        this.deleteCount = deleteCount;
    }

    public BigDecimal getDeleteMoney() {
        return deleteMoney;
    }

    public void setDeleteMoney(BigDecimal deleteMoney) {
        this.deleteMoney = deleteMoney;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getAntiOrderCount() {
        return antiOrderCount;
    }

    public void setAntiOrderCount(Integer antiOrderCount) {
        this.antiOrderCount = antiOrderCount;
    }

    public BigDecimal getAntiOrderMoney() {
        return antiOrderMoney;
    }

    public void setAntiOrderMoney(BigDecimal antiOrderMoney) {
        this.antiOrderMoney = antiOrderMoney;
    }

    public Integer getChangePriceCount() {
        return changePriceCount;
    }

    public void setChangePriceCount(Integer changePriceCount) {
        this.changePriceCount = changePriceCount;
    }

    public BigDecimal getChangePriceMoney() {
        return changePriceMoney;
    }

    public void setChangePriceMoney(BigDecimal changePriceMoney) {
        this.changePriceMoney = changePriceMoney;
    }

    public Integer getMgrDiscountCount() {
        return mgrDiscountCount;
    }

    public void setMgrDiscountCount(Integer mgrDiscountCount) {
        this.mgrDiscountCount = mgrDiscountCount;
    }

    public BigDecimal getMgrDiscountMoney() {
        return mgrDiscountMoney;
    }

    public void setMgrDiscountMoney(BigDecimal mgrDiscountMoney) {
        this.mgrDiscountMoney = mgrDiscountMoney;
    }
}