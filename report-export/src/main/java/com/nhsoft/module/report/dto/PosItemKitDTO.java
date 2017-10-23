package com.nhsoft.module.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * PosItemKit entity. @author MyEclipse Persistence Tools
 */


//TODO  新增属性后 posItemDaoHibernate相关查询代码也需要修改
public class PosItemKitDTO implements java.io.Serializable {

	private static final long serialVersionUID = -3876270134227789443L;
	private Integer itemNum;
	private Integer kitItemNum;
	private PosItemDTO posItem;
	private BigDecimal posItemKitAmount;
	private Integer posItemKitItemMatrixNum; 
	private String systemBookCode;
	private Boolean posItemAmountUnFixed;

	public Boolean getPosItemAmountUnFixed() {
		return posItemAmountUnFixed;
	}

	public void setPosItemAmountUnFixed(Boolean posItemAmountUnFixed) {
		this.posItemAmountUnFixed = posItemAmountUnFixed;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public Integer getKitItemNum() {
		return kitItemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public void setKitItemNum(Integer kitItemNum) {
		this.kitItemNum = kitItemNum;
	}

	public BigDecimal getPosItemKitAmount() {
		return posItemKitAmount;
	}

	public void setPosItemKitAmount(BigDecimal posItemKitAmount) {
		this.posItemKitAmount = posItemKitAmount;
	}

	public PosItemDTO getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItemDTO posItem) {
		this.posItem = posItem;
	}

	public Integer getPosItemKitItemMatrixNum() {
		return posItemKitItemMatrixNum;
	}

	public void setPosItemKitItemMatrixNum(Integer posItemKitItemMatrixNum) {
		this.posItemKitItemMatrixNum = posItemKitItemMatrixNum;
	}
	
	public static List<PosItemKitDTO> find(List<PosItemKitDTO> posItemKits, Integer itemNum){
		List<PosItemKitDTO> list = new ArrayList<PosItemKitDTO>();
		boolean find = false;
		for(int i = 0;i < posItemKits.size();i++){
			PosItemKitDTO posItemKit = posItemKits.get(i);
			if(find && !posItemKit.getItemNum().equals(itemNum)){
				break;
			}
			if(posItemKit.getItemNum().equals(itemNum)){
				list.add(posItemKit);
				posItemKits.remove(i);
				i--;
				find = true;
			}
		}
		return list;
	}
	
	public static List<PosItemKitDTO> findV2(List<PosItemKitDTO> posItemKits, Integer itemNum){
		List<PosItemKitDTO> list = new ArrayList<PosItemKitDTO>();
		boolean find = false;
		for(int i = 0;i < posItemKits.size();i++){
			PosItemKitDTO posItemKit = posItemKits.get(i);
			posItemKit.setPosItem(null);
			if(find && !posItemKit.getItemNum().equals(itemNum)){
				break;
			}
			if(posItemKit.getItemNum().equals(itemNum)){
				list.add(posItemKit);
				posItemKits.remove(i);
				i--;
				find = true;
			}
		}
		return list;
	}

}