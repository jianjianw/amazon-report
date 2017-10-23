package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.RequestOrderDao;
import com.nhsoft.module.report.model.RequestOrder;
import com.nhsoft.module.report.model.RequestOrderDetail;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Repository
public class RequestOrderDaoImpl extends DaoImpl implements RequestOrderDao {




	@Override
	public List<Object[]> findCenterRequestMatrixAmount(String systemBookCode, Integer outBranchNum, List<Integer> itemNums) {

		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.request_order_detail_item_matrix_num, sum(detail.request_order_detail_qty) as qty, ");
		sb.append("sum(detail.request_order_detail_out_qty) as outQty, sum(detail.request_order_detail_purchase_qty) as pQty ");
		sb.append("from request_order_detail as detail with(nolock) inner join request_order as r with(nolock) ");
		sb.append("on detail.request_order_fid = r.request_order_fid ");
		sb.append("where r.out_system_book_code = '" + systemBookCode + "' and r.out_branch_num = " + outBranchNum + " ");
		sb.append("and r.request_order_deadline >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(Calendar.getInstance().getTime())) + "' ");
		sb.append("and r.request_order_state_code = 3 ");
		sb.append("and detail.REQUEST_ORDER_DETAIL_IGNORE = 0 ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num, detail.request_order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}


	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(RequestOrder.class, "r")
				.add(Restrictions.eq("r.outSystemBookCode", systemBookCode));
		if (branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			criteria.add(Restrictions.eq("r.branchNum", branchNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.requestOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.requestOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}


	@Override
	public List<Object[]> findBranchNewItemSummary(String systemBookCode, Integer branchNum, List<Integer> branchNums, List<Integer> newItemNums, List<String> categoryCodes, Date dateFrom,
												   Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.branch_num, count(distinct d.item_num) as newItemCount ");
		sb.append("from request_order_detail as d with(nolock) inner join request_order as r with(nolock) on r.request_order_fid = d.request_order_fid ");
		sb.append("where r.out_system_book_code = :systemBookCode and r.out_branch_num = :branchNum ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and r.request_order_audit_time >= :dateFrom and r.request_order_audit_time <= :dateTo and r.request_order_state_code = 3 ");
		if (newItemNums != null && newItemNums.size() > 0) {
			sb.append("and d.item_num in " + AppUtil.getIntegerParmeList(newItemNums));
		}
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in " + AppUtil.getStringParmeList(categoryCodes)
					+ " and item.item_num = d.item_num) ");
		}
		sb.append("group by r.branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		List<Object[]> objects = query.list();
		return objects;
	}



	@Override
	public List<RequestOrderDetail> findDetails(String requestOrderFid) {
		Criteria criteria = currentSession().createCriteria(RequestOrderDetail.class, "d").add(Restrictions.eq("d.id.requestOrderFid", requestOrderFid));
		List<RequestOrderDetail> requestOrderDetails = criteria.list();
		return requestOrderDetails;
	}



	@Override
	public List<Object[]> findItemSummary(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(detail.request_order_detail_qty) as amount,  sum(detail.request_order_detail_use_qty) as useAmount, ");
		sb.append("sum(detail.request_order_detail_out_qty) as outAmount, ");
		sb.append(
				"sum(case when request_order_detail_out_use_qty is not null then request_order_detail_out_use_qty when request_order_detail_out_qty is null then 0.00 else (detail.request_order_detail_out_qty/detail.request_order_detail_use_rate) end) as outUseAmount ");
		sb.append("from request_order_detail as detail with(nolock) inner join request_order as r with(nolock) on r.request_order_fid = detail.request_order_fid ");
		sb.append("where r.out_system_book_code = '" + systemBookCode + "' and r.out_branch_num = " + centerBranchNum + " ");
		sb.append("and r.branch_num = " + branchNum);
		sb.append("and r.request_order_audit_time between '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		sb.append("and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		sb.append("and r.request_order_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
}