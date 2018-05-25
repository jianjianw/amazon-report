package com.nhsoft.module.report.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(MerchantContractStall.MerchantContractStallId.class)
public class MerchantContractStall implements Serializable {

    private static final long serialVersionUID = 4646869953929272696L;

    public static class MerchantContractStallId implements Serializable {
        private static final long serialVersionUID = 6274855618313727388L;
        private String merchantContractFid;
        private Integer stallNum;

        public String getMerchantContractFid() {
            return merchantContractFid;
        }

        public void setMerchantContractFid(String merchantContractFid) {
            this.merchantContractFid = merchantContractFid;
        }

        public Integer getStallNum() {
            return stallNum;
        }

        public void setStallNum(Integer stallNum) {
            this.stallNum = stallNum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MerchantContractStallId that = (MerchantContractStallId) o;

            if (merchantContractFid != null ? !merchantContractFid.equals(that.merchantContractFid) : that.merchantContractFid != null)
                return false;
            return stallNum != null ? stallNum.equals(that.stallNum) : that.stallNum == null;
        }

        @Override
        public int hashCode() {
            int result = merchantContractFid != null ? merchantContractFid.hashCode() : 0;
            result = 31 * result + (stallNum != null ? stallNum.hashCode() : 0);
            return result;
        }
    }

    @Id
    private String merchantContractFid;
    @Id
    private Integer stallNum;
    private String systemBookCode;
    private Integer branchNum;

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

    public String getMerchantContractFid() {
        return merchantContractFid;
    }

    public void setMerchantContractFid(String merchantContractFid) {
        this.merchantContractFid = merchantContractFid;
    }

    public Integer getStallNum() {
        return stallNum;
    }

    public void setStallNum(Integer stallNum) {
        this.stallNum = stallNum;
    }
}
