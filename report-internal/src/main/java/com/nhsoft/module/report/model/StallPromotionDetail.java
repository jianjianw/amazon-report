package com.nhsoft.module.report.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@IdClass(StallPromotionDetailId.class)
public class StallPromotionDetail implements Serializable {
    private static final long serialVersionUID = -5955736652364886027L;

    @Id
    private String policyPromotionNo;
    @Id
    private Integer policyPromotionDetailNum;
    private Integer itemNum;
    private BigDecimal policyPromotionDetailStdPrice;
    private BigDecimal policyPromotionDetailSpecialPrice;
    private String policyPromotionDetailMemo;
    private BigDecimal policyPromotionDetailBillLimit;
    private BigDecimal policyPromotionDetailRate;
    private Integer itemGradeNum;
    private String systemBookCode;
    private Integer branchNum;

    public String getPolicyPromotionNo() {
        return policyPromotionNo;
    }

    public void setPolicyPromotionNo(String policyPromotionNo) {
        this.policyPromotionNo = policyPromotionNo;
    }

    public Integer getPolicyPromotionDetailNum() {
        return policyPromotionDetailNum;
    }

    public void setPolicyPromotionDetailNum(Integer policyPromotionDetailNum) {
        this.policyPromotionDetailNum = policyPromotionDetailNum;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getPolicyPromotionDetailStdPrice() {
        return policyPromotionDetailStdPrice;
    }

    public void setPolicyPromotionDetailStdPrice(BigDecimal policyPromotionDetailStdPrice) {
        this.policyPromotionDetailStdPrice = policyPromotionDetailStdPrice;
    }

    public BigDecimal getPolicyPromotionDetailSpecialPrice() {
        return policyPromotionDetailSpecialPrice;
    }

    public void setPolicyPromotionDetailSpecialPrice(BigDecimal policyPromotionDetailSpecialPrice) {
        this.policyPromotionDetailSpecialPrice = policyPromotionDetailSpecialPrice;
    }

    public String getPolicyPromotionDetailMemo() {
        return policyPromotionDetailMemo;
    }

    public void setPolicyPromotionDetailMemo(String policyPromotionDetailMemo) {
        this.policyPromotionDetailMemo = policyPromotionDetailMemo;
    }

    public Integer getItemGradeNum() {
        return itemGradeNum;
    }

    public void setItemGradeNum(Integer itemGradeNum) {
        this.itemGradeNum = itemGradeNum;
    }

    public BigDecimal getPolicyPromotionDetailBillLimit() {
        return policyPromotionDetailBillLimit;
    }

    public void setPolicyPromotionDetailBillLimit(BigDecimal policyPromotionDetailBillLimit) {
        this.policyPromotionDetailBillLimit = policyPromotionDetailBillLimit;
    }

    public BigDecimal getPolicyPromotionDetailRate() {
        return policyPromotionDetailRate;
    }

    public void setPolicyPromotionDetailRate(BigDecimal policyPromotionDetailRate) {
        this.policyPromotionDetailRate = policyPromotionDetailRate;
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
}
