package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PreSettlementDao;
import com.nhsoft.report.model.PreSettlement;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class PreSettlementDaoImpl extends DaoImpl implements PreSettlementDao {
	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(PreSettlement.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("p.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.preSettlementCreatorTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.preSettlementCreatorTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}
}
