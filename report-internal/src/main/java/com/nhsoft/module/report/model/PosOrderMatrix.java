package com.nhsoft.module.report.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class PosOrderMatrix implements Serializable {


    @Id
    private String orderNo;
    private String systemBookCode;
    private Integer branchNum;
    private String shiftTableBizday;
    private Integer orderCancelItemCount;
    private BigDecimal orderCancelItemMoney;

    public PosOrderMatrix(){

    }
    public PosOrderMatrix(String orderNo){
        this.orderNo = orderNo;
    }

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getShiftTableBizday() {
        return shiftTableBizday;
    }

    public void setShiftTableBizday(String shiftTableBizday) {
        this.shiftTableBizday = shiftTableBizday;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderCancelItemCount() {
        return orderCancelItemCount;
    }

    public void setOrderCancelItemCount(Integer orderCancelItemCount) {
        this.orderCancelItemCount = orderCancelItemCount;
    }

    public BigDecimal getOrderCancelItemMoney() {
        return orderCancelItemMoney;
    }

    public void setOrderCancelItemMoney(BigDecimal orderCancelItemMoney) {
        this.orderCancelItemMoney = orderCancelItemMoney;
    }
}
