package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BusinessCollectionIncome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6265277934983900792L;
	private String name;
	private BigDecimal money;
	private BigDecimal qty;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		//返回空字符串会影响RPC组装
		if(name == null || name.isEmpty()){
			name = "   ";
		}
		this.name = name;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

}
