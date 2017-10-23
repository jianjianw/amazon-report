package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.List;

public class BranchInfoDTO implements Serializable {
    private static final long serialVersionUID = 707794221264837394L;
    private Integer itemNum;
    private List<Integer> branchNums;

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public List<Integer> getBranchNums() {
        return branchNums;
    }

    public void setBranchNums(List<Integer> branchNums) {
        this.branchNums = branchNums;
    }
}
