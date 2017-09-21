package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.CardSettlementDao;
import com.nhsoft.report.model.CardSettlement;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;

import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
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


	@Override
	public BigDecimal readBranchUnPaidMoney(String systemBookCode,
											Integer branchNum, Integer centerBranchNum) {
		String hql = " select sum(cardSettlementMoney - cardSettlementPaidMoney - cardSettlementDiscountMoney) from CardSettlement" +
				" where systemBookCode = :systemBookCode and state.stateCode = :stateCode " +
				" and branchNum = :centerBranch and toBranchNum = :branchNum " +
				" and abs(cardSettlementMoney - cardSettlementPaidMoney - cardSettlementDiscountMoney) > 0.01";
		Query query = currentSession().createQuery(hql);
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setInteger("stateCode", AppConstants.STATE_INIT_AUDIT_CODE);
		query.setInteger("centerBranch",centerBranchNum);
		Object object = query.uniqueResult();
		if(object != null){
			return (BigDecimal)object;
		}
		return BigDecimal.ZERO;
	}
}
