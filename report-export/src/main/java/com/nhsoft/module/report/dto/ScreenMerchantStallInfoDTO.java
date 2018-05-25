package com.nhsoft.module.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhsoft.module.report.annotation.ReportInfo;
import com.nhsoft.module.report.annotation.ReportKey;

import java.math.BigDecimal;
import java.util.Date;

public class ScreenMerchantStallInfoDTO {
    @ReportKey
    private Integer merchantNum;
    @ReportInfo
    private String merchantName;
    @ReportKey
    private Integer stallNum;
    @ReportInfo
    private String stallName;
    @ReportInfo
    private String merchantLinkman;
    @ReportInfo
    private String merchantPhone;
    @ReportInfo
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date merchantContractEnd;
    private BigDecimal thisMonthSaleMoney = BigDecimal.ZERO;
    private BigDecimal lastMonthSaleMoney = BigDecimal.ZERO;
    private BigDecimal saleMoney;
    @ReportInfo
    private String bizMonth;

    public Integer getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(Integer merchantNum) {
        this.merchantNum = merchantNum;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getStallNum() {
        return stallNum;
    }

    public void setStallNum(Integer stallNum) {
        this.stallNum = stallNum;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public String getMerchantLinkman() {
        return merchantLinkman;
    }

    public void setMerchantLinkman(String merchantLinkman) {
        this.merchantLinkman = merchantLinkman;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public Date getMerchantContractEnd() {
        return merchantContractEnd;
    }

    public void setMerchantContractEnd(Date merchantContractEnd) {
        this.merchantContractEnd = merchantContractEnd;
    }

    public BigDecimal getThisMonthSaleMoney() {
        return thisMonthSaleMoney;
    }

    public void setThisMonthSaleMoney(BigDecimal thisMonthSaleMoney) {
        this.thisMonthSaleMoney = thisMonthSaleMoney;
    }

    public BigDecimal getLastMonthSaleMoney() {
        return lastMonthSaleMoney;
    }

    public void setLastMonthSaleMoney(BigDecimal lastMonthSaleMoney) {
        this.lastMonthSaleMoney = lastMonthSaleMoney;
    }

    public String getBizMonth() {
        return bizMonth;
    }

    public void setBizMonth(String bizMonth) {
        this.bizMonth = bizMonth;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }
}
