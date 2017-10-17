package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.AdjustmentOrderDao;
import com.nhsoft.report.model.AdjustmentOrder;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public class AdjustmentOrderDaoImpl extends DaoImpl implements AdjustmentOrderDao{


	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> reasons) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bs.branch_num, d.item_num, d.adjustment_order_detail_item_matrix_num, sum(d.adjustment_order_detail_subtotal) as money ");
		sb.append("from adjustment_order as a with(nolock) inner join branch_storehouse as bs with(nolock)  on a.system_book_code = bs.system_book_code inner join adjustment_order_detail as d with(nolock) on a.adjustment_order_fid = d.adjustment_order_fid ");
		sb.append("and a.storehouse_num = bs.storehouse_num ");
		sb.append("where a.system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and bs.branch_num in (:branchNums) ");
		}
		if(reasons != null && reasons.size() > 0){
			sb.append("and a.adjustment_order_cause in (:reasons) ");
		}
		if(dateFrom != null){
			sb.append("and a.adjustment_order_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and a.adjustment_order_audit_time <= :dateTo ");
		}
		if(itemNums != null && itemNums.size() > 0) {
			sb.append("and d.item_num in (:itemNums) ");
		}
		sb.append("and a.adjustment_order_state_code = 3 group by bs.branch_num, d.item_num, d.adjustment_order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(branchNums != null && branchNums.size() > 0){
			query.setParameterList("branchNums", branchNums);
		}
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		if(itemNums != null && itemNums.size() > 0) {
			query.setParameterList("itemNums", itemNums);
		}
		if(reasons != null && reasons.size() > 0){
			query.setParameterList("reasons", reasons);
		}
		return query.list();
	}




	@Override
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
										  List<String> reasons, Boolean inOut, Boolean isAudit, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.adjustment_order_detail_item_matrix_num, sum(detail.adjustment_order_detail_subtotal) as money, ");
		sb.append("sum(detail.adjustment_order_detail_qty) as amount ");
		sb.append("from adjustment_order_detail as detail with(nolock) inner join adjustment_order as a with(nolock) on detail.adjustment_order_fid = a.adjustment_order_fid ");
		sb.append("inner join branch_storehouse as bs with(nolock) on a.system_book_code = bs.system_book_code and a.storehouse_num = bs.storehouse_num ");
		sb.append("where a.system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and bs.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(reasons != null && reasons.size() > 0){
			sb.append("and a.adjustment_order_cause in " + AppUtil.getStringParmeList(reasons));
		}
		if(isAudit != null && isAudit){
			if(dateFrom != null){
				sb.append("and a.adjustment_order_audit_time >= :dateFrom ");
			}
			if(dateTo != null){
				sb.append("and a.adjustment_order_audit_time <= :dateTo ");
			}
		} else {
			if(dateFrom != null){
				sb.append("and a.adjustment_order_create_time >= :dateFrom ");
			}
			if(dateTo != null){
				sb.append("and a.adjustment_order_create_time <= :dateTo ");
			}
		}

		if(inOut != null){
			if(inOut){
				sb.append("and a.adjustment_order_direction = '" + AppConstants.ADJUSTMENT_DIRECTION_IN + "' ");

			} else {
				sb.append("and a.adjustment_order_direction = '" + AppConstants.ADJUSTMENT_DIRECTION_OUT + "' ");
			}
		}
		if(isAudit != null){
			if(isAudit){
				sb.append("and a.adjustment_order_state_code = 3 ");
			} else {
				sb.append("and a.adjustment_order_state_code = 1 ");
			}
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num,detail.adjustment_order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		return query.list();
	}


	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> reasons) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bs.branch_num, sum(a.adjustment_order_money) ");
		sb.append("from adjustment_order as a with(nolock) inner join branch_storehouse as bs with(nolock)  on a.system_book_code = bs.system_book_code ");
		sb.append("and a.storehouse_num = bs.storehouse_num ");
		sb.append("where a.system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and bs.branch_num in (:branchNums) ");
		}
		if(reasons != null && reasons.size() > 0){
			sb.append("and a.adjustment_order_cause in (:reasons) ");
		}
		if(dateFrom != null){
			sb.append("and a.adjustment_order_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and a.adjustment_order_audit_time <= :dateTo ");
		}
		sb.append("and a.adjustment_order_state_code = 3 group by bs.branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(branchNums != null && branchNums.size() > 0){
			query.setParameterList("branchNums", branchNums);
		}
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		if(reasons != null && reasons.size() > 0){
			query.setParameterList("reasons", reasons);
		}
		return query.list();
	}

	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(AdjustmentOrder.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.sqlRestriction("storehouse_num in (select storehouse_num from branch_storehouse where system_book_code = ? and branch_num = ?)"
					, new Object[]{systemBookCode, branchNum}, new Type[]{StandardBasicTypes.STRING, StandardBasicTypes.INTEGER}));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("a.adjustmentOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("a.adjustmentOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}


	@Override
	public List<Object[]> findLossMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.branch_num,sum(a.adjustment_order_money) ");
		sb.append("from adjustment_order a with(nolock) INNER JOIN branch_storehouse b with(nolock) on a.storehouse_num = b.storehouse_num ");
		sb.append("where a.system_book_code = :systemBookCode ");
		sb.append("and adjustment_order_direction = '出库' ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and b.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and adjustment_order_audit_time >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and adjustment_order_audit_time <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and adjustment_order_state_code = '3' ");
		sb.append("group by b.branch_num order by b.branch_num asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findCheckMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select house.branch_num,sum(ckdetail.check_order_detail_cost) as money ");
		sb.append("from check_order_detail ckdetail WITH (nolock) ");
		sb.append("INNER JOIN check_order ckorder WITH (nolock) ON ckdetail.check_order_fid = ckorder.check_order_fid ");
		sb.append("INNER JOIN branch_storehouse house WITH (nolock) ON ckorder.storehouse_num = house.storehouse_num ");
		sb.append("where ckorder.system_book_code = :systemBookCode ");
		sb.append("and ckdetail.check_order_detail_stock_amount > ckdetail.check_order_detail_qty ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and house.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and ckorder.check_order_audit_time >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and ckorder.check_order_audit_time <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and check_order_state_code = '3' ");
		sb.append("GROUP BY house.branch_num ");
		sb.append("ORDER BY house.branch_num asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}





	@Override
	public List<Object[]> findAdjustmentCauseMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();

		sb.append("select b.branch_num, ");
		sb.append("sum(case when a.adjustment_order_cause = '试吃' then a.adjustment_order_money END) tryEat, ");
		sb.append("sum(case when a.adjustment_order_cause = '去皮' then a.adjustment_order_money END) faly, ");
		sb.append("sum(case when a.adjustment_order_cause = '报损' then a.adjustment_order_money END) loss, ");
		sb.append("sum(case when a.adjustment_order_cause = '其他' then a.adjustment_order_money END) other ");
		sb.append("from adjustment_order a inner join branch_storehouse b on a.storehouse_num = b.storehouse_num ");
		sb.append("where a.system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and b.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and a.adjustment_order_audit_time >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and a.adjustment_order_audit_time <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and adjusyment_order_state_code = '3' ");
		sb.append("group by b.branch_num order by b.branch_num asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}

}