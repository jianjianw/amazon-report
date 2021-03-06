package com.nhsoft.module.report.model;


import com.nhsoft.module.report.query.State;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AdjustmentOrder generated by hbm2java
 */
@Entity
public class AdjustmentOrder implements java.io.Serializable {

	private static final long serialVersionUID = -5285530469049779180L;
	@Id
	private String adjustmentOrderFid;
	private Integer storehouseNum;
	private String systemBookCode;
	@Transient
	private Integer branchNum;
	private Date adjustmentOrderDate;
	private String adjustmentOrderCause;
	private String adjustmentOrderOperator;
	private String adjustmentOrderDirection;
	private String adjustmentOrderMemo;
	@Embedded
	@AttributeOverrides( {
		 			@AttributeOverride(name="stateCode", column = @Column(name="adjustmentOrderStateCode")), 
		@AttributeOverride(name="stateName", column = @Column(name="adjustmentOrderStateName")) } )
	private State state;
	private String adjustmentOrderCreator;
	private String adjustmentOrderAuditor;
	private boolean adjustmentOrderTransferFlag;
	private BigDecimal adjustmentOrderMoney;
	private Date adjustmentOrderCreateTime;
	private Date adjustmentOrderAuditTime;
	private BigDecimal adjustmentOrderSaleMoney;
	private String adjustmentOrderRefBill;
	private Boolean adjustmentOrderAntiFlag;
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "adjustmentOrderFid", updatable=false, insertable=false)
	private List<AdjustmentOrderDetail> adjustmentOrderDetails = new ArrayList<AdjustmentOrderDetail>();

	@Transient
	private Boolean saveAudit = false;

	//临时属性
	@Transient
	private AppUser appUser;
	@Transient
	private String copyFid;


	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getCopyFid() {
		return copyFid;
	}

	public void setCopyFid(String copyFid) {
		this.copyFid = copyFid;
	}


	public AdjustmentOrder() {
	}

	public Boolean getSaveAudit() {
		return saveAudit;
	}

	public void setSaveAudit(Boolean saveAudit) {
		this.saveAudit = saveAudit;
	}

	public Boolean getAdjustmentOrderAntiFlag() {
		return adjustmentOrderAntiFlag;
	}

	public void setAdjustmentOrderAntiFlag(Boolean adjustmentOrderAntiFlag) {
		this.adjustmentOrderAntiFlag = adjustmentOrderAntiFlag;
	}

	public String getAdjustmentOrderRefBill() {
		return adjustmentOrderRefBill;
	}

	public void setAdjustmentOrderRefBill(String adjustmentOrderRefBill) {
		this.adjustmentOrderRefBill = adjustmentOrderRefBill;
	}

	public BigDecimal getAdjustmentOrderSaleMoney() {
		return adjustmentOrderSaleMoney;
	}

	public void setAdjustmentOrderSaleMoney(BigDecimal adjustmentOrderSaleMoney) {
		this.adjustmentOrderSaleMoney = adjustmentOrderSaleMoney;
	}

	public String getAdjustmentOrderFid() {
		return this.adjustmentOrderFid;
	}

	public void setAdjustmentOrderFid(String adjustmentOrderFid) {
		this.adjustmentOrderFid = adjustmentOrderFid;
	}

	public Date getAdjustmentOrderDate() {
		return this.adjustmentOrderDate;
	}

	public void setAdjustmentOrderDate(Date adjustmentOrderDate) {
		this.adjustmentOrderDate = adjustmentOrderDate;
	}

	public String getAdjustmentOrderCause() {
		return this.adjustmentOrderCause;
	}

	public void setAdjustmentOrderCause(String adjustmentOrderCause) {
		this.adjustmentOrderCause = adjustmentOrderCause;
	}

	public String getAdjustmentOrderOperator() {
		return this.adjustmentOrderOperator;
	}

	public void setAdjustmentOrderOperator(String adjustmentOrderOperator) {
		this.adjustmentOrderOperator = adjustmentOrderOperator;
	}

	public String getAdjustmentOrderDirection() {
		return this.adjustmentOrderDirection;
	}

	public void setAdjustmentOrderDirection(String adjustmentOrderDirection) {
		this.adjustmentOrderDirection = adjustmentOrderDirection;
	}

	public String getAdjustmentOrderMemo() {
		return this.adjustmentOrderMemo;
	}

	public void setAdjustmentOrderMemo(String adjustmentOrderMemo) {
		this.adjustmentOrderMemo = adjustmentOrderMemo;
	}

	public String getAdjustmentOrderCreator() {
		return this.adjustmentOrderCreator;
	}

	public void setAdjustmentOrderCreator(String adjustmentOrderCreator) {
		this.adjustmentOrderCreator = adjustmentOrderCreator;
	}

	public String getAdjustmentOrderAuditor() {
		return this.adjustmentOrderAuditor;
	}

	public void setAdjustmentOrderAuditor(String adjustmentOrderAuditor) {
		this.adjustmentOrderAuditor = adjustmentOrderAuditor;
	}

	public boolean isAdjustmentOrderTransferFlag() {
		return this.adjustmentOrderTransferFlag;
	}

	public void setAdjustmentOrderTransferFlag(boolean adjustmentOrderTransferFlag) {
		this.adjustmentOrderTransferFlag = adjustmentOrderTransferFlag;
	}

	public BigDecimal getAdjustmentOrderMoney() {
		return this.adjustmentOrderMoney;
	}

	public void setAdjustmentOrderMoney(BigDecimal adjustmentOrderMoney) {
		this.adjustmentOrderMoney = adjustmentOrderMoney;
	}

	public Date getAdjustmentOrderCreateTime() {
		return this.adjustmentOrderCreateTime;
	}

	public void setAdjustmentOrderCreateTime(Date adjustmentOrderCreateTime) {
		this.adjustmentOrderCreateTime = adjustmentOrderCreateTime;
	}

	public Date getAdjustmentOrderAuditTime() {
		return this.adjustmentOrderAuditTime;
	}

	public void setAdjustmentOrderAuditTime(Date adjustmentOrderAuditTime) {
		this.adjustmentOrderAuditTime = adjustmentOrderAuditTime;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<AdjustmentOrderDetail> getAdjustmentOrderDetails() {
		return adjustmentOrderDetails;
	}

	public void setAdjustmentOrderDetails(List<AdjustmentOrderDetail> adjustmentOrderDetails) {
		this.adjustmentOrderDetails = adjustmentOrderDetails;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public void removeZeroDetail() {
		for (int i = adjustmentOrderDetails.size() - 1; i >= 0; i--) {
			AdjustmentOrderDetail detail = adjustmentOrderDetails.get(i);
			if (detail.getAdjustmentOrderDetailQty().compareTo(BigDecimal.ZERO) == 0) {
				adjustmentOrderDetails.remove(i);
			}
		}
	}

	public static AdjustmentOrder get(List<AdjustmentOrder> adjustmentOrders, String adjustmentOrderFid) {
		for (int i = 0; i < adjustmentOrders.size(); i++) {
			AdjustmentOrder adjustmentOrder = adjustmentOrders.get(i);
			if (adjustmentOrder.getAdjustmentOrderFid().equals(adjustmentOrderFid)) {
				return adjustmentOrder;
			}
		}
		return null;
	}

	public void recalMoney() {
		adjustmentOrderMoney = BigDecimal.ZERO;
		adjustmentOrderSaleMoney = BigDecimal.ZERO;
		for (int i = 0; i < adjustmentOrderDetails.size(); i++) {
			AdjustmentOrderDetail adjustmentOrderDetail = adjustmentOrderDetails.get(i);
			adjustmentOrderMoney = adjustmentOrderMoney.add(adjustmentOrderDetail.getAdjustmentOrderDetailSubtotal());
			if(adjustmentOrderDetail.getAdjustmentOrderDetailSalePrice() != null){
				adjustmentOrderSaleMoney = adjustmentOrderSaleMoney.add(adjustmentOrderDetail.getAdjustmentOrderDetailSalePrice()
						.multiply(adjustmentOrderDetail.getAdjustmentOrderDetailQty()));
			}
			
		}
		adjustmentOrderMoney = adjustmentOrderMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		adjustmentOrderSaleMoney = adjustmentOrderSaleMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		
	}
	
	public void calSaleMoney() {
		adjustmentOrderSaleMoney = BigDecimal.ZERO;
		for (int i = 0; i < adjustmentOrderDetails.size(); i++) {
			AdjustmentOrderDetail adjustmentOrderDetail = adjustmentOrderDetails.get(i);
			if(adjustmentOrderDetail.getAdjustmentOrderDetailSalePrice() != null){
				adjustmentOrderSaleMoney = adjustmentOrderSaleMoney.add(adjustmentOrderDetail.getAdjustmentOrderDetailSalePrice()
						.multiply(adjustmentOrderDetail.getAdjustmentOrderDetailQty()));
			}
			
		}
		adjustmentOrderSaleMoney = adjustmentOrderSaleMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		
	}

	public static AdjustmentOrderDetail getDetail(List<AdjustmentOrderDetail> adjustmentOrderDetails, Integer itemNum, 
			Integer adjustmentOrderDetailItemMatrixNum, String adjustmentOrderDetailLotNumber) {
		if(adjustmentOrderDetailItemMatrixNum == null){
			adjustmentOrderDetailItemMatrixNum = 0;
		}
		if(adjustmentOrderDetailLotNumber == null){
			adjustmentOrderDetailLotNumber = "";
		}
		for(int i = 0;i < adjustmentOrderDetails.size();i++){
			AdjustmentOrderDetail detail = adjustmentOrderDetails.get(i);
			
			if(detail.getItemNum().equals(itemNum) 
					&& detail.getAdjustmentOrderDetailItemMatrixNum().equals(adjustmentOrderDetailItemMatrixNum)
					&& detail.getAdjustmentOrderDetailLotNumber().equals(adjustmentOrderDetailLotNumber)){
				return detail;
			}
		}
		return null;
	}

}
