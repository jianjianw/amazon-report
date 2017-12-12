package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 商品日销售汇总
 * */
@Entity
public class ItemDaily implements Serializable {


    @Id
    private String systemBookCode;
    @Id
    private Integer branchNum;
    @Id
    private String shiftTableBizday;
    @Id
    private Integer itemNum;
    private Date shiftTableDate;
    private BigDecimal itemMoney;
    private BigDecimal itemAmount;   //销售数量

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

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Date getShiftTableDate() {
        return shiftTableDate;
    }

    public void setShiftTableDate(Date shiftTableDate) {
        this.shiftTableDate = shiftTableDate;
    }

    public BigDecimal getItemMoney() {
        return itemMoney;
    }

    public void setItemMoney(BigDecimal itemMoney) {
        this.itemMoney = itemMoney;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemDaily itemDaily = (ItemDaily) o;

        if (systemBookCode != null ? !systemBookCode.equals(itemDaily.systemBookCode) : itemDaily.systemBookCode != null)
            return false;
        if (branchNum != null ? !branchNum.equals(itemDaily.branchNum) : itemDaily.branchNum != null) return false;
        if (shiftTableBizday != null ? !shiftTableBizday.equals(itemDaily.shiftTableBizday) : itemDaily.shiftTableBizday != null)
            return false;
        if (itemNum != null ? !itemNum.equals(itemDaily.itemNum) : itemDaily.itemNum != null) return false;
        if (shiftTableDate != null ? !shiftTableDate.equals(itemDaily.shiftTableDate) : itemDaily.shiftTableDate != null)
            return false;
        if (itemMoney != null ? !itemMoney.equals(itemDaily.itemMoney) : itemDaily.itemMoney != null) return false;
        return itemAmount != null ? itemAmount.equals(itemDaily.itemAmount) : itemDaily.itemAmount == null;
    }

    @Override
    public int hashCode() {
        int result = systemBookCode != null ? systemBookCode.hashCode() : 0;
        result = 31 * result + (branchNum != null ? branchNum.hashCode() : 0);
        result = 31 * result + (shiftTableBizday != null ? shiftTableBizday.hashCode() : 0);
        result = 31 * result + (itemNum != null ? itemNum.hashCode() : 0);
        result = 31 * result + (shiftTableDate != null ? shiftTableDate.hashCode() : 0);
        result = 31 * result + (itemMoney != null ? itemMoney.hashCode() : 0);
        result = 31 * result + (itemAmount != null ? itemAmount.hashCode() : 0);
        return result;
    }
}
