package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.InnerSettlementDao;
import com.nhsoft.module.report.model.InnerSettlement;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
		criteria.setLockMode(LockMode.NONE);
		return ((Long)criteria.uniqueResult()).intValue();
	}
	
	@Override
	public List<Object[]> findMoneyByBranchNums(String systemBookCode,
	                                            Integer branchNum, Date dateFrom, Date dateTo,
	                                            List<Integer> branchNums, boolean flag) {
		Criteria criteria = currentSession().createCriteria(InnerSettlement.class, "i")
				.add(Restrictions.eq("i.centerBranchNum", branchNum))
				.add(Restrictions.eq("i.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("i.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("i.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("i.innerSettlementAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("i.innerSettlementAuditTime", DateUtil.getMaxOfDate(dateTo)));
			
		}
		if(flag){
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("i.branchNum"))
					.add(Projections.sqlProjection("sum(case when inner_settlement_payment_type = '预收冲应收' then 0 else inner_settlement_total_money end) as money", new String[]{"money"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
					.add(Projections.sum("i.innerSettlementTotalDiscount"))
			);
		} else {
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("i.branchNum"))
					.add(Projections.sum("i.innerSettlementTotalMoney"))
					.add(Projections.sum("i.innerSettlementTotalDiscount"))
			);
		}
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}
	@Override
	public List<InnerSettlement> findBySettleBranchNum(String systemBookCode,
	                                                   Integer centerBranchNum, Integer branchNum, Date dateFrom,
	                                                   Date dateTo) {
		Criteria criteria = currentSession().createCriteria(InnerSettlement.class, "i")
				.add(Restrictions.eq("i.centerBranchNum", centerBranchNum))
				.add(Restrictions.eq("i.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("i.systemBookCode", systemBookCode));
		if(branchNum != null){
			criteria.add(Restrictions.eq("i.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("i.innerSettlementAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("i.innerSettlementAuditTime", DateUtil.getMaxOfDate(dateTo)));
			
		}
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}
}
