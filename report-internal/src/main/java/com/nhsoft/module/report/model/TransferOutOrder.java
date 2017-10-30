package com.nhsoft.module.report.model;

import com.nhsoft.module.report.query.State;
import com.nhsoft.module.report.util.AppConstants;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TransferOutOrder entity. @author MyEclipse Persistence Tools
 */

@Entity
public class TransferOutOrder implements java.io.Serializable {

	private static final long serialVersionUID = -4784395156086714997L;
	@Id
	private String outOrderFid;
	private String systemBookCode;
	private Integer branchNum;
	private String outSystemBookCode;
	private Integer outBranchNum;
	private Integer storehouseNum;
	private Date outOrderDate;
	private String outOrderOperator;
	@Embedded
	@AttributeOverrides( {
		 			@AttributeOverride(name="stateCode", column = @Column(name="outOrderStateCode")), 
		@AttributeOverride(name="stateName", column = @Column(name="outOrderStateName")) } )
	private State state;
	private String outOrderMemo;
	private Boolean outOrderTransferFlag;
	private String outOrderCreator;
	private String outOrderAuditor;
	private String outOrderUuid;
	private BigDecimal outOrderTotalMoney;
	private BigDecimal outOrderDiscountMoney;
	private BigDecimal outOrderDueMoney;
	private BigDecimal outOrderPaidMoney;
	private Date outOrderLastestPaymentDate;
	private Date outOrderPaymentDate;
	private String outOrderRefInBillNo; //暂时用作小嘴的单号
	private Date outOrderCreateTime;
	private Date outOrderAuditTime;
	private Date outOrderSendTime;
	private Boolean outOrderOnlineFlag;
	private Date outOrderPickingTime;//配货时间
	private Integer outOrderPrintCount;
	private Boolean outOrderRepealFlag;
	private String outOrderBillNo;
	private BigDecimal outOrderOtherFee;
	private String outOrderPicker;
	private Integer transferLineNum;
	private Boolean outOrderAntiFlag;
	private String outOrderSender;
	private String outOrderAuditBizday;
	private BigDecimal outOrderSaleMoney;
	private BigDecimal outOrderUseQty;
	private String outOrderLabel;
	private String outOrderInnerBill;
	private BigDecimal outOrderCostMoney;
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "outOrderFid", updatable=false, insertable=false)
	private List<OutOrderDetail> outOrderDetails = new ArrayList<OutOrderDetail>();
	@ManyToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name="RequestOrderTransferOutOrder", joinColumns={@JoinColumn(name="outOrderFid")}, inverseJoinColumns={@JoinColumn(name="requestOrderFid", referencedColumnName="requestOrderFid")})
	private List<RequestOrder> requestOrders = new ArrayList<RequestOrder>();
	
	@Transient
	private String requestOrderFids;
	@Transient
	private Branch branch;
	
	//配送中心按顶级类拆单时过滤用
	@Transient
	private String categoryCode;
	@Transient
	private String itemDepartment;
	@Transient
	private AppUser appUser;
	@Transient
	private String copyFid;//复制单据号
	@Transient
	private boolean flushFlag = false;
	@Transient
	private Boolean saveAndAudit = false;
	@Transient
	private Date outOrderInTime;
	@Transient
	private String settleMentState;
	@Transient
	private String orderState;
	@Transient
	private Boolean tempAudit;
	
	public Branch getToBranch() {
		return toBranch;
	}
	
	public void setToBranch(Branch toBranch) {
		this.toBranch = toBranch;
	}
	
	@Transient
	private Branch toBranch;

	
	//内部调拨单使用
	@Transient
	private Integer inStorehouseNum;
	
	public BigDecimal getOutOrderCostMoney() {
		return outOrderCostMoney;
	}
	
	public void setOutOrderCostMoney(BigDecimal outOrderCostMoney) {
		this.outOrderCostMoney = outOrderCostMoney;
	}
	
	public Boolean getTempAudit() {
		return tempAudit;
	}

	public void setTempAudit(Boolean tempAudit) {
		this.tempAudit = tempAudit;
	}

	public Integer getInStorehouseNum() {
		return inStorehouseNum;
	}

	public void setInStorehouseNum(Integer inStorehouseNum) {
		this.inStorehouseNum = inStorehouseNum;
	}

	public String getOutOrderInnerBill() {
		return outOrderInnerBill;
	}

	public void setOutOrderInnerBill(String outOrderInnerBill) {
		this.outOrderInnerBill = outOrderInnerBill;
	}

	public String getOutOrderLabel() {
		return outOrderLabel;
	}

	public void setOutOrderLabel(String outOrderLabel) {
		this.outOrderLabel = outOrderLabel;
	}

	public String getItemDepartment() {
		return itemDepartment;
	}

	public void setItemDepartment(String itemDepartment) {
		this.itemDepartment = itemDepartment;
	}

	public void setSettleMentState(String settleMentState) {
		this.settleMentState = settleMentState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Date getOutOrderInTime() {
		return outOrderInTime;
	}

	public void setOutOrderInTime(Date outOrderInTime) {
		this.outOrderInTime = outOrderInTime;
	}

	public BigDecimal getOutOrderSaleMoney() {
		return outOrderSaleMoney;
	}

	public void setOutOrderSaleMoney(BigDecimal outOrderSaleMoney) {
		this.outOrderSaleMoney = outOrderSaleMoney;
	}

	public BigDecimal getOutOrderUseQty() {
		return outOrderUseQty;
	}

	public void setOutOrderUseQty(BigDecimal outOrderUseQty) {
		this.outOrderUseQty = outOrderUseQty;
	}

	public String getOutOrderAuditBizday() {
		return outOrderAuditBizday;
	}

	public void setOutOrderAuditBizday(String outOrderAuditBizday) {
		this.outOrderAuditBizday = outOrderAuditBizday;
	}

	public String getOutOrderSender() {
		return outOrderSender;
	}

	public void setOutOrderSender(String outOrderSender) {
		this.outOrderSender = outOrderSender;
	}

	public Boolean getSaveAndAudit() {
		return saveAndAudit;
	}

	public void setSaveAndAudit(Boolean saveAndAudit) {
		this.saveAndAudit = saveAndAudit;
	}

	public Boolean getOutOrderAntiFlag() {
		return outOrderAntiFlag;
	}

	public void setOutOrderAntiFlag(Boolean outOrderAntiFlag) {
		this.outOrderAntiFlag = outOrderAntiFlag;
	}

	public Integer getTransferLineNum() {
		return transferLineNum;
	}

	public void setTransferLineNum(Integer transferLineNum) {
		this.transferLineNum = transferLineNum;
	}

	public String getOutOrderPicker() {
		return outOrderPicker;
	}

	public void setOutOrderPicker(String outOrderPicker) {
		this.outOrderPicker = outOrderPicker;
	}

	public boolean isFlushFlag() {
		return flushFlag;
	}

	public void setFlushFlag(boolean flushFlag) {
		this.flushFlag = flushFlag;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public BigDecimal getOutOrderOtherFee() {
		return outOrderOtherFee;
	}

	public void setOutOrderOtherFee(BigDecimal outOrderOtherFee) {
		this.outOrderOtherFee = outOrderOtherFee;
	}

	public String getCopyFid() {
		return copyFid;
	}

	public void setCopyFid(String copyFid) {
		this.copyFid = copyFid;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Boolean getOutOrderRepealFlag() {
		return outOrderRepealFlag;
	}

	public void setOutOrderRepealFlag(Boolean outOrderRepealFlag) {
		this.outOrderRepealFlag = outOrderRepealFlag;
	}

	public String getOutOrderBillNo() {
		return outOrderBillNo;
	}

	public void setOutOrderBillNo(String outOrderBillNo) {
		this.outOrderBillNo = outOrderBillNo;
	}

	public String getRequestOrderFids() {
		return requestOrderFids;
	}

	public void setRequestOrderFids(String requestOrderFids) {
		this.requestOrderFids = requestOrderFids;
	}

	public String getOutOrderFid() {
		return outOrderFid;
	}

	public void setOutOrderFid(String outOrderFid) {
		this.outOrderFid = outOrderFid;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getOutSystemBookCode() {
		return outSystemBookCode;
	}

	public void setOutSystemBookCode(String outSystemBookCode) {
		this.outSystemBookCode = outSystemBookCode;
	}

	public Integer getOutBranchNum() {
		return outBranchNum;
	}

	public void setOutBranchNum(Integer outBranchNum) {
		this.outBranchNum = outBranchNum;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public Date getOutOrderDate() {
		return outOrderDate;
	}

	public void setOutOrderDate(Date outOrderDate) {
		this.outOrderDate = outOrderDate;
	}

	public String getOutOrderOperator() {
		return outOrderOperator;
	}

	public void setOutOrderOperator(String outOrderOperator) {
		this.outOrderOperator = outOrderOperator;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getOutOrderMemo() {
		return outOrderMemo;
	}

	public void setOutOrderMemo(String outOrderMemo) {
		this.outOrderMemo = outOrderMemo;
	}

	public Boolean getOutOrderTransferFlag() {
		return outOrderTransferFlag;
	}

	public void setOutOrderTransferFlag(Boolean outOrderTransferFlag) {
		this.outOrderTransferFlag = outOrderTransferFlag;
	}

	public String getOutOrderCreator() {
		return outOrderCreator;
	}

	public void setOutOrderCreator(String outOrderCreator) {
		this.outOrderCreator = outOrderCreator;
	}

	public String getOutOrderAuditor() {
		return outOrderAuditor;
	}

	public void setOutOrderAuditor(String outOrderAuditor) {
		this.outOrderAuditor = outOrderAuditor;
	}

	public String getOutOrderUuid() {
		return outOrderUuid;
	}

	public void setOutOrderUuid(String outOrderUuid) {
		this.outOrderUuid = outOrderUuid;
	}

	public BigDecimal getOutOrderTotalMoney() {
		return outOrderTotalMoney;
	}

	public void setOutOrderTotalMoney(BigDecimal outOrderTotalMoney) {
		this.outOrderTotalMoney = outOrderTotalMoney;
	}

	public BigDecimal getOutOrderDiscountMoney() {
		return outOrderDiscountMoney;
	}

	public void setOutOrderDiscountMoney(BigDecimal outOrderDiscountMoney) {
		if(outOrderDiscountMoney != null){
			outOrderDiscountMoney = outOrderDiscountMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.outOrderDiscountMoney = outOrderDiscountMoney;
	}

	public BigDecimal getOutOrderDueMoney() {
		return outOrderDueMoney;
	}

	public void setOutOrderDueMoney(BigDecimal outOrderDueMoney) {
		if(outOrderDueMoney != null){
			outOrderDueMoney = outOrderDueMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.outOrderDueMoney = outOrderDueMoney;
	}

	public BigDecimal getOutOrderPaidMoney() {
		return outOrderPaidMoney;
	}

	public void setOutOrderPaidMoney(BigDecimal outOrderPaidMoney) {
		if(outOrderPaidMoney != null){
			outOrderPaidMoney = outOrderPaidMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.outOrderPaidMoney = outOrderPaidMoney;
	}

	public Date getOutOrderLastestPaymentDate() {
		return outOrderLastestPaymentDate;
	}

	public void setOutOrderLastestPaymentDate(Date outOrderLastestPaymentDate) {
		this.outOrderLastestPaymentDate = outOrderLastestPaymentDate;
	}

	public Date getOutOrderPaymentDate() {
		return outOrderPaymentDate;
	}

	public void setOutOrderPaymentDate(Date outOrderPaymentDate) {
		this.outOrderPaymentDate = outOrderPaymentDate;
	}

	public String getOutOrderRefInBillNo() {
		return outOrderRefInBillNo;
	}

	public void setOutOrderRefInBillNo(String outOrderRefInBillNo) {
		this.outOrderRefInBillNo = outOrderRefInBillNo;
	}

	public Date getOutOrderCreateTime() {
		return outOrderCreateTime;
	}

	public void setOutOrderCreateTime(Date outOrderCreateTime) {
		this.outOrderCreateTime = outOrderCreateTime;
	}

	public Date getOutOrderAuditTime() {
		return outOrderAuditTime;
	}

	public void setOutOrderAuditTime(Date outOrderAuditTime) {
		this.outOrderAuditTime = outOrderAuditTime;


	}

	public List<OutOrderDetail> getOutOrderDetails() {
		return outOrderDetails;
	}

	public void setOutOrderDetails(List<OutOrderDetail> outOrderDetails) {
		this.outOrderDetails = outOrderDetails;
	}

	public Date getOutOrderSendTime() {
		return outOrderSendTime;
	}

	public void setOutOrderSendTime(Date outOrderSendTime) {
		this.outOrderSendTime = outOrderSendTime;
	}

	public Boolean getOutOrderOnlineFlag() {
		return outOrderOnlineFlag;
	}

	public void setOutOrderOnlineFlag(Boolean outOrderOnlineFlag) {
		this.outOrderOnlineFlag = outOrderOnlineFlag;
	}

	public List<RequestOrder> getRequestOrders() {
		return requestOrders;
	}

	public void setRequestOrders(List<RequestOrder> requestOrders) {
		this.requestOrders = requestOrders;
	}

	public Date getOutOrderPickingTime() {
		return outOrderPickingTime;
	}

	public void setOutOrderPickingTime(Date outOrderPickingTime) {
		this.outOrderPickingTime = outOrderPickingTime;
	}

	public Integer getOutOrderPrintCount() {
		return outOrderPrintCount;
	}

	public void setOutOrderPrintCount(Integer outOrderPrintCount) {
		this.outOrderPrintCount = outOrderPrintCount;
	}

	public String createSettleMentState(){
		if(outOrderPaidMoney == null){
			outOrderPaidMoney = BigDecimal.ZERO;
		}
		if(outOrderDiscountMoney == null){
			outOrderDiscountMoney = BigDecimal.ZERO;
		}
		if(outOrderPaidMoney.compareTo(BigDecimal.ZERO) == 0 && outOrderDiscountMoney.compareTo(BigDecimal.ZERO) == 0){
			settleMentState = "未结算";
			return settleMentState;
		} else if(outOrderDueMoney.subtract(outOrderPaidMoney).subtract(outOrderDiscountMoney).compareTo(BigDecimal.ZERO) == 0){
			settleMentState = "已结算";
			return settleMentState;
		} else {
			settleMentState = "部分结算";
			return settleMentState;
		}
	}
	
	public Boolean hasReceive() {
		if(requestOrders != null){
			for(int i = 0;i < requestOrders.size();i++){
				RequestOrder requestOrder = requestOrders.get(i);
				List<RequestOrderDetail> requestOrderDetails = requestOrder.getRequestOrderDetails();
				for (int j = 0; j < requestOrderDetails.size(); j++) {
					RequestOrderDetail detail = requestOrderDetails.get(j);
					if (detail.getRequestOrderDetailPurchaseReceivedQty() == null){
						detail.setRequestOrderDetailPurchaseReceivedQty(BigDecimal.ZERO);
					}
					if (detail.getRequestOrderDetailOutReceivedQty() == null){
						detail.setRequestOrderDetailOutReceivedQty(BigDecimal.ZERO);
					}
					if(detail.getRequestOrderDetailOutReceivedQty().compareTo(BigDecimal.ZERO) > 0
							|| detail.getRequestOrderDetailPurchaseReceivedQty().compareTo(BigDecimal.ZERO) > 0){
						return true;
					}
					
				}
			}		
			
		}
		return false;
	}
	
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String createOrderState(){
		if(hasReceive()){
			orderState = "已收货";
			return orderState;
		}
		if(state.getStateCode() == AppConstants.STATE_INIT_CODE){
			orderState = "待审核";
			return orderState;
		}
		if(state.getStateCode() == AppConstants.STATE_INIT_AUDIT_CODE
				&& outOrderPickingTime == null
				&& outOrderSendTime == null
				&& !hasReceive()){
			orderState = "已审核";
			return orderState;
		}
		if(state.getStateCode() == AppConstants.STATE_INIT_AUDIT_CODE
				&& outOrderPickingTime != null
				&& outOrderSendTime == null
				&& !hasReceive()){
			orderState = "已配货";
			return orderState;
		}
		if(state.getStateCode() == AppConstants.STATE_INIT_AUDIT_CODE
				&& outOrderSendTime != null
				&& !hasReceive()){
			orderState = "已发货";
			return orderState;
		}	
		orderState = "待审核";
		return orderState;
	}
	
//	public String getOrderState() {
//		return orderState;
//	}

	public BigDecimal calcMoney(){
		BigDecimal total = BigDecimal.ZERO;
		for(int i = 0; i < outOrderDetails.size(); i++){
			OutOrderDetail detail = outOrderDetails.get(i);
			total = total.add(detail.getOutOrderDetailSaleSubtotal());
		}
		return total;
	}
	
	public BigDecimal calCostMoney(){
		BigDecimal total = BigDecimal.ZERO;
		for(int i = 0; i < outOrderDetails.size(); i++){
			OutOrderDetail detail = outOrderDetails.get(i);
			total = total.add(detail.getOutOrderDetailSaleSubtotal());
		}
		return total;
	}
	
	public void recal(){
		outOrderTotalMoney = BigDecimal.ZERO;
		outOrderOtherFee = BigDecimal.ZERO;
		outOrderSaleMoney = BigDecimal.ZERO;
		BigDecimal useQty = BigDecimal.ZERO;
		outOrderCostMoney = BigDecimal.ZERO;
		for(int i = outOrderDetails.size() - 1;i >= 0;i--){
			OutOrderDetail detail = outOrderDetails.get(i);
			
			if(detail.getOutOrderDetailUseQty().compareTo(BigDecimal.ZERO) != 0){
				detail.setOutOrderDetailSaleSubtotal(detail.getOutOrderDetailUsePrice().multiply(detail.getOutOrderDetailUseQty()).setScale(2, BigDecimal.ROUND_HALF_UP));
				detail.setOutOrderDetailQty(detail.getOutOrderDetailUseQty().multiply(detail.getOutOrderDetailUseRate()));
				
			}
			if(detail.getOutOrderDetailQty().compareTo(BigDecimal.ZERO) > 0){
				detail.setOutOrderDetailPrice(detail.getOutOrderDetailSaleSubtotal().divide(detail.getOutOrderDetailQty(), 4, BigDecimal.ROUND_HALF_UP));
			}
			if(detail.getOutOrderDetailPresentBasicQty() == null){
				detail.setOutOrderDetailPresentBasicQty(BigDecimal.ZERO);
			}
			if(detail.getOutOrderDetailPresentAssistQty() == null){
				detail.setOutOrderDetailPresentAssistQty(BigDecimal.ZERO);
			}
			outOrderTotalMoney = outOrderTotalMoney.add(detail.getOutOrderDetailSaleSubtotal());
			
			if(detail.getOutOrderDetailOtherFee() == null){
				detail.setOutOrderDetailOtherFee(BigDecimal.ZERO);
			}
			if(detail.getOutOrderDetailSalePrice() != null){
				outOrderSaleMoney = outOrderSaleMoney.add(detail.getOutOrderDetailSalePrice().multiply(detail.getOutOrderDetailQty()));
			}
			outOrderOtherFee = outOrderOtherFee.add(detail.getOutOrderDetailOtherFee());
			if(detail.getOutOrderDetailSubtotal() != null){
				outOrderCostMoney = outOrderCostMoney.add(detail.getOutOrderDetailSubtotal());
				
			}
			
			
			useQty = useQty.add(detail.getOutOrderDetailUseQty());
		}
		outOrderTotalMoney = outOrderTotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		outOrderSaleMoney = outOrderSaleMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		outOrderCostMoney = outOrderCostMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		outOrderDueMoney = outOrderTotalMoney;
		if(outOrderUseQty == null){
			outOrderUseQty = useQty;
		}
	}

	public void removeZeroDetail() {
		for(int i = outOrderDetails.size() - 1;i >= 0;i--){
			OutOrderDetail detail = outOrderDetails.get(i);
			//AMA-7023
			if(detail.getOutOrderDetailPresentBasicQty() == null){
				detail.setOutOrderDetailPresentBasicQty(BigDecimal.ZERO);
			}
			if(detail.getOutOrderDetailQty().compareTo(BigDecimal.ZERO) == 0 
					&& detail.getOutOrderDetailPresentBasicQty().compareTo(BigDecimal.ZERO) == 0){
				outOrderDetails.remove(i);
			}
		}
		
	}
	
	public static TransferOutOrder getTransferOutOrder(List<TransferOutOrder> transferOutOrders, Integer branchNum){
		for(int i = 0, len = transferOutOrders.size(); i < len; i++){
			TransferOutOrder transferOutOrder = transferOutOrders.get(i);
			if (transferOutOrder.getBranchNum().equals(branchNum)){
				return transferOutOrder;
			}
		}
		return null;
	}
	
	public static TransferOutOrder get(List<TransferOutOrder> transferOutOrders, String outOrderFid){
		for(int i = 0, len = transferOutOrders.size(); i < len; i++){
			TransferOutOrder transferOutOrder = transferOutOrders.get(i);
			if (transferOutOrder.getOutOrderFid().equals(outOrderFid)){
				return transferOutOrder;
			}
		}
		return null;
	}

	public static TransferOutOrder getByStorehouseNum(List<TransferOutOrder> transferOutOrders, Integer storehouseNum){
		for(int i = 0, len = transferOutOrders.size(); i < len; i++){
			TransferOutOrder transferOutOrder = transferOutOrders.get(i);
			if (transferOutOrder.getStorehouseNum().equals(storehouseNum)){
				return transferOutOrder;
			}
		}
		return null;
	}
	
	public static TransferOutOrder getByInStorehouseNum(List<TransferOutOrder> transferOutOrders, Integer storehouseNum){
		for(int i = 0, len = transferOutOrders.size(); i < len; i++){
			TransferOutOrder transferOutOrder = transferOutOrders.get(i);
			if (transferOutOrder.getInStorehouseNum().equals(storehouseNum)){
				return transferOutOrder;
			}
		}
		return null;
	}

}