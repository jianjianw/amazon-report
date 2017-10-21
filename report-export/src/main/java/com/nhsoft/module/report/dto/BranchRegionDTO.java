package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.List;

public class BranchRegionDTO implements Serializable {
    private Integer branchRegionNum;
    private String systemBookCode;
    private String branchRegionCode;
    private String branchRegionName;
    private Integer parentRegionNum;

    public Integer getBranchRegionNum() {
        return branchRegionNum;
    }

    public void setBranchRegionNum(Integer branchRegionNum) {
        this.branchRegionNum = branchRegionNum;
    }

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public String getBranchRegionCode() {
        return branchRegionCode;
    }

    public void setBranchRegionCode(String branchRegionCode) {
        this.branchRegionCode = branchRegionCode;
    }

    public String getBranchRegionName() {
        return branchRegionName;
    }

    public void setBranchRegionName(String branchRegionName) {
        this.branchRegionName = branchRegionName;
    }

    public Integer getParentRegionNum() {
        return parentRegionNum;
    }

    public void setParentRegionNum(Integer parentRegionNum) {
        this.parentRegionNum = parentRegionNum;
    }

    public static BranchRegionDTO get(List<BranchRegionDTO> branchRegions, Integer branchRegionNum) {
        for(int i = 0;i < branchRegions.size();i++){
            BranchRegionDTO branchRegion = branchRegions.get(i);

            if(branchRegion.getBranchRegionNum().equals(branchRegionNum)){
                return branchRegion;
            }
        }
        return null;
    }
}
