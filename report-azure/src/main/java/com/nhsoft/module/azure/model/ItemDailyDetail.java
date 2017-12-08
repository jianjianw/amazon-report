package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品日时段销售汇总
 * */
@Entity
public class ItemDailyDetail implements Serializable {



    public ItemDailyDetail() {
        this.itemAmout = 0;
        this.itemMoney = BigDecimal.ZERO;
    }

    @Id
    private Integer branchNum;
    @Id
    private String shiftTableBizday;      //营业日期
    @Id
    private Integer itemNum;
    @Id
    private String systemBookCode;
    @Id
    private String itemPeriod;      //时段
    private Date shiftTableDate;          //营业日期
    private Integer itemAmout;   //销售数量
    private BigDecimal itemMoney;   //销售金额
    private String itemSource;      //销售来源

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

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public String getItemPeriod() {
        return itemPeriod;
    }

    public void setItemPeriod(String itemPeriod) {
        this.itemPeriod = itemPeriod;
    }

    public Date getShiftTableDate() {
        return shiftTableDate;
    }

    public void setShiftTableDate(Date shiftTableDate) {
        this.shiftTableDate = shiftTableDate;
    }

    public Integer getItemAmout() {
        return itemAmout;
    }

    public void setItemAmout(Integer itemAmout) {
        this.itemAmout = itemAmout;
    }

    public BigDecimal getItemMoney() {
        return itemMoney;
    }

    public void setItemMoney(BigDecimal itemMoney) {
        this.itemMoney = itemMoney;
    }

    public String getItemSource() {
        return itemSource;
    }

    public void setItemSource(String itemSource) {
        this.itemSource = itemSource;
    }
}
