package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TransferProfitByPosItemPageDTO implements Serializable {
    private static final long serialVersionUID = 3364352481172961233L;

    private Integer count;
    private List<TransferProfitByPosItemDTO> data;


    private BigDecimal saleMoneySum;
    private BigDecimal outMoneySum;
    private BigDecimal outCostSum;
    private BigDecimal outProfitSum;
    private BigDecimal saleProfitSum;
    private BigDecimal outAmountSum;
    private BigDecimal outAmountPrSum;
    private BigDecimal basicQtySum;
    private BigDecimal basicQtyPrSum;
    private BigDecimal outAmountPrTranferMoneySum;
    private BigDecimal outAmountPrCostMoneySum;
    private BigDecimal receiveTareSum;
    private BigDecimal totalMoneySum;
    private BigDecimal totalAmountSum;
    private BigDecimal outProfitRateSum;
    private BigDecimal saleProfitRateSum;
    private BigDecimal inAmountSum;
    private BigDecimal inMoneySum;
    private BigDecimal outsAmountSum;
    private BigDecimal outsMoneySum;


    public BigDecimal getSaleMoneySum() {
        return saleMoneySum;
    }

    public void setSaleMoneySum(BigDecimal saleMoneySum) {
        this.saleMoneySum = saleMoneySum;
    }

    public BigDecimal getOutMoneySum() {
        return outMoneySum;
    }

    public void setOutMoneySum(BigDecimal outMoneySum) {
        this.outMoneySum = outMoneySum;
    }

    public BigDecimal getOutCostSum() {
        return outCostSum;
    }

    public void setOutCostSum(BigDecimal outCostSum) {
        this.outCostSum = outCostSum;
    }

    public BigDecimal getOutProfitSum() {
        return outProfitSum;
    }

    public void setOutProfitSum(BigDecimal outProfitSum) {
        this.outProfitSum = outProfitSum;
    }

    public BigDecimal getSaleProfitSum() {
        return saleProfitSum;
    }

    public void setSaleProfitSum(BigDecimal saleProfitSum) {
        this.saleProfitSum = saleProfitSum;
    }

    public BigDecimal getOutAmountSum() {
        return outAmountSum;
    }

    public void setOutAmountSum(BigDecimal outAmountSum) {
        this.outAmountSum = outAmountSum;
    }

    public BigDecimal getOutAmountPrSum() {
        return outAmountPrSum;
    }

    public void setOutAmountPrSum(BigDecimal outAmountPrSum) {
        this.outAmountPrSum = outAmountPrSum;
    }

    public BigDecimal getBasicQtySum() {
        return basicQtySum;
    }

    public void setBasicQtySum(BigDecimal basicQtySum) {
        this.basicQtySum = basicQtySum;
    }

    public BigDecimal getBasicQtyPrSum() {
        return basicQtyPrSum;
    }

    public void setBasicQtyPrSum(BigDecimal basicQtyPrSum) {
        this.basicQtyPrSum = basicQtyPrSum;
    }

    public BigDecimal getOutAmountPrTranferMoneySum() {
        return outAmountPrTranferMoneySum;
    }

    public void setOutAmountPrTranferMoneySum(BigDecimal outAmountPrTranferMoneySum) {
        this.outAmountPrTranferMoneySum = outAmountPrTranferMoneySum;
    }

    public BigDecimal getOutAmountPrCostMoneySum() {
        return outAmountPrCostMoneySum;
    }

    public void setOutAmountPrCostMoneySum(BigDecimal outAmountPrCostMoneySum) {
        this.outAmountPrCostMoneySum = outAmountPrCostMoneySum;
    }

    public BigDecimal getReceiveTareSum() {
        return receiveTareSum;
    }

    public void setReceiveTareSum(BigDecimal receiveTareSum) {
        this.receiveTareSum = receiveTareSum;
    }

    public BigDecimal getTotalMoneySum() {
        return totalMoneySum;
    }

    public void setTotalMoneySum(BigDecimal totalMoneySum) {
        this.totalMoneySum = totalMoneySum;
    }

    public BigDecimal getTotalAmountSum() {
        return totalAmountSum;
    }

    public void setTotalAmountSum(BigDecimal totalAmountSum) {
        this.totalAmountSum = totalAmountSum;
    }

    public BigDecimal getOutProfitRateSum() {
        return outProfitRateSum;
    }

    public void setOutProfitRateSum(BigDecimal outProfitRateSum) {
        this.outProfitRateSum = outProfitRateSum;
    }

    public BigDecimal getSaleProfitRateSum() {
        return saleProfitRateSum;
    }

    public void setSaleProfitRateSum(BigDecimal saleProfitRateSum) {
        this.saleProfitRateSum = saleProfitRateSum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<TransferProfitByPosItemDTO> getData() {
        return data;
    }

    public void setData(List<TransferProfitByPosItemDTO> data) {
        this.data = data;
    }

    public BigDecimal getInAmountSum() {
        return inAmountSum;
    }

    public void setInAmountSum(BigDecimal inAmountSum) {
        this.inAmountSum = inAmountSum;
    }

    public BigDecimal getInMoneySum() {
        return inMoneySum;
    }

    public void setInMoneySum(BigDecimal inMoneySum) {
        this.inMoneySum = inMoneySum;
    }

    public BigDecimal getOutsAmountSum() {
        return outsAmountSum;
    }

    public void setOutsAmountSum(BigDecimal outsAmountSum) {
        this.outsAmountSum = outsAmountSum;
    }

    public BigDecimal getOutsMoneySum() {
        return outsMoneySum;
    }

    public void setOutsMoneySum(BigDecimal outsMoneySum) {
        this.outsMoneySum = outsMoneySum;
    }

    //默认按配送门店升序排序
    //int i = data01.getTranferBranchNum().compareTo(data02.getTranferBranchNum());
}
