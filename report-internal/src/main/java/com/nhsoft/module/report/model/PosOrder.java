package com.nhsoft.module.report.model;

import com.nhsoft.module.report.dto.GsonIgnore;
import com.nhsoft.module.report.dto.TypeAndTwoValuesDTO;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PosOrder generated by hbm2java
 */
public class PosOrder implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(PosOrder.class);
	private static final long serialVersionUID = 1598009540157242167L;
	private String orderNo;
	private String layawayOrderNo;
	private String clientFid;
	private Integer storehouseNum;
	private String systemBookCode;
	private Integer branchNum;
	private Integer shiftTableNum;
	private String shiftTableBizday;
	private Date orderDate;
	private String orderSoldBy;
	private String orderOperator;
	private Date orderOperateTime;
	private Boolean orderFlag;
	private String orderPrintedNum;
	private String orderCardUser;
	private String orderCardTypeDesc;
	private BigDecimal orderDiscountMoney;
	private BigDecimal orderCommission;
	private BigDecimal orderTotalMoney;
	private BigDecimal orderPaymentMoney;
	private BigDecimal orderRound;
	private BigDecimal orderBalance;
	private BigDecimal orderTotalInvoice;
	private BigDecimal orderChange;
	private Date orderTime;
	private String orderMachine;
	private String orderChangeAuditor;
	private Date orderChangeTime;
	private String orderPayee;
	private Integer orderStateCode;
	private String orderStateName;
	private String orderMemo;
	private String orderRefBillno;
	private BigDecimal orderPoint;
	private BigDecimal orderGrossProfit;
	private BigDecimal orderMgrDiscountMoney;
	private BigDecimal orderCouponTotalMoney;
	private BigDecimal orderCouponPaymentMoney;
	private Integer orderCardUserNum;
	private Integer orderCardType;
	private String orderSource;
	private BigDecimal orderPostFee;
	private BigDecimal orderPromotionDiscountMoney;
	private String orderExternalNo;
	private Integer orderDetailItemCount;
	private String orderTimeChar;
	private Boolean orderStockFlag;
	private String orderCardPhone;
	private BigDecimal orderCardChange;
	private BigDecimal orderTaxMoney;
	private String orderTmallMemo;
	private String orderUserGroup;
	private BigDecimal orderOnlineDiscount;
	private List<PosOrderDetail> posOrderDetails = new ArrayList<PosOrderDetail>(0);
	private List<Payment> payments = new ArrayList<Payment>(0);
	private List<InvoiceChange> invoiceChanges = new ArrayList<InvoiceChange>();

	//临时属性
	@GsonIgnore
	private AppUser appUser;
	@GsonIgnore
	private String paymentTypes;
	@GsonIgnore
	private PosClient posClient;
	@GsonIgnore
	private List<TypeAndTwoValuesDTO> typeAndTwoValuesDTOs = new ArrayList<TypeAndTwoValuesDTO>();
	@GsonIgnore
	private CardPosConsume cardPosConsume;
	@GsonIgnore
	private String orderMemberCode; //天猫会员编号
	private String reSendFid;
	
	public PosOrder() {
	}
	
	public BigDecimal getOrderOnlineDiscount() {
		return orderOnlineDiscount;
	}
	
	public void setOrderOnlineDiscount(BigDecimal orderOnlineDiscount) {
		this.orderOnlineDiscount = orderOnlineDiscount;
	}
	
	public String getOrderUserGroup() {
		return orderUserGroup;
	}

	public void setOrderUserGroup(String orderUserGroup) {
		this.orderUserGroup = orderUserGroup;
	}

	public String getReSendFid() {
		return reSendFid;
	}

	public void setReSendFid(String reSendFid) {
		this.reSendFid = reSendFid;
	}

	public BigDecimal getOrderTaxMoney() {
		return orderTaxMoney;
	}

	public void setOrderTaxMoney(BigDecimal orderTaxMoney) {
		this.orderTaxMoney = orderTaxMoney;
	}

	public String getOrderCardPhone() {
		return orderCardPhone;
	}

	public void setOrderCardPhone(String orderCardPhone) {
		this.orderCardPhone = orderCardPhone;
	}

	public CardPosConsume getCardPosConsume() {
		return cardPosConsume;
	}

	public void setCardPosConsume(CardPosConsume cardPosConsume) {
		this.cardPosConsume = cardPosConsume;
	}

	public Boolean getOrderStockFlag() {
		return orderStockFlag;
	}

	public void setOrderStockFlag(Boolean orderStockFlag) {
		this.orderStockFlag = orderStockFlag;
	}

	public String getOrderTimeChar() {
		return orderTimeChar;
	}

	public void setOrderTimeChar(String orderTimeChar) {
		this.orderTimeChar = orderTimeChar;
	}

	public Integer getOrderDetailItemCount() {
		return orderDetailItemCount;
	}

	public void setOrderDetailItemCount(Integer orderDetailItemCount) {
		this.orderDetailItemCount = orderDetailItemCount;
	}

	public String getOrderExternalNo() {
		return orderExternalNo;
	}

	public void setOrderExternalNo(String orderExternalNo) {
		this.orderExternalNo = orderExternalNo;
	}

	public BigDecimal getOrderPromotionDiscountMoney() {
		return orderPromotionDiscountMoney;
	}

	public void setOrderPromotionDiscountMoney(BigDecimal orderPromotionDiscountMoney) {
		this.orderPromotionDiscountMoney = orderPromotionDiscountMoney;
	}

	public List<TypeAndTwoValuesDTO> getTypeAndTwoValuesDTOs() {
		return typeAndTwoValuesDTOs;
	}

	public void setTypeAndTwoValuesDTOs(List<TypeAndTwoValuesDTO> typeAndTwoValuesDTOs) {
		this.typeAndTwoValuesDTOs = typeAndTwoValuesDTOs;
	}

	public PosClient getPosClient() {
		return posClient;
	}

	public void setPosClient(PosClient posClient) {
		this.posClient = posClient;
	}

	public String getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(String paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLayawayOrderNo() {
		return layawayOrderNo;
	}

	public void setLayawayOrderNo(String layawayOrderNo) {
		this.layawayOrderNo = layawayOrderNo;
	}

	public String getClientFid() {
		return this.clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public Integer getStorehouseNum() {
		return this.storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return this.branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getShiftTableNum() {
		return this.shiftTableNum;
	}

	public void setShiftTableNum(Integer shiftTableNum) {
		this.shiftTableNum = shiftTableNum;
	}

	public String getShiftTableBizday() {
		return this.shiftTableBizday;
	}

	public void setShiftTableBizday(String shiftTableBizday) {
		this.shiftTableBizday = shiftTableBizday;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderSoldBy() {
		return this.orderSoldBy;
	}

	public void setOrderSoldBy(String orderSoldBy) {
		this.orderSoldBy = orderSoldBy;
	}

	public String getOrderOperator() {
		return this.orderOperator;
	}

	public void setOrderOperator(String orderOperator) {
		this.orderOperator = orderOperator;
	}

	public Date getOrderOperateTime() {
		return this.orderOperateTime;
	}

	public void setOrderOperateTime(Date orderOperateTime) {
		this.orderOperateTime = orderOperateTime;
	}

	public String getOrderPrintedNum() {
		return this.orderPrintedNum;
	}

	public void setOrderPrintedNum(String orderPrintedNum) {
		this.orderPrintedNum = orderPrintedNum;
	}

	public String getOrderCardUser() {
		return this.orderCardUser;
	}

	public void setOrderCardUser(String orderCardUser) {
		this.orderCardUser = orderCardUser;
	}

	public String getOrderCardTypeDesc() {
		return this.orderCardTypeDesc;
	}

	public void setOrderCardTypeDesc(String orderCardTypeDesc) {
		this.orderCardTypeDesc = orderCardTypeDesc;
	}

	public BigDecimal getOrderDiscountMoney() {
		return this.orderDiscountMoney;
	}

	public void setOrderDiscountMoney(BigDecimal orderDiscountMoney) {
		this.orderDiscountMoney = orderDiscountMoney;
	}

	public BigDecimal getOrderCommission() {
		return this.orderCommission;
	}

	public void setOrderCommission(BigDecimal orderCommission) {
		this.orderCommission = orderCommission;
	}

	public BigDecimal getOrderTotalMoney() {
		return this.orderTotalMoney;
	}

	public void setOrderTotalMoney(BigDecimal orderTotalMoney) {
		this.orderTotalMoney = orderTotalMoney;
	}

	public BigDecimal getOrderPaymentMoney() {
		return this.orderPaymentMoney;
	}

	public void setOrderPaymentMoney(BigDecimal orderPaymentMoney) {
		this.orderPaymentMoney = orderPaymentMoney;
	}

	public BigDecimal getOrderRound() {
		return this.orderRound;
	}

	public void setOrderRound(BigDecimal orderRound) {
		this.orderRound = orderRound;
	}

	public BigDecimal getOrderBalance() {
		return this.orderBalance;
	}

	public void setOrderBalance(BigDecimal orderBalance) {
		this.orderBalance = orderBalance;
	}

	public BigDecimal getOrderTotalInvoice() {
		return this.orderTotalInvoice;
	}

	public void setOrderTotalInvoice(BigDecimal orderTotalInvoice) {
		this.orderTotalInvoice = orderTotalInvoice;
	}

	public BigDecimal getOrderChange() {
		return this.orderChange;
	}

	public void setOrderChange(BigDecimal orderChange) {
		this.orderChange = orderChange;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderMachine() {
		return this.orderMachine;
	}

	public void setOrderMachine(String orderMachine) {
		this.orderMachine = orderMachine;
	}

	public String getOrderChangeAuditor() {
		return this.orderChangeAuditor;
	}

	public void setOrderChangeAuditor(String orderChangeAuditor) {
		this.orderChangeAuditor = orderChangeAuditor;
	}

	public Date getOrderChangeTime() {
		return this.orderChangeTime;
	}

	public void setOrderChangeTime(Date orderChangeTime) {
		this.orderChangeTime = orderChangeTime;
	}

	public String getOrderPayee() {
		return this.orderPayee;
	}

	public void setOrderPayee(String orderPayee) {
		this.orderPayee = orderPayee;
	}

	public Integer getOrderStateCode() {
		return this.orderStateCode;
	}

	public void setOrderStateCode(Integer orderStateCode) {
		this.orderStateCode = orderStateCode;
	}

	public String getOrderStateName() {
		return this.orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public String getOrderMemo() {
		return this.orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	public String getOrderRefBillno() {
		return this.orderRefBillno;
	}

	public void setOrderRefBillno(String orderRefBillno) {
		this.orderRefBillno = orderRefBillno;
	}

	public BigDecimal getOrderPoint() {
		return this.orderPoint;
	}

	public void setOrderPoint(BigDecimal orderPoint) {
		this.orderPoint = orderPoint;
	}

	public BigDecimal getOrderGrossProfit() {
		return this.orderGrossProfit;
	}

	public void setOrderGrossProfit(BigDecimal orderGrossProfit) {
		this.orderGrossProfit = orderGrossProfit;
	}

	public BigDecimal getOrderMgrDiscountMoney() {
		return this.orderMgrDiscountMoney;
	}

	public void setOrderMgrDiscountMoney(BigDecimal orderMgrDiscountMoney) {
		this.orderMgrDiscountMoney = orderMgrDiscountMoney;
	}

	public BigDecimal getOrderCouponTotalMoney() {
		return this.orderCouponTotalMoney;
	}

	public void setOrderCouponTotalMoney(BigDecimal orderCouponTotalMoney) {
		this.orderCouponTotalMoney = orderCouponTotalMoney;
	}

	public BigDecimal getOrderCouponPaymentMoney() {
		return this.orderCouponPaymentMoney;
	}

	public void setOrderCouponPaymentMoney(BigDecimal orderCouponPaymentMoney) {
		this.orderCouponPaymentMoney = orderCouponPaymentMoney;
	}

	public Integer getOrderCardUserNum() {
		return this.orderCardUserNum;
	}

	public void setOrderCardUserNum(Integer orderCardUserNum) {
		this.orderCardUserNum = orderCardUserNum;
	}

	public Integer getOrderCardType() {
		return this.orderCardType;
	}

	public void setOrderCardType(Integer orderCardType) {
		this.orderCardType = orderCardType;
	}

	public Boolean getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(Boolean orderFlag) {
		this.orderFlag = orderFlag;
	}

	public List<PosOrderDetail> getPosOrderDetails() {
		return posOrderDetails;
	}

	public void setPosOrderDetails(List<PosOrderDetail> posOrderDetails) {
		this.posOrderDetails = posOrderDetails;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public BigDecimal getOrderPostFee() {
		return orderPostFee;
	}

	public void setOrderPostFee(BigDecimal orderPostFee) {
		this.orderPostFee = orderPostFee;
	}

	public BigDecimal getOrderCardChange() {
		return orderCardChange;
	}

	public void setOrderCardChange(BigDecimal orderCardChange) {
		this.orderCardChange = orderCardChange;
	}

	public List<InvoiceChange> getInvoiceChanges() {
		return invoiceChanges;
	}

	public void setInvoiceChanges(List<InvoiceChange> invoiceChanges) {
		this.invoiceChanges = invoiceChanges;
	}

	public String getOrderTmallMemo() {
		return orderTmallMemo;
	}

	public void setOrderTmallMemo(String orderTmallMemo) {
		this.orderTmallMemo = orderTmallMemo;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getOrderMemberCode() {
		return orderMemberCode;
	}

	public void setOrderMemberCode(String orderMemberCode) {
		this.orderMemberCode = orderMemberCode;
	}

	public static PosOrder readFromXml(String text){
		PosOrder posOrder = new PosOrder();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Element element = (Element)root.selectSingleNode("POS_ORDER");
			if(element != null){
				Element node = (Element) element.selectSingleNode("ORDER_NO");
				if(node != null){
					posOrder.setOrderNo(node.getText());
				}
				
				node = (Element) element.selectSingleNode("LAYAWAY_ORDER_NO");
				if(node != null){
					posOrder.setLayawayOrderNo(node.getText());
				}
				
				node = (Element) element.selectSingleNode("CLIENT_FID");
				if(node != null){
					posOrder.setClientFid(node.getText());
				}
				
				node = (Element) element.selectSingleNode("STOREHOUSE_NUM");
				if(node != null){
					posOrder.setStorehouseNum(Integer.parseInt(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("SYSTEM_BOOK_CODE");
				if(node != null){
					posOrder.setSystemBookCode(node.getText());
				}
				
				node = (Element) element.selectSingleNode("BRANCH_NUM");
				if(node != null){
					posOrder.setBranchNum(Integer.parseInt(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("SHIFT_TABLE_NUM");
				if(node != null){
					posOrder.setShiftTableNum(Integer.parseInt(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("SHIFT_TABLE_BIZDAY");
				if(node != null){
					posOrder.setShiftTableBizday(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_DATE");
				if(node != null){
					posOrder.setOrderDate(DateUtil.getXmlTDate(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_SOLD_BY");
				if(node != null){
					posOrder.setOrderSoldBy(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_OPERATOR");
				if(node != null){
					posOrder.setOrderOperator(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_OPERATOR_TIME");
				if(node != null){
					posOrder.setOrderOperateTime(DateUtil.getXmlTDate(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_FLAG");
				if(node != null){
					posOrder.setOrderFlag(BooleanUtils.toBoolean(node.getText(), "1", "0"));
				}
				
				node = (Element) element.selectSingleNode("ORDER_PRINTED_NUM");
				if(node != null){
					posOrder.setOrderPrintedNum(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_CARD_USER");
				if(node != null){
					posOrder.setOrderCardUser(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_CARD_TYPE_DESC");
				if(node != null){
					posOrder.setOrderCardTypeDesc(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_DISCOUNT_MONEY");
				if(node != null){
					posOrder.setOrderDiscountMoney(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_MGR_DISCOUNT_MONEY");
				if(node != null){
					posOrder.setOrderMgrDiscountMoney(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_PAYMENT_MONEY");
				if(node != null){
					posOrder.setOrderPaymentMoney(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_STATE_CODE");
				if(node != null){
					posOrder.setOrderStateCode(Integer.parseInt(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_STATE_NAME");
				if(node != null){
					posOrder.setOrderStateName(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_COMMISSION");
				if(node != null){
					posOrder.setOrderCommission(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_TOTAL_MONEY");
				if(node != null){
					posOrder.setOrderTotalMoney(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_ROUND");
				if(node != null){
					posOrder.setOrderRound(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_BALANCE");
				if(node != null){
					posOrder.setOrderBalance(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_TOTAL_INVOICE");
				if(node != null){
					posOrder.setOrderTotalInvoice(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_CHANGE");
				if(node != null){
					posOrder.setOrderChange(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_TIME");
				if(node != null){
					posOrder.setOrderTime(DateUtil.getXmlTDate(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_MACHINE");
				if(node != null){
					posOrder.setOrderMachine(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_PAYEE");
				if(node != null){
					posOrder.setOrderPayee(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_MEMO");
				if(node != null){
					posOrder.setOrderMemo(node.getText());
				}
				
				node = (Element) element.selectSingleNode("ORDER_POINT");
				if(node != null){
					posOrder.setOrderPoint(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_GROSS_PROFIT");
				if(node != null){
					posOrder.setOrderGrossProfit(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("ORDER_COUPON_TOTAL_MONEY");
				if(node != null){
					posOrder.setOrderCouponTotalMoney(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("POS_ORDER_DETAIL_LIST");
				if(node != null){
					posOrder.setPosOrderDetails(PosOrderDetail.readFromNode(node));
				}
				
				node = (Element) element.selectSingleNode("PAYMENT_LIST");
				if(node != null){
					posOrder.setPayments(Payment.readFromNode(node));
				}
				
				node = (Element) element.selectSingleNode("ORDER_CARD_CHANGE");
				if(node != null){
					posOrder.setOrderCardChange(new BigDecimal(node.getText()));
				}
				
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return posOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((orderStateCode == null) ? 0 : orderStateCode.hashCode());
		result = prime * result + ((orderStateName == null) ? 0 : orderStateName.hashCode());
		result = prime * result + ((orderTime == null) ? 0 : orderTime.hashCode());
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
		PosOrder other = (PosOrder) obj;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (orderStateCode == null) {
			if (other.orderStateCode != null)
				return false;
		} else if (!orderStateCode.equals(other.orderStateCode))
			return false;
		if (orderStateName == null) {
			if (other.orderStateName != null)
				return false;
		} else if (!orderStateName.equals(other.orderStateName))
			return false;
		if (orderTime == null) {
			if (other.orderTime != null)
				return false;
		} else if (!orderTime.equals(other.orderTime))
			return false;
		return true;
	}
	
	public TypeAndTwoValuesDTO getTypeAndTwoValuesDTO(String name){
		for(int i = 0;i < typeAndTwoValuesDTOs.size();i++){
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = typeAndTwoValuesDTOs.get(i);
			if(typeAndTwoValuesDTO.getType().equals(name)){	
				return typeAndTwoValuesDTO;
			}
		}
		return null;
	}
	
	public static int calItemCount(List<PosOrderDetail> posOrderDetails){
		int orderDetailItemCount = 0;
		List<String> items = new ArrayList<String>();
		for(int i = 0;i < posOrderDetails.size();i++){
			PosOrderDetail posOrderDetail = posOrderDetails.get(i);
			if(posOrderDetail.getItemNum() == null){
				continue;
			}
			if(posOrderDetail.getOrderDetailStateCode() != AppConstants.POS_ORDER_DETAIL_STATE_SALE){
				continue;
			}
			String item = posOrderDetail.getItemNum() + "|" + posOrderDetail.getItemGradeNum();
			if(items.contains(item)){
				continue;
			}
			orderDetailItemCount ++;
			items.add(item);
		}
		return orderDetailItemCount;
	}

	@Override
	public String toString() {
		//orderno  orderStateCode paymentCount  posOrderDetailsCount  orderTime orderOperateTime orderPaymentMoney
		return "PosOrder []";
	}

}