package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchProfitDataDTO implements Serializable {
    private static final long serialVersionUID = -5049290037909839775L;

    private int id;
    private String branch;
    private String branchName;
    private String branchCode;

    private Integer branchNum;
    private Integer itemNum;
    private String itemCatagory;

    private String posItemCatagoryCode;
    private String posItemCatagoryName;
    private String posItemCode;
    private String posItemName;

    private BigDecimal startInventoryMoney;
    private BigDecimal endInventoryMoney;
    private BigDecimal startSaleMoney;
    private BigDecimal endSaleMoney;

    private BigDecimal posOrderMoney;
    private BigDecimal discountMoney;
    private BigDecimal costMoney;

    private BigDecimal transferInMoney;
    private BigDecimal transferInSaleMoney;
    private BigDecimal transferOutMoney;
    private BigDecimal transferOutSaleMoney;

    private BigDecimal wholesaleOrderMoney;
    private BigDecimal wholesaleOrderSaleMoney;
    private BigDecimal receiveMoney;
    private BigDecimal returnMoney;

    private BigDecimal posDifference;
    private BigDecimal profitMoney;
    private BigDecimal profitRate;
    private BigDecimal posRate;
    private BigDecimal lossMoney;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

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

    public String getItemCatagory() {
        return itemCatagory;
    }

    public void setItemCatagory(String itemCatagory) {
        this.itemCatagory = itemCatagory;
    }

    public String getPosItemCatagoryCode() {
        return posItemCatagoryCode;
    }

    public void setPosItemCatagoryCode(String posItemCatagoryCode) {
        this.posItemCatagoryCode = posItemCatagoryCode;
    }

    public String getPosItemCatagoryName() {
        return posItemCatagoryName;
    }

    public void setPosItemCatagoryName(String posItemCatagoryName) {
        this.posItemCatagoryName = posItemCatagoryName;
    }

    public String getPosItemCode() {
        return posItemCode;
    }

    public void setPosItemCode(String posItemCode) {
        this.posItemCode = posItemCode;
    }

    public String getPosItemName() {
        return posItemName;
    }

    public void setPosItemName(String posItemName) {
        this.posItemName = posItemName;
    }

    public BigDecimal getStartInventoryMoney() {
        return startInventoryMoney;
    }

    public void setStartInventoryMoney(BigDecimal startInventoryMoney) {
        this.startInventoryMoney = startInventoryMoney;
    }

    public BigDecimal getEndInventoryMoney() {
        return endInventoryMoney;
    }

    public void setEndInventoryMoney(BigDecimal endInventoryMoney) {
        this.endInventoryMoney = endInventoryMoney;
    }

    public BigDecimal getStartSaleMoney() {
        return startSaleMoney;
    }

    public void setStartSaleMoney(BigDecimal startSaleMoney) {
        this.startSaleMoney = startSaleMoney;
    }

    public BigDecimal getEndSaleMoney() {
        return endSaleMoney;
    }

    public void setEndSaleMoney(BigDecimal endSaleMoney) {
        this.endSaleMoney = endSaleMoney;
    }

    public BigDecimal getPosOrderMoney() {
        return posOrderMoney;
    }

    public void setPosOrderMoney(BigDecimal posOrderMoney) {
        this.posOrderMoney = posOrderMoney;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(BigDecimal costMoney) {
        this.costMoney = costMoney;
    }

    public BigDecimal getTransferInMoney() {
        return transferInMoney;
    }

    public void setTransferInMoney(BigDecimal transferInMoney) {
        this.transferInMoney = transferInMoney;
    }

    public BigDecimal getTransferInSaleMoney() {
        return transferInSaleMoney;
    }

    public void setTransferInSaleMoney(BigDecimal transferInSaleMoney) {
        this.transferInSaleMoney = transferInSaleMoney;
    }

    public BigDecimal getTransferOutMoney() {
        return transferOutMoney;
    }

    public void setTransferOutMoney(BigDecimal transferOutMoney) {
        this.transferOutMoney = transferOutMoney;
    }

    public BigDecimal getTransferOutSaleMoney() {
        return transferOutSaleMoney;
    }

    public void setTransferOutSaleMoney(BigDecimal transferOutSaleMoney) {
        this.transferOutSaleMoney = transferOutSaleMoney;
    }

    public BigDecimal getWholesaleOrderMoney() {
        return wholesaleOrderMoney;
    }

    public void setWholesaleOrderMoney(BigDecimal wholesaleOrderMoney) {
        this.wholesaleOrderMoney = wholesaleOrderMoney;
    }

    public BigDecimal getWholesaleOrderSaleMoney() {
        return wholesaleOrderSaleMoney;
    }

    public void setWholesaleOrderSaleMoney(BigDecimal wholesaleOrderSaleMoney) {
        this.wholesaleOrderSaleMoney = wholesaleOrderSaleMoney;
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public BigDecimal getPosDifference() {
        return posDifference;
    }

    public void setPosDifference(BigDecimal posDifference) {
        this.posDifference = posDifference;
    }

    public BigDecimal getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(BigDecimal profitMoney) {
        this.profitMoney = profitMoney;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getPosRate() {
        return posRate;
    }

    public void setPosRate(BigDecimal posRate) {
        this.posRate = posRate;
    }

    public BigDecimal getLossMoney() {
        return lossMoney;
    }

    public void setLossMoney(BigDecimal lossMoney) {
        this.lossMoney = lossMoney;
    }
}
