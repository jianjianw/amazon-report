package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardSummaryPageDTO implements Serializable{

    private Integer count;
    private List<CardSummaryDTO> data;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CardSummaryDTO> getData() {
        return data;
    }

    public void setData(List<CardSummaryDTO> data) {
        this.data = data;
    }
}
