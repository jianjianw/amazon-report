package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.CardSettlementDao;
import com.nhsoft.module.report.model.CardSettlement;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
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
	
	@Override
	public List<CardSettlement> findBySettleBranch(String systemBookCode, Integer branchNum, Integer settleBranchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(CardSettlement.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode))
				.add(Restrictions.eq("c.branchNum", branchNum))
				.add(Restrictions.eq("c.toBranchNum", settleBranchNum))
				.add(Restrictions.eq("c.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.cardSettlementAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.cardSettlementAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findMoneyBySettleBranch(String systemBookCode, Integer branchNum, Integer settleBranchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(CardSettlement.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode))
				.add(Restrictions.eq("c.branchNum", branchNum))
				.add(Restrictions.eq("c.toBranchNum", settleBranchNum))
				.add(Restrictions.eq("c.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.cardSettlementAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.cardSettlementAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sum("c.cardSettlementMoney"))
				.add(Projections.sum("c.cardSettlementPaidMoney"))
				.add(Projections.sum("c.cardSettlementDiscountMoney"))
		);
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findBranchsMoney(String systemBookCode, Integer branchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(CardSettlement.class, "c")
				.add(Restrictions.eq("c.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNum != null){
			criteria.add(Restrictions.eq("c.branchNum", branchNum));
			
		}
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("c.toBranchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.cardSettlementAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.cardSettlementAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.toBranchNum"))
				.add(Projections.sum("c.cardSettlementMoney"))
				.add(Projections.sqlProjection("sum(card_settlement_money - card_settlement_paid_money - card_settlement_discount_money ) as unpay",
						new String[]{"unpay"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}
}
