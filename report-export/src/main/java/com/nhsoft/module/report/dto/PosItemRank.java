package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosItemRank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3833316028241601146L;

	private String outOrderDetailItemName;
	private String outOrderDetailItemCode;
	private String outOrderDetailItemSpec;
	private String outOrderDetailUseUnit;
	private BigDecimal outOrderDetailUseQty;// 查询时间内的商品总数量
	private BigDecimal outOrderDetailUsePrice;
	private BigDecimal outOrderDetailSubtotal;// 查询时间内的商品总金额
	private Integer itemNum;
	private Integer branchNum;

	public String getOutOrderDetailItemName() {
		return outOrderDetailItemName;
	}

	public void setOutOrderDetailItemName(String outOrderDetailItemName) {
		this.outOrderDetailItemName = outOrderDetailItemName;
	}

	public String getOutOrderDetailUseUnit() {
		return outOrderDetailUseUnit;
	}

	public void setOutOrderDetailUseUnit(String outOrderDetailUseUnit) {
		this.outOrderDetailUseUnit = outOrderDetailUseUnit;
	}

	public BigDecimal getOutOrderDetailUseQty() {
		return outOrderDetailUseQty;
	}

	public void setOutOrderDetailUseQty(BigDecimal outOrderDetailUseQty) {
		this.outOrderDetailUseQty = outOrderDetailUseQty;
	}

	public BigDecimal getOutOrderDetailUsePrice() {
		return outOrderDetailUsePrice;
	}

	public void setOutOrderDetailUsePrice(BigDecimal outOrderDetailUsePrice) {
		this.outOrderDetailUsePrice = outOrderDetailUsePrice;
	}

	public BigDecimal getOutOrderDetailSubtotal() {
		return outOrderDetailSubtotal;
	}

	public void setOutOrderDetailSubtotal(BigDecimal outOrderDetailSubtotal) {
		this.outOrderDetailSubtotal = outOrderDetailSubtotal;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getOutOrderDetailItemCode() {
		return outOrderDetailItemCode;
	}

	public void setOutOrderDetailItemCode(String outOrderDetailItemCode) {
		this.outOrderDetailItemCode = outOrderDetailItemCode;
	}

	public String getOutOrderDetailItemSpec() {
		return outOrderDetailItemSpec;
	}

	public void setOutOrderDetailItemSpec(String outOrderDetailItemSpec) {
		this.outOrderDetailItemSpec = outOrderDetailItemSpec;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

}
