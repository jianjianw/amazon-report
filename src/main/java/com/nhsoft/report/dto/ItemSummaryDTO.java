package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.param.ReportKey;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemSummaryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6930729728514576123L;
	@ReportKey
	private Integer itemNum;
	private String itemName;
	private String itemCode;
	private String itemUnit;
	private String itemSpec;
	private BigDecimal receiveOrderQty;
	private BigDecimal receiveOrderMoney;
	private BigDecimal posSaleQty;
	private BigDecimal posSaleMoney;
	private BigDecimal profit;

	public ItemSummaryDTO() {
		setReceiveOrderQty(BigDecimal.ZERO);
		setReceiveOrderMoney(BigDecimal.ZERO);
		setPosSaleQty(BigDecimal.ZERO);
		setPosSaleMoney(BigDecimal.ZERO);
		setProfit(BigDecimal.ZERO);
	}
	
	public BigDecimal getReceiveOrderQty() {
		return receiveOrderQty;
	}

	public void setReceiveOrderQty(BigDecimal receiveOrderQty) {
		this.receiveOrderQty = receiveOrderQty;
	}

	public BigDecimal getPosSaleQty() {
		return posSaleQty;
	}

	public void setPosSaleQty(BigDecimal posSaleQty) {
		this.posSaleQty = posSaleQty;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public BigDecimal getReceiveOrderMoney() {
		return receiveOrderMoney;
	}

	public void setReceiveOrderMoney(BigDecimal receiveOrderMoney) {
		this.receiveOrderMoney = receiveOrderMoney;
	}

	public BigDecimal getPosSaleMoney() {
		return posSaleMoney;
	}

	public void setPosSaleMoney(BigDecimal posSaleMoney) {
		this.posSaleMoney = posSaleMoney;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

}
