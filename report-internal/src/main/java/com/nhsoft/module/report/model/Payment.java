package com.nhsoft.module.report.model;

import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Payment generated by hbm2java
 */
@Entity
public class Payment implements java.io.Serializable {

	private static final long serialVersionUID = -4554431883981504708L;
	@Id
	private String paymentNo;
	private String orderNo;
	private String clientFid;
	private String systemBookCode;
	private Integer branchNum;
	private Integer shiftTableNum;
	private String shiftTableBizday;
	private Date paymentTime;
	private String paymentPayBy;
	private BigDecimal paymentRound;
	private BigDecimal paymentReceive;
	private BigDecimal paymentMoney;
	private BigDecimal paymentChange;
	private BigDecimal paymentPaid;
	private BigDecimal paymentBalance;
	private String paymentBillNo;
	private String paymentMemo;
	private String paymentAcctNo;
	private String paymentAuditor;
	private String paymentMachine;
	private Integer paymentCustNum;
	private BigDecimal paymentCardBalance;
	private Integer paymentConsumeCount;
	private Boolean paymentFlag;
	private Date paymentDate;
	private Date paymentLastestDate;
	private Integer accountBankNum;
	private BigDecimal paymentPoint;
	private Boolean consumeRevokeFlag;
	@Column(name = "paymentOnlineUndiscount")
	private BigDecimal paymentOnlineUnDiscount; //不可折扣金额
	private BigDecimal paymentBuyerMoney;
	private BigDecimal paymentReceiptMoney;

	public Payment() {
	}

	public BigDecimal getPaymentOnlineUnDiscount() {
		return paymentOnlineUnDiscount;
	}

	public void setPaymentOnlineUnDiscount(BigDecimal paymentOnlineUnDiscount) {
		this.paymentOnlineUnDiscount = paymentOnlineUnDiscount;
	}

	public String getPaymentNo() {
		return this.paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getClientFid() {
		return this.clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
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

	public Date getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentPayBy() {
		return this.paymentPayBy;
	}

	public void setPaymentPayBy(String paymentPayBy) {
		this.paymentPayBy = paymentPayBy;
	}

	public BigDecimal getPaymentRound() {
		return this.paymentRound;
	}

	public void setPaymentRound(BigDecimal paymentRound) {
		this.paymentRound = paymentRound;
	}

	public BigDecimal getPaymentReceive() {
		return this.paymentReceive;
	}

	public void setPaymentReceive(BigDecimal paymentReceive) {
		this.paymentReceive = paymentReceive;
	}

	public BigDecimal getPaymentMoney() {
		return this.paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public BigDecimal getPaymentChange() {
		return this.paymentChange;
	}

	public void setPaymentChange(BigDecimal paymentChange) {
		this.paymentChange = paymentChange;
	}

	public BigDecimal getPaymentPaid() {
		return this.paymentPaid;
	}

	public void setPaymentPaid(BigDecimal paymentPaid) {
		this.paymentPaid = paymentPaid;
	}

	public BigDecimal getPaymentBalance() {
		return this.paymentBalance;
	}

	public void setPaymentBalance(BigDecimal paymentBalance) {
		this.paymentBalance = paymentBalance;
	}

	public String getPaymentBillNo() {
		return this.paymentBillNo;
	}

	public void setPaymentBillNo(String paymentBillNo) {
		this.paymentBillNo = paymentBillNo;
	}

	public String getPaymentMemo() {
		return this.paymentMemo;
	}

	public void setPaymentMemo(String paymentMemo) {
		this.paymentMemo = paymentMemo;
	}

	public String getPaymentAcctNo() {
		return this.paymentAcctNo;
	}

	public void setPaymentAcctNo(String paymentAcctNo) {
		this.paymentAcctNo = paymentAcctNo;
	}

	public String getPaymentAuditor() {
		return this.paymentAuditor;
	}

	public void setPaymentAuditor(String paymentAuditor) {
		this.paymentAuditor = paymentAuditor;
	}

	public String getPaymentMachine() {
		return this.paymentMachine;
	}

	public void setPaymentMachine(String paymentMachine) {
		this.paymentMachine = paymentMachine;
	}

	public Integer getPaymentCustNum() {
		return this.paymentCustNum;
	}

	public void setPaymentCustNum(Integer paymentCustNum) {
		this.paymentCustNum = paymentCustNum;
	}

	public BigDecimal getPaymentCardBalance() {
		return this.paymentCardBalance;
	}

	public void setPaymentCardBalance(BigDecimal paymentCardBalance) {
		this.paymentCardBalance = paymentCardBalance;
	}

	public Integer getPaymentConsumeCount() {
		return this.paymentConsumeCount;
	}

	public void setPaymentConsumeCount(Integer paymentConsumeCount) {
		this.paymentConsumeCount = paymentConsumeCount;
	}

	public Boolean getPaymentFlag() {
		return this.paymentFlag;
	}

	public void setPaymentFlag(Boolean paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getPaymentLastestDate() {
		return this.paymentLastestDate;
	}

	public void setPaymentLastestDate(Date paymentLastestDate) {
		this.paymentLastestDate = paymentLastestDate;
	}

	public Integer getAccountBankNum() {
		return this.accountBankNum;
	}

	public void setAccountBankNum(Integer accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public BigDecimal getPaymentPoint() {
		return this.paymentPoint;
	}

	public void setPaymentPoint(BigDecimal paymentPoint) {
		this.paymentPoint = paymentPoint;
	}
	
	public Boolean getConsumeRevokeFlag() {
		return consumeRevokeFlag;
	}

	public void setConsumeRevokeFlag(Boolean consumeRevokeFlag) {
		this.consumeRevokeFlag = consumeRevokeFlag;
	}

	public BigDecimal getPaymentBuyerMoney() {
		return paymentBuyerMoney;
	}

	public void setPaymentBuyerMoney(BigDecimal paymentBuyerMoney) {
		this.paymentBuyerMoney = paymentBuyerMoney;
	}

	public BigDecimal getPaymentReceiptMoney() {
		return paymentReceiptMoney;
	}

	public void setPaymentReceiptMoney(BigDecimal paymentReceiptMoney) {
		this.paymentReceiptMoney = paymentReceiptMoney;
	}

	public static List<Payment> readFromNode(Element root){
		List<Payment> payments = new ArrayList<Payment>();
		Iterator<Element> paymentIterator = root.elementIterator();
		Element paymenetElement = null;
		Element paymenetSubElement = null;
		while(paymentIterator.hasNext()){
			paymenetElement = paymentIterator.next();
			
			Payment payment = new Payment();
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("SYSTEM_BOOK_CODE");
			if(paymenetSubElement != null){
				payment.setSystemBookCode(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("BRANCH_NUM");
			if(paymenetSubElement != null){
				payment.setBranchNum(Integer.parseInt(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("SHIFT_TABLE_NUM");
			if(paymenetSubElement != null){
				payment.setShiftTableNum(Integer.parseInt(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("SHIFT_TABLE_BIZDAY");
			if(paymenetSubElement != null){
				payment.setShiftTableBizday(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("ORDER_NO");
			if(paymenetSubElement != null){
				payment.setOrderNo(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_NO");
			if(paymenetSubElement != null){
				payment.setPaymentNo(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_TIME");
			if(paymenetSubElement != null){
				payment.setPaymentTime(DateUtil.getXmlTDate(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_PAY_BY");
			if(paymenetSubElement != null){
				payment.setPaymentPayBy(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_ROUND");
			if(paymenetSubElement != null){
				if(StringUtils.isEmpty(paymenetSubElement.getText())){
					payment.setPaymentRound(BigDecimal.ZERO);

				} else {
					payment.setPaymentRound(new BigDecimal(paymenetSubElement.getText()));
					
				}
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_RECEIVE");
			if(paymenetSubElement != null){
				
				if(StringUtils.isEmpty(paymenetSubElement.getText())){
					payment.setPaymentRound(BigDecimal.ZERO);

				} else {
					payment.setPaymentReceive(new BigDecimal(paymenetSubElement.getText()));
					
				}
				
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_MONEY");
			if(paymenetSubElement != null){
				payment.setPaymentMoney(new BigDecimal(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_CHANGE");
			if(paymenetSubElement != null){
				payment.setPaymentChange(new BigDecimal(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_PAID");
			if(paymenetSubElement != null){
				payment.setPaymentPaid(new BigDecimal(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_BALANCE");
			if(paymenetSubElement != null){
				payment.setPaymentBalance(new BigDecimal(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_BILL_NO");
			if(paymenetSubElement != null){
				payment.setPaymentBillNo(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_MEMO");
			if(paymenetSubElement != null){
				payment.setPaymentMemo(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_ACCT_NO");
			if(paymenetSubElement != null){
				payment.setPaymentAcctNo(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_AUDITOR");
			if(paymenetSubElement != null){
				payment.setPaymentAuditor(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_MACHINE");
			if(paymenetSubElement != null){
				payment.setPaymentMachine(paymenetSubElement.getText());
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_CUST_NUM");
			if(paymenetSubElement != null){
				payment.setPaymentCustNum(Integer.parseInt(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_CARD_BALANCE");
			if(paymenetSubElement != null){
				payment.setPaymentCardBalance(new BigDecimal(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_CONSUME_COUNT");
			if(paymenetSubElement != null){
				payment.setPaymentConsumeCount(Integer.parseInt(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_FLAG");
			if(paymenetSubElement != null){
				payment.setPaymentFlag(BooleanUtils.toBoolean(paymenetSubElement.getText(), "1", "0"));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_DATE");
			if(paymenetSubElement != null){
				payment.setPaymentDate(DateUtil.getXmlTDate(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("PAYMENT_LASTEST_DATE");
			if(paymenetSubElement != null){
				payment.setPaymentLastestDate(DateUtil.getXmlTDate(paymenetSubElement.getText()));
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("CLIENT_FID");
			if(paymenetSubElement != null){
				
				payment.setClientFid(paymenetSubElement.getText());
				if(payment.getClientFid().isEmpty()){
					payment.setClientFid(null);
				}
			}
			paymenetSubElement = (Element) paymenetElement.selectSingleNode("ACCOUNT_BANK_NUM");
			if(paymenetSubElement != null){
				payment.setAccountBankNum(Integer.parseInt(paymenetSubElement.getText()));
			}
			payment.setConsumeRevokeFlag(false);
			payments.add(payment);
		}
		
		return payments;
	}
	
	public static Payment get(List<Payment> payments, String paymentPayBy){
		for(int i = 0;i < payments.size();i++){
			Payment payment = payments.get(i);
			if(payment.getPaymentPayBy().equals(paymentPayBy)){
				return payment;
			}
		}
		return null;
	}
	
	public static List<Payment> find(
			List<Payment> payments, String orderNo) {
		List<Payment> list = new ArrayList<Payment>();
		for(int i = 0;i < payments.size();i++){
			Payment payment = payments.get(i);
			if(payment.getOrderNo().equals(orderNo)){
				list.add(payment);
			}
		}
		return list;
	}

}
