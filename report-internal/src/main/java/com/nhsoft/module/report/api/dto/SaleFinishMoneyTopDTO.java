package com.nhsoft.module.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleFinishMoneyTopDTO implements Serializable {
    private Integer num;//分店或区域号
    private String name;//分店或区域名
    private BigDecimal finishMoneyRate;//完成率

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFinishMoneyRate() {
        return finishMoneyRate;
    }

    public void setFinishMoneyRate(BigDecimal finishMoneyRate) {
        this.finishMoneyRate = finishMoneyRate;
    }
}
