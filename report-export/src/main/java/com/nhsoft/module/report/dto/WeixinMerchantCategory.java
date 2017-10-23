package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.List;

public class WeixinMerchantCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2771250261477036941L;
	/** 分类ID */
	public String id;

    /** 分类名称 */
    public String name;

    /** 子分类（不是API标准字段） */
    public List<WeixinMerchantCategory> subWeixinMerchantCategories;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WeixinMerchantCategory> getSubWeixinMerchantCategories() {
		return subWeixinMerchantCategories;
	}

	public void setSubWeixinMerchantCategories(List<WeixinMerchantCategory> subWeixinMerchantCategories) {
		this.subWeixinMerchantCategories = subWeixinMerchantCategories;
	}
    
    
}
