package com.nhsoft.module.report.dto;


import java.io.Serializable;
import java.math.BigDecimal;

public class ShipOrderSummary implements Serializable {

    private String company;       //货运公司
    private BigDecimal  carriageMoney;          //运费金额

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigDecimal getCarriageMoney() {
        return carriageMoney;
    }

    public void setCarriageMoney(BigDecimal carriageMoney) {
        this.carriageMoney = carriageMoney;
    }
}
