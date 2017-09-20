package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.CardUserLogDao;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class CardUserLogDaoImpl extends  DaoImpl implements CardUserLogDao{
	@Override
	public List<Object[]> findBranchRevokeCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, count(card_user_log_fid) from card_user_log with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and card_user_log_type = '" + AppConstants.CARD_USER_LOG_TYPE_REVOKE + "' ");
		sb.append("and (card_user_log_memo is null or card_user_log_memo != '" + AppConstants.CARD_USER_LOG_REVOKE_INIT_CARD + "') ");
		sb.append("group by branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		return query.list();
	}
	
	@Override
	public List<Object[]> findBranchCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String cardUserLogType, Integer cardUserCardType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, count(card_user_log_fid) from card_user_log with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and card_user_log_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if(dateTo != null){
			sb.append("and card_user_log_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		if(StringUtils.isNotEmpty(cardUserLogType)){
			sb.append("and card_user_log_type = '" + cardUserLogType + "' ");
			
		}
		if(cardUserCardType != null){
			sb.append("and exists (select 1 from card_user with(nolock) where card_user.card_user_num = card_user_log.card_user_num and card_user_card_type = " + cardUserCardType + " ) ");
		}
		sb.append("group by branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		return query.list();
	}
	
	@Override
	public List<Object[]> findBizdayCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String cardUserLogType, Integer cardUserCardType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select CONVERT(varchar(12) , card_user_log_time, 112 ) as biz, count(card_user_log_fid) as amount from card_user_log with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and card_user_log_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if(dateTo != null){
			sb.append("and card_user_log_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		if(StringUtils.isNotEmpty(cardUserLogType)){
			sb.append("and card_user_log_type = '" + cardUserLogType + "' ");
			
		}
		if(cardUserCardType != null){
			sb.append("and exists (select 1 from card_user with(nolock) where card_user.card_user_num = card_user_log.card_user_num and card_user_card_type = " + cardUserCardType + " ) ");
		}
		sb.append("group by CONVERT(varchar(12) , card_user_log_time, 112 ) ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		return query.list();
	}
}
