package com.nhsoft.report.model;

import com.nhsoft.report.dto.AppUserToDoDTO;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("unchecked")
public class AppUser implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(AppUser.class);
	private static final long serialVersionUID = 832856281656623301L;
	private Integer appUserNum;
	private String systemBookCode;
	private String appUserCode;
	private String appUserName;
	private String appUserPassword;
	private String appUserCert;
	private Integer appUserStateCode;
	private String appUserStateName;
	private Boolean appUserDelTag;
	private String appUserProperty;
	private String appUserKey;
	private String appUserUuid;
	private String appUserPhoneProperty;
	private String appUserPhone;
	private String appUserIp;
	private String appUserEmail;
	private String appUserPwKey;
	private String branchRegionNum;
	private Date appUserPwEditTime;
	private String appUserSecurityCode;
	private Date appUserSecuritySendTime;
	private String appUserOpenId;
	private String appUserStockRole;  //仓管中心角色
	private String appUserAuthorizeCard; //用户授权卡号
	private String appUserWshopOpenId;
    private Boolean appUserWshopRemind;//是否微商城提醒
    private Boolean appUserDefault; //是否申请证书用户

    
    //临时属性
    private Boolean ignoreCheckName = false;//保存时忽略验证用户名称
	private String appUserSystem;//系统来源
	
	public String getAppUserSystem() {
		return appUserSystem;
	}
	
	public void setAppUserSystem(String appUserSystem) {
		this.appUserSystem = appUserSystem;
	}
	

	public Boolean getIgnoreCheckName() {
		return ignoreCheckName;
	}

	public void setIgnoreCheckName(Boolean ignoreCheckName) {
		this.ignoreCheckName = ignoreCheckName;
	}

	public Boolean getAppUserDefault() {
		return appUserDefault;
	}

	public void setAppUserDefault(Boolean appUserDefault) {
		this.appUserDefault = appUserDefault;
	}

	public Boolean getAppUserWshopRemind() {
		return appUserWshopRemind;
	}

	public void setAppUserWshopRemind(Boolean appUserWshopRemind) {
		this.appUserWshopRemind = appUserWshopRemind;
	}

	public String getAppUserWshopOpenId() {
		return appUserWshopOpenId;
	}

	public void setAppUserWshopOpenId(String appUserWshopOpenId) {
		this.appUserWshopOpenId = appUserWshopOpenId;
	}


	public String getAppUserAuthorizeCard() {
		return appUserAuthorizeCard;
	}

	public void setAppUserAuthorizeCard(String appUserAuthorizeCard) {
		this.appUserAuthorizeCard = appUserAuthorizeCard;
	}

	public String getAppUserOpenId() {
		return appUserOpenId;
	}

	public void setAppUserOpenId(String appUserOpenId) {
		this.appUserOpenId = appUserOpenId;
	}

	public String getAppUserKey() {
		return appUserKey;
	}

	public void setAppUserKey(String appUserKey) {
		this.appUserKey = appUserKey;
	}

	public String getAppUserUuid() {
		return appUserUuid;
	}

	public void setAppUserUuid(String appUserUuid) {
		this.appUserUuid = appUserUuid;
	}

	public Integer getAppUserNum() {
		return appUserNum;
	}

	public void setAppUserNum(Integer appUserNum) {
		this.appUserNum = appUserNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getAppUserCode() {
		return appUserCode;
	}

	public void setAppUserCode(String appUserCode) {
		this.appUserCode = appUserCode;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public String getAppUserPassword() {
		return appUserPassword;
	}

	public void setAppUserPassword(String appUserPassword) {
		this.appUserPassword = appUserPassword;
	}

	public String getAppUserCert() {
		return appUserCert;
	}

	public void setAppUserCert(String appUserCert) {
		this.appUserCert = appUserCert;
	}

	public Integer getAppUserStateCode() {
		return appUserStateCode;
	}

	public void setAppUserStateCode(Integer appUserStateCode) {
		this.appUserStateCode = appUserStateCode;
	}

	public String getAppUserStateName() {
		return appUserStateName;
	}

	public void setAppUserStateName(String appUserStateName) {
		this.appUserStateName = appUserStateName;
	}

	public Boolean getAppUserDelTag() {
		return appUserDelTag;
	}

	public void setAppUserDelTag(Boolean appUserDelTag) {
		this.appUserDelTag = appUserDelTag;
	}

	public String getAppUserProperty() {
		return appUserProperty;
	}

	public void setAppUserProperty(String appUserProperty) {
		this.appUserProperty = appUserProperty;
	}

	public String getAppUserPhoneProperty() {
		return appUserPhoneProperty;
	}

	public void setAppUserPhoneProperty(String appUserPhoneProperty) {
		this.appUserPhoneProperty = appUserPhoneProperty;
	}
	


	public String getAppUserEmail() {
		return appUserEmail;
	}

	public void setAppUserEmail(String appUserEmail) {
		this.appUserEmail = appUserEmail;
	}


	



	public String getAppUserPhone() {
		return appUserPhone;
	}

	public void setAppUserPhone(String appUserPhone) {
		this.appUserPhone = appUserPhone;
	}

	public String getAppUserIp() {
		return appUserIp;
	}

	public void setAppUserIp(String appUserIp) {
		this.appUserIp = appUserIp;
	}

	public String getAppUserPwKey() {
		return appUserPwKey;
	}

	public void setAppUserPwKey(String appUserPwKey) {
		this.appUserPwKey = appUserPwKey;
	}

	public String getBranchRegionNum() {
		return branchRegionNum;
	}

	public void setBranchRegionNum(String branchRegionNum) {
		this.branchRegionNum = branchRegionNum;
	}

	public Date getAppUserPwEditTime() {
		return appUserPwEditTime;
	}

	public void setAppUserPwEditTime(Date appUserPwEditTime) {
		this.appUserPwEditTime = appUserPwEditTime;
	}

	public String getAppUserSecurityCode() {
		return appUserSecurityCode;
	}

	public void setAppUserSecurityCode(String appUserSecurityCode) {
		this.appUserSecurityCode = appUserSecurityCode;
	}

	public Date getAppUserSecuritySendTime() {
		return appUserSecuritySendTime;
	}

	public void setAppUserSecuritySendTime(Date appUserSecuritySendTime) {
		this.appUserSecuritySendTime = appUserSecuritySendTime;
	}


	public String getAppUserStockRole() {
		return appUserStockRole;
	}

	public void setAppUserStockRole(String appUserStockRole) {
		this.appUserStockRole = appUserStockRole;
	}

	



	


	



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appUserNum == null) ? 0 : appUserNum.hashCode());
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
		AppUser other = (AppUser) obj;
		if (appUserNum == null) {
			if (other.appUserNum != null)
				return false;
		} else if (!appUserNum.equals(other.appUserNum))
			return false;
		return true;
	}
	
	private static Boolean strToBool(String str) {
		if (str == null) {
			return false;
		}
		if (str.equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	public static AppUser getAppUser(List<AppUser> appUsers, int appUserNum) {
		for(int i = 0;i < appUsers.size();i++){
			AppUser appUser = appUsers.get(i);
			if(appUser.getAppUserNum() == appUserNum){
				return appUser;
			}
		}
		return null;
	}
	
	
}