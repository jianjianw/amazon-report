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
public class StallPromotion implements Serializable{
    private static final long serialVersionUID = 6114405259430695908L;

    @Id
    private String policyPromotionNo;
    private String systemBookCode;
    private Integer branchNum;
    private Integer merchantNum;
    private Integer stallNum;
    private Date policyPromotionDateFrom;
    private Date policyPromotionDateTo;
    private Date policyPromotionTimeFrom;
    private Date policyPromotionTimeTo;
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="stateCode", column = @Column(name="policyPromotionStateCode")),
            @AttributeOverride(name="stateName", column = @Column(name="policyPromotionStateName")) } )
    private State state;
    private Date policyPromotionCreateTime;
    private String policyPromotionCreator;
    private Date policyPromotionAuditTime;
    private String policyPromotionAuditor;
    private String policyPromotionMemo;
    private Date policyPromotionCancelTime;
    private String policyPromotionCancelOperator;
    private Boolean policyPromotionCardOnly;
    private String policyPromotionCardType;
    private Boolean policyPromotionCardOnce;
    private Boolean stallPromotionAll;
    private BigDecimal policyPromotionDiscount;
    private String policyPromotionCategory;
    private String policyPromotionExceptItem;
    private BigDecimal policyPromotionRate;

    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "policyPromotionNo", updatable=false, insertable=false)
    private List<StallPromotionDetail> details;

    public String getPolicyPromotionNo() {
        return policyPromotionNo;
    }

    public void setPolicyPromotionNo(String policyPromotionNo) {
        this.policyPromotionNo = policyPromotionNo;
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

    public Integer getStallNum() {
        return stallNum;
    }

    public void setStallNum(Integer stallNum) {
        this.stallNum = stallNum;
    }

    public Date getPolicyPromotionDateFrom() {
        return policyPromotionDateFrom;
    }

    public void setPolicyPromotionDateFrom(Date policyPromotionDateFrom) {
        this.policyPromotionDateFrom = policyPromotionDateFrom;
    }

    public Date getPolicyPromotionDateTo() {
        return policyPromotionDateTo;
    }

    public void setPolicyPromotionDateTo(Date policyPromotionDateTo) {
        this.policyPromotionDateTo = policyPromotionDateTo;
    }

    public Date getPolicyPromotionTimeFrom() {
        return policyPromotionTimeFrom;
    }

    public void setPolicyPromotionTimeFrom(Date policyPromotionTimeFrom) {
        this.policyPromotionTimeFrom = policyPromotionTimeFrom;
    }

    public Date getPolicyPromotionTimeTo() {
        return policyPromotionTimeTo;
    }

    public void setPolicyPromotionTimeTo(Date policyPromotionTimeTo) {
        this.policyPromotionTimeTo = policyPromotionTimeTo;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getPolicyPromotionCreateTime() {
        return policyPromotionCreateTime;
    }

    public void setPolicyPromotionCreateTime(Date policyPromotionCreateTime) {
        this.policyPromotionCreateTime = policyPromotionCreateTime;
    }

    public String getPolicyPromotionCreator() {
        return policyPromotionCreator;
    }

    public void setPolicyPromotionCreator(String policyPromotionCreator) {
        this.policyPromotionCreator = policyPromotionCreator;
    }

    public Date getPolicyPromotionAuditTime() {
        return policyPromotionAuditTime;
    }

    public void setPolicyPromotionAuditTime(Date policyPromotionAuditTime) {
        this.policyPromotionAuditTime = policyPromotionAuditTime;
    }

    public String getPolicyPromotionAuditor() {
        return policyPromotionAuditor;
    }

    public void setPolicyPromotionAuditor(String policyPromotionAuditor) {
        this.policyPromotionAuditor = policyPromotionAuditor;
    }

    public String getPolicyPromotionMemo() {
        return policyPromotionMemo;
    }

    public void setPolicyPromotionMemo(String policyPromotionMemo) {
        this.policyPromotionMemo = policyPromotionMemo;
    }

    public Date getPolicyPromotionCancelTime() {
        return policyPromotionCancelTime;
    }

    public void setPolicyPromotionCancelTime(Date policyPromotionCancelTime) {
        this.policyPromotionCancelTime = policyPromotionCancelTime;
    }

    public String getPolicyPromotionCancelOperator() {
        return policyPromotionCancelOperator;
    }

    public void setPolicyPromotionCancelOperator(String policyPromotionCancelOperator) {
        this.policyPromotionCancelOperator = policyPromotionCancelOperator;
    }

    public Boolean getPolicyPromotionCardOnly() {
        return policyPromotionCardOnly;
    }

    public void setPolicyPromotionCardOnly(Boolean policyPromotionCardOnly) {
        this.policyPromotionCardOnly = policyPromotionCardOnly;
    }

    public String getPolicyPromotionCardType() {
        return policyPromotionCardType;
    }

    public void setPolicyPromotionCardType(String policyPromotionCardType) {
        this.policyPromotionCardType = policyPromotionCardType;
    }

    public List<StallPromotionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<StallPromotionDetail> details) {
        this.details = details;
    }

    public Boolean getPolicyPromotionCardOnce() {
        return policyPromotionCardOnce;
    }

    public void setPolicyPromotionCardOnce(Boolean policyPromotionCardOnce) {
        this.policyPromotionCardOnce = policyPromotionCardOnce;
    }

    public Boolean getStallPromotionAll() {
        return stallPromotionAll;
    }

    public void setStallPromotionAll(Boolean stallPromotionAll) {
        this.stallPromotionAll = stallPromotionAll;
    }

    public BigDecimal getPolicyPromotionDiscount() {
        return policyPromotionDiscount;
    }

    public void setPolicyPromotionDiscount(BigDecimal policyPromotionDiscount) {
        this.policyPromotionDiscount = policyPromotionDiscount;
    }

    public String getPolicyPromotionCategory() {
        return policyPromotionCategory;
    }

    public void setPolicyPromotionCategory(String policyPromotionCategory) {
        this.policyPromotionCategory = policyPromotionCategory;
    }

    public String getPolicyPromotionExceptItem() {
        return policyPromotionExceptItem;
    }

    public void setPolicyPromotionExceptItem(String policyPromotionExceptItem) {
        this.policyPromotionExceptItem = policyPromotionExceptItem;
    }

    public BigDecimal getPolicyPromotionRate() {
        return policyPromotionRate;
    }

    public void setPolicyPromotionRate(BigDecimal policyPromotionRate) {
        this.policyPromotionRate = policyPromotionRate;
    }
}
