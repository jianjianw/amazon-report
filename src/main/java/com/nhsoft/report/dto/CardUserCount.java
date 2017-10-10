package com.nhsoft.report.dto;

import java.io.Serializable;

public class CardUserCount implements Serializable {
    private Integer branchNum;
    private Integer count;//新增会员数

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
