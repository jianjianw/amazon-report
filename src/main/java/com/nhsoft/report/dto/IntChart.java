package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class IntChart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5904925597644195039L;

	private String name;
	private Integer intValue;
	private BigDecimal bigDecimalValue;
	private BigDecimal bigDecimalValue2;

	public IntChart() {
		bigDecimalValue = BigDecimal.ZERO;
		bigDecimalValue2 = BigDecimal.ZERO;
		intValue = 0;
	}

	public BigDecimal getBigDecimalValue2() {
		return bigDecimalValue2;
	}

	public void setBigDecimalValue2(BigDecimal bigDecimalValue2) {
		this.bigDecimalValue2 = bigDecimalValue2;
	}

	public BigDecimal getBigDecimalValue() {
		return bigDecimalValue;
	}

	public void setBigDecimalValue(BigDecimal bigDecimalValue) {
		this.bigDecimalValue = bigDecimalValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}
	
}
