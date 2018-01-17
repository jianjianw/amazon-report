package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RequestOrderDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2680106608274531318L;
	private Integer itemNum;
	private String requestOrderDetailItemName;
	private BigDecimal requestOrderDetailQty;
	private BigDecimal requestOrderDetailSubtotal;
	private BigDecimal requestOrderDetailOutQty;
	private BigDecimal requestOrderDetailUseRate;
	private String requestOrderDetailUseUnit;
	private String requestOrderDetailItemSpec;
	private String requestOrderDetailItemCode;
	private BigDecimal requestOrderDetailOutMoney;
	private BigDecimal requestOrderDetailOutUseQty;
	private BigDecimal requestOrderDetailOutReceivedQty; //调入数量
	private BigDecimal requestOrderDetailInventoryQty; //中心库存数
	private BigDecimal requestOrderDetailUseQty;

	public BigDecimal getRequestOrderDetailOutReceivedQty() {
		return requestOrderDetailOutReceivedQty;
	}

	public void setRequestOrderDetailOutReceivedQty(BigDecimal requestOrderDetailOutReceivedQty) {
		this.requestOrderDetailOutReceivedQty = requestOrderDetailOutReceivedQty;
	}

	public BigDecimal getRequestOrderDetailInventoryQty() {
		return requestOrderDetailInventoryQty;
	}

	public void setRequestOrderDetailInventoryQty(BigDecimal requestOrderDetailInventoryQty) {
		this.requestOrderDetailInventoryQty = requestOrderDetailInventoryQty;
	}

	public BigDecimal getRequestOrderDetailOutUseQty() {
		return requestOrderDetailOutUseQty;
	}

	public void setRequestOrderDetailOutUseQty(BigDecimal requestOrderDetailOutUseQty) {
		this.requestOrderDetailOutUseQty = requestOrderDetailOutUseQty;
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

	public BigDecimal getRequestOrderDetailSubtotal() {
		return requestOrderDetailSubtotal;
	}

	public void setRequestOrderDetailSubtotal(BigDecimal requestOrderDetailSubtotal) {
		this.requestOrderDetailSubtotal = requestOrderDetailSubtotal;
	}

	public BigDecimal getRequestOrderDetailOutQty() {
		return requestOrderDetailOutQty;
	}

	public void setRequestOrderDetailOutQty(BigDecimal requestOrderDetailOutQty) {
		this.requestOrderDetailOutQty = requestOrderDetailOutQty;
	}

	public BigDecimal getRequestOrderDetailUseRate() {
		return requestOrderDetailUseRate;
	}

	public void setRequestOrderDetailUseRate(BigDecimal requestOrderDetailUseRate) {
		this.requestOrderDetailUseRate = requestOrderDetailUseRate;
	}

	public String getRequestOrderDetailUseUnit() {
		return requestOrderDetailUseUnit;
	}

	public void setRequestOrderDetailUseUnit(String requestOrderDetailUseUnit) {
		this.requestOrderDetailUseUnit = requestOrderDetailUseUnit;
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

	public BigDecimal getRequestOrderDetailOutMoney() {
		return requestOrderDetailOutMoney;
	}

	public void setRequestOrderDetailOutMoney(BigDecimal requestOrderDetailOutMoney) {
		this.requestOrderDetailOutMoney = requestOrderDetailOutMoney;
	}

	public BigDecimal getRequestOrderDetailUseQty() {
		return requestOrderDetailUseQty;
	}

	public void setRequestOrderDetailUseQty(BigDecimal requestOrderDetailUseQty) {
		this.requestOrderDetailUseQty = requestOrderDetailUseQty;
	}

	public static RequestOrderDetailDTO get(List<RequestOrderDetailDTO> dtos, Integer itemNum){
		for(int i = 0;i < dtos.size();i++){
			RequestOrderDetailDTO dto = dtos.get(i);
			
			if(dto.getItemNum().equals(itemNum)){
				return dto;
			}
		}
		return null;
	}

}
