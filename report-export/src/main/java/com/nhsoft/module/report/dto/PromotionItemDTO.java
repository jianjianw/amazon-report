package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PromotionItemDTO implements Serializable {
    private static final long serialVersionUID = 6742846820580570317L;



    private Integer branchNum; // 分店编号
    private String itemCategoryCode; // 商品类别代码
    private Integer itemNum; // 商品项目
    private String itemCode; // 商品代码
    private String itemName; // 商品名称
    private String itemBarcode; // 商品条码
    private String itemSpec; // 规格
    private String itemUnit; // 计量单位
    private BigDecimal itemRegularPrice; // 标准售价
    private BigDecimal itemAveragePrice; // 平均售价
    private BigDecimal saleMoney; // 销售金额
    private BigDecimal allowProfitMoney; // 让利金额
    private BigDecimal saleAmount; // 销售数量
    private BigDecimal saleCost; // 销售成本

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }


    public String getItemCategoryCode() {
        return itemCategoryCode;
    }

    public void setItemCategoryCode(String itemCategoryCode) {
        this.itemCategoryCode = itemCategoryCode;
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

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public BigDecimal getItemRegularPrice() {
        return itemRegularPrice;
    }

    public void setItemRegularPrice(BigDecimal itemRegularPrice) {
        this.itemRegularPrice = itemRegularPrice;
    }

    public BigDecimal getItemAveragePrice() {
        return itemAveragePrice;
    }

    public void setItemAveragePrice(BigDecimal itemAveragePrice) {
        this.itemAveragePrice = itemAveragePrice;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public BigDecimal getSaleCost() {
        return saleCost;
    }

    public void setSaleCost(BigDecimal saleCost) {
        this.saleCost = saleCost;
    }

    public BigDecimal getAllowProfitMoney() {
        return allowProfitMoney;
    }

    public void setAllowProfitMoney(BigDecimal allowProfitMoney) {
        this.allowProfitMoney = allowProfitMoney;
    }
}
