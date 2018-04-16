package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class TransferProfitByPosItemPageDTO implements Serializable,Comparator<TransferProfitByPosItemDTO> {
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



    //排序
    private String sortField;
    private String sortType;

    public TransferProfitByPosItemPageDTO() {
    }

    public TransferProfitByPosItemPageDTO(String sortField, String sortType) {
        this.sortField = sortField;
        this.sortType = sortType;
    }

    @Override
    public int compare(TransferProfitByPosItemDTO data01, TransferProfitByPosItemDTO data02) {
        int value01 = 0;
        int value02 = 0;
        if("asc".equals(sortType) || "ASC".equals(sortType)){
            value01 = 1;
            value02 = -1;
        }
        if("desc".equals(sortType) || "DESC".equals(sortType)){
            value01 = -1;
            value02 = 1;
        }

        //默认按配送门店升序排序
        int i = data01.getTranferBranchNum().compareTo(data02.getTranferBranchNum());

        switch (sortField){
            case "branchNum" :
                if(i == 0){
                    if(data01.getBranchNum().compareTo(data02.getBranchNum()) > 0){
                        return value01;
                    }else if(data01.getBranchNum().compareTo(data02.getBranchNum()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "posItemCode" :
                if(i == 0){
                    if(data01.getPosItemCode().compareTo(data02.getPosItemCode()) > 0){
                        return value01;
                    }else if(data01.getPosItemCode().compareTo(data02.getPosItemCode()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "outAmount" :
                if(i == 0){
                    if(data01.getOutAmount().compareTo(data02.getOutAmount()) > 0){
                        return value01;
                    }else if(data01.getOutAmount().compareTo(data02.getOutAmount()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "outMoney" :
                if(i == 0){
                    if(data01.getOutMoney().compareTo(data02.getOutMoney()) > 0){
                        return value01;
                    }else if(data01.getOutMoney().compareTo(data02.getOutMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "saleMoney" :
                if(i == 0){
                    if(data01.getSaleMoney().compareTo(data02.getSaleMoney()) > 0){
                        return value01;
                    }else if(data01.getSaleMoney().compareTo(data02.getSaleMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "outAmountPr":
                if(i == 0){
                    if(data01.getOutAmountPr().compareTo(data02.getOutAmountPr()) > 0){
                        return value01;
                    }else if(data01.getOutAmountPr().compareTo(data02.getOutAmountPr()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "outAmountPrTranferMoney" :
                if(i == 0){
                    if(data01.getOutAmountPrTranferMoney().compareTo(data02.getOutAmountPrTranferMoney()) > 0){
                        return value01;
                    }else if(data01.getOutAmountPrTranferMoney().compareTo(data02.getOutAmountPrTranferMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "basicQty" :
                if(i == 0){
                    if(data01.getBasicQty().compareTo(data02.getBasicQty()) > 0){
                        return value01;
                    }else if(data01.getBasicQty().compareTo(data02.getBasicQty()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "outAmountPrCostMoney" :
                if(i == 0){
                    if(data01.getOutAmountPrCostMoney().compareTo(data02.getOutAmountPrCostMoney()) > 0){
                        return value01;
                    }else if(data01.getOutAmountPrCostMoney().compareTo(data02.getOutAmountPrCostMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
            case "basicQtyPr" :
                if(i == 0){
                    if(data01.getBasicQtyPr().compareTo(data02.getBasicQtyPr()) > 0){
                        return value01;
                    }else if(data01.getBasicQtyPr().compareTo(data02.getBasicQtyPr()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;

            case "totalAmount" :
                if(i == 0){
                    if(data01.getTotalAmount().compareTo(data02.getTotalAmount()) > 0){
                        return value01;
                    }else if(data01.getTotalAmount().compareTo(data02.getTotalAmount()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
            case "totalMoney" :
                if(i == 0){
                    if(data01.getTotalMoney().compareTo(data02.getTotalMoney()) > 0){
                        return value01;
                    }else if(data01.getTotalMoney().compareTo(data02.getTotalMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;


        }

        return i;
    }
}
