package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PolicyPromotionMoneyDao;
import com.nhsoft.report.model.PolicyPromotionMoney;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
}
