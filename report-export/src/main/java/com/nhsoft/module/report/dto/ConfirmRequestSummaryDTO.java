package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConfirmRequestSummaryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3641926709357224603L;
	private Integer branchNum;
	private String branchName;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private BigDecimal useQty;

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

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getUseQty() {
		return useQty;
	}

	public void setUseQty(BigDecimal useQty) {
		this.useQty = useQty;
	}

}
