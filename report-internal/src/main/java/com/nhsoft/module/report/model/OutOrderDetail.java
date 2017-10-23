package com.nhsoft.module.report.model;


import com.nhsoft.module.report.dto.GsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OutOrderDetail entity. @author MyEclipse Persistence Tools
 */

public class OutOrderDetail implements java.io.Serializable {

	private static final long serialVersionUID = 5808497566717324855L;
	private OutOrderDetailId id;
	private Integer itemNum;
	private String outOrderDetailItemName;
	private BigDecimal outOrderDetailQty;
	private BigDecimal outOrderDetailAssistQty;
	private BigDecimal outOrderDetailInventoryAssistQty;
	private BigDecimal outOrderDetailInventoryQty;
	private BigDecimal outOrderDetailCustOrderQty;
	private Integer outOrderDetailItemMatrixNum;
	private BigDecimal outOrderDetailCost;
	private BigDecimal outOrderDetailSubtotal;
	private String outOrderDetailItemUnit;
	private String outOrderDetailItemAssistUnit;
	private String outOrderDetailMemo;
	private Date outOrderDetailInvalidDate;
	private BigDecimal outOrderDetailPrice;
	private BigDecimal outOrderDetailSaleSubtotal;
	private BigDecimal outOrderDetailTaxRate;
	private Date outOrderDetailProducingDate;
	private String outOrderDetailLotNumber;
	private String outOrderDetailItemCode;
	private String outOrderDetailItemSpec;
	private String outOrderDetailUseUnit;
	private BigDecimal outOrderDetailUseQty;
	private BigDecimal outOrderDetailUsePrice;
	private BigDecimal outOrderDetailUseRate;
	private BigDecimal outOrderDetailSalePrice;
	private String outOrderDetailPeriod;
	private BigDecimal outOrderDetailInQty;
	private BigDecimal outOrderDetailInAssistQty;
	private String outOrderDetailPresentUseUnit;
	private BigDecimal outOrderDetailPresentUseQty;
	private BigDecimal outOrderDetailPresentBasicQty;
	private BigDecimal outOrderDetailPresentAssistQty;
	private String outOrderDetailRefBill; //order_task_detail_fid
	private BigDecimal outOrderDetailOtherFee;
	private BigDecimal outOrderDetailTare;
	private BigDecimal outOrderDetailQtyAppand;
	private BigDecimal outOrderDetailStdTare;//标准皮重
	private BigDecimal outOrderDetailStdPrice;//原价
	private String outOrderDetailPolicyNo;
	private BigDecimal outOrderDetailInPresentQty;
	private BigDecimal outOrderDetailPolicyUseQty;
	private BigDecimal outOrderDetailInventoryBasicQty;
	private Integer supplierNum;
	private BigDecimal outOrderDetailActualTare;
	private BigDecimal outOrderDetailActualQty; //实际净重


	//查询用
	@GsonIgnore
	private TransferOutOrder transferOutOrder;
	
	// 临时属性
	private BigDecimal itemInventoryRate;
	@GsonIgnore
	private ItemMatrix itemMatrix;
	private Boolean centerPackage = false;//是否中心压筐
	private BigDecimal outOrderDetailPreUseRate; //扣减皮重之前的转换率
	private BigDecimal outOrderDetailLockInventoryQty;
	private BigDecimal outOrderDetailUseViewQty;
	private Boolean fixProfit;//是否锁定毛利率
	private String itemCostMode;
	
	public String getItemCostMode() {
		return itemCostMode;
	}
	
	public void setItemCostMode(String itemCostMode) {
		this.itemCostMode = itemCostMode;
	}
	
	public Boolean getFixProfit() {
		return fixProfit;
	}

	public void setFixProfit(Boolean fixProfit) {
		this.fixProfit = fixProfit;
	}

	public BigDecimal getOutOrderDetailActualQty() {
		return outOrderDetailActualQty;
	}

	public void setOutOrderDetailActualQty(BigDecimal outOrderDetailActualQty) {
		this.outOrderDetailActualQty = outOrderDetailActualQty;
	}

	public BigDecimal getOutOrderDetailUseViewQty() {
		return outOrderDetailUseViewQty;
	}

	public void setOutOrderDetailUseViewQty(BigDecimal outOrderDetailUseViewQty) {
		this.outOrderDetailUseViewQty = outOrderDetailUseViewQty;
	}

	public BigDecimal getOutOrderDetailActualTare() {
		return outOrderDetailActualTare;
	}

	public void setOutOrderDetailActualTare(BigDecimal outOrderDetailActualTare) {
		this.outOrderDetailActualTare = outOrderDetailActualTare;
	}

	public BigDecimal getOutOrderDetailLockInventoryQty() {
		return outOrderDetailLockInventoryQty;
	}

	public void setOutOrderDetailLockInventoryQty(BigDecimal outOrderDetailLockInventoryQty) {
		this.outOrderDetailLockInventoryQty = outOrderDetailLockInventoryQty;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public BigDecimal getOutOrderDetailInventoryBasicQty() {
		return outOrderDetailInventoryBasicQty;
	}

	public void setOutOrderDetailInventoryBasicQty(BigDecimal outOrderDetailInventoryBasicQty) {
		this.outOrderDetailInventoryBasicQty = outOrderDetailInventoryBasicQty;
	}

	public BigDecimal getOutOrderDetailPolicyUseQty() {
		return outOrderDetailPolicyUseQty;
	}

	public void setOutOrderDetailPolicyUseQty(BigDecimal outOrderDetailPolicyUseQty) {
		this.outOrderDetailPolicyUseQty = outOrderDetailPolicyUseQty;
	}

	public BigDecimal getOutOrderDetailInPresentQty() {
		return outOrderDetailInPresentQty;
	}

	public void setOutOrderDetailInPresentQty(BigDecimal outOrderDetailInPresentQty) {
		this.outOrderDetailInPresentQty = outOrderDetailInPresentQty;
	}

	public String getOutOrderDetailPolicyNo() {
		return outOrderDetailPolicyNo;
	}

	public void setOutOrderDetailPolicyNo(String outOrderDetailPolicyNo) {
		this.outOrderDetailPolicyNo = outOrderDetailPolicyNo;
	}

	public BigDecimal getOutOrderDetailStdPrice() {
		return outOrderDetailStdPrice;
	}

	public void setOutOrderDetailStdPrice(BigDecimal outOrderDetailStdPrice) {
		this.outOrderDetailStdPrice = outOrderDetailStdPrice;
	}

	public BigDecimal getOutOrderDetailStdTare() {
		return outOrderDetailStdTare;
	}

	public void setOutOrderDetailStdTare(BigDecimal outOrderDetailStdTare) {
		this.outOrderDetailStdTare = outOrderDetailStdTare;
	}

	public BigDecimal getOutOrderDetailPreUseRate() {
		return outOrderDetailPreUseRate;
	}

	public void setOutOrderDetailPreUseRate(BigDecimal outOrderDetailPreUseRate) {
		this.outOrderDetailPreUseRate = outOrderDetailPreUseRate;
	}

	public BigDecimal getOutOrderDetailTare() {
		return outOrderDetailTare;
	}

	public void setOutOrderDetailTare(BigDecimal outOrderDetailTare) {
		this.outOrderDetailTare = outOrderDetailTare;
	}

	public BigDecimal getOutOrderDetailQtyAppand() {
		return outOrderDetailQtyAppand;
	}

	public void setOutOrderDetailQtyAppand(BigDecimal outOrderDetailQtyAppand) {
		this.outOrderDetailQtyAppand = outOrderDetailQtyAppand;
	}

	public Boolean getCenterPackage() {
		return centerPackage;
	}

	public void setCenterPackage(Boolean centerPackage) {
		this.centerPackage = centerPackage;
	}

	public BigDecimal getOutOrderDetailOtherFee() {
		return outOrderDetailOtherFee;
	}

	public void setOutOrderDetailOtherFee(BigDecimal outOrderDetailOtherFee) {
		this.outOrderDetailOtherFee = outOrderDetailOtherFee;
	}

	public String getOutOrderDetailRefBill() {
		return outOrderDetailRefBill;
	}

	public void setOutOrderDetailRefBill(String outOrderDetailRefBill) {
		this.outOrderDetailRefBill = outOrderDetailRefBill;
	}

	public String getOutOrderDetailPeriod() {
		return outOrderDetailPeriod;
	}

	public void setOutOrderDetailPeriod(String outOrderDetailPeriod) {
		this.outOrderDetailPeriod = outOrderDetailPeriod;
	}

	public BigDecimal getOutOrderDetailSalePrice() {
		return outOrderDetailSalePrice;
	}

	public void setOutOrderDetailSalePrice(BigDecimal outOrderDetailSalePrice) {
		this.outOrderDetailSalePrice = outOrderDetailSalePrice;
	}

	public OutOrderDetailId getId() {
		return id;
	}

	public void setId(OutOrderDetailId id) {
		this.id = id;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getOutOrderDetailItemName() {
		return outOrderDetailItemName;
	}

	public void setOutOrderDetailItemName(String outOrderDetailItemName) {
		this.outOrderDetailItemName = outOrderDetailItemName;
	}

	public BigDecimal getOutOrderDetailQty() {
		return outOrderDetailQty;
	}

	public void setOutOrderDetailQty(BigDecimal outOrderDetailQty) {
		this.outOrderDetailQty = outOrderDetailQty;
	}

	public BigDecimal getOutOrderDetailAssistQty() {
		return outOrderDetailAssistQty;
	}

	public void setOutOrderDetailAssistQty(BigDecimal outOrderDetailAssistQty) {
		this.outOrderDetailAssistQty = outOrderDetailAssistQty;
	}

	public BigDecimal getOutOrderDetailInventoryAssistQty() {
		return outOrderDetailInventoryAssistQty;
	}

	public void setOutOrderDetailInventoryAssistQty(BigDecimal outOrderDetailInventoryAssistQty) {
		this.outOrderDetailInventoryAssistQty = outOrderDetailInventoryAssistQty;
	}

	public BigDecimal getOutOrderDetailInventoryQty() {
		return outOrderDetailInventoryQty;
	}

	public void setOutOrderDetailInventoryQty(BigDecimal outOrderDetailInventoryQty) {
		this.outOrderDetailInventoryQty = outOrderDetailInventoryQty;
	}

	public BigDecimal getOutOrderDetailCustOrderQty() {
		return outOrderDetailCustOrderQty;
	}

	public void setOutOrderDetailCustOrderQty(BigDecimal outOrderDetailCustOrderQty) {
		this.outOrderDetailCustOrderQty = outOrderDetailCustOrderQty;
	}

	public Integer getOutOrderDetailItemMatrixNum() {
		return outOrderDetailItemMatrixNum;
	}

	public void setOutOrderDetailItemMatrixNum(Integer outOrderDetailItemMatrixNum) {
		this.outOrderDetailItemMatrixNum = outOrderDetailItemMatrixNum;
	}

	public BigDecimal getOutOrderDetailCost() {
		return outOrderDetailCost;
	}

	public void setOutOrderDetailCost(BigDecimal outOrderDetailCost) {
		this.outOrderDetailCost = outOrderDetailCost;
	}

	public BigDecimal getOutOrderDetailSubtotal() {
		return outOrderDetailSubtotal;
	}

	public void setOutOrderDetailSubtotal(BigDecimal outOrderDetailSubtotal) {
		this.outOrderDetailSubtotal = outOrderDetailSubtotal;
	}

	public String getOutOrderDetailItemUnit() {
		return outOrderDetailItemUnit;
	}

	public void setOutOrderDetailItemUnit(String outOrderDetailItemUnit) {
		this.outOrderDetailItemUnit = outOrderDetailItemUnit;
	}

	public String getOutOrderDetailItemAssistUnit() {
		return outOrderDetailItemAssistUnit;
	}

	public void setOutOrderDetailItemAssistUnit(String outOrderDetailItemAssistUnit) {
		this.outOrderDetailItemAssistUnit = outOrderDetailItemAssistUnit;
	}

	public String getOutOrderDetailMemo() {
		return outOrderDetailMemo;
	}

	public void setOutOrderDetailMemo(String outOrderDetailMemo) {
		this.outOrderDetailMemo = outOrderDetailMemo;
	}

	public Date getOutOrderDetailInvalidDate() {
		return outOrderDetailInvalidDate;
	}

	public void setOutOrderDetailInvalidDate(Date outOrderDetailInvalidDate) {
		this.outOrderDetailInvalidDate = outOrderDetailInvalidDate;
	}

	public BigDecimal getOutOrderDetailPrice() {
		return outOrderDetailPrice;
	}

	public void setOutOrderDetailPrice(BigDecimal outOrderDetailPrice) {
		this.outOrderDetailPrice = outOrderDetailPrice;
	}

	public BigDecimal getOutOrderDetailSaleSubtotal() {
		return outOrderDetailSaleSubtotal;
	}

	public void setOutOrderDetailSaleSubtotal(BigDecimal outOrderDetailSaleSubtotal) {
		this.outOrderDetailSaleSubtotal = outOrderDetailSaleSubtotal;
	}

	public BigDecimal getOutOrderDetailTaxRate() {
		return outOrderDetailTaxRate;
	}

	public void setOutOrderDetailTaxRate(BigDecimal outOrderDetailTaxRate) {
		this.outOrderDetailTaxRate = outOrderDetailTaxRate;
	}

	public Date getOutOrderDetailProducingDate() {
		return outOrderDetailProducingDate;
	}

	public void setOutOrderDetailProducingDate(Date outOrderDetailProducingDate) {
		this.outOrderDetailProducingDate = outOrderDetailProducingDate;
	}

	public String getOutOrderDetailLotNumber() {
		return outOrderDetailLotNumber;
	}

	public void setOutOrderDetailLotNumber(String outOrderDetailLotNumber) {
		this.outOrderDetailLotNumber = outOrderDetailLotNumber;
	}

	public String getOutOrderDetailItemCode() {
		return outOrderDetailItemCode;
	}

	public void setOutOrderDetailItemCode(String outOrderDetailItemCode) {
		this.outOrderDetailItemCode = outOrderDetailItemCode;
	}

	public String getOutOrderDetailItemSpec() {
		return outOrderDetailItemSpec;
	}

	public void setOutOrderDetailItemSpec(String outOrderDetailItemSpec) {
		this.outOrderDetailItemSpec = outOrderDetailItemSpec;
	}

	public String getOutOrderDetailUseUnit() {
		return outOrderDetailUseUnit;
	}

	public void setOutOrderDetailUseUnit(String outOrderDetailUseUnit) {
		this.outOrderDetailUseUnit = outOrderDetailUseUnit;
	}

	public BigDecimal getOutOrderDetailUseQty() {
		return outOrderDetailUseQty;
	}

	public void setOutOrderDetailUseQty(BigDecimal outOrderDetailUseQty) {
		this.outOrderDetailUseQty = outOrderDetailUseQty;
	}

	public BigDecimal getOutOrderDetailUsePrice() {
		return outOrderDetailUsePrice;
	}

	public void setOutOrderDetailUsePrice(BigDecimal outOrderDetailUsePrice) {
		this.outOrderDetailUsePrice = outOrderDetailUsePrice;
	}

	public BigDecimal getOutOrderDetailUseRate() {
		return outOrderDetailUseRate;
	}

	public void setOutOrderDetailUseRate(BigDecimal outOrderDetailUseRate) {
		this.outOrderDetailUseRate = outOrderDetailUseRate;
	}

	public BigDecimal getOutOrderDetailInQty() {
		return outOrderDetailInQty;
	}

	public void setOutOrderDetailInQty(BigDecimal outOrderDetailInQty) {
		this.outOrderDetailInQty = outOrderDetailInQty;
	}

	public BigDecimal getOutOrderDetailInAssistQty() {
		return outOrderDetailInAssistQty;
	}

	public void setOutOrderDetailInAssistQty(BigDecimal outOrderDetailInAssistQty) {
		this.outOrderDetailInAssistQty = outOrderDetailInAssistQty;
	}

	public BigDecimal getItemInventoryRate() {
		return itemInventoryRate;
	}

	public void setItemInventoryRate(BigDecimal itemInventoryRate) {
		this.itemInventoryRate = itemInventoryRate;
	}

	public ItemMatrix getItemMatrix() {
		return itemMatrix;
	}

	public void setItemMatrix(ItemMatrix itemMatrix) {
		this.itemMatrix = itemMatrix;
	}

	public BigDecimal getOutOrderDetailPresentUseQty() {
		return outOrderDetailPresentUseQty;
	}

	public void setOutOrderDetailPresentUseQty(BigDecimal outOrderDetailPresentUseQty) {
		this.outOrderDetailPresentUseQty = outOrderDetailPresentUseQty;
	}

	public String getOutOrderDetailPresentUseUnit() {
		return outOrderDetailPresentUseUnit;
	}

	public void setOutOrderDetailPresentUseUnit(String outOrderDetailPresentUseUnit) {
		this.outOrderDetailPresentUseUnit = outOrderDetailPresentUseUnit;
	}

	public BigDecimal getOutOrderDetailPresentBasicQty() {
		return outOrderDetailPresentBasicQty;
	}

	public void setOutOrderDetailPresentBasicQty(BigDecimal outOrderDetailPresentBasicQty) {
		this.outOrderDetailPresentBasicQty = outOrderDetailPresentBasicQty;
	}

	public BigDecimal getOutOrderDetailPresentAssistQty() {
		return outOrderDetailPresentAssistQty;
	}

	public void setOutOrderDetailPresentAssistQty(BigDecimal outOrderDetailPresentAssistQty) {
		this.outOrderDetailPresentAssistQty = outOrderDetailPresentAssistQty;
	}

	public TransferOutOrder getTransferOutOrder() {
		return transferOutOrder;
	}

	public void setTransferOutOrder(TransferOutOrder transferOutOrder) {
		this.transferOutOrder = transferOutOrder;
	}

	public static List<OutOrderDetail> findDetails(List<OutOrderDetail> outOrderDetails, String outOrderFid) {
		List<OutOrderDetail> list= new ArrayList<OutOrderDetail>();
		for(int i = 0;i < outOrderDetails.size();i++){
			OutOrderDetail outOrderDetail = outOrderDetails.get(i);
			if(outOrderDetail.getId().getOutOrderFid().equals(outOrderFid)){
				list.add(outOrderDetail);
			}
		}
		return list;
	}
	
	public static OutOrderDetail get(List<OutOrderDetail> outOrderDetails, Integer itemNum,
			Integer itemMatrixNum, String lotNumber) {
		for (int i = 0; i < outOrderDetails.size(); i++) {
			OutOrderDetail outOrderDetail = outOrderDetails.get(i);
			if (outOrderDetail.getItemNum().equals(itemNum)
					&& outOrderDetail.getOutOrderDetailItemMatrixNum().equals(itemMatrixNum)
					&& outOrderDetail.getOutOrderDetailLotNumber().equals(lotNumber)) {
				return outOrderDetail;
			}
		}

		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutOrderDetail other = (OutOrderDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}