package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OutOrderPaying implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5259909323175570551L;
	private Integer fidNum;
	private BigDecimal Paimoney; // 金额
	private BigDecimal Duemoney; // 应收
	
	private Date date;
	private String memo;
	private String fid;

	public OutOrderPaying(){
		fidNum = 0;
		Paimoney = BigDecimal.ZERO;
		Duemoney = BigDecimal.ZERO;
	}
	
	public Integer getFidNum() {
		return fidNum;
	}

	public void setFidNum(Integer fidNum) {
		this.fidNum = fidNum;
	}

	public BigDecimal getMoney() {
		return Paimoney;
	}

	public void setMoney(BigDecimal Paimoney) {
		this.Paimoney = Paimoney;
	}

	public BigDecimal getDuemoney() {
		return Duemoney;
	}

	public void setDuemoney(BigDecimal duemoney) {
		this.Duemoney = duemoney;
	}

	public BigDecimal getPaimoney() {
		return Paimoney;
	}

	public void setPaimoney(BigDecimal paimoney) {
		Paimoney = paimoney;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	
}
