package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class InventoryProfitQuery extends QueryBuilder {
    private static final long serialVersionUID = 1495379720119368630L;

    private List<Integer> branchNums;
    private Integer storeNum;
    private Date dateFrom;
    private Date dateTo;
    private List<String> categoryCodes;
    private List<Integer> itemNums;
    private String checkType;
    private Boolean isChechUp;
    private Boolean isChechZero;
    private List<String> reasons;
    private String unit;
    private String exportId;
    private boolean useTopCategory;




    private String sortField;
    private String sortType;
    private boolean page;
    private int offset;
    private int limit;


    public List<Integer> getBranchNums() {
        return branchNums;
    }

    public void setBranchNums(List<Integer> branchNums) {
        this.branchNums = branchNums;
    }

    public Integer getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(Integer storeNum) {
        this.storeNum = storeNum;
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

    public List<String> getCategoryCodes() {
        return categoryCodes;
    }

    public void setCategoryCodes(List<String> categoryCodes) {
        this.categoryCodes = categoryCodes;
    }

    public List<Integer> getItemNums() {
        return itemNums;
    }

    public void setItemNums(List<Integer> itemNums) {
        this.itemNums = itemNums;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public Boolean getIsChechUp() {
        return isChechUp;
    }

    public void setIsChechUp(Boolean chechUp) {
        isChechUp = chechUp;
    }

    public Boolean getIsChechZero() {
        return isChechZero;
    }

    public void setIsChechZero(Boolean chechZero) {
        isChechZero = chechZero;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExportId() {
        return exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
    }

    public boolean isUseTopCategory() {
        return useTopCategory;
    }

    public void setUseTopCategory(boolean useTopCategory) {
        this.useTopCategory = useTopCategory;
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
}
