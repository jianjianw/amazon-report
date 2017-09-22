package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.ShipOrderDao;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public class ShipOrderDaoImpl extends DaoImpl implements ShipOrderDao {
	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, Integer branchNum, List<Integer> branchNums,
											Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select ship_branch_num , sum(ship_order_carriage) as feeMoney, sum(ship_order_money) as totalMoney, ");
		sb.append("count(ship_order_fid) as shipCount ");
		sb.append("from ship_order with(nolock) where system_book_code = :systemBookCode and ship_branch_num is not null ");
		sb.append("and branch_num = :branchNum ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and ship_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and ship_order_audit_time between :dateFrom and :dateTo and ship_order_state_code = 3 ");
		sb.append("group by ship_branch_num ");
		String sql = sb.toString();
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}


	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode,
												Integer branchNum, List<Integer> branchNums, List<String> categoryCodes, List<Integer> exceptItemNums,
												Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select shipOrder.ship_branch_num, shipOrder.ship_order_fid, ");
		sb.append("sum(out_order_detail_sale_subtotal) as totalMoney ");
		sb.append("from ship_order as shipOrder with(nolock), out_order_detail as detail with(nolock), ship_transfer_out as trans with(nolock) ");
		sb.append("where shipOrder.system_book_code=:systemBookCode and shipOrder.branch_num = :branchNum ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and shipOrder.ship_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and shipOrder.ship_order_audit_time >= :dateFrom and shipOrder.ship_order_audit_time <= :dateTo and shipOrder.ship_order_state_code = 3 ");
		sb.append("and shipOrder.ship_order_fid = trans.ship_order_fid and trans.out_order_fid = detail.out_order_fid ");
		if(categoryCodes != null && categoryCodes.size() > 0){
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "+AppUtil.getStringParmeList(categoryCodes)+" and item.item_num = detail.item_num) ");
		}
		if(exceptItemNums != null && exceptItemNums.size() > 0){
			sb.append("and detail.item_num not in " + AppUtil.getIntegerParmeList(exceptItemNums));
		}
		sb.append("group by shipOrder.ship_branch_num, shipOrder.ship_order_fid ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Object[]> findBranchNewItemSummary(String systemBookCode,
												   Integer branchNum, List<Integer> branchNums,
												   List<Integer> newItemNums, List<String> categoryCodes,
												   Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select shipOrder.ship_branch_num, ");
		sb.append("count(distinct detail.item_num) as newItemCount ");
		sb.append("from ship_order as shipOrder with(nolock) , out_order_detail as detail with(nolock) , ship_transfer_out as trans with(nolock) ");
		sb.append("where shipOrder.system_book_code=:systemBookCode and shipOrder.branch_num = :branchNum ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and shipOrder.ship_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and shipOrder.ship_order_audit_time >= :dateFrom and shipOrder.ship_order_audit_time <= :dateTo and shipOrder.ship_order_state_code = 3 ");
		sb.append("and shipOrder.ship_order_fid = trans.ship_order_fid and trans.out_order_fid = detail.out_order_fid ");
		if(newItemNums != null && newItemNums.size() > 0){
			sb.append("and detail.item_num in "+AppUtil.getIntegerParmeList(newItemNums)+" ");
		}
		if(categoryCodes != null && categoryCodes.size() > 0){
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "+AppUtil.getStringParmeList(categoryCodes)+" and item.item_num = detail.item_num) ");
		}
		sb.append("group by shipOrder.ship_branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Object[]> findBranchNewItemDetail(String systemBookCode,
												  Integer outBranchNum, Integer branchNum, List<Integer> newItemNums,
												  Date dateFrom, Date dateTo, List<String> categoryCodes) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(out_order_detail_sale_subtotal) as totalMoney ");
		sb.append("from ship_order as shipOrder with(nolock), out_order_detail as detail with(nolock), ship_transfer_out as trans with(nolock) ");
		sb.append("where shipOrder.system_book_code=:systemBookCode and shipOrder.branch_num = :outBranchNum and shipOrder.ship_branch_num = :branchNum ");
		sb.append("and shipOrder.ship_order_audit_time >= :dateFrom and shipOrder.ship_order_audit_time <= :dateTo and shipOrder.ship_order_state_code = 3 ");
		sb.append("and shipOrder.ship_order_fid = trans.ship_order_fid and trans.out_order_fid = detail.out_order_fid ");
		if(newItemNums != null && newItemNums.size() > 0){
			sb.append("and detail.item_num in "+AppUtil.getIntegerParmeList(newItemNums)+" ");
		}
		if(categoryCodes != null && categoryCodes.size() > 0){
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "+AppUtil.getStringParmeList(categoryCodes)+" and item.item_num = detail.item_num) ");
		}
		sb.append("group by detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("outBranchNum", outBranchNum);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Object[]> findLineSummary(String systemBookCode, Integer branchNum, List<Integer> transferLineNums,
										  Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select transfer_line_num , sum(ship_order_carriage) as feeMoney  ");
		sb.append("from ship_order with(nolock) where system_book_code = :systemBookCode and ship_branch_num is not null ");
		sb.append("and branch_num = :branchNum ");
		if(transferLineNums != null && transferLineNums.size() > 0){
			sb.append("and transfer_line_num in " + AppUtil.getIntegerParmeList(transferLineNums));
		}
		sb.append("and ship_order_audit_time between :dateFrom and :dateTo and ship_order_state_code = 3 ");
		sb.append("and transfer_line_num is not null ");
		sb.append("group by transfer_line_num ");
		String sql = sb.toString();
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}


	@Override
	public List<Object[]> findLineCount(String systemBookCode, Integer branchNum, List<Integer> transferLineNums,
										Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.line, count(a.dateStr) as amount from ");
		sb.append("(select convert(varchar(20), ship_order_create_time, 120) as dateStr , transfer_line_num as line  ");
		sb.append("from ship_order with(nolock) where system_book_code = :systemBookCode and ship_branch_num is not null ");
		sb.append("and branch_num = :branchNum ");
		if(transferLineNums != null && transferLineNums.size() > 0){
			sb.append("and transfer_line_num in " + AppUtil.getIntegerParmeList(transferLineNums));
		}
		sb.append("and ship_order_audit_time between :dateFrom and :dateTo and ship_order_state_code = 3 ");
		sb.append("and transfer_line_num is not null and ship_order_carriage > 0 ");
		sb.append("group by convert(varchar(20), ship_order_create_time, 120) , transfer_line_num) as a ");
		sb.append("group by a.line ");
		String sql = sb.toString();
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}
}