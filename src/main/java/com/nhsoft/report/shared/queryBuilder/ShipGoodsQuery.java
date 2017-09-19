package com.nhsoft.report.shared.queryBuilder;

import java.util.List;

public class ShipGoodsQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 864637283183283330L;

	private String systemBookCode;
	private Integer centerBranchNum;
	private Integer branchNum;
	private List<Integer> branchNums; // 收货分店
	private List<String> clientFids; // 收货客户
	private Integer storehouseNum; // 出货仓库
	private Boolean isClientFid;
	private String settlementState;			//结算状态
		
	@Override
	public boolean checkQueryBuild() {
		if (systemBookCode == null) {
			return false;
		}
		return true;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public List<String> getClientFids() {
		return clientFids;
	}

	public void setClientFids(List<String> clientFids) {
		this.clientFids = clientFids;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}


	public Integer getCenterBranchNum() {
		return centerBranchNum;
	}

	public void setCenterBranchNum(Integer centerBranchNum) {
		this.centerBranchNum = centerBranchNum;
	}

	public String getSettlementState() {
		return settlementState;
	}
	public void setSettlementState(String settlementState) {
		this.settlementState = settlementState;
	}

	public Boolean getIsClientFid() {
		return isClientFid;
	}

	public void setIsClientFid(Boolean isClientFid) {
		this.isClientFid = isClientFid;
	}

}
