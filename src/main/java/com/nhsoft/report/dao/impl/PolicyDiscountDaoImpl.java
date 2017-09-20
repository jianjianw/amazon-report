package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PolicyDiscountDao;
import com.nhsoft.report.model.PolicyDiscount;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
@SuppressWarnings("deprecation")
public class PolicyDiscountDaoImpl extends DaoImpl implements PolicyDiscountDao {
	@Override
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		Criteria criteria = createCriteria(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	private Criteria createCriteria(String systemBookCode, List<Integer> branchNums,
									Date dateFrom, Date dateTo, String dateType) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Criteria criteria = currentSession().createCriteria(PolicyDiscount.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("p.branchNum", branchNums));

		}
		if(dateType.equals(AppConstants.STATE_INIT_TIME)){
			criteria.add(Restrictions.between("p.policyDiscountCreateTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.STATE_AUDIT_TIME)){
			criteria.add(Restrictions.between("p.policyDiscountAuditTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.POLICY_ORDER_TIME)){

			dateFrom = DateUtil.getMinOfDate(dateFrom);
			dateTo = DateUtil.getMinOfDate(dateTo);
			criteria.add(Restrictions.le("p.policyDiscountDateFrom", dateTo))
					.add(Restrictions.ge("p.policyDiscountDateTo", dateFrom));
		}
		return criteria;
	}
}
