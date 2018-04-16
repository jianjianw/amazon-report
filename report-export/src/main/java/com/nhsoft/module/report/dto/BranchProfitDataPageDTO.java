package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class BranchProfitDataPageDTO implements Serializable {

    private static final long serialVersionUID = -2117245383226510737L;


    private Integer count;
    private List<BranchProfitDataDTO> data;


    private BigDecimal posOrderMoneySum;
    private BigDecimal startInventoryMoneySum;
    private BigDecimal endInventoryMoneySum;
    private BigDecimal transferOutMoneySum;
    private BigDecimal wholesaleOrderMoneySum;
    private BigDecimal transferInMoneySum;
    private BigDecimal profitMoneySum;
    private BigDecimal startSaleMoneySum;
    private BigDecimal endSaleMoneySum;
    private BigDecimal posDifferenceSum;
    private BigDecimal lossMoneySum;
    private BigDecimal discountMoneySum;
    private BigDecimal receiveMoneySum;
    private BigDecimal returnMoneySum;
    private BigDecimal costMoneySum;

    private BigDecimal profitRateSum;
    private BigDecimal posRateSum;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<BranchProfitDataDTO> getData() {
        return data;
    }

    public void setData(List<BranchProfitDataDTO> data) {
        this.data = data;
    }

    public BigDecimal getPosOrderMoneySum() {
        return posOrderMoneySum;
    }

    public void setPosOrderMoneySum(BigDecimal posOrderMoneySum) {
        this.posOrderMoneySum = posOrderMoneySum;
    }

    public BigDecimal getStartInventoryMoneySum() {
        return startInventoryMoneySum;
    }

    public void setStartInventoryMoneySum(BigDecimal startInventoryMoneySum) {
        this.startInventoryMoneySum = startInventoryMoneySum;
    }

    public BigDecimal getEndInventoryMoneySum() {
        return endInventoryMoneySum;
    }

    public void setEndInventoryMoneySum(BigDecimal endInventoryMoneySum) {
        this.endInventoryMoneySum = endInventoryMoneySum;
    }

    public BigDecimal getTransferOutMoneySum() {
        return transferOutMoneySum;
    }

    public void setTransferOutMoneySum(BigDecimal transferOutMoneySum) {
        this.transferOutMoneySum = transferOutMoneySum;
    }

    public BigDecimal getWholesaleOrderMoneySum() {
        return wholesaleOrderMoneySum;
    }

    public void setWholesaleOrderMoneySum(BigDecimal wholesaleOrderMoneySum) {
        this.wholesaleOrderMoneySum = wholesaleOrderMoneySum;
    }

    public BigDecimal getTransferInMoneySum() {
        return transferInMoneySum;
    }

    public void setTransferInMoneySum(BigDecimal transferInMoneySum) {
        this.transferInMoneySum = transferInMoneySum;
    }

    public BigDecimal getProfitMoneySum() {
        return profitMoneySum;
    }

    public void setProfitMoneySum(BigDecimal profitMoneySum) {
        this.profitMoneySum = profitMoneySum;
    }

    public BigDecimal getStartSaleMoneySum() {
        return startSaleMoneySum;
    }

    public void setStartSaleMoneySum(BigDecimal startSaleMoneySum) {
        this.startSaleMoneySum = startSaleMoneySum;
    }

    public BigDecimal getEndSaleMoneySum() {
        return endSaleMoneySum;
    }

    public void setEndSaleMoneySum(BigDecimal endSaleMoneySum) {
        this.endSaleMoneySum = endSaleMoneySum;
    }

    public BigDecimal getPosDifferenceSum() {
        return posDifferenceSum;
    }

    public void setPosDifferenceSum(BigDecimal posDifferenceSum) {
        this.posDifferenceSum = posDifferenceSum;
    }

    public BigDecimal getLossMoneySum() {
        return lossMoneySum;
    }

    public void setLossMoneySum(BigDecimal lossMoneySum) {
        this.lossMoneySum = lossMoneySum;
    }

    public BigDecimal getDiscountMoneySum() {
        return discountMoneySum;
    }

    public void setDiscountMoneySum(BigDecimal discountMoneySum) {
        this.discountMoneySum = discountMoneySum;
    }

    public BigDecimal getReceiveMoneySum() {
        return receiveMoneySum;
    }

    public void setReceiveMoneySum(BigDecimal receiveMoneySum) {
        this.receiveMoneySum = receiveMoneySum;
    }

    public BigDecimal getReturnMoneySum() {
        return returnMoneySum;
    }

    public void setReturnMoneySum(BigDecimal returnMoneySum) {
        this.returnMoneySum = returnMoneySum;
    }

    public BigDecimal getCostMoneySum() {
        return costMoneySum;
    }

    public void setCostMoneySum(BigDecimal costMoneySum) {
        this.costMoneySum = costMoneySum;
    }

    public BigDecimal getProfitRateSum() {
        return profitRateSum;
    }

    public void setProfitRateSum(BigDecimal profitRateSum) {
        this.profitRateSum = profitRateSum;
    }

    public BigDecimal getPosRateSum() {
        return posRateSum;
    }

    public void setPosRateSum(BigDecimal posRateSum) {
        this.posRateSum = posRateSum;
    }


    //默认按配送门店升序排序
    //int i = data01.getTranferBranchNum().compareTo(data02.getTranferBranchNum());


}
