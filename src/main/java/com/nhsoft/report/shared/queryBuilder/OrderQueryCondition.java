package com.nhsoft.report.shared.queryBuilder;


import com.nhsoft.report.shared.State;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderQueryCondition implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2378343498739563873L;
	private String systemBookCode;
	private Date dateStart;
	private Date dateEnd;
	private String fid;
	private BigDecimal money;
	private Integer supplierNum;
	private String operator;
	private State state;
	private List<Integer> stateCodes;
	private Integer toAccountNum;
	private Integer fromAccountNum;
	private Integer storehouseNum;
	private Integer toStorehouseNum;//调入仓库
	private String clientNum;
	private String dateType;
	private String paymentType;
	private Boolean stateInit;
	private Boolean stateAudit;
	private Boolean statePick;
	private Boolean stateSend;
	private String shipOrderDeliver;
	private String memo;
	private List<Integer> regionNums;
	private List<String> clientFids;
	private String innerNo; //手工单号
	private Integer printCountLessThen;//打印次数小于
	private boolean withDetails = false;
	private String creater;//创建人
	private String bookOrderState;//批发订单状态
	private List<Integer> toBranchNums;
	private boolean filterRedOrder = false; //是否过滤红单和被冲红单
	private String auditor; //审核人
	private Integer transferLineNum;
	private List<Integer> filterBranchNums;
	private List<Integer> receiveBranchNums; //采购单收货分店
	private Boolean queryUnPick; //查询未配货的单据
	private Boolean queryUnIn; //查询未调入的单据
	private Boolean queryReceive; //只查询已收货的单据
	private String orderFid;
	private Boolean plusFlag;
	private String label;
	private Boolean containLabel = true;
	private List<Integer> itemNums;
	private Date lastAuditTime;//最后审核日期
	private Boolean queryUnOut; //查询未生成调出单的单据
	private Boolean queryRequestOrders;
	
	
	
	private String sortField;
	private String sortType;
	private boolean isPaging = true;
	private Boolean ignoreDetails;
	
	public Boolean getQueryRequestOrders() {
		return queryRequestOrders;
	}
	
	public void setQueryRequestOrders(Boolean queryRequestOrders) {
		this.queryRequestOrders = queryRequestOrders;
	}
	
	public Boolean getIgnoreDetails() {
		return ignoreDetails;
	}

	public void setIgnoreDetails(Boolean ignoreDetails) {
		this.ignoreDetails = ignoreDetails;
	}

	public Boolean getQueryUnOut() {
		return queryUnOut;
	}

	public void setQueryUnOut(Boolean queryUnOut) {
		this.queryUnOut = queryUnOut;
	}

	public Date getLastAuditTime() {
		return lastAuditTime;
	}

	public void setLastAuditTime(Date lastAuditTime) {
		this.lastAuditTime = lastAuditTime;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public Boolean getContainLabel() {
		return containLabel;
	}

	public void setContainLabel(Boolean containLabel) {
		this.containLabel = containLabel;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getPlusFlag() {
		return plusFlag;
	}

	public void setPlusFlag(Boolean plusFlag) {
		this.plusFlag = plusFlag;
	}

	public String getOrderFid() {
		return orderFid;
	}

	public void setOrderFid(String orderFid) {
		this.orderFid = orderFid;
	}

	public Boolean getQueryUnIn() {
		return queryUnIn;
	}

	public void setQueryUnIn(Boolean queryUnIn) {
		this.queryUnIn = queryUnIn;
	}

	public Boolean getQueryReceive() {
		return queryReceive;
	}

	public void setQueryReceive(Boolean queryReceive) {
		this.queryReceive = queryReceive;
	}

	public Boolean getQueryUnPick() {
		return queryUnPick;
	}

	public void setQueryUnPick(Boolean queryUnPick) {
		this.queryUnPick = queryUnPick;
	}

	public List<Integer> getReceiveBranchNums() {
		return receiveBranchNums;
	}

	public void setReceiveBranchNums(List<Integer> receiveBranchNums) {
		this.receiveBranchNums = receiveBranchNums;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public List<Integer> getFilterBranchNums() {
		return filterBranchNums;
	}

	public void setFilterBranchNums(List<Integer> filterBranchNums) {
		this.filterBranchNums = filterBranchNums;
	}

	public Integer getTransferLineNum() {
		return transferLineNum;
	}

	public void setTransferLineNum(Integer transferLineNum) {
		this.transferLineNum = transferLineNum;
	}

	public Integer getToStorehouseNum() {
		return toStorehouseNum;
	}

	public void setToStorehouseNum(Integer toStorehouseNum) {
		this.toStorehouseNum = toStorehouseNum;
	}

	public List<Integer> getToBranchNums() {
		return toBranchNums;
	}

	public void setToBranchNums(List<Integer> toBranchNums) {
		this.toBranchNums = toBranchNums;
	}

	public String getBookOrderState() {
		return bookOrderState;
	}

	public void setBookOrderState(String bookOrderState) {
		this.bookOrderState = bookOrderState;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public List<Integer> getRegionNums() {
		return regionNums;
	}

	public void setRegionNums(List<Integer> regionNums) {
		this.regionNums = regionNums;
	}
	
	public Date getDateStart() {
		return dateStart;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public Integer getToAccountNum() {
		return toAccountNum;
	}

	public void setToAccountNum(Integer toAccountNum) {
		this.toAccountNum = toAccountNum;
	}
	
	public Integer getFromAccountNum() {
		return fromAccountNum;
	}

	public void setFromAccountNum(Integer fromAccountNum) {
		this.fromAccountNum = fromAccountNum;
	}
	
	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public boolean isvalidData(){
		if ((dateStart != null) && (dateEnd != null) && (dateEnd.getYear() > 0) && (dateStart.getYear() > 0)){
			return true;
		}
		return false;
	}

	public String getClientNum() {
		return clientNum;
	}

	public void setClientNum(String clientNum) {
		this.clientNum = clientNum;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Boolean getStateInit() {
		return stateInit;
	}

	public void setStateInit(Boolean stateInit) {
		this.stateInit = stateInit;
	}

	public Boolean getStateAudit() {
		return stateAudit;
	}

	public void setStateAudit(Boolean stateAudit) {
		this.stateAudit = stateAudit;
	}

	public Boolean getStatePick() {
		return statePick;
	}

	public void setStatePick(Boolean statePick) {
		this.statePick = statePick;
	}

	public Boolean getStateSend() {
		return stateSend;
	}

	public void setStateSend(Boolean stateSend) {
		this.stateSend = stateSend;
	}

	public String getShipOrderDeliver() {
		return shipOrderDeliver;
	}

	public void setShipOrderDeliver(String shipOrderDeliver) {
		this.shipOrderDeliver = shipOrderDeliver;
	}

	public List<String> getClientFids() {
		return clientFids;
	}

	public void setClientFids(List<String> clientFids) {
		this.clientFids = clientFids;
	}

	public Integer getPrintCountLessThen() {
		return printCountLessThen;
	}

	public void setPrintCountLessThen(Integer printCountLessThen) {
		this.printCountLessThen = printCountLessThen;
	}

	public String getInnerNo() {
		return innerNo;
	}

	public void setInnerNo(String innerNo) {
		this.innerNo = innerNo;
	}

	public boolean isWithDetails() {
		return withDetails;
	}

	public void setWithDetails(boolean withDetails) {
		this.withDetails = withDetails;
	}

	public boolean isFilterRedOrder() {
		return filterRedOrder;
	}

	public void setFilterRedOrder(boolean filterRedOrder) {
		this.filterRedOrder = filterRedOrder;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public List<Integer> getStateCodes() {
		return stateCodes;
	}

	public void setStateCodes(List<Integer> stateCodes) {
		this.stateCodes = stateCodes;
	}

}
