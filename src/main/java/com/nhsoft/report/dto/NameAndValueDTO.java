package com.nhsoft.report.dto;

import com.nhsoft.report.model.Branch;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NameAndValueDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 853485185863633564L;
	private String name;
	private BigDecimal value;
	private Integer intValue;
	private Integer intValue2;
	private List<NameAndValueDetailDTO> details = new ArrayList<NameAndValueDetailDTO>();

	public static class NameAndValueDetailDTO implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6703427678421820353L;
		private String branchName;
		private Integer branchNum;
		private String detailName;
		private BigDecimal detailValue;

		public String getBranchName() {
			return branchName;
		}

		public void setBranchName(String shopName) {
			this.branchName = shopName;
		}

		public Integer getBranchNum() {
			return branchNum;
		}

		public void setBranchNum(Integer shopNum) {
			this.branchNum = shopNum;
		}

		public String getDetailName() {
			return detailName;
		}

		public void setDetailName(String detailName) {
			this.detailName = detailName;
		}

		public BigDecimal getDetailValue() {
			return detailValue;
		}

		public void setDetailValue(BigDecimal detailValue) {
			this.detailValue = detailValue;
		}

	}
	
	public Integer getIntValue2() {
		return intValue2;
	}

	public void setIntValue2(Integer intValue2) {
		this.intValue2 = intValue2;
	}

	public List<NameAndValueDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<NameAndValueDetailDTO> details) {
		this.details = details;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public static NameAndValueDTO get(List<NameAndValueDTO> nameAndValueDTOs, String name){
		for(int i = 0;i < nameAndValueDTOs.size();i++){
			NameAndValueDTO nameAndValueDTO = nameAndValueDTOs.get(i);
			if(nameAndValueDTO.getName().equals(name)){
				return nameAndValueDTO;
			}
		}
		return null;
	}
	
	public static NameAndValueDTO get(List<NameAndValueDTO> nameAndValueDTOs, int intValue){
		for(int i = 0;i < nameAndValueDTOs.size();i++){
			NameAndValueDTO nameAndValueDTO = nameAndValueDTOs.get(i);
			if(nameAndValueDTO.getIntValue() == intValue){
				return nameAndValueDTO;
			}
		}
		return null;
	}
	
	public  NameAndValueDetailDTO getDetail(Integer branchNum){
		for(int i = 0;i < details.size();i++){
			NameAndValueDetailDTO nameAndValueDetailDTO = details.get(i);
			if(nameAndValueDetailDTO.getBranchNum().equals(branchNum)){
				return nameAndValueDetailDTO;
			}
		}
		return null;
	}
	
	public void createDetails(List<Integer> branchNums, List<Branch> branchs){
		for (int i = 0; i < branchs.size(); i++) {
			Branch branch = branchs.get(i);
			if (!branchNums.contains(branch.getId().getBranchNum())) {
				continue;
			}
			NameAndValueDetailDTO detailDTO = new NameAndValueDetailDTO();
			detailDTO.setBranchName(branch.getBranchName());
			detailDTO.setBranchNum(branch.getId().getBranchNum());
			getDetails().add(detailDTO);
		
		}
	}

}
