package com.nhsoft.module.report.query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BusinessCollectionQuery implements Serializable {
    private static final long serialVersionUID = 6545890070797288363L;
    private List<Integer> branchNums;
    private Date dateFrom;
    private Date dateTo;
    private boolean queryAccountMove = false;

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

    public boolean isQueryAccountMove() {
        return queryAccountMove;
    }

    public void setQueryAccountMove(boolean queryAccountMove) {
        this.queryAccountMove = queryAccountMove;
    }
}
