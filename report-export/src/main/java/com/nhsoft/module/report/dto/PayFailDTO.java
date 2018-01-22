package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayFailDTO implements Serializable {

    private Integer branchNum;
    private BigDecimal logMoney;
    private Long logId;


    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getLogMoney() {
        return logMoney;
    }

    public void setLogMoney(BigDecimal logMoney) {
        this.logMoney = logMoney;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }
}
