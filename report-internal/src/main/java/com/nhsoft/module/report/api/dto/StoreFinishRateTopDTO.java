package com.nhsoft.module.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 分店完成率排名
 */
public class StoreFinishRateTopDTO implements Serializable {
    private String name;                               //分店名
    private BigDecimal saleMoneyGoal;                //当天营业额
    private BigDecimal saleMoneyFinishRate;         //营业额完成率
    private BigDecimal monthSaleMoneyGoal;          //月营业额目标
    private BigDecimal avgSaleMoneyGoal;            //日均营业额目标
    private BigDecimal FriSaleMoneyGoal;            //星期五营业额目标
    private BigDecimal SatSaleMoneyGoal;            //星期六营业额目标
    private BigDecimal SunSaleMoneyGoal;            //星期天营业额目标
    private BigDecimal MonSaleMoneyGoal;            //星期一到星期四营业额目标

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSaleMoneyGoal() {
        return saleMoneyGoal;
    }

    public void setSaleMoneyGoal(BigDecimal saleMoneyGoal) {
        this.saleMoneyGoal = saleMoneyGoal;
    }

    public BigDecimal getSaleMoneyFinishRate() {
        return saleMoneyFinishRate;
    }

    public void setSaleMoneyFinishRate(BigDecimal saleMoneyFinishRate) {
        this.saleMoneyFinishRate = saleMoneyFinishRate;
    }

    public BigDecimal getMonthSaleMoneyGoal() {
        return monthSaleMoneyGoal;
    }

    public void setMonthSaleMoneyGoal(BigDecimal monthSaleMoneyGoal) {
        this.monthSaleMoneyGoal = monthSaleMoneyGoal;
    }

    public BigDecimal getAvgSaleMoneyGoal() {
        return avgSaleMoneyGoal;
    }

    public void setAvgSaleMoneyGoal(BigDecimal avgSaleMoneyGoal) {
        this.avgSaleMoneyGoal = avgSaleMoneyGoal;
    }

    public BigDecimal getFriSaleMoneyGoal() {
        return FriSaleMoneyGoal;
    }

    public void setFriSaleMoneyGoal(BigDecimal friSaleMoneyGoal) {
        FriSaleMoneyGoal = friSaleMoneyGoal;
    }

    public BigDecimal getSatSaleMoneyGoal() {
        return SatSaleMoneyGoal;
    }

    public void setSatSaleMoneyGoal(BigDecimal satSaleMoneyGoal) {
        SatSaleMoneyGoal = satSaleMoneyGoal;
    }

    public BigDecimal getSunSaleMoneyGoal() {
        return SunSaleMoneyGoal;
    }

    public void setSunSaleMoneyGoal(BigDecimal sunSaleMoneyGoal) {
        SunSaleMoneyGoal = sunSaleMoneyGoal;
    }

    public BigDecimal getMonSaleMoneyGoal() {
        return MonSaleMoneyGoal;
    }

    public void setMonSaleMoneyGoal(BigDecimal monSaleMoneyGoal) {
        MonSaleMoneyGoal = monSaleMoneyGoal;
    }
}
