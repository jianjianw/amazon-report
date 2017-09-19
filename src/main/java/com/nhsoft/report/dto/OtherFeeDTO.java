package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OtherFeeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -143600669121147027L;
	private String otherFeeName;
	private BigDecimal otherFeeQty;
	private BigDecimal otherFeeMoney;
	private BigDecimal otherFeePrice;

	public BigDecimal getOtherFeePrice() {
		return otherFeePrice;
	}

	public void setOtherFeePrice(BigDecimal otherFeePrice) {
		this.otherFeePrice = otherFeePrice;
	}

	public String getOtherFeeName() {
		return otherFeeName;
	}

	public void setOtherFeeName(String otherFeeName) {
		this.otherFeeName = otherFeeName;
	}

	public BigDecimal getOtherFeeQty() {
		return otherFeeQty;
	}

	public void setOtherFeeQty(BigDecimal otherFeeQty) {
		this.otherFeeQty = otherFeeQty;
	}

	public BigDecimal getOtherFeeMoney() {
		return otherFeeMoney;
	}

	public void setOtherFeeMoney(BigDecimal otherFeeMoney) {
		this.otherFeeMoney = otherFeeMoney;
	}

}
