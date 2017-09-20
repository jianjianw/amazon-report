package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.InnerSettlementDao;
import com.nhsoft.report.model.InnerSettlement;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class InnerSettlementDaoImpl extends DaoImpl implements InnerSettlementDao {
	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(InnerSettlement.class, "s")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("s.centerBranchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("s.innerSettlementCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("s.innerSettlementCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}
}
