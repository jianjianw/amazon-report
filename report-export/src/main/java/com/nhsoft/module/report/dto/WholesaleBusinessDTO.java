package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleBusinessDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4900500720087155582L;
	private BigDecimal wholesaleOrderMoney;
	private Integer wholesaleOrderCount;
	private Integer wholesaleReturnCount;
	private BigDecimal settlementMoney;
	private BigDecimal unSettlementMoney;
	
	public WholesaleBusinessDTO() {
		setWholesaleOrderMoney(BigDecimal.ZERO);
		setSettlementMoney(BigDecimal.ZERO);
		setUnSettlementMoney(BigDecimal.ZERO);
	}

	public BigDecimal getWholesaleOrderMoney() {
		return wholesaleOrderMoney;
	}

	public Integer getWholesaleOrderCount() {
		return wholesaleOrderCount;
	}

	public BigDecimal getSettlementMoney() {
		return settlementMoney;
	}

	public BigDecimal getUnSettlementMoney() {
		return unSettlementMoney;
	}

	public void setWholesaleOrderMoney(BigDecimal wholesaleOrderMoney) {
		this.wholesaleOrderMoney = wholesaleOrderMoney;
	}

	public void setWholesaleOrderCount(Integer wholesaleOrderCount) {
		this.wholesaleOrderCount = wholesaleOrderCount;
	}

	public void setSettlementMoney(BigDecimal settlementMoney) {
		this.settlementMoney = settlementMoney;
	}

	public void setUnSettlementMoney(BigDecimal unSettlementMoney) {
		this.unSettlementMoney = unSettlementMoney;
	}

	public Integer getWholesaleReturnCount() {
		return wholesaleReturnCount;
	}

	public void setWholesaleReturnCount(Integer wholesaleReturnCount) {
		this.wholesaleReturnCount = wholesaleReturnCount;
	}

}
