package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShipOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7183747157933133048L;
	private String shipOrderFid;
	private BigDecimal shipOrderMoney;

	public String getShipOrderFid() {
		return shipOrderFid;
	}

	public void setShipOrderFid(String shipOrderFid) {
		this.shipOrderFid = shipOrderFid;
	}

	public BigDecimal getShipOrderMoney() {
		return shipOrderMoney;
	}

	public void setShipOrderMoney(BigDecimal shipOrderMoney) {
		this.shipOrderMoney = shipOrderMoney;
	}

}
