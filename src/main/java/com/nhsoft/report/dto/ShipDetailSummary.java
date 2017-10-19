package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShipDetailSummary implements Serializable {

    private String orderId;     //发货单号
    private String companies;   //货运公司
    private BigDecimal money;       //运费金额
    private Date time;        //时间

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
