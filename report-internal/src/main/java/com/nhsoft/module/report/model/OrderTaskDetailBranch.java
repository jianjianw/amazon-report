package com.nhsoft.module.report.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class OrderTaskDetailBranch implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1801179548548265243L;
	@Id
	private String orderTaskDetailBranchFid;
	private String orderTaskDetailFid;
	private String systemBookCode;
	private Integer branchNum;
	private String branchName;
	private BigDecimal transferQty;
	private BigDecimal transferUseQty;
	private BigDecimal transferViewQty;
	private BigDecimal transferTare;
	
	public BigDecimal getTransferViewQty() {
		return transferViewQty;
	}

	public void setTransferViewQty(BigDecimal transferViewQty) {
		this.transferViewQty = transferViewQty;
	}

	public String getOrderTaskDetailBranchFid() {
		return orderTaskDetailBranchFid;
	}
	
	public void setOrderTaskDetailBranchFid(String orderTaskDetailBranchFid) {
		this.orderTaskDetailBranchFid = orderTaskDetailBranchFid;
	}
	
	public BigDecimal getTransferTare() {
		return transferTare;
	}
	
	public void setTransferTare(BigDecimal transferTare) {
		this.transferTare = transferTare;
	}
	
	public String getOrderTaskDetailFid() {
		return orderTaskDetailFid;
	}
	
	public void setOrderTaskDetailFid(String orderTaskDetailFid) {
		this.orderTaskDetailFid = orderTaskDetailFid;
	}
	
	public String getSystemBookCode() {
		return systemBookCode;
	}
	
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	
	public Integer getBranchNum() {
		return branchNum;
	}
	
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public BigDecimal getTransferQty() {
		return transferQty;
	}
	
	public void setTransferQty(BigDecimal transferQty) {
		this.transferQty = transferQty;
	}
	
	public BigDecimal getTransferUseQty() {
		return transferUseQty;
	}
	
	public void setTransferUseQty(BigDecimal transferUseQty) {
		this.transferUseQty = transferUseQty;
	}
	
}
