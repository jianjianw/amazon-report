package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.model.CardConsume;
import com.nhsoft.pos3.server.model.CardDeposit;
import com.nhsoft.pos3.server.model.WeixinCardType;
import com.nhsoft.pos3.shared.AppConstants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WeixinNoticeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5119503276365370571L;
	private String systemBookCode;
	private String openId;
	private Date date;
	private String branchName;
	private BigDecimal money;
	private BigDecimal balance;
	private String printedNum;
	private String type;
	private String fid;
	private Integer branchNum;
	private String cardUrl;

	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getPrintedNum() {
		return printedNum;
	}

	public void setPrintedNum(String printedNum) {
		this.printedNum = printedNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	
	public static WeixinNoticeDTO createDeposit(CardDeposit cardDeposit, List<WeixinCardType> weixinCardTypes){
		WeixinNoticeDTO weixinNoticeDTO = new WeixinNoticeDTO();
		weixinNoticeDTO.setOpenId(cardDeposit.getDepositOpenId());
		weixinNoticeDTO.setDate(cardDeposit.getDepositDate());
		weixinNoticeDTO.setBranchName(cardDeposit.getDepositBranchName());
		weixinNoticeDTO.setMoney(cardDeposit.getDepositMoney());
		weixinNoticeDTO.setBalance(cardDeposit.getDepositBalance());
		weixinNoticeDTO.setPrintedNum(cardDeposit.getDepositPrintedNum());
		weixinNoticeDTO.setSystemBookCode(cardDeposit.getSystemBookCode());
		weixinNoticeDTO.setFid(cardDeposit.getDepositFid());
		weixinNoticeDTO.setType(AppConstants.DEPOSIT_TYPE_DEPOSIT);
		weixinNoticeDTO.setBranchNum(cardDeposit.getBranchNum());
		weixinNoticeDTO.setCardUrl(WeixinCardType.getCardUrl(weixinCardTypes, cardDeposit.getDepositCardType()));
		return weixinNoticeDTO;
	}
	
	public static WeixinNoticeDTO createConsume(CardConsume cardConsume, List<WeixinCardType> weixinCardTypes){
		WeixinNoticeDTO weixinNoticeDTO = new WeixinNoticeDTO();
		weixinNoticeDTO.setOpenId(cardConsume.getConsumeOpenId());
		weixinNoticeDTO.setDate(cardConsume.getConsumeDate());
		weixinNoticeDTO.setBranchName(cardConsume.getConsumeBranchName());
		weixinNoticeDTO.setMoney(cardConsume.getConsumeMoney());
		weixinNoticeDTO.setBalance(cardConsume.getConsumeBalance());
		weixinNoticeDTO.setPrintedNum(cardConsume.getConsumePrintedNum());
		weixinNoticeDTO.setSystemBookCode(cardConsume.getSystemBookCode());
		weixinNoticeDTO.setFid(cardConsume.getConsumeFid());
		weixinNoticeDTO.setBranchNum(cardConsume.getBranchNum());
		weixinNoticeDTO.setCardUrl(WeixinCardType.getCardUrl(weixinCardTypes, cardConsume.getConsumeCardType()));
		return weixinNoticeDTO;
	}

}
