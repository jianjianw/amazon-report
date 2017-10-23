package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.Date;

public class WholesaleOrderLostSummary implements Serializable {


    private String clientFid;
    private Integer itemNum;
    private Integer fidCount;
    private Date maxAuditTime;

    public String getClientFid() {
        return clientFid;
    }

    public void setClientFid(String clientFid) {
        this.clientFid = clientFid;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Integer getFidCount() {
        return fidCount;
    }

    public void setFidCount(Integer fidCount) {
        this.fidCount = fidCount;
    }

    public Date getMaxAuditTime() {
        return maxAuditTime;
    }

    public void setMaxAuditTime(Date maxAuditTime) {
        this.maxAuditTime = maxAuditTime;
    }
}
