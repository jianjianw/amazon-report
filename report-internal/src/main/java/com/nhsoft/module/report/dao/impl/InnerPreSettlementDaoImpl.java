package com.nhsoft.module.report.dao.impl;



import com.nhsoft.module.report.dao.InnerPreSettlementDao;
import com.nhsoft.module.report.model.InnerPreSettlement;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.hibernate.Criteria;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
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
		return BigDecimal.ZERO;
	}
}
