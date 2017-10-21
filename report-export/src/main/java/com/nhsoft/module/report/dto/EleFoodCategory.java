package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EleFoodCategory implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2898065838099604464L;
	private long id;
    private String name;
    private String description;
    private int isValid;
    private long parentId;
    private List<EleFoodCategory> children = new ArrayList<EleFoodCategory>();
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getIsValid() {
		return isValid;
	}
	public long getParentId() {
		return parentId;
	}
	public List<EleFoodCategory> getChildren() {
		return children;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public void setChildren(List<EleFoodCategory> children) {
		this.children = children;
	}
}
