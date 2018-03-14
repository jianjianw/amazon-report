package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.List;

public class ProfitByBranchAndItemSummaryPageDTO implements Serializable {

    private Integer count;
    private List<ProfitByBranchAndItemSummary> data;

    public ProfitByBranchAndItemSummaryPageDTO() {
    }

    public ProfitByBranchAndItemSummaryPageDTO(Integer count, List<ProfitByBranchAndItemSummary> data) {
        this.count = count;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProfitByBranchAndItemSummary> getData() {
        return data;
    }

    public void setData(List<ProfitByBranchAndItemSummary> data) {
        this.data = data;
    }
}
