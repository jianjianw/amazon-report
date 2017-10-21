package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class NameAndTwoValueDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6760885804659516805L;
	private String key;
	private String name;
	private BigDecimal value;
	private BigDecimal value2;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue2() {
		return value2;
	}

	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
	}

}
