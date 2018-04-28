package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.List;

public class AllOperatePageDTO implements Serializable {
    private static final long serialVersionUID = 2703752628735587980L;

    private Integer count;
    private List<AllOperateSummary> data;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<AllOperateSummary> getData() {
        return data;
    }

    public void setData(List<AllOperateSummary> data) {
        this.data = data;
    }
}
