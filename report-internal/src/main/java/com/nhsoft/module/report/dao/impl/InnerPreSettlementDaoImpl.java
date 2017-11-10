package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.InnerPreSettlementDao;
import com.nhsoft.module.report.model.InnerPreSettlement;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class InnerPreSettlementDaoImpl extends DaoImpl implements InnerPreSettlementDao {
	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(InnerPreSettlement.class, "s")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("s.centerBranchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("s.preSettlementCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("s.preSettlementCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		criteria.setLockMode(LockMode.NONE);
		return ((Long)criteria.uniqueResult()).intValue();
	}


	@Override
	public BigDecimal readBranchUnPaidMoney(String systemBookCode,
											Integer branchNum, Integer centerBranchNum) {
		Criteria criteria = currentSession().createCriteria(InnerPreSettlement.class, "t")
				.add(Restrictions.eq("t.systemBookCode", systemBookCode))
				.add(Restrictions.eq("t.centerBranchNum", centerBranchNum))
				.add(Restrictions.eq("t.state.stateCode",AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("t.branchNum", branchNum));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sum("t.preSettlementPaid"))
				.add(Projections.sum("t.preSettlementMoney")));
		Object[] object = (Object[]) criteria.uniqueResult();
		if(object != null){
			BigDecimal paid = object[0] == null?BigDecimal.ZERO:(BigDecimal)object[0];
			BigDecimal money = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
			BigDecimal total = paid.subtract(money);
			return total;
		}
		criteria.setLockMode(LockMode.NONE);
		
		return BigDecimal.ZERO;
	}
	
	@Override
	public List<InnerPreSettlement> findBySettleBranch(String systemBookCode, Integer branchNum, Integer centerBranchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(InnerPreSettlement.class, "t")
				.add(Restrictions.eq("t.systemBookCode", systemBookCode))
				.add(Restrictions.eq("t.centerBranchNum", centerBranchNum))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("t.branchNum", branchNum));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("t.preSettlementAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("t.preSettlementAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("t.preSettlementNo"))
				.add(Projections.property("t.preSettlementAuditTime"))
				.add(Projections.property("t.preSettlementMemo"))
				.add(Projections.property("t.preSettlementPaid"))
				.add(Projections.property("t.preSettlementMoney"))
		
		);
		List<InnerPreSettlement> list = new ArrayList<InnerPreSettlement>();
		criteria.setLockMode(LockMode.NONE);
		
		List<Object[]> objects = criteria.list();
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			InnerPreSettlement innerPreSettlement = new InnerPreSettlement();
			innerPreSettlement.setPreSettlementNo((String)object[0]);
			innerPreSettlement.setPreSettlementAuditTime((Date)object[1]);
			innerPreSettlement.setPreSettlementMemo((String)object[2]);
			innerPreSettlement.setPreSettlementPaid((BigDecimal)object[3]);
			innerPreSettlement.setPreSettlementMoney(object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4]);
			list.add(innerPreSettlement);
		}
		return list;
	}
	
	@Override
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> branchNums) {
		Criteria criteria = currentSession().createCriteria(InnerPreSettlement.class, "i")
				.add(Restrictions.eq("i.centerBranchNum", branchNum))
				.add(Restrictions.eq("i.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("i.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("i.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("i.preSettlementDate", dateFrom));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("i.preSettlementDate", dateTo));
			
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("i.branchNum"))
				.add(Projections.sum("i.preSettlementPaid"))
		);
		criteria.setLockMode(LockMode.NONE);
		
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findDueMoney(String systemBookCode, Integer branchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branchNum, sum(preSettlementPaid - preSettlementMoney ) ");
		sb.append("from InnerPreSettlement where state.stateCode = 3 and systemBookCode = :systemBookCode and centerBranchNum = :branchNum ");
		if(dateFrom != null){
			sb.append("and preSettlementDate >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and preSettlementDate <= :dateTo ");
		}
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branchNum in (:branchNums) ");
		}
		sb.append("group by branchNum ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		if(branchNums != null && branchNums.size() > 0){
			query.setParameterList("branchNums", branchNums);
		}
		return query.list();
	}
}
