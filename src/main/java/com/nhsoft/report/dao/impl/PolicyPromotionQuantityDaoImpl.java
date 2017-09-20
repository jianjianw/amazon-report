package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PolicyPromotionQuantityDao;
import com.nhsoft.report.model.PolicyPromotionQuantity;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
public class PolicyPromotionQuantityDaoImpl extends DaoImpl implements PolicyPromotionQuantityDao {
	@Override
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom,
					 Date dateTo, String dateType, String promotionQuantityCategory) {
		Criteria criteria = createCriteria(systemBookCode, branchNums, dateFrom, dateTo, dateType, promotionQuantityCategory);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	private Criteria createCriteria(String systemBookCode, List<Integer> branchNums,
									Date dateFrom, Date dateTo, String dateType, String promotionQuantityCategory) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Criteria criteria = currentSession().createCriteria(PolicyPromotionQuantity.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("p.branchNum", branchNums));

		}
		if(dateType.equals(AppConstants.STATE_INIT_TIME)){
			criteria.add(Restrictions.between("p.promotionQuantityCreateTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.STATE_AUDIT_TIME)){
			criteria.add(Restrictions.between("p.promotionQuantityAuditTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.POLICY_ORDER_TIME)){

			criteria.add(Restrictions.le("p.promotionQuantityDateFrom", dateTo))
					.add(Restrictions.ge("p.promotionQuantityDateTo", dateFrom));
		}
		if(StringUtils.isNotEmpty(promotionQuantityCategory)){
			criteria.add(Restrictions.eq("p.promotionQuantityCategory", promotionQuantityCategory));
		} else {
			criteria.add(Restrictions.isNull("p.promotionQuantityCategory"));
		}
		return criteria;
	}
}
