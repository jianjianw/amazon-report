package com.nhsoft.module.report.dto.azure;

import java.io.Serializable;

/**
 * 分店纬度
 * */
public class BranchLat implements Serializable {


    private String systemBookCode;
    private Integer branchNum;
    private String branchName;
    private String location;            //商圈分类
    private String organization;        //组织分类
    private String macrocell;           //大区
    private String region;              //区域


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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMacrocell() {
        return macrocell;
    }

    public void setMacrocell(String macrocell) {
        this.macrocell = macrocell;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
