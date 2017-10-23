package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShipDetailDTO implements Serializable {

    private String orderId;     //发货单号
    private String company;   //货运公司
    private BigDecimal carriageMoney;       //运费金额
    private Date time;        //时间

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
