package com.nhsoft.report.shared.queryBuilder;

import java.util.Date;

public class MarketPaymentQuery extends QueryBuilder {

    private static final long serialVersionUID = -1490590318253607897L;

    private String systemBookCode;
    private Integer branchNum;
    private Date dateFrom;
    private Date dateTo;
    private String marketPaymentToCustomerNo;
    private String sortField;
    private String sortName;

    public MarketPaymentQuery() {

    }

    public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
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

    public String getMarketPaymentToCustomerNo() {
        return marketPaymentToCustomerNo;
    }

    public void setMarketPaymentToCustomerNo(String marketPaymentToCustomerNo) {
        this.marketPaymentToCustomerNo = marketPaymentToCustomerNo;
    }

    @Override
    public boolean checkQueryBuild() {
        if (dateFrom != null && dateTo != null) {
            if (dateFrom.after(dateTo)) {
                return false;
            }
        }
        if (systemBookCode == null) {
            return false;
        }
        return true;
    }

}
