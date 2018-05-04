package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class InventoryProfitDTO implements Serializable {
    private static final long serialVersionUID = 7311721265937041210L;


    private int id;
    private Integer itemNum;
    private Integer itemMatrixNum;
    private String itemUnit;
    private BigDecimal profitAssitQty;
    private BigDecimal profitQty;
    private BigDecimal profitMoney;
    private BigDecimal itemSaleMoney;
    private BigDecimal saleQty;
    private BigDecimal saleMoney;
    private BigDecimal saleAssitQty;
    private BigDecimal adjustMoney;

    private BigDecimal profitRate;
    private String itemCategoryCode;
    private String itemCategoryName;
    private String itemCode;
    private String itemName;
    private String itemSpec;
    private String itemMatrixName;


    public InventoryProfitDTO() {
        this.profitAssitQty = BigDecimal.ZERO;
        this.profitQty = BigDecimal.ZERO;
        this.profitMoney = BigDecimal.ZERO;
        this.itemSaleMoney = BigDecimal.ZERO;
        this.saleQty = BigDecimal.ZERO;
        this.saleMoney = BigDecimal.ZERO;
        this.saleAssitQty = BigDecimal.ZERO;
        this.profitRate = BigDecimal.ZERO;
        this.adjustMoney = BigDecimal.ZERO;
    }

    public BigDecimal getAdjustMoney() {
        return adjustMoney;
    }

    public void setAdjustMoney(BigDecimal adjustMoney) {
        this.adjustMoney = adjustMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Integer getItemMatrixNum() {
        return itemMatrixNum;
    }

    public void setItemMatrixNum(Integer itemMatrixNum) {
        this.itemMatrixNum = itemMatrixNum;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public BigDecimal getProfitAssitQty() {
        return profitAssitQty;
    }

    public void setProfitAssitQty(BigDecimal profitAssitQty) {
        this.profitAssitQty = profitAssitQty;
    }

    public BigDecimal getProfitQty() {
        return profitQty;
    }

    public void setProfitQty(BigDecimal profitQty) {
        this.profitQty = profitQty;
    }

    public BigDecimal getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(BigDecimal profitMoney) {
        this.profitMoney = profitMoney;
    }

    public BigDecimal getItemSaleMoney() {
        return itemSaleMoney;
    }

    public void setItemSaleMoney(BigDecimal itemSaleMoney) {
        this.itemSaleMoney = itemSaleMoney;
    }

    public BigDecimal getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(BigDecimal saleQty) {
        this.saleQty = saleQty;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public BigDecimal getSaleAssitQty() {
        return saleAssitQty;
    }

    public void setSaleAssitQty(BigDecimal saleAssitQty) {
        this.saleAssitQty = saleAssitQty;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public String getItemCategoryCode() {
        return itemCategoryCode;
    }

    public void setItemCategoryCode(String itemCategoryCode) {
        this.itemCategoryCode = itemCategoryCode;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
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

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemMatrixName() {
        return itemMatrixName;
    }

    public void setItemMatrixName(String itemMatrixName) {
        this.itemMatrixName = itemMatrixName;
    }
}
