package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryLostDTO implements Serializable {


    private static final long serialVersionUID = 5468197238510011561L;

    public static class InventoryLostDetailDTO implements Serializable{

        private static final long serialVersionUID = 800581540744534911L;
        private String bizday;
        private BigDecimal inventoryAmount;

        public String getBizday() {
            return bizday;
        }

        public void setBizday(String bizday) {
            this.bizday = bizday;
        }

        public BigDecimal getInventoryAmount() {
            return inventoryAmount;
        }

        public void setInventoryAmount(BigDecimal inventoryAmount) {
            this.inventoryAmount = inventoryAmount;
        }
    }


    public InventoryLostDTO() {

        this.inventoryAmount = BigDecimal.ZERO;
        this.receiveAmount = BigDecimal.ZERO;
        this.requestAmount = BigDecimal.ZERO;
        this.requestOutAmount = BigDecimal.ZERO;

    }

    private Integer itemNum;
    private String itemName;
    private String itemSpec;//商品规格
    private String itemUnit;//单位
    private BigDecimal inventoryAmount; //配送仓库库存
    private Integer lostCount;//断货次数
    private Integer lostDay;//断货天数
    private BigDecimal receiveAmount;//收货数量
    private BigDecimal requestAmount;//要货数量
    private BigDecimal requestOutAmount;//要货调出数量
    private String firstReceiveDay;//首次收货日期
    private String maxReceiveDay;//最近收货日期
    private List<InventoryLostDetailDTO> inventoryDetails = new ArrayList<>();


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

    public BigDecimal getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(BigDecimal inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public Integer getLostCount() {
        return lostCount;
    }

    public void setLostCount(Integer lostCount) {
        this.lostCount = lostCount;
    }

    public Integer getLostDay() {
        return lostDay;
    }

    public void setLostDay(Integer lostDay) {
        this.lostDay = lostDay;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public BigDecimal getRequestOutAmount() {
        return requestOutAmount;
    }

    public void setRequestOutAmount(BigDecimal requestOutAmount) {
        this.requestOutAmount = requestOutAmount;
    }

    public String getFirstReceiveDay() {
        return firstReceiveDay;
    }

    public void setFirstReceiveDay(String firstReceiveDay) {
        this.firstReceiveDay = firstReceiveDay;
    }

    public String getMaxReceiveDay() {
        return maxReceiveDay;
    }

    public void setMaxReceiveDay(String maxReceiveDay) {
        this.maxReceiveDay = maxReceiveDay;
    }

    public List<InventoryLostDetailDTO> getInventoryDetails() {
        return inventoryDetails;
    }

    public void setInventoryDetails(List<InventoryLostDetailDTO> inventoryDetails) {
        this.inventoryDetails = inventoryDetails;
    }
}
