package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class InventoryProfitPageDTO<T> implements Serializable,Comparator<T> {
    private static final long serialVersionUID = 3127619689745595017L;


    private Integer count;
    private List<InventoryProfitDTO> data;



    private BigDecimal profitQtySum;
    private BigDecimal profitAssitQtySum;
    private BigDecimal profitMoneySum;
    private BigDecimal saleQtySum;
    private BigDecimal saleAssitQtySum;
    private BigDecimal saleMoneySum;
    private BigDecimal itemSaleMoneySum;
    private BigDecimal profitRateSum;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<InventoryProfitDTO> getData() {
        return data;
    }

    public void setData(List<InventoryProfitDTO> data) {
        this.data = data;
    }

    public BigDecimal getProfitQtySum() {
        return profitQtySum;
    }

    public void setProfitQtySum(BigDecimal profitQtySum) {
        this.profitQtySum = profitQtySum;
    }

    public BigDecimal getProfitAssitQtySum() {
        return profitAssitQtySum;
    }

    public void setProfitAssitQtySum(BigDecimal profitAssitQtySum) {
        this.profitAssitQtySum = profitAssitQtySum;
    }

    public BigDecimal getProfitMoneySum() {
        return profitMoneySum;
    }

    public void setProfitMoneySum(BigDecimal profitMoneySum) {
        this.profitMoneySum = profitMoneySum;
    }

    public BigDecimal getSaleQtySum() {
        return saleQtySum;
    }

    public void setSaleQtySum(BigDecimal saleQtySum) {
        this.saleQtySum = saleQtySum;
    }

    public BigDecimal getSaleAssitQtySum() {
        return saleAssitQtySum;
    }

    public void setSaleAssitQtySum(BigDecimal saleAssitQtySum) {
        this.saleAssitQtySum = saleAssitQtySum;
    }

    public BigDecimal getSaleMoneySum() {
        return saleMoneySum;
    }

    public void setSaleMoneySum(BigDecimal saleMoneySum) {
        this.saleMoneySum = saleMoneySum;
    }

    public BigDecimal getItemSaleMoneySum() {
        return itemSaleMoneySum;
    }

    public void setItemSaleMoneySum(BigDecimal itemSaleMoneySum) {
        this.itemSaleMoneySum = itemSaleMoneySum;
    }

    public BigDecimal getProfitRateSum() {
        return profitRateSum;
    }

    public void setProfitRateSum(BigDecimal profitRateSum) {
        this.profitRateSum = profitRateSum;
    }




    //排序
    private String sortField;
    private String sortType;

    public InventoryProfitPageDTO() {
    }

    public InventoryProfitPageDTO(String sortField, String sortType) {
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
