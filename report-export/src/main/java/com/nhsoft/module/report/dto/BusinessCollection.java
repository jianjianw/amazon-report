package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusinessCollection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8448182988576779396L;
	private Integer branchNum;
	private String branchName;
	private Integer merchantNum;
	private String merchantCode;
	private String merchantName;
	private Integer stallNum;
	private String stallCode;
	private String stallName;
	private Integer shiftTableNum; // 交班编号
	private String shiftTableBizday; // 营业日
	private String posMachineName; // pos终端名称 十六进制字符串 对应的PosMachineData 中的 id
	private String shiftTableTerminalId;
	private BigDecimal unPaidMoney = BigDecimal.ZERO;  //未扣款金额
	private BigDecimal rePaidMoney = BigDecimal.ZERO;	//补扣金额
	private BigDecimal signInMoney = BigDecimal.ZERO;//签收金额
	private Date shiftTableStart;
	private Date shiftTableEnd;
	private BigDecimal receiveCash = BigDecimal.ZERO;//交班时录入现金
	private BigDecimal differenceCash = BigDecimal.ZERO;//差额=现金汇总-录入现金
	private BigDecimal allBankMoney = BigDecimal.ZERO;
	private BigDecimal receiveBankMoney = BigDecimal.ZERO;
	private BigDecimal differenceBankMoney = BigDecimal.ZERO;
	private BigDecimal oldCardChangeMoney = BigDecimal.ZERO; //老会员转卡金额
	private BigDecimal cardRevokeMoney = BigDecimal.ZERO; //卡回收金额
	private BigDecimal signInDiscountMoney = BigDecimal.ZERO;//签收优惠金额（客户结算折扣金额）
	private BigDecimal cardChangeInMoney = BigDecimal.ZERO; //零钱包收入金额
	// pos收入
	private List<BusinessCollectionIncome> posIncomes = new ArrayList<BusinessCollectionIncome>();

	// 卡收入
	private List<BusinessCollectionIncome> cardIncomes = new ArrayList<BusinessCollectionIncome>();

	// 其他收入
	private List<BusinessCollectionIncome> otherIncomes = new ArrayList<BusinessCollectionIncome>();

	// 续卡收入收入
	private List<BusinessCollectionIncome> relateCardIncomes = new ArrayList<BusinessCollectionIncome>();

	//换卡收入
	private List<BusinessCollectionIncome> changeCardIncomes = new ArrayList<BusinessCollectionIncome>();
	//消费券收入
	private List<BusinessCollectionIncome> ticketIncomes = new ArrayList<BusinessCollectionIncome>();
	
	private BigDecimal allMoney = BigDecimal.ZERO; // 现金汇总
	
	private BigDecimal allDiscountMoney = BigDecimal.ZERO;//总折扣
	//营业收款报表（班次）使用
	private String casher;//收银员
	
	public String getShiftTableTerminalId() {
		return shiftTableTerminalId;
	}
	
	public void setShiftTableTerminalId(String shiftTableTerminalId) {
		this.shiftTableTerminalId = shiftTableTerminalId;
	}
	
	public BigDecimal getCardChangeInMoney() {
		return cardChangeInMoney;
	}

	public void setCardChangeInMoney(BigDecimal cardChangeInMoney) {
		this.cardChangeInMoney = cardChangeInMoney;
	}

	public BigDecimal getSignInDiscountMoney() {
		return signInDiscountMoney;
	}

	public void setSignInDiscountMoney(BigDecimal signInDiscountMoney) {
		this.signInDiscountMoney = signInDiscountMoney;
	}

	public BigDecimal getCardRevokeMoney() {
		return cardRevokeMoney;
	}

	public void setCardRevokeMoney(BigDecimal cardRevokeMoney) {
		this.cardRevokeMoney = cardRevokeMoney;
	}

	public BigDecimal getOldCardChangeMoney() {
		return oldCardChangeMoney;
	}

	public void setOldCardChangeMoney(BigDecimal oldCardChangeMoney) {
		this.oldCardChangeMoney = oldCardChangeMoney;
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

	public Integer getShiftTableNum() {
		return shiftTableNum;
	}

	public void setShiftTableNum(Integer shiftTableNum) {
		this.shiftTableNum = shiftTableNum;
	}

	public String getShiftTableBizday() {
		return shiftTableBizday;
	}

	public void setShiftTableBizday(String shiftTableBizday) {
		this.shiftTableBizday = shiftTableBizday;
	}

	public String getPosMachineName() {
		return posMachineName;
	}

	public void setPosMachineName(String posMachineName) {
		this.posMachineName = posMachineName;
	}

	public List<BusinessCollectionIncome> getPosIncomes() {
		return posIncomes;
	}

	public void setPosIncomes(List<BusinessCollectionIncome> posIncomes) {
		this.posIncomes = posIncomes;
	}

	public List<BusinessCollectionIncome> getCardIncomes() {
		return cardIncomes;
	}

	public void setCardIncomes(List<BusinessCollectionIncome> cardIncomes) {
		this.cardIncomes = cardIncomes;
	}

	public List<BusinessCollectionIncome> getOtherIncomes() {
		return otherIncomes;
	}

	public void setOtherIncomes(List<BusinessCollectionIncome> otherIncomes) {
		this.otherIncomes = otherIncomes;
	}

	public List<BusinessCollectionIncome> getRelateCardIncomes() {
		return relateCardIncomes;
	}

	public void setRelateCardIncomes(
			List<BusinessCollectionIncome> relateCardIncomes) {
		this.relateCardIncomes = relateCardIncomes;
	}

	public BigDecimal getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}

	public BigDecimal getUnPaidMoney() {
		return unPaidMoney;
	}

	public void setUnPaidMoney(BigDecimal unPaidMoney) {
		this.unPaidMoney = unPaidMoney;
	}

	public BigDecimal getRePaidMoney() {
		return rePaidMoney;
	}

	public void setRePaidMoney(BigDecimal rePaidMoney) {
		this.rePaidMoney = rePaidMoney;
	}

	public List<BusinessCollectionIncome> getChangeCardIncomes() {
		return changeCardIncomes;
	}

	public void setChangeCardIncomes(
			List<BusinessCollectionIncome> changeCardIncomes) {
		this.changeCardIncomes = changeCardIncomes;
	}

	public List<BusinessCollectionIncome> getTicketIncomes() {
		return ticketIncomes;
	}

	public void setTicketIncomes(List<BusinessCollectionIncome> ticketIncomes) {
		this.ticketIncomes = ticketIncomes;
	}

	public BigDecimal getSignInMoney() {
		return signInMoney;
	}

	public void setSignInMoney(BigDecimal signInMoney) {
		this.signInMoney = signInMoney;
	}

	public String getCasher() {
		return casher;
	}

	public void setCasher(String casher) {
		this.casher = casher;
	}

	public BigDecimal getAllDiscountMoney() {
		return allDiscountMoney;
	}

	public void setAllDiscountMoney(BigDecimal allDiscountMoney) {
		this.allDiscountMoney = allDiscountMoney;
	}

	public Date getShiftTableStart() {
		return shiftTableStart;
	}

	public void setShiftTableStart(Date shiftTableStart) {
		this.shiftTableStart = shiftTableStart;
	}

	public Date getShiftTableEnd() {
		return shiftTableEnd;
	}

	public void setShiftTableEnd(Date shiftTableEnd) {
		this.shiftTableEnd = shiftTableEnd;
	}

	public BigDecimal getReceiveCash() {
		return receiveCash;
	}

	public void setReceiveCash(BigDecimal receiveCash) {
		this.receiveCash = receiveCash;
	}

	public BigDecimal getDifferenceCash() {
		return differenceCash;
	}

	public void setDifferenceCash(BigDecimal differenceCash) {
		this.differenceCash = differenceCash;
	}

	public BigDecimal getAllBankMoney() {
		return allBankMoney;
	}

	public void setAllBankMoney(BigDecimal allBankMoney) {
		this.allBankMoney = allBankMoney;
	}

	public BigDecimal getReceiveBankMoney() {
		return receiveBankMoney;
	}

	public void setReceiveBankMoney(BigDecimal receiveBankMoney) {
		this.receiveBankMoney = receiveBankMoney;
	}

	public BigDecimal getDifferenceBankMoney() {
		return differenceBankMoney;
	}

	public void setDifferenceBankMoney(BigDecimal differenceBankMoney) {
		this.differenceBankMoney = differenceBankMoney;
	}

	public Integer getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(Integer merchantNum) {
		this.merchantNum = merchantNum;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getStallNum() {
		return stallNum;
	}

	public void setStallNum(Integer stallNum) {
		this.stallNum = stallNum;
	}

	public String getStallCode() {
		return stallCode;
	}

	public void setStallCode(String stallCode) {
		this.stallCode = stallCode;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
}
