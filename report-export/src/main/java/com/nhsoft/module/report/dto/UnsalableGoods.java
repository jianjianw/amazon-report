package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UnsalableGoods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5836575063045925225L;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private String itemSpec;
	private BigDecimal wholeNum; // 批发数量
	private BigDecimal wholePrice; // 批发价格
	private BigDecimal wholeCost; // 批发成本
	private BigDecimal wholeProfit; // 批发毛利
	private BigDecimal outNum; // 调出数量
	private BigDecimal outPrice; // 调出价格
	private BigDecimal outCost; // 调出成本
	private BigDecimal outProfit; // 调出毛利
	private BigDecimal saleNum; // 销售数量
	private BigDecimal salePrice; // 销售价格
	private BigDecimal saleCost; // 销售成本
	private BigDecimal saleProfit; // 销售毛利
	private BigDecimal inventory; //库存量

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

	public BigDecimal getInventory() {
		return inventory;
	}

	public void setInventory(BigDecimal inventory) {
		this.inventory = inventory;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public BigDecimal getWholeNum() {
		return wholeNum;
	}

	public void setWholeNum(BigDecimal wholeNum) {
		this.wholeNum = wholeNum;
	}

	public BigDecimal getWholeCost() {
		return wholeCost;
	}

	public void setWholeCost(BigDecimal wholeCost) {
		this.wholeCost = wholeCost;
	}

	public BigDecimal getWholeProfit() {
		return wholeProfit;
	}

	public void setWholeProfit(BigDecimal wholeProfit) {
		this.wholeProfit = wholeProfit;
	}

	public BigDecimal getOutNum() {
		return outNum;
	}

	public void setOutNum(BigDecimal outNum) {
		this.outNum = outNum;
	}

	public BigDecimal getOutCost() {
		return outCost;
	}

	public void setOutCost(BigDecimal outCost) {
		this.outCost = outCost;
	}

	public BigDecimal getOutProfit() {
		return outProfit;
	}

	public void setOutProfit(BigDecimal outProfit) {
		this.outProfit = outProfit;
	}

	public BigDecimal getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(BigDecimal saleNum) {
		this.saleNum = saleNum;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public BigDecimal getSaleProfit() {
		return saleProfit;
	}

	public void setSaleProfit(BigDecimal saleProfit) {
		this.saleProfit = saleProfit;
	}
	
	public BigDecimal getWholePrice() {
		return wholePrice;
	}

	public void setWholePrice(BigDecimal wholePrice) {
		this.wholePrice = wholePrice;
	}

	public BigDecimal getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(BigDecimal outPrice) {
		this.outPrice = outPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnsalableGoods other = (UnsalableGoods) obj;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		return true;
	}

}
