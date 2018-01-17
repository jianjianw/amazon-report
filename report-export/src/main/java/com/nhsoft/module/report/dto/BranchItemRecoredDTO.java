package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class BranchItemRecoredDTO implements Serializable {

    private Integer itemNum;
    private String auditDate;//最近审核时间


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
}
