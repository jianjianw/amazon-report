package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchSaleAnalysisSummary implements Serializable {

    private Integer branchNum;
    private String bizDate;                    // 日 或 月
    private BigDecimal data;                // 根据type返回数据    0营业额  1日均客单量  2客单价  3毛利  4毛利率
    private BigDecimal memberData;         //  会员数据


    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public BigDecimal getData() {
        return data;
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }

    public BigDecimal getMemberData() {
        return memberData;
    }

    public void setMemberData(BigDecimal memberData) {
        this.memberData = memberData;
    }
}
