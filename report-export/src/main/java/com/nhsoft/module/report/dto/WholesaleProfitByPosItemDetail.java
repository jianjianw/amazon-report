package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WholesaleProfitByPosItemDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4612023214206350474L;
	private String posOrderNum; // 单据号
	private String posOrderType; // 单据类型
	private Date saleTime; // 销售时间
	private String orderSeller; // 销售员
	private String orderMaker; // 制单人
	private String orderAuditor; // 审核人
	private String clientFid; // 客户fid
	private String clientCode; // 客户编号
	private String clientName; // 客户名称
	private String posItemCategory; //商品类别
	private String posItemCode; // 商品代码
	private String posItemName; // 商品名称
	private String spec; // 规格
	private String unit; // 单位
	private BigDecimal wholesaleNum; // 批发数量
	private BigDecimal wholesaleUnitPrice; // 批发单价
	private BigDecimal wholesaleMoney; // 批发金额
	private BigDecimal wholesaleCost; // 批发成本
	private BigDecimal wholesaleProfit; // 批发毛利
	private BigDecimal wholesaleProfitRate; // 批发毛利率
	private BigDecimal saleCost; // 零售金额
	private BigDecimal saleProfit; // 零售毛利
	private BigDecimal saleProfitRate; // 零售毛利率
	private String remark; // 备注
	private Integer itemNum;
	private Integer itemMatrixNum;
	private String orderMemo; //附加备注
	private String innerNo; //手工单号
	private BigDecimal wholesaleUseNum; // 批发常用数量
	private BigDecimal wholesaleBaseNum;//基本数量
	private String baseUnit; // 基本单位
	private BigDecimal presentUseQty;
	private BigDecimal presentCostMoney;
	private BigDecimal presentMoney;
	private String presentUnit;
	private BigDecimal presentQty;
	private Integer itemValidPeriod;
	private Date productDate;
	
	public WholesaleProfitByPosItemDetail(){
		setWholesaleNum(BigDecimal.ZERO);
		setWholesaleCost(BigDecimal.ZERO);
		setWholesaleProfit(BigDecimal.ZERO);
		setSaleCost(BigDecimal.ZERO);
		setSaleProfit(BigDecimal.ZERO);
		setWholesaleMoney(BigDecimal.ZERO);
		setWholesaleUseNum(BigDecimal.ZERO);
		setWholesaleBaseNum(BigDecimal.ZERO);
		setPresentUseQty(BigDecimal.ZERO);
		setPresentCostMoney(BigDecimal.ZERO);
		setPresentMoney(BigDecimal.ZERO);
		setPresentQty(BigDecimal.ZERO);
	}


	public Date getProductDate() {
		return productDate;
	}


	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}


	public Integer getItemValidPeriod() {
		return itemValidPeriod;
	}


	public void setItemValidPeriod(Integer itemValidPeriod) {
		this.itemValidPeriod = itemValidPeriod;
	}


	public BigDecimal getPresentQty() {
		return presentQty;
	}


	public void setPresentQty(BigDecimal presentQty) {
		this.presentQty = presentQty;
	}


	public String getPresentUnit() {
		return presentUnit;
	}


	public void setPresentUnit(String presentUnit) {
		this.presentUnit = presentUnit;
	}


	public BigDecimal getPresentUseQty() {
		return presentUseQty;
	}

	public void setPresentUseQty(BigDecimal presentUseQty) {
		this.presentUseQty = presentUseQty;
	}

	public BigDecimal getPresentCostMoney() {
		return presentCostMoney;
	}

	public void setPresentCostMoney(BigDecimal presentCostMoney) {
		this.presentCostMoney = presentCostMoney;
	}

	public BigDecimal getPresentMoney() {
		return presentMoney;
	}

	public void setPresentMoney(BigDecimal presentMoney) {
		this.presentMoney = presentMoney;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public BigDecimal getWholesaleBaseNum() {
		return wholesaleBaseNum;
	}

	public void setWholesaleBaseNum(BigDecimal wholesaleBaseNum) {
		this.wholesaleBaseNum = wholesaleBaseNum;
	}

	public BigDecimal getWholesaleUseNum() {
		return wholesaleUseNum;
	}

	public void setWholesaleUseNum(BigDecimal wholesaleUseNum) {
		this.wholesaleUseNum = wholesaleUseNum;
	}

	public String getInnerNo() {
		return innerNo;
	}

	public void setInnerNo(String innerNo) {
		this.innerNo = innerNo;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	public String getOrderMemo() {
		return orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	public String getPosOrderNum() {
		return posOrderNum;
	}

	public void setPosOrderNum(String posOrderNum) {
		this.posOrderNum = posOrderNum;
	}

	public String getPosOrderType() {
		return posOrderType;
	}

	public void setPosOrderType(String posOrderType) {
		this.posOrderType = posOrderType;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public String getOrderSeller() {
		return orderSeller;
	}

	public void setOrderSeller(String orderSeller) {
		this.orderSeller = orderSeller;
	}

	public String getOrderMaker() {
		return orderMaker;
	}

	public void setOrderMaker(String orderMaker) {
		this.orderMaker = orderMaker;
	}

	public String getOrderAuditor() {
		return orderAuditor;
	}

	public void setOrderAuditor(String orderAuditor) {
		this.orderAuditor = orderAuditor;
	}

	public String getClientFid() {
		return clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPosItemCode() {
		return posItemCode;
	}

	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
	}

	public String getPosItemName() {
		return posItemName;
	}

	public void setPosItemName(String posItemName) {
		this.posItemName = posItemName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getWholesaleNum() {
		return wholesaleNum;
	}

	public void setWholesaleNum(BigDecimal wholesaleNum) {
		this.wholesaleNum = wholesaleNum;
	}

	public BigDecimal getWholesaleUnitPrice() {
		return wholesaleUnitPrice;
	}

	public void setWholesaleUnitPrice(BigDecimal wholesaleUnitPrice) {
		this.wholesaleUnitPrice = wholesaleUnitPrice;
	}

	public BigDecimal getWholesaleMoney() {
		return wholesaleMoney;
	}

	public void setWholesaleMoney(BigDecimal wholesaleMoney) {
		this.wholesaleMoney = wholesaleMoney;
	}

	public BigDecimal getWholesaleCost() {
		return wholesaleCost;
	}

	public void setWholesaleCost(BigDecimal wholesaleCost) {
		this.wholesaleCost = wholesaleCost;
	}

	public BigDecimal getWholesaleProfit() {
		return wholesaleProfit;
	}

	public void setWholesaleProfit(BigDecimal wholesaleProfit) {
		this.wholesaleProfit = wholesaleProfit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public BigDecimal getSaleProfit() {
		return saleProfit;
	}

	public void setSaleProfit(BigDecimal saleProfit) {
		this.saleProfit = saleProfit;
	}

	public BigDecimal getSaleProfitRate() {
		return saleProfitRate;
	}

	public void setSaleProfitRate(BigDecimal saleProfitRate) {
		this.saleProfitRate = saleProfitRate;
	}

	public BigDecimal getWholesaleProfitRate() {
		return wholesaleProfitRate;
	}

	public void setWholesaleProfitRate(BigDecimal wholesaleProfitRate) {
		this.wholesaleProfitRate = wholesaleProfitRate;
	}

	public String getPosItemCategory() {
		return posItemCategory;
	}

	public void setPosItemCategory(String posItemCategory) {
		this.posItemCategory = posItemCategory;
	}

}
