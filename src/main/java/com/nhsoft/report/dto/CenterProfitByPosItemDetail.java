package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CenterProfitByPosItemDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7175666371398456807L;

	private String posOrderNum; // 单据号
	private String posOrderType; // 单据类型
	private Date saleTime;// 销售时间
	private String orderSeller;// 售货员
	private String orderMaker;// 制单人
	private String orderAuditor;// 审核人
	private Integer responseBranchNum;// 调往分店 号
	private String responseBranchName;// 调往分店 名
	private Integer posItemNum;
	private String posItemName;// 商品名称
	private String posItemCode;// 商品编号
	private String spec;// 规格
	private String outUnit;// 调出单位
	private BigDecimal outNum;// 调出数量
	private BigDecimal outUnitPrice;// 调出单价
	private BigDecimal outMoney;// 调出金额
	private BigDecimal costUnitPrice;// 成本金额
	private BigDecimal profitMoney;// 毛利金额
	private String remark;// 备注

	public CenterProfitByPosItemDetail() {
		setPosOrderNum("");
		setPosOrderType("");
		setSaleTime(new Date());
		setOrderSeller("");
		setOrderMaker("");
		setOrderAuditor("");
		setPosItemCode("");
		setPosItemNum(0000);
		setPosItemName("");
		setSpec("");
		setOutUnit("");
		setRemark("");
		setOutNum(BigDecimal.ZERO);
		setOutMoney(BigDecimal.ZERO);
		setOutUnitPrice(BigDecimal.ZERO);
		setCostUnitPrice(BigDecimal.ZERO);
		setProfitMoney(BigDecimal.ZERO);
	}

	public String getPosItemCode() {
		return posItemCode;
	}

	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
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

	public Integer getResponseBranchNum() {
		return responseBranchNum;
	}

	public void setResponseBranchNum(Integer responseBranchNum) {
		this.responseBranchNum = responseBranchNum;
	}

	public String getResponseBranchName() {
		return responseBranchName;
	}

	public void setResponseBranchName(String responseBranchName) {
		this.responseBranchName = responseBranchName;
	}

	public Integer getPosItemNum() {
		return posItemNum;
	}

	public void setPosItemNum(Integer posItemNum) {
		this.posItemNum = posItemNum;
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

	public String getOutUnit() {
		return outUnit;
	}

	public void setOutUnit(String outUnit) {
		this.outUnit = outUnit;
	}

	public BigDecimal getOutNum() {
		return outNum;
	}

	public void setOutNum(BigDecimal outNum) {
		this.outNum = outNum;
	}

	public BigDecimal getOutUnitPrice() {
		return outUnitPrice;
	}

	public void setOutUnitPrice(BigDecimal outUnitPrice) {
		this.outUnitPrice = outUnitPrice;
	}

	public BigDecimal getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(BigDecimal outMoney) {
		this.outMoney = outMoney;
	}

	public BigDecimal getCostUnitPrice() {
		return costUnitPrice;
	}

	public void setCostUnitPrice(BigDecimal costUnitPrice) {
		this.costUnitPrice = costUnitPrice;
	}

	public BigDecimal getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(BigDecimal profitMoney) {
		this.profitMoney = profitMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
