package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangqin on 2017/8/3.
 */
public class NewItemAnalysis implements  Serializable{
	
	
	private static final long serialVersionUID = 3727403826371760452L;
	private String itemCreator;
	private Date itemCreateDate;
	private Integer itemNum;
	private String itemName;
	private String itemCode;
	private String itemSpec;
	private String itemUnit;
	private BigDecimal itemSaleMoney;
	private BigDecimal itemSaleProfit;
	
	public Date getItemCreateDate() {
		return itemCreateDate;
	}
	
	public void setItemCreateDate(Date itemCreateDate) {
		this.itemCreateDate = itemCreateDate;
	}
	
	public String getItemCreator() {
		return itemCreator;
	}
	
	public void setItemCreator(String itemCreator) {
		this.itemCreator = itemCreator;
	}
	
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
	
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemSpec() {
		return itemSpec;
	}
	
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}
	
	public String getItemUnit() {
		return itemUnit;
	}
	
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	
	public BigDecimal getItemSaleMoney() {
		return itemSaleMoney;
	}
	
	public void setItemSaleMoney(BigDecimal itemSaleMoney) {
		this.itemSaleMoney = itemSaleMoney;
	}
	
	public BigDecimal getItemSaleProfit() {
		return itemSaleProfit;
	}
	
	public void setItemSaleProfit(BigDecimal itemSaleProfit) {
		this.itemSaleProfit = itemSaleProfit;
	}
}
