package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class SaleInventoryQuery extends QueryBuilder {


    private static final long serialVersionUID = -6182342045292508302L;
    private Integer branchNum;
    private List<Integer> branchNums;
    private Date dateFrom;
    private Date dateTo;
    private Integer storehouseNum;
    private List<String> categoryCodes;
    private List<Integer> itemNums;
    private boolean queryKit = false;
    private boolean filterEliminativeItems = false;

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

    public Integer getStorehouseNum() {
        return storehouseNum;
    }

    public void setStorehouseNum(Integer storehouseNum) {
        this.storehouseNum = storehouseNum;
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

    public boolean isQueryKit() {
        return queryKit;
    }

    public void setQueryKit(boolean queryKit) {
        this.queryKit = queryKit;
    }

    public boolean isFilterEliminativeItems() {
        return filterEliminativeItems;
    }

    public void setFilterEliminativeItems(boolean filterEliminativeItems) {
        this.filterEliminativeItems = filterEliminativeItems;
    }
}
