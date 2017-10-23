package com.nhsoft.module.report.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * BranchRegion entity. @author MyEclipse Persistence Tools
 */

@Entity
public class BranchRegion implements java.io.Serializable {

	private static final long serialVersionUID = -3990997637256313674L;
	@Id
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

	public static BranchRegion get(List<BranchRegion> branchRegions, Integer branchRegionNum) {
		for(int i = 0;i < branchRegions.size();i++){
			BranchRegion branchRegion = branchRegions.get(i);
			
			if(branchRegion.getBranchRegionNum().equals(branchRegionNum)){
				return branchRegion;
			}
		}
		return null;
	}

}