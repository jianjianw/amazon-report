package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CenterBoxReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4309071629919626433L;
	private BigDecimal boxPrice;// 筐单价
	private BigDecimal purchaseBoxQty;// 采购压筐数
	private BigDecimal purchaseBoxMoney;// 采购压筐金额
	private BigDecimal returnBoxQty;// 退筐数量
	private BigDecimal returnBoxMoney;// 退筐金额
	private BigDecimal inventoryBoxQty;// 在仓数量
	private BigDecimal inventoryBoxMoney;// 在仓金额
	
	public CenterBoxReportDTO(){
		setPurchaseBoxMoney(BigDecimal.ZERO);
		setPurchaseBoxQty(BigDecimal.ZERO);
		setReturnBoxMoney(BigDecimal.ZERO);
		setReturnBoxQty(BigDecimal.ZERO);
		setInventoryBoxMoney(BigDecimal.ZERO);
		setInventoryBoxQty(BigDecimal.ZERO);
	}

	public BigDecimal getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(BigDecimal boxPrice) {
		this.boxPrice = boxPrice;
	}

	public BigDecimal getPurchaseBoxQty() {
		return purchaseBoxQty;
	}

	public void setPurchaseBoxQty(BigDecimal purchaseBoxQty) {
		this.purchaseBoxQty = purchaseBoxQty;
	}

	public BigDecimal getPurchaseBoxMoney() {
		return purchaseBoxMoney;
	}

	public void setPurchaseBoxMoney(BigDecimal purchaseBoxMoney) {
		this.purchaseBoxMoney = purchaseBoxMoney;
	}

	public BigDecimal getReturnBoxQty() {
		return returnBoxQty;
	}

	public void setReturnBoxQty(BigDecimal returnBoxQty) {
		this.returnBoxQty = returnBoxQty;
	}

	public BigDecimal getReturnBoxMoney() {
		return returnBoxMoney;
	}

	public void setReturnBoxMoney(BigDecimal returnBoxMoney) {
		this.returnBoxMoney = returnBoxMoney;
	}

	public BigDecimal getInventoryBoxQty() {
		return inventoryBoxQty;
	}

	public void setInventoryBoxQty(BigDecimal inventoryBoxQty) {
		this.inventoryBoxQty = inventoryBoxQty;
	}

	public BigDecimal getInventoryBoxMoney() {
		return inventoryBoxMoney;
	}

	public void setInventoryBoxMoney(BigDecimal inventoryBoxMoney) {
		this.inventoryBoxMoney = inventoryBoxMoney;
	}
	
	public static CenterBoxReportDTO getCenterBoxReportDTO(List<CenterBoxReportDTO> centerBoxReportDTOs, BigDecimal price){
		for(int i = 0;i < centerBoxReportDTOs.size();i++){
			CenterBoxReportDTO centerBoxReportDTO = centerBoxReportDTOs.get(i);
			if(centerBoxReportDTO.getBoxPrice().compareTo(price) == 0){
				return centerBoxReportDTO;
			}
		}
		return null;
	}

}
