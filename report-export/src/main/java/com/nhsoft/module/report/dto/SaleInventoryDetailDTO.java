package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleInventoryDetailDTO implements Serializable{

    private static final long serialVersionUID = 915998549862979618L;
    private String branchCode;
    private String branchName;
    private Integer branchNum;
    private BigDecimal salePrice;
    private BigDecimal salePrice2;
    private BigDecimal saleQty;
    private BigDecimal saleAveQty;// 日均销量
    private BigDecimal saleDays;// 可售天数
    private BigDecimal inventoryQty;
    private BigDecimal onloadQty;// 在途数量

    public SaleInventoryDetailDTO(){
        setSaleQty(BigDecimal.ZERO);
        setOnloadQty(BigDecimal.ZERO);
        setInventoryQty(BigDecimal.ZERO);
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSalePrice2() {
        return salePrice2;
    }

    public void setSalePrice2(BigDecimal salePrice2) {
        this.salePrice2 = salePrice2;
    }

    public BigDecimal getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(BigDecimal saleQty) {
        this.saleQty = saleQty;
    }

    public BigDecimal getSaleAveQty() {
        return saleAveQty;
    }

    public void setSaleAveQty(BigDecimal saleAveQty) {
        this.saleAveQty = saleAveQty;
    }

    public BigDecimal getSaleDays() {
        return saleDays;
    }

    public void setSaleDays(BigDecimal saleDays) {
        this.saleDays = saleDays;
    }

    public BigDecimal getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(BigDecimal inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public BigDecimal getOnloadQty() {
        return onloadQty;
    }

    public void setOnloadQty(BigDecimal onloadQty) {
        this.onloadQty = onloadQty;
    }
}
