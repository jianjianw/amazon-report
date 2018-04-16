package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class TransferProfitByPosItemDetailPageDTO<T> implements Serializable,Comparator<T> {
    private static final long serialVersionUID = 2511530237290680328L;


    private Integer count;
    private List<TransferProfitByPosItemDetailDTO> data;

    private BigDecimal outMoneySum;
    private BigDecimal costUnitPriceSum;
    private BigDecimal profitMoneySum;
    private BigDecimal outAmountSum;
    private BigDecimal baseAmountSum;
    private BigDecimal outAmountPrSum;
    private BigDecimal baseAmountPrSum;
    private BigDecimal outAmountPrCostMoneySum;
    private BigDecimal outAmountPrTranferMoneySum;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<TransferProfitByPosItemDetailDTO> getData() {
        return data;
    }

    public void setData(List<TransferProfitByPosItemDetailDTO> data) {
        this.data = data;
    }

    public BigDecimal getOutMoneySum() {
        return outMoneySum;
    }

    public void setOutMoneySum(BigDecimal outMoneySum) {
        this.outMoneySum = outMoneySum;
    }

    public BigDecimal getCostUnitPriceSum() {
        return costUnitPriceSum;
    }

    public void setCostUnitPriceSum(BigDecimal costUnitPriceSum) {
        this.costUnitPriceSum = costUnitPriceSum;
    }

    public BigDecimal getProfitMoneySum() {
        return profitMoneySum;
    }

    public void setProfitMoneySum(BigDecimal profitMoneySum) {
        this.profitMoneySum = profitMoneySum;
    }

    public BigDecimal getOutAmountSum() {
        return outAmountSum;
    }

    public void setOutAmountSum(BigDecimal outAmountSum) {
        this.outAmountSum = outAmountSum;
    }

    public BigDecimal getBaseAmountSum() {
        return baseAmountSum;
    }

    public void setBaseAmountSum(BigDecimal baseAmountSum) {
        this.baseAmountSum = baseAmountSum;
    }

    public BigDecimal getOutAmountPrSum() {
        return outAmountPrSum;
    }

    public void setOutAmountPrSum(BigDecimal outAmountPrSum) {
        this.outAmountPrSum = outAmountPrSum;
    }

    public BigDecimal getBaseAmountPrSum() {
        return baseAmountPrSum;
    }

    public void setBaseAmountPrSum(BigDecimal baseAmountPrSum) {
        this.baseAmountPrSum = baseAmountPrSum;
    }

    public BigDecimal getOutAmountPrCostMoneySum() {
        return outAmountPrCostMoneySum;
    }

    public void setOutAmountPrCostMoneySum(BigDecimal outAmountPrCostMoneySum) {
        this.outAmountPrCostMoneySum = outAmountPrCostMoneySum;
    }

    public BigDecimal getOutAmountPrTranferMoneySum() {
        return outAmountPrTranferMoneySum;
    }

    public void setOutAmountPrTranferMoneySum(BigDecimal outAmountPrTranferMoneySum) {
        this.outAmountPrTranferMoneySum = outAmountPrTranferMoneySum;
    }





    //排序
    private String sortField;
    private String sortType;

    public TransferProfitByPosItemDetailPageDTO() {
    }

    public TransferProfitByPosItemDetailPageDTO(String sortField, String sortType) {
        this.sortField = sortField;
        this.sortType = sortType;
    }

    @Override
    public int compare(T data01, T data02) {
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
        try {
            Class clazz01 = data01.getClass();
            Method method01 = clazz01.getMethod("get" + sortField);

            Class clazz02 = data02.getClass();
            Method method02 = clazz02.getMethod("get" + sortField);


            Type type=method02.getGenericReturnType();
            String end = type.toString();
            String substring = end.substring(type.toString().lastIndexOf(".") + 1, end.length());
            if("Integer".equals(substring)){
                Integer invoke01 = (Integer)method01.invoke(data01);
                Integer invoke02 = (Integer)method02.invoke(data02);

                if (invoke01.compareTo(invoke02) > 0) {
                    return value01;
                } else if (invoke01.compareTo(invoke02) < 0) {
                    return value02;
                } else {
                    return 0;
                }
            }
            if("String".equals(substring)){
                String invoke01 = (String)method01.invoke(data01);
                String invoke02 = (String)method02.invoke(data02);
                if (invoke01.compareTo(invoke02) > 0) {
                    return value01;
                } else if (invoke01.compareTo(invoke02) < 0) {
                    return value02;
                } else {
                    return 0;
                }
            }
            if("BigDecimal".equals(substring)){
                BigDecimal invoke01 = (BigDecimal)method01.invoke(data01);
                BigDecimal invoke02 = (BigDecimal)method02.invoke(data02);
                if (invoke01.compareTo(invoke02) > 0) {
                    return value01;
                } else if (invoke01.compareTo(invoke02) < 0) {
                    return value02;
                } else {
                    return 0;
                }
            }

        } catch (Exception e) {

        }
        return 0;
    }
}
