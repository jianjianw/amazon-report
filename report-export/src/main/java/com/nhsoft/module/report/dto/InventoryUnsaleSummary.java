package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class InventoryUnsaleSummary implements Serializable {


    private BigDecimal inventoryAmount;
    private BigDecimal inventoryMoney;
    private BigDecimal unSaleAmount;
    private BigDecimal unSaleMoney;

    public BigDecimal getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(BigDecimal inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public BigDecimal getInventoryMoney() {
        return inventoryMoney;
    }

    public void setInventoryMoney(BigDecimal inventoryMoney) {
        this.inventoryMoney = inventoryMoney;
    }

    public BigDecimal getUnSaleAmount() {
        return unSaleAmount;
    }

    public void setUnSaleAmount(BigDecimal unSaleAmount) {
        this.unSaleAmount = unSaleAmount;
    }

    public BigDecimal getUnSaleMoney() {
        return unSaleMoney;
    }

    public void setUnSaleMoney(BigDecimal unSaleMoney) {
        this.unSaleMoney = unSaleMoney;
    }
}
