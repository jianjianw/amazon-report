package com.nhsoft.module.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ItemMatrix entity. @author MyEclipse Persistence Tools
 */

public class ItemMatrix implements java.io.Serializable {

	private static final long serialVersionUID = 8089649682486878559L;
	private ItemMatrixId id;
	private String itemMatrixBarcode;
	private String itemMatrixCode;
	private String itemMatrix01;
	private String itemMatrix02;
	private String itemMatrix03;
	private String itemMatrix04;
	private String itemMatrix05;
	private String itemMatrix06;
	private Boolean itemMatrixDelTag;
	private BigDecimal itemMatrixAddOns;
	private String systemBookCode;

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public ItemMatrixId getId() {
		return id;
	}

	public void setId(ItemMatrixId id) {
		this.id = id;
	}

	public String getItemMatrixBarcode() {
		return itemMatrixBarcode;
	}

	public void setItemMatrixBarcode(String itemMatrixBarcode) {
		this.itemMatrixBarcode = itemMatrixBarcode;
	}

	public String getItemMatrixCode() {
		return itemMatrixCode;
	}

	public void setItemMatrixCode(String itemMatrixCode) {
		this.itemMatrixCode = itemMatrixCode;
	}

	public String getItemMatrix01() {
		return itemMatrix01;
	}

	public void setItemMatrix01(String itemMatrix01) {
		this.itemMatrix01 = itemMatrix01;
	}

	public String getItemMatrix02() {
		return itemMatrix02;
	}

	public void setItemMatrix02(String itemMatrix02) {
		this.itemMatrix02 = itemMatrix02;
	}

	public String getItemMatrix03() {
		return itemMatrix03;
	}

	public void setItemMatrix03(String itemMatrix03) {
		this.itemMatrix03 = itemMatrix03;
	}

	public String getItemMatrix04() {
		return itemMatrix04;
	}

	public void setItemMatrix04(String itemMatrix04) {
		this.itemMatrix04 = itemMatrix04;
	}

	public String getItemMatrix05() {
		return itemMatrix05;
	}

	public void setItemMatrix05(String itemMatrix05) {
		this.itemMatrix05 = itemMatrix05;
	}

	public String getItemMatrix06() {
		return itemMatrix06;
	}

	public void setItemMatrix06(String itemMatrix06) {
		this.itemMatrix06 = itemMatrix06;
	}

	public Boolean getItemMatrixDelTag() {
		return itemMatrixDelTag;
	}

	public void setItemMatrixDelTag(Boolean itemMatrixDelTag) {
		this.itemMatrixDelTag = itemMatrixDelTag;
	}	

	public BigDecimal getItemMatrixAddOns() {
		return itemMatrixAddOns;
	}

	public void setItemMatrixAddOns(BigDecimal itemMatrixAddOns) {
		this.itemMatrixAddOns = itemMatrixAddOns;
	}
	
	public static List<ItemMatrix> find(List<ItemMatrix> itemMatrixs, Integer itemNum) {
		List<ItemMatrix> list = new ArrayList<ItemMatrix>();
		boolean find = false;
		for (int i = 0; i < itemMatrixs.size(); i++) {
			ItemMatrix itemMatrix = itemMatrixs.get(i);
			if(find && !itemMatrix.getId().getItemNum().equals(itemNum)){
				break;
			}
			if (itemMatrix.getId().getItemNum().equals(itemNum)) {
				list.add(itemMatrix);
				itemMatrixs.remove(i);
				i--;
				find = true;
			}
		}
		return list;
	}

}