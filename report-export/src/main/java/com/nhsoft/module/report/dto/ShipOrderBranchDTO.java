package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShipOrderBranchDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3269220584729726767L;
	private Integer branchNum;
	private String branchName;
	private Integer shipOrderCount; //进货次数
	private BigDecimal shipOrderMoney; //进货金额
	private Integer newItemCount; //门店新品数
	private BigDecimal newItemRate; //门店新品率
	private BigDecimal newRequestItemRate; //新品叫货率
	private List<Integer> newItemNums = new ArrayList<Integer>();
	private String shipOrderFid;
	private List<ShipOrderBranchDTO> shipOrderBranchDatas = new ArrayList<ShipOrderBranchDTO>();
	
	public ShipOrderBranchDTO() {
		setShipOrderCount(0);
		setShipOrderMoney(BigDecimal.ZERO);
		newItemRate = BigDecimal.ZERO;
		newRequestItemRate = BigDecimal.ZERO;
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

	public Integer getShipOrderCount() {
		return shipOrderCount;
	}

	public void setShipOrderCount(Integer shipOrderCount) {
		this.shipOrderCount = shipOrderCount;
	}

	public BigDecimal getShipOrderMoney() {
		return shipOrderMoney;
	}

	public void setShipOrderMoney(BigDecimal shipOrderMoney) {
		this.shipOrderMoney = shipOrderMoney;
	}

	public BigDecimal getNewItemRate() {
		return newItemRate;
	}

	public void setNewItemRate(BigDecimal newItemRate) {
		this.newItemRate = newItemRate;
	}

	public BigDecimal getNewRequestItemRate() {
		return newRequestItemRate;
	}

	public void setNewRequestItemRate(BigDecimal newRequestItemRate) {
		this.newRequestItemRate = newRequestItemRate;
	}

	public String getShipOrderFid() {
		return shipOrderFid;
	}

	public void setShipOrderFid(String shipOrderFid) {
		this.shipOrderFid = shipOrderFid;
	}

	public List<ShipOrderBranchDTO> getShipOrderBranchDatas() {
		return shipOrderBranchDatas;
	}

	public void setShipOrderBranchDatas(
			List<ShipOrderBranchDTO> shipOrderBranchDatas) {
		this.shipOrderBranchDatas = shipOrderBranchDatas;
	}

	public Integer getNewItemCount() {
		return newItemCount;
	}

	public void setNewItemCount(Integer newItemCount) {
		this.newItemCount = newItemCount;
	}

	public List<Integer> getNewItemNums() {
		return newItemNums;
	}

	public void setNewItemNums(List<Integer> newItemNums) {
		this.newItemNums = newItemNums;
	}
	
}