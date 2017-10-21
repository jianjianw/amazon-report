package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class BookSummaryReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8447203269817388575L;
	private String systemBookCode;
	private String systemBookName;

	private Integer allBranchs; //总分店数
	private Integer activeBranchs; //活跃分店数
	
	private Integer purchaseOrderCount;// 采购单据数量 采购订单 + 采购收货 + 采购退货
	private Integer receiveOrderCount;
	private Integer returnOrderCount;

	private Integer wholesaleOrderCount;// 批发单据数量 批发订单 + 批发收货 + 批发退货
	private Integer wholesaleBookCount;
	private Integer wholesaleReturnCount;

	private Integer checkOrderCount;// 库存单据数量 库存盘点单 + 调整单 + 转仓单 + 组合拆分单
	private Integer adjustmentOrderCount;
	private Integer allocationOrderCount;
	private Integer assembleOrderCount;

	private Integer requestOrderCount; // 连锁单据数量 要货单＋调出单＋调入单
	private Integer transferOutCount;
	private Integer transferInCount;

	private Integer settlementOrderCount;// 供应商结算单数量　结算单＋预付单
	private Integer preSettlementOrderCount;

	private Integer innerSettlementOrderCount;// 加盟店结算单数量 结算单＋预付单
	private Integer innerPreSettlementOrderCount;

	private Integer clientSettlementOrderCount;// 批发客户结算单数量 结算单＋预付单
	private Integer clientPreSettlementOrderCount;
	
	private Integer promotionPresentCount;//赠送促销
	private Integer promotionCount;  //促销特价
	private Integer promotionMoneyCount;//超额奖励
	private Integer promotionQuantityCount;//超量特价
	private Integer policyDiscountCount; //超额折扣
	
	private Integer cardSettlementOrderCount;// 会员卡结算单数量　
	private Integer messageCount; // 留言数量
	private Integer newCustomerCount;// 新会员数量
	private Integer rewardCount;// 抽奖活动数量
	private Integer mobileLoginCount; // 手机查询登陆次数
	private Integer webLoginCount;// 网页后台登陆次数
	private Integer marketActionCount;// 新建营销活动数量
	private Integer smsSendCount; // 发送短信数量
	private Integer posMachineLoginCount;// 移动POS登陆次数
	private Integer wholesaleLoginCount;// 网页批发在线订货登录次数
	private Integer shopLoginCount; // 网页门店要货登录次数
	private Integer supplierLoginCount;// 供应商入口登陆次数
	private Integer onlineOrderCount;//网店订单数
	private Integer posOrderCount = 0;//单据数

	public Integer getPolicyDiscountCount() {
		return policyDiscountCount;
	}

	public void setPolicyDiscountCount(Integer policyDiscountCount) {
		this.policyDiscountCount = policyDiscountCount;
	}

	public Integer getAllBranchs() {
		return allBranchs;
	}

	public void setAllBranchs(Integer allBranchs) {
		this.allBranchs = allBranchs;
	}

	public Integer getActiveBranchs() {
		return activeBranchs;
	}

	public void setActiveBranchs(Integer activeBranchs) {
		this.activeBranchs = activeBranchs;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getSystemBookName() {
		return systemBookName;
	}

	public void setSystemBookName(String systemBookName) {
		this.systemBookName = systemBookName;
	}

	public Integer getPurchaseOrderCount() {
		return purchaseOrderCount;
	}

	public void setPurchaseOrderCount(Integer purchaseOrderCount) {
		this.purchaseOrderCount = purchaseOrderCount;
	}

	public Integer getWholesaleOrderCount() {
		return wholesaleOrderCount;
	}

	public void setWholesaleOrderCount(Integer wholesaleOrderCount) {
		this.wholesaleOrderCount = wholesaleOrderCount;
	}

	public Integer getInnerSettlementOrderCount() {
		return innerSettlementOrderCount;
	}

	public void setInnerSettlementOrderCount(Integer innerSettlementOrderCount) {
		this.innerSettlementOrderCount = innerSettlementOrderCount;
	}

	public Integer getCardSettlementOrderCount() {
		return cardSettlementOrderCount;
	}

	public void setCardSettlementOrderCount(Integer cardSettlementOrderCount) {
		this.cardSettlementOrderCount = cardSettlementOrderCount;
	}

	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public Integer getNewCustomerCount() {
		return newCustomerCount;
	}

	public void setNewCustomerCount(Integer newCustomerCount) {
		this.newCustomerCount = newCustomerCount;
	}

	public Integer getPromotionPresentCount() {
		return promotionPresentCount;
	}

	public void setPromotionPresentCount(Integer promotionPresentCount) {
		this.promotionPresentCount = promotionPresentCount;
	}

	public Integer getPromotionCount() {
		return promotionCount;
	}

	public void setPromotionCount(Integer promotionCount) {
		this.promotionCount = promotionCount;
	}

	public Integer getPromotionMoneyCount() {
		return promotionMoneyCount;
	}

	public void setPromotionMoneyCount(Integer promotionMoneyCount) {
		this.promotionMoneyCount = promotionMoneyCount;
	}

	public Integer getPromotionQuantityCount() {
		return promotionQuantityCount;
	}

	public void setPromotionQuantityCount(Integer promotionQuantityCount) {
		this.promotionQuantityCount = promotionQuantityCount;
	}

	public Integer getRewardCount() {
		return rewardCount;
	}

	public void setRewardCount(Integer rewardCount) {
		this.rewardCount = rewardCount;
	}

	public Integer getMobileLoginCount() {
		return mobileLoginCount;
	}

	public void setMobileLoginCount(Integer mobileLoginCount) {
		this.mobileLoginCount = mobileLoginCount;
	}

	public Integer getWebLoginCount() {
		return webLoginCount;
	}

	public void setWebLoginCount(Integer webLoginCount) {
		this.webLoginCount = webLoginCount;
	}

	public Integer getMarketActionCount() {
		return marketActionCount;
	}

	public void setMarketActionCount(Integer marketActionCount) {
		this.marketActionCount = marketActionCount;
	}

	public Integer getSmsSendCount() {
		return smsSendCount;
	}

	public void setSmsSendCount(Integer smsSendCount) {
		this.smsSendCount = smsSendCount;
	}

	public Integer getPosMachineLoginCount() {
		return posMachineLoginCount;
	}

	public void setPosMachineLoginCount(Integer posMachineLoginCount) {
		this.posMachineLoginCount = posMachineLoginCount;
	}

	public Integer getReceiveOrderCount() {
		return receiveOrderCount;
	}

	public void setReceiveOrderCount(Integer receiveOrderCount) {
		this.receiveOrderCount = receiveOrderCount;
	}

	public Integer getReturnOrderCount() {
		return returnOrderCount;
	}

	public void setReturnOrderCount(Integer returnOrderCount) {
		this.returnOrderCount = returnOrderCount;
	}

	public Integer getWholesaleBookCount() {
		return wholesaleBookCount;
	}

	public void setWholesaleBookCount(Integer wholesaleBookCount) {
		this.wholesaleBookCount = wholesaleBookCount;
	}

	public Integer getWholesaleReturnCount() {
		return wholesaleReturnCount;
	}

	public void setWholesaleReturnCount(Integer wholesaleReturnCount) {
		this.wholesaleReturnCount = wholesaleReturnCount;
	}

	public Integer getCheckOrderCount() {
		return checkOrderCount;
	}

	public void setCheckOrderCount(Integer checkOrderCount) {
		this.checkOrderCount = checkOrderCount;
	}

	public Integer getAdjustmentOrderCount() {
		return adjustmentOrderCount;
	}

	public void setAdjustmentOrderCount(Integer adjustmentOrderCount) {
		this.adjustmentOrderCount = adjustmentOrderCount;
	}

	public Integer getAllocationOrderCount() {
		return allocationOrderCount;
	}

	public void setAllocationOrderCount(Integer allocationOrderCount) {
		this.allocationOrderCount = allocationOrderCount;
	}

	public Integer getAssembleOrderCount() {
		return assembleOrderCount;
	}

	public void setAssembleOrderCount(Integer assembleOrderCount) {
		this.assembleOrderCount = assembleOrderCount;
	}

	public Integer getRequestOrderCount() {
		return requestOrderCount;
	}

	public void setRequestOrderCount(Integer requestOrderCount) {
		this.requestOrderCount = requestOrderCount;
	}

	public Integer getTransferOutCount() {
		return transferOutCount;
	}

	public void setTransferOutCount(Integer transferOutCount) {
		this.transferOutCount = transferOutCount;
	}

	public Integer getTransferInCount() {
		return transferInCount;
	}

	public void setTransferInCount(Integer transferInCount) {
		this.transferInCount = transferInCount;
	}

	public Integer getSettlementOrderCount() {
		return settlementOrderCount;
	}

	public void setSettlementOrderCount(Integer settlementOrderCount) {
		this.settlementOrderCount = settlementOrderCount;
	}

	public Integer getPreSettlementOrderCount() {
		return preSettlementOrderCount;
	}

	public void setPreSettlementOrderCount(Integer preSettlementOrderCount) {
		this.preSettlementOrderCount = preSettlementOrderCount;
	}

	public Integer getInnerPreSettlementOrderCount() {
		return innerPreSettlementOrderCount;
	}

	public void setInnerPreSettlementOrderCount(Integer innerPreSettlementOrderCount) {
		this.innerPreSettlementOrderCount = innerPreSettlementOrderCount;
	}

	public Integer getClientSettlementOrderCount() {
		return clientSettlementOrderCount;
	}

	public void setClientSettlementOrderCount(Integer clientSettlementOrderCount) {
		this.clientSettlementOrderCount = clientSettlementOrderCount;
	}

	public Integer getClientPreSettlementOrderCount() {
		return clientPreSettlementOrderCount;
	}

	public void setClientPreSettlementOrderCount(Integer clientPreSettlementOrderCount) {
		this.clientPreSettlementOrderCount = clientPreSettlementOrderCount;
	}

	public Integer getWholesaleLoginCount() {
		return wholesaleLoginCount;
	}

	public void setWholesaleLoginCount(Integer wholesaleLoginCount) {
		this.wholesaleLoginCount = wholesaleLoginCount;
	}

	public Integer getShopLoginCount() {
		return shopLoginCount;
	}

	public void setShopLoginCount(Integer shopLoginCount) {
		this.shopLoginCount = shopLoginCount;
	}

	public Integer getSupplierLoginCount() {
		return supplierLoginCount;
	}

	public void setSupplierLoginCount(Integer supplierLoginCount) {
		this.supplierLoginCount = supplierLoginCount;
	}

	public Integer getOnlineOrderCount() {
		return onlineOrderCount;
	}

	public void setOnlineOrderCount(Integer onlineOrderCount) {
		this.onlineOrderCount = onlineOrderCount;
	}

	public Integer getPosOrderCount() {
		return posOrderCount;
	}

	public void setPosOrderCount(Integer posOrderCount) {
		this.posOrderCount = posOrderCount;
	}

	public boolean checkZero(){
		Integer value = activeBranchs + posOrderCount;
		if(purchaseOrderCount != null){
			value = value + purchaseOrderCount + receiveOrderCount + returnOrderCount;
		}
		if(wholesaleBookCount != null){
			value = value + wholesaleBookCount + wholesaleOrderCount + wholesaleReturnCount;
		}
		if(requestOrderCount != null){
			value = value + requestOrderCount + transferInCount + transferOutCount;
		}
		if(checkOrderCount != null){
			value = value + checkOrderCount + adjustmentOrderCount + allocationOrderCount + assembleOrderCount;
		}
		if(promotionCount != null){
			value = value + promotionCount + promotionMoneyCount + promotionPresentCount + promotionQuantityCount;
		}
		if(cardSettlementOrderCount != null){
			value = value + cardSettlementOrderCount + settlementOrderCount + preSettlementOrderCount
					+ innerPreSettlementOrderCount + innerSettlementOrderCount + clientPreSettlementOrderCount + clientSettlementOrderCount;
		}	
		if(smsSendCount != null){
			value = value + smsSendCount;
		}
		return value + newCustomerCount
				+ rewardCount + mobileLoginCount + webLoginCount + marketActionCount + messageCount + posMachineLoginCount + wholesaleLoginCount
				+ shopLoginCount + supplierLoginCount + onlineOrderCount == 0;
	}

}
