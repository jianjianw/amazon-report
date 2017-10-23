package com.nhsoft.module.report.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * BranchResource entity. @author MyEclipse Persistence Tools
 */

@Entity
public class BranchResource implements java.io.Serializable {

	private static final long serialVersionUID = 5025676189966560125L;
	@EmbeddedId
	private BranchResourceId id;
	private String branchResourceParam;

	public BranchResourceId getId() {
		return id;
	}

	public void setId(BranchResourceId id) {
		this.id = id;
	}

	public String getBranchResourceParam() {
		return branchResourceParam;
	}

	public void setBranchResourceParam(String branchResourceParam) {
		this.branchResourceParam = branchResourceParam;
	}

}