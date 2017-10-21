package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopItemCategory implements Serializable {

	private static final long serialVersionUID = -1707572961465184521L;

	private Integer categroyid;
	private String categroyName;
	private Integer parentid;
	private List<ShopItemCategory> subCategories = new ArrayList<ShopItemCategory>();

	public Integer getCategroyid() {
		return categroyid;
	}

	public void setCategroyid(Integer categroyid) {
		this.categroyid = categroyid;
	}

	public String getCategroyName() {
		return categroyName;
	}

	public void setCategroyName(String categroyName) {
		this.categroyName = categroyName;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public List<ShopItemCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<ShopItemCategory> subCategories) {
		this.subCategories = subCategories;
	}
	
	

}
