package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchConsumeReport implements Serializable {

    private Integer branchNum;
    private BigDecimal consume;

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getConsume() {
        return consume;
    }

    public void setConsume(BigDecimal consume) {
        this.consume = consume;
    }
}
