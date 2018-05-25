package com.nhsoft.module.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ScreenTraceDTO {
    private Integer merchantNum;
    private String merchantName;
    private Integer stallNum;
    private String stallName;
    private Integer itemNum;
    private String itemName;
    private Integer supplierNum;
    private String supplierName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date itemTraceLogProductDate;
    private String itemTraceLogCategory;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date itemTraceLogDate;
    private String itemTraceLogResult;

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

    public String getItemTraceLogResult() {
        return itemTraceLogResult;
    }

    public void setItemTraceLogResult(String itemTraceLogResult) {
        this.itemTraceLogResult = itemTraceLogResult;
    }

    public Integer getStallNum() {
        return stallNum;
    }

    public void setStallNum(Integer stallNum) {
        this.stallNum = stallNum;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(Integer supplierNum) {
        this.supplierNum = supplierNum;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getItemTraceLogProductDate() {
        return itemTraceLogProductDate;
    }

    public void setItemTraceLogProductDate(Date itemTraceLogProductDate) {
        this.itemTraceLogProductDate = itemTraceLogProductDate;
    }

    public String getItemTraceLogCategory() {
        return itemTraceLogCategory;
    }

    public void setItemTraceLogCategory(String itemTraceLogCategory) {
        this.itemTraceLogCategory = itemTraceLogCategory;
    }
}
