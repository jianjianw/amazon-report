package com.nhsoft.module.report.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Merchant implements Serializable{
    private static final long serialVersionUID = -7990473525148157659L;
    @Id
    private Integer merchantNum;
    private String systemBookCode;
    private Integer branchNum;
    private String merchantCode;
    private String merchantLinkman;
    private String merchantName;
    private String merchantCardNo;
    private String merchantPhone;
    private String merchantAddress;
    private String merchantCategory;
    private String merchantMemo;
    private Boolean merchantDelTag;
    private Integer merchantStatus;

    public Integer getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(Integer merchantNum) {
        this.merchantNum = merchantNum;
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantLinkman() {
        return merchantLinkman;
    }

    public void setMerchantLinkman(String merchantLinkman) {
        this.merchantLinkman = merchantLinkman;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCardNo() {
        return merchantCardNo;
    }

    public void setMerchantCardNo(String merchantCardNo) {
        this.merchantCardNo = merchantCardNo;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public void setMerchantCategory(String merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public String getMerchantMemo() {
        return merchantMemo;
    }

    public void setMerchantMemo(String merchantMemo) {
        this.merchantMemo = merchantMemo;
    }

    public Boolean getMerchantDelTag() {
        return merchantDelTag;
    }

    public void setMerchantDelTag(Boolean merchantDelTag) {
        this.merchantDelTag = merchantDelTag;
    }

    public Integer getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(Integer merchantStatus) {
        this.merchantStatus = merchantStatus;
    }
}
