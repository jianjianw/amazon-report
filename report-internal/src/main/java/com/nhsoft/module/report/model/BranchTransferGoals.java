package com.nhsoft.module.report.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Branch entity. @author MyEclipse Persistence Tools
 */

@Entity
public class BranchTransferGoals implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8442385698180998816L;
	@EmbeddedId
	private BranchTransferGoalsId id;
	private BigDecimal branchTransferValue;
	private BigDecimal branchTransferSaleValue;
	private BigDecimal branchTransferGrossValue;
	private BigDecimal branchTransferDiffValue;
	private Date branchTransferStart;
	private Date branchTransferEnd;
	private Boolean branchTransferSynchFlag;

	public BigDecimal getBranchTransferDiffValue() {
		return branchTransferDiffValue;
	}

	public void setBranchTransferDiffValue(BigDecimal branchTransferDiffValue) {
		this.branchTransferDiffValue = branchTransferDiffValue;
	}

	public BranchTransferGoalsId getId() {
		return id;
	}

	public void setId(BranchTransferGoalsId id) {
		this.id = id;
	}

	public BigDecimal getBranchTransferValue() {
		return branchTransferValue;
	}

	public void setBranchTransferValue(BigDecimal branchTransferValue) {
		this.branchTransferValue = branchTransferValue;
	}

	public BigDecimal getBranchTransferSaleValue() {
		return branchTransferSaleValue;
	}

	public void setBranchTransferSaleValue(BigDecimal branchTransferSaleValue) {
		this.branchTransferSaleValue = branchTransferSaleValue;
	}

	public BigDecimal getBranchTransferGrossValue() {
		return branchTransferGrossValue;
	}

	public void setBranchTransferGrossValue(BigDecimal branchTransferGrossValue) {
		this.branchTransferGrossValue = branchTransferGrossValue;
	}

	public Date getBranchTransferStart() {
		return branchTransferStart;
	}

	public void setBranchTransferStart(Date branchTransferStart) {
		this.branchTransferStart = branchTransferStart;
	}

	public Date getBranchTransferEnd() {
		return branchTransferEnd;
	}

	public void setBranchTransferEnd(Date branchTransferEnd) {
		this.branchTransferEnd = branchTransferEnd;
	}

	public Boolean getBranchTransferSynchFlag() {
		return branchTransferSynchFlag;
	}

	public void setBranchTransferSynchFlag(Boolean branchTransferSynchFlag) {
		this.branchTransferSynchFlag = branchTransferSynchFlag;
	}

}