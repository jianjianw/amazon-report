package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 分店纬度
 * */
@Entity
public class BranchLat implements Serializable {

    @Id
    private String systemBookCode;
    @Id
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchLat branchLat = (BranchLat) o;

        if (systemBookCode != null ? !systemBookCode.equals(branchLat.systemBookCode) : branchLat.systemBookCode != null)
            return false;
        if (branchNum != null ? !branchNum.equals(branchLat.branchNum) : branchLat.branchNum != null) return false;
        if (branchName != null ? !branchName.equals(branchLat.branchName) : branchLat.branchName != null) return false;
        if (location != null ? !location.equals(branchLat.location) : branchLat.location != null) return false;
        if (organization != null ? !organization.equals(branchLat.organization) : branchLat.organization != null)
            return false;
        if (macrocell != null ? !macrocell.equals(branchLat.macrocell) : branchLat.macrocell != null) return false;
        return region != null ? region.equals(branchLat.region) : branchLat.region == null;
    }

    @Override
    public int hashCode() {
        int result = systemBookCode != null ? systemBookCode.hashCode() : 0;
        result = 31 * result + (branchNum != null ? branchNum.hashCode() : 0);
        result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (macrocell != null ? macrocell.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        return result;
    }
}
