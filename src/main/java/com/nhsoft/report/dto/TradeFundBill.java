package com.nhsoft.report.dto;

import com.google.gson.annotations.SerializedName;
import org.dom4j.Element;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TradeFundBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6698850671147259166L;
	private BigDecimal amount;
	@SerializedName("fund_channel")
	private String fundChannel;
	private String fundChannelName;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFundChannel() {
		return fundChannel;
	}

	public void setFundChannel(String fundChannel) {
		this.fundChannel = fundChannel;
	}

	public String getFundChannelName() {
		return fundChannelName;
	}

	public void setFundChannelName(String fundChannelName) {
		this.fundChannelName = fundChannelName;
	}

	
	@SuppressWarnings("unchecked")
	public static List<TradeFundBill> readFromElement(Element e){
		List<TradeFundBill> list = new ArrayList<TradeFundBill>();
		Iterator<Element> iterator = e.elementIterator("TradeFundBill");
		Element subElement;
		while(iterator.hasNext()){
			Element element = iterator.next();
			TradeFundBill tradeFundBill = new TradeFundBill();
			
			subElement = (Element) element.selectSingleNode("amount");
			if(subElement != null){
				tradeFundBill.setAmount(new BigDecimal(subElement.getText()));
			}
			subElement = (Element) element.selectSingleNode("fund_channel");
			if(subElement != null){
				tradeFundBill.setFundChannel(subElement.getText());
			}
			if(tradeFundBill.getFundChannel().equals("00")){
				tradeFundBill.setFundChannelName("支付宝红包");
			} else if(tradeFundBill.getFundChannel().equals("10")){
				tradeFundBill.setFundChannelName("支付宝余额");
			} else if(tradeFundBill.getFundChannel().equals("60")){
				tradeFundBill.setFundChannelName("支付宝预存卡");
			} else if(tradeFundBill.getFundChannel().equals("30")){
				tradeFundBill.setFundChannelName("积分");
			} else if(tradeFundBill.getFundChannel().equals("70")){
				tradeFundBill.setFundChannelName("信用支付");
			} else if(tradeFundBill.getFundChannel().equals("40")){
				tradeFundBill.setFundChannelName("折扣券");
			} else if(tradeFundBill.getFundChannel().equals("80")){
				tradeFundBill.setFundChannelName("预付卡");
			} else if(tradeFundBill.getFundChannel().equals("90")){
				tradeFundBill.setFundChannelName("信用支付（消费信贷）");
			} else if(tradeFundBill.getFundChannel().equals("100")){
				tradeFundBill.setFundChannelName("支付宝理财专户");
			} else if(tradeFundBill.getFundChannel().equals("101")){
				tradeFundBill.setFundChannelName("商户店铺卡");
			} else if(tradeFundBill.getFundChannel().equals("102")){
				tradeFundBill.setFundChannelName("商户优惠券");
			} else if(tradeFundBill.getFundChannel().equals("103")){
				tradeFundBill.setFundChannelName("白条");
			} else if(tradeFundBill.getFundChannel().equals("104")){
				tradeFundBill.setFundChannelName("商户红包");
			}
			list.add(tradeFundBill);
		}
		return list;
	}
}
