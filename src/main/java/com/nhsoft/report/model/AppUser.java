package com.nhsoft.report.model;

import com.nhsoft.pos3.server.dto.AppUserToDoDTO;
import com.nhsoft.pos3.server.param.*;
import com.nhsoft.pos3.shared.AppConstants;
import com.nhsoft.pos3.shared.LemengPrivilegeConstants;
import com.nhsoft.pos3.shared.PrivilegeConstants;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
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

	private List<SystemRole> systemRoles = new ArrayList<SystemRole>();
	private List<Branch> branchs = new ArrayList<Branch>();
	private List<PrivilegeResource> privilegeResources = new ArrayList<PrivilegeResource>();
	private List<Region> regions = new ArrayList<Region>();
	private HomeIndex homeIndex;
	private WholesaleParam wholesaleParam;
	private List<UserPurchaseItem> userPurchaseItems;//采购员关联商品

	//解析用 不存表
	private Boolean touchPOSUser;
	private BigDecimal userMaxDiscount;
	private BigDecimal userMaxDiscountRate;
	private BigDecimal userMaxPolicyDiscount;
    private boolean isRefreshRemind = false;//是否每5分钟刷新一次提醒	
    private String limitTimeType;//限制时间类型
    private Integer limitTimeCount;//限制时间数
    private Integer beforeBirthdayDays; //会员生日提醒提前天数
    private String userFeeItems;
    private Boolean superRequestAudit;//超限要货审核
    private AppUserToDoDTO appUserToDoDTO;//代办事项
    private Integer branchNum;
    
    //临时属性
    private Boolean ignoreCheckName = false;//保存时忽略验证用户名称
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

	public List<PrivilegeResource> getPrivilegeResources() {
		return privilegeResources;
	}

	public void setPrivilegeResources(List<PrivilegeResource> privilegeResources) {
		this.privilegeResources = privilegeResources;
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

	public HomeIndex getHomeIndex() {
		return homeIndex;
	}

	public void setHomeIndex(HomeIndex homeIndex) {
		this.homeIndex = homeIndex;
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

	public List<SystemRole> getSystemRoles() {
		return systemRoles;
	}

	public void setSystemRoles(List<SystemRole> systemRoles) {
		this.systemRoles = systemRoles;
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
	
	public WholesaleParam getWholesaleParam() {
		return wholesaleParam;
	}

	public void setWholesaleParam(WholesaleParam wholesaleParam) {
		this.wholesaleParam = wholesaleParam;
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

	public List<UserPurchaseItem> getUserPurchaseItems() {
		return userPurchaseItems;
	}

	public void setUserPurchaseItems(List<UserPurchaseItem> userPurchaseItems) {
		this.userPurchaseItems = userPurchaseItems;
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
	
	public boolean hasPermission(String privilegeName) {
	    if (systemRoles.size() == 0) {
	           return false;
	    }
	    for(int i = 0;i < systemRoles.size();i++){
	    	SystemRole systemRole = systemRoles.get(i);
	    	List<PrivilegeResource> privilegeResources = systemRole.getPrivilegeResources();
	    	 for(int j = 0; j < privilegeResources.size(); j++){
	             PrivilegeResource privilegeResource = privilegeResources.get(j);
	             if (privilegeResource.getPrivilegeResourceName().equals(privilegeName)) {
	                 return true;
	             }
	         }
	    }       
       return false;
   }
	
	public boolean hasLemengPhoneLoginPermission() {
	    if (systemRoles.size() == 0) {
	           return false;
	    }
	    for(int i = 0;i < systemRoles.size();i++){
	    	SystemRole systemRole = systemRoles.get(i);
	    	List<PrivilegeResource> privilegeResources = systemRole.getPrivilegeResources();
	    	 for(int j = 0; j < privilegeResources.size(); j++){
	             PrivilegeResource privilegeResource = privilegeResources.get(j);
	             if (privilegeResource.getPrivilegeResourceCategory().equals(LemengPrivilegeConstants.PRIVILEGE_PHONE)) {
	                 return true;
	             }
	         }
	    }       
       return false;
   }
	
	public List<String> findPhonePrivileges(){
		Set<String> privileges = new HashSet<String>();
		if (systemRoles.size() == 0) {
	        return new ArrayList<String>();
	    }
	    for(int i = 0;i < systemRoles.size();i++){
	    	SystemRole systemRole = systemRoles.get(i);
	    	List<PrivilegeResource> privilegeResources = systemRole.getPrivilegeResources();
	    	 for(int j = 0; j < privilegeResources.size(); j++){
	             PrivilegeResource privilegeResource = privilegeResources.get(j);
	             if (privilegeResource.getPrivilegeResourceCategory().equals(PrivilegeConstants.PRIVILEGE_PHONE)) {
	                if(!privilegeResource.getPrivilegeResourceName().equals(PrivilegeConstants.PRIVILEGE_RESOURCE_PHONE_USER)){
	                	privileges.add(privilegeResource.getPrivilegeResourceName());
	                }
	             }
	         }
	    }
	    List<String> returnList = new ArrayList<String>(privileges);
	    //分店登陆没有群体查询权限
	    if(branchs.size() > 0){
	    	Branch branch = branchs.get(0);
	    	Integer branchNum = branch.getId().getBranchNum();
	    	if(!branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
	    		for(int i = returnList.size() - 1;i >= 0;i--){
	    			String privilegeName = returnList.get(i);
	    			if(privilegeName.equals(PrivilegeConstants.PRIVILEGE_RESOURCE_PHONE_CARD_MODEL)
	    					|| privilegeName.equals(PrivilegeConstants.PRIVILEGE_RESOURCE_PHONE_ACTION)){
	    				returnList.remove(i);
	    			}
	    		}
	    	}
	    }
	    return returnList;
	}
	
	public List<String> findLemengPhonePrivileges(){
		Set<String> privileges = new HashSet<String>();
		if (systemRoles.size() == 0) {
	        return new ArrayList<String>();
	    }
	    for(int i = 0;i < systemRoles.size();i++){
	    	SystemRole systemRole = systemRoles.get(i);
	    	List<PrivilegeResource> privilegeResources = systemRole.getPrivilegeResources();
	    	 for(int j = 0; j < privilegeResources.size(); j++){
	             PrivilegeResource privilegeResource = privilegeResources.get(j);
	             if (privilegeResource.getPrivilegeResourceCategory().equals(LemengPrivilegeConstants.PRIVILEGE_PHONE)) {
	               
	                privileges.add(privilegeResource.getPrivilegeResourceName());
	                
	             }
	         }
	    }
	    List<String> returnList = new ArrayList<String>(privileges);
	    //分店登陆没有群体查询权限
	    if(branchs.size() > 0){
	    	Branch branch = branchs.get(0);
	    	Integer branchNum = branch.getId().getBranchNum();
	    	if(!branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
	    		for(int i = returnList.size() - 1;i >= 0;i--){
	    			String privilegeName = returnList.get(i);
	    			if(privilegeName.equals(LemengPrivilegeConstants.PRIVILEGE_PHONE_MEMBER_MODEL)
	    					){
	    				returnList.remove(i);
	    			}
	    		}
	    	}
	    }
	    return returnList;
	}
	
	

	
	public static List<PointParam> readFromXml(String text){
		List<PointParam> pointParams = new ArrayList<PointParam>();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			
			Iterator<Element> iterator = root.elementIterator();
			while(iterator.hasNext()){
				Element element = iterator.next();
				PointParam pointParam = new PointParam();
				pointParam.setPointName(element.attributeValue("规则名称"));
				pointParam.setConsumeMoney(new BigDecimal(element.attributeValue("消费金额")));
				pointParam.setPointValue(new BigDecimal(element.attributeValue("积分值")));
				pointParam.setPointMemo(element.attributeValue("附加设置"));
				pointParam.setIsActival(BooleanUtils.toBoolean(element.attributeValue("启用"), "-1", "0"));
				pointParams.add(pointParam);
			}		
			
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}	
		return pointParams;
	}
	
	public void readFromXml(){
		if(StringUtils.isEmpty(appUserProperty)){
			return ;
		}
		try {
			Document document = DocumentHelper.parseText(appUserProperty);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator();
			HomeIndex homeIndex = new HomeIndex();
			//默认
			setTouchPOSUser(true);
			while(iterator.hasNext()){
				Element element = iterator.next();
				
				if(element.getName().equals("AppUserProperty")){
					if(element.attributeValue("TouchPOSUser") != null){
						setTouchPOSUser(strToBool(element.attributeValue("TouchPOSUser")));
					}
					if(element.attributeValue("UserMaxDiscount") != null){
						setUserMaxDiscount(new BigDecimal(element.attributeValue("UserMaxDiscount")));
					}
					if(element.attributeValue("UserMaxDiscountRate") != null){
						setUserMaxDiscountRate(new BigDecimal(element.attributeValue("UserMaxDiscountRate")));
					}
					if(element.attributeValue("RefreshRemind") != null){
						setRefreshRemind(strToBool(element.attributeValue("RefreshRemind")));
					}
					if(element.attributeValue("LimitTimeType") != null){
						setLimitTimeType(element.attributeValue("LimitTimeType"));
					} else {
						setLimitTimeType(AppConstants.DATE_TYPE_NO_LIMIT);
					}
					
					if(element.attributeValue("LimitTimeCount") != null){
						setLimitTimeCount(Integer.parseInt(element.attributeValue("LimitTimeCount")));
					} else {
						setLimitTimeCount(0);
					}
					
					if(element.attributeValue("RemindBirthDays") != null){
						setBeforeBirthdayDays(Integer.parseInt(element.attributeValue("RemindBirthDays")));
					} else {
						setBeforeBirthdayDays(0);
					}
					if(element.attributeValue("UserMaxPolicyDiscount") != null){
						setUserMaxPolicyDiscount(new BigDecimal(element.attributeValue("UserMaxPolicyDiscount")));
					}
					if(element.attributeValue("UserFeeItems") != null){
						setUserFeeItems(element.attributeValue("UserFeeItems"));
					}
					if(element.attributeValue("SuperRequestAudit") != null){
						setSuperRequestAudit(strToBool(element.attributeValue("SuperRequestAudit")));
					}
					
				} else if(element.getName().equals("HomePanel_List")){
										
					List<HomePanel> homePanels = HomePanel.readFromXml(element);
					homeIndex.setHomePanels(homePanels);
						
					
				} else if(element.getName().equals("Favorite_List")){
					List<Favorite> favorites = Favorite.readFromXml(element);
					homeIndex.setFavorites(favorites);

				} else if(element.getName().equals("Custom_List")){
					List<Favorite> customs = Favorite.readFromXml(element);
					homeIndex.setCustoms(customs);

				} else if(element.getName().equals("WHOLE_SALE_PARAM")){
					WholesaleParam wholesaleParam = new WholesaleParam();
					if(element.attributeValue("ImaxDisount") != null){
						wholesaleParam.setImaxDisount(new BigDecimal(element.attributeValue("ImaxDisount")));
					}
					if(element.attributeValue("IsWholeSaller") != null){
						wholesaleParam.setIsWholeSaller((strToBool(element.attributeValue("IsWholeSaller"))));
					}
					if(element.attributeValue("ItemWholePrice") != null){
						wholesaleParam.setItemWholePrice((strToBool(element.attributeValue("ItemWholePrice"))));
					}
					if(element.attributeValue("ItemWholePrice2") != null){
						wholesaleParam.setItemWholePrice2((strToBool(element.attributeValue("ItemWholePrice2"))));
					}
					if(element.attributeValue("ItemWholePrice3") != null){
						wholesaleParam.setItemWholePrice3((strToBool(element.attributeValue("ItemWholePrice3"))));
					}
					if(element.attributeValue("ItemWholePrice4") != null){
						wholesaleParam.setItemWholePrice4((strToBool(element.attributeValue("ItemWholePrice4"))));
					}
					setWholesaleParam(wholesaleParam);
				} else if(element.getName().equals("AppUserToDo")){
					AppUserToDoDTO appUserToDoDTO = new AppUserToDoDTO();
					
					Element subElement = (Element) element.selectSingleNode("UnReceivePurchase");
					if(subElement != null){
						appUserToDoDTO.setUnReceivePurchase(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("UnInOutOrder");
					if(subElement != null){
						appUserToDoDTO.setUnInOutOrder(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("UnSaleBookOrder");
					if(subElement != null){
						appUserToDoDTO.setUnSaleBookOrder(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("NegativeInventoryItems");
					if(subElement != null){
						appUserToDoDTO.setNegativeInventoryItems(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("SuggestSupplyItems");
					if(subElement != null){
						appUserToDoDTO.setSuggestSupplyItems(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("UnOutRequestOrder");
					if(subElement != null){
						appUserToDoDTO.setUnOutRequestOrder(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("UnPrintWholesaleOrders");
					if(subElement != null){
						appUserToDoDTO.setUnPrintWholesaleOrders(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("ExpireItems");
					if(subElement != null){
						appUserToDoDTO.setExpireItems(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("CustomerBirthRemind");
					if(subElement != null){
						appUserToDoDTO.setCustomerBirthRemind(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("UnAuditPriceAdjust");
					if(subElement != null){
						appUserToDoDTO.setUnAuditPriceAdjust(strToBool(subElement.getText()));
					}
					subElement = (Element) element.selectSingleNode("PosClientExtraMoney");
					if(subElement != null){
						appUserToDoDTO.setPosClientExtraMoney(strToBool(subElement.getText()));
					}
					
					setAppUserToDoDTO(appUserToDoDTO);
				}
				
			}
			setHomeIndex(homeIndex);
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}
	}
	
	public String writeToXml(){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("用户属性");
		Element element = root.addElement("AppUserProperty");
		if(touchPOSUser != null ){
			element.addAttribute("TouchPOSUser", BooleanUtils.toString(touchPOSUser, "-1", "0"));
			
		} else {
			element.addAttribute("TouchPOSUser", "-1");
		}
		if(userMaxDiscount != null){
			element.addAttribute("UserMaxDiscount", userMaxDiscount.toString());
			
		} else {
			element.addAttribute("UserMaxDiscount", "0.00");
		}
		if(userMaxDiscountRate != null){
			element.addAttribute("UserMaxDiscountRate", userMaxDiscountRate.toString());
			
		} else {
			element.addAttribute("UserMaxDiscountRate", "0.00");
		}
		element.addAttribute("RefreshRemind", BooleanUtils.toString(isRefreshRemind, "1", "0"));
		
		if(limitTimeType == null){
			limitTimeType = AppConstants.DATE_TYPE_NO_LIMIT;
		} 
		element.addAttribute("LimitTimeType", limitTimeType);
		
		if(limitTimeCount == null){
			limitTimeCount = 0;
		} 
		element.addAttribute("LimitTimeCount", limitTimeCount.toString());
		
		if(beforeBirthdayDays == null){
			beforeBirthdayDays = 0;
		}
		element.addAttribute("RemindBirthDays", beforeBirthdayDays.toString());

		if(userMaxPolicyDiscount != null){
			element.addAttribute("UserMaxPolicyDiscount", userMaxPolicyDiscount.toString());
			
		} else {
			element.addAttribute("UserMaxPolicyDiscount", "0.00");
		}
		if(userFeeItems != null){
			element.addAttribute("UserFeeItems", userFeeItems);
			
		}
		if(superRequestAudit != null){
			element.addAttribute("SuperRequestAudit", BooleanUtils.toString(superRequestAudit, "1", "0"));
		}
		if(homeIndex != null){
			List<HomePanel> homePanels = homeIndex.getHomePanels();
			List<Favorite> favorites = homeIndex.getFavorites();
			
			Element home = root.addElement("HomePanel_List");
			HomePanel.writeToXml(homePanels, home);
			
			Element favorite = root.addElement("Favorite_List");
			Favorite.writeToXml(favorites, favorite);
			
			if(homeIndex.getCustoms() != null){
				
				Element customs = root.addElement("Custom_List");
				Favorite.writeToXml(homeIndex.getCustoms(), customs);
			}
		}
		if(wholesaleParam != null){
			element = root.addElement("WHOLE_SALE_PARAM");
			if(wholesaleParam.getImaxDisount() == null){
				wholesaleParam.setImaxDisount(BigDecimal.ZERO);
			}
			element.addAttribute("ImaxDisount", wholesaleParam.getImaxDisount().toString());
			element.addAttribute("IsWholeSaller", BooleanUtils.toString(wholesaleParam.getIsWholeSaller(), "-1", "0"));
			element.addAttribute("ItemWholePrice", BooleanUtils.toString(wholesaleParam.getItemWholePrice(), "-1", "0"));
			element.addAttribute("ItemWholePrice2", BooleanUtils.toString(wholesaleParam.getItemWholePrice2(), "-1", "0"));
			element.addAttribute("ItemWholePrice3", BooleanUtils.toString(wholesaleParam.getItemWholePrice3(), "-1", "0"));
			element.addAttribute("ItemWholePrice4", BooleanUtils.toString(wholesaleParam.getItemWholePrice4(), "-1", "0"));
		}
		if(appUserToDoDTO != null){
			element = root.addElement("AppUserToDo");
			if(appUserToDoDTO.getUnReceivePurchase() != null){
				element.addElement("UnReceivePurchase").setText(BooleanUtils.toString(appUserToDoDTO.getUnReceivePurchase(), "1", "0"));
			}
			if(appUserToDoDTO.getUnInOutOrder() != null){
				element.addElement("UnInOutOrder").setText(BooleanUtils.toString(appUserToDoDTO.getUnInOutOrder(), "1", "0"));
			}
			if(appUserToDoDTO.getUnSaleBookOrder() != null){
				element.addElement("UnSaleBookOrder").setText(BooleanUtils.toString(appUserToDoDTO.getUnSaleBookOrder(), "1", "0"));
			}
			if(appUserToDoDTO.getNegativeInventoryItems() != null){
				element.addElement("NegativeInventoryItems").setText(BooleanUtils.toString(appUserToDoDTO.getNegativeInventoryItems(), "1", "0"));
			}
			if(appUserToDoDTO.getSuggestSupplyItems() != null){
				element.addElement("SuggestSupplyItems").setText(BooleanUtils.toString(appUserToDoDTO.getSuggestSupplyItems(), "1", "0"));
			}
			if(appUserToDoDTO.getUnOutRequestOrder() != null){
				element.addElement("UnOutRequestOrder").setText(BooleanUtils.toString(appUserToDoDTO.getUnOutRequestOrder(), "1", "0"));
			}
			if(appUserToDoDTO.getUnPrintWholesaleOrders() != null){
				element.addElement("UnPrintWholesaleOrders").setText(BooleanUtils.toString(appUserToDoDTO.getUnPrintWholesaleOrders(), "1", "0"));
			}
			if(appUserToDoDTO.getExpireItems() != null){
				element.addElement("ExpireItems").setText(BooleanUtils.toString(appUserToDoDTO.getExpireItems(), "1", "0"));
			}
			if(appUserToDoDTO.getCustomerBirthRemind() != null){
				element.addElement("CustomerBirthRemind").setText(BooleanUtils.toString(appUserToDoDTO.getCustomerBirthRemind(), "1", "0"));
			}
			if(appUserToDoDTO.getUnAuditPriceAdjust() != null){
				element.addElement("UnAuditPriceAdjust").setText(BooleanUtils.toString(appUserToDoDTO.getUnAuditPriceAdjust(), "1", "0"));
				
			}
			if(appUserToDoDTO.getPosClientExtraMoney() != null){
				element.addElement("PosClientExtraMoney").setText(BooleanUtils.toString(appUserToDoDTO.getPosClientExtraMoney(), "1", "0"));
				
			}
			
			
		}
		return document.asXML();
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