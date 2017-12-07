package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分店表
 * */
@Entity
public class Branch implements Serializable {

    @Id
    private String systemBookCode;
    @Id
    private Integer branchNum;
    private String branchCode;
    private String branchName;
    private boolean branchActived;        //是否启用
    private boolean ranchRdc;            //是否配送区域
    private String branchType;            //门店类型
    private BigDecimal branchArea;
    private Integer branchEmployeeCount;  //门店人数
    private Date branchCreateTime;


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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public boolean isBranchActived() {
        return branchActived;
    }

    public void setBranchActived(boolean branchActived) {
        this.branchActived = branchActived;
    }

    public boolean isRanchRdc() {
        return ranchRdc;
    }

    public void setRanchRdc(boolean ranchRdc) {
        this.ranchRdc = ranchRdc;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public BigDecimal getBranchArea() {
        return branchArea;
    }

    public void setBranchArea(BigDecimal branchArea) {
        this.branchArea = branchArea;
    }

    public Integer getBranchEmployeeCount() {
        return branchEmployeeCount;
    }

    public void setBranchEmployeeCount(Integer branchEmployeeCount) {
        this.branchEmployeeCount = branchEmployeeCount;
    }

    public Date getBranchCreateTime() {
        return branchCreateTime;
    }

    public void setBranchCreateTime(Date branchCreateTime) {
        this.branchCreateTime = branchCreateTime;
    }
}
