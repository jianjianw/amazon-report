package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemSaleQtySummary implements Serializable {


    private Integer itemNum;
    private Integer matrixNum;
    private BigDecimal amount;

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Integer getMatrixNum() {
        return matrixNum;
    }

    public void setMatrixNum(Integer matrixNum) {
        this.matrixNum = matrixNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
