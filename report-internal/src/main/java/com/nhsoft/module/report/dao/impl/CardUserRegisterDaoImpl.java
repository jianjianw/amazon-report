package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.CardUserRegisterDao;
import com.nhsoft.module.report.model.CardUserLog;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class CardUserRegisterDaoImpl extends  DaoImpl implements CardUserRegisterDao {

	@Override
	public List<Object[]> findSalerSummary(String systemBookCode,
										   List<Integer> branchNums, Date dateFrom, Date dateTo,
										   List<String> salerNames) {
		StringBuffer sb = new StringBuffer();
		sb.append("select card_user_register_seller, branch_num, count(card_user_register_fid) from card_user_register with(nolock) ");
		sb.append("where system_book_code=:systemBookCode ");
		if(branchNums != null && branchNums.size() != 0){
			sb.append("and branch_num in :branchNums ");
		}
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= :dateTo ");
		}
		if(salerNames != null && salerNames.size() != 0){
			sb.append("and card_user_register_seller in :salerNames ");
		}
		sb.append("and card_user_register_type = '" + AppConstants.REGISTER_TYPE_DELIVER + "' ");
		sb.append("group by card_user_register_seller, branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(branchNums != null && branchNums.size() != 0){
			query.setParameterList("branchNums", branchNums);
		}
		if(dateFrom != null){
			query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		}
		if(salerNames != null && salerNames.size() != 0){
			query.setParameterList("salerNames", salerNames);
		}
		return query.list();
	}
	@Override
	public List<Object[]> findBranchDeliverCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<String> types = new ArrayList<String>();
		types.add(AppConstants.REGISTER_TYPE_DELIVER);
		types.add(AppConstants.REGISTER_TYPE_ORI);
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, count(card_user_register_fid) from card_user_register with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and card_user_register_type in " + AppUtil.getStringParmeList(types));
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
	public List<CardUserLog> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("select register.card_user_register_fid as cardUserLogFid, register.card_user_register_cust_num as cardUserNum, register.system_book_code as systemBookCode, ");
		sb.append("branch_num as branchNum, register.card_user_register_type as cardUserLogType, register.card_user_register_operator as cardUserLogOperator, ");
		sb.append("register.card_user_register_date as cardUserLogTime, ");
		sb.append("register.card_user_register_memo as cardUserLogMemo, c.card_user_cust_name as cardUserCustName, ");
		sb.append("c.card_user_printed_num as cardUserPrintedNum, c.card_user_card_type as cardUserCardType ");
		sb.append("from card_user_register as register with(nolock) inner join card_user as c with(nolock) on register.card_user_register_cust_num = c.card_user_num ");
		sb.append("where register.system_book_code = :systemBookCode ");
		if(cardReportQuery.getOperateBranch() != null){
			sb.append("and register.branch_num = :operateBranchNum ");
		}
		if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0) {
			sb.append("and register.branch_num in " + AppUtil.getIntegerParmeList(cardReportQuery.getOperateBranchNums()));
		}
		if(cardReportQuery.getBranchNum() != null){
			sb.append("and c.card_user_enroll_shop = :branchNum ");
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
			sb.append("and c.card_user_printed_num = :cardUserPrintedNum ");
		}
		if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
			sb.append("and c.card_user_enroll_shop in " + AppUtil.getIntegerParmeList(cardReportQuery.getBranchNums()));

		}
		if(cardReportQuery.getDateFrom() != null){
			sb.append("and register.card_user_register_date >= :dateFrom ");
		}
		if(cardReportQuery.getDateTo() != null){
			sb.append("and register.card_user_register_date <= :dateTo ");
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			sb.append("and register.card_user_register_operator = :operator ");
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getPaymentType())){
			sb.append("and register.card_user_register_type = :type ");

		}
		if(cardReportQuery.getSortField() != null){
			sb.append("order by " + AppUtil.getDBColumnName(cardReportQuery.getSortField()) + " " + cardReportQuery.getSortType());
		} else {
			sb.append("order by card_user_register_date asc ");
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", cardReportQuery.getSystemBookCode());
		if(cardReportQuery.getOperateBranch() != null){
			query.setInteger("operateBranchNum", cardReportQuery.getOperateBranch());
		}

		if(cardReportQuery.getBranchNum() != null){
			query.setInteger("branchNum", cardReportQuery.getBranchNum());
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
			query.setString("cardUserPrintedNum", cardReportQuery.getCardPrintNum());

		}
		if(cardReportQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(cardReportQuery.getDateFrom()));
		}
		if(cardReportQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(cardReportQuery.getDateTo()));
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			query.setString("operator", cardReportQuery.getOperator());
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getPaymentType())){
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
}
