package com.nhsoft.module.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhsoft.amazon.shared.AppConstants;
import com.nhsoft.report.utils.DateUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Supplier entity. @author MyEclipse Persistence Tools
 */

@Entity
public class Supplier implements java.io.Serializable {

	private static final long serialVersionUID = -7209508044762750480L;
	@Id
	private Integer supplierNum;
	private String abchinaAccountId;
	private String systemBookCode;
	private String supplierCode;
	private String supplierName;
	private String supplierPin;
	private String supplierKind;
	private String supplierBank;
	private String supplierBankAccount;
	private String supplierTaxNo;
	private String supplierPhone;
	private String supplierFax;
	private String supplierPostcode;
	private String supplierAddr;
	private String supplierLinkman;
	private String supplierLinktel;
	private String supplierSite;
	private String supplierEmail;
	private Boolean supplierActived;
	private String supplierMemo;
	private Boolean supplierFlag;
	private Boolean supplierDelTag;
	private String supplierSettlementType;
	private Integer supplierSettlePeriod;
	private Integer branchNum;
	private Short supplierSettleDayOfMonth;
	private String supplierMethod;
	private Boolean supplierShared;// 供应商共享
	private Integer supplierPurchasePeriod;// 采购周期
	private String supplierPurchasePeriodUnit;// 采购周期单位
	private Date supplierPurchaseDate;// 首次/上次采购时间
	private Integer supplierPurchaseDeadline;// 交货期限
	private BigDecimal supplierCarriage;
	private Date supplierLastEditTime;
	private Date supplierCreateTime;
	private String supplierCreator;
	private String supplierSettleDays;
	
	@Transient
	private List<SupplierShareBranch> supplierSharedBranches;
	
	// 界面使用
	@Transient
	private BigDecimal itemPrice;// 采购单价
	@Transient
	private BigDecimal lastPrice;// 最后一次进价
	@Transient
	private String storeItemReturnType;
	@Transient
	private AppUser appUser;

	public Supplier() {

	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getSupplierSettleDays() {
		return supplierSettleDays;
	}

	public void setSupplierSettleDays(String supplierSettleDays) {
		this.supplierSettleDays = supplierSettleDays;
	}

	public String getStoreItemReturnType() {
		return storeItemReturnType;
	}

	public void setStoreItemReturnType(String storeItemReturnType) {
		this.storeItemReturnType = storeItemReturnType;
	}

	public Date getSupplierCreateTime() {
		return supplierCreateTime;
	}

	public void setSupplierCreateTime(Date supplierCreateTime) {
		this.supplierCreateTime = supplierCreateTime;
	}

	public String getSupplierCreator() {
		return supplierCreator;
	}

	public void setSupplierCreator(String supplierCreator) {
		this.supplierCreator = supplierCreator;
	}

	public Date getSupplierLastEditTime() {
		return supplierLastEditTime;
	}

	public void setSupplierLastEditTime(Date supplierLastEditTime) {
		this.supplierLastEditTime = supplierLastEditTime;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Supplier(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public Boolean getSupplierShared() {
		return supplierShared;
	}

	public void setSupplierShared(Boolean supplierShared) {
		this.supplierShared = supplierShared;
	}

	public Integer getSupplierPurchasePeriod() {
		return supplierPurchasePeriod;
	}

	public void setSupplierPurchasePeriod(Integer supplierPurchasePeriod) {
		this.supplierPurchasePeriod = supplierPurchasePeriod;
	}

	public String getSupplierPurchasePeriodUnit() {
		return supplierPurchasePeriodUnit;
	}

	public void setSupplierPurchasePeriodUnit(String supplierPurchasePeriodUnit) {
		this.supplierPurchasePeriodUnit = supplierPurchasePeriodUnit;
	}

	public Date getSupplierPurchaseDate() {
		return supplierPurchaseDate;
	}

	public void setSupplierPurchaseDate(Date supplierPurchaseDate) {
		this.supplierPurchaseDate = supplierPurchaseDate;
	}

	public Integer getSupplierPurchaseDeadline() {
		return supplierPurchaseDeadline;
	}

	public void setSupplierPurchaseDeadline(Integer supplierPurchaseDeadline) {
		this.supplierPurchaseDeadline = supplierPurchaseDeadline;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public String getAbchinaAccountId() {
		return abchinaAccountId;
	}

	public void setAbchinaAccountId(String abchinaAccountId) {
		this.abchinaAccountId = abchinaAccountId;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierPin() {
		return supplierPin;
	}

	public void setSupplierPin(String supplierPin) {
		this.supplierPin = supplierPin;
	}

	public String getSupplierKind() {
		return supplierKind;
	}

	public void setSupplierKind(String supplierKind) {
		this.supplierKind = supplierKind;
	}

	public String getSupplierBank() {
		return supplierBank;
	}

	public void setSupplierBank(String supplierBank) {
		this.supplierBank = supplierBank;
	}

	public String getSupplierBankAccount() {
		return supplierBankAccount;
	}

	public void setSupplierBankAccount(String supplierBankAccount) {
		this.supplierBankAccount = supplierBankAccount;
	}

	public String getSupplierTaxNo() {
		return supplierTaxNo;
	}

	public void setSupplierTaxNo(String supplierTaxNo) {
		this.supplierTaxNo = supplierTaxNo;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public String getSupplierFax() {
		return supplierFax;
	}

	public void setSupplierFax(String supplierFax) {
		this.supplierFax = supplierFax;
	}

	public String getSupplierPostcode() {
		return supplierPostcode;
	}

	public void setSupplierPostcode(String supplierPostcode) {
		this.supplierPostcode = supplierPostcode;
	}

	public String getSupplierAddr() {
		return supplierAddr;
	}

	public void setSupplierAddr(String supplierAddr) {
		this.supplierAddr = supplierAddr;
	}

	public String getSupplierLinkman() {
		return supplierLinkman;
	}

	public void setSupplierLinkman(String supplierLinkman) {
		this.supplierLinkman = supplierLinkman;
	}

	public String getSupplierLinktel() {
		return supplierLinktel;
	}

	public void setSupplierLinktel(String supplierLinktel) {
		this.supplierLinktel = supplierLinktel;
	}

	public String getSupplierSite() {
		return supplierSite;
	}

	public void setSupplierSite(String supplierSite) {
		this.supplierSite = supplierSite;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public Boolean getSupplierActived() {
		return supplierActived;
	}

	public void setSupplierActived(Boolean supplierActived) {
		this.supplierActived = supplierActived;
	}

	public String getSupplierMemo() {
		return supplierMemo;
	}

	public void setSupplierMemo(String supplierMemo) {
		this.supplierMemo = supplierMemo;
	}

	public Boolean getSupplierFlag() {
		return supplierFlag;
	}

	public void setSupplierFlag(Boolean supplierFlag) {
		this.supplierFlag = supplierFlag;
	}

	public Boolean getSupplierDelTag() {
		return supplierDelTag;
	}

	public void setSupplierDelTag(Boolean supplierDelTag) {
		this.supplierDelTag = supplierDelTag;
	}

	public String getSupplierSettlementType() {
		return supplierSettlementType;
	}

	public void setSupplierSettlementType(String supplierSettlementType) {
		this.supplierSettlementType = supplierSettlementType;
	}

	public Integer getSupplierSettlePeriod() {
		return supplierSettlePeriod;
	}

	public void setSupplierSettlePeriod(Integer supplierSettlePeriod) {
		this.supplierSettlePeriod = supplierSettlePeriod;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Short getSupplierSettleDayOfMonth() {
		return supplierSettleDayOfMonth;
	}

	public void setSupplierSettleDayOfMonth(Short supplierSettleDayOfMonth) {
		this.supplierSettleDayOfMonth = supplierSettleDayOfMonth;
	}

	public String getSupplierMethod() {
		return supplierMethod;
	}

	public void setSupplierMethod(String supplierMethod) {
		this.supplierMethod = supplierMethod;
	}

	public BigDecimal getSupplierCarriage() {
		return supplierCarriage;
	}

	public void setSupplierCarriage(BigDecimal supplierCarriage) {
		this.supplierCarriage = supplierCarriage;
	}
	
	public List<SupplierShareBranch> getSupplierSharedBranches() {
		return supplierSharedBranches;
	}

	public void setSupplierSharedBranches(List<SupplierShareBranch> supplierSharedBranches) {
		this.supplierSharedBranches = supplierSharedBranches;
	}
	
	@JsonIgnore
	public Date calcSettleDate(){
		
		Date now = Calendar.getInstance().getTime();
		if (supplierSettlementType == null){
			return now;
		}
		if (supplierSettlementType.equals(AppConstants.SETTLE_TYPE_TEMP)){
			return now;
		}else if (supplierSettlementType.equals(AppConstants.SETTLE_TYPE_FIXED_DATE)){
			return DateUtil.addDay(now, getSupplierSettlePeriod());
		}else if (supplierSettlementType.equals(AppConstants.SETTLE_TYPE_FIXED_DAY)){
			
			Calendar c = Calendar.getInstance();
			
			int day = c.get(Calendar.DAY_OF_MONTH);
			if (day > supplierSettleDayOfMonth){
				c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
			}	
			c.set(Calendar.DAY_OF_MONTH, supplierSettleDayOfMonth);
			return c.getTime();

		}else if (getSupplierSettlementType().equals(AppConstants.SETTLE_TYPE_FIXED_NOW)){
			return now;
		}
		return now;
	}
	
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		
		int day = c.get(Calendar.DAY_OF_MONTH);
		System.out.println(day);
	}

	public static Supplier get(List<Supplier> suppliers, Integer supplierNum) {
		if(suppliers == null) {
			return null;
		}
		return suppliers.stream().filter(s -> s.getSupplierNum().equals(supplierNum)).findFirst().orElse(null);
	}

}