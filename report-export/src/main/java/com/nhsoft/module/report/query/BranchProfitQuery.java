package com.nhsoft.module.report.query;

import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public class BranchProfitQuery extends QueryBuilder {
    private static final long serialVersionUID = 3824342707140756915L;



    private String exportId;
    private Date dateFrom;
    private Date dateTo;

    private Integer branchNum;
    private List<Integer> branchNums;

    private List<String> categoryCodeList;
    private List<Integer> itemNums;

    private Boolean queryKit;
    private Boolean isFilterDel;



    private int offset;
    private int limit;
    private String sortField;
    private String sortType;
    private boolean page = true;


    public String getExportId() {
        return exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
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

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public List<Integer> getBranchNums() {
        return branchNums;
    }

    public void setBranchNums(List<Integer> branchNums) {
        this.branchNums = branchNums;
    }

    public List<String> getCategoryCodeList() {
        return categoryCodeList;
    }

    public void setCategoryCodeList(List<String> categoryCodeList) {
        this.categoryCodeList = categoryCodeList;
    }

    public List<Integer> getItemNums() {
        return itemNums;
    }

    public void setItemNums(List<Integer> itemNums) {
        this.itemNums = itemNums;
    }

    public Boolean getQueryKit() {
        return queryKit;
    }

    public void setQueryKit(Boolean queryKit) {
        this.queryKit = queryKit;
    }

    public Boolean getFilterDel() {
        return isFilterDel;
    }

    public void setFilterDel(Boolean filterDel) {
        isFilterDel = filterDel;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }
}
