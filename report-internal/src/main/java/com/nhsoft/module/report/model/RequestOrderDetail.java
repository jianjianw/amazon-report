package com.nhsoft.module.report.model;


import com.nhsoft.module.report.dto.GsonIgnore;

import java.math.BigDecimal;
import java.util.List;

/**
 * RequestOrderDetail entity. @author MyEclipse Persistence Tools
 */

public class RequestOrderDetail implements java.io.Serializable {

	private static final long serialVersionUID = 4454653338602908706L;
	private RequestOrderDetailId id;
	private Integer itemNum;
	private String requestOrderDetailItemName;
	private BigDecimal requestOrderDetailQty;
	private BigDecimal requestOrderDetailInventoryQty;
	private BigDecimal requestOrderDetailCenterQty;
	private BigDecimal requestOrderDetailCustOrderQty;
	private Integer requestOrderDetailItemMatrixNum;
	private BigDecimal requestOrderDetailCost;
	private BigDecimal requestOrderDetailSubtotal;
	private String requestOrderDetailItemUnit;
	private String requestOrderDetailMemo;
	private BigDecimal requestOrderDetailOutQty;
	private BigDecimal requestOrderDetailPurchaseQty;
	private BigDecimal requestOrderDetailOutReceivedQty;
	private BigDecimal requestOrderDetailPurchaseReceivedQty;
	private Boolean requestOrderDetailIgnore;
	private String requestOrderDetailItemSpec;
	private String requestOrderDetailItemCode;
	private String requestOrderDetailUseUnit;
	private BigDecimal requestOrderDetailUseQty;
	private BigDecimal requestOrderDetailUsePrice;
	private BigDecimal requestOrderDetailUseRate;
	private String requestOrderDetailAssistUnit;
	private BigDecimal requestOrderDetailAssistQty;
	private BigDecimal requestOrderDetailSalePrice;
	private String requestOrderDetailPeriod;
	private BigDecimal requestOrderDetailOutMoney;
	private BigDecimal requestOrderDetailInMoney;
	private BigDecimal requestOrderDetailOutUseQty;
	private BigDecimal requestOrderDetailOutReceivedUseQty;
	private Integer requestOrderDetailItemType;
	
	//临时属性
	private BigDecimal itemInventoryRate;
	@GsonIgnore
	private ItemMatrix itemMatrix;
	private BigDecimal requestOrderDetailActualQty;

	

	public BigDecimal getRequestOrderDetailActualQty() {
		return requestOrderDetailActualQty;
	}

	public void setRequestOrderDetailActualQty(BigDecimal requestOrderDetailActualQty) {
		this.requestOrderDetailActualQty = requestOrderDetailActualQty;
	}

	public Integer getRequestOrderDetailItemType() {
		return requestOrderDetailItemType;
	}

	public void setRequestOrderDetailItemType(Integer requestOrderDetailItemType) {
		this.requestOrderDetailItemType = requestOrderDetailItemType;
	}

	public BigDecimal getRequestOrderDetailOutUseQty() {
		return requestOrderDetailOutUseQty;
	}

	public void setRequestOrderDetailOutUseQty(BigDecimal requestOrderDetailOutUseQty) {
		this.requestOrderDetailOutUseQty = requestOrderDetailOutUseQty;
	}

	public BigDecimal getRequestOrderDetailOutReceivedUseQty() {
		return requestOrderDetailOutReceivedUseQty;
	}

	public void setRequestOrderDetailOutReceivedUseQty(BigDecimal requestOrderDetailOutReceivedUseQty) {
		this.requestOrderDetailOutReceivedUseQty = requestOrderDetailOutReceivedUseQty;
	}

	public RequestOrderDetailId getId() {
		return id;
	}

	public void setId(RequestOrderDetailId id) {
		this.id = id;
	}

	public String getRequestOrderDetailPeriod() {
		return requestOrderDetailPeriod;
	}

	public void setRequestOrderDetailPeriod(String requestOrderDetailPeriod) {
		this.requestOrderDetailPeriod = requestOrderDetailPeriod;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getRequestOrderDetailItemName() {
		return requestOrderDetailItemName;
	}

	public void setRequestOrderDetailItemName(String requestOrderDetailItemName) {
		this.requestOrderDetailItemName = requestOrderDetailItemName;
	}

	public BigDecimal getRequestOrderDetailQty() {
		return requestOrderDetailQty;
	}

	public void setRequestOrderDetailQty(BigDecimal requestOrderDetailQty) {
		this.requestOrderDetailQty = requestOrderDetailQty;
	}

	public BigDecimal getRequestOrderDetailInventoryQty() {
		return requestOrderDetailInventoryQty;
	}

	public void setRequestOrderDetailInventoryQty(
			BigDecimal requestOrderDetailInventoryQty) {
		this.requestOrderDetailInventoryQty = requestOrderDetailInventoryQty;
	}

	public BigDecimal getRequestOrderDetailCenterQty() {
		return requestOrderDetailCenterQty;
	}

	public void setRequestOrderDetailCenterQty(
			BigDecimal requestOrderDetailCenterQty) {
		this.requestOrderDetailCenterQty = requestOrderDetailCenterQty;
	}

	public BigDecimal getRequestOrderDetailCustOrderQty() {
		return requestOrderDetailCustOrderQty;
	}

	public void setRequestOrderDetailCustOrderQty(
			BigDecimal requestOrderDetailCustOrderQty) {
		this.requestOrderDetailCustOrderQty = requestOrderDetailCustOrderQty;
	}

	public Integer getRequestOrderDetailItemMatrixNum() {
		return requestOrderDetailItemMatrixNum;
	}

	public void setRequestOrderDetailItemMatrixNum(
			Integer requestOrderDetailItemMatrixNum) {
		this.requestOrderDetailItemMatrixNum = requestOrderDetailItemMatrixNum;
	}

	public BigDecimal getRequestOrderDetailCost() {
		return requestOrderDetailCost;
	}

	public void setRequestOrderDetailCost(BigDecimal requestOrderDetailCost) {
		this.requestOrderDetailCost = requestOrderDetailCost;
	}

	public BigDecimal getRequestOrderDetailSubtotal() {
		return requestOrderDetailSubtotal;
	}

	public void setRequestOrderDetailSubtotal(
			BigDecimal requestOrderDetailSubtotal) {
		this.requestOrderDetailSubtotal = requestOrderDetailSubtotal;
	}

	public String getRequestOrderDetailItemUnit() {
		return requestOrderDetailItemUnit;
	}

	public void setRequestOrderDetailItemUnit(String requestOrderDetailItemUnit) {
		this.requestOrderDetailItemUnit = requestOrderDetailItemUnit;
	}

	public String getRequestOrderDetailMemo() {
		return requestOrderDetailMemo;
	}

	public void setRequestOrderDetailMemo(String requestOrderDetailMemo) {
		this.requestOrderDetailMemo = requestOrderDetailMemo;
	}

	public BigDecimal getRequestOrderDetailOutQty() {
		return requestOrderDetailOutQty;
	}

	public void setRequestOrderDetailOutQty(BigDecimal requestOrderDetailOutQty) {
		this.requestOrderDetailOutQty = requestOrderDetailOutQty;
	}

	public BigDecimal getRequestOrderDetailPurchaseQty() {
		return requestOrderDetailPurchaseQty;
	}

	public void setRequestOrderDetailPurchaseQty(
			BigDecimal requestOrderDetailPurchaseQty) {
		this.requestOrderDetailPurchaseQty = requestOrderDetailPurchaseQty;
	}

	public BigDecimal getRequestOrderDetailOutReceivedQty() {
		return requestOrderDetailOutReceivedQty;
	}

	public void setRequestOrderDetailOutReceivedQty(
			BigDecimal requestOrderDetailOutReceivedQty) {
		this.requestOrderDetailOutReceivedQty = requestOrderDetailOutReceivedQty;
	}

	public BigDecimal getRequestOrderDetailPurchaseReceivedQty() {
		return requestOrderDetailPurchaseReceivedQty;
	}

	public void setRequestOrderDetailPurchaseReceivedQty(
			BigDecimal requestOrderDetailPurchaseReceivedQty) {
		this.requestOrderDetailPurchaseReceivedQty = requestOrderDetailPurchaseReceivedQty;
	}

	public Boolean getRequestOrderDetailIgnore() {
		return requestOrderDetailIgnore;
	}

	public void setRequestOrderDetailIgnore(Boolean requestOrderDetailIgnore) {
		this.requestOrderDetailIgnore = requestOrderDetailIgnore;
	}

	public String getRequestOrderDetailItemSpec() {
		return requestOrderDetailItemSpec;
	}

	public void setRequestOrderDetailItemSpec(String requestOrderDetailItemSpec) {
		this.requestOrderDetailItemSpec = requestOrderDetailItemSpec;
	}

	public String getRequestOrderDetailItemCode() {
		return requestOrderDetailItemCode;
	}

	public void setRequestOrderDetailItemCode(String requestOrderDetailItemCode) {
		this.requestOrderDetailItemCode = requestOrderDetailItemCode;
	}

	public String getRequestOrderDetailUseUnit() {
		return requestOrderDetailUseUnit;
	}

	public void setRequestOrderDetailUseUnit(String requestOrderDetailUseUnit) {
		this.requestOrderDetailUseUnit = requestOrderDetailUseUnit;
	}

	public BigDecimal getRequestOrderDetailUseQty() {
		return requestOrderDetailUseQty;
	}

	public void setRequestOrderDetailUseQty(BigDecimal requestOrderDetailUseQty) {
		this.requestOrderDetailUseQty = requestOrderDetailUseQty;
	}

	public BigDecimal getRequestOrderDetailUsePrice() {
		return requestOrderDetailUsePrice;
	}

	public void setRequestOrderDetailUsePrice(
			BigDecimal requestOrderDetailUsePrice) {
		this.requestOrderDetailUsePrice = requestOrderDetailUsePrice;
	}

	public BigDecimal getRequestOrderDetailUseRate() {
		return requestOrderDetailUseRate;
	}

	public void setRequestOrderDetailUseRate(
			BigDecimal requestOrderDetailUseRate) {
		this.requestOrderDetailUseRate = requestOrderDetailUseRate;
	}

	public String getRequestOrderDetailAssistUnit() {
		return requestOrderDetailAssistUnit;
	}

	public void setRequestOrderDetailAssistUnit(
			String requestOrderDetailAssistUnit) {
		this.requestOrderDetailAssistUnit = requestOrderDetailAssistUnit;
	}

	public BigDecimal getRequestOrderDetailAssistQty() {
		return requestOrderDetailAssistQty;
	}

	public void setRequestOrderDetailAssistQty(
			BigDecimal requestOrderDetailAssistQty) {
		this.requestOrderDetailAssistQty = requestOrderDetailAssistQty;
	}

	public BigDecimal getRequestOrderDetailSalePrice() {
		return requestOrderDetailSalePrice;
	}

	public void setRequestOrderDetailSalePrice(
			BigDecimal requestOrderDetailSalePrice) {
		this.requestOrderDetailSalePrice = requestOrderDetailSalePrice;
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

	public BigDecimal getRequestOrderDetailOutMoney() {
		return requestOrderDetailOutMoney;
	}

	public void setRequestOrderDetailOutMoney(BigDecimal requestOrderDetailOutMoney) {
		this.requestOrderDetailOutMoney = requestOrderDetailOutMoney;
	}

	public BigDecimal getRequestOrderDetailInMoney() {
		return requestOrderDetailInMoney;
	}

	public void setRequestOrderDetailInMoney(BigDecimal requestOrderDetailInMoney) {
		this.requestOrderDetailInMoney = requestOrderDetailInMoney;
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
		RequestOrderDetail other = (RequestOrderDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigDecimal getRemainQty(){
		if (requestOrderDetailOutQty == null){
			setRequestOrderDetailOutQty(BigDecimal.ZERO);
		}
		if (requestOrderDetailPurchaseQty == null){
			setRequestOrderDetailPurchaseQty(BigDecimal.ZERO);
		}
		
		BigDecimal qty =  null;
		if(requestOrderDetailActualQty != null){
			qty =  requestOrderDetailActualQty;

		} else {
			
			qty =  requestOrderDetailQty.subtract(requestOrderDetailOutQty).subtract(requestOrderDetailPurchaseQty);
		}
		if (qty.compareTo(BigDecimal.ZERO) < 0){
			return BigDecimal.ZERO;
		}
		return qty;
	}

	public static RequestOrderDetail get(List<RequestOrderDetail> requestOrderDetails, RequestOrderDetailId id) {
		for(int i = 0;i < requestOrderDetails.size();i++){
			RequestOrderDetail detail = requestOrderDetails.get(i);
			if(detail.getId().equals(id)){
				return detail;
			}
		}
		return null;
	}
}