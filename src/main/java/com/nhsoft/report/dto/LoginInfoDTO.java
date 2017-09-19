package com.nhsoft.report.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangqianwen on 2017/6/15.
 */
public class LoginInfoDTO implements Serializable {
    private static final long serialVersionUID = 491428889337631120L;
    private String systemBookCode;
    private Integer branchNum;
    private String branchName;
    private String terminalId;
    private String touchVersion;
    private String dllVersion;
    private String manageVersion;
    private String retailVersion;
    private String synchVersion;
    private String firebirdVersion;
    private Date refreshTime;

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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTouchVersion() {
        return touchVersion;
    }

    public void setTouchVersion(String touchVersion) {
        this.touchVersion = touchVersion;
    }

    public String getDllVersion() {
        return dllVersion;
    }

    public void setDllVersion(String dllVersion) {
        this.dllVersion = dllVersion;
    }

    public String getManageVersion() {
        return manageVersion;
    }

    public void setManageVersion(String manageVersion) {
        this.manageVersion = manageVersion;
    }

    public String getRetailVersion() {
        return retailVersion;
    }

    public void setRetailVersion(String retailVersion) {
        this.retailVersion = retailVersion;
    }

    public String getSynchVersion() {
        return synchVersion;
    }

    public void setSynchVersion(String synchVersion) {
        this.synchVersion = synchVersion;
    }

    public String getFirebirdVersion() {
        return firebirdVersion;
    }

    public void setFirebirdVersion(String firebirdVersion) {
        this.firebirdVersion = firebirdVersion;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }
}
