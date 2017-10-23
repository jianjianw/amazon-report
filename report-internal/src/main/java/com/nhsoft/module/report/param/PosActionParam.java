package com.nhsoft.module.report.param;

import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PosActionParam implements Serializable {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(PosActionParam.class);
	private static final long serialVersionUID = -8891455443261424266L;
	private BigDecimal actionMoney;// 消费起赠金额
	private Boolean actionIncrease;// 超额依次递增
	private List<Branch> branchs = new ArrayList<Branch>();
	private List<String> paymentTypes = new ArrayList<String>();
	
	private String actionType;//活动条件类型
	private Integer mostCount;//单次消费最多赠券份数
	private List<Integer> itemNums = new ArrayList<Integer>();//指定商品主键
	private String itemNames;//指定商品名称
	private List<String> itemTypes = new ArrayList<String>();//指定类别代码
	private String itemTypeNames;//指定类别名称
	private List<Integer> otherItemNums = new ArrayList<Integer>();//例外商品主键
	private String otherItemNames;//例外商品名称
	private String exceptDate;//例外日期
	private Integer actionSmsType;//0 不处理 1：只发短信；2：只发微信；3：短信+微信；4：二选一微信优先；

	private Integer actionEffectiveTime;//奖励发放时间
	private Integer actionBirthdayBefore;//奖励提前发放天数
	private List<Integer> cardNums = new ArrayList<Integer>();//会员卡编号
	private List<String> cardNames = new ArrayList<String>();;//会员卡名称
	private List<CardUserType> cardUserTypes = new ArrayList<CardUserType>();
	private Boolean firstAction; //是否首次赠券
	private String actionUrl; //朋友圈赠券url
	private String weixinActionTitle;//封面标题
	private String weixinActionTitleUrl;//封面标题图片
	private String weixinActionDetailUrl;//内容图片URL
	private Boolean weixinActionShare;//是否可分享
	private String weixinActionTitleOssUrl;//封面标题图片本地Oss的url
	private String weixinActionShareLink;//微信分享链接
	private String weixinActionQrcodeUrl;//关注二维码URL
	private String weixinActionMsg; //发券成功提示消息

	
	//临时属性
	private PosActionParam posActionParam;

	public Boolean getFirstAction() {
		return firstAction;
	}

	public void setFirstAction(Boolean firstAction) {
		this.firstAction = firstAction;
	}

	public PosActionParam getPosActionParam() {
		return posActionParam;
	}

	public void setPosActionParam(PosActionParam posActionParam) {
		this.posActionParam = posActionParam;
	}

	public List<CardUserType> getCardUserTypes() {
		return cardUserTypes;
	}

	public void setCardUserTypes(List<CardUserType> cardUserTypes) {
		this.cardUserTypes = cardUserTypes;
	}

	public Integer getActionEffectiveTime() {
		return actionEffectiveTime;
	}

	public void setActionEffectiveTime(Integer actionEffectiveTime) {
		this.actionEffectiveTime = actionEffectiveTime;
	}

	public Integer getActionBirthdayBefore() {
		return actionBirthdayBefore;
	}

	public void setActionBirthdayBefore(Integer actionBirthdayBefore) {
		this.actionBirthdayBefore = actionBirthdayBefore;
	}

	public BigDecimal getActionMoney() {
		return actionMoney;
	}

	public void setActionMoney(BigDecimal actionMoney) {
		this.actionMoney = actionMoney;
	}

	public Boolean getActionIncrease() {
		return actionIncrease;
	}

	public void setActionIncrease(Boolean actionIncrease) {
		this.actionIncrease = actionIncrease;
	}

	public List<Branch> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Branch> branchs) {
		this.branchs = branchs;
	}

	public List<String> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<String> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
	
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Integer getMostCount() {
		return mostCount;
	}

	public void setMostCount(Integer mostCount) {
		this.mostCount = mostCount;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public String getItemNames() {
		return itemNames;
	}

	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}

	public List<String> getItemTypes() {
		return itemTypes;
	}

	public void setItemTypes(List<String> itemTypes) {
		this.itemTypes = itemTypes;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getItemTypeNames() {
		return itemTypeNames;
	}

	public void setItemTypeNames(String itemTypeNames) {
		this.itemTypeNames = itemTypeNames;
	}

	public List<Integer> getOtherItemNums() {
		return otherItemNums;
	}

	public void setOtherItemNums(List<Integer> otherItemNums) {
		this.otherItemNums = otherItemNums;
	}

	public String getOtherItemNames() {
		return otherItemNames;
	}

	public void setOtherItemNames(String otherItemNames) {
		this.otherItemNames = otherItemNames;
	}

	public String getExceptDate() {
		return exceptDate;
	}

	public void setExceptDate(String exceptDate) {
		this.exceptDate = exceptDate;
	}

	public String getWeixinActionTitle() {
		return weixinActionTitle;
	}

	public String getWeixinActionTitleUrl() {
		return weixinActionTitleUrl;
	}

	public String getWeixinActionDetailUrl() {
		return weixinActionDetailUrl;
	}

	public Boolean getWeixinActionShare() {
		return weixinActionShare;
	}

	public void setWeixinActionTitle(String weixinActionTitle) {
		this.weixinActionTitle = weixinActionTitle;
	}

	public void setWeixinActionTitleUrl(String weixinActionTitleUrl) {
		this.weixinActionTitleUrl = weixinActionTitleUrl;
	}

	public void setWeixinActionDetailUrl(String weixinActionDetailUrl) {
		this.weixinActionDetailUrl = weixinActionDetailUrl;
	}

	public void setWeixinActionShare(Boolean weixinActionShare) {
		this.weixinActionShare = weixinActionShare;
	}

	public String getWeixinActionTitleOssUrl() {
		return weixinActionTitleOssUrl;
	}

	public void setWeixinActionTitleOssUrl(String weixinActionTitleOssUrl) {
		this.weixinActionTitleOssUrl = weixinActionTitleOssUrl;
	}

	public List<Integer> getCardNums() {
		return cardNums;
	}

	public void setCardNums(List<Integer> cardNums) {
		this.cardNums = cardNums;
	}

	public String getWeixinActionQrcodeUrl() {
		return weixinActionQrcodeUrl;
	}

	public void setWeixinActionQrcodeUrl(String weixinActionQrcodeUrl) {
		this.weixinActionQrcodeUrl = weixinActionQrcodeUrl;
	}

	public List<String> getCardNames() {
		return cardNames;
	}

	public void setCardNames(List<String> cardNames) {
		this.cardNames = cardNames;
	}

	public String getWeixinActionShareLink() {
		return weixinActionShareLink;
	}

	public void setWeixinActionShareLink(String weixinActionShareLink) {
		this.weixinActionShareLink = weixinActionShareLink;
	}

	public String getWeixinActionMsg() {
		return weixinActionMsg;
	}

	public void setWeixinActionMsg(String weixinActionMsg) {
		this.weixinActionMsg = weixinActionMsg;
	}

	public Integer getActionSmsType() {
		return actionSmsType;
	}

	public void setActionSmsType(Integer actionSmsType) {
		this.actionSmsType = actionSmsType;
	}

	@SuppressWarnings("unchecked")
	public static PosActionParam readFromXml(String text){
		if(StringUtils.isEmpty(text)){
			return null;
		}
		try {
			PosActionParam posActionParam = new PosActionParam();
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			
			Element element = (Element) root.selectSingleNode("action_money");
			if(element != null){
				posActionParam.setActionMoney(new BigDecimal(element.getText()));
			}
			element = (Element) root.selectSingleNode("action_increase");
			if(element != null){
				posActionParam.setActionIncrease(AppUtil.strToBool(element.getText()));
			}
			element = (Element) root.selectSingleNode("apply_branchs");
			if(element != null){
				Iterator<Element> iterator = element.elementIterator();
				while(iterator.hasNext()){
					Element subElement = iterator.next();
					Branch branch = new Branch();					
					Branch.BranchId branchId = new Branch.BranchId(null, Integer.parseInt(subElement.selectSingleNode("branch_num").getText()));
					branch.setId(branchId);
					branch.setBranchName(subElement.selectSingleNode("branch_name").getText());
					posActionParam.getBranchs().add(branch);
				}
			}
			element = (Element) root.selectSingleNode("apply_payment_types");
			if(element != null){
				String[] array = element.getText().split(",");
				for(int i = 0;i < array.length;i++){
					posActionParam.getPaymentTypes().add(array[i]);
				}
			}
			
			element = (Element) root.selectSingleNode("action_type");
			if(element != null){
				posActionParam.setActionType(element.getText());
			} else {
				posActionParam.setActionType(AppConstants.MARKET_ACTION_TYPE_PAYMENT);
			}
			
			element = (Element) root.selectSingleNode("most_count");
			if(element != null){
				posActionParam.setMostCount(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("specify_item_names");
			if(element != null){
				posActionParam.setItemNames(element.getText());
			}
			
			element = (Element) root.selectSingleNode("specify_item_nums");
			if(element != null){
				Iterator<Element> iterator = element.elementIterator();
				while(iterator.hasNext()){
					Element subElement = iterator.next();
					posActionParam.getItemNums().add(Integer.parseInt(subElement.getText()));
				}
			}
			
			element = (Element) root.selectSingleNode("specify_item_type_names");
			if(element != null){
				posActionParam.setItemTypeNames(element.getText());
			}
			
			element = (Element) root.selectSingleNode("specify_item_type_codes");
			if(element != null){
				Iterator<Element> iterator = element.elementIterator();
				while(iterator.hasNext()){
					Element subElement = iterator.next();
					posActionParam.getItemTypes().add(subElement.getText());
				}
			}
			
			element = (Element) root.selectSingleNode("exception_item_names");
			if(element != null){
				posActionParam.setOtherItemNames(element.getText());
			}
			
			element = (Element) root.selectSingleNode("exception_item_nums");
			if(element != null){
				Iterator<Element> iterator = element.elementIterator();
				while(iterator.hasNext()){
					Element subElement = iterator.next();
					posActionParam.getOtherItemNums().add(Integer.parseInt(subElement.getText()));
				}
			}
			
			element = (Element) root.selectSingleNode("except_date");
			if(element != null){	
				String test = element.getText();
				if(test.contains("-")){
					test = test.split("-")[1];
				}
				posActionParam.setExceptDate(test);
	
			}
			
			element = (Element) root.selectSingleNode("card_user_nums");
			if(element != null){
				Iterator<Element> iterator = element.elementIterator();
				while(iterator.hasNext()){
					Element subElement = iterator.next();
					posActionParam.getCardNums().add(Integer.parseInt(subElement.getText()));
				}
			}
			element = (Element) root.selectSingleNode("card_user_names");
			if(element != null){
				Iterator<Element> iterator = element.elementIterator();
				while(iterator.hasNext()){
					Element subElement = iterator.next();
					posActionParam.getCardNames().add(subElement.getText());
				}
			}
			element = (Element) root.selectSingleNode("action_notice_type");
			if(element != null){
				posActionParam.setActionSmsType(Integer.parseInt(element.getText()));
			} else {
				posActionParam.setActionSmsType(3);
			}
			element = (Element) root.selectSingleNode("action_effective_time");
			if(element != null){
				posActionParam.setActionEffectiveTime(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("action_birthday_before");
			if(element != null){
				posActionParam.setActionBirthdayBefore(Integer.parseInt(element.getText()));
			}
			element = (Element) root.selectSingleNode("apply_card_user_types");
			if(element != null){
				Iterator<Element> iterator = element.elementIterator();
				while(iterator.hasNext()){
					Element subElement = iterator.next();
					CardUserType cardUserType = new CardUserType();
					cardUserType.setTypeCode(subElement.selectSingleNode("type_code").getText());
					cardUserType.setTypeName(subElement.selectSingleNode("type_name").getText());
					posActionParam.getCardUserTypes().add(cardUserType);
				}
			}
			
			element = (Element) root.selectSingleNode("first_action");
			if(element != null){
				posActionParam.setFirstAction(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("action_url");
			if(element != null) {
				posActionParam.setActionUrl(element.getText());
			}
			
			element = (Element) root.selectSingleNode("weixin_action_title");
			if(element != null) {
				posActionParam.setWeixinActionTitle(element.getText());
			}
			
			element = (Element) root.selectSingleNode("weixin_action_title_url");
			if(element != null) {
				posActionParam.setWeixinActionTitleUrl(element.getText());
			}
			
			element = (Element) root.selectSingleNode("weixin_action_detail_url");
			if(element != null) {
				posActionParam.setWeixinActionDetailUrl(element.getText());
			}
			
			element = (Element) root.selectSingleNode("weixin_action_share");
			if(element != null) {
				posActionParam.setWeixinActionShare(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("weixin_action_title_oss_url");
			if(element != null) {
				posActionParam.setWeixinActionTitleOssUrl(element.getText());
			}
			
			element = (Element) root.selectSingleNode("weixin_action_share_link");
			if(element != null) {
				posActionParam.setWeixinActionShareLink(element.getText());
			}
			
			element = (Element) root.selectSingleNode("weixin_action_qrcode_url");
			if(element != null) {
				posActionParam.setWeixinActionQrcodeUrl(element.getText());
			}
			
			element = (Element) root.selectSingleNode("weixin_action_msg");
			if(element != null) {
				posActionParam.setWeixinActionMsg(element.getText());
			}
			return posActionParam;
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public String writeToXml(){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("pos_action_param");
		if(actionMoney != null){
			root.addElement("action_money").setText(actionMoney.toString());
		}
		if(actionIncrease != null){
			root.addElement("action_increase").setText(BooleanUtils.toString(actionIncrease, "1", "0", "0"));
		}
		if(branchs.size() > 0){
			Element element = root.addElement("apply_branchs");
			for(int i = 0;i < branchs.size();i++){
				Branch branch = branchs.get(i);
				Element subElement = element.addElement("apply_branch");
				subElement.addElement("branch_num").setText(branch.getId().getBranchNum().toString());
				subElement.addElement("branch_name").setText(branch.getBranchName());				
			}
		}
		if(paymentTypes.size() > 0){
			String text = "";
			for(int i = 0;i < paymentTypes.size();i++){
				if(StringUtils.isEmpty(paymentTypes.get(i))){
					continue;
				}
				if(text.isEmpty()){
					text = text.concat(paymentTypes.get(i));
				} else {
					text = text.concat("," + paymentTypes.get(i));
				}
			}
			root.addElement("apply_payment_types").setText(text);;

		}
		if(StringUtils.isEmpty(actionType)){
			actionType = AppConstants.MARKET_ACTION_TYPE_PAYMENT;
		}
		root.addElement("action_type").setText(actionType);
		
		if(mostCount != null){
			root.addElement("most_count").setText(mostCount.toString());
		}
		
		if(StringUtils.isNotEmpty(itemNames)){
			root.addElement("specify_item_names").setText(itemNames);
		}
		if(itemNums.size() > 0){
			Element element = root.addElement("specify_item_nums");
			for(int i = 0;i < itemNums.size();i++){
				Integer itemNum = itemNums.get(i);
				element.addElement("item_num").setText(itemNum.toString());
			}
		}
		if(StringUtils.isNotEmpty(itemTypeNames)){
			root.addElement("specify_item_type_names").setText(itemTypeNames);
		}
		if(itemTypes.size() > 0){
			Element element = root.addElement("specify_item_type_codes");
			for(int i = 0;i < itemTypes.size();i++){
				String itemTypeCode = itemTypes.get(i);
				element.addElement("item_type_code").setText(itemTypeCode);
			}
		}
		
		if(StringUtils.isNotEmpty(otherItemNames)){
			root.addElement("exception_item_names").setText(otherItemNames);
		}
		if(otherItemNums.size() > 0){
			Element element = root.addElement("exception_item_nums");
			for(int i = 0;i < otherItemNums.size();i++){
				Integer itemNum = otherItemNums.get(i);
				element.addElement("item_num").setText(itemNum.toString());
			}
		}
		if(cardNums != null && cardNums.size() > 0){
			Element element = root.addElement("card_user_nums");
			for(int i = 0;i < cardNums.size();i++){
				Integer cardUserNum = cardNums.get(i);
				element.addElement("card_user_num").setText(cardUserNum.toString());
			}
		}
		if(cardNames != null && cardNames.size() > 0){
			Element element = root.addElement("card_user_names");
			for(int i = 0;i < cardNames.size();i++){
				String cardUserName = cardNames.get(i);
				element.addElement("card_user_name").setText(cardUserName);
			}
		}
		if(exceptDate != null){
			root.addElement("except_date").setText(exceptDate);
		}
		if(actionSmsType != null){
			root.addElement("action_notice_type").setText(actionSmsType.toString());
		}
		if(actionEffectiveTime != null){
			root.addElement("action_effective_time").setText(actionEffectiveTime.toString());

		}
		if(actionBirthdayBefore != null){
			root.addElement("action_birthday_before").setText(actionBirthdayBefore.toString());

		}
		if(cardUserTypes != null && cardUserTypes.size() > 0){
			Element element = root.addElement("apply_card_user_types");
			for(int i = 0;i < cardUserTypes.size();i++){
				CardUserType cardUserType = cardUserTypes.get(i);
				Element subElement = element.addElement("apply_card_user_type");
				subElement.addElement("type_code").setText(cardUserType.getTypeCode());
				subElement.addElement("type_name").setText(cardUserType.getTypeName());	
			}
		}
		if(firstAction != null){
			root.addElement("first_action").setText(BooleanUtils.toString(firstAction, "1", "0"));
		}
		if(actionUrl != null) {
			root.addElement("action_url").setText(actionUrl);
		}
		if(weixinActionTitle != null) {
			root.addElement("weixin_action_title").setText(weixinActionTitle);
		}
		if(weixinActionTitleUrl != null) {
			root.addElement("weixin_action_title_url").setText(weixinActionTitleUrl);
		}
		if(weixinActionDetailUrl != null) {
			root.addElement("weixin_action_detail_url").setText(weixinActionDetailUrl);
		}
		if(weixinActionShare != null) {
			root.addElement("weixin_action_share").setText(BooleanUtils.toString(weixinActionShare, "1", "0"));
		}
		if(weixinActionTitleOssUrl != null) {
			root.addElement("weixin_action_title_oss_url").setText(weixinActionTitleOssUrl);
		}
		if(weixinActionShareLink != null) {
			root.addElement("weixin_action_share_link").setText(weixinActionShareLink);
		}
		if(weixinActionQrcodeUrl != null) {
			root.addElement("weixin_action_qrcode_url").setText(weixinActionQrcodeUrl);
		}
		if(weixinActionMsg != null) {
			root.addElement("weixin_action_msg").setText(weixinActionMsg);
		}
		return document.asXML();
	}

}
