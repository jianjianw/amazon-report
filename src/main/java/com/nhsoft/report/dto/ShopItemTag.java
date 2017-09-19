package com.nhsoft.report.dto;

import java.io.Serializable;

public class ShopItemTag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6535522704470967426L;
	private Integer tagId;
	private String tagName;

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
