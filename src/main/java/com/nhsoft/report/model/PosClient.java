package com.nhsoft.report.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

/**
 * PosClient generated by hbm2java
 */
public class PosClient implements java.io.Serializable {

	private static final long serialVersionUID = 4096913637241441771L;
	private String clientFid;
	private String systemBookCode;
	private Integer branchNum;
	private String clientName;
	private String clientCode;
	private String clientPinyin;
	private Date clientBirth;
	private String clientCompany;
	private String clientAddr01;
	private String clientAddr02;
	private String clientZip;
	private String clientEmail;
	private String clientWebSite;
	private String clientPhone;
	private String clientFax;
	private String clientMobile;
	private String clientType;
	private Date clientLastPurchase;
	private Date clientLastEmailDate;
	private Date clientLastSmsDate;
	private BigDecimal clientUsualDiscount;
	private short clientPriceLevel;
	private String clientShipAddr;
	private String clientNoteInfo;
	private String clientAcctNo;
	private BigDecimal clientCreditLimit;
	private boolean clientTax1Exemption;
	private boolean clientTax2Exemption;
	private boolean clientFlag;
	@JsonIgnore
	private Blob clientPicture;
	private boolean clientDelTag;
	private Date clientLastEditTime;
	private String clientSettlementType;
	private Integer clientSettlePeriod;
	private Short clientSettleDayOfMonth;
	private String clientBarcode;
	private Boolean isDealatClient;
	private Boolean isRetailClient;
	private Boolean clientActived;
	private BigDecimal clientWholesaleDiscount;
	private Integer clientWholesaleLevel;
	private Integer regionNum;
	private Boolean clientShared;
	private BigDecimal clientJoiningFee;
	private Integer clientGradeNum;
	private Date clientCreateTime;
	private String clientLinkman;
	
	
	public String getClientLinkman() {
		return clientLinkman;
	}
	
	public void setClientLinkman(String clientLinkman) {
		this.clientLinkman = clientLinkman;
	}
	
	private AppUser appUser;
	
	public PosClient() {
	}

	public Date getClientCreateTime() {
		return clientCreateTime;
	}

	public void setClientCreateTime(Date clientCreateTime) {
		this.clientCreateTime = clientCreateTime;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Integer getClientGradeNum() {
		return clientGradeNum;
	}

	public void setClientGradeNum(Integer clientGradeNum) {
		this.clientGradeNum = clientGradeNum;
	}

	public BigDecimal getClientJoiningFee() {
		return clientJoiningFee;
	}

	public void setClientJoiningFee(BigDecimal clientJoiningFee) {
		this.clientJoiningFee = clientJoiningFee;
	}

	public Integer getRegionNum() {
		return regionNum;
	}

	public void setRegionNum(Integer regionNum) {
		this.regionNum = regionNum;
	}

	public BigDecimal getClientWholesaleDiscount() {
		return clientWholesaleDiscount;
	}

	public void setClientWholesaleDiscount(BigDecimal clientWholesaleDiscount) {
		this.clientWholesaleDiscount = clientWholesaleDiscount;
	}

	public Integer getClientWholesaleLevel() {
		return clientWholesaleLevel;
	}

	public void setClientWholesaleLevel(Integer clientWholesaleLevel) {
		this.clientWholesaleLevel = clientWholesaleLevel;
	}

	public String getClientFid() {
		return this.clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
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

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientCode() {
		return this.clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientPinyin() {
		return this.clientPinyin;
	}

	public void setClientPinyin(String clientPinyin) {
		this.clientPinyin = clientPinyin;
	}

	public Date getClientBirth() {
		return this.clientBirth;
	}

	public void setClientBirth(Date clientBirth) {
		this.clientBirth = clientBirth;
	}

	public String getClientCompany() {
		return this.clientCompany;
	}

	public void setClientCompany(String clientCompany) {
		this.clientCompany = clientCompany;
	}

	public String getClientAddr01() {
		return this.clientAddr01;
	}

	public void setClientAddr01(String clientAddr01) {
		this.clientAddr01 = clientAddr01;
	}

	public String getClientAddr02() {
		return this.clientAddr02;
	}

	public void setClientAddr02(String clientAddr02) {
		this.clientAddr02 = clientAddr02;
	}

	public String getClientZip() {
		return this.clientZip;
	}

	public void setClientZip(String clientZip) {
		this.clientZip = clientZip;
	}

	public String getClientEmail() {
		return this.clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getClientWebSite() {
		return this.clientWebSite;
	}

	public void setClientWebSite(String clientWebSite) {
		this.clientWebSite = clientWebSite;
	}

	public String getClientPhone() {
		return this.clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getClientFax() {
		return this.clientFax;
	}

	public void setClientFax(String clientFax) {
		this.clientFax = clientFax;
	}

	public String getClientMobile() {
		return this.clientMobile;
	}

	public void setClientMobile(String clientMobile) {
		this.clientMobile = clientMobile;
	}

	public String getClientType() {
		return this.clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Date getClientLastPurchase() {
		return this.clientLastPurchase;
	}

	public void setClientLastPurchase(Date clientLastPurchase) {
		this.clientLastPurchase = clientLastPurchase;
	}

	public Date getClientLastEmailDate() {
		return this.clientLastEmailDate;
	}

	public void setClientLastEmailDate(Date clientLastEmailDate) {
		this.clientLastEmailDate = clientLastEmailDate;
	}

	public Date getClientLastSmsDate() {
		return this.clientLastSmsDate;
	}

	public void setClientLastSmsDate(Date clientLastSmsDate) {
		this.clientLastSmsDate = clientLastSmsDate;
	}

	public BigDecimal getClientUsualDiscount() {
		return this.clientUsualDiscount;
	}

	public void setClientUsualDiscount(BigDecimal clientUsualDiscount) {
		this.clientUsualDiscount = clientUsualDiscount;
	}

	public short getClientPriceLevel() {
		return this.clientPriceLevel;
	}

	public void setClientPriceLevel(short clientPriceLevel) {
		this.clientPriceLevel = clientPriceLevel;
	}

	public String getClientShipAddr() {
		return this.clientShipAddr;
	}

	public void setClientShipAddr(String clientShipAddr) {
		this.clientShipAddr = clientShipAddr;
	}

	public String getClientNoteInfo() {
		return this.clientNoteInfo;
	}

	public void setClientNoteInfo(String clientNoteInfo) {
		this.clientNoteInfo = clientNoteInfo;
	}

	public String getClientAcctNo() {
		return this.clientAcctNo;
	}

	public void setClientAcctNo(String clientAcctNo) {
		this.clientAcctNo = clientAcctNo;
	}

	public BigDecimal getClientCreditLimit() {
		return this.clientCreditLimit;
	}

	public void setClientCreditLimit(BigDecimal clientCreditLimit) {
		this.clientCreditLimit = clientCreditLimit;
	}

	public boolean isClientTax1Exemption() {
		return this.clientTax1Exemption;
	}

	public void setClientTax1Exemption(boolean clientTax1Exemption) {
		this.clientTax1Exemption = clientTax1Exemption;
	}

	public boolean isClientTax2Exemption() {
		return this.clientTax2Exemption;
	}

	public void setClientTax2Exemption(boolean clientTax2Exemption) {
		this.clientTax2Exemption = clientTax2Exemption;
	}

	public boolean isClientFlag() {
		return this.clientFlag;
	}

	public void setClientFlag(boolean clientFlag) {
		this.clientFlag = clientFlag;
	}

	public Blob getClientPicture() {
		return this.clientPicture;
	}

	public void setClientPicture(Blob clientPicture) {
		this.clientPicture = clientPicture;
	}

	public boolean isClientDelTag() {
		return this.clientDelTag;
	}

	public void setClientDelTag(boolean clientDelTag) {
		this.clientDelTag = clientDelTag;
	}

	public Date getClientLastEditTime() {
		return this.clientLastEditTime;
	}

	public void setClientLastEditTime(Date clientLastEditTime) {
		this.clientLastEditTime = clientLastEditTime;
	}

	public String getClientSettlementType() {
		return this.clientSettlementType;
	}

	public void setClientSettlementType(String clientSettlementType) {
		this.clientSettlementType = clientSettlementType;
	}

	public Integer getClientSettlePeriod() {
		return this.clientSettlePeriod;
	}

	public void setClientSettlePeriod(Integer clientSettlePeriod) {
		this.clientSettlePeriod = clientSettlePeriod;
	}

	public Short getClientSettleDayOfMonth() {
		return this.clientSettleDayOfMonth;
	}

	public void setClientSettleDayOfMonth(Short clientSettleDayOfMonth) {
		this.clientSettleDayOfMonth = clientSettleDayOfMonth;
	}

	public String getClientBarcode() {
		return this.clientBarcode;
	}

	public void setClientBarcode(String clientBarcode) {
		this.clientBarcode = clientBarcode;
	}

	public Boolean getIsDealatClient() {
		return isDealatClient;
	}

	public void setIsDealatClient(Boolean isDealatClient) {
		this.isDealatClient = isDealatClient;
	}

	public Boolean getIsRetailClient() {
		return isRetailClient;
	}

	public void setIsRetailClient(Boolean isRetailClient) {
		this.isRetailClient = isRetailClient;
	}

	public Boolean getClientActived() {
		return clientActived;
	}

	public void setClientActived(Boolean clientActived) {
		this.clientActived = clientActived;
	}

	public Boolean getClientShared() {
		return clientShared;
	}

	public void setClientShared(Boolean clientShared) {
		this.clientShared = clientShared;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientFid == null) ? 0 : clientFid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PosClient other = (PosClient) obj;
		if (clientFid == null) {
			if (other.clientFid != null)
				return false;
		} else if (!clientFid.equals(other.clientFid))
			return false;
		return true;
	}

}
