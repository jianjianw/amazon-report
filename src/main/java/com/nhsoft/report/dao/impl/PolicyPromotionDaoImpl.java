package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PolicyPromotionDao;
import com.nhsoft.report.dto.PolicyPosItem;
import com.nhsoft.report.model.PolicyPromotion;
import com.nhsoft.report.shared.queryBuilder.PolicyPosItemQuery;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Repository
public class PolicyPromotionDaoImpl extends DaoImpl implements PolicyPromotionDao {
	@Override
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom,
					 Date dateTo, String dateType, String policyPromotionCategory, List<Integer> stateCodes) {
		Criteria criteria = createCriteria(systemBookCode, branchNums, dateFrom, dateTo, dateType, policyPromotionCategory, stateCodes);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	private Criteria createCriteria(String systemBookCode, List<Integer> branchNums,
									Date dateFrom, Date dateTo, String dateType, String policyPromotionCategory, List<Integer> stateCodes) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Criteria criteria = currentSession().createCriteria(PolicyPromotion.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("p.branchNum", branchNums));

		}
		if(stateCodes != null && stateCodes.size() > 0){
			criteria.add(Restrictions.in("p.state.stateCode", stateCodes));

		}
		if(dateType.equals(AppConstants.STATE_INIT_TIME)){
			criteria.add(Restrictions.between("p.policyPromotionCreateTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.STATE_AUDIT_TIME)){
			criteria.add(Restrictions.between("p.policyPromotionAuditTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.POLICY_ORDER_TIME)){

			criteria.add(Restrictions.le("p.policyPromotionDateFrom", dateTo))
					.add(Restrictions.ge("p.policyPromotionDateTo", dateFrom));
		}
		if(StringUtils.isNotEmpty(policyPromotionCategory)){
			criteria.add(Restrictions.eq("p.policyPromotionCategory", policyPromotionCategory));
		} else {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.isNull("p.policyPromotionCategory"))
					.add(Restrictions.eq("p.policyPromotionCategory", ""))

			);
		}
		return criteria;
	}


	@Override
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery) {
		Date dateFrom = DateUtil.getMinOfDate(posItemQuery.getDtFrom());
		Date dateTo = DateUtil.getMaxOfDate(posItemQuery.getDtTo());
		Criteria criteria = currentSession().createCriteria(PolicyPromotion.class, "p")
				.createAlias("p.policyPromotionDetails", "detail")
				.add(Restrictions.eq("p.systemBookCode", posItemQuery.getSystemBookCode()))
				.add(Restrictions.eq("p.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.le("p.policyPromotionDateFrom", dateTo))
				.add(Restrictions.ge("p.policyPromotionDateTo", dateFrom));

		if(posItemQuery.getItemNums() != null && posItemQuery.getItemNums().size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", posItemQuery.getItemNums()));
		}
		if(StringUtils.isNotEmpty(posItemQuery.getPolicyCategory())){

			criteria.add(Restrictions.eq("p.policyPromotionCategory", posItemQuery.getPolicyCategory()));


		} else 	{
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("p.policyPromotionCategory", ""))
					.add(Restrictions.isNull("p.policyPromotionCategory")));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("p.policyPromotionAppliedBranch"))
				.add(Projections.property("detail.policyPromotionDetailStdPrice"))
				.add(Projections.property("detail.policyPromotionDetailSpecialPrice"))
				.add(Projections.property("p.policyPromotionDateFrom"))
				.add(Projections.property("p.policyPromotionDateTo"))
				.add(Projections.property("p.policyPromotionTimeFrom"))
				.add(Projections.property("p.policyPromotionTimeTo"))
				.add(Projections.property("p.policyPromotionNo"))
				.add(Projections.property("p.policyPromotionCreator"))
				.add(Projections.property("p.policyPromotionAuditor"))
				.add(Projections.property("p.policyPromotionMonActived"))
				.add(Projections.property("p.policyPromotionTuesActived"))
				.add(Projections.property("p.policyPromotionWedActived"))
				.add(Projections.property("p.policyPromotionThursActived"))
				.add(Projections.property("p.policyPromotionFridayActived"))
				.add(Projections.property("p.policyPromotionSatActived"))
				.add(Projections.property("p.policyPromotionSunActived"))
				.add(Projections.property("p.policyPromotionCategory"))
				.add(Projections.property("detail.itemGradeNum"))
				.add(Projections.property("detail.policyPromotionDetailBillLimit"))
				.add(Projections.property("p.policyPromotionCardOnly"))


		);
		List<PolicyPosItem> list = new ArrayList<PolicyPosItem>();
		List<Object[]> objects = criteria.list();
		String policyPromotionCategory = null;

		for(int i = 0;i < objects.size();i++){
			PolicyPosItem policyPosItem = new PolicyPosItem();
			Object[] object = objects.get(i);
			policyPosItem.setItemNum((Integer)object[0]);
			policyPosItem.setPolicyAppliedBranch((String)object[1]);
			policyPosItem.setPolicyStdPrice((BigDecimal)object[2]);
			policyPosItem.setPolicyPrice((BigDecimal)object[3]);
			policyPosItem.setDateFrom((Date)object[4]);
			policyPosItem.setDateTo((Date)object[5]);
			policyPosItem.setTimeFrom((Date)object[6]);
			policyPosItem.setTimeTo((Date)object[7]);
			policyPosItem.setPolicyNo((String)object[8]);
			policyPosItem.setPolicyCreator((String)object[9]);
			policyPosItem.setPolicyAuditor((String)object[10]);
			policyPosItem.setPolicyType(AppConstants.POLICY_PROMOTION);
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
			if(StringUtils.isNotEmpty(posItemQuery.getPolicyCategory())){
				policyPosItem.setPolicyType(posItemQuery.getPolicyCategory());

			} else {

				policyPromotionCategory = (String)object[18];
				if(StringUtils.isNotEmpty(policyPromotionCategory)){
					policyPosItem.setPolicyType(policyPromotionCategory);

				} else {
					policyPosItem.setPolicyType(AppConstants.POLICY_PROMOTION);

				}

			}
			policyPosItem.setItemGradeNum((Integer)object[19]);
			policyPosItem.setBillLimmit(object[20] == null?BigDecimal.ZERO:(BigDecimal)object[20] );
			policyPosItem.setPolicyType(AppConstants.POLICY_PROMOTION);
			policyPosItem.setCardOnly(object[21] == null?false:(Boolean)object[21] );
			list.add(policyPosItem);
		}
		return list;
	}
}
