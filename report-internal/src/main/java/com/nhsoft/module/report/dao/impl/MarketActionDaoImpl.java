package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.MarketActionDao;
import com.nhsoft.module.report.model.MarketAction;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class MarketActionDaoImpl extends DaoImpl implements MarketActionDao {
	@Override
	public int count(String systemBookCode, Integer branchNum, Date dateFrom,
					 Date dateTo, Integer stateCode, String actionType) {
		Criteria criteria = createCriteria(systemBookCode, branchNum, dateFrom, dateTo, stateCode, actionType);
		criteria.setProjection(Projections.rowCount());
		Object object = criteria.uniqueResult();
		return ((Long)object).intValue();
	}

	private Criteria createCriteria(String systemBookCode, Integer branchNum,
									Date dateFrom, Date dateTo, Integer stateCode, String actionType){
		Criteria criteria = currentSession().createCriteria(MarketAction.class, "m")
				.add(Restrictions.eq("m.systemBookCode", systemBookCode));
		if(branchNum != null){
			criteria.add(Restrictions.eq("m.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("m.actionCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("m.actionCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if(stateCode != null){
			criteria.add(Restrictions.eq("m.actionState.stateCode", stateCode));
		}
		if(StringUtils.isNotEmpty(actionType)){
			criteria.add(Restrictions.eq("m.actionType", actionType));
		} else {
			criteria.add(Restrictions.ne("m.actionType", AppConstants.MARKETACTION_MOMENTS_ACTION));
		}
		return criteria;
	}
}
