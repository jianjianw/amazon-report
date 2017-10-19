package com.nhsoft.report.dto;


import java.io.Serializable;
import java.math.BigDecimal;

public class ShipMoneySummary implements Serializable {

    private String companies;       //货运公司
    private BigDecimal  money;          //运费金额

    public String getCompanies() {
        return companies;
    }

    public void setCompanies(String companies) {
        this.companies = companies;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
