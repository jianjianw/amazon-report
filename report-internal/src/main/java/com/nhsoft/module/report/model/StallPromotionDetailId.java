package com.nhsoft.module.report.model;

import java.io.Serializable;


public class StallPromotionDetailId implements Serializable{
    private static final long serialVersionUID = 542286150122427009L;

    private String policyPromotionNo;
    private Integer policyPromotionDetailNum;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((policyPromotionNo == null) ? 0 : policyPromotionNo.hashCode());
        result = prime * result + ((policyPromotionDetailNum == null) ? 0 : policyPromotionDetailNum.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StallPromotionDetailId other = (StallPromotionDetailId) obj;
        if (policyPromotionNo == null) {
            if (other.policyPromotionNo != null)
                return false;
        } else if (!policyPromotionNo.equals(other.policyPromotionNo))
            return false;
        if (policyPromotionDetailNum == null) {
            if (other.policyPromotionDetailNum != null)
                return false;
        } else if (!policyPromotionDetailNum.equals(other.policyPromotionDetailNum))
            return false;
        return true;
    }

}
