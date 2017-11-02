package com.nhsoft.module.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleFinishMoneyTopDTO implements Serializable{

    private Integer num;//分店或区域号
    private String name;//分店或区域名
    private BigDecimal saleMoney;   //营业额
    private BigDecimal goalMoney;   //营业额目标
    private BigDecimal finishMoneyRate;//完成率
    private Integer topNum;//排名

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public BigDecimal getGoalMoney() {
        return goalMoney;
    }

    public void setGoalMoney(BigDecimal goalMoney) {
        this.goalMoney = goalMoney;
    }

    public BigDecimal getFinishMoneyRate() {
        return finishMoneyRate;
    }

    public void setFinishMoneyRate(BigDecimal finishMoneyRate) {
        this.finishMoneyRate = finishMoneyRate;
    }

    public Integer getTopNum() {
        return topNum;
    }

    public void setTopNum(Integer topNum) {
        this.topNum = topNum;
    }

}
