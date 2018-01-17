package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReceiveOrderInfoDTO implements Serializable {
    private static final long serialVersionUID = 6869863939574085909L;
    private String orderFid;
    private Date auditTime;
    private String auditor;
    private BigDecimal receiveOrderPrice;

    private Integer itemNum;
    private BigDecimal receiveQty;  //收货数量
    private BigDecimal receiveMoney;  //收货金额

    public String getOrderFid() {
        return orderFid;
    }

    public void setOrderFid(String orderFid) {
        this.orderFid = orderFid;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public BigDecimal getReceiveOrderPrice() {
        return receiveOrderPrice;
    }

    public void setReceiveOrderPrice(BigDecimal receiveOrderPrice) {
        this.receiveOrderPrice = receiveOrderPrice;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getReceiveQty() {
        return receiveQty;
    }

    public void setReceiveQty(BigDecimal receiveQty) {
        this.receiveQty = receiveQty;
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }
}
