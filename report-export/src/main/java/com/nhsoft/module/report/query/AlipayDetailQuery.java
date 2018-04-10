package com.nhsoft.module.report.query;


import java.util.Date;
import java.util.List;

public class AlipayDetailQuery extends QueryBuilder {
    private static final long serialVersionUID = 3905949611381966539L;


    private List<Integer> branchNums;
    private Date dateFrom;
    private Date dateTo;
    private String type;             //前台销售 or 卡存款
    private String paymentType;     //线上支付类型
    private Boolean queryAll;       //是否查询所有明细
    private Boolean orderState = true;     //1.成功   2.失败


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getQueryAll() {
        return queryAll;
    }

    public void setQueryAll(Boolean queryAll) {
        this.queryAll = queryAll;
    }

    public Boolean getOrderState() {
        return orderState;
    }

    public void setOrderState(Boolean orderState) {
        this.orderState = orderState;
    }
}
