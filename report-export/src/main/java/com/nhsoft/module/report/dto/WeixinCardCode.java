package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WeixinCardCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -112993246167387060L;
	private String openid;
	private Integer errcode;
	private String errmsg;
	private String user_card_status;
	private String user_card_status_name;
	private Date begin_time;
	private Date end_time; //有效日期
	private boolean can_consume;//是否可用
	private String title;//卡券名称
	private BigDecimal least_cost;//代金券专用，表示起用金额（单位为分）
	private BigDecimal reduce_cost;//代金券专用，表示减免金额（单位为分） 
	private BigDecimal discount;//折扣券专用字段，表示打折额度（百分比），例：填30为七折团购详情
	private String deal_detail;//团购券专用字段，团购详情。 

	public String getDeal_detail() {
		return deal_detail;
	}

	public void setDeal_detail(String deal_detail) {
		this.deal_detail = deal_detail;
	}

	public BigDecimal getLeast_cost() {
		return least_cost;
	}

	public void setLeast_cost(BigDecimal least_cost) {
		this.least_cost = least_cost;
	}

	public BigDecimal getReduce_cost() {
		return reduce_cost;
	}

	public void setReduce_cost(BigDecimal reduce_cost) {
		this.reduce_cost = reduce_cost;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getUser_card_status() {
		return user_card_status;
	}

	public void setUser_card_status(String user_card_status) {
		this.user_card_status = user_card_status;
	}

	public String getUser_card_status_name() {
		return user_card_status_name;
	}

	public boolean isCan_consume() {
		return can_consume;
	}

	public void setUser_card_status_name(String user_card_status_name) {
		this.user_card_status_name = user_card_status_name;
	}

	public void setCan_consume(boolean can_consume) {
		this.can_consume = can_consume;
	}

}
