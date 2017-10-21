package com.nhsoft.module.report.query;

import java.io.Serializable;

public class TwoStringValueData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5785142451239057272L;
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
