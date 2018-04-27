package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitByBranchAndItemDTO implements Serializable {
    private static final long serialVersionUID = -4794442868712659775L;

    private Integer branchNum;
    private Integer itemNum;
    private int matrixNum;
    private String categoryCode;//商品类别代码
    private String categoryName;//商品类别名称
    private String itemCode;//商品代码
    private String itemName;//商品名称
    private String spec;//规格
    private String unit;//计量单位
    private BigDecimal profit;//毛利
    private BigDecimal saleCost;//销售成本
    private BigDecimal saleNums;//销售数量
    private BigDecimal saleMoney;//销售金额
    private Integer itemType;//商品属性

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

    public int getMatrixNum() {
        return matrixNum;
    }

    public void setMatrixNum(int matrixNum) {
        this.matrixNum = matrixNum;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getSaleCost() {
        return saleCost;
    }

    public void setSaleCost(BigDecimal saleCost) {
        this.saleCost = saleCost;
    }

    public BigDecimal getSaleNums() {
        return saleNums;
    }

    public void setSaleNums(BigDecimal saleNums) {
        this.saleNums = saleNums;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
}
