package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TransferProfitByPosItemDetailDTO implements Serializable {
    private static final long serialVersionUID = -2517453090380449510L;


    private String id;
    private String posOrderNum;
    private String posOrderType;
    private Date saleTime;
    private String orderSeller;
    private String orderMaker;
    private String orderAuditor;
    private Integer responseBranchNum;
    private String posItemCode;
    private String posItemName;
    private String spec;
    private String outUnit;
    private BigDecimal outAmount;
    private BigDecimal outUnitPrice;
    private BigDecimal outMoney;
    private BigDecimal costUnitPrice;
    private BigDecimal profitMoney;
    private String remark;
    private Integer distributionBranchNum;
    private String baseUnit;
    private BigDecimal baseAmount;
    private BigDecimal basePrice;
    private Integer itemNum;
    private String outUnitPr;
    private BigDecimal baseAmountPr;
    private BigDecimal outAmountPr;
    private BigDecimal outAmountPrTranferMoney;
    private BigDecimal outAmountPrCostMoney;
    private Date productDate;
    private String state;
    private String distributionBranchName;
    private String responseBranchName;
    private String posItemData;//类型待定
    private Integer itemValidPeriod;
    private Date productPassDate;
    private String department;

    public TransferProfitByPosItemDetailDTO() {
        this.outAmount = BigDecimal.ZERO;
        this.outUnitPrice = BigDecimal.ZERO;
        this.outMoney = BigDecimal.ZERO;
        this.costUnitPrice = BigDecimal.ZERO;
        this.profitMoney = BigDecimal.ZERO;
        this.baseAmount = BigDecimal.ZERO;
        this.basePrice = BigDecimal.ZERO;
        this.baseAmountPr = BigDecimal.ZERO;
        this.outAmountPr = BigDecimal.ZERO;
        this.outAmountPrTranferMoney = BigDecimal.ZERO;
        this.outAmountPrCostMoney = BigDecimal.ZERO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosOrderNum() {
        return posOrderNum;
    }

    public void setPosOrderNum(String posOrderNum) {
        this.posOrderNum = posOrderNum;
    }

    public String getPosOrderType() {
        return posOrderType;
    }

    public void setPosOrderType(String posOrderType) {
        this.posOrderType = posOrderType;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public String getOrderSeller() {
        return orderSeller;
    }

    public void setOrderSeller(String orderSeller) {
        this.orderSeller = orderSeller;
    }

    public String getOrderMaker() {
        return orderMaker;
    }

    public void setOrderMaker(String orderMaker) {
        this.orderMaker = orderMaker;
    }

    public String getOrderAuditor() {
        return orderAuditor;
    }

    public void setOrderAuditor(String orderAuditor) {
        this.orderAuditor = orderAuditor;
    }

    public Integer getResponseBranchNum() {
        return responseBranchNum;
    }

    public void setResponseBranchNum(Integer responseBranchNum) {
        this.responseBranchNum = responseBranchNum;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getOutUnit() {
        return outUnit;
    }

    public void setOutUnit(String outUnit) {
        this.outUnit = outUnit;
    }

    public BigDecimal getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(BigDecimal outAmount) {
        this.outAmount = outAmount;
    }

    public BigDecimal getOutUnitPrice() {
        return outUnitPrice;
    }

    public void setOutUnitPrice(BigDecimal outUnitPrice) {
        this.outUnitPrice = outUnitPrice;
    }

    public BigDecimal getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(BigDecimal outMoney) {
        this.outMoney = outMoney;
    }

    public BigDecimal getCostUnitPrice() {
        return costUnitPrice;
    }

    public void setCostUnitPrice(BigDecimal costUnitPrice) {
        this.costUnitPrice = costUnitPrice;
    }

    public BigDecimal getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(BigDecimal profitMoney) {
        this.profitMoney = profitMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDistributionBranchNum() {
        return distributionBranchNum;
    }

    public void setDistributionBranchNum(Integer distributionBranchNum) {
        this.distributionBranchNum = distributionBranchNum;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public String getOutUnitPr() {
        return outUnitPr;
    }

    public void setOutUnitPr(String outUnitPr) {
        this.outUnitPr = outUnitPr;
    }

    public BigDecimal getBaseAmountPr() {
        return baseAmountPr;
    }

    public void setBaseAmountPr(BigDecimal baseAmountPr) {
        this.baseAmountPr = baseAmountPr;
    }

    public BigDecimal getOutAmountPr() {
        return outAmountPr;
    }

    public void setOutAmountPr(BigDecimal outAmountPr) {
        this.outAmountPr = outAmountPr;
    }

    public BigDecimal getOutAmountPrTranferMoney() {
        return outAmountPrTranferMoney;
    }

    public void setOutAmountPrTranferMoney(BigDecimal outAmountPrTranferMoney) {
        this.outAmountPrTranferMoney = outAmountPrTranferMoney;
    }

    public BigDecimal getOutAmountPrCostMoney() {
        return outAmountPrCostMoney;
    }

    public void setOutAmountPrCostMoney(BigDecimal outAmountPrCostMoney) {
        this.outAmountPrCostMoney = outAmountPrCostMoney;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistributionBranchName() {
        return distributionBranchName;
    }

    public void setDistributionBranchName(String distributionBranchName) {
        this.distributionBranchName = distributionBranchName;
    }

    public String getResponseBranchName() {
        return responseBranchName;
    }

    public void setResponseBranchName(String responseBranchName) {
        this.responseBranchName = responseBranchName;
    }

    public String getPosItemData() {
        return posItemData;
    }

    public void setPosItemData(String posItemData) {
        this.posItemData = posItemData;
    }

    public Integer getItemValidPeriod() {
        return itemValidPeriod;
    }

    public void setItemValidPeriod(Integer itemValidPeriod) {
        this.itemValidPeriod = itemValidPeriod;
    }

    public Date getProductPassDate() {
        return productPassDate;
    }

    public void setProductPassDate(Date productPassDate) {
        this.productPassDate = productPassDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
