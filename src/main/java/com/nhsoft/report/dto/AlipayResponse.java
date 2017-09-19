package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.util.DateUtil;
import org.apache.commons.lang.BooleanUtils;
import org.dom4j.Element;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlipayResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7148409200165199333L;
	private String buyerLogonId;
	private String buyerUserId;
	private String outTradeNo;
	private String resultCode;
	private String tradeNo;
	private Date gmtPayment;
	private BigDecimal totalFee;
	private String detailErrorCode;
	private String detailErrorDes;
	private Boolean retryFlag;
	private String action;
	private String tradeStatus;
	private String qrCode;//二维码码串
	private String picUrl;//二维码图片地址
	private String smallPicUrl;//二维码小图地址
	private List<TradeFundBill> tradeFundBills = new ArrayList<TradeFundBill>();

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getSmallPicUrl() {
		return smallPicUrl;
	}

	public void setSmallPicUrl(String smallPicUrl) {
		this.smallPicUrl = smallPicUrl;
	}

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public Date getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(Date gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getDetailErrorCode() {
		return detailErrorCode;
	}

	public void setDetailErrorCode(String detailErrorCode) {
		this.detailErrorCode = detailErrorCode;
	}

	public String getDetailErrorDes() {
		return detailErrorDes;
	}

	public void setDetailErrorDes(String detailErrorDes) {
		this.detailErrorDes = detailErrorDes;
	}

	public Boolean getRetryFlag() {
		return retryFlag;
	}

	public void setRetryFlag(Boolean retryFlag) {
		this.retryFlag = retryFlag;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public List<TradeFundBill> getTradeFundBills() {
		return tradeFundBills;
	}

	public void setTradeFundBills(List<TradeFundBill> tradeFundBills) {
		this.tradeFundBills = tradeFundBills;
	}

	public static AlipayResponse readFromElement(Element e){
		AlipayResponse response = new AlipayResponse();
		Element root = (Element) e.selectSingleNode("alipay");
		if(root == null){
			return null;
		}
		Element element = (Element) root.selectSingleNode("buyer_logon_id");
		if(element != null){
			response.setBuyerLogonId(element.getText());
		}
		element = (Element) root.selectSingleNode("buyer_user_id");
		if(element != null){
			response.setBuyerUserId(element.getText());
		}
		element = (Element) root.selectSingleNode("out_trade_no");
		if(element != null){
			response.setOutTradeNo(element.getText());
		}
		element = (Element) root.selectSingleNode("result_code");
		if(element != null){
			response.setResultCode(element.getText());
		}
		element = (Element) root.selectSingleNode("trade_no");
		if(element != null){
			response.setTradeNo(element.getText());
		}
		element = (Element) root.selectSingleNode("detail_error_des");
		if(element != null){
			response.setDetailErrorDes(element.getText());
		}
		element = (Element) root.selectSingleNode("detail_error_code");
		if(element != null){
			response.setDetailErrorCode(element.getText());
		}
		element = (Element) root.selectSingleNode("gmt_payment");
		if(element != null){
			response.setGmtPayment(DateUtil.getDateTimeHMS(element.getText()));
		}
		element = (Element) root.selectSingleNode("total_fee");
		if(element != null){
			response.setTotalFee(new BigDecimal(element.getText()));
		}
		element = (Element) root.selectSingleNode("retry_flag");
		if(element != null){
			response.setRetryFlag(BooleanUtils.toBoolean(element.getText(), "Y", "N"));
		}
		element = (Element) root.selectSingleNode("action");
		if(element != null){
			response.setAction(element.getText());
		}
		element = (Element) root.selectSingleNode("trade_status");
		if(element != null){
			response.setTradeStatus(element.getText());
		}
		
		element = (Element) root.selectSingleNode("qr_code");
		if(element != null){
			response.setQrCode(element.getText());
		}
		
		element = (Element) root.selectSingleNode("pic_url");
		if(element != null){
			response.setPicUrl(element.getText());
		}
		
		element = (Element) root.selectSingleNode("small_pic_url");
		if(element != null){
			response.setSmallPicUrl(element.getText());
		}
		
		element = (Element) root.selectSingleNode("fund_bill_list");
		if(element != null){
			response.setTradeFundBills(TradeFundBill.readFromElement(element));
		}
		
		return response;
	}
	
	private List<String> getCustomerDiscountPaymentTypes(){
		List<String> codes = new ArrayList<String>();
		codes.add("00"); //支付宝红包
		codes.add("40"); //折扣券
		codes.add("102"); //商户优惠券
		codes.add("104"); //商户红包
		return codes;
	}
	
	private List<String> getBranchDiscountPaymentTypes(){
		List<String> codes = new ArrayList<String>();
		codes.add("102"); //商户优惠券
		codes.add("104"); //商户红包
		return codes;
	}
	
	public BigDecimal getCustomerPaymentMoney(){
		BigDecimal value = BigDecimal.ZERO;
		List<String> codes = getCustomerDiscountPaymentTypes();
		for(int i = 0; i < tradeFundBills.size();i++){
			TradeFundBill tradeFundBill = tradeFundBills.get(i);
			if(codes.contains(tradeFundBill.getFundChannel())){
				continue;	
			}
			value = value.add(tradeFundBill.getAmount());
		}
		if(value.compareTo(BigDecimal.ZERO) == 0){
			value = totalFee;
		}
		return value;
	}
	
	public BigDecimal getBranchReceiveMoney(){
		BigDecimal value = BigDecimal.ZERO;
		List<String> codes = getBranchDiscountPaymentTypes();
		for(int i = 0; i < tradeFundBills.size();i++){
			TradeFundBill tradeFundBill = tradeFundBills.get(i);
			if(codes.contains(tradeFundBill.getFundChannel())){
				continue;
			}
			value = value.add(tradeFundBill.getAmount());
		}
		if(value.compareTo(BigDecimal.ZERO) == 0){
			value = totalFee;
		}
		return value;
	}

}
