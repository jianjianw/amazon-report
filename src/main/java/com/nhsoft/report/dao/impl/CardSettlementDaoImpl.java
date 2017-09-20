package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.CardSettlementDao;
import com.nhsoft.report.model.CardSettlement;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
public class CardSettlementDaoImpl extends DaoImpl implements CardSettlementDao {
	@Override
	public Integer countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(CardSettlement.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("c.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.cardSettlementCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.cardSettlementCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

}
