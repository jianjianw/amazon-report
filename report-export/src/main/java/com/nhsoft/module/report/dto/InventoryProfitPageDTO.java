package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class InventoryProfitPageDTO implements Serializable {
    private static final long serialVersionUID = 3127619689745595017L;


    private Integer count;
    private List<InventoryProfitDTO> data;



    private BigDecimal profitQtySum;
    private BigDecimal profitAssitQtySum;
    private BigDecimal profitMoneySum;
    private BigDecimal saleQtySum;
    private BigDecimal saleAssitQtySum;
    private BigDecimal saleMoneySum;
    private BigDecimal itemSaleMoneySum;
    private BigDecimal profitRateSum;
    private BigDecimal adjustMoneySum;

    public BigDecimal getAdjustMoneySum() {
        return adjustMoneySum;
    }

    public void setAdjustMoneySum(BigDecimal adjustMoneySum) {
        this.adjustMoneySum = adjustMoneySum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<InventoryProfitDTO> getData() {
        return data;
    }

    public void setData(List<InventoryProfitDTO> data) {
        this.data = data;
    }

    public BigDecimal getProfitQtySum() {
        return profitQtySum;
    }

    public void setProfitQtySum(BigDecimal profitQtySum) {
        this.profitQtySum = profitQtySum;
    }

    public BigDecimal getProfitAssitQtySum() {
        return profitAssitQtySum;
    }

    public void setProfitAssitQtySum(BigDecimal profitAssitQtySum) {
        this.profitAssitQtySum = profitAssitQtySum;
    }

    public BigDecimal getProfitMoneySum() {
        return profitMoneySum;
    }

    public void setProfitMoneySum(BigDecimal profitMoneySum) {
        this.profitMoneySum = profitMoneySum;
    }

    public BigDecimal getSaleQtySum() {
        return saleQtySum;
    }

    public void setSaleQtySum(BigDecimal saleQtySum) {
        this.saleQtySum = saleQtySum;
    }

    public BigDecimal getSaleAssitQtySum() {
        return saleAssitQtySum;
    }

    public void setSaleAssitQtySum(BigDecimal saleAssitQtySum) {
        this.saleAssitQtySum = saleAssitQtySum;
    }

    public BigDecimal getSaleMoneySum() {
        return saleMoneySum;
    }

    public void setSaleMoneySum(BigDecimal saleMoneySum) {
        this.saleMoneySum = saleMoneySum;
    }

    public BigDecimal getItemSaleMoneySum() {
        return itemSaleMoneySum;
    }

    public void setItemSaleMoneySum(BigDecimal itemSaleMoneySum) {
        this.itemSaleMoneySum = itemSaleMoneySum;
    }

    public BigDecimal getProfitRateSum() {
        return profitRateSum;
    }

    public void setProfitRateSum(BigDecimal profitRateSum) {
        this.profitRateSum = profitRateSum;
    }

}
