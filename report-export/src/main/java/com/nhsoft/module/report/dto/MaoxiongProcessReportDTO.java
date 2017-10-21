package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class MaoxiongProcessReportDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -225408991531630710L;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private String itemUnit;
	private String purchaseBizday;
	private BigDecimal itemOutAmount;//领料量
	private BigDecimal itemProcessAmount;//加工量
	private BigDecimal itemProcessDiffAmount;//加工损耗
	private BigDecimal itemCostPrice;//成本单价
	private BigDecimal itemCostMoney;//成本金额
	
	private BigDecimal itemTransferAmount;//配送量
	private BigDecimal itemTransferPrice;//配送单价
	private BigDecimal itemTransferMoney;//配送金额
	private BigDecimal itemTransferDiffAmount;//配送损耗
	private BigDecimal itemTransferProfit;//配送毛利
	private BigDecimal itemTransferProfitRate;//配送毛利率
	
	private BigDecimal currentItemFinishRate;//当前出成率
	private BigDecimal itemFinishRate;//出成率
	
	public MaoxiongProcessReportDTO(){
		setItemProcessAmount(BigDecimal.ZERO);
		setItemTransferAmount(BigDecimal.ZERO);
		setItemTransferMoney(BigDecimal.ZERO);
		
	}
	
	public BigDecimal getCurrentItemFinishRate() {
		return currentItemFinishRate;
	}
	
	public void setCurrentItemFinishRate(BigDecimal currentItemFinishRate) {
		this.currentItemFinishRate = currentItemFinishRate;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}


	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}


	public String getItemCode() {
		return itemCode;
	}


	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getItemUnit() {
		return itemUnit;
	}


	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}


	public String getPurchaseBizday() {
		return purchaseBizday;
	}


	public void setPurchaseBizday(String purchaseBizday) {
		this.purchaseBizday = purchaseBizday;
	}


	public BigDecimal getItemOutAmount() {
		return itemOutAmount;
	}


	public void setItemOutAmount(BigDecimal itemOutAmount) {
		this.itemOutAmount = itemOutAmount;
	}


	public BigDecimal getItemProcessAmount() {
		return itemProcessAmount;
	}


	public void setItemProcessAmount(BigDecimal itemProcessAmount) {
		this.itemProcessAmount = itemProcessAmount;
	}


	public BigDecimal getItemProcessDiffAmount() {
		return itemProcessDiffAmount;
	}


	public void setItemProcessDiffAmount(BigDecimal itemProcessDiffAmount) {
		this.itemProcessDiffAmount = itemProcessDiffAmount;
	}


	public BigDecimal getItemCostPrice() {
		return itemCostPrice;
	}


	public void setItemCostPrice(BigDecimal itemCostPrice) {
		this.itemCostPrice = itemCostPrice;
	}


	public BigDecimal getItemCostMoney() {
		return itemCostMoney;
	}


	public void setItemCostMoney(BigDecimal itemCostMoney) {
		this.itemCostMoney = itemCostMoney;
	}


	public BigDecimal getItemTransferAmount() {
		return itemTransferAmount;
	}


	public void setItemTransferAmount(BigDecimal itemTransferAmount) {
		this.itemTransferAmount = itemTransferAmount;
	}


	public BigDecimal getItemTransferPrice() {
		return itemTransferPrice;
	}


	public void setItemTransferPrice(BigDecimal itemTransferPrice) {
		this.itemTransferPrice = itemTransferPrice;
	}


	public BigDecimal getItemTransferMoney() {
		return itemTransferMoney;
	}


	public void setItemTransferMoney(BigDecimal itemTransferMoney) {
		this.itemTransferMoney = itemTransferMoney;
	}


	public BigDecimal getItemTransferDiffAmount() {
		return itemTransferDiffAmount;
	}


	public void setItemTransferDiffAmount(BigDecimal itemTransferDiffAmount) {
		this.itemTransferDiffAmount = itemTransferDiffAmount;
	}


	public BigDecimal getItemTransferProfit() {
		return itemTransferProfit;
	}


	public void setItemTransferProfit(BigDecimal itemTransferProfit) {
		this.itemTransferProfit = itemTransferProfit;
	}


	public BigDecimal getItemTransferProfitRate() {
		return itemTransferProfitRate;
	}


	public void setItemTransferProfitRate(BigDecimal itemTransferProfitRate) {
		this.itemTransferProfitRate = itemTransferProfitRate;
	}


	public BigDecimal getItemFinishRate() {
		return itemFinishRate;
	}


	public void setItemFinishRate(BigDecimal itemFinishRate) {
		this.itemFinishRate = itemFinishRate;
	}
	
	public static MaoxiongProcessReportDTO get(List<MaoxiongProcessReportDTO> list, Integer itemNum){
		if(itemNum == null){
			return null;
		}
		for(int i = 0;i < list.size();i++){
			MaoxiongProcessReportDTO dto = list.get(i);
			if(dto.getItemNum().equals(itemNum)){
				return dto;
			}
		}
		return null;
	}
	
	public static MaoxiongProcessReportDTO get(List<MaoxiongProcessReportDTO> list, Integer itemNum, String purchaseBizday){
		for(int i = 0;i < list.size();i++){
			MaoxiongProcessReportDTO dto = list.get(i);
			if(dto.getItemNum().equals(itemNum) && dto.getPurchaseBizday().equals(purchaseBizday)){
				return dto;
			}
		}
		return null;
	}
	

	
}
