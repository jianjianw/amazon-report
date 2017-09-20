package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.OtherInoutDao;
import com.nhsoft.report.model.OtherInout;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;


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
}
