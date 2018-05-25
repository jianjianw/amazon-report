package com.nhsoft.module.report.dto;

import java.math.BigDecimal;

public class ScreenItemDTO {
    private Integer itemNum;
    private String itemName;
    private Integer merchantNum;
    private String merchantName;
    private Integer stallNum;
    private String stallName;
    private BigDecimal stallMatrixRegularPrice;

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

    public BigDecimal getStallMatrixRegularPrice() {
        return stallMatrixRegularPrice;
    }

    public void setStallMatrixRegularPrice(BigDecimal stallMatrixRegularPrice) {
        this.stallMatrixRegularPrice = stallMatrixRegularPrice;
    }
}
