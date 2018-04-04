package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferItemDetailSummary implements Serializable {
    private static final long serialVersionUID = -7436816208388598904L;


    private Integer itemNum;
    private String itemCode;
    private String itemName;
    private BigDecimal transferQty;
    private BigDecimal transferMoney;
    private BigDecimal receiveQty;
    private BigDecimal requestQty;


    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getTransferQty() {
        return transferQty;
    }

    public void setTransferQty(BigDecimal transferQty) {
        this.transferQty = transferQty;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public BigDecimal getReceiveQty() {
        return receiveQty;
    }

    public void setReceiveQty(BigDecimal receiveQty) {
        this.receiveQty = receiveQty;
    }

    public BigDecimal getRequestQty() {
        return requestQty;
    }

    public void setRequestQty(BigDecimal requestQty) {
        this.requestQty = requestQty;
    }
}
