package com.nhsoft.module.report.model;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ReceiveOrderDetail generated by hbm2java
 */
@Entity
public class ReceiveOrderDetail implements java.io.Serializable {

	private static final long serialVersionUID = 4667890246539139623L;
	@EmbeddedId
	private ReceiveOrderDetailId id;
	private Integer itemNum;
	private String receiveOrderDetailItemName;
	private String receiveOrderDetailItemSpec;
	private String receiveOrderDetailItemUnit;
	private BigDecimal receiveOrderDetailQty;
	private BigDecimal receiveOrderDetailCost;
	private BigDecimal receiveOrderDetailPresentCount;
	private BigDecimal receiveOrderDetailSubtotal;
	private BigDecimal receiveOrderDetailPrice;
	private BigDecimal receiveOrderDetailSaleSubtotal;
	private String receiveOrderDetailProducingArea;
	private String receiveOrderDetailMemo;
	private Integer receiveOrderDetailItemMatrixNum;
	private BigDecimal receiveOrderDetailNoTaxCost;
	private Date receiveOrderDetailProducingDate;
	private String receiveOrderDetailLotNumber;
	private BigDecimal receiveOrderDetailAssistQty;
	private String receiveOrderDetailItemAssistUnit;
	private BigDecimal receiveOrderDetailPresentCountAssist;
	private String receiveOrderDetailUseUnit;
	private BigDecimal receiveOrderDetailUseQty;
	private BigDecimal receiveOrderDetailUsePrice;
	private BigDecimal receiveOrderDetailUseRate;
	private String receiveOrderDetailItemCode;
	private String receiveOrderDetailPresentUnit;
	private BigDecimal receiveOrderDetailPresentUseQty;
	private String receiveOrderDetailPeriod;
	private BigDecimal receiveOrderDetailLastPrice;
	private BigDecimal receiveOrderDetailPurchaseQty;
	@Transient
	private BigDecimal receiveOrderDetailInventoryQty;
	@Transient
	private BigDecimal receiveOrderDetailInventoryAssistQty;
	private String receiveOrderDetailRefBill;  //order_task_detail_fid
	private BigDecimal receiveOrderDetailSupplierMoney;  //审核后存的是金额  审核前存的是件价 需特别注意
	private BigDecimal receiveOrderDetailOtherMoney;
	@Transient
	private BigDecimal receiveOrderDetailSalePrice;
	private BigDecimal receiveOrderDetailTare; //件皮重
	private BigDecimal receiveOrderDetailStdPrice; //标准售价
	private BigDecimal receiveOrderDetailTaxMoney;
	
	//临时属性
	@Transient
	private BigDecimal actualPrice;

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public ReceiveOrderDetailId getId() {
		return this.id;
	}

	public BigDecimal getReceiveOrderDetailStdPrice() {
		return receiveOrderDetailStdPrice;
	}

	public void setReceiveOrderDetailStdPrice(BigDecimal receiveOrderDetailStdPrice) {
		this.receiveOrderDetailStdPrice = receiveOrderDetailStdPrice;
	}

	public BigDecimal getReceiveOrderDetailTare() {
		return receiveOrderDetailTare;
	}

	public void setReceiveOrderDetailTare(BigDecimal receiveOrderDetailTare) {
		this.receiveOrderDetailTare = receiveOrderDetailTare;
	}

	public void setId(ReceiveOrderDetailId id) {
		this.id = id;
	}

	public BigDecimal getReceiveOrderDetailOtherMoney() {
		return receiveOrderDetailOtherMoney;
	}

	public void setReceiveOrderDetailOtherMoney(BigDecimal receiveOrderDetailOtherMoney) {
		this.receiveOrderDetailOtherMoney = receiveOrderDetailOtherMoney;
	}

	public Integer getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getReceiveOrderDetailItemName() {
		return this.receiveOrderDetailItemName;
	}

	public void setReceiveOrderDetailItemName(String receiveOrderDetailItemName) {
		this.receiveOrderDetailItemName = receiveOrderDetailItemName;
	}

	public String getReceiveOrderDetailItemSpec() {
		return this.receiveOrderDetailItemSpec;
	}

	public void setReceiveOrderDetailItemSpec(String receiveOrderDetailItemSpec) {
		this.receiveOrderDetailItemSpec = receiveOrderDetailItemSpec;
	}

	public String getReceiveOrderDetailItemUnit() {
		return this.receiveOrderDetailItemUnit;
	}

	public void setReceiveOrderDetailItemUnit(String receiveOrderDetailItemUnit) {
		this.receiveOrderDetailItemUnit = receiveOrderDetailItemUnit;
	}

	public BigDecimal getReceiveOrderDetailQty() {
		return this.receiveOrderDetailQty;
	}

	public void setReceiveOrderDetailQty(BigDecimal receiveOrderDetailQty) {
		this.receiveOrderDetailQty = receiveOrderDetailQty;
	}

	public BigDecimal getReceiveOrderDetailCost() {
		return this.receiveOrderDetailCost;
	}

	public void setReceiveOrderDetailCost(BigDecimal receiveOrderDetailCost) {
		this.receiveOrderDetailCost = receiveOrderDetailCost;
	}

	public BigDecimal getReceiveOrderDetailPresentCount() {
		return this.receiveOrderDetailPresentCount;
	}

	public void setReceiveOrderDetailPresentCount(
			BigDecimal receiveOrderDetailPresentCount) {
		this.receiveOrderDetailPresentCount = receiveOrderDetailPresentCount;
	}

	public BigDecimal getReceiveOrderDetailSubtotal() {
		return this.receiveOrderDetailSubtotal;
	}

	public void setReceiveOrderDetailSubtotal(
			BigDecimal receiveOrderDetailSubtotal) {
		this.receiveOrderDetailSubtotal = receiveOrderDetailSubtotal;
	}

	public BigDecimal getReceiveOrderDetailPrice() {
		return this.receiveOrderDetailPrice;
	}

	public void setReceiveOrderDetailPrice(BigDecimal receiveOrderDetailPrice) {
		this.receiveOrderDetailPrice = receiveOrderDetailPrice;
	}

	public BigDecimal getReceiveOrderDetailSaleSubtotal() {
		return this.receiveOrderDetailSaleSubtotal;
	}

	public void setReceiveOrderDetailSaleSubtotal(
			BigDecimal receiveOrderDetailSaleSubtotal) {
		this.receiveOrderDetailSaleSubtotal = receiveOrderDetailSaleSubtotal;
	}

	public String getReceiveOrderDetailProducingArea() {
		return this.receiveOrderDetailProducingArea;
	}

	public void setReceiveOrderDetailProducingArea(
			String receiveOrderDetailProducingArea) {
		this.receiveOrderDetailProducingArea = receiveOrderDetailProducingArea;
	}

	public String getReceiveOrderDetailMemo() {
		return this.receiveOrderDetailMemo;
	}

	public void setReceiveOrderDetailMemo(String receiveOrderDetailMemo) {
		this.receiveOrderDetailMemo = receiveOrderDetailMemo;
	}

	public Integer getReceiveOrderDetailItemMatrixNum() {
		return this.receiveOrderDetailItemMatrixNum;
	}

	public void setReceiveOrderDetailItemMatrixNum(
			Integer receiveOrderDetailItemMatrixNum) {
		this.receiveOrderDetailItemMatrixNum = receiveOrderDetailItemMatrixNum;
	}

	public BigDecimal getReceiveOrderDetailNoTaxCost() {
		return this.receiveOrderDetailNoTaxCost;
	}

	public void setReceiveOrderDetailNoTaxCost(
			BigDecimal receiveOrderDetailNoTaxCost) {
		this.receiveOrderDetailNoTaxCost = receiveOrderDetailNoTaxCost;
	}

	public Date getReceiveOrderDetailProducingDate() {
		return this.receiveOrderDetailProducingDate;
	}

	public void setReceiveOrderDetailProducingDate(
			Date receiveOrderDetailProducingDate) {
		this.receiveOrderDetailProducingDate = receiveOrderDetailProducingDate;
	}

	public String getReceiveOrderDetailLotNumber() {
		return this.receiveOrderDetailLotNumber;
	}

	public void setReceiveOrderDetailLotNumber(
			String receiveOrderDetailLotNumber) {
		this.receiveOrderDetailLotNumber = receiveOrderDetailLotNumber;
	}

	public BigDecimal getReceiveOrderDetailAssistQty() {
		return this.receiveOrderDetailAssistQty;
	}

	public void setReceiveOrderDetailAssistQty(
			BigDecimal receiveOrderDetailAssistQty) {
		this.receiveOrderDetailAssistQty = receiveOrderDetailAssistQty;
	}

	public String getReceiveOrderDetailItemAssistUnit() {
		return this.receiveOrderDetailItemAssistUnit;
	}

	public void setReceiveOrderDetailItemAssistUnit(
			String receiveOrderDetailItemAssistUnit) {
		this.receiveOrderDetailItemAssistUnit = receiveOrderDetailItemAssistUnit;
	}

	public BigDecimal getReceiveOrderDetailPresentCountAssist() {
		return this.receiveOrderDetailPresentCountAssist;
	}

	public void setReceiveOrderDetailPresentCountAssist(
			BigDecimal receiveOrderDetailPresentCountAssist) {
		this.receiveOrderDetailPresentCountAssist = receiveOrderDetailPresentCountAssist;
	}

	public String getReceiveOrderDetailUseUnit() {
		return this.receiveOrderDetailUseUnit;
	}

	public void setReceiveOrderDetailUseUnit(String receiveOrderDetailUseUnit) {
		this.receiveOrderDetailUseUnit = receiveOrderDetailUseUnit;
	}

	public BigDecimal getReceiveOrderDetailUseQty() {
		return this.receiveOrderDetailUseQty;
	}

	public void setReceiveOrderDetailUseQty(BigDecimal receiveOrderDetailUseQty) {
		this.receiveOrderDetailUseQty = receiveOrderDetailUseQty;
	}

	public BigDecimal getReceiveOrderDetailUsePrice() {
		return this.receiveOrderDetailUsePrice;
	}

	public void setReceiveOrderDetailUsePrice(
			BigDecimal receiveOrderDetailUsePrice) {
		this.receiveOrderDetailUsePrice = receiveOrderDetailUsePrice;
	}

	public BigDecimal getReceiveOrderDetailUseRate() {
		return this.receiveOrderDetailUseRate;
	}

	public void setReceiveOrderDetailUseRate(
			BigDecimal receiveOrderDetailUseRate) {
		this.receiveOrderDetailUseRate = receiveOrderDetailUseRate;
	}

	public String getReceiveOrderDetailItemCode() {
		return this.receiveOrderDetailItemCode;
	}

	public void setReceiveOrderDetailItemCode(String receiveOrderDetailItemCode) {
		this.receiveOrderDetailItemCode = receiveOrderDetailItemCode;
	}

	public String getReceiveOrderDetailPresentUnit() {
		return this.receiveOrderDetailPresentUnit;
	}

	public void setReceiveOrderDetailPresentUnit(
			String receiveOrderDetailPresentUnit) {
		this.receiveOrderDetailPresentUnit = receiveOrderDetailPresentUnit;
	}

	public BigDecimal getReceiveOrderDetailPresentUseQty() {
		return this.receiveOrderDetailPresentUseQty;
	}

	public void setReceiveOrderDetailPresentUseQty(
			BigDecimal receiveOrderDetailPresentUseQty) {
		this.receiveOrderDetailPresentUseQty = receiveOrderDetailPresentUseQty;
	}

	public String getReceiveOrderDetailPeriod() {
		return this.receiveOrderDetailPeriod;
	}

	public void setReceiveOrderDetailPeriod(String receiveOrderDetailPeriod) {
		this.receiveOrderDetailPeriod = receiveOrderDetailPeriod;
	}

	public BigDecimal getReceiveOrderDetailLastPrice() {
		return receiveOrderDetailLastPrice;
	}

	public void setReceiveOrderDetailLastPrice(
			BigDecimal receiveOrderDetailLastPrice) {
		this.receiveOrderDetailLastPrice = receiveOrderDetailLastPrice;
	}

	public BigDecimal getReceiveOrderDetailPurchaseQty() {
		return receiveOrderDetailPurchaseQty;
	}

	public void setReceiveOrderDetailPurchaseQty(BigDecimal receiveOrderDetailPurchaseQty) {
		this.receiveOrderDetailPurchaseQty = receiveOrderDetailPurchaseQty;
	}

	public BigDecimal getReceiveOrderDetailInventoryQty() {
		return receiveOrderDetailInventoryQty;
	}

	public void setReceiveOrderDetailInventoryQty(BigDecimal receiveOrderDetailInventoryQty) {
		this.receiveOrderDetailInventoryQty = receiveOrderDetailInventoryQty;
	}

	public BigDecimal getReceiveOrderDetailInventoryAssistQty() {
		return receiveOrderDetailInventoryAssistQty;
	}

	public void setReceiveOrderDetailInventoryAssistQty(BigDecimal receiveOrderDetailInventoryAssistQty) {
		this.receiveOrderDetailInventoryAssistQty = receiveOrderDetailInventoryAssistQty;
	}

	public String getReceiveOrderDetailRefBill() {
		return receiveOrderDetailRefBill;
	}

	public void setReceiveOrderDetailRefBill(String receiveOrderDetailRefBill) {
		this.receiveOrderDetailRefBill = receiveOrderDetailRefBill;
	}

	public BigDecimal getReceiveOrderDetailSupplierMoney() {
		return receiveOrderDetailSupplierMoney;
	}

	public void setReceiveOrderDetailSupplierMoney(BigDecimal receiveOrderDetailSupplierMoney) {
		this.receiveOrderDetailSupplierMoney = receiveOrderDetailSupplierMoney;
	}
	
	public BigDecimal getReceiveOrderDetailSalePrice() {
		return receiveOrderDetailSalePrice;
	}

	public void setReceiveOrderDetailSalePrice(BigDecimal receiveOrderDetailSalePrice) {
		this.receiveOrderDetailSalePrice = receiveOrderDetailSalePrice;
	}

	public BigDecimal getReceiveOrderDetailTaxMoney() {
		return receiveOrderDetailTaxMoney;
	}

	public void setReceiveOrderDetailTaxMoney(BigDecimal receiveOrderDetailTaxMoney) {
		this.receiveOrderDetailTaxMoney = receiveOrderDetailTaxMoney;
	}

	public static List<ReceiveOrderDetail> findDetails(List<ReceiveOrderDetail> receiveOrderDetails, String receiveOrderFid){
		List<ReceiveOrderDetail> list= new ArrayList<ReceiveOrderDetail>();
		for(int i = 0;i < receiveOrderDetails.size();i++){
			ReceiveOrderDetail receiveOrderDetail = receiveOrderDetails.get(i);
			if(receiveOrderDetail.getId().getReceiveOrderFid().equals(receiveOrderFid)){
				list.add(receiveOrderDetail);
			}
		}
		return list;
	}
	
	public static ReceiveOrderDetail getDetails(List<ReceiveOrderDetail> receiveOrderDetails, Integer itemNum){
		for(int i = 0;i < receiveOrderDetails.size();i++){
			ReceiveOrderDetail receiveOrderDetail = receiveOrderDetails.get(i);
			if(receiveOrderDetail.getItemNum().equals(itemNum)){
				return receiveOrderDetail;
			}
		}
		return null;
	}

}
