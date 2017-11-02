package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yangqin on 2017/11/2.
 */
public class PosItemLogSummaryDTO implements Serializable {
	
	/**
	 * group by
	 */
	private Integer itemNum;
	private Integer itemMatrixNum;
	private Integer branchNum;
	private Boolean inoutFlag;
	private String summary;
	private String bizday;
	
	
	/**
	 * sum
	 */
	private BigDecimal qty;
	private BigDecimal money;
	private BigDecimal assistQty;
	private BigDecimal useQty;
	private BigDecimal saleMoney;
	private String useUnit;
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}
	
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	
	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}
	
	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}
	
	public Integer getBranchNum() {
		return branchNum;
	}
	
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
	public Boolean getInoutFlag() {
		return inoutFlag;
	}
	
	public void setInoutFlag(Boolean inoutFlag) {
		this.inoutFlag = inoutFlag;
	}
	
	public String getBizday() {
		return bizday;
	}
	
	public void setBizday(String bizday) {
		this.bizday = bizday;
	}
	
	public BigDecimal getQty() {
		return qty;
	}
	
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public BigDecimal getAssistQty() {
		return assistQty;
	}
	
	public void setAssistQty(BigDecimal assistQty) {
		this.assistQty = assistQty;
	}
	
	public BigDecimal getUseQty() {
		return useQty;
	}
	
	public void setUseQty(BigDecimal useQty) {
		this.useQty = useQty;
	}
	
	public BigDecimal getSaleMoney() {
		return saleMoney;
	}
	
	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}
	
	public String getUseUnit() {
		return useUnit;
	}
	
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
}
