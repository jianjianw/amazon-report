package com.nhsoft.module.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ItemBar generated by hbm2java
 */
public class ItemBarDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8699653580551003603L;
	private Integer itemNum;
	private Integer itemBarNum;
	private String systemBookCode;
	private String itemBarCode;
	private Date itemBarCreateDate;
	private BigDecimal itemBarRate;

	public ItemBarDTO() {
	}

	public ItemBarDTO(Integer itemNum, Integer itemBarNum, String systemBookCode, String itemBarCode, Date itemBarCreateDate) {
		this.itemNum = itemNum;
		this.itemBarNum = itemBarNum;
		this.systemBookCode = systemBookCode;
		this.itemBarCode = itemBarCode;
		this.itemBarCreateDate = itemBarCreateDate;
	}

	public BigDecimal getItemBarRate() {
		return itemBarRate;
	}

	public void setItemBarRate(BigDecimal itemBarRate) {
		this.itemBarRate = itemBarRate;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public Integer getItemBarNum() {
		return itemBarNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public void setItemBarNum(Integer itemBarNum) {
		this.itemBarNum = itemBarNum;
	}

	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getItemBarCode() {
		return this.itemBarCode;
	}

	public void setItemBarCode(String itemBarCode) {
		this.itemBarCode = itemBarCode;
	}

	public Date getItemBarCreateDate() {
		return this.itemBarCreateDate;
	}

	public void setItemBarCreateDate(Date itemBarCreateDate) {
		this.itemBarCreateDate = itemBarCreateDate;
	}
	
	public static List<ItemBarDTO> find(List<ItemBarDTO> itemBars, Integer itemNum) {
		List<ItemBarDTO> list = new ArrayList<ItemBarDTO>();
		boolean find = false;
		for (int i = 0; i < itemBars.size(); i++) {
			ItemBarDTO itemBar = itemBars.get(i);
			if(find && !itemBar.getItemNum().equals(itemNum)){
				break;
			}			
			if (itemBar.getItemNum().equals(itemNum)) {
				list.add(itemBar);
				itemBars.remove(i);
				i--;
				find = true;
			}
		}
		return list;
	}

}
