package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.util.AppUtil;
import com.nhsoft.pos3.shared.AppConstants;

import java.io.Serializable;

public class WeixinCardUpdateDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6571443164618799649L;
    private String systemBookCode;
    private String code;                // 卡券Code码。
    private String cardId;              // 卡券ID。
    private String backgroundPicUrl;    // 支持商家激活时针对单个会员卡分配自定义的会员卡背景。
    private Integer bonus;              // 需要设置的积分全量值，传入的数值会直接显示
    private Integer addBonus;           // 本次积分变动值，传负数代表减少
    private String recordBonus;         // 商家自定义积分消耗记录，不超过14个汉字

    private Integer balance;            // 需要设置的余额全量值，传入的数值会直接显示在卡面
    private Integer addBalance;         // 本次余额变动值，传负数代表减少
    private String recordBalance;       // 商家自定义金额消耗记录，不超过14个汉字。

    private Integer ticketTotalNum;     // 优惠券梳理

    private Boolean isNotifyBonus = false;      // 积分变动时是否触发系统模板消息，默认为true
    private Boolean isNotifyBalance;    // 余额变动时是否触发系统模板消息，默认为true
    private Boolean isNotifyTicket;     // 优惠券变动时是否触发系统模板消息，默认为false。

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public void setBackgroundPicUrl(String backgroundPicUrl) {
        this.backgroundPicUrl = backgroundPicUrl;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getAddBonus() {
        return addBonus;
    }

    public void setAddBonus(Integer addBonus) {
        this.addBonus = addBonus;
    }

    public String getRecordBonus() {
        return recordBonus;
    }

    public void setRecordBonus(String recordBonus) {
        this.recordBonus = recordBonus;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getAddBalance() {
        return addBalance;
    }

    public void setAddBalance(Integer addBalance) {
        this.addBalance = addBalance;
    }

    public String getRecordBalance() {
        return recordBalance;
    }

    public void setRecordBalance(String recordBalance) {
        this.recordBalance = recordBalance;
    }

    public Integer getTicketTotalNum() {
        return ticketTotalNum;
    }

    public void setTicketTotalNum(Integer ticketTotalNum) {
        this.ticketTotalNum = ticketTotalNum;
    }

    public Boolean getIsNotifyBonus() {
        return isNotifyBonus;
    }

    public void setIsNotifyBonus(Boolean isNotifyBonus) {
        this.isNotifyBonus = isNotifyBonus;
    }

    public Boolean getIsNotifyBalance() {
        return isNotifyBalance;
    }

    public void setIsNotifyBalance(Boolean isNotifyBalance) {
        this.isNotifyBalance = isNotifyBalance;
    }

    public Boolean getIsNotifyTicket() {
        return isNotifyTicket;
    }

    public void setIsNotifyTicket(Boolean isNotifyTicket) {
        this.isNotifyTicket = isNotifyTicket;
    }
    
	public String createMNSContent(){
		return AppConstants.MESSAGE_COMMAND_WEIXIN_CARD_UPDATE + AppUtil.getEnter() + AppUtil.getGson().toJson(this);
	}

}
