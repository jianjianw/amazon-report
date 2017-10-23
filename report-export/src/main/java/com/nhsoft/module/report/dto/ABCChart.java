package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ABCChart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3934697170026084913L;
	private Integer itemNum; // 商品NUM
	private String itemName; // 商品NAME
	private List<ABCCharXy> abcchartXYs = new ArrayList<ABCCharXy>();
	
	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public List<ABCCharXy> getAbcchartXYs() {
		return abcchartXYs;
	}

	public void setAbcchartXYs(List<ABCCharXy> abcchartXYs) {
		this.abcchartXYs = abcchartXYs;
	}

}
