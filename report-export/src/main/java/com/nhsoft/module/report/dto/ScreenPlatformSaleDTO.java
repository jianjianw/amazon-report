package com.nhsoft.module.report.dto;

import java.math.BigDecimal;

public class ScreenPlatformSaleDTO {
    private String platformName;
    private BigDecimal saleMoney;

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }
}
