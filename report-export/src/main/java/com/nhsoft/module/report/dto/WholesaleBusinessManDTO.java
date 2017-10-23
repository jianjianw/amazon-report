package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleBusinessManDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1101653057909737050L;
	private String clientFid; // 客户主键
	private String name; //销售员名称 或 客户名称
	private Boolean newClientFlag; // 新客户标记
	private Integer wholesaleOrderCount;
	private Integer wholesaleReturnCount;
	private BigDecimal wholesaleMoney;
	
	public WholesaleBusinessManDTO() {
		wholesaleOrderCount = 0;
		wholesaleReturnCount = 0;
		setWholesaleMoney(BigDecimal.ZERO);
	}

	public String getClientFid() {
		return clientFid;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getWholesaleMoney() {
		return wholesaleMoney;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWholesaleMoney(BigDecimal wholesaleMoney) {
		this.wholesaleMoney = wholesaleMoney;
	}

	public Integer getWholesaleOrderCount() {
		return wholesaleOrderCount;
	}

	public Integer getWholesaleReturnCount() {
		return wholesaleReturnCount;
	}

	public void setWholesaleOrderCount(Integer wholesaleOrderCount) {
		this.wholesaleOrderCount = wholesaleOrderCount;
	}

	public void setWholesaleReturnCount(Integer wholesaleReturnCount) {
		this.wholesaleReturnCount = wholesaleReturnCount;
	}

	public Boolean getNewClientFlag() {
		return newClientFlag;
	}

	public void setNewClientFlag(Boolean newClientFlag) {
		this.newClientFlag = newClientFlag;
	}
	
}
