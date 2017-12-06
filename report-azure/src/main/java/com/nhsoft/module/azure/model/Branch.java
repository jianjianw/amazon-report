package com.nhsoft.module.azure.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分店表
 * */
public class Branch implements Serializable {

    private String systemBookCode;
    private Integer branchNum;
    private String branchCode;
    private String branchName;
    private boolean actived;        //是否启用
    private boolean rdc;            //是否配送区域
    private String type;            //门店类型
    private BigDecimal area;
    private Integer employeeCount;  //门店人数
    private Date createTime;


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

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public boolean isRdc() {
        return rdc;
    }

    public void setRdc(boolean rdc) {
        this.rdc = rdc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
