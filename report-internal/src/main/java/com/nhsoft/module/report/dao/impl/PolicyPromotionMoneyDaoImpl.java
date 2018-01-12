package com.nhsoft.module.report.dao.impl;



import com.nhsoft.module.report.dao.PolicyPromotionMoneyDao;
import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.module.report.model.PolicyPromotionMoney;
import com.nhsoft.module.report.query.PolicyPosItemQuery;

import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Repository
public class PolicyPromotionMoneyDaoImpl extends DaoImpl implements PolicyPromotionMoneyDao {
	@Override
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom,
					 Date dateTo, String dateType) {
		Criteria criteria = createCriteria(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	private Criteria createCriteria(String systemBookCode, List<Integer> branchNums,
									Date dateFrom, Date dateTo, String dateType) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Criteria criteria = currentSession().createCriteria(PolicyPromotionMoney.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("p.branchNum", branchNums));

		}
		if(dateType.equals(AppConstants.STATE_INIT_TIME)){
			criteria.add(Restrictions.between("p.promotionMoneyCreateTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.STATE_AUDIT_TIME)){
			criteria.add(Restrictions.between("p.promotionMoneyAuditTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.POLICY_ORDER_TIME)){
			dateFrom = DateUtil.getMinOfDate(dateFrom);
			dateTo = DateUtil.getMaxOfDate(dateTo);
			criteria.add(Restrictions.le("p.promotionMoneyDateFrom", dateTo))
					.add(Restrictions.ge("p.promotionMoneyDateTo", dateFrom));
		}
		return criteria;
	}


	@Override
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery) {
		Date dateFrom = DateUtil.getMinOfDate(posItemQuery.getDtFrom());
		Date dateTo = DateUtil.getMaxOfDate(posItemQuery.getDtTo());
		Criteria criteria = currentSession().createCriteria(PolicyPromotionMoney.class, "p")
				.createAlias("p.policyPromotionMoneyDetails", "detail")
				.add(Restrictions.eq("p.systemBookCode", posItemQuery.getSystemBookCode()))
				.add(Restrictions.eq("p.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.le("p.promotionMoneyDateFrom", dateTo))
				.add(Restrictions.ge("p.promotionMoneyDateTo", dateFrom));

		if(posItemQuery.getItemNums() != null && posItemQuery.getItemNums().size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", posItemQuery.getItemNums()));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("p.promotionMoneyAppliedBranch"))
				.add(Projections.property("p.promotionMoneyDateFrom"))
				.add(Projections.property("p.promotionMoneyDateTo"))
				.add(Projections.property("p.promotionMoneyTimeFrom"))
				.add(Projections.property("p.promotionMoneyTimeTo"))
				.add(Projections.property("p.promotionMoneyNo"))
				.add(Projections.property("p.promotionMoneyCreator"))
				.add(Projections.property("p.promotionMoneyAuditor"))
				.add(Projections.property("detail.promotionMoneyDetailAmountLimit"))
				.add(Projections.property("detail.promotionMoneyDetailSpecialPrice"))
				.add(Projections.property("p.promotionMoneyMonActived"))
				.add(Projections.property("p.promotionMoneyTuesActived"))
				.add(Projections.property("p.promotionMoneyWedActived"))
				.add(Projections.property("p.promotionMoneyThursActived"))
				.add(Projections.property("p.promotionMoneyFridayActived"))
				.add(Projections.property("p.promotionMoneySatActived"))
				.add(Projections.property("p.promotionMoneySunActived"))
				.add(Projections.property("p.promotionMoneyCardOnly"))


		);
		List<PolicyPosItem> list = new ArrayList<PolicyPosItem>();
		List<Object[]> objects = criteria.list();
		for(int i = 0;i < objects.size();i++){
			PolicyPosItem policyPosItem = new PolicyPosItem();
			Object[] object = objects.get(i);
			policyPosItem.setItemNum((Integer)object[0]);
			policyPosItem.setPolicyAppliedBranch((String)object[1]);
			policyPosItem.setDateFrom((Date)object[2]);
			policyPosItem.setDateTo((Date)object[3]);
			policyPosItem.setTimeFrom((Date)object[4]);
			policyPosItem.setTimeTo((Date)object[5]);
			policyPosItem.setPolicyNo((String)object[6]);
			policyPosItem.setPolicyCreator((String)object[7]);
			policyPosItem.setPolicyAuditor((String)object[8]);
			policyPosItem.setPolicyType(AppConstants.POLICY_PROMOTION_MONEY);
			policyPosItem.setBillLimmit((BigDecimal)object[9]);
			policyPosItem.setPolicyPrice((BigDecimal)object[10]);
			String value = "";
			if((Boolean)object[11]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[12]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[13]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[14]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[15]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[16]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[17]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			policyPosItem.setEffectiveDate(value);
			policyPosItem.setCardOnly(object[18] == null?false:(Boolean)object[18]);
			list.add(policyPosItem);
		}
		return list;
	}

}
