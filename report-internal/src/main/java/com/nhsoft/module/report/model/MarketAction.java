package com.nhsoft.module.report.model;




import com.nhsoft.module.report.param.PosActionParam;
import com.nhsoft.module.report.query.State;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * MarketAction generated by hbm2java
 */
public class MarketAction implements java.io.Serializable {

	private static final long serialVersionUID = 2785910907587259547L;
	private String actionId;
	private String systemBookCode;
	private Integer branchNum;
	private String actionName;
	private Date actionCreateTime;
	private String actionCreator;
	private Date actionAuditTime;
	private String actionAuditor;
	private String actionType;
	private String actionCondition;
	private State actionState;
	private String actionMemo;
	private String actionSmsTemplate;
	private Date actionDateFrom;
	private Date actionDateTo;
	private Integer actionSceneId;
	private List<MarketActionDetail> marketActionDetails = new ArrayList<MarketActionDetail>(0);
	private List<GroupCustomer> groupCustomers = new ArrayList<GroupCustomer>();

	private PosActionParam posActionParam;
	
	//朋友圈发券交互对象
	private MarketData marketData;

	public MarketAction() {
	}

	public String getActionId() {
		return this.actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
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

	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Date getActionCreateTime() {
		return this.actionCreateTime;
	}

	public void setActionCreateTime(Date actionCreateTime) {
		this.actionCreateTime = actionCreateTime;
	}

	public String getActionCreator() {
		return this.actionCreator;
	}

	public void setActionCreator(String actionCreator) {
		this.actionCreator = actionCreator;
	}

	public Date getActionAuditTime() {
		return this.actionAuditTime;
	}

	public void setActionAuditTime(Date actionAuditTime) {
		this.actionAuditTime = actionAuditTime;
	}

	public String getActionAuditor() {
		return this.actionAuditor;
	}

	public void setActionAuditor(String actionAuditor) {
		this.actionAuditor = actionAuditor;
	}

	public String getActionType() {
		return this.actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActionCondition() {
		return this.actionCondition;
	}

	public void setActionCondition(String actionCondition) {
		this.actionCondition = actionCondition;
	}

	public State getActionState() {
		return actionState;
	}

	public void setActionState(State actionState) {
		this.actionState = actionState;
	}

	public String getActionMemo() {
		return this.actionMemo;
	}

	public void setActionMemo(String actionMemo) {
		this.actionMemo = actionMemo;
	}

	public String getActionSmsTemplate() {
		return this.actionSmsTemplate;
	}

	public void setActionSmsTemplate(String actionSmsTemplate) {
		this.actionSmsTemplate = actionSmsTemplate;
	}

	public Date getActionDateFrom() {
		return this.actionDateFrom;
	}

	public void setActionDateFrom(Date actionDateFrom) {
		this.actionDateFrom = actionDateFrom;
	}

	public Date getActionDateTo() {
		return this.actionDateTo;
	}

	public void setActionDateTo(Date actionDateTo) {
		this.actionDateTo = actionDateTo;
	}

	public List<MarketActionDetail> getMarketActionDetails() {
		return marketActionDetails;
	}

	public void setMarketActionDetails(List<MarketActionDetail> marketActionDetails) {
		this.marketActionDetails = marketActionDetails;
	}

	public List<GroupCustomer> getGroupCustomers() {
		return groupCustomers;
	}

	public void setGroupCustomers(List<GroupCustomer> groupCustomers) {
		this.groupCustomers = groupCustomers;
	}

	public PosActionParam getPosActionParam() {
		return posActionParam;
	}

	public void setPosActionParam(PosActionParam posActionParam) {
		this.posActionParam = posActionParam;
	}

	public Integer getActionSceneId() {
		return actionSceneId;
	}

	public void setActionSceneId(Integer actionSceneId) {
		this.actionSceneId = actionSceneId;
	}

	public String getMarketActionSmsText(CardUser cardUser, MarketActionDetail detail, Integer count,
			String printedNum, String smsTemplate) {
		if (cardUser == null) {
			return null;
		}

		String customerPhone = cardUser.getCardUserPhone();
		if (!AppUtil.isValidMobilePhoneNumber(customerPhone)) {
			return null;
		}

		String customerName = cardUser.getCardUserCustName();
		if (customerName == null) {
			customerName = cardUser.getCardUserPhone();
		} else {
			if (cardUser.getCardUserCustSex() != null) {
				if (cardUser.getCardUserCustSex().equals("男")) {
					customerName = customerName + "先生";
				} else if (cardUser.getCardUserCustSex().equals("女")) {
					customerName = customerName + "女士";
				}
			}
		}

		Date validDate = null;
		if(detail.getMarketActionDetailDay() != null){
			if (detail.getMarketActionDetailEffectiveDate() == null) {
				
				validDate = DateUtil.addDay(Calendar.getInstance().getTime(),
						detail.getMarketActionDetailDay());

			} else {
				
				validDate = DateUtil.addDay(detail.getMarketActionDetailEffectiveDate(),
						detail.getMarketActionDetailDay());
			}
		} else {
			validDate = detail.getMarketActionDetailDate();
		}
		String smsText = StringUtils.replace(smsTemplate, "[称呼]", customerName);
		smsText = StringUtils.replace(smsText, "[优惠券类型]", detail.getMarketActionDetailTypeName());
		smsText = StringUtils.replace(smsText, "[有效期]", DateUtil.getShortDateTimeStr(validDate));
		smsText = StringUtils.replace(smsText, "[面值]", detail.getMarketActionDetailValue() + "");
		smsText = StringUtils.replace(smsText, "[数量]", count + "");
		smsText = StringUtils.replace(smsText, "[券编号]", printedNum + "");
		int printedNumLength = cardUser.getCardUserPrintedNum().length();
		if(printedNumLength < 4){
			smsText = StringUtils.replace(smsText, "[会员卡尾号]", cardUser.getCardUserPrintedNum());

		} else {
			smsText = StringUtils.replace(smsText, "[会员卡尾号]", cardUser.getCardUserPrintedNum().substring(printedNumLength - 4));
			
		}
		return smsText;
	}

	/**
	 * 查询应用的营销活动
	 * @param marketActions
	 * @param onlineOrder
	 * @return
	 */
	public static MarketAction filter(List<MarketAction> marketActions, OnlineOrder onlineOrder) {
		Date now = Calendar.getInstance().getTime();
		Date min = DateUtil.getMinOfDate(now);
		Date max = DateUtil.getMaxOfDate(now);
		for(int i = 0;i < marketActions.size();i++){
			MarketAction marketAction = marketActions.get(i);
			if(marketAction.getActionDateFrom() != null && marketAction.getActionDateFrom().compareTo(max) > 0){
				continue;
			}
			if(marketAction.getActionDateTo() != null && marketAction.getActionDateTo().compareTo(min) < 0){
				continue;
			}
			PosActionParam posActionParam = PosActionParam.readFromXml(marketAction.getActionCondition());
			if(posActionParam == null){
				continue;
			}
			List<Branch> applyBranchs = posActionParam.getBranchs();
			if(applyBranchs.size() > 0){
				boolean find = false;
				Branch branch = null;
				for(int j = 0;j < applyBranchs.size();j++){
					branch = applyBranchs.get(j);
					if(branch.getId().getBranchNum() == 0){
						find = true;
						break;
					}
					if(branch.getId().getBranchNum() == onlineOrder.getBranchNum().intValue()){
						find = true;
						break;
					}
				}
				if(!find){
					continue;
				}
			}
			if(posActionParam.getActionType().equals(AppConstants.MARKET_ACTION_TYPE_PAYMENT)){
				if(!posActionParam.getPaymentTypes().contains(onlineOrder.getOnlineOrderPaymentType())){
					continue;
				}
			} 
			if(StringUtils.isNotEmpty(posActionParam.getExceptDate())){
				if(posActionParam.getExceptDate().equals(DateUtil.getDay(now))){
					continue;
				}
			}
			marketAction.setPosActionParam(posActionParam);
			return marketAction;
			
		}
		return null;
	}

	public MarketData getMarketData() {
		return marketData;
	}

	public void setMarketData(MarketData marketData) {
		this.marketData = marketData;
	}
}