package com.nhsoft.module.report.dto;


import com.nhsoft.module.report.annotation.ReportInfo;
import com.nhsoft.module.report.annotation.ReportKey;

import java.math.BigDecimal;

public class ScreenMerchantDTO {
    @ReportKey
    private Integer merchantNum;
    @ReportInfo
    private String merchantName;
    private BigDecimal todaySaleMoney = BigDecimal.ZERO;
    private BigDecimal thisMonthSaleMoney = BigDecimal.ZERO;
    private BigDecimal thisQuarterSaleMoney = BigDecimal.ZERO;
    private BigDecimal thisYearSaleMoney = BigDecimal.ZERO;

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

    public BigDecimal getTodaySaleMoney() {
        return todaySaleMoney;
    }

    public void setTodaySaleMoney(BigDecimal todaySaleMoney) {
        this.todaySaleMoney = todaySaleMoney;
    }

    public BigDecimal getThisMonthSaleMoney() {
        return thisMonthSaleMoney;
    }

    public void setThisMonthSaleMoney(BigDecimal thisMonthSaleMoney) {
        this.thisMonthSaleMoney = thisMonthSaleMoney;
    }

    public BigDecimal getThisQuarterSaleMoney() {
        return thisQuarterSaleMoney;
    }

    public void setThisQuarterSaleMoney(BigDecimal thisQuarterSaleMoney) {
        this.thisQuarterSaleMoney = thisQuarterSaleMoney;
    }

    public BigDecimal getThisYearSaleMoney() {
        return thisYearSaleMoney;
    }

    public void setThisYearSaleMoney(BigDecimal thisYearSaleMoney) {
        this.thisYearSaleMoney = thisYearSaleMoney;
    }
}
