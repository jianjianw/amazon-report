package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OtherInoutReportDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6490421376966081156L;
	private Integer branchNum;
	private String branchName;
	private List<NameAndValueDTO> inDetails = new ArrayList<NameAndValueDTO>();
	private List<NameAndValueDTO> outDetails = new ArrayList<NameAndValueDTO>();
	
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
	
	public List<NameAndValueDTO> getInDetails() {
		return inDetails;
	}
	
	public void setInDetails(List<NameAndValueDTO> inDetails) {
		this.inDetails = inDetails;
	}
	
	public List<NameAndValueDTO> getOutDetails() {
		return outDetails;
	}
	
	public void setOutDetails(List<NameAndValueDTO> outDetails) {
		this.outDetails = outDetails;
	}
	
	public static OtherInoutReportDTO get(List<OtherInoutReportDTO> list, Integer branchNum){
		for(int i = 0;i < list.size();i++){
			OtherInoutReportDTO dto = list.get(i);
			if(dto.getBranchNum().equals(branchNum)){
				return dto;
			}
		}
		return null;
	}
	
}
