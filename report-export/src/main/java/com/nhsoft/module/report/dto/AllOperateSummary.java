package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.Date;

public class AllOperateSummary implements Serializable {
    private static final long serialVersionUID = 4986311650932667784L;


    private String reportType;
    private Date reportDate;
    private String reportOperator;
    private String reportCustName;
    private String reportPrintedNum;
    private String reportMemo;
    private String cardUserTypeName;
    private String reportOperateBranch;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportOperator() {
        return reportOperator;
    }

    public void setReportOperator(String reportOperator) {
        this.reportOperator = reportOperator;
    }

    public String getReportCustName() {
        return reportCustName;
    }

    public void setReportCustName(String reportCustName) {
        this.reportCustName = reportCustName;
    }

    public String getReportPrintedNum() {
        return reportPrintedNum;
    }

    public void setReportPrintedNum(String reportPrintedNum) {
        this.reportPrintedNum = reportPrintedNum;
    }

    public String getReportMemo() {
        return reportMemo;
    }

    public void setReportMemo(String reportMemo) {
        this.reportMemo = reportMemo;
    }

    public String getCardUserTypeName() {
        return cardUserTypeName;
    }

    public void setCardUserTypeName(String cardUserTypeName) {
        this.cardUserTypeName = cardUserTypeName;
    }

    public String getReportOperateBranch() {
        return reportOperateBranch;
    }

    public void setReportOperateBranch(String reportOperateBranch) {
        this.reportOperateBranch = reportOperateBranch;
    }
}
