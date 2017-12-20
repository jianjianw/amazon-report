package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 商品日销售汇总
 * */
@Entity
public class ItemSaleDaily implements Serializable {
    @Id
    private String systemBookCode;
    @Id
    private Integer branchNum;
    @Id
    private String shiftTableBizday;
    @Id
    private Integer itemNum;
    private Date shiftTableDate;
    private BigDecimal itemMoney;   //销售金额
    private BigDecimal itemAmount;  //销售数量
    private Integer itemCount;      //销售次数
    @Id
    private String itemSource;      //单据来源
    @Id
    private Integer itemMemberTag;  //会员标记


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

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemSource() {
        return itemSource;
    }

    public void setItemSource(String itemSource) {
        this.itemSource = itemSource;
    }

    public Integer getItemMemberTag() {
        return itemMemberTag;
    }

    public void setItemMemberTag(Integer itemMemberTag) {
        this.itemMemberTag = itemMemberTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSaleDaily that = (ItemSaleDaily) o;

        if (systemBookCode != null ? !systemBookCode.equals(that.systemBookCode) : that.systemBookCode != null)
            return false;
        if (branchNum != null ? !branchNum.equals(that.branchNum) : that.branchNum != null) return false;
        if (shiftTableBizday != null ? !shiftTableBizday.equals(that.shiftTableBizday) : that.shiftTableBizday != null)
            return false;
        if (itemNum != null ? !itemNum.equals(that.itemNum) : that.itemNum != null) return false;
        if (shiftTableDate != null ? !shiftTableDate.equals(that.shiftTableDate) : that.shiftTableDate != null)
            return false;
        if (itemMoney != null ? !itemMoney.equals(that.itemMoney) : that.itemMoney != null) return false;
        if (itemAmount != null ? !itemAmount.equals(that.itemAmount) : that.itemAmount != null) return false;
        if (itemCount != null ? !itemCount.equals(that.itemCount) : that.itemCount != null) return false;
        if (itemSource != null ? !itemSource.equals(that.itemSource) : that.itemSource != null) return false;
        return itemMemberTag != null ? itemMemberTag.equals(that.itemMemberTag) : that.itemMemberTag == null;
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
        result = 31 * result + (itemCount != null ? itemCount.hashCode() : 0);
        result = 31 * result + (itemSource != null ? itemSource.hashCode() : 0);
        result = 31 * result + (itemMemberTag != null ? itemMemberTag.hashCode() : 0);
        return result;
    }
}
