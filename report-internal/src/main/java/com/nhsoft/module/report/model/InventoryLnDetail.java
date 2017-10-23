package com.nhsoft.module.report.model;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * InventoryLnDetail entity. @author MyEclipse Persistence Tools
 */

public class InventoryLnDetail implements java.io.Serializable {


	private static final long serialVersionUID = 2035213025643053078L;
	private InventoryLnDetailId id;
	private Integer itemNum;
	private Integer itemMatrixNum;
	private Date inventoryLnDetailProducingDate;
	private String inventoryLnDetailLotNumber;
	private BigDecimal inventoryLnDetailAmount;
	private BigDecimal inventoryLnDetailCostPrice;
	private BigDecimal inventoryLnDetailInitAmount;
	private BigDecimal inventoryLnDetailAssistAmount;
	private BigDecimal inventoryLnDetailInitAssistAmount;
	private Integer inventoryLnDetailPri;
	private Date inventoryLnDetailDate;
	private BigDecimal inventoryLnDetailUseRate;
	private String inventoryLnDetailUseUnit;
	private BigDecimal inventoryLnDetailUseAmount;
	private Date inventoryLnDetailUpdateTime;
	private BigDecimal inventoryLnDetailPackPrice;
	private String inventoryLnDetailMemo;
	private BigDecimal inventoryLnDetailTare;//件皮重
	private Integer supplierNum;

	//临时属性 
	private BigDecimal inventoryLnDetailRemainAmount;
	private BigDecimal inventoryLnDetailRemainAssistAmount;

	//临时属性 更新标记
	private boolean update = false;  

	public BigDecimal getInventoryLnDetailRemainAssistAmount() {
		return inventoryLnDetailRemainAssistAmount;
	}

	public void setInventoryLnDetailRemainAssistAmount(BigDecimal inventoryLnDetailRemainAssistAmount) {
		this.inventoryLnDetailRemainAssistAmount = inventoryLnDetailRemainAssistAmount;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public BigDecimal getInventoryLnDetailTare() {
		return inventoryLnDetailTare;
	}

	public void setInventoryLnDetailTare(BigDecimal inventoryLnDetailTare) {
		this.inventoryLnDetailTare = inventoryLnDetailTare;
	}

	public String getInventoryLnDetailMemo() {
		return inventoryLnDetailMemo;
	}

	public void setInventoryLnDetailMemo(String inventoryLnDetailMemo) {
		this.inventoryLnDetailMemo = inventoryLnDetailMemo;
	}

	public InventoryLnDetailId getId() {
		return id;
	}

	public BigDecimal getInventoryLnDetailRemainAmount() {
		return inventoryLnDetailRemainAmount;
	}

	public void setInventoryLnDetailRemainAmount(BigDecimal inventoryLnDetailRemainAmount) {
		this.inventoryLnDetailRemainAmount = inventoryLnDetailRemainAmount;
	}

	public void setId(InventoryLnDetailId id) {
		this.id = id;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	public Date getInventoryLnDetailProducingDate() {
		return inventoryLnDetailProducingDate;
	}

	public void setInventoryLnDetailProducingDate(
			Date inventoryLnDetailProducingDate) {
		this.inventoryLnDetailProducingDate = inventoryLnDetailProducingDate;
	}

	public String getInventoryLnDetailLotNumber() {
		return inventoryLnDetailLotNumber;
	}

	public void setInventoryLnDetailLotNumber(String inventoryLnDetailLotNumber) {
		this.inventoryLnDetailLotNumber = inventoryLnDetailLotNumber;
	}

	public BigDecimal getInventoryLnDetailAmount() {
		return inventoryLnDetailAmount;
	}

	public void setInventoryLnDetailAmount(BigDecimal inventoryLnDetailAmount) {
		this.inventoryLnDetailAmount = inventoryLnDetailAmount;
	}

	public BigDecimal getInventoryLnDetailCostPrice() {
		return inventoryLnDetailCostPrice;
	}

	public void setInventoryLnDetailCostPrice(
			BigDecimal inventoryLnDetailCostPrice) {
		this.inventoryLnDetailCostPrice = inventoryLnDetailCostPrice;
	}

	public BigDecimal getInventoryLnDetailInitAmount() {
		return inventoryLnDetailInitAmount;
	}

	public void setInventoryLnDetailInitAmount(
			BigDecimal inventoryLnDetailInitAmount) {
		this.inventoryLnDetailInitAmount = inventoryLnDetailInitAmount;
	}

	public BigDecimal getInventoryLnDetailAssistAmount() {
		return inventoryLnDetailAssistAmount;
	}

	public void setInventoryLnDetailAssistAmount(
			BigDecimal inventoryLnDetailAssistAmount) {
		this.inventoryLnDetailAssistAmount = inventoryLnDetailAssistAmount;
	}

	public BigDecimal getInventoryLnDetailInitAssistAmount() {
		return inventoryLnDetailInitAssistAmount;
	}

	public void setInventoryLnDetailInitAssistAmount(
			BigDecimal inventoryLnDetailInitAssistAmount) {
		this.inventoryLnDetailInitAssistAmount = inventoryLnDetailInitAssistAmount;
	}

	public Integer getInventoryLnDetailPri() {
		return inventoryLnDetailPri;
	}

	public void setInventoryLnDetailPri(Integer inventoryLnDetailPri) {
		this.inventoryLnDetailPri = inventoryLnDetailPri;
	}

	public Date getInventoryLnDetailDate() {
		return inventoryLnDetailDate;
	}

	public void setInventoryLnDetailDate(Date inventoryLnDetailDate) {
		this.inventoryLnDetailDate = inventoryLnDetailDate;
	}

	public BigDecimal getInventoryLnDetailUseRate() {
		return inventoryLnDetailUseRate;
	}

	public void setInventoryLnDetailUseRate(BigDecimal inventoryLnDetailUseRate) {
		this.inventoryLnDetailUseRate = inventoryLnDetailUseRate;
	}

	public String getInventoryLnDetailUseUnit() {
		return inventoryLnDetailUseUnit;
	}

	public void setInventoryLnDetailUseUnit(String inventoryLnDetailUseUnit) {
		this.inventoryLnDetailUseUnit = inventoryLnDetailUseUnit;
	}

	public BigDecimal getInventoryLnDetailUseAmount() {
		return inventoryLnDetailUseAmount;
	}

	public void setInventoryLnDetailUseAmount(BigDecimal inventoryLnDetailUseAmount) {
		this.inventoryLnDetailUseAmount = inventoryLnDetailUseAmount;
	}

	public Date getInventoryLnDetailUpdateTime() {
		return inventoryLnDetailUpdateTime;
	}

	public void setInventoryLnDetailUpdateTime(Date inventoryLnDetailUpdateTime) {
		this.inventoryLnDetailUpdateTime = inventoryLnDetailUpdateTime;
	}

	public BigDecimal getInventoryLnDetailPackPrice() {
		return inventoryLnDetailPackPrice;
	}

	public void setInventoryLnDetailPackPrice(BigDecimal inventoryLnDetailPackPrice) {
		this.inventoryLnDetailPackPrice = inventoryLnDetailPackPrice;
	}

	public static List<InventoryLnDetail> findAndRemove(List<InventoryLnDetail> inventoryLnDetails,
			Integer storehouseNum, Integer inventoryNum) {
		List<InventoryLnDetail> list = new ArrayList<InventoryLnDetail>();
		int size = inventoryLnDetails.size();
		InventoryLnDetail detail = null;
		for(int i = size - 1;i >= 0;i--){
			detail =  inventoryLnDetails.get(i);
			if(detail.getId().getStorehouseNum().equals(storehouseNum) 
					&& detail.getId().getInventoryNum().equals(inventoryNum)){
				list.add(detail);
				inventoryLnDetails.remove(i);
				continue;
			}
		}		
		return list;
	}

	public static InventoryLnDetail get(List<InventoryLnDetail> inventoryLnDetails, String inventoryLnDetailLotNumber) {
		for(int i = 0;i < inventoryLnDetails.size();i++){
			InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(i);
			if(StringUtils.equals(inventoryLnDetail.getInventoryLnDetailLotNumber(), inventoryLnDetailLotNumber)){
				return inventoryLnDetail;
			}
		}
		return null;
	}
	

}