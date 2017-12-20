package com.nhsoft.module.report.dto;

import java.io.Serializable;
/**
 * 按分店和营业日汇总退卡数
 * */
public class BranchBizdayCardReturnSummary implements Serializable {

    private Integer branchNum;
    private String  bizday;
    private Long returnCount;  //发卡数

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getBizday() {
        return bizday;
    }

    public void setBizday(String bizday) {
        this.bizday = bizday;
    }

    public Long getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Long returnCount) {
        this.returnCount = returnCount;
    }
}
