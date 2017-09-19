package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardQtySumDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -474514397813198532L;
	private Integer branchNum;// 门店编号
	private String branchName;// 门店名称
	private String bizDay;// 营业日
	private Integer inQty;// 入库数量
	private Integer revokeQty;// 卡回收数量
	private Integer sendQty;// 发卡数量
	private Integer changeQty;// 老会员转卡数量
	private Integer checkQty;// 盘点调整数量
	private Integer inventoryQty;// 库存量
	private BigDecimal depositMoney;// 卡存款汇总
	private Integer outQty;// 出库数量
	private Integer replaceQty;//换卡数量
	private List<TypeAndTwoValuesDTO> typeAndTwoValuesDatas = new ArrayList<TypeAndTwoValuesDTO>();// 卡存款付款方式明细
	
	public CardQtySumDTO(){
		setInQty(0);
		setRevokeQty(0);
		setSendQty(0);
		setChangeQty(0);
		setCheckQty(0);
		setDepositMoney(BigDecimal.ZERO);
		setOutQty(0);
		setReplaceQty(0);
	}

	public Integer getReplaceQty() {
		return replaceQty;
	}

	public void setReplaceQty(Integer replaceQty) {
		this.replaceQty = replaceQty;
	}

	public Integer getOutQty() {
		return outQty;
	}

	public void setOutQty(Integer outQty) {
		this.outQty = outQty;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBizDay() {
		return bizDay;
	}

	public void setBizDay(String bizDay) {
		this.bizDay = bizDay;
	}

	public Integer getInQty() {
		return inQty;
	}

	public void setInQty(Integer inQty) {
		this.inQty = inQty;
	}

	public Integer getRevokeQty() {
		return revokeQty;
	}

	public void setRevokeQty(Integer revokeQty) {
		this.revokeQty = revokeQty;
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public Integer getChangeQty() {
		return changeQty;
	}

	public void setChangeQty(Integer changeQty) {
		this.changeQty = changeQty;
	}

	public Integer getCheckQty() {
		return checkQty;
	}

	public void setCheckQty(Integer checkQty) {
		this.checkQty = checkQty;
	}

	public Integer getInventoryQty() {
		return inventoryQty;
	}

	public void setInventoryQty(Integer inventoryQty) {
		this.inventoryQty = inventoryQty;
	}

	public BigDecimal getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}

	public List<TypeAndTwoValuesDTO> getTypeAndTwoValuesDatas() {
		return typeAndTwoValuesDatas;
	}

	public void setTypeAndTwoValuesDatas(List<TypeAndTwoValuesDTO> typeAndTwoValuesDatas) {
		this.typeAndTwoValuesDatas = typeAndTwoValuesDatas;
	}

	public static CardQtySumDTO get(List<CardQtySumDTO> cardQtySumDTOs, Integer branchNum, String bizday){
		for(int i = 0;i < cardQtySumDTOs.size();i++){
			CardQtySumDTO cardQtySumDTO = cardQtySumDTOs.get(i);
			if(cardQtySumDTO.getBranchNum().equals(branchNum) && cardQtySumDTO.getBizDay().equals(bizday)){
				return cardQtySumDTO;
			}
		}
		return null;
	}
	
	public static CardQtySumDTO get(List<CardQtySumDTO> cardQtySumDTOs, Integer branchNum){
		for(int i = 0;i < cardQtySumDTOs.size();i++){
			CardQtySumDTO cardQtySumDTO = cardQtySumDTOs.get(i);
			if(cardQtySumDTO.getBranchNum().equals(branchNum)){
				return cardQtySumDTO;
			}
		}
		return null;
	}
	
}
