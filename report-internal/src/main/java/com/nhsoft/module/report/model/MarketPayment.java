package com.nhsoft.module.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class MarketPayment implements java.io.Serializable {

    private static final long serialVersionUID = -7991185599164480824L;
    private String marketPaymentNo;
    private String systemBookCode;
    private String marketPaymentFid;
    private BigDecimal marketPaymentMoney;
    private String marketPaymentCustomerNo;
    private String marketPaymentCustomerName;
    private String marketPaymentToCustomerNo;
    private String marketPaymentToCustomerName;
    private Date marketPaymentApplyTime;
    private Date marketPaymentPayTime;
    private Integer marketPaymentStatus;
    private Integer marketPaymentBranchNum;
    private String marketPaymentBranchName;
    private String marketPaymentOperator;
    private String marketPaymentOrderNo;
    private String marketPaymentMemo;
    
    public String getMarketPaymentNo() {
        return this.marketPaymentNo;
    }

    public void setMarketPaymentNo(String marketPaymentNo) {
        this.marketPaymentNo = marketPaymentNo;
    }

    public String getSystemBookCode() {
        return this.systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public String getMarketPaymentFid() {
        return this.marketPaymentFid;
    }

    public void setMarketPaymentFid(String marketPaymentFid) {
        this.marketPaymentFid = marketPaymentFid;
    }

    public BigDecimal getMarketPaymentMoney() {
        return this.marketPaymentMoney;
    }

    public void setMarketPaymentMoney(BigDecimal marketPaymentMoney) {
        this.marketPaymentMoney = marketPaymentMoney;
    }

    public String getMarketPaymentCustomerNo() {
        return this.marketPaymentCustomerNo;
    }

    public void setMarketPaymentCustomerNo(String marketPaymentCustomerNo) {
        this.marketPaymentCustomerNo = marketPaymentCustomerNo;
    }

    public String getMarketPaymentCustomerName() {
        return this.marketPaymentCustomerName;
    }

    public void setMarketPaymentCustomerName(String marketPaymentCustomerName) {
        this.marketPaymentCustomerName = marketPaymentCustomerName;
    }

    public String getMarketPaymentToCustomerNo() {
        return this.marketPaymentToCustomerNo;
    }

    public void setMarketPaymentToCustomerNo(String marketPaymentToCustomerNo) {
        this.marketPaymentToCustomerNo = marketPaymentToCustomerNo;
    }

    public String getMarketPaymentToCustomerName() {
        return this.marketPaymentToCustomerName;
    }

    public void setMarketPaymentToCustomerName(String marketPaymentToCustomerName) {
        this.marketPaymentToCustomerName = marketPaymentToCustomerName;
    }

    public Date getMarketPaymentApplyTime() {
        return this.marketPaymentApplyTime;
    }

    public void setMarketPaymentApplyTime(Date marketPaymentApplyTime) {
        this.marketPaymentApplyTime = marketPaymentApplyTime;
    }

    public Date getMarketPaymentPayTime() {
        return this.marketPaymentPayTime;
    }

    public void setMarketPaymentPayTime(Date marketPaymentPayTime) {
        this.marketPaymentPayTime = marketPaymentPayTime;
    }

    public Integer getMarketPaymentStatus() {
        return this.marketPaymentStatus;
    }

    public void setMarketPaymentStatus(Integer marketPaymentStatus) {
        this.marketPaymentStatus = marketPaymentStatus;
    }

    public Integer getMarketPaymentBranchNum() {
        return this.marketPaymentBranchNum;
    }

    public void setMarketPaymentBranchNum(Integer marketPaymentBranchNum) {
        this.marketPaymentBranchNum = marketPaymentBranchNum;
    }

    public String getMarketPaymentBranchName() {
        return this.marketPaymentBranchName;
    }

    public void setMarketPaymentBranchName(String marketPaymentBranchName) {
        this.marketPaymentBranchName = marketPaymentBranchName;
    }

    public String getMarketPaymentOperator() {
        return this.marketPaymentOperator;
    }

    public void setMarketPaymentOperator(String marketPaymentOperator) {
        this.marketPaymentOperator = marketPaymentOperator;
    }
    
    public String getMarketPaymentOrderNo() {
        return marketPaymentOrderNo;
    }

    public void setMarketPaymentOrderNo(String marketPaymentOrderNo) {
        this.marketPaymentOrderNo = marketPaymentOrderNo;
    }

    public String getMarketPaymentMemo() {
        return marketPaymentMemo;
    }

    public void setMarketPaymentMemo(String marketPaymentMemo) {
        this.marketPaymentMemo = marketPaymentMemo;
    }
    
    
}