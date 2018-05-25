package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StallPromotionCondition implements Serializable {


    private static final long serialVersionUID = -7224031549351402714L;
    private String systemBookCode;
    private Integer branchNum;
    private String dateType;
    private Date dateStart;
    private Date dateEnd;
    private String stallPromotionNo;
    private Integer merchantNum;
    private Integer stallNum;
    private List<Integer> states;
    private boolean page;
    private Integer offset;
    private Integer limit;
    private String sortField;
    private String sortType;
    private boolean queryDetail;

    public boolean isQueryDetail() {
        return queryDetail;
    }

    public void setQueryDetail(boolean queryDetail) {
        this.queryDetail = queryDetail;
    }

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getStallPromotionNo() {
        return stallPromotionNo;
    }

    public void setStallPromotionNo(String stallPromotionNo) {
        this.stallPromotionNo = stallPromotionNo;
    }

    public Integer getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(Integer merchantNum) {
        this.merchantNum = merchantNum;
    }

    public Integer getStallNum() {
        return stallNum;
    }

    public void setStallNum(Integer stallNum) {
        this.stallNum = stallNum;
    }

    public List<Integer> getStates() {
        return states;
    }

    public void setStates(List<Integer> states) {
        this.states = states;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
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
}
