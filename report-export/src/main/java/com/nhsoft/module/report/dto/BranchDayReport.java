package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchDayReport implements Serializable{
    private static final long serialVersionUID = 5978813697825433532L;
    private int branchNum;
    private String day;
    private BigDecimal value;   // 根据type   返回    0营业额 1客单量2客单价3会员客单量4会员客单价5毛利6平均毛利率

    public int getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(int branchNum) {
        this.branchNum = branchNum;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
