package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PurchaseOrderCollect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3093367264127536524L;
	private String purchaseOrderNo; // 单据号
	private Date operateDate;// 操作时间
	private Integer itemNum;
	private Integer itemMatrixNum;
	private String supplierName;
	private Integer supplierNum;
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String itemSpec;// 规格
	private String purchaseItemUnit;// 采购单位
	private BigDecimal purchaseItemPrice;// 收货单价
	private BigDecimal purchaseItemAmount;// 收货数量
	private BigDecimal purchaseItemMoney;// 收货金额
	private String purchasePresentUnit;// 赠品单位
	private BigDecimal purchasePresentAmount;// 赠品数量
	private BigDecimal purchasePresentMoney;// 赠品金额
	private BigDecimal purchaseItemReturnPrice;// 退货单价
	private BigDecimal purchaseItemReturnAmount;// 退货数量
	private BigDecimal purchaseItemReturnMoney;// 退货金额
	private String baseUnit;// 基本单位
	private BigDecimal baseAmount;// 基本数量
	private BigDecimal basePresentAmount;// 基本赠量
	private BigDecimal baseReturnAmount;// 基本退量
	private Date produceDate;// 生产日期
	private Date overDate;// 过期日期
	private BigDecimal averagePrice;// 平均单价
	private BigDecimal amountTotal;// 数量小计
	private BigDecimal moneyTotal;// 金额小计
	private Date receiveDate;//收货单收货时间（receiveOrderDate）
	private String receiveMemo;//收货单备注
	private BigDecimal receiveOrderDetailOtherQty;//筐数量
	private BigDecimal receiveOrderDetailOtherMoney;//筐押金
	private Integer branchNum;
	private BigDecimal receiveOrderDetailSupplierMoney;//运费金额
	private String itemCategoryCode;
	private String itemCategory;
	private String operator;
	private BigDecimal purchaseUseRate;// 换算率
	private BigDecimal receiveSaleMoney; //零售金额
	
	//临时属性
	private BigDecimal rate;

	public PurchaseOrderCollect(){
		purchaseItemAmount = BigDecimal.ZERO;
		purchaseItemMoney = BigDecimal.ZERO;
		purchasePresentAmount = BigDecimal.ZERO;
		purchasePresentMoney = BigDecimal.ZERO;
		purchaseItemReturnAmount = BigDecimal.ZERO;
		purchaseItemReturnMoney = BigDecimal.ZERO;
		baseAmount = BigDecimal.ZERO;
		basePresentAmount = BigDecimal.ZERO;
		amountTotal = BigDecimal.ZERO;
		moneyTotal = BigDecimal.ZERO;
		receiveOrderDetailOtherQty = BigDecimal.ZERO;
		receiveOrderDetailOtherMoney = BigDecimal.ZERO;
		receiveOrderDetailSupplierMoney = BigDecimal.ZERO;
		receiveSaleMoney = BigDecimal.ZERO;
	}
	
	public BigDecimal getPurchaseUseRate() {
		return purchaseUseRate;
	}
	
	public void setPurchaseUseRate(BigDecimal purchaseUseRate) {
		this.purchaseUseRate = purchaseUseRate;
	}
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public BigDecimal getReceiveOrderDetailSupplierMoney() {
		return receiveOrderDetailSupplierMoney;
	}

	public void setReceiveOrderDetailSupplierMoney(BigDecimal receiveOrderDetailSupplierMoney) {
		this.receiveOrderDetailSupplierMoney = receiveOrderDetailSupplierMoney;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public BigDecimal getReceiveOrderDetailOtherQty() {
		return receiveOrderDetailOtherQty;
	}

	public void setReceiveOrderDetailOtherQty(BigDecimal receiveOrderDetailOtherQty) {
		this.receiveOrderDetailOtherQty = receiveOrderDetailOtherQty;
	}

	public BigDecimal getReceiveOrderDetailOtherMoney() {
		return receiveOrderDetailOtherMoney;
	}

	public void setReceiveOrderDetailOtherMoney(BigDecimal receiveOrderDetailOtherMoney) {
		this.receiveOrderDetailOtherMoney = receiveOrderDetailOtherMoney;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
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

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getPurchaseItemUnit() {
		return purchaseItemUnit;
	}

	public void setPurchaseItemUnit(String purchaseItemUnit) {
		this.purchaseItemUnit = purchaseItemUnit;
	}

	public BigDecimal getPurchaseItemPrice() {
		return purchaseItemPrice;
	}

	public void setPurchaseItemPrice(BigDecimal purchaseItemPrice) {
		this.purchaseItemPrice = purchaseItemPrice;
	}

	public BigDecimal getPurchaseItemAmount() {
		return purchaseItemAmount;
	}

	public void setPurchaseItemAmount(BigDecimal purchaseItemAmount) {
		this.purchaseItemAmount = purchaseItemAmount;
	}

	public BigDecimal getPurchaseItemMoney() {
		return purchaseItemMoney;
	}

	public void setPurchaseItemMoney(BigDecimal purchaseItemMoney) {
		this.purchaseItemMoney = purchaseItemMoney;
	}

	public String getPurchasePresentUnit() {
		return purchasePresentUnit;
	}

	public void setPurchasePresentUnit(String purchasePresentUnit) {
		this.purchasePresentUnit = purchasePresentUnit;
	}

	public BigDecimal getPurchasePresentAmount() {
		return purchasePresentAmount;
	}

	public void setPurchasePresentAmount(BigDecimal purchasePresentAmount) {
		this.purchasePresentAmount = purchasePresentAmount;
	}

	public BigDecimal getPurchasePresentMoney() {
		return purchasePresentMoney;
	}

	public void setPurchasePresentMoney(BigDecimal purchasePresentMoney) {
		this.purchasePresentMoney = purchasePresentMoney;
	}

	public BigDecimal getPurchaseItemReturnPrice() {
		return purchaseItemReturnPrice;
	}

	public void setPurchaseItemReturnPrice(BigDecimal purchaseItemReturnPrice) {
		this.purchaseItemReturnPrice = purchaseItemReturnPrice;
	}

	public BigDecimal getPurchaseItemReturnAmount() {
		return purchaseItemReturnAmount;
	}

	public void setPurchaseItemReturnAmount(BigDecimal purchaseItemReturnAmount) {
		this.purchaseItemReturnAmount = purchaseItemReturnAmount;
	}

	public BigDecimal getPurchaseItemReturnMoney() {
		return purchaseItemReturnMoney;
	}

	public void setPurchaseItemReturnMoney(BigDecimal purchaseItemReturnMoney) {
		this.purchaseItemReturnMoney = purchaseItemReturnMoney;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public BigDecimal getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(BigDecimal baseAmount) {
		this.baseAmount = baseAmount;
	}

	public BigDecimal getBasePresentAmount() {
		return basePresentAmount;
	}

	public void setBasePresentAmount(BigDecimal basePresentAmount) {
		this.basePresentAmount = basePresentAmount;
	}

	public BigDecimal getBaseReturnAmount() {
		return baseReturnAmount;
	}

	public void setBaseReturnAmount(BigDecimal baseReturnAmount) {
		this.baseReturnAmount = baseReturnAmount;
	}

	public Date getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = amountTotal;
	}

	public BigDecimal getMoneyTotal() {
		return moneyTotal;
	}

	public void setMoneyTotal(BigDecimal moneyTotal) {
		this.moneyTotal = moneyTotal;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getReceiveMemo() {
		return receiveMemo;
	}

	public void setReceiveMemo(String receiveMemo) {
		this.receiveMemo = receiveMemo;
	}

	public BigDecimal getReceiveSaleMoney() {
		return receiveSaleMoney;
	}

	public void setReceiveSaleMoney(BigDecimal receiveSaleMoney) {
		this.receiveSaleMoney = receiveSaleMoney;
	}

	public static PurchaseOrderCollect get(List<PurchaseOrderCollect> purchaseOrderCollects, Integer branchNum, Integer itemNum, Integer itemMatrixNum){
		for(int i = 0;i < purchaseOrderCollects.size();i++){
			PurchaseOrderCollect collect = purchaseOrderCollects.get(i);
			if(collect.getBranchNum().equals(branchNum) 
					&& collect.getItemNum().equals(itemNum) 
					&& collect.getItemMatrixNum().equals(itemMatrixNum)){
				return collect;
			}
		}
		return null;
	}

}
