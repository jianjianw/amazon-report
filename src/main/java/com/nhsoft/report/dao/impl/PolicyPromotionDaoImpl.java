package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PolicyPromotionDao;
import com.nhsoft.report.model.PolicyPromotion;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

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
}
