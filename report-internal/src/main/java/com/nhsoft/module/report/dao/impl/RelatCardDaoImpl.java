package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.RelatCardDao;
import com.nhsoft.module.report.model.RelatCard;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/10/11.
 */
@Repository
public class RelatCardDaoImpl extends DaoImpl implements RelatCardDao {
	@Override
	public BigDecimal getCashMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		String sql = "select sum(relat_card_money) as cash " +
				" from relat_card with(nolock) where system_book_code = '" + systemBookCode + "'  " +
				" and branch_num in " + AppUtil.getIntegerParmeList(branchNums) +
				" and relat_card_payment_type_name = '现金' " +
				" and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ";
		Query query = currentSession().createSQLQuery(sql);
		Object object = query.uniqueResult();
		return object == null?BigDecimal.ZERO:(BigDecimal)object;
	}
	
	private Criteria createByCash(String systemBookCode,
	                              List<Integer> branchNums, Date dateFrom, Date dateTo){
		Criteria criteria = currentSession().createCriteria(RelatCard.class, "r")
				.add(Restrictions.eq("r.relatCardPaymentTypeName", AppConstants.PAYMENT_CASH))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if(dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
			
		}
		if(dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		return criteria;
	}
	
	@Override
	public List<Object[]> findCashGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		Criteria criteria = createByCash(systemBookCode, branchNums, dateFrom, dateTo);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.branchNum"))
				.add(Projections.sum("r.relatCardMoney"))
		);
		return criteria.list();
	}
}
