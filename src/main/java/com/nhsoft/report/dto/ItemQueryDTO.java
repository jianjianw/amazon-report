package com.nhsoft.report.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ItemQueryDTO implements Serializable {
    private static final long serialVersionUID = -8001865847195125918L;
    private String systemBookCode;
    private List<Integer> branchNums;
    private Date dateFrom;
    private Date dateTo;
    private List<Integer> itemNums;
    private boolean queryKit;
    private String itemMethod;

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
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

    public List<Integer> getItemNums() {
        return itemNums;
    }

    public void setItemNums(List<Integer> itemNums) {
        this.itemNums = itemNums;
    }

    public boolean getQueryKit() {
        return queryKit;
    }

    public void setQueryKit(boolean queryKit) {
        this.queryKit = queryKit;
    }

    public String getItemMethod() {
        return itemMethod;
    }

    public void setItemMethod(String itemMethod) {
        this.itemMethod = itemMethod;
    }
}
