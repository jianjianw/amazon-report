package com.nhsoft.module.report.shared.queryBuilder;

import java.io.Serializable;

public abstract class QueryBuilder implements Serializable{

	private static final long serialVersionUID = 6415998873911206105L;
	protected String systemBookCode;

	public String getSystemBookCode() {
		return systemBookCode;
	}


	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}


	public abstract boolean checkQueryBuild();

}
