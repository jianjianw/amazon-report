package com.nhsoft.report.dto;

import com.nhsoft.pos3.shared.AppConstants;

import java.io.Serializable;

public class PosShopAccount implements Serializable {

	private static final long serialVersionUID = 7325533056625390920L;

	private String accountId;
	private String accountSecret;
	private String accountType;
	private String systemBookCode;
	private Boolean youzanAuthorize; //是否有赞授权模式

	public PosShopAccount(){
		
	}
	
	public Boolean getYouzanAuthorize() {
		return youzanAuthorize;
	}

	public void setYouzanAuthorize(Boolean youzanAuthorize) {
		this.youzanAuthorize = youzanAuthorize;
	}

	public PosShopAccount(String accountId, String accountSecret){
		this.accountId = accountId;
		this.accountSecret = accountSecret;
	}
	
	public PosShopAccount(String accountId, String accountSecret, String accountType){
		this.accountId = accountId;
		this.accountSecret = accountSecret;
		this.accountType = accountType;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountSecret() {
		return accountSecret;
	}

	public void setAccountSecret(String accountSecret) {
		this.accountSecret = accountSecret;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	
	public boolean isKDT(){
		return accountType == null
				|| accountType.equals(AppConstants.WEIXIN_BRANCH_TYPE_KDT)
				|| accountType.equals(AppConstants.WEIXIN_BRANCH_TYPE_YOUZAN);
	}
	
	public String createYouzanStoreId(){
		return "AMA-" + systemBookCode;
	}

}
