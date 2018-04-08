package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SaleAnalysisBranchItemPageSummary implements Serializable {
    private static final long serialVersionUID = 9086397023135026656L;


    private Integer count;
    private List<SaleAnalysisByPosItemDTO> data;
    private BigDecimal itemDiscount;//折扣金额
    private BigDecimal saleNumSum;// 销售数量
    private BigDecimal saleMoneySum;// 销售金额
    private BigDecimal returnNumSum;// 退货数量
    private BigDecimal returnMoneySum;// 退货金额
    private BigDecimal presentNumSum;// 赠送数量
    private BigDecimal presentMoneySum;// 赠送金额
    private BigDecimal totalNumSum;// 数量小计
    private BigDecimal totalMoneySum;// 金额小计
    private BigDecimal countTotalSum;// 次数小计
    private BigDecimal saleAssistSum;// 销售辅量
    private BigDecimal returnAssistSum;// 退货辅量
    private BigDecimal presentAssistSum;// 赠送辅量


    public SaleAnalysisBranchItemPageSummary() {
        this.itemDiscount = BigDecimal.ZERO;
        this.saleNumSum = BigDecimal.ZERO;
        this.saleMoneySum = BigDecimal.ZERO;
        this.returnNumSum = BigDecimal.ZERO;
        this.returnMoneySum = BigDecimal.ZERO;
        this.presentNumSum = BigDecimal.ZERO;
        this.presentMoneySum = BigDecimal.ZERO;
        this.totalNumSum = BigDecimal.ZERO;
        this.totalMoneySum = BigDecimal.ZERO;
        this.countTotalSum = BigDecimal.ZERO;
        this.saleAssistSum = BigDecimal.ZERO;
        this.returnAssistSum = BigDecimal.ZERO;
        this.presentAssistSum = BigDecimal.ZERO;

    }

    public BigDecimal getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(BigDecimal itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SaleAnalysisByPosItemDTO> getData() {
        return data;
    }

    public void setData(List<SaleAnalysisByPosItemDTO> data) {
        this.data = data;
    }

    public BigDecimal getSaleNumSum() {
        return saleNumSum;
    }

    public void setSaleNumSum(BigDecimal saleNumSum) {
        this.saleNumSum = saleNumSum;
    }

    public BigDecimal getSaleMoneySum() {
        return saleMoneySum;
    }

    public void setSaleMoneySum(BigDecimal saleMoneySum) {
        this.saleMoneySum = saleMoneySum;
    }

    public BigDecimal getReturnNumSum() {
        return returnNumSum;
    }

    public void setReturnNumSum(BigDecimal returnNumSum) {
        this.returnNumSum = returnNumSum;
    }

    public BigDecimal getReturnMoneySum() {
        return returnMoneySum;
    }

    public void setReturnMoneySum(BigDecimal returnMoneySum) {
        this.returnMoneySum = returnMoneySum;
    }

    public BigDecimal getPresentNumSum() {
        return presentNumSum;
    }

    public void setPresentNumSum(BigDecimal presentNumSum) {
        this.presentNumSum = presentNumSum;
    }

    public BigDecimal getPresentMoneySum() {
        return presentMoneySum;
    }

    public void setPresentMoneySum(BigDecimal presentMoneySum) {
        this.presentMoneySum = presentMoneySum;
    }

    public BigDecimal getTotalNumSum() {
        return totalNumSum;
    }

    public void setTotalNumSum(BigDecimal totalNumSum) {
        this.totalNumSum = totalNumSum;
    }

    public BigDecimal getTotalMoneySum() {
        return totalMoneySum;
    }

    public void setTotalMoneySum(BigDecimal totalMoneySum) {
        this.totalMoneySum = totalMoneySum;
    }

    public BigDecimal getCountTotalSum() {
        return countTotalSum;
    }

    public void setCountTotalSum(BigDecimal countTotalSum) {
        this.countTotalSum = countTotalSum;
    }

    public BigDecimal getSaleAssistSum() {
        return saleAssistSum;
    }

    public void setSaleAssistSum(BigDecimal saleAssistSum) {
        this.saleAssistSum = saleAssistSum;
    }

    public BigDecimal getReturnAssistSum() {
        return returnAssistSum;
    }

    public void setReturnAssistSum(BigDecimal returnAssistSum) {
        this.returnAssistSum = returnAssistSum;
    }

    public BigDecimal getPresentAssistSum() {
        return presentAssistSum;
    }

    public void setPresentAssistSum(BigDecimal presentAssistSum) {
        this.presentAssistSum = presentAssistSum;
    }



}
