package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.api.alipay.AlipayConstants;
import com.nhsoft.pos3.server.jsonrpc.dto.AlipayJsonResult;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class AlipayResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8660000616686866034L;
	private static final Logger logger = LoggerFactory.getLogger(AlipayResult.class);
	private boolean isSuccess = false;
	private String sign;
	private String signType;
	private String error;
	private AlipayResponse alipayResponse;
	private String context;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public AlipayResponse getAlipayResponse() {
		return alipayResponse;
	}

	public void setAlipayResponse(AlipayResponse alipayResponse) {
		this.alipayResponse = alipayResponse;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public static AlipayResult readFromXml(String text) {
		AlipayResult result = new AlipayResult();
		result.setContext(text);
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();

			Element element = (Element) root.selectSingleNode("is_success");
			if (element != null) {
				result.setSuccess(BooleanUtils.toBoolean(element.getText(), "T", "F"));

			}

			element = (Element) root.selectSingleNode("sign");
			if (element != null) {
				result.setSign(element.getText());

			}

			element = (Element) root.selectSingleNode("sign_type");
			if (element != null) {
				result.setSignType(element.getText());

			}
			// 请求失败或者接入数据错误
			if (!result.isSuccess()) {
				element = (Element) root.selectSingleNode("error");
				String error = AlipayConstants.getSystemErrorName(element.getText());
				if (error == null) {
					error = element.getText();
				}
				result.setError(error);

			}
			element = (Element) root.selectSingleNode("response");
			if (element != null) {
				result.setAlipayResponse(AlipayResponse.readFromElement(element));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 处理下单并支付返回内容
	 * @return
	 */
	public String handleCreateResult(){
		String result = "";
		if(!isSuccess){
			return error;
		}
		if(alipayResponse != null){
			if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_ORDER_SUCCESS_PAY_SUCCESS)){
				return AlipayConstants.getBizResult(alipayResponse.getResultCode());
			} else {
				result = AlipayConstants.getBizResult(alipayResponse.getResultCode());
				if(StringUtils.isNotEmpty(alipayResponse.getDetailErrorDes())){
					result = result.concat(":" + alipayResponse.getDetailErrorDes());
					
				}
			}
		}		
		return result;
	}
	
	/**
	 * 处理预支付返回内容
	 * @return
	 */
	public String handlePrecreateResult(){
		String result = "";
		if(!isSuccess){
			return error;
		}
		if(alipayResponse != null){
			if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_SUCCESS)){
				return "预下单成功";
			} else if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_FAIL)){
				result = "预下单失败";
				
			} else if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_UNKNOWN)){
				result = "处理结果未知";
				
			}
			if(StringUtils.isNotEmpty(alipayResponse.getDetailErrorDes())){
				result = result.concat(":" + alipayResponse.getDetailErrorDes());
				
			}
		}		
		return result;
	}
	
	/**
	 *  处理撤销请求
	 * @return
	 */
	public String handleCancelResult(){
		String result = "";
		if(!isSuccess){
			result = AlipayConstants.getSystemErrorName(error);
			if(result == null){
				result = error;
			}
			return result;
		}
		if(alipayResponse != null){
			if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_SUCCESS)){
				return AlipayConstants.getBizResult(alipayResponse.getResultCode());
			} else {
				result = AlipayConstants.getBizResult(alipayResponse.getResultCode());
				if(StringUtils.isNotEmpty(alipayResponse.getDetailErrorDes())){
					result = result.concat(":" + alipayResponse.getDetailErrorDes());
					
				}
			}
		}
		return result;
	}
	
	/**
	 *  处理查询请求
	 * @return
	 */
	public String handleQueryResult(){
		String result = "";
		if(!isSuccess){
			result = AlipayConstants.getSystemErrorName(error);
			if(result == null){
				result = error;
			}
			return result;
		}
		if(alipayResponse != null){
			if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_SUCCESS)){
				return AlipayConstants.getOrderStateName(alipayResponse.getTradeStatus());
			} else {
				result = AlipayConstants.getBizResult(alipayResponse.getResultCode());
				if(StringUtils.isNotEmpty(alipayResponse.getDetailErrorDes())){
					result = result.concat(":" + alipayResponse.getDetailErrorDes());
					
				}
			}
		}
		return result;
	}
	
	public AlipayJsonResult parseCreateJson(){
		AlipayJsonResult alipayJsonResult = new AlipayJsonResult();
		if(alipayResponse != null){
			alipayJsonResult.setTradeNo(alipayResponse.getTradeNo());
			if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_ORDER_SUCCESS_PAY_SUCCESS)){
				alipayJsonResult.setTradeState(true);
			}
			alipayJsonResult.setTradeFundBills(alipayResponse.getTradeFundBills());
			alipayJsonResult.setAlipayBuyerId(alipayResponse.getBuyerLogonId());
			alipayJsonResult.setAlipayResultCode(alipayResponse.getDetailErrorCode());
			alipayJsonResult.setAlipayPaymentMoney(alipayResponse.getCustomerPaymentMoney());
			alipayJsonResult.setAlipayReceiveMoney(alipayResponse.getBranchReceiveMoney());
		}
		alipayJsonResult.setAlipayResult(handleCreateResult());		
		return alipayJsonResult;
	}
	
	public AlipayJsonResult parseCancelJson(){
		AlipayJsonResult alipayJsonResult = new AlipayJsonResult();
		if(alipayResponse != null){
			alipayJsonResult.setTradeNo(alipayResponse.getTradeNo());
			if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_SUCCESS)){
				alipayJsonResult.setTradeState(true);
			}
			alipayJsonResult.setAlipayBuyerId(alipayResponse.getBuyerLogonId());
			alipayJsonResult.setAlipayResultCode(alipayResponse.getDetailErrorCode());
		}
		alipayJsonResult.setAlipayResult(handleCancelResult());
		return alipayJsonResult;
	}
	
	public AlipayJsonResult parseQueryJson(){
		AlipayJsonResult alipayJsonResult = new AlipayJsonResult();
		if(alipayResponse != null){
			alipayJsonResult.setTradeNo(alipayResponse.getTradeNo());
			if(alipayResponse.getResultCode().equals(AlipayConstants.RESULT_CODE_SUCCESS)){
				alipayJsonResult.setTradeState(true);
			}
			alipayJsonResult.setTradeFundBills(alipayResponse.getTradeFundBills());
			alipayJsonResult.setAlipayBuyerId(alipayResponse.getBuyerLogonId());
			alipayJsonResult.setAlipayResultCode(alipayResponse.getDetailErrorCode());
			alipayJsonResult.setAlipayPaymentMoney(alipayResponse.getCustomerPaymentMoney());
			alipayJsonResult.setAlipayReceiveMoney(alipayResponse.getBranchReceiveMoney());
		}
		alipayJsonResult.setAlipayResult(handleQueryResult());
		return alipayJsonResult;
	}
	
}
