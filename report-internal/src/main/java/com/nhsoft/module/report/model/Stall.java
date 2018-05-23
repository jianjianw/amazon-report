package com.nhsoft.module.report.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity
public class Stall implements Serializable {
    private static final long serialVersionUID = 8553163867065163156L;
    @Id
    private Integer stallNum;
    private Integer storehouseNum;
    private String stallCode;
    private String stallName;
    private String stallCategory;
    private String stallMemo;
    private String systemBookCode;
    private Integer branchNum;
    private Boolean stallDelTag;

    public Boolean getStallDelTag() {
        return stallDelTag;
    }

    public void setStallDelTag(Boolean stallDelTag) {
        this.stallDelTag = stallDelTag;
    }

    public Integer getStallNum() {
        return stallNum;
    }

    public void setStallNum(Integer stallNum) {
        this.stallNum = stallNum;
    }

    public Integer getStorehouseNum() {
        return storehouseNum;
    }

    public void setStorehouseNum(Integer storehouseNum) {
        this.storehouseNum = storehouseNum;
    }

    public String getStallCode() {
        return stallCode;
    }

    public void setStallCode(String stallCode) {
        this.stallCode = stallCode;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public String getStallCategory() {
        return stallCategory;
    }

    public void setStallCategory(String stallCategory) {
        this.stallCategory = stallCategory;
    }

    public String getStallMemo() {
        return stallMemo;
    }

    public void setStallMemo(String stallMemo) {
        this.stallMemo = stallMemo;
    }

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

    public static Stall get(List<Stall> stalls, Integer stallNum) {
        for(Stall stall : stalls){

            if(stall.getStallNum().equals(stallNum)){
                return stall;
            }

        }
        return null;
    }
}
