package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.List;

public class CustomerAnalysisHistoryPageDTO implements Serializable {

    private Integer count;
    private List<CustomerAnalysisHistory> data;

    public CustomerAnalysisHistoryPageDTO() {
    }

    public CustomerAnalysisHistoryPageDTO(Integer count, List<CustomerAnalysisHistory> data) {
        this.count = count;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CustomerAnalysisHistory> getData() {
        return data;
    }

    public void setData(List<CustomerAnalysisHistory> data) {
        this.data = data;
    }
}
