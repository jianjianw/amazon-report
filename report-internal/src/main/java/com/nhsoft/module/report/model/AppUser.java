package com.nhsoft.module.report.model;
import com.nhsoft.module.report.dto.AppUserToDoDTO;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
public class AppUser implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(AppUser.class);
	private static final long serialVersionUID = 832856281656623301L;
	@Id
	private Integer appUserNum;
	private String systemBookCode;
	
	@Transient
	private Integer branchNum;
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
	@Transient
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

	/*@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name="UserRole", joinColumns={@JoinColumn(name="appUserNum")}, inverseJoinColumns={@JoinColumn(name="systemRoleNum", referencedColumnName="systemRoleNum")})
	private List<SystemRole> systemRoles = new ArrayList<SystemRole>();*/
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name="BranchUser", joinColumns={@JoinColumn(name="appUserNum")}, inverseJoinColumns={@JoinColumn(name="systemBookCode", referencedColumnName="systemBookCode"), @JoinColumn(name="branchNum", referencedColumnName="branchNum")})
	private List<Branch> branchs = new ArrayList<Branch>();
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name="AppUserRegion", joinColumns={@JoinColumn(name="appUserNum")}, inverseJoinColumns={@JoinColumn(name="regionNum", referencedColumnName="regionNum")})
	private List<Region> regions = new ArrayList<Region>();

	//解析用 不存表
	@Transient
	private Boolean touchPOSUser;
	@Transient
	private BigDecimal userMaxDiscount;
	@Transient
	private BigDecimal userMaxDiscountRate;
	@Transient
	private BigDecimal userMaxPolicyDiscount;
    @Transient
	private boolean isRefreshRemind = false;//是否每5分钟刷新一次提醒
    @Transient
	private String limitTimeType;//限制时间类型
    @Transient
	private Integer limitTimeCount;//限制时间数
    @Transient
	private Integer beforeBirthdayDays; //会员生日提醒提前天数
    @Transient
	private String userFeeItems;
    @Transient
	private Boolean superRequestAudit;//超限要货审核
    @Transient
	private AppUserToDoDTO appUserToDoDTO;//代办事项
    
    //临时属性
    @Transient
	private Boolean ignoreCheckName = false;//保存时忽略验证用户名称
	@Transient
	private String appUserSystem;//系统来源
	
	public String getAppUserSystem() {
		return appUserSystem;
	}
	
	public void setAppUserSystem(String appUserSystem) {
		this.appUserSystem = appUserSystem;
	}
	
	public Boolean getSuperRequestAudit() {
		return superRequestAudit;
	}

	public void setSuperRequestAudit(Boolean superRequestAudit) {
		this.superRequestAudit = superRequestAudit;
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

	public String getUserFeeItems() {
		return userFeeItems;
	}

	public void setUserFeeItems(String userFeeItems) {
		this.userFeeItems = userFeeItems;
	}

	public BigDecimal getUserMaxPolicyDiscount() {
		return userMaxPolicyDiscount;
	}

	public void setUserMaxPolicyDiscount(BigDecimal userMaxPolicyDiscount) {
		this.userMaxPolicyDiscount = userMaxPolicyDiscount;
	}

	public String getAppUserAuthorizeCard() {
		return appUserAuthorizeCard;
	}

	public void setAppUserAuthorizeCard(String appUserAuthorizeCard) {
		this.appUserAuthorizeCard = appUserAuthorizeCard;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public AppUserToDoDTO getAppUserToDoDTO() {
		return appUserToDoDTO;
	}

	public void setAppUserToDoDTO(AppUserToDoDTO appUserToDoDTO) {
		this.appUserToDoDTO = appUserToDoDTO;
	}

	public Integer getBeforeBirthdayDays() {
		return beforeBirthdayDays;
	}

	public void setBeforeBirthdayDays(Integer beforeBirthdayDays) {
		this.beforeBirthdayDays = beforeBirthdayDays;
	}

	public List<Branch> getBranchs() {
		return branchs;
	}

	public String getAppUserOpenId() {
		return appUserOpenId;
	}

	public void setAppUserOpenId(String appUserOpenId) {
		this.appUserOpenId = appUserOpenId;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public void setBranchs(List<Branch> branchs) {
		this.branchs = branchs;
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
	
	public Boolean getTouchPOSUser() {
		return touchPOSUser;
	}

	public void setTouchPOSUser(Boolean touchPOSUser) {
		this.touchPOSUser = touchPOSUser;
	}

	public BigDecimal getUserMaxDiscount() {
		return userMaxDiscount;
	}

	public void setUserMaxDiscount(BigDecimal userMaxDiscount) {
		this.userMaxDiscount = userMaxDiscount;
	}

	public BigDecimal getUserMaxDiscountRate() {
		return userMaxDiscountRate;
	}

	public void setUserMaxDiscountRate(BigDecimal userMaxDiscountRate) {
		this.userMaxDiscountRate = userMaxDiscountRate;
	}

	public String getAppUserEmail() {
		return appUserEmail;
	}

	public void setAppUserEmail(String appUserEmail) {
		this.appUserEmail = appUserEmail;
	}

	public String getLimitTimeType() {
		return limitTimeType;
	}

	public void setLimitTimeType(String limitTimeType) {
		this.limitTimeType = limitTimeType;
	}

	public Integer getLimitTimeCount() {
		return limitTimeCount;
	}

	public void setLimitTimeCount(Integer limitTimeCount) {
		this.limitTimeCount = limitTimeCount;
	}

	public static String writeToXml(List<AppUser> appUsers){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("APP_USER_LIST");
		for(int i = 0;i < appUsers.size();i++){
			AppUser appUser = appUsers.get(i);
			Element element = root.addElement("APP_USER");

			element.addElement("APP_USER_NUM").setText(appUser.getAppUserNum().toString());
			element.addElement("BOOK_CODE").setText(appUser.getSystemBookCode());
			element.addElement("APP_USER_CODE").setText(appUser.getAppUserCode());
			element.addElement("APP_USER_NAME").setText(appUser.getAppUserName());
			if(StringUtils.isNotEmpty(appUser.getAppUserPassword())){
				element.addElement("APP_USER_PASSWORD").setText(appUser.getAppUserPassword());
				
			}
			element.addElement("APP_USER_STATE_CODE").setText(appUser.getAppUserStateCode().toString());
			element.addElement("APP_USER_STATE_NAME").setText(appUser.getAppUserStateName());
			element.addElement("APP_USER_DEL_TAG").setText(BooleanUtils.toString(appUser.getAppUserDelTag(), "1", "0", "0"));
			
			if(appUser.getBranchs().size() > 0){
				Element branchElement = element.addElement("BRANCH");
				branchElement.addElement("BRANCH_NUM").setText(appUser.getBranchs().get(0).getId().getBranchNum().toString());
			}
		}
		return document.asXML();
		
	}
	
	public static AppUser fromXml(Element father) {
		AppUser appUser = new AppUser();
		Element element;
		element = (Element) father.selectSingleNode("APP_USER_NUM");
		if (element != null) {
			appUser.setAppUserNum(Integer.parseInt(element.getText()));
		}

		element = (Element) father.selectSingleNode("BOOK_CODE");
		if (element != null) {
			appUser.setSystemBookCode(element.getText());
		}

		element = (Element) father.selectSingleNode("APP_USER_CODE");
		if (element != null) {
			appUser.setAppUserCode(element.getText());
		}

		element = (Element) father.selectSingleNode("APP_USER_NAME");
		if (element != null) {
			appUser.setAppUserName(element.getText());
		}
		
		element = (Element) father.selectSingleNode("APP_USER_STATE_CODE");
		if (element != null) {
			appUser.setAppUserStateCode(Integer.parseInt(element.getText()));
		}
		
		element = (Element) father.selectSingleNode("APP_USER_STATE_NAME");
		if (element != null) {
			appUser.setAppUserStateName(element.getText());
		}
		
		element = (Element) father.selectSingleNode("BRANCH");
		if (element != null) {
			Element child = (Element) element.selectSingleNode("BRANCH_NUM");
			if (child != null) {
				appUser.setBranchNum(Integer.parseInt(child.getText()));
			}
		}
		return appUser;

	}

/*	public List<SystemRole> getSystemRoles() {
		return systemRoles;
	}

	public void setSystemRoles(List<SystemRole> systemRoles) {
		this.systemRoles = systemRoles;
	}*/

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

	public boolean isRefreshRemind() {
		return isRefreshRemind;
	}

	public void setRefreshRemind(boolean isRefreshRemind) {
		this.isRefreshRemind = isRefreshRemind;
	}

	public String getAppUserStockRole() {
		return appUserStockRole;
	}

	public void setAppUserStockRole(String appUserStockRole) {
		this.appUserStockRole = appUserStockRole;
	}



	public static List<AppUser> fromXML(byte[] stream){
		List<AppUser> list = new ArrayList<AppUser>();
		Document document;
		try {
			document = DocumentHelper.parseText(new String(stream));			
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator();
			while (iterator.hasNext()) {
				Element element = iterator.next();
				AppUser appUser = fromXml(element);
				list.add(appUser);
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}

		return list;
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
	
	@Transient
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