package com.nhsoft.module.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * PosItemKit entity. @author MyEclipse Persistence Tools
 */


//TODO  新增属性后 posItemDaoHibernate相关查询代码也需要修改
public class PosItemKit implements java.io.Serializable {

	private static final long serialVersionUID = -3876270134227789443L;
	private PosItemKitId id;
	private PosItem posItem;
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

	public PosItemKitId getId() {
		return id;
	}

	public void setId(PosItemKitId id) {
		this.id = id;
	}

	public BigDecimal getPosItemKitAmount() {
		return posItemKitAmount;
	}

	public void setPosItemKitAmount(BigDecimal posItemKitAmount) {
		this.posItemKitAmount = posItemKitAmount;
	}

	public PosItem getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItem posItem) {
		this.posItem = posItem;
	}

	public Integer getPosItemKitItemMatrixNum() {
		return posItemKitItemMatrixNum;
	}

	public void setPosItemKitItemMatrixNum(Integer posItemKitItemMatrixNum) {
		this.posItemKitItemMatrixNum = posItemKitItemMatrixNum;
	}
	
	public static List<PosItemKit> find(List<PosItemKit> posItemKits, Integer itemNum){
		List<PosItemKit> list = new ArrayList<PosItemKit>();
		boolean find = false;
		for(int i = 0;i < posItemKits.size();i++){
			PosItemKit posItemKit = posItemKits.get(i);
			if(find && !posItemKit.getId().getItemNum().equals(itemNum)){
				break;
			}
			if(posItemKit.getId().getItemNum().equals(itemNum)){
				list.add(posItemKit);
				posItemKits.remove(i);
				i--;
				find = true;
			}
		}
		return list;
	}
	
	public static List<PosItemKit> findV2(List<PosItemKit> posItemKits, Integer itemNum){
		List<PosItemKit> list = new ArrayList<PosItemKit>();
		boolean find = false;
		for(int i = 0;i < posItemKits.size();i++){
			PosItemKit posItemKit = posItemKits.get(i);
			posItemKit.setPosItem(null);
			if(find && !posItemKit.getId().getItemNum().equals(itemNum)){
				break;
			}
			if(posItemKit.getId().getItemNum().equals(itemNum)){
				list.add(posItemKit);
				posItemKits.remove(i);
				i--;
				find = true;
			}
		}
		return list;
	}

}