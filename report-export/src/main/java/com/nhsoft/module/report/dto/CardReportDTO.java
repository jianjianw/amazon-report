package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CardReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7664330494200412724L;
	private Integer branchNum;// 门店编号
	private String branchName;// 门店名称
	private String bizDay;// 营业日
	private int sendCardCount;// 发卡数
	private int returnCardCount;// 退卡数（卡回收）
	private BigDecimal returnCardMoney;// 退卡金额（卡回收）
	private BigDecimal paymentMoney;// 付款金额
	private BigDecimal depositMoney;// 存款金额
	private BigDecimal sendMoney;// 赠送金额
	private BigDecimal consumeMoney;// 消费金额
	private BigDecimal onlineConsumeMoney; //线上消费金额
	private BigDecimal oriDepositMoney; //老会员转卡金额
	private int changeEleCardCount;//转电子卡数
	private Integer cardUserType;

	public CardReportDTO() {
		setSendCardCount(0);
		setReturnCardCount(0);
		setPaymentMoney(BigDecimal.ZERO);
		setDepositMoney(BigDecimal.ZERO);
		setSendMoney(BigDecimal.ZERO);
		setConsumeMoney(BigDecimal.ZERO);
		setOriDepositMoney(BigDecimal.ZERO);
		setReturnCardMoney(BigDecimal.ZERO);
		setOnlineConsumeMoney(BigDecimal.ZERO);
	}

	public BigDecimal getOnlineConsumeMoney() {
		return onlineConsumeMoney;
	}

	public void setOnlineConsumeMoney(BigDecimal onlineConsumeMoney) {
		this.onlineConsumeMoney = onlineConsumeMoney;
	}

	public int getChangeEleCardCount() {
		return changeEleCardCount;
	}

	public void setChangeEleCardCount(int changeEleCardCount) {
		this.changeEleCardCount = changeEleCardCount;
	}

	public BigDecimal getReturnCardMoney() {
		return returnCardMoney;
	}

	public void setReturnCardMoney(BigDecimal returnCardMoney) {
		this.returnCardMoney = returnCardMoney;
	}

	public BigDecimal getOriDepositMoney() {
		return oriDepositMoney;
	}

	public void setOriDepositMoney(BigDecimal oriDepositMoney) {
		this.oriDepositMoney = oriDepositMoney;
	}

	public Integer getCardUserType() {
		return cardUserType;
	}

	public void setCardUserType(Integer cardUserType) {
		this.cardUserType = cardUserType;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBizDay() {
		return bizDay;
	}

	public void setBizDay(String bizDay) {
		this.bizDay = bizDay;
	}

	public int getSendCardCount() {
		return sendCardCount;
	}

	public void setSendCardCount(int sendCardCount) {
		this.sendCardCount = sendCardCount;
	}

	public int getReturnCardCount() {
		return returnCardCount;
	}

	public void setReturnCardCount(int returnCardCount) {
		this.returnCardCount = returnCardCount;
	}

	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public BigDecimal getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}

	public BigDecimal getSendMoney() {
		return sendMoney;
	}

	public void setSendMoney(BigDecimal sendMoney) {
		this.sendMoney = sendMoney;
	}

	public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public static CardReportDTO readByBranch(Integer branchNum, List<CardReportDTO> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBranchNum().equals(branchNum)) {
				return list.get(i);
			}
		}
		return null;
	}

	public static CardReportDTO readByBizday(String bizday, List<CardReportDTO> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBizDay().equals(bizday)) {
				return list.get(i);
			}
		}
		return null;
	}
	
	public static CardReportDTO readByBranchBizday(String bizday, Integer branchNum, List<CardReportDTO> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBizDay().equals(bizday) && list.get(i).getBranchNum().equals(branchNum)) {
				return list.get(i);
			}
		}
		return null;
	}

	public static CardReportDTO readByBranchCardType(Integer branchNum, Integer cardUserCardType,
			List<CardReportDTO> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBranchNum().equals(branchNum) && list.get(i).getCardUserType().equals(cardUserCardType)) {
				return list.get(i);
			}
		}
		return null;
	}
}
