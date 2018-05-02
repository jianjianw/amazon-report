package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.CardUserLogDao;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.model.CardUserLog;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class CardUserLogDaoImpl extends  DaoImpl implements CardUserLogDao {
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


	@Override
	public List<CardUserLog> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {

		StringBuilder sb = new StringBuilder();
		sb.append("select log.card_user_log_fid as cardUserLogFid, log.card_user_num as cardUserNum, log.system_book_code as systemBookCode, ");
		sb.append("branch_num as branchNum, log.card_user_log_type as cardUserLogType, log.card_user_log_operator as cardUserLogOperator, ");
		sb.append("log.card_user_log_time as cardUserLogTime, log.card_user_log_branch_name as cardUserLogBranchName, ");
		sb.append("log.card_user_log_memo as cardUserLogMemo, c.card_user_cust_name as cardUserCustName, ");
		sb.append("c.card_user_printed_num as cardUserPrintedNum, c.card_user_card_type as cardUserCardType ");
		sb.append("from card_user_log as log with(nolock) inner join card_user as c with(nolock) on log.card_user_num = c.card_user_num ");
		sb.append("where log.system_book_code = :systemBookCode ");
		if(cardReportQuery.getOperateBranch() != null){
			sb.append("and log.branch_num = :operateBranchNum ");
		}
		if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0){
			sb.append("and log.branch_num in " + AppUtil.getIntegerParmeList(cardReportQuery.getOperateBranchNums()));
		}
		if(cardReportQuery.getBranchNum() != null){
			sb.append("and c.card_user_enroll_shop = :branchNum ");
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
			sb.append("and c.card_user_printed_num = :cardUserPrintedNum ");
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getPaymentType())){
			sb.append("and log.card_user_log_type = :type ");

			if(cardReportQuery.getPaymentType().equals(AppConstants.CARD_USER_LOG_TYPE_REVOKE)){
				sb.append("and c.card_user_state_code != " + AppConstants.CARD_INIT_REVOKE_CODE + " ");

			}

		}
		if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
			sb.append("and c.card_user_enroll_shop in " + AppUtil.getIntegerParmeList(cardReportQuery.getBranchNums()));

		}
		if(cardReportQuery.getDateFrom() != null){
			sb.append("and log.card_user_log_time >= :dateFrom ");
		}
		if(cardReportQuery.getDateTo() != null){
			sb.append("and log.card_user_log_time <= :dateTo ");
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			sb.append("and log.card_user_log_operator = :operator ");
		}
		if(cardReportQuery.getCardUserCardType() != null){
			sb.append("and c.card_user_card_type = " + cardReportQuery.getCardUserCardType() + " ");
		}
		if(cardReportQuery.getSortField() != null){
			sb.append("order by " + AppUtil.getDBColumnName(cardReportQuery.getSortField()) + " " + cardReportQuery.getSortType());
		} else {
			sb.append("order by card_user_log_time asc ");
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", cardReportQuery.getSystemBookCode());
		if(cardReportQuery.getOperateBranch() != null){
			query.setInteger("operateBranchNum", cardReportQuery.getOperateBranch());
		}

		if(cardReportQuery.getBranchNum() != null){
			query.setInteger("branchNum", cardReportQuery.getBranchNum());
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
			query.setString("cardUserPrintedNum", cardReportQuery.getCardPrintNum());

		}
		if(cardReportQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(cardReportQuery.getDateFrom()));
		}
		if(cardReportQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(cardReportQuery.getDateTo()));
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			query.setString("operator", cardReportQuery.getOperator());
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getPaymentType())){
			query.setString("type", cardReportQuery.getPaymentType());
		}
		if(cardReportQuery.isPaging()){
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
		query.setResultTransformer(Transformers.aliasToBean(CardUserLog.class));
		List<CardUserLog> cardUserLogs = query.list();
		return cardUserLogs;
	}

	@Override
	public int countByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {
		if(org.apache.commons.lang.StringUtils.equals(cardReportQuery.getPaymentType(), AppConstants.CARD_USER_LOG_TYPE_REVOKE)){
			StringBuilder sb = new StringBuilder();
			sb.append("select count(*) ");
			sb.append("from card_user_log as log with(nolock) inner join card_user as c with(nolock) on log.card_user_num = c.card_user_num ");
			sb.append("where log.system_book_code = :systemBookCode ");
			if(cardReportQuery.getOperateBranch() != null){
				sb.append("and log.branch_num = :operateBranchNum ");
			}
			if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0){
				sb.append("and log.branch_num in " + AppUtil.getIntegerParmeList(cardReportQuery.getOperateBranchNums()));
			}
			if(cardReportQuery.getBranchNum() != null){
				sb.append("and c.card_user_enroll_shop = :branchNum ");
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
				sb.append("and c.card_user_printed_num = :cardUserPrintedNum ");
			}
			sb.append("and log.card_user_log_type = :type ");

			sb.append("and  c.card_user_state_code != " + AppConstants.CARD_INIT_REVOKE_CODE + " ");

			if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
				sb.append("and c.card_user_enroll_shop in " + AppUtil.getIntegerParmeList(cardReportQuery.getBranchNums()));

			}
			if(cardReportQuery.getDateFrom() != null){
				sb.append("and log.card_user_log_time >= :dateFrom ");
			}
			if(cardReportQuery.getDateTo() != null){
				sb.append("and log.card_user_log_time <= :dateTo ");
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getOperator())){
				sb.append("and log.card_user_log_operator = :operator ");
			}
			if(cardReportQuery.getCardUserCardType() != null){
				sb.append("and c.card_user_card_type = " + cardReportQuery.getCardUserCardType() + " ");
			}
			SQLQuery query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", cardReportQuery.getSystemBookCode());
			if(cardReportQuery.getOperateBranch() != null){
				query.setInteger("operateBranchNum", cardReportQuery.getOperateBranch());
			}

			if(cardReportQuery.getBranchNum() != null){
				query.setInteger("branchNum", cardReportQuery.getBranchNum());
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
				query.setString("cardUserPrintedNum", cardReportQuery.getCardPrintNum());

			}
			if(cardReportQuery.getDateFrom() != null){
				query.setParameter("dateFrom", DateUtil.getMinOfDate(cardReportQuery.getDateFrom()));
			}
			if(cardReportQuery.getDateTo() != null){
				query.setParameter("dateTo", DateUtil.getMaxOfDate(cardReportQuery.getDateTo()));
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getOperator())){
				query.setString("operator", cardReportQuery.getOperator());
			}

			query.setString("type", cardReportQuery.getPaymentType());
			Object object = query.uniqueResult();
			return object == null?0:(Integer)object;

		} else {
			Criteria criteria = createByCardReportQuery(cardReportQuery);
			criteria.setProjection(Projections.rowCount());
			return ((Long)criteria.uniqueResult()).intValue();

		}
	}

	private Criteria createByCardReportQuery(CardReportQuery cardReportQuery){

		Criteria criteria = currentSession().createCriteria(CardUserLog.class, "c")
				.add(Restrictions.eq("c.systemBookCode", cardReportQuery.getSystemBookCode()));
		if(cardReportQuery.getOperateBranch() != null){
			criteria.add(Restrictions.eq("c.branchNum", cardReportQuery.getOperateBranch()));
		}
		if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0){
			criteria.add(Restrictions.in("c.branchNum", cardReportQuery.getOperateBranchNums()));
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getPaymentType())){
			criteria.add(Restrictions.eq("c.cardUserLogType", cardReportQuery.getPaymentType()));
		}
		if(cardReportQuery.getBranchNum() != null
				|| org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())
				|| (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0)
				|| cardReportQuery.getCardUserCardType() != null){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(CardUser.class, "u")
					.add(Restrictions.eqProperty("u.cardUserNum", "c.cardUserNum"))
					.add(Restrictions.eq("u.systemBookCode", cardReportQuery.getSystemBookCode()));
			if(cardReportQuery.getBranchNum() != null){
				subCriteria.add(Restrictions.eq("u.cardUserEnrollShop", cardReportQuery.getBranchNum()));
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
				subCriteria.add(Restrictions.eq("u.cardUserPrintedNum", cardReportQuery.getCardPrintNum()));
			}
			if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
				subCriteria.add(Restrictions.in("u.cardUserEnrollShop", cardReportQuery.getBranchNums()));
			}
			if(cardReportQuery.getCardUserCardType() != null){
				criteria.add(Restrictions.eq("u.cardUserCardType", cardReportQuery.getCardUserCardType()));
			}
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("u.cardUserNum"))));
		}
		if(cardReportQuery.getDateFrom() != null){
			criteria.add(Restrictions.ge("c.cardUserLogTime", DateUtil.getMinOfDate(cardReportQuery.getDateFrom())));
		}
		if(cardReportQuery.getDateTo() != null){
			criteria.add(Restrictions.le("c.cardUserLogTime", DateUtil.getMaxOfDate(cardReportQuery.getDateTo())));
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			criteria.add(Restrictions.eq("c.cardUserLogOperator", cardReportQuery.getOperator()));
		}

		return criteria;
	}

}
