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
 * TransferInOrder entity. @author MyEclipse Persistence Tools
 */

@Entity
public class TransferInOrder implements java.io.Serializable {

	private static final long serialVersionUID = -924794960784002869L;
	@Id
	private String inOrderFid;
	private String systemBookCode;
	private Integer branchNum;
	private Integer storehouseNum;
	private String inSystemBookCode;
	private Integer inBranchNum;
	private String outOrderFid;
	private Date inOrderDate;
	private String inOrderOperator;
	private String inOrderMemo;
	@Embedded
	@AttributeOverrides( {
		 			@AttributeOverride(name="stateCode", column = @Column(name="inOrderStateCode")), 
		@AttributeOverride(name="stateName", column = @Column(name="inOrderStateName")) } )
	private State state;
	private Boolean inOrderTransferFlag;
	private String inOrderCreator;
	private String inOrderAuditor;
	private String inOrderUuid;
	private BigDecimal inOrderTotalMoney;
	private BigDecimal inOrderDiscountMoney;
	private BigDecimal inOrderDueMoney;
	private BigDecimal inOrderPaidMoney;
	private Date inOrderLastestPaymentDate;
	private Date inOrderPaymentDate;
	private Date inOrderCreateTime;
	private Date inOrderAuditTime;
	private Integer inOrderPrintCount;
	private Boolean inOrderRepealFlag;
	private String inOrderBillNo;
	private BigDecimal inOrderOtherFee;
	private String inOrderRequestOrderFids;
	private Boolean inOrderAntiFlag;
	private String inOrderAuditBizday;
	private String inOrderLabel;
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "inOrderFid", updatable=false, insertable=false)
	private List<InOrderDetail> inOrderDetails = new ArrayList<InOrderDetail>();
	
	//临时属性
	@Transient
	private String antiOrderFid;
	@Transient
	private Boolean tempAudit;
	@Transient
	private Boolean saveAndAudit = false;
	
	public Boolean getSaveAndAudit() {
		return saveAndAudit;
	}
	
	public void setSaveAndAudit(Boolean saveAndAudit) {
		this.saveAndAudit = saveAndAudit;
	}
	
	public Boolean getTempAudit() {
		return tempAudit;
	}

	public void setTempAudit(Boolean tempAudit) {
		this.tempAudit = tempAudit;
	}

	public String getAntiOrderFid() {
		return antiOrderFid;
	}

	public void setAntiOrderFid(String antiOrderFid) {
		this.antiOrderFid = antiOrderFid;
	}

	public String getInOrderLabel() {
		return inOrderLabel;
	}

	public void setInOrderLabel(String inOrderLabel) {
		this.inOrderLabel = inOrderLabel;
	}

	public Boolean getInOrderAntiFlag() {
		return inOrderAntiFlag;
	}

	public void setInOrderAntiFlag(Boolean inOrderAntiFlag) {
		this.inOrderAntiFlag = inOrderAntiFlag;
	}

	//临时属性 是否来自API
	@Transient
	private boolean fromAPi = false;


	@Transient
	private AppUser appUser;
	@Transient
	private String copyFid;
	
	
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
	
    public boolean isFromAPi() {
		return fromAPi;
	}

	public String getInOrderAuditBizday() {
		return inOrderAuditBizday;
	}

	public void setInOrderAuditBizday(String inOrderAuditBizday) {
		this.inOrderAuditBizday = inOrderAuditBizday;
	}

	public void setFromAPi(boolean fromAPi) {
		this.fromAPi = fromAPi;
	}

	public String getInOrderRequestOrderFids() {
		return inOrderRequestOrderFids;
	}

	public void setInOrderRequestOrderFids(String inOrderRequestOrderFids) {
		this.inOrderRequestOrderFids = inOrderRequestOrderFids;
	}

	public BigDecimal getInOrderOtherFee() {
		return inOrderOtherFee;
	}

	public void setInOrderOtherFee(BigDecimal inOrderOtherFee) {
		this.inOrderOtherFee = inOrderOtherFee;
	}

	public Boolean getInOrderRepealFlag() {
		return inOrderRepealFlag;
	}

	public void setInOrderRepealFlag(Boolean inOrderRepealFlag) {
		this.inOrderRepealFlag = inOrderRepealFlag;
	}

	public String getInOrderBillNo() {
		return inOrderBillNo;
	}

	public void setInOrderBillNo(String inOrderBillNo) {
		this.inOrderBillNo = inOrderBillNo;
	}

	public Integer getInOrderPrintCount() {
		return inOrderPrintCount;
	}

	public void setInOrderPrintCount(Integer inOrderPrintCount) {
		this.inOrderPrintCount = inOrderPrintCount;
	}

	public String getInOrderFid() {
		return inOrderFid;
	}

	public void setInOrderFid(String inOrderFid) {
		this.inOrderFid = inOrderFid;
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

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getInSystemBookCode() {
		return inSystemBookCode;
	}

	public void setInSystemBookCode(String inSystemBookCode) {
		this.inSystemBookCode = inSystemBookCode;
	}

	public Integer getInBranchNum() {
		return inBranchNum;
	}

	public void setInBranchNum(Integer inBranchNum) {
		this.inBranchNum = inBranchNum;
	}

	public String getOutOrderFid() {
		return outOrderFid;
	}

	public void setOutOrderFid(String outOrderFid) {
		this.outOrderFid = outOrderFid;
	}

	public Date getInOrderDate() {
		return inOrderDate;
	}

	public void setInOrderDate(Date inOrderDate) {
		this.inOrderDate = inOrderDate;
	}

	public String getInOrderOperator() {
		return inOrderOperator;
	}

	public void setInOrderOperator(String inOrderOperator) {
		this.inOrderOperator = inOrderOperator;
	}

	public String getInOrderMemo() {
		return inOrderMemo;
	}

	public void setInOrderMemo(String inOrderMemo) {
		this.inOrderMemo = inOrderMemo;
	}

	public Boolean getInOrderTransferFlag() {
		return inOrderTransferFlag;
	}

	public void setInOrderTransferFlag(Boolean inOrderTransferFlag) {
		this.inOrderTransferFlag = inOrderTransferFlag;
	}

	public String getInOrderCreator() {
		return inOrderCreator;
	}

	public void setInOrderCreator(String inOrderCreator) {
		this.inOrderCreator = inOrderCreator;
	}

	public String getInOrderAuditor() {
		return inOrderAuditor;
	}

	public void setInOrderAuditor(String inOrderAuditor) {
		this.inOrderAuditor = inOrderAuditor;
	}

	public String getInOrderUuid() {
		return inOrderUuid;
	}

	public void setInOrderUuid(String inOrderUuid) {
		this.inOrderUuid = inOrderUuid;
	}

	public BigDecimal getInOrderTotalMoney() {
		return inOrderTotalMoney;
	}

	public void setInOrderTotalMoney(BigDecimal inOrderTotalMoney) {
		this.inOrderTotalMoney = inOrderTotalMoney;
	}

	public BigDecimal getInOrderDiscountMoney() {
		return inOrderDiscountMoney;
	}

	public void setInOrderDiscountMoney(BigDecimal inOrderDiscountMoney) {
		if(inOrderDiscountMoney != null){
			inOrderDiscountMoney = inOrderDiscountMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.inOrderDiscountMoney = inOrderDiscountMoney;
	}

	public BigDecimal getInOrderDueMoney() {
		return inOrderDueMoney;
	}

	public void setInOrderDueMoney(BigDecimal inOrderDueMoney) {
		if(inOrderDueMoney != null){
			inOrderDueMoney = inOrderDueMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.inOrderDueMoney = inOrderDueMoney;
	}

	public BigDecimal getInOrderPaidMoney() {
		return inOrderPaidMoney;
	}

	public void setInOrderPaidMoney(BigDecimal inOrderPaidMoney) {
		if(inOrderPaidMoney != null){
			inOrderPaidMoney = inOrderPaidMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.inOrderPaidMoney = inOrderPaidMoney;
	}

	public Date getInOrderLastestPaymentDate() {
		return inOrderLastestPaymentDate;
	}

	public void setInOrderLastestPaymentDate(Date inOrderLastestPaymentDate) {
		this.inOrderLastestPaymentDate = inOrderLastestPaymentDate;
	}

	public Date getInOrderPaymentDate() {
		return inOrderPaymentDate;
	}

	public void setInOrderPaymentDate(Date inOrderPaymentDate) {
		this.inOrderPaymentDate = inOrderPaymentDate;
	}

	public Date getInOrderCreateTime() {
		return inOrderCreateTime;
	}

	public void setInOrderCreateTime(Date inOrderCreateTime) {
		this.inOrderCreateTime = inOrderCreateTime;
	}

	public Date getInOrderAuditTime() {
		return inOrderAuditTime;
	}

	public void setInOrderAuditTime(Date inOrderAuditTime) {
		this.inOrderAuditTime = inOrderAuditTime;

	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<InOrderDetail> getInOrderDetails() {
		return inOrderDetails;
	}

	public void setInOrderDetails(List<InOrderDetail> inOrderDetails) {
		this.inOrderDetails = inOrderDetails;
	}
	
	public InOrderDetail getInOrderDetail(Integer itemNum, Integer inOrderDetailItemMatrixNum){
		for(int i = 0;i < inOrderDetails.size();i++){
			InOrderDetail inOrderDetail = inOrderDetails.get(i);
			if(inOrderDetail.getItemNum().equals(itemNum) && inOrderDetail.getInOrderDetailItemMatrixNum().equals(inOrderDetailItemMatrixNum)){
				return inOrderDetail;
			}
		}
		return null;
		
	}

	public void removeZeroDetail(){
		for(int i = inOrderDetails.size() - 1;i >= 0;i--){
			InOrderDetail detail = inOrderDetails.get(i);
			if(detail.getInOrderDetailPresentQty() == null){
				detail.setInOrderDetailPresentQty(BigDecimal.ZERO);
			}
			
			if(detail.getInOrderDetailQty().compareTo(BigDecimal.ZERO) == 0 
					&& detail.getInOrderDetailPresentQty().compareTo(BigDecimal.ZERO) == 0){
				inOrderDetails.remove(i);
			}
		}
	}
	
	public static TransferInOrder get(List<TransferInOrder> transferInOrders, String inOrderFid){
		
		for(int i = 0;i < transferInOrders.size();i++){
			TransferInOrder transferInOrder = transferInOrders.get(i);
			if(transferInOrder.getInOrderFid().equals(inOrderFid)){
				return transferInOrder;
			}
			
		}
		return null;
		
	}
	
	public static TransferInOrder getByOutOrderFid(List<TransferInOrder> transferInOrders, String outOrderFid){
		
		for(int i = 0;i < transferInOrders.size();i++){
			TransferInOrder transferInOrder = transferInOrders.get(i);
			if(transferInOrder.getOutOrderFid().equals(outOrderFid)){
				return transferInOrder;
			}
			
		}
		return null;
		
	}
	
	public void recal(){
		inOrderTotalMoney = BigDecimal.ZERO;		
		inOrderOtherFee = BigDecimal.ZERO;		
		for(int i = inOrderDetails.size() - 1;i >= 0;i--){
			InOrderDetail detail = inOrderDetails.get(i);
			
			if(detail.getInOrderDetailUseQty().compareTo(BigDecimal.ZERO) != 0){
				detail.setInOrderDetailSaleSubtotal(detail.getInOrderDetailUsePrice().multiply(detail.getInOrderDetailUseQty()).setScale(2, BigDecimal.ROUND_HALF_UP));
				detail.setInOrderDetailQty(detail.getInOrderDetailUseQty().multiply(detail.getInOrderDetailUseRate()));
				
			}
			
			if(detail.getInOrderDetailQty().compareTo(BigDecimal.ZERO) > 0){
				detail.setInOrderDetailPrice(detail.getInOrderDetailSaleSubtotal().divide(detail.getInOrderDetailQty(), 4, BigDecimal.ROUND_HALF_UP));
			}
			if(detail.getInOrderDetailPresentQty() == null){
				detail.setInOrderDetailPresentQty(BigDecimal.ZERO);
			}
			inOrderTotalMoney = inOrderTotalMoney.add(detail.getInOrderDetailSaleSubtotal());
			
			if(detail.getInOrderDetailOtherFee() == null){
				detail.setInOrderDetailOtherFee(BigDecimal.ZERO);
			}
			inOrderOtherFee = inOrderOtherFee.add(detail.getInOrderDetailOtherFee());
					
		}
		inOrderOtherFee = inOrderOtherFee.setScale(2, BigDecimal.ROUND_HALF_UP);
		inOrderTotalMoney = inOrderTotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		inOrderDueMoney = inOrderTotalMoney.negate();
	}

}