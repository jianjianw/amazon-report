package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CenterItemStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 677992132240462838L;
	private String systemBookCode;
	private Integer branchNum;
	private Integer itemNum;
	private BigDecimal itemQty;
	private BigDecimal itemMoney;
	private BigDecimal itemAssistQty = BigDecimal.ZERO;

	
	//只更新不保存
	private boolean onlyUpdate = false;
	
	public CenterItemStore(){
		
	}
	
	public BigDecimal getItemAssistQty() {
		if(itemAssistQty == null){
			itemAssistQty = BigDecimal.ZERO;
		}
		return itemAssistQty;
	}

	public void setItemAssistQty(BigDecimal itemAssistQty) {
		if(itemAssistQty == null){
			itemAssistQty = BigDecimal.ZERO;
		}
		this.itemAssistQty = itemAssistQty;
	}

	public boolean isOnlyUpdate() {
		return onlyUpdate;
	}

	public void setOnlyUpdate(boolean onlyUpdate) {
		this.onlyUpdate = onlyUpdate;
	}

	public CenterItemStore(Integer itemNum, BigDecimal itemQty){
		this.itemNum = itemNum;
		this.itemQty = itemQty;
	}
	
	public CenterItemStore(Integer itemNum, BigDecimal itemQty, BigDecimal itemAssistQty){
		this.itemNum = itemNum;
		this.itemQty = itemQty;
		if(itemAssistQty == null){
			itemAssistQty = BigDecimal.ZERO;
		}
		this.itemAssistQty = itemAssistQty;
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

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getItemQty() {
		if(itemQty == null){
			itemQty = BigDecimal.ZERO;
		}
		return itemQty;
	}

	public void setItemQty(BigDecimal itemQty) {
		this.itemQty = itemQty;
	}

	public BigDecimal getItemMoney() {
		return itemMoney;
	}

	public void setItemMoney(BigDecimal itemMoney) {
		this.itemMoney = itemMoney;
	}
	
	public static CenterItemStore findItem(List<CenterItemStore> centerItemStores, String systemBookCode, Integer branchNum,
			Integer itemNum) {
		for(int i = 0;i < centerItemStores.size();i++){
			CenterItemStore centerItemStore = centerItemStores.get(i);
			if(centerItemStore.getSystemBookCode().equals(systemBookCode) 
					&& centerItemStore.getBranchNum().equals(branchNum)
					&& centerItemStore.getItemNum().equals(itemNum)){
				return centerItemStore;
			}
		}
		return null;
	}

}
