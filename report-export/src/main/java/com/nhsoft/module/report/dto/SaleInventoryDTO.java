package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleInventoryDTO implements Serializable {

    private static final long serialVersionUID = -1726114731163404573L;
    private Integer itemNum;
    private String itemCode;
    private String itemName;
    private String itemCategory;
    private String itemCategoryCode;
    private String itemCostMode;
    private String itemBarcode;
    private BigDecimal salePrice;
    private BigDecimal salePrice2;
    private BigDecimal saleQty;
    private BigDecimal saleAveQty;// 日均销量
    private BigDecimal inventoryQty;

    public SaleInventoryDTO(){
        saleQty = BigDecimal.ZERO;
        inventoryQty = BigDecimal.ZERO;

    }


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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemCategoryCode() {
        return itemCategoryCode;
    }

    public void setItemCategoryCode(String itemCategoryCode) {
        this.itemCategoryCode = itemCategoryCode;
    }

    public String getItemCostMode() {
        return itemCostMode;
    }

    public void setItemCostMode(String itemCostMode) {
        this.itemCostMode = itemCostMode;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
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

    public BigDecimal getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(BigDecimal inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

}
