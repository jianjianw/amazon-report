package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 待配货订单
 * @author Administrator
 *
 */
public class ToPicking implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1585144919483091235L;
	private String orderNo;									//订单号
	private String orderType;									//订单类型
	private Integer branchNum;
	private String shipBranch;								//收货分店
	private String shipClient;									//收货客户	
	private String orderState;								//状态
	private String settlementState;						//结算状态
	private BigDecimal shipOrderTotalMoeny;		//单据总额
	private String storehouseName;						//出货仓库
	private Integer storehouseNum;
	private String orderCreator;								//制单人
	private Date orderCreateTime;							//制单时间
	private String  shipOrderAuditor;						//审核人
	private Date shipOrderAuditTime;					//审核时间
	private String memo;										//备注
	private String clientFid;									//客户主键
	
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getStorehouseName() {
		return storehouseName;
	}
	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getShipBranch() {
		return shipBranch;
	}
	public void setShipBranch(String shipBranch) {
		this.shipBranch = shipBranch;
	}
	public String getShipClient() {
		return shipClient;
	}
	public void setShipClient(String shipClient) {
		this.shipClient = shipClient;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getSettlementState() {
		return settlementState;
	}
	public void setSettlementState(String settlementState) {
		this.settlementState = settlementState;
	}
	public String getOrderCreator() {
		return orderCreator;
	}
	public void setOrderCreator(String orderCreator) {
		this.orderCreator = orderCreator;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public BigDecimal getShipOrderTotalMoeny() {
		return shipOrderTotalMoeny;
	}
	public void setShipOrderTotalMoeny(BigDecimal shipOrderTotalMoeny) {
		this.shipOrderTotalMoeny = shipOrderTotalMoeny;
	}
	public String getShipOrderAuditor() {
		return shipOrderAuditor;
	}
	public void setShipOrderAuditor(String shipOrderAuditor) {
		this.shipOrderAuditor = shipOrderAuditor;
	}
	public Date getShipOrderAuditTime() {
		return shipOrderAuditTime;
	}
	public void setShipOrderAuditTime(Date shipOrderAuditTime) {
		this.shipOrderAuditTime = shipOrderAuditTime;
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

	public String getClientFid() {
		return clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}
}
