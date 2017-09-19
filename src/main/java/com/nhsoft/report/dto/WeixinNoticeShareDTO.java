package com.nhsoft.report.dto;

import java.io.Serializable;

public class WeixinNoticeShareDTO implements Serializable {

	/**
	 * 
	 */
	public static String CARD_USER_TYPE_CHANGE = "OPENTM400905659"; //卡类型变更模板
	public static String ONLINE_TEAM_SUCCESS = "OPENTM406187542"; //组团成功提醒
	public static String ONLINE_TEAM_ZITI_SUCCESS = "OPENTM407110531"; //到店自提组团成功提醒

	public static String ONLINE_TEAM_FAIL = "TM00020"; //团购失败退款通知
	public static String RETURN_MONEY = "OPENTM400691869"; //退款提醒
	public static String ONLINE_ORDER_DELIVER = "OPENTM207664690"; //发货提醒	
	public static String POINT_CARD_CONSUME = "OPENTM406276464"; //积分卡消费提醒	
	public static String ONLINE_ORDER_TRANSFER_NOTICE = "OPENTM207002763"; //在线订单配送通知	

	private static final long serialVersionUID = 4358934294619991279L;
	private String systemBookCode;
	private String openTMID;// 模板编号
	private String title = "";// 模板标题
	private String remark = "";// 模板结尾
	private String url = "";//
	private String topcolor = "";// 颜色
	private String openId;
	private String value;// 模板keyword 多个用#隔开
	private String kind;//提醒类型
	
	public WeixinNoticeShareDTO(){
		
	}
	
	public WeixinNoticeShareDTO(String systemBookCode, String title, String openId){
		this.systemBookCode = systemBookCode;
		this.title = title;
		this.openId = openId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getOpenTMID() {
		return openTMID;
	}

	public void setOpenTMID(String openTMID) {
		this.openTMID = openTMID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
