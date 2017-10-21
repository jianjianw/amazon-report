package com.nhsoft.module.report.query;


import com.nhsoft.module.report.dto.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OnlineOrderQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1926242425931281506L;
	private List<Integer> branchNums;
	private Date dateFrom; // 付款时间
	private Date dateTo;
	private String onlineOrderNo;
	private String weixingFid;// 微信订单号
	private String onlineOrderClientNick;// 微信昵称
	private String receiverName;
	private String receiverPhone;
	private List<State> states; // 单据状态
	private Boolean handled; //是否处理
	private String mathFalg;
	private BigDecimal paymentMoney;
	private String dateType; //时间类型
	private String province; //省
	private String city; //市
	private String area; //区
	private String openId;
	private Boolean teamOrder; //是否团购订单
	private String weixinItemName;
	private String payType;
	private String deliveryType;
	private List<Integer> itemNums;
	private List<Integer> weixinItemNums;
	private String onlineOrderSource;
	private boolean queryDetails = false; //是否查询明细
	private boolean queryOrderDetails = false; //是否查询明细
	private String sortField;
	private String sortType;
	private String eqOnlineOrderNo;
	private Integer offset;
	private Integer limit;
	private String onlineOrderFid;
	
	public String getOnlineOrderFid() {
		return onlineOrderFid;
	}
	
	public void setOnlineOrderFid(String onlineOrderFid) {
		this.onlineOrderFid = onlineOrderFid;
	}
	
	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getEqOnlineOrderNo() {
		return eqOnlineOrderNo;
	}

	public void setEqOnlineOrderNo(String eqOnlineOrderNo) {
		this.eqOnlineOrderNo = eqOnlineOrderNo;
	}

	public String getOnlineOrderSource() {
		return onlineOrderSource;
	}

	public void setOnlineOrderSource(String onlineOrderSource) {
		this.onlineOrderSource = onlineOrderSource;
	}

	public boolean isQueryDetails() {
		return queryDetails;
	}

	public void setQueryDetails(boolean queryDetails) {
		this.queryDetails = queryDetails;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public List<Integer> getWeixinItemNums() {
		return weixinItemNums;
	}

	public void setWeixinItemNums(List<Integer> weixinItemNums) {
		this.weixinItemNums = weixinItemNums;
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

	public String getWeixinItemName() {
		return weixinItemName;
	}

	public void setWeixinItemName(String weixinItemName) {
		this.weixinItemName = weixinItemName;
	}

	public Boolean getTeamOrder() {
		return teamOrder;
	}

	public void setTeamOrder(Boolean teamOrder) {
		this.teamOrder = teamOrder;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getOnlineOrderNo() {
		return onlineOrderNo;
	}

	public void setOnlineOrderNo(String onlineOrderNo) {
		this.onlineOrderNo = onlineOrderNo;
	}

	public String getWeixingFid() {
		return weixingFid;
	}

	public void setWeixingFid(String weixingFid) {
		this.weixingFid = weixingFid;
	}

	public String getOnlineOrderClientNick() {
		return onlineOrderClientNick;
	}

	public void setOnlineOrderClientNick(String onlineOrderClientNick) {
		this.onlineOrderClientNick = onlineOrderClientNick;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public String getMathFalg() {
		return mathFalg;
	}

	public void setMathFalg(String mathFalg) {
		this.mathFalg = mathFalg;
	}

	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public boolean isQueryOrderDetails() {
		return queryOrderDetails;
	}

	public void setQueryOrderDetails(boolean queryOrderDetails) {
		this.queryOrderDetails = queryOrderDetails;
	}

	public Boolean getHandled() {
		return handled;
	}

	public void setHandled(Boolean handled) {
		this.handled = handled;
	}

	@Override
	public boolean checkQueryBuild() {
		
		return false;
	}

}
