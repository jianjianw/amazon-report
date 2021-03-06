package com.nhsoft.module.report.model;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * AssembleSplitDetail generated by hbm2java
 */
@Entity
public class AssembleSplitDetail implements java.io.Serializable {

	private static final long serialVersionUID = 7459798124242977385L;
	@EmbeddedId
	private AssembleSplitDetailId id;
	private Integer itemNum;
	private String assembleSplitDetailItemCode;
	private String assembleSplitDetailItemName;
	private String assembleSplitDetailItemUnit;
	private BigDecimal assembleSplitDetailItemAmount;
	private BigDecimal assembleSplitDetailItemPrice;
	private BigDecimal assembleSplitDetailItemSubtotal;
	private String assembleSplitDetailMemo;
	private Integer assembleSplitDetailItemMatrixNum;
	private Date assembleSplitDetailProducingDate;
	private String assembleSplitDetailLotNumber;
	private BigDecimal assembleSplitDetailItemAssistAmount;
	private String assembleSplitDetailItemAssistUnit;
	private String assembleSplitDetailUseUnit;
	private BigDecimal assembleSplitDetailUseQty;
	private BigDecimal assembleSplitDetailUsePrice;
	private BigDecimal assembleSplitDetailUseRate;
	private String assembleSplitDetailItemSpec;
	private BigDecimal assembleSplitDetailInventoryQty;
	private BigDecimal assembleSplitDetailInventoryAssistQty;
	private Integer supplierNum;
	
	
	public AssembleSplitDetail() {
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public AssembleSplitDetailId getId() {
		return this.id;
	}

	public void setId(AssembleSplitDetailId id) {
		this.id = id;
	}

	public String getAssembleSplitDetailItemCode() {
		return this.assembleSplitDetailItemCode;
	}

	public void setAssembleSplitDetailItemCode(
			String assembleSplitDetailItemCode) {
		this.assembleSplitDetailItemCode = assembleSplitDetailItemCode;
	}

	public String getAssembleSplitDetailItemName() {
		return this.assembleSplitDetailItemName;
	}

	public void setAssembleSplitDetailItemName(
			String assembleSplitDetailItemName) {
		this.assembleSplitDetailItemName = assembleSplitDetailItemName;
	}

	public String getAssembleSplitDetailItemUnit() {
		return this.assembleSplitDetailItemUnit;
	}

	public void setAssembleSplitDetailItemUnit(
			String assembleSplitDetailItemUnit) {
		this.assembleSplitDetailItemUnit = assembleSplitDetailItemUnit;
	}

	public BigDecimal getAssembleSplitDetailItemAmount() {
		return this.assembleSplitDetailItemAmount;
	}

	public void setAssembleSplitDetailItemAmount(
			BigDecimal assembleSplitDetailItemAmount) {
		this.assembleSplitDetailItemAmount = assembleSplitDetailItemAmount;
	}

	public BigDecimal getAssembleSplitDetailItemPrice() {
		return this.assembleSplitDetailItemPrice;
	}

	public void setAssembleSplitDetailItemPrice(
			BigDecimal assembleSplitDetailItemPrice) {
		this.assembleSplitDetailItemPrice = assembleSplitDetailItemPrice;
	}

	public BigDecimal getAssembleSplitDetailItemSubtotal() {
		return this.assembleSplitDetailItemSubtotal;
	}

	public void setAssembleSplitDetailItemSubtotal(
			BigDecimal assembleSplitDetailItemSubtotal) {
		this.assembleSplitDetailItemSubtotal = assembleSplitDetailItemSubtotal;
	}

	public String getAssembleSplitDetailMemo() {
		return this.assembleSplitDetailMemo;
	}

	public void setAssembleSplitDetailMemo(String assembleSplitDetailMemo) {
		this.assembleSplitDetailMemo = assembleSplitDetailMemo;
	}

	public Integer getAssembleSplitDetailItemMatrixNum() {
		return this.assembleSplitDetailItemMatrixNum;
	}

	public void setAssembleSplitDetailItemMatrixNum(
			Integer assembleSplitDetailItemMatrixNum) {
		this.assembleSplitDetailItemMatrixNum = assembleSplitDetailItemMatrixNum;
	}

	public Date getAssembleSplitDetailProducingDate() {
		return this.assembleSplitDetailProducingDate;
	}

	public void setAssembleSplitDetailProducingDate(
			Date assembleSplitDetailProducingDate) {
		this.assembleSplitDetailProducingDate = assembleSplitDetailProducingDate;
	}

	public String getAssembleSplitDetailLotNumber() {
		return this.assembleSplitDetailLotNumber;
	}

	public void setAssembleSplitDetailLotNumber(
			String assembleSplitDetailLotNumber) {
		this.assembleSplitDetailLotNumber = assembleSplitDetailLotNumber;
	}

	public BigDecimal getAssembleSplitDetailItemAssistAmount() {
		return this.assembleSplitDetailItemAssistAmount;
	}

	public void setAssembleSplitDetailItemAssistAmount(
			BigDecimal assembleSplitDetailItemAssistAmount) {
		this.assembleSplitDetailItemAssistAmount = assembleSplitDetailItemAssistAmount;
	}

	public String getAssembleSplitDetailItemAssistUnit() {
		return this.assembleSplitDetailItemAssistUnit;
	}

	public void setAssembleSplitDetailItemAssistUnit(
			String assembleSplitDetailItemAssistUnit) {
		this.assembleSplitDetailItemAssistUnit = assembleSplitDetailItemAssistUnit;
	}

	public String getAssembleSplitDetailUseUnit() {
		return this.assembleSplitDetailUseUnit;
	}

	public void setAssembleSplitDetailUseUnit(String assembleSplitDetailUseUnit) {
		this.assembleSplitDetailUseUnit = assembleSplitDetailUseUnit;
	}

	public BigDecimal getAssembleSplitDetailUseQty() {
		return this.assembleSplitDetailUseQty;
	}

	public void setAssembleSplitDetailUseQty(
			BigDecimal assembleSplitDetailUseQty) {
		this.assembleSplitDetailUseQty = assembleSplitDetailUseQty;
	}

	public BigDecimal getAssembleSplitDetailUsePrice() {
		return this.assembleSplitDetailUsePrice;
	}

	public void setAssembleSplitDetailUsePrice(
			BigDecimal assembleSplitDetailUsePrice) {
		this.assembleSplitDetailUsePrice = assembleSplitDetailUsePrice;
	}

	public BigDecimal getAssembleSplitDetailUseRate() {
		return this.assembleSplitDetailUseRate;
	}

	public void setAssembleSplitDetailUseRate(
			BigDecimal assembleSplitDetailUseRate) {
		this.assembleSplitDetailUseRate = assembleSplitDetailUseRate;
	}

	public String getAssembleSplitDetailItemSpec() {
		return this.assembleSplitDetailItemSpec;
	}

	public void setAssembleSplitDetailItemSpec(
			String assembleSplitDetailItemSpec) {
		this.assembleSplitDetailItemSpec = assembleSplitDetailItemSpec;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getAssembleSplitDetailInventoryQty() {
		return assembleSplitDetailInventoryQty;
	}

	public void setAssembleSplitDetailInventoryQty(
			BigDecimal assembleSplitDetailInventoryQty) {
		this.assembleSplitDetailInventoryQty = assembleSplitDetailInventoryQty;
	}

	public BigDecimal getAssembleSplitDetailInventoryAssistQty() {
		return assembleSplitDetailInventoryAssistQty;
	}

	public void setAssembleSplitDetailInventoryAssistQty(
			BigDecimal assembleSplitDetailInventoryAssistQty) {
		this.assembleSplitDetailInventoryAssistQty = assembleSplitDetailInventoryAssistQty;
	}

	public static AssembleSplitDetail read(List<AssembleSplitDetail> assembleSplitDetails, Integer itemNum) {
		for(int i = 0;i < assembleSplitDetails.size();i++){
			AssembleSplitDetail assembleSplitDetail = assembleSplitDetails.get(i);
			if(assembleSplitDetail.getItemNum().equals(itemNum)){
				return assembleSplitDetail;
			}
		}
		return null;
	}

}
