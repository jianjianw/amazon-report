package com.nhsoft.module.report.query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransferQueryDTO extends  QueryBuilder{

    private static final long serialVersionUID = 6995580192206131075L;
    private List<Integer> branchNums;
    private List<Integer> storehouseNums;
    private Date dateFrom;
    private Date dateTo;
    private List<Integer> itemNums;
    private String sortField;
    private Integer centerBranchNum; //中心调出门店

    public List<Integer> getBranchNums() {
        return branchNums;
    }

    public void setBranchNums(List<Integer> branchNums) {
        this.branchNums = branchNums;
    }

    public List<Integer> getStorehouseNums() {
        return storehouseNums;
    }

    public void setStorehouseNums(List<Integer> storehouseNums) {
        this.storehouseNums = storehouseNums;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public List<Integer> getItemNums() {
        return itemNums;
    }

    public void setItemNums(List<Integer> itemNums) {
        this.itemNums = itemNums;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Integer getCenterBranchNum() {
        return centerBranchNum;
    }

    public void setCenterBranchNum(Integer centerBranchNum) {
        this.centerBranchNum = centerBranchNum;
    }
}
