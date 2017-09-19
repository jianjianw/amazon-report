package com.nhsoft.report.model;


import com.nhsoft.report.dto.GsonIgnore;
import com.nhsoft.report.shared.State;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * RequestOrder entity. @author MyEclipse Persistence Tools
 */

public class RequestOrder implements java.io.Serializable {

	private static final long serialVersionUID = 7592986647652749128L;
	private String requestOrderFid;
	private String systemBookCode;
	private Integer branchNum;
	private String outSystemBookCode;
	private Integer outBranchNum;
	private String requestOrderProposer;
	private Date requestOrderApplyTime;
	private Date requestOrderDeadline;
	private State state;
	private String requestOrderMemo;
	private Boolean requestOrderTransferFlag;
	private String requestOrderCreator;
	private String requestOrderAuditor;
	private String requestOrderUuid;
	private BigDecimal requestOrderTotalMoney;
	private Date requestOrderCreateTime;
	private Date requestOrderAuditTime;
	private BigDecimal requestOrderPreMoney;
	private Date requestOrderOutDate;
	private Date requestOrderPickDate;
	private Date requestOrderSendDate;
	private Date requestOrderInDate;
	private String requestOrderRefBillNo;
	private Date requestOrderLastEditTime;
	private String requestOrderTransferState;
	private String requestOrderAuditBizday;
	private Integer requestOrderPrintCount;
	private List<RequestOrderDetail> requestOrderDetails = new ArrayList<RequestOrderDetail>();
	
	@GsonIgnore
	private List<TransferOutOrder> transferOutOrders = new ArrayList<TransferOutOrder>();
	private String itemCategoryCode;
	
	//临时属性
	@GsonIgnore
	private AppUser appUser;
	private String copyFid;
	private String source;//单据来源
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public Integer getRequestOrderPrintCount() {
		return requestOrderPrintCount;
	}

	public void setRequestOrderPrintCount(Integer requestOrderPrintCount) {
		this.requestOrderPrintCount = requestOrderPrintCount;
	}

	public String getRequestOrderAuditBizday() {
		return requestOrderAuditBizday;
	}

	public void setRequestOrderAuditBizday(String requestOrderAuditBizday) {
		this.requestOrderAuditBizday = requestOrderAuditBizday;
	}

	public String getRequestOrderTransferState() {
		return requestOrderTransferState;
	}

	public void setRequestOrderTransferState(String requestOrderTransferState) {
		this.requestOrderTransferState = requestOrderTransferState;
	}

	public Date getRequestOrderLastEditTime() {
		return requestOrderLastEditTime;
	}

	public void setRequestOrderLastEditTime(Date requestOrderLastEditTime) {
		this.requestOrderLastEditTime = requestOrderLastEditTime;
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

	public String getRequestOrderRefBillNo() {
		return requestOrderRefBillNo;
	}

	public void setRequestOrderRefBillNo(String requestOrderRefBillNo) {
		this.requestOrderRefBillNo = requestOrderRefBillNo;
	}

	public String getRequestOrderFid() {
		return requestOrderFid;
	}

	public void setRequestOrderFid(String requestOrderFid) {
		this.requestOrderFid = requestOrderFid;
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

	public String getRequestOrderProposer() {
		return requestOrderProposer;
	}

	public void setRequestOrderProposer(String requestOrderProposer) {
		this.requestOrderProposer = requestOrderProposer;
	}

	public Date getRequestOrderApplyTime() {
		return requestOrderApplyTime;
	}

	public void setRequestOrderApplyTime(Date requestOrderApplyTime) {
		this.requestOrderApplyTime = requestOrderApplyTime;
	}

	public Date getRequestOrderDeadline() {
		return requestOrderDeadline;
	}

	public void setRequestOrderDeadline(Date requestOrderDeadline) {
		this.requestOrderDeadline = requestOrderDeadline;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getRequestOrderMemo() {
		return requestOrderMemo;
	}

	public void setRequestOrderMemo(String requestOrderMemo) {
		this.requestOrderMemo = requestOrderMemo;
	}

	public Boolean getRequestOrderTransferFlag() {
		return requestOrderTransferFlag;
	}

	public void setRequestOrderTransferFlag(Boolean requestOrderTransferFlag) {
		this.requestOrderTransferFlag = requestOrderTransferFlag;
	}

	public String getRequestOrderCreator() {
		return requestOrderCreator;
	}

	public void setRequestOrderCreator(String requestOrderCreator) {
		this.requestOrderCreator = requestOrderCreator;
	}

	public String getRequestOrderAuditor() {
		return requestOrderAuditor;
	}

	public void setRequestOrderAuditor(String requestOrderAuditor) {
		this.requestOrderAuditor = requestOrderAuditor;
	}

	public String getRequestOrderUuid() {
		return requestOrderUuid;
	}

	public void setRequestOrderUuid(String requestOrderUuid) {
		this.requestOrderUuid = requestOrderUuid;
	}

	public BigDecimal getRequestOrderTotalMoney() {
		return requestOrderTotalMoney;
	}

	public void setRequestOrderTotalMoney(BigDecimal requestOrderTotalMoney) {
		this.requestOrderTotalMoney = requestOrderTotalMoney;
	}

	public Date getRequestOrderCreateTime() {
		return requestOrderCreateTime;
	}

	public void setRequestOrderCreateTime(Date requestOrderCreateTime) {
		this.requestOrderCreateTime = requestOrderCreateTime;
	}

	public Date getRequestOrderAuditTime() {
		return requestOrderAuditTime;
	}

	public void setRequestOrderAuditTime(Date requestOrderAuditTime) {
		this.requestOrderAuditTime = requestOrderAuditTime;

	}

	public List<RequestOrderDetail> getRequestOrderDetails() {
		return requestOrderDetails;
	}

	public void setRequestOrderDetails(
			List<RequestOrderDetail> requestOrderDetails) {
		this.requestOrderDetails = requestOrderDetails;
	}
	
	public List<TransferOutOrder> getTransferOutOrders() {
		return transferOutOrders;
	}

	public void setTransferOutOrders(List<TransferOutOrder> transferOutOrders) {
		this.transferOutOrders = transferOutOrders;
	}

	public BigDecimal getRequestOrderPreMoney() {
		return requestOrderPreMoney;
	}

	public void setRequestOrderPreMoney(BigDecimal requestOrderPreMoney) {
		this.requestOrderPreMoney = requestOrderPreMoney;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public Date getRequestOrderOutDate() {
		return requestOrderOutDate;
	}

	public void setRequestOrderOutDate(Date requestOrderOutDate) {
		this.requestOrderOutDate = requestOrderOutDate;
	}

	public Date getRequestOrderPickDate() {
		return requestOrderPickDate;
	}

	public void setRequestOrderPickDate(Date requestOrderPickDate) {
		this.requestOrderPickDate = requestOrderPickDate;
	}

	public Date getRequestOrderSendDate() {
		return requestOrderSendDate;
	}

	public void setRequestOrderSendDate(Date requestOrderSendDate) {
		this.requestOrderSendDate = requestOrderSendDate;
	}

	public Date getRequestOrderInDate() {
		return requestOrderInDate;
	}

	public void setRequestOrderInDate(Date requestOrderInDate) {
		this.requestOrderInDate = requestOrderInDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((requestOrderFid == null) ? 0 : requestOrderFid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestOrder other = (RequestOrder) obj;
		if (requestOrderFid == null) {
			if (other.requestOrderFid != null)
				return false;
		} else if (!requestOrderFid.equals(other.requestOrderFid))
			return false;
		return true;
	}
	
	public BigDecimal calcMoney() {
		BigDecimal total = BigDecimal.ZERO;
		for (int i = 0; i < requestOrderDetails.size(); i++) {
			RequestOrderDetail detail = requestOrderDetails.get(i);
			//detail.setRequestOrderDetailSubtotal(requestOrderDetailSubtotal);
			total = total.add(detail.getRequestOrderDetailSubtotal());
		}
		return total;
	}
	
	public void recal() {
		requestOrderTotalMoney = BigDecimal.ZERO;
		for (int i = requestOrderDetails.size() - 1; i >= 0; i--) {
			RequestOrderDetail detail = requestOrderDetails.get(i);
			detail.setRequestOrderDetailSubtotal(detail.getRequestOrderDetailUseQty().multiply(detail.getRequestOrderDetailUsePrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
			detail.setRequestOrderDetailQty(detail.getRequestOrderDetailUseQty().multiply(detail.getRequestOrderDetailUseRate()));
			if(detail.getRequestOrderDetailQty().compareTo(BigDecimal.ZERO) > 0){
				detail.setRequestOrderDetailCost(detail.getRequestOrderDetailSubtotal().divide(detail.getRequestOrderDetailQty(), 4, BigDecimal.ROUND_HALF_UP));
			}
			if(detail.getRequestOrderDetailOutQty() == null){
				detail.setRequestOrderDetailOutQty(BigDecimal.ZERO);
			}
			if(detail.getRequestOrderDetailPurchaseQty() == null){
				detail.setRequestOrderDetailPurchaseQty(BigDecimal.ZERO);
			}
			requestOrderTotalMoney = requestOrderTotalMoney.add(detail.getRequestOrderDetailSubtotal());
		}
		requestOrderTotalMoney = requestOrderTotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void removeZeroDetail() {
		for (int i = requestOrderDetails.size() - 1; i >= 0; i--) {
			RequestOrderDetail detail = requestOrderDetails.get(i);
			if (detail.getRequestOrderDetailQty().compareTo(BigDecimal.ZERO) == 0) {
				requestOrderDetails.remove(i);
			}
		}
		
	}

	public RequestOrderDetail getRequestOrderDetail(Integer itemNum, Integer itemMatrixNum){
		for(int i = 0;i < requestOrderDetails.size();i++){
			RequestOrderDetail requestOrderDetail = requestOrderDetails.get(i);
			if(requestOrderDetail.getRequestOrderDetailItemMatrixNum() == null){
				requestOrderDetail.setRequestOrderDetailItemMatrixNum(0);
			}
			if(requestOrderDetail.getItemNum().equals(itemNum) && requestOrderDetail.getRequestOrderDetailItemMatrixNum().equals(itemMatrixNum)){
				return requestOrderDetail;
			}
		}
		return null;
	}
	
	public static List<RequestOrderDetail> findDetails(List<RequestOrder> requestOrders, Integer itemNum, Integer itemMatrixNum){
		List<RequestOrderDetail> list = new ArrayList<RequestOrderDetail>();
		for(int i = 0;i < requestOrders.size();i++){
			RequestOrder requestOrder = requestOrders.get(i);
			List<RequestOrderDetail> requestOrderDetails = requestOrder.getRequestOrderDetails();
			for(int j = 0;j < requestOrderDetails.size();j++){
				RequestOrderDetail requestOrderDetail = requestOrderDetails.get(j);
				
				if (requestOrderDetail.getRequestOrderDetailItemMatrixNum() == null) {
					requestOrderDetail.setRequestOrderDetailItemMatrixNum(0);
				}
				if (requestOrderDetail.getItemNum().equals(itemNum)
						&& requestOrderDetail.getRequestOrderDetailItemMatrixNum().equals(itemMatrixNum)) {
					list.add(requestOrderDetail);
					
				}
			}
		}
		return list;
	}
	
	
	public String getRequestState() {
		boolean isHaveOut = false;
		boolean isAllOut = true;
		boolean isAllReceive = true;
		BigDecimal requestQty;
		BigDecimal outQty;
		BigDecimal receivedQty;
		for (int i = 0; i < requestOrderDetails.size(); i++) {
			RequestOrderDetail detail = requestOrderDetails.get(i);
			requestQty = detail.getRequestOrderDetailQty();
			outQty = BigDecimal.ZERO;
			receivedQty = BigDecimal.ZERO;
			if (detail.getRequestOrderDetailOutQty() == null){
				detail.setRequestOrderDetailOutQty(BigDecimal.ZERO);
			}
			if (detail.getRequestOrderDetailOutReceivedQty() == null){
				detail.setRequestOrderDetailOutReceivedQty(BigDecimal.ZERO);
			}
			outQty = outQty.add(detail.getRequestOrderDetailOutQty());
			receivedQty = receivedQty.add(detail.getRequestOrderDetailOutReceivedQty());
			if (requestQty.compareTo(receivedQty) > 0){
				isAllReceive = false;
			}
			if (requestQty.compareTo(outQty) > 0){
				isAllOut = false;
			}
			if (outQty.compareTo(BigDecimal.ZERO) != 0){
				isHaveOut = true;
			}
		}
		if (isAllReceive){
			return "全部收货";
		}else if (isAllOut){
			return "全部发货";
		}else if (!isHaveOut){
			return "待处理";
		}		
		return "部分发货";
	}
	
	public static RequestOrder getRequestOrderByCategory(List<RequestOrder> requestOrders, String itemCategoryCode) {
		for (int i = 0; i < requestOrders.size(); i++) {
			RequestOrder requestOrder = requestOrders.get(i);
			if (requestOrder.getItemCategoryCode().equals(itemCategoryCode)) {
				return requestOrder;
			}

		}
		return null;
	}
	
	private String orderNo;
	private Long storeId;
	private String proposer;
	private Date applyTime;
	private Date deadLine;
	private Integer status;
	private Long totalFee;
	private Long prePayment;
	private Date pickTime;
	private Date sendTime;
	private Date receiveTime;
	private String memo;
}