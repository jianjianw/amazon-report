package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferProfitByPosItemDTO implements Serializable {
    private static final long serialVersionUID = -8390283439053553931L;


    private String id;
    private String branchName;
    private String tranferBranchName;
    private String posItemData;
    private String posItemTypeCode;
    private String posItemTypeName;
    private String spec;
    private String unit;
    private String basicUnit;
    private String posItemCode;
    private String posItemName;
    private BigDecimal outProfit;
    private BigDecimal saleProfit;
    private BigDecimal outProfitRate;
    private BigDecimal basicPrice;
    private BigDecimal saleProfitRate;
    private Integer tranferBranchNum;
    private Integer branchNum;
    private Integer itemNum;
    private Integer itemMatrixNum;
    private BigDecimal basicQty;
    private BigDecimal outAmount;
    private BigDecimal basicQtyPr;
    private BigDecimal outAmountPr;
    private BigDecimal outAmountPrTranferMoney;
    private BigDecimal outAmountPrCostMoney;
    private BigDecimal outCost;
    private BigDecimal outMoney;
    private BigDecimal receiveTare;
    private BigDecimal totalAmount;
    private BigDecimal totalMoney;
    private BigDecimal inAmount;
    private BigDecimal inMoney;
    private BigDecimal saleMoney;
    private BigDecimal outsAmount;
	private BigDecimal outsMoney;
	private BigDecimal inUseAmount;
	private BigDecimal outUseAmount;



    public TransferProfitByPosItemDTO( ) {
        this.outProfit = BigDecimal.ZERO;
        this.saleProfit = BigDecimal.ZERO;
        this.outProfitRate = BigDecimal.ZERO;
        this.basicPrice = BigDecimal.ZERO;
        this.saleProfitRate = BigDecimal.ZERO;
        this.basicQty = BigDecimal.ZERO;
        this.outAmount = BigDecimal.ZERO;
        this.basicQtyPr = BigDecimal.ZERO;
        this.outAmountPr = BigDecimal.ZERO;
        this.outAmountPrTranferMoney = BigDecimal.ZERO;
        this.outAmountPrCostMoney = BigDecimal.ZERO;
        this.outCost = BigDecimal.ZERO;
        this.outMoney = BigDecimal.ZERO;
        this.receiveTare = BigDecimal.ZERO;
        this.totalAmount = BigDecimal.ZERO;
        this.totalMoney = BigDecimal.ZERO;
        this.inAmount = BigDecimal.ZERO;
        this.inMoney = BigDecimal.ZERO;
        this.saleMoney = BigDecimal.ZERO;
        this.outsAmount = BigDecimal.ZERO;
        this.outsMoney = BigDecimal.ZERO;
    }

    public String getTranferBranchName() {
        return tranferBranchName;
    }

    public void setTranferBranchName(String tranferBranchName) {
        this.tranferBranchName = tranferBranchName;
    }

    public BigDecimal getInMoney() {
        return inMoney;
    }

    public void setInMoney(BigDecimal inMoney) {
        this.inMoney = inMoney;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public BigDecimal getSaleProfitRate() {
        return saleProfitRate;
    }

    public void setSaleProfitRate(BigDecimal saleProfitRate) {
        this.saleProfitRate = saleProfitRate;
    }

    public BigDecimal getOutProfitRate() {
        return outProfitRate;
    }

    public void setOutProfitRate(BigDecimal outProfitRate) {
        this.outProfitRate = outProfitRate;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public BigDecimal getOutProfit() {
        return outProfit;
    }

    public void setOutProfit(BigDecimal outProfit) {
        this.outProfit = outProfit;
    }

    public BigDecimal getSaleProfit() {
        return saleProfit;
    }

    public void setSaleProfit(BigDecimal saleProfit) {
        this.saleProfit = saleProfit;
    }

    public String getBasicUnit() {
        return basicUnit;
    }

    public void setBasicUnit(String basicUnit) {
        this.basicUnit = basicUnit;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPosItemData() {
        return posItemData;
    }

    public void setPosItemData(String posItemData) {
        this.posItemData = posItemData;
    }

    public String getPosItemTypeCode() {
        return posItemTypeCode;
    }

    public void setPosItemTypeCode(String posItemTypeCode) {
        this.posItemTypeCode = posItemTypeCode;
    }

    public String getPosItemTypeName() {
        return posItemTypeName;
    }

    public void setPosItemTypeName(String posItemTypeName) {
        this.posItemTypeName = posItemTypeName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getTranferBranchNum() {
        return tranferBranchNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getInAmount() {
        return inAmount;
    }

    public void setInAmount(BigDecimal inAmount) {
        this.inAmount = inAmount;
    }

    public void setTranferBranchNum(Integer tranferBranchNum) {
        this.tranferBranchNum = tranferBranchNum;
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

    public Integer getItemMatrixNum() {
        return itemMatrixNum;
    }

    public void setItemMatrixNum(Integer itemMatrixNum) {
        this.itemMatrixNum = itemMatrixNum;
    }

    public BigDecimal getBasicQty() {
        return basicQty;
    }

    public void setBasicQty(BigDecimal basicQty) {
        this.basicQty = basicQty;
    }

    public BigDecimal getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(BigDecimal outAmount) {
        this.outAmount = outAmount;
    }

    public BigDecimal getBasicQtyPr() {
        return basicQtyPr;
    }

    public void setBasicQtyPr(BigDecimal basicQtyPr) {
        this.basicQtyPr = basicQtyPr;
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

    public BigDecimal getOutCost() {
        return outCost;
    }

    public void setOutCost(BigDecimal outCost) {
        this.outCost = outCost;
    }

    public BigDecimal getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(BigDecimal outMoney) {
        this.outMoney = outMoney;
    }

    public BigDecimal getReceiveTare() {
        return receiveTare;
    }

    public void setReceiveTare(BigDecimal receiveTare) {
        this.receiveTare = receiveTare;
    }

    public BigDecimal getOutsAmount() {
        return outsAmount;
    }

    public void setOutsAmount(BigDecimal outsAmount) {
        this.outsAmount = outsAmount;
    }

    public BigDecimal getOutsMoney() {
        return outsMoney;
    }

    public void setOutsMoney(BigDecimal outsMoney) {
        this.outsMoney = outsMoney;
    }

    public BigDecimal getInUseAmount() {
        return inUseAmount;
    }

    public void setInUseAmount(BigDecimal inUseAmount) {
        this.inUseAmount = inUseAmount;
    }

    public BigDecimal getOutUseAmount() {
        return outUseAmount;
    }

    public void setOutUseAmount(BigDecimal outUseAmount) {
        this.outUseAmount = outUseAmount;
    }
}
