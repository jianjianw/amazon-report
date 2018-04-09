package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemInventoryTrendSummary implements Serializable {
    private static final long serialVersionUID = 5182485469143457449L;

    private String categoryCode;
    private String categoryName;
    private Integer inventoryAmount;
    private Integer unInventoryAmount;
    private Integer totalAmount;
    private List<PosItemDTO> posItems = new ArrayList<>();


    public ItemInventoryTrendSummary() {
        this.inventoryAmount = 0;
        this.unInventoryAmount = 0;
        this.totalAmount = 0;
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

    public Integer getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Integer inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public Integer getUnInventoryAmount() {
        return unInventoryAmount;
    }

    public void setUnInventoryAmount(Integer unInventoryAmount) {
        this.unInventoryAmount = unInventoryAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PosItemDTO> getPosItems() {
        return posItems;
    }

    public void setPosItems(List<PosItemDTO> posItems) {
        this.posItems = posItems;
    }
}
