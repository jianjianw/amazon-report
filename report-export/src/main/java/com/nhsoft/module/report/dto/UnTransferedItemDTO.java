package com.nhsoft.module.report.dto;




import com.nhsoft.module.report.query.ReportKey;

import java.io.Serializable;
import java.math.BigDecimal;

public class UnTransferedItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8904792202481648499L;
	@ReportKey
	private Integer branchNum;
	private String branchName;
	private BigDecimal itemAmount;
	
	public UnTransferedItemDTO() {
		setItemAmount(BigDecimal.ZERO);
	}
	
	public Integer getBranchNum() {
		return branchNum;
	}
	public String getBranchName() {
		return branchName;
	}
	public BigDecimal getItemAmount() {
		return itemAmount;
	}
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}
}
