package com.nhsoft.module.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class UndeductionPosOrderDetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2277059797330313062L;

	public static class UndeductionPosOrderDetailId implements java.io.Serializable {

		private static final long serialVersionUID = 3355956852426062662L;
		private String orderNo;
		private Integer orderDetailNum;

		public UndeductionPosOrderDetailId() {
		}

		public UndeductionPosOrderDetailId(String orderNo, Integer orderDetailNum) {
			this.orderNo = orderNo;
			this.orderDetailNum = orderDetailNum;
		}

		public String getOrderNo() {
			return this.orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public Integer getOrderDetailNum() {
			return this.orderDetailNum;
		}

		public void setOrderDetailNum(Integer orderDetailNum) {
			this.orderDetailNum = orderDetailNum;
		}

		public boolean equals(Object other) {
			if ((this == other))
				return true;
			if ((other == null))
				return false;
			if (!(other instanceof PosOrderDetailId))
				return false;
			PosOrderDetailId castOther = (PosOrderDetailId) other;

			return ((this.getOrderNo() == castOther.getOrderNo()) || (this.getOrderNo() != null
					&& castOther.getOrderNo() != null && this.getOrderNo().equals(castOther.getOrderNo())))
					&& ((this.getOrderDetailNum() == castOther.getOrderDetailNum()) || (this.getOrderDetailNum() != null
							&& castOther.getOrderDetailNum() != null && this.getOrderDetailNum().equals(
							castOther.getOrderDetailNum())));
		}

		public int hashCode() {
			int result = 17;

			result = 37 * result + (getOrderNo() == null ? 0 : this.getOrderNo().hashCode());
			result = 37 * result + (getOrderDetailNum() == null ? 0 : this.getOrderDetailNum().hashCode());
			return result;
		}

	}

	private UndeductionPosOrderDetailId id;
	private Integer itemNum;
	private String orderDetailType;
	private String orderDetailItem;
	private BigDecimal orderDetailPrice;
	private BigDecimal orderDetailAmount;
	private BigDecimal orderDetailMoney;
	private BigDecimal orderDetailDiscount;
	private BigDecimal orderDetailPaymentMoney;
	private Integer orderDetailStateCode;
	private String orderDetailStateName;
	private String orderDetailMemo;
	private Integer orderDetailItemMatrixNum;
	private BigDecimal orderDetailCommission;
	private String orderDetailLotNumber;
	private BigDecimal orderDetailAssistAmount;
	private String orderDetailBookCode;
	private Integer orderDetailBranchNum;
	private String orderDetailBizday;
	private Integer orderDetailOrderState;
	private Integer itemGradeNum;
	private String orderSource;
	private Integer storehouseNum;
	private String orderOperator;
	private Date orderTime;
	private Boolean orderDetailHasKit;
	
	private Integer orderKitDetailNum;


	public Integer getOrderKitDetailNum() {
		return orderKitDetailNum;
	}

	public void setOrderKitDetailNum(Integer orderKitDetailNum) {
		this.orderKitDetailNum = orderKitDetailNum;
	}

	public Boolean getOrderDetailHasKit() {
		return orderDetailHasKit;
	}

	public void setOrderDetailHasKit(Boolean orderDetailHasKit) {
		this.orderDetailHasKit = orderDetailHasKit;
	}

	public UndeductionPosOrderDetailId getId() {
		return id;
	}

	public void setId(UndeductionPosOrderDetailId id) {
		this.id = id;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getOrderDetailType() {
		return orderDetailType;
	}

	public void setOrderDetailType(String orderDetailType) {
		this.orderDetailType = orderDetailType;
	}

	public String getOrderDetailItem() {
		return orderDetailItem;
	}

	public void setOrderDetailItem(String orderDetailItem) {
		this.orderDetailItem = orderDetailItem;
	}

	public BigDecimal getOrderDetailPrice() {
		return orderDetailPrice;
	}

	public void setOrderDetailPrice(BigDecimal orderDetailPrice) {
		this.orderDetailPrice = orderDetailPrice;
	}

	public BigDecimal getOrderDetailAmount() {
		return orderDetailAmount;
	}

	public void setOrderDetailAmount(BigDecimal orderDetailAmount) {
		this.orderDetailAmount = orderDetailAmount;
	}

	public BigDecimal getOrderDetailMoney() {
		return orderDetailMoney;
	}

	public void setOrderDetailMoney(BigDecimal orderDetailMoney) {
		this.orderDetailMoney = orderDetailMoney;
	}

	public BigDecimal getOrderDetailDiscount() {
		return orderDetailDiscount;
	}

	public void setOrderDetailDiscount(BigDecimal orderDetailDiscount) {
		this.orderDetailDiscount = orderDetailDiscount;
	}

	public BigDecimal getOrderDetailPaymentMoney() {
		return orderDetailPaymentMoney;
	}

	public void setOrderDetailPaymentMoney(BigDecimal orderDetailPaymentMoney) {
		this.orderDetailPaymentMoney = orderDetailPaymentMoney;
	}

	public Integer getOrderDetailStateCode() {
		return orderDetailStateCode;
	}

	public void setOrderDetailStateCode(Integer orderDetailStateCode) {
		this.orderDetailStateCode = orderDetailStateCode;
	}

	public String getOrderDetailStateName() {
		return orderDetailStateName;
	}

	public void setOrderDetailStateName(String orderDetailStateName) {
		this.orderDetailStateName = orderDetailStateName;
	}

	public String getOrderDetailMemo() {
		return orderDetailMemo;
	}

	public void setOrderDetailMemo(String orderDetailMemo) {
		this.orderDetailMemo = orderDetailMemo;
	}

	public Integer getOrderDetailItemMatrixNum() {
		return orderDetailItemMatrixNum;
	}

	public void setOrderDetailItemMatrixNum(Integer orderDetailItemMatrixNum) {
		this.orderDetailItemMatrixNum = orderDetailItemMatrixNum;
	}

	public BigDecimal getOrderDetailCommission() {
		return orderDetailCommission;
	}

	public void setOrderDetailCommission(BigDecimal orderDetailCommission) {
		this.orderDetailCommission = orderDetailCommission;
	}

	public String getOrderDetailLotNumber() {
		return orderDetailLotNumber;
	}

	public void setOrderDetailLotNumber(String orderDetailLotNumber) {
		this.orderDetailLotNumber = orderDetailLotNumber;
	}

	public BigDecimal getOrderDetailAssistAmount() {
		return orderDetailAssistAmount;
	}

	public void setOrderDetailAssistAmount(BigDecimal orderDetailAssistAmount) {
		this.orderDetailAssistAmount = orderDetailAssistAmount;
	}

	public String getOrderDetailBookCode() {
		return orderDetailBookCode;
	}

	public void setOrderDetailBookCode(String orderDetailBookCode) {
		this.orderDetailBookCode = orderDetailBookCode;
	}

	public Integer getOrderDetailBranchNum() {
		return orderDetailBranchNum;
	}

	public void setOrderDetailBranchNum(Integer orderDetailBranchNum) {
		this.orderDetailBranchNum = orderDetailBranchNum;
	}

	public String getOrderDetailBizday() {
		return orderDetailBizday;
	}

	public void setOrderDetailBizday(String orderDetailBizday) {
		this.orderDetailBizday = orderDetailBizday;
	}

	public Integer getOrderDetailOrderState() {
		return orderDetailOrderState;
	}

	public void setOrderDetailOrderState(Integer orderDetailOrderState) {
		this.orderDetailOrderState = orderDetailOrderState;
	}

	public Integer getItemGradeNum() {
		return itemGradeNum;
	}

	public void setItemGradeNum(Integer itemGradeNum) {
		this.itemGradeNum = itemGradeNum;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getOrderOperator() {
		return orderOperator;
	}

	public void setOrderOperator(String orderOperator) {
		this.orderOperator = orderOperator;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

}
