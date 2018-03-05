package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.Date;

public class BranchItemRecoredDTO implements Serializable {

    private Integer itemNum;
    private String auditDate;//最近审核时间(最近收货日)
    private Date receiveDate;


    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
}
