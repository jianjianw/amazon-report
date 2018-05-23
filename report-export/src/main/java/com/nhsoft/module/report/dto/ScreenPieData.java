package com.nhsoft.module.report.dto;

import java.math.BigDecimal;

public class ScreenPieData {
    private String x;
    private Integer y;
    private BigDecimal z;

    public ScreenPieData(String x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public ScreenPieData(String x, BigDecimal z) {
        this.x = x;
        this.z = z;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public BigDecimal getZ() {
        return z;
    }

    public void setZ(BigDecimal z) {
        this.z = z;
    }
}
