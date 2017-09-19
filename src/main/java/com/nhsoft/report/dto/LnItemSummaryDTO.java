package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnItemSummaryDTO implements Serializable {

	/**
	 * 
	 */

	public static class LnItemDetailDTO implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1348569864717341677L;
		private String billNo; // 单据号
		private String billType; // 单据类型
		private BigDecimal itemQty; // 数量
		private BigDecimal itemMoney; // 金额
		private String billToObject; // 出入对象：收货退货为供应商，调出调入为门店，批发为客户，库存操作为仓库
		private String billOperater; // 操作人
		private Date date;
		private String billMemo;


		public String getBillMemo() {
			return billMemo;
		}

		public void setBillMemo(String billMemo) {
			this.billMemo = billMemo;
		}

		public String getBillNo() {
			return billNo;
		}

		public void setBillNo(String billNo) {
			this.billNo = billNo;
		}

		public String getBillType() {
			return billType;
		}

		public void setBillType(String billType) {
			this.billType = billType;
		}

		public BigDecimal getItemQty() {
			return itemQty;
		}

		public void setItemQty(BigDecimal itemQty) {
			this.itemQty = itemQty;
		}

		public BigDecimal getItemMoney() {
			return itemMoney;
		}

		public void setItemMoney(BigDecimal itemMoney) {
			this.itemMoney = itemMoney;
		}

		public String getBillToObject() {
			return billToObject;
		}

		public void setBillToObject(String billToObject) {
			this.billToObject = billToObject;
		}

		public String getBillOperater() {
			return billOperater;
		}

		public void setBillOperater(String billOperater) {
			this.billOperater = billOperater;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

	}

	private static final long serialVersionUID = -8417365214366104505L;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private String itemLotNumber;// 批次号
	private Date itemPurchaseDate;// 采购日期
	private String itemBillOperater;// 采购员
	private BigDecimal itemPurchaseQty; // 采购数量
	private BigDecimal itemPurchaseMoney; // 采购金额
	private BigDecimal itemOutQty; // 出库数量=调出数量-调入数量+批发销售数量-批发退货数量
	private BigDecimal itemOutMoney; // 出库金额=调出金额-调入金额+批发销售金额-批发退货金额
	private BigDecimal itemAdjustQty; // 调整数量=盘点+调整；
	private BigDecimal itemAdjustMoney; // 调整金额
	private BigDecimal itemProfit; // 毛利
	private BigDecimal itemInventory; // 当前库存数量
	private BigDecimal itemInventoryMoney; // 当前库存金额
	private String itemUnit;
	private List<LnItemDetailDTO> lnItemDetailDTOs = new ArrayList<LnItemDetailDTO>();
	
	
	public LnItemSummaryDTO(){
		setItemPurchaseQty(BigDecimal.ZERO);
		setItemPurchaseMoney(BigDecimal.ZERO);
		setItemOutQty(BigDecimal.ZERO);
		setItemOutMoney(BigDecimal.ZERO);
		setItemAdjustMoney(BigDecimal.ZERO);
		setItemAdjustQty(BigDecimal.ZERO);
		setItemProfit(BigDecimal.ZERO);
		setItemInventory(BigDecimal.ZERO);
		setItemInventoryMoney(BigDecimal.ZERO);
	}

	public BigDecimal getItemInventoryMoney() {
		return itemInventoryMoney;
	}

	public void setItemInventoryMoney(BigDecimal itemInventoryMoney) {
		this.itemInventoryMoney = itemInventoryMoney;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
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

	public String getItemLotNumber() {
		return itemLotNumber;
	}

	public void setItemLotNumber(String itemLotNumber) {
		this.itemLotNumber = itemLotNumber;
	}

	public Date getItemPurchaseDate() {
		return itemPurchaseDate;
	}

	public void setItemPurchaseDate(Date itemPurchaseDate) {
		this.itemPurchaseDate = itemPurchaseDate;
	}

	public String getItemBillOperater() {
		return itemBillOperater;
	}

	public void setItemBillOperater(String itemBillOperater) {
		this.itemBillOperater = itemBillOperater;
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

	public BigDecimal getItemOutQty() {
		return itemOutQty;
	}

	public void setItemOutQty(BigDecimal itemOutQty) {
		this.itemOutQty = itemOutQty;
	}

	public BigDecimal getItemOutMoney() {
		return itemOutMoney;
	}

	public void setItemOutMoney(BigDecimal itemOutMoney) {
		this.itemOutMoney = itemOutMoney;
	}

	public BigDecimal getItemAdjustQty() {
		return itemAdjustQty;
	}

	public void setItemAdjustQty(BigDecimal itemAdjustQty) {
		this.itemAdjustQty = itemAdjustQty;
	}

	public BigDecimal getItemAdjustMoney() {
		return itemAdjustMoney;
	}

	public void setItemAdjustMoney(BigDecimal itemAdjustMoney) {
		this.itemAdjustMoney = itemAdjustMoney;
	}

	public BigDecimal getItemProfit() {
		return itemProfit;
	}

	public void setItemProfit(BigDecimal itemProfit) {
		this.itemProfit = itemProfit;
	}

	public BigDecimal getItemInventory() {
		return itemInventory;
	}

	public void setItemInventory(BigDecimal itemInventory) {
		this.itemInventory = itemInventory;
	}

	public List<LnItemDetailDTO> getLnItemDetailDTOs() {
		return lnItemDetailDTOs;
	}

	public void setLnItemDetailDTOs(List<LnItemDetailDTO> lnItemDetailDTOs) {
		this.lnItemDetailDTOs = lnItemDetailDTOs;
	}
	
	public static LnItemSummaryDTO get(List<LnItemSummaryDTO> lnItemSummaryDTOs, String lotNumber){
		for(int i = 0;i < lnItemSummaryDTOs.size();i++){
			LnItemSummaryDTO dto = lnItemSummaryDTOs.get(i);
			if(lotNumber.equals(dto.getItemLotNumber())){
				return dto;
			}
		}
		return null;
	}

}
