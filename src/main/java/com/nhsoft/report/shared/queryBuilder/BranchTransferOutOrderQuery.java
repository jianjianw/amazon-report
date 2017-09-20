package com.nhsoft.report.shared.queryBuilder;

import com.nhsoft.pos3.shared.State;

import java.util.Date;

public class BranchTransferOutOrderQuery extends QueryBuilder{

	private static final long serialVersionUID = -1490590318253607897L;
	
	private Integer branchNum;
	private Date outDateFrom;
	private Date outDateTo;
	private Date auditDateFrom;
	private Date auditDateTo;
	private String outOrderFid;
	private State outOrderState;
	private String sortField;
	private String sortName;

	public BranchTransferOutOrderQuery(){

	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public BranchTransferOutOrderQuery(String systemBookCode){
		this.systemBookCode = systemBookCode;
	}

	public Date getOutDateFrom() {
		return outDateFrom;
	}

	public void setOutDateFrom(Date outDateFrom) {
		this.outDateFrom = outDateFrom;
	}

	public Date getOutDateTo() {
		return outDateTo;
	}

	public void setOutDateTo(Date outDateTo) {
		this.outDateTo = outDateTo;
	}

	public Date getAuditDateFrom() {
		return auditDateFrom;
	}

	public void setAuditDateFrom(Date auditDateFrom) {
		this.auditDateFrom = auditDateFrom;
	}

	public Date getAuditDateTo() {
		return auditDateTo;
	}

	public void setAuditDateTo(Date auditDateTo) {
		this.auditDateTo = auditDateTo;
	}

	public String getOutOrderFid() {
		return outOrderFid;
	}

	public void setOutOrderFid(String outOrderFid) {
		this.outOrderFid = outOrderFid;
	}

	public State getOutOrderState() {
		return outOrderState;
	}

	public void setOutOrderState(State outOrderState) {
		this.outOrderState = outOrderState;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	@Override
	public boolean checkQueryBuild() {
		if (outDateFrom != null && outDateTo != null) {
			if (outDateFrom.after(outDateTo)) {
				return false;
			}
		}
		if (auditDateFrom != null && auditDateTo != null) {
			if (auditDateFrom.after(auditDateTo)) {
				return false;
			}
		}
		return true;
	}


}