package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.RetailPosLogDao;
import com.nhsoft.module.report.model.RetailPosLog;
import com.nhsoft.module.report.query.LogQuery;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/10/11.
 */
@Repository
public class RetailPosLogDaoImpl extends DaoImpl implements RetailPosLogDao {
	@Override
	public List<RetailPosLog> findByQuery(String systemBookCode, Integer branchNum, LogQuery queryCondition, Integer offset, Integer limit) {
		String queryYear = DateUtil.getYearString(queryCondition.getDateFrom());
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		if(AppUtil.checkYearTable(queryYear)){
			sb.append("from retail_pos_log_" + queryYear + " with(nolock) ");
			
		} else {
			sb.append("from retail_pos_log with(nolock)");
		}
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNum != null){
			sb.append("and branch_num = :branchNum ");
		}
		if(queryCondition.getDateFrom() != null){
			sb.append("and retail_pos_log_bizday >= :bizFrom ");
		}
		if(queryCondition.getDateTo() != null){
			sb.append("and retail_pos_log_bizday <= :bizTo ");
		}
		if(StringUtils.isNotEmpty(queryCondition.getOperateType())){
			sb.append("and retail_pos_log_type = :type ");
		}
		if(StringUtils.isNotEmpty(queryCondition.getOperator())){
			sb.append("and retail_pos_log_operator = :operator ");
		}
		if(StringUtils.isNotEmpty(queryCondition.getLogMemo())){
			sb.append("and retail_pos_log_memo like :memo ");
		}
		if(StringUtils.isNotEmpty(queryCondition.getLogItem())){
			sb.append("and retail_pos_log_item_name like :itemName ");
		}
		if(queryCondition.getSortField() != null){
			sb.append("order by " + AppUtil.getDBColumnName(queryCondition.getSortField()) + " " + queryCondition.getSortType());
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if(queryCondition.isPaging()){
			sqlQuery.setFirstResult(offset);
			sqlQuery.setMaxResults(limit);
		}
		
		if(branchNum != null){
			sqlQuery.setInteger("branchNum", branchNum);
		}
		if(queryCondition.getDateFrom() != null){
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(queryCondition.getDateFrom()));
		}
		if(queryCondition.getDateTo() != null){
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(queryCondition.getDateTo()));
		}
		if(StringUtils.isNotEmpty(queryCondition.getOperateType())){
			sqlQuery.setString("type", queryCondition.getOperateType());
		}
		if(StringUtils.isNotEmpty(queryCondition.getOperator())){
			sqlQuery.setString("operator", queryCondition.getOperator());
		}
		if(StringUtils.isNotEmpty(queryCondition.getLogMemo())){
			sqlQuery.setString("memo", "%" + queryCondition.getLogMemo() + "%");
		}
		if(StringUtils.isNotEmpty(queryCondition.getLogItem())){
			sqlQuery.setString("itemName", "%" + queryCondition.getLogItem() + "%");
			
		}
		sqlQuery.addEntity(RetailPosLog.class);
		return sqlQuery.list();
	}

	@Override
	public List<RetailPosLog> findByQuery(String systemBookCode, Integer branchNum, Integer stallNum, LogQuery queryCondition, Integer offset, Integer limit) {
		String queryYear = DateUtil.getYearString(queryCondition.getDateFrom());
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		if(AppUtil.checkYearTable(queryYear)){
			sb.append("from retail_pos_log_" + queryYear + " with(nolock) ");

		} else {
			sb.append("from retail_pos_log with(nolock)");
		}
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNum != null){
			sb.append("and branch_num = :branchNum ");
		}
		if(stallNum != null) {
			sb.append("and stall_num = " + stallNum + " ");
		}
		if(queryCondition.getDateFrom() != null){
			sb.append("and retail_pos_log_bizday >= :bizFrom ");
		}
		if(queryCondition.getDateTo() != null){
			sb.append("and retail_pos_log_bizday <= :bizTo ");
		}
		if(StringUtils.isNotEmpty(queryCondition.getOperateType())){
			sb.append("and retail_pos_log_type = :type ");
		}
		if(StringUtils.isNotEmpty(queryCondition.getOperator())){
			sb.append("and retail_pos_log_operator = :operator ");
		}
		if(StringUtils.isNotEmpty(queryCondition.getLogMemo())){
			sb.append("and retail_pos_log_memo like :memo ");
		}
		if(StringUtils.isNotEmpty(queryCondition.getLogItem())){
			sb.append("and retail_pos_log_item_name like :itemName ");
		}
		if(queryCondition.getSortField() != null){
			sb.append("order by " + AppUtil.getDBColumnName(queryCondition.getSortField()) + " " + queryCondition.getSortType());
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if(queryCondition.isPaging()){
			sqlQuery.setFirstResult(offset);
			sqlQuery.setMaxResults(limit);
		}

		if(branchNum != null){
			sqlQuery.setInteger("branchNum", branchNum);
		}
		if(queryCondition.getDateFrom() != null){
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(queryCondition.getDateFrom()));
		}
		if(queryCondition.getDateTo() != null){
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(queryCondition.getDateTo()));
		}
		if(StringUtils.isNotEmpty(queryCondition.getOperateType())){
			sqlQuery.setString("type", queryCondition.getOperateType());
		}
		if(StringUtils.isNotEmpty(queryCondition.getOperator())){
			sqlQuery.setString("operator", queryCondition.getOperator());
		}
		if(StringUtils.isNotEmpty(queryCondition.getLogMemo())){
			sqlQuery.setString("memo", "%" + queryCondition.getLogMemo() + "%");
		}
		if(StringUtils.isNotEmpty(queryCondition.getLogItem())){
			sqlQuery.setString("itemName", "%" + queryCondition.getLogItem() + "%");

		}
		sqlQuery.addEntity(RetailPosLog.class);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findTypeCountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String logTypes) {
		StringBuffer sb = new StringBuffer();
		sb.append("select retail_pos_log_type, ");
		//BMW-204
		sb.append("sum(case when (retail_pos_log_type = '退货' and retail_pos_log_money < 0) then - 1 when (retail_pos_log_type = '整单取消' and retail_pos_log_money < 0) then 0 else 1 end) as amount, ");
		sb.append("sum(case when (retail_pos_log_type != '整单取消' or (retail_pos_log_type = '整单取消' and retail_pos_log_money > 0)) then retail_pos_log_money end) as money ");
		sb.append("from retail_pos_log with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and retail_pos_log_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		sb.append("and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		if(StringUtils.isNotEmpty(logTypes)){
			sb.append("and retail_pos_log_type in " + AppUtil.getStringParmeArray(logTypes.split(",")));
		}
		sb.append("group by retail_pos_log_type ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findTypeCountAndMoney(String systemBookCode, Integer branchNum, List<Integer> stallNums, Date dateFrom, Date dateTo, String logTypes) {
		StringBuffer sb = new StringBuffer();
		sb.append("select retail_pos_log_type, merchant_num");
		//BMW-204
		sb.append("sum(case when (retail_pos_log_type = '退货' and retail_pos_log_money < 0) then - 1 when (retail_pos_log_type = '整单取消' and retail_pos_log_money < 0) then 0 else 1 end) as amount, ");
		sb.append("sum(case when (retail_pos_log_type != '整单取消' or (retail_pos_log_type = '整单取消' and retail_pos_log_money > 0)) then retail_pos_log_money end) as money ");
		sb.append("from retail_pos_log with(nolock) where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " and stall_num is not null ");
		if(stallNums != null && stallNums.size() > 0){
			sb.append("and stall_num in " + AppUtil.getIntegerParmeList(stallNums));
		}
		sb.append("and retail_pos_log_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		sb.append("and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		if(StringUtils.isNotEmpty(logTypes)){
			sb.append("and retail_pos_log_type in " + AppUtil.getStringParmeArray(logTypes.split(",")));
		}
		sb.append("group by retail_pos_log_type ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findTypeCountAndMoneyByOrder(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String logTypes) {
		StringBuffer sb = new StringBuffer();
		sb.append("select retail_pos_log_type, retail_pos_log_order_no");
		//BMW-204
		sb.append("sum(case when (retail_pos_log_type = '退货' and retail_pos_log_money < 0) then - 1 when (retail_pos_log_type = '整单取消' and retail_pos_log_money < 0) then 0 else 1 end) as amount, ");
		sb.append("sum(case when (retail_pos_log_type != '整单取消' or (retail_pos_log_type = '整单取消' and retail_pos_log_money > 0)) then retail_pos_log_money end) as money ");
		sb.append("from retail_pos_log with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and retail_pos_log_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		sb.append("and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		if(StringUtils.isNotEmpty(logTypes)){
			sb.append("and retail_pos_log_type in " + AppUtil.getStringParmeArray(logTypes.split(",")));
		}
		sb.append("and retail_pos_log_order_no is not null ");
		sb.append("group by retail_pos_log_type, retail_pos_log_order_no ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
}
