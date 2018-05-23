package com.nhsoft.module.report.model;

import com.nhsoft.module.report.query.State;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class MerchantContract implements Serializable {
    @Id
    private String merchantContractFid;
    private String systemBookCode;
    private Integer branchNum;
    private Integer merchantNum;
    private String merchantContractBillNo;
    private Date merchantContractDate;
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="stateCode", column = @Column(name="merchantContractStateCode")),
            @AttributeOverride(name="stateName", column = @Column(name="merchantContractStateName")) } )
    private State state;
    private String merchantContractCreator;
    private Date merchantContractCreateTime;
    private String merchantContractAuditor;
    private Date merchantContractAuditTime;
    private String merchantContractCancelOperator;
    private Date merchantContractCancelTime;
    private String merchantContractMemo;
    private Date merchantContractStart;
    private Date merchantContractEnd;
    private BigDecimal merchantContractMoney;
    private String merchantContractType;
    private BigDecimal merchantContractRent;
    private BigDecimal merchantContractRate;
    private Boolean merchantContractUseCategory;
    private String merchantContractPayPeriod;

    public String getMerchantContractFid() {
        return merchantContractFid;
    }

    public void setMerchantContractFid(String merchantContractFid) {
        this.merchantContractFid = merchantContractFid;
    }

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

    public Integer getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(Integer merchantNum) {
        this.merchantNum = merchantNum;
    }

    public String getMerchantContractBillNo() {
        return merchantContractBillNo;
    }

    public void setMerchantContractBillNo(String merchantContractBillNo) {
        this.merchantContractBillNo = merchantContractBillNo;
    }

    public Date getMerchantContractDate() {
        return merchantContractDate;
    }

    public void setMerchantContractDate(Date merchantContractDate) {
        this.merchantContractDate = merchantContractDate;
    }

    public String getMerchantContractCreator() {
        return merchantContractCreator;
    }

    public void setMerchantContractCreator(String merchantContractCreator) {
        this.merchantContractCreator = merchantContractCreator;
    }

    public Date getMerchantContractCreateTime() {
        return merchantContractCreateTime;
    }

    public void setMerchantContractCreateTime(Date merchantContractCreateTime) {
        this.merchantContractCreateTime = merchantContractCreateTime;
    }

    public String getMerchantContractAuditor() {
        return merchantContractAuditor;
    }

    public void setMerchantContractAuditor(String merchantContractAuditor) {
        this.merchantContractAuditor = merchantContractAuditor;
    }

    public Date getMerchantContractAuditTime() {
        return merchantContractAuditTime;
    }

    public void setMerchantContractAuditTime(Date merchantContractAuditTime) {
        this.merchantContractAuditTime = merchantContractAuditTime;
    }

    public String getMerchantContractCancelOperator() {
        return merchantContractCancelOperator;
    }

    public void setMerchantContractCancelOperator(String merchantContractCancelOperator) {
        this.merchantContractCancelOperator = merchantContractCancelOperator;
    }

    public Date getMerchantContractCancelTime() {
        return merchantContractCancelTime;
    }

    public void setMerchantContractCancelTime(Date merchantContractCancelTime) {
        this.merchantContractCancelTime = merchantContractCancelTime;
    }

    public String getMerchantContractMemo() {
        return merchantContractMemo;
    }

    public void setMerchantContractMemo(String merchantContractMemo) {
        this.merchantContractMemo = merchantContractMemo;
    }

    public Date getMerchantContractStart() {
        return merchantContractStart;
    }

    public void setMerchantContractStart(Date merchantContractStart) {
        this.merchantContractStart = merchantContractStart;
    }

    public Date getMerchantContractEnd() {
        return merchantContractEnd;
    }

    public void setMerchantContractEnd(Date merchantContractEnd) {
        this.merchantContractEnd = merchantContractEnd;
    }

    public BigDecimal getMerchantContractMoney() {
        return merchantContractMoney;
    }

    public void setMerchantContractMoney(BigDecimal merchantContractMoney) {
        this.merchantContractMoney = merchantContractMoney;
    }

    public String getMerchantContractType() {
        return merchantContractType;
    }

    public void setMerchantContractType(String merchantContractType) {
        this.merchantContractType = merchantContractType;
    }

    public BigDecimal getMerchantContractRent() {
        return merchantContractRent;
    }

    public void setMerchantContractRent(BigDecimal merchantContractRent) {
        this.merchantContractRent = merchantContractRent;
    }

    public BigDecimal getMerchantContractRate() {
        return merchantContractRate;
    }

    public void setMerchantContractRate(BigDecimal merchantContractRate) {
        this.merchantContractRate = merchantContractRate;
    }

    public Boolean getMerchantContractUseCategory() {
        return merchantContractUseCategory;
    }

    public void setMerchantContractUseCategory(Boolean merchantContractUseCategory) {
        this.merchantContractUseCategory = merchantContractUseCategory;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getMerchantContractPayPeriod() {
        return merchantContractPayPeriod;
    }

    public void setMerchantContractPayPeriod(String merchantContractPayPeriod) {
        this.merchantContractPayPeriod = merchantContractPayPeriod;
    }
}
