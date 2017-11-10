package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.OtherInoutDao;
import com.nhsoft.module.report.model.OtherInout;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
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

/**
 * Created by yangqin on 2017/9/20.
 */
@Repository
public class OtherInoutDaoImpl extends  DaoImpl implements OtherInoutDao {
	@Override
	public List<Object[]> findPosBranchFlagKindSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, other_inout_flag, other_inout_kind, sum(other_inout_money) as money ");
		sb.append("from other_inout with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && !branchNums.isEmpty()){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and other_inout_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("group by branch_num, other_inout_flag, other_inout_kind ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
	
	@Override
	public List<Object[]> findClientsMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> clientFids) {
		Criteria criteria = currentSession().createCriteria(OtherInout.class, "o")
				.add(Restrictions.eq("o.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.isNotNull("o.clientFid")).add(Restrictions.eq("o.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("o.branchNum", branchNums));
			
		}
		if (clientFids != null && clientFids.size() > 0) {
			criteria.add(Restrictions.in("o.clientFid", clientFids));
		}
		if (dateFrom != null) {

			criteria.add(Restrictions.ge("o.otherInoutAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("o.otherInoutAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.groupProperty("o.clientFid"))
				.add(Projections.groupProperty("o.otherInoutFlag"))
				.add(Projections.sum("o.otherInoutMoney"))
				.add(Projections.sqlProjection(
						"sum(OTHER_INOUT_DUE_MONEY - OTHER_INOUT_DISCOUNT_MONEY - OTHER_INOUT_PAID_MONEY ) as unpay",
						new String[] { "unpay" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
				.add(Projections.sum("o.otherInoutDueMoney"))
		);
		return criteria.list();
	}

	@Override
	public BigDecimal getUnPaidMoney(String systemBookCode, Integer branchNum, Integer innerBranchNum,
									 Integer supplierNum, String clientFid, int type) {
		Criteria criteria = currentSession()
				.createCriteria(OtherInout.class, "o")
				.add(Restrictions.eq("o.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions
						.sqlRestriction("abs(other_inout_due_money - other_inout_paid_money - other_inout_discount_money) > 0.01"))
				.add(Restrictions.eq("o.systemBookCode", systemBookCode));
		if (branchNum != null) {
			criteria.add(Restrictions.eq("o.branchNum", branchNum));

		}
		if (type == 0) {
			criteria.add(Restrictions.isNotNull("o.innerBranchNum"));
		} else if (type == 1) {
			criteria.add(Restrictions.isNotNull("o.supplierNum"));
		} else if (type == 2) {
			criteria.add(Restrictions.isNotNull("o.clientFid"));
		}
		if (innerBranchNum != null) {
			criteria.add(Restrictions.eq("o.innerBranchNum", innerBranchNum));
		}
		if (supplierNum != null) {
			criteria.add(Restrictions.eq("o.supplierNum", supplierNum));
		}
		if (clientFid != null) {
			criteria.add(Restrictions.eq("o.clientFid", clientFid));
		}
		criteria.setProjection(Projections.sqlProjection(
				"sum(other_inout_due_money - other_inout_paid_money - other_inout_discount_money) as unPaid",
				new String[] { "unPaid" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }));
		Object object = criteria.uniqueResult();
		if (object != null) {
			return (BigDecimal) object;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getCashMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		String sql = "select sum(other_inout_money) as cash " + " from other_inout with(nolock) where system_book_code = '"
				+ systemBookCode + "'  " + " and branch_num in " + AppUtil.getIntegerParmeList(branchNums)
				+ " and other_inout_state_code = 3" + " and other_inout_payment_type = '现金' "
				+ " and other_inout_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' ";
		Query query = currentSession().createSQLQuery(sql);
		Object object = query.uniqueResult();
		return object == null ? BigDecimal.ZERO : (BigDecimal) object;
	}

	private Criteria createByCash(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(OtherInout.class, "o")
				.add(Restrictions.eq("o.otherInoutPaymentType", AppConstants.PAYMENT_CASH))
				.add(Restrictions.eq("o.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("o.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("o.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("o.otherInoutBizday", DateUtil.getDateShortStr(dateFrom)));

		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("o.otherInoutBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setLockMode(LockMode.NONE);
		return criteria;
	}


	@Override
	public List<Object[]> findCashGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo) {
		Criteria criteria = createByCash(systemBookCode, branchNums, dateFrom, dateTo);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("o.branchNum"))
				.add(Projections.sum("o.otherInoutMoney")));
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findBranchsMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> innerBranchNums) {
		Criteria criteria = currentSession().createCriteria(OtherInout.class, "o")
				.add(Restrictions.eq("o.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.isNotNull("o.innerBranchNum"))
				.add(Restrictions.eq("o.systemBookCode", systemBookCode));
		if (branchNum != null) {
			criteria.add(Restrictions.eq("o.branchNum", branchNum));
			
		}
		if (innerBranchNums != null && innerBranchNums.size() > 0) {
			criteria.add(Restrictions.in("o.innerBranchNum", innerBranchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("o.otherInoutAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("o.otherInoutAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setLockMode(LockMode.NONE);
		
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.groupProperty("o.innerBranchNum"))
				.add(Projections.groupProperty("o.otherInoutFlag"))
				.add(Projections.sum("o.otherInoutMoney"))
				.add(Projections.sqlProjection(
						"sum(OTHER_INOUT_DUE_MONEY - OTHER_INOUT_DISCOUNT_MONEY - OTHER_INOUT_PAID_MONEY ) as unpay",
						new String[] { "unpay" }, new Type[] { StandardBasicTypes.BIG_DECIMAL })));
		return criteria.list();
	}
	
	@Override
	public List<OtherInout> findByBranch(String systemBookCode, Integer branchNum, Integer innerBranchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(OtherInout.class, "o")
				.add(Restrictions.eq("o.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("o.systemBookCode", systemBookCode))
				.add(Restrictions.eq("o.branchNum", branchNum));
		if (innerBranchNum != null) {
			criteria.add(Restrictions.eq("o.innerBranchNum", innerBranchNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("o.otherInoutAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("o.otherInoutAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setLockMode(LockMode.NONE);
		
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findMoneybyBranch(String systemBookCode, Integer branchNum, Integer innerBranchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(OtherInout.class, "o")
				.add(Restrictions.eq("o.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("o.systemBookCode", systemBookCode))
				.add(Restrictions.eq("o.branchNum", branchNum));
		if (innerBranchNum != null) {
			criteria.add(Restrictions.eq("o.innerBranchNum", innerBranchNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("o.otherInoutAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("o.otherInoutAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setLockMode(LockMode.NONE);
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.groupProperty("o.otherInoutFlag"))
				.add(Projections.sum("o.otherInoutMoney"))
				.add(Projections.sqlProjection(
						"sum(OTHER_INOUT_DUE_MONEY - OTHER_INOUT_DISCOUNT_MONEY - OTHER_INOUT_PAID_MONEY ) as unpay",
						new String[] { "unpay" }, new Type[] { StandardBasicTypes.BIG_DECIMAL })));
		return criteria.list();
	}
	
}
