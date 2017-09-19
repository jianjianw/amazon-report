package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PurchaseAndTransferDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1858301347091526148L;
	private String itemUnit;
	private String itemLotnumber;
	private Date itemPurchaseDate;
	private BigDecimal itemPurchaseQty;
	private BigDecimal itemPurchaseMoney;
	private BigDecimal itemTransferQty;
	private BigDecimal itemTransferMoney;
	private BigDecimal itemTransferProfit; //总毛利
	private BigDecimal itemWholesaleQty;
	private BigDecimal itemWholesaleMoney;
	private BigDecimal itemOtherQty;
	private BigDecimal itemOtherMoney;
	private BigDecimal itemAllocationQty;
	private BigDecimal itemAllocationMoney;
	
	//临时属性
	private String purchaser;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private BigDecimal itemRate;
	
	public PurchaseAndTransferDetailDTO(){
		setItemPurchaseQty(BigDecimal.ZERO);
		setItemPurchaseMoney(BigDecimal.ZERO);
		setItemTransferMoney(BigDecimal.ZERO);
		setItemTransferProfit(BigDecimal.ZERO);
		setItemTransferQty(BigDecimal.ZERO);
		setItemWholesaleMoney(BigDecimal.ZERO);
		setItemWholesaleQty(BigDecimal.ZERO);
		setItemOtherMoney(BigDecimal.ZERO);
		setItemOtherQty(BigDecimal.ZERO);
		setItemAllocationMoney(BigDecimal.ZERO);
		setItemAllocationQty(BigDecimal.ZERO);
	}

	public BigDecimal getItemAllocationQty() {
		return itemAllocationQty;
	}

	public void setItemAllocationQty(BigDecimal itemAllocationQty) {
		this.itemAllocationQty = itemAllocationQty;
	}

	public BigDecimal getItemAllocationMoney() {
		return itemAllocationMoney;
	}

	public void setItemAllocationMoney(BigDecimal itemAllocationMoney) {
		this.itemAllocationMoney = itemAllocationMoney;
	}

	public BigDecimal getItemWholesaleQty() {
		return itemWholesaleQty;
	}

	public void setItemWholesaleQty(BigDecimal itemWholesaleQty) {
		this.itemWholesaleQty = itemWholesaleQty;
	}

	public BigDecimal getItemWholesaleMoney() {
		return itemWholesaleMoney;
	}

	public void setItemWholesaleMoney(BigDecimal itemWholesaleMoney) {
		this.itemWholesaleMoney = itemWholesaleMoney;
	}

	public BigDecimal getItemOtherQty() {
		return itemOtherQty;
	}

	public void setItemOtherQty(BigDecimal itemOtherQty) {
		this.itemOtherQty = itemOtherQty;
	}

	public BigDecimal getItemOtherMoney() {
		return itemOtherMoney;
	}

	public void setItemOtherMoney(BigDecimal itemOtherMoney) {
		this.itemOtherMoney = itemOtherMoney;
	}

	public BigDecimal getItemRate() {
		return itemRate;
	}

	public void setItemRate(BigDecimal itemRate) {
		this.itemRate = itemRate;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
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

	public String getItemLotnumber() {
		return itemLotnumber;
	}

	public void setItemLotnumber(String itemLotnumber) {
		this.itemLotnumber = itemLotnumber;
	}

	public Date getItemPurchaseDate() {
		return itemPurchaseDate;
	}

	public void setItemPurchaseDate(Date itemPurchaseDate) {
		this.itemPurchaseDate = itemPurchaseDate;
	}

	public BigDecimal getItemPurchaseQty() {
		return itemPurchaseQty;
	}

	public void setItemPurchaseQty(BigDecimal itemPurchaseQty) {
		this.itemPurchaseQty = itemPurchaseQty;
	}

	public BigDecimal getItemPurchaseMoney() {
		return itemPurchaseMoney;
	}

	public void setItemPurchaseMoney(BigDecimal itemPurchaseMoney) {
		this.itemPurchaseMoney = itemPurchaseMoney;
	}

	public BigDecimal getItemTransferQty() {
		return itemTransferQty;
	}

	public void setItemTransferQty(BigDecimal itemTransferQty) {
		this.itemTransferQty = itemTransferQty;
	}

	public BigDecimal getItemTransferMoney() {
		return itemTransferMoney;
	}

	public void setItemTransferMoney(BigDecimal itemTransferMoney) {
		this.itemTransferMoney = itemTransferMoney;
	}

	public BigDecimal getItemTransferProfit() {
		return itemTransferProfit;
	}

	public void setItemTransferProfit(BigDecimal itemTransferProfit) {
		this.itemTransferProfit = itemTransferProfit;
	}
	
	public static PurchaseAndTransferDetailDTO get(List<PurchaseAndTransferDetailDTO> purchaseAndTransferDetailDTOs, 
			Integer itemNum, String itemLotnumber){
		for(int i = 0;i < purchaseAndTransferDetailDTOs.size();i++){
			PurchaseAndTransferDetailDTO dto = purchaseAndTransferDetailDTOs.get(i);
			if(dto.getItemNum().equals(itemNum) 
					&& dto.getItemLotnumber().equals(itemLotnumber)){
				return dto;
			}
		}
		return null;
	}
	
	public static PurchaseAndTransferDetailDTO get(List<PurchaseAndTransferDetailDTO> purchaseAndTransferDetailDTOs, 
			Integer itemNum, String itemLotnumber, String purchase){
		for(int i = 0;i < purchaseAndTransferDetailDTOs.size();i++){
			PurchaseAndTransferDetailDTO dto = purchaseAndTransferDetailDTOs.get(i);
			if(dto.getItemNum().equals(itemNum) 
					&& dto.getItemLotnumber().equals(itemLotnumber)
					&& dto.getPurchaser().equals(purchase)){
				return dto;
			}
		}
		return null;
	}

}
