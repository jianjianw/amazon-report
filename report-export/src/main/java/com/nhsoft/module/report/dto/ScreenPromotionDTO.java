package com.nhsoft.module.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ScreenPromotionDTO {
    private Integer itemNum;
    private String itemName;
    private BigDecimal policyPromotionDetailStdPrice;
    private BigDecimal policyPromotionDetailSpecialPrice;
    private String policyPromotionDate;
    private Integer stallNum;
    private String stallName;

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

    public BigDecimal getPolicyPromotionDetailStdPrice() {
        return policyPromotionDetailStdPrice;
    }

    public void setPolicyPromotionDetailStdPrice(BigDecimal policyPromotionDetailStdPrice) {
        this.policyPromotionDetailStdPrice = policyPromotionDetailStdPrice;
    }

    public BigDecimal getPolicyPromotionDetailSpecialPrice() {
        return policyPromotionDetailSpecialPrice;
    }

    public void setPolicyPromotionDetailSpecialPrice(BigDecimal policyPromotionDetailSpecialPrice) {
        this.policyPromotionDetailSpecialPrice = policyPromotionDetailSpecialPrice;
    }

    public String getPolicyPromotionDate() {
        return policyPromotionDate;
    }

    public void setPolicyPromotionDate(String policyPromotionDate) {
        this.policyPromotionDate = policyPromotionDate;
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
}
