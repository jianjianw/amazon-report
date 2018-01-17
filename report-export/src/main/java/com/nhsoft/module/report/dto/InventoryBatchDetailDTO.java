package com.nhsoft.module.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * InventoryBatchDetail entity. @author MyEclipse Persistence Tools
 */

public class InventoryBatchDetailDTO implements java.io.Serializable {

	private static final long serialVersionUID = -2715190943803830563L;
	private Integer storehouseNum;
	private Integer inventoryNum;
	private Integer inventoryBatchDetailPri;
	private Integer itemNum;
	private Integer itemMatrixNum;
	private String inventoryBatchDetailRefFid;
	private String inventoryBatchDetailBizType;
	private BigDecimal inventoryBatchDetailCostPrice;
	private BigDecimal inventoryBatchDetailAmount;
	private BigDecimal inventoryBatchDetailInitAmount;
	private BigDecimal inventoryBatchDetailAssistAmount;
	private BigDecimal inventoryBatchDetailInitAssistAmount;
	private BigDecimal inventoryBatchDetailUseRate;
	private String inventoryBatchDetailUseUnit;
	private BigDecimal inventoryBatchDetailUseAmount;
	private Date inventoryBatchDetailUpdateTime;
	private Date inventoryBatchDetailDate;
	private String inventoryBatchDetailUuid;
	
	//临时属性 修改前数量 
	private BigDecimal inventoryBatchDetailPreAmount;

	public String getInventoryBatchDetailUuid() {
		return inventoryBatchDetailUuid;
	}

	public void setInventoryBatchDetailUuid(String inventoryBatchDetailUuid) {
		this.inventoryBatchDetailUuid = inventoryBatchDetailUuid;
	}

	public BigDecimal getInventoryBatchDetailPreAmount() {
		return inventoryBatchDetailPreAmount;
	}

	public void setInventoryBatchDetailPreAmount(BigDecimal inventoryBatchDetailPreAmount) {
		this.inventoryBatchDetailPreAmount = inventoryBatchDetailPreAmount;
	}

	public Date getInventoryBatchDetailDate() {
		return inventoryBatchDetailDate;
	}

	public void setInventoryBatchDetailDate(Date inventoryBatchDetailDate) {
		this.inventoryBatchDetailDate = inventoryBatchDetailDate;
	}

	public Date getInventoryBatchDetailUpdateTime() {
		return inventoryBatchDetailUpdateTime;
	}

	public void setInventoryBatchDetailUpdateTime(Date inventoryBatchDetailUpdateTime) {
		this.inventoryBatchDetailUpdateTime = inventoryBatchDetailUpdateTime;
	}

	public BigDecimal getInventoryBatchDetailUseRate() {
		return inventoryBatchDetailUseRate;
	}

	public void setInventoryBatchDetailUseRate(BigDecimal inventoryBatchDetailUseRate) {
		this.inventoryBatchDetailUseRate = inventoryBatchDetailUseRate;
	}

	public String getInventoryBatchDetailUseUnit() {
		return inventoryBatchDetailUseUnit;
	}

	public void setInventoryBatchDetailUseUnit(String inventoryBatchDetailUseUnit) {
		this.inventoryBatchDetailUseUnit = inventoryBatchDetailUseUnit;
	}

	public BigDecimal getInventoryBatchDetailUseAmount() {
		return inventoryBatchDetailUseAmount;
	}

	public void setInventoryBatchDetailUseAmount(BigDecimal inventoryBatchDetailUseAmount) {
		this.inventoryBatchDetailUseAmount = inventoryBatchDetailUseAmount;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public Integer getInventoryBatchDetailPri() {
		return inventoryBatchDetailPri;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}

	public void setInventoryBatchDetailPri(Integer inventoryBatchDetailPri) {
		this.inventoryBatchDetailPri = inventoryBatchDetailPri;
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

	public String getInventoryBatchDetailRefFid() {
		return inventoryBatchDetailRefFid;
	}

	public void setInventoryBatchDetailRefFid(String inventoryBatchDetailRefFid) {
		this.inventoryBatchDetailRefFid = inventoryBatchDetailRefFid;
	}

	public String getInventoryBatchDetailBizType() {
		return inventoryBatchDetailBizType;
	}

	public void setInventoryBatchDetailBizType(String inventoryBatchDetailBizType) {
		this.inventoryBatchDetailBizType = inventoryBatchDetailBizType;
	}

	public BigDecimal getInventoryBatchDetailCostPrice() {
		return inventoryBatchDetailCostPrice;
	}

	public void setInventoryBatchDetailCostPrice(BigDecimal inventoryBatchDetailCostPrice) {
		this.inventoryBatchDetailCostPrice = inventoryBatchDetailCostPrice;
	}

	public BigDecimal getInventoryBatchDetailAmount() {
		return inventoryBatchDetailAmount;
	}

	public void setInventoryBatchDetailAmount(BigDecimal inventoryBatchDetailAmount) {
		this.inventoryBatchDetailAmount = inventoryBatchDetailAmount;
	}

	public BigDecimal getInventoryBatchDetailInitAmount() {
		return inventoryBatchDetailInitAmount;
	}

	public void setInventoryBatchDetailInitAmount(BigDecimal inventoryBatchDetailInitAmount) {
		this.inventoryBatchDetailInitAmount = inventoryBatchDetailInitAmount;
	}

	public BigDecimal getInventoryBatchDetailAssistAmount() {
		return inventoryBatchDetailAssistAmount;
	}

	public void setInventoryBatchDetailAssistAmount(BigDecimal inventoryBatchDetailAssistAmount) {
		this.inventoryBatchDetailAssistAmount = inventoryBatchDetailAssistAmount;
	}

	public BigDecimal getInventoryBatchDetailInitAssistAmount() {
		return inventoryBatchDetailInitAssistAmount;
	}

	public void setInventoryBatchDetailInitAssistAmount(BigDecimal inventoryBatchDetailInitAssistAmount) {
		this.inventoryBatchDetailInitAssistAmount = inventoryBatchDetailInitAssistAmount;
	}

	public static List<InventoryBatchDetailDTO> findAndRemove(List<InventoryBatchDetailDTO> inventoryBatchDetails,
			Integer storehouseNum, Integer inventoryNum) {
		List<InventoryBatchDetailDTO> list = new ArrayList<InventoryBatchDetailDTO>();
		int size = inventoryBatchDetails.size();
		InventoryBatchDetailDTO detail = null;
		for(int i = size - 1;i >= 0;i--){
			detail =  inventoryBatchDetails.get(i);
			if(detail.getStorehouseNum().equals(storehouseNum)
					&& detail.getInventoryNum().equals(inventoryNum)){
				list.add(detail);
				inventoryBatchDetails.remove(i);
				continue;
			}
		}		
		return list;
	}

}