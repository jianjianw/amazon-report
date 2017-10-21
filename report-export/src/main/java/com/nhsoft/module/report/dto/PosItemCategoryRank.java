package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosItemCategoryRank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2065861058969894425L;

	private String posItemCategoryCode;// 商品类别代码
	private String posItemCategoryName;// 商品类别名
	private BigDecimal outOrderDetailUseQty;// 查询时间内的商品总数量
	private BigDecimal outOrderDetailSubtotal;// 查询时间内的商品总金额
	private Integer itemNum; //商品编号
	
	public PosItemCategoryRank(){
		outOrderDetailUseQty = BigDecimal.ZERO;
		outOrderDetailSubtotal = BigDecimal.ZERO;
	}

	public String getPosItemCategoryName() {
		return posItemCategoryName;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public void setPosItemCategoryName(String posItemCategoryName) {
		this.posItemCategoryName = posItemCategoryName;
	}

	public BigDecimal getOutOrderDetailUseQty() {
		return outOrderDetailUseQty;
	}

	public void setOutOrderDetailUseQty(BigDecimal outOrderDetailUseQty) {
		this.outOrderDetailUseQty = outOrderDetailUseQty;
	}

	public BigDecimal getOutOrderDetailSubtotal() {
		return outOrderDetailSubtotal;
	}

	public void setOutOrderDetailSubtotal(BigDecimal outOrderDetailSubtotal) {
		this.outOrderDetailSubtotal = outOrderDetailSubtotal;
	}

	public String getPosItemCategoryCode() {
		return posItemCategoryCode;
	}

	public void setPosItemCategoryCode(String posItemCategoryCode) {
		this.posItemCategoryCode = posItemCategoryCode;
	}

}
