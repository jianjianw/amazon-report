package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ABCCharXy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2663372620431884674L;
	private String month;
	private BigDecimal content = BigDecimal.ZERO;	//销售金额 or 毛利
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getContent() {
		return content;
	}
	public void setContent(BigDecimal content) {
		this.content = content;
	}
}
