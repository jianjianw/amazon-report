package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.SettlementDao;
import com.nhsoft.module.report.model.Settlement;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.hibernate.Criteria;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class SettlementDaoImpl extends DaoImpl implements SettlementDao {
	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(Settlement.class, "s")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("s.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("s.settlementCreateTme", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("s.settlementCreateTme", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}
}
