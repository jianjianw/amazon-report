package com.nhsoft.module.report.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

public class TransferRuleQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6502344436593242532L;
	private Integer branchNum;
	private Integer type;
	private Integer month;
	private Integer rank;
	private Integer storehouseNum;

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}
	

}
