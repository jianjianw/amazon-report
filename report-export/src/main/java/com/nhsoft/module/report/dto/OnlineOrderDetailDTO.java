package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OnlineOrderDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -485854018051221799L;
	private Integer branchNum; // 分配门店
	private String onlineOrderDeliver; // 物流方式
	private String orderState; // 单据状态
	private String onlineOrderFid; // 订单号
	private Date paymentDate; // 付款时间
	private String memo; // 备注
	private String detailState; // 明细状态
	private String weixinItemName; // 微商城商品名称
	private String weixinItemSpec; // 微商城商品名称规格
	private String weixinItemUnit; // 微商城商品单位 (销售单位)
	private String itemCode; // 关联商品代码
	private String itemName; // 关联商品名称
	private String itemSpec; // 基本规格
	private String itemUnit; // 基本单位
	private String itemBarCode;//关联商品条码
	private BigDecimal usePrice; // 销售单价
	private BigDecimal price; // 基本单价
	private BigDecimal useAmount;// 销售数量
	private BigDecimal amount; // 基本数量
	private BigDecimal money; // 销售额
	
	private Integer itemNum;
	private Integer weixinItemNum;

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getWeixinItemNum() {
		return weixinItemNum;
	}

	public void setWeixinItemNum(Integer weixinItemNum) {
		this.weixinItemNum = weixinItemNum;
	}

	public String getItemBarCode() {
		return itemBarCode;
	}

	public void setItemBarCode(String itemBarCode) {
		this.itemBarCode = itemBarCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getOnlineOrderDeliver() {
		return onlineOrderDeliver;
	}

	public void setOnlineOrderDeliver(String onlineOrderDeliver) {
		this.onlineOrderDeliver = onlineOrderDeliver;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getDetailState() {
		return detailState;
	}

	public void setDetailState(String detailState) {
		this.detailState = detailState;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOnlineOrderFid() {
		return onlineOrderFid;
	}

	public void setOnlineOrderFid(String onlineOrderFid) {
		this.onlineOrderFid = onlineOrderFid;
	}

	public String getWeixinItemName() {
		return weixinItemName;
	}

	public void setWeixinItemName(String weixinItemName) {
		this.weixinItemName = weixinItemName;
	}

	public String getWeixinItemSpec() {
		return weixinItemSpec;
	}

	public void setWeixinItemSpec(String weixinItemSpec) {
		this.weixinItemSpec = weixinItemSpec;
	}

	public String getWeixinItemUnit() {
		return weixinItemUnit;
	}

	public void setWeixinItemUnit(String weixinItemUnit) {
		this.weixinItemUnit = weixinItemUnit;
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

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public BigDecimal getUsePrice() {
		return usePrice;
	}

	public void setUsePrice(BigDecimal usePrice) {
		this.usePrice = usePrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(BigDecimal useAmount) {
		this.useAmount = useAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

}
