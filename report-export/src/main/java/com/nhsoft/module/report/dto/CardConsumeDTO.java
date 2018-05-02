package com.nhsoft.module.report.dto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CardConsume generated by hbm2java
 */
@SuppressWarnings("rawtypes")
public class CardConsumeDTO implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(CardConsumeDTO.class);
	private static final long serialVersionUID = 2243541647048389784L;
	private String consumeFid;
	private String systemBookCode;
	private Integer branchNum;
	private Integer shiftTableNum;
	private String shiftTableBizday;
	private Integer consumeCustNum;
	private Integer consumeCardType;
	private String consumePrintedNum;
	private Integer consumeCategory;
	private String consumeType;
	private BigDecimal consumeBalance;
	private BigDecimal consumeTotalMoney;
	private BigDecimal consumePoint;
	private String consumePhysicalNum;
	private BigDecimal consumeDiscount;
	private BigDecimal consumeRound;
	private BigDecimal consumeMoney;
	private BigDecimal consumeInvoice;
	private Date consumeDate;
	private String consumeOperator;
	private String consumeBillNum;
	private Integer consumeCount;
	private String consumeMemo;
	private String consumeBranchName;
	private String consumeCustName;
	private Integer consumeBalanceDetailNum;
	private Boolean consumeSettlementFlag;
	private Boolean consumeRevokeFlag;
	private Boolean consumeWinningFlag;
	private String consumeOrderNo;
	private Boolean consumeRecardFlag;
	private String consumeMachine;
	private Boolean consumeRepealed;
	private Boolean consumeNoticeFlag;
	private String consumeOpenId;
	private String consumeAntiFid;
	private String consumeSeller;
	private Date consumeSynchDate;
	private BigDecimal consumeRepealedMoney;
	private String consumeSource;
	private Integer merchantNum;
	private Integer stallNum;
	private Boolean consumeMerchantFlag;
	
	//临时属性
	private BigDecimal consumeWinningMaxMoney;
	private BigDecimal consumeWinningMinMoney;
	private String consumeWinningNums;
	private Date consumeWinningStart;
	private String consumeCrypt;
	private String reSendFid;//重新上传原单据号
	private CardUserDTO cardUser;

	public CardUserDTO getCardUser() {
		return cardUser;
	}

	public void setCardUser(CardUserDTO cardUser) {
		this.cardUser = cardUser;
	}

	public BigDecimal getConsumeRepealedMoney() {
		return consumeRepealedMoney;
	}
	
	public void setConsumeRepealedMoney(BigDecimal consumeRepealedMoney) {
		this.consumeRepealedMoney = consumeRepealedMoney;
	}
	
	public CardConsumeDTO() {
		setConsumeSettlementFlag(false);
	}

	public String getReSendFid() {
		return reSendFid;
	}

	public void setReSendFid(String reSendFid) {
		this.reSendFid = reSendFid;
	}

	public Date getConsumeSynchDate() {
		return consumeSynchDate;
	}

	public void setConsumeSynchDate(Date consumeSynchDate) {
		this.consumeSynchDate = consumeSynchDate;
	}

	public Date getConsumeWinningStart() {
		return consumeWinningStart;
	}

	public void setConsumeWinningStart(Date consumeWinningStart) {
		this.consumeWinningStart = consumeWinningStart;
	}

	public String getConsumeWinningNums() {
		return consumeWinningNums;
	}

	public void setConsumeWinningNums(String consumeWinningNums) {
		this.consumeWinningNums = consumeWinningNums;
	}

	public String getConsumeCrypt() {
		return consumeCrypt;
	}

	public void setConsumeCrypt(String consumeCrypt) {
		this.consumeCrypt = consumeCrypt;
	}

	public String getConsumeSeller() {
		return consumeSeller;
	}

	public void setConsumeSeller(String consumeSeller) {
		this.consumeSeller = consumeSeller;
	}

	public String getConsumeAntiFid() {
		return consumeAntiFid;
	}

	public void setConsumeAntiFid(String consumeAntiFid) {
		this.consumeAntiFid = consumeAntiFid;
	}

	public BigDecimal getConsumeWinningMinMoney() {
		return consumeWinningMinMoney;
	}

	public void setConsumeWinningMinMoney(BigDecimal consumeWinningMinMoney) {
		this.consumeWinningMinMoney = consumeWinningMinMoney;
	}

	public BigDecimal getConsumeWinningMaxMoney() {
		return consumeWinningMaxMoney;
	}

	public void setConsumeWinningMaxMoney(BigDecimal consumeWinningMaxMoney) {
		this.consumeWinningMaxMoney = consumeWinningMaxMoney;
	}

	public Boolean getConsumeRepealed() {
		return consumeRepealed;
	}

	public void setConsumeRepealed(Boolean consumeRepealed) {
		this.consumeRepealed = consumeRepealed;
	}

	public String getConsumeFid() {
		return this.consumeFid;
	}

	public void setConsumeFid(String consumeFid) {
		this.consumeFid = consumeFid;
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

	public String getConsumeOrderNo() {
		return consumeOrderNo;
	}

	public void setConsumeOrderNo(String consumeOrderNo) {
		this.consumeOrderNo = consumeOrderNo;
	}

	public Integer getConsumeCustNum() {
		return this.consumeCustNum;
	}

	public void setConsumeCustNum(Integer consumeCustNum) {
		this.consumeCustNum = consumeCustNum;
	}

	public Integer getConsumeCardType() {
		return this.consumeCardType;
	}

	public void setConsumeCardType(Integer consumeCardType) {
		this.consumeCardType = consumeCardType;
	}

	public String getConsumePrintedNum() {
		return this.consumePrintedNum;
	}

	public void setConsumePrintedNum(String consumePrintedNum) {
		this.consumePrintedNum = consumePrintedNum;
	}

	public Integer getConsumeCategory() {
		return this.consumeCategory;
	}

	public void setConsumeCategory(Integer consumeCategory) {
		this.consumeCategory = consumeCategory;
	}

	public String getConsumeType() {
		return this.consumeType;
	}

	public void setConsumeType(String consumeType) {
		this.consumeType = consumeType;
	}

	public BigDecimal getConsumeBalance() {
		return this.consumeBalance;
	}

	public void setConsumeBalance(BigDecimal consumeBalance) {
		this.consumeBalance = consumeBalance;
	}

	public BigDecimal getConsumeTotalMoney() {
		return this.consumeTotalMoney;
	}

	public void setConsumeTotalMoney(BigDecimal consumeTotalMoney) {
		this.consumeTotalMoney = consumeTotalMoney;
	}

	public BigDecimal getConsumePoint() {
		return this.consumePoint;
	}

	public void setConsumePoint(BigDecimal consumePoint) {
		this.consumePoint = consumePoint;
	}

	public String getConsumePhysicalNum() {
		return this.consumePhysicalNum;
	}

	public void setConsumePhysicalNum(String consumePhysicalNum) {
		this.consumePhysicalNum = consumePhysicalNum;
	}

	public BigDecimal getConsumeDiscount() {
		return this.consumeDiscount;
	}

	public void setConsumeDiscount(BigDecimal consumeDiscount) {
		this.consumeDiscount = consumeDiscount;
	}

	public BigDecimal getConsumeRound() {
		return this.consumeRound;
	}

	public void setConsumeRound(BigDecimal consumeRound) {
		this.consumeRound = consumeRound;
	}

	public BigDecimal getConsumeMoney() {
		return this.consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public BigDecimal getConsumeInvoice() {
		return this.consumeInvoice;
	}

	public void setConsumeInvoice(BigDecimal consumeInvoice) {
		this.consumeInvoice = consumeInvoice;
	}

	public Date getConsumeDate() {
		return this.consumeDate;
	}

	public void setConsumeDate(Date consumeDate) {
		this.consumeDate = consumeDate;
	}

	public String getConsumeOperator() {
		return this.consumeOperator;
	}

	public void setConsumeOperator(String consumeOperator) {
		this.consumeOperator = consumeOperator;
	}

	public String getConsumeBillNum() {
		return this.consumeBillNum;
	}

	public void setConsumeBillNum(String consumeBillNum) {
		this.consumeBillNum = consumeBillNum;
	}

	public Integer getConsumeCount() {
		return this.consumeCount;
	}

	public void setConsumeCount(Integer consumeCount) {
		this.consumeCount = consumeCount;
	}

	public String getConsumeMemo() {
		return this.consumeMemo;
	}

	public void setConsumeMemo(String consumeMemo) {
		this.consumeMemo = consumeMemo;
	}

	public String getConsumeBranchName() {
		return this.consumeBranchName;
	}

	public void setConsumeBranchName(String consumeBranchName) {
		this.consumeBranchName = consumeBranchName;
	}

	public String getConsumeCustName() {
		return this.consumeCustName;
	}

	public void setConsumeCustName(String consumeCustName) {
		this.consumeCustName = consumeCustName;
	}

	public Integer getConsumeBalanceDetailNum() {
		return this.consumeBalanceDetailNum;
	}

	public void setConsumeBalanceDetailNum(Integer consumeBalanceDetailNum) {
		this.consumeBalanceDetailNum = consumeBalanceDetailNum;
	}

	public Boolean getConsumeSettlementFlag() {
		return this.consumeSettlementFlag;
	}

	public void setConsumeSettlementFlag(Boolean consumeSettlementFlag) {
		this.consumeSettlementFlag = consumeSettlementFlag;
	}

	public Boolean getConsumeRevokeFlag() {
		return this.consumeRevokeFlag;
	}

	public void setConsumeRevokeFlag(Boolean consumeRevokeFlag) {
		this.consumeRevokeFlag = consumeRevokeFlag;
	}

	public Boolean getConsumeWinningFlag() {
		return consumeWinningFlag;
	}

	public void setConsumeWinningFlag(Boolean consumeWinningFlag) {
		this.consumeWinningFlag = consumeWinningFlag;
	}

	public Boolean getConsumeRecardFlag() {
		return consumeRecardFlag;
	}

	public void setConsumeRecardFlag(Boolean consumeRecardFlag) {
		this.consumeRecardFlag = consumeRecardFlag;
	}

	public String getConsumeMachine() {
		return consumeMachine;
	}

	public void setConsumeMachine(String consumeMachine) {
		this.consumeMachine = consumeMachine;
	}

	public Boolean getConsumeNoticeFlag() {
		return consumeNoticeFlag;
	}

	public void setConsumeNoticeFlag(Boolean consumeNoticeFlag) {
		this.consumeNoticeFlag = consumeNoticeFlag;
	}

	public String getConsumeOpenId() {
		return consumeOpenId;
	}

	public void setConsumeOpenId(String consumeOpenId) {
		this.consumeOpenId = consumeOpenId;
	}

	public String getConsumeSource() {
		return consumeSource;
	}

	public void setConsumeSource(String consumeSource) {
		this.consumeSource = consumeSource;
	}

	public Integer getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(Integer merchantNum) {
		this.merchantNum = merchantNum;
	}

	public Integer getStallNum() {
		return stallNum;
	}

	public void setStallNum(Integer stallNum) {
		this.stallNum = stallNum;
	}

	public Boolean getConsumeMerchantFlag() {
		return consumeMerchantFlag;
	}

	public void setConsumeMerchantFlag(Boolean consumeMerchantFlag) {
		this.consumeMerchantFlag = consumeMerchantFlag;
	}
}
