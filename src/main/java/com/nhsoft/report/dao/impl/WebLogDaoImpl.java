package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.WebLogDao;
import com.nhsoft.module.report.query.LogQuery;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class WebLogDaoImpl extends DaoImpl implements WebLogDao {
	@Override
	public Integer countByQuery(String systemBookCode, Integer branchNum,
								LogQuery logQuery) {
		StringBuffer sb = new StringBuffer();
		if(logQuery.getQueryType() == 0){
			sb.append("select count(web_log_id) from web_log with(nolock) ").append(createCondition(systemBookCode, branchNum, logQuery));
		} else if(logQuery.getQueryType() == 1){
			sb.append("select count(web_log_id) from web_log_history with(nolock) ").append(createCondition(systemBookCode, branchNum, logQuery));
		} else {
			Date dateTo = logQuery.getDateTo();
			logQuery.setDateTo(DateUtil.addSecond(logQuery.getLimitDate(), -1));
			sb.append("select count(web.web_log_id) from ((select * from web_log_history with(nolock) ").append(createCondition(systemBookCode, branchNum, logQuery));
			sb.append(") union (");

			logQuery.setDateFrom(logQuery.getLimitDate());
			logQuery.setDateTo(dateTo);
			sb.append("select * from web_log with(nolock) ").append(createCondition(systemBookCode, branchNum, logQuery));
			sb.append(")) as web ");
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		Object object = query.uniqueResult();
		if(object != null){
			return (Integer)object;
		}
		return 0;
	}

	private String createCondition(String systemBookCode, Integer branchNum, LogQuery logQuery){
		StringBuffer sb = new StringBuffer();
		sb.append("where web_log_date between '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(logQuery.getDateFrom())) + "' ");
		sb.append("and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(logQuery.getDateTo())) + "' ");
		sb.append("and system_book_code = '").append(systemBookCode).append("' and web_log_item != '微会员' ");
		if(logQuery.getAppUserNum() != null){
			sb.append("and app_user_num = ").append(logQuery.getAppUserNum()).append(" ");
		}
		if(logQuery.getSupplierUserNum() != null){
			if(logQuery.getSupplierUserNum() == 0){
				sb.append("and supplier_user_num > 0 ");
			} else {
				sb.append("and supplier_user_num = ").append(logQuery.getSupplierUserNum()).append(" ");
			}
		}
		if(StringUtils.isNotEmpty(logQuery.getWholesaleUserId())){
			if(logQuery.getWholesaleUserId().equals("0")){
				sb.append("and wholesale_user_id != '' ");
			} else {
				sb.append("and wholesale_user_id = '").append(logQuery.getWholesaleUserId()).append("' ");
			}
		}
		if(StringUtils.isNotEmpty(logQuery.getOperateType())){
			sb.append("and web_log_action = '").append(logQuery.getOperateType()).append("' ");
		}
		if(StringUtils.isNotEmpty(logQuery.getLogMemo())){
			sb.append("and web_log_memo like '%").append(logQuery.getLogMemo()).append("%' ");
		}
		if(StringUtils.isNotEmpty(logQuery.getLogItem())){
			sb.append("and web_log_item like '%").append(logQuery.getLogItem()).append("%' ");
		}
		if(branchNum != null){
			sb.append("and branch_num = ").append(branchNum).append(" ");
		}
		if(StringUtils.isNotEmpty(logQuery.getLogSystem())){
			sb.append("and web_log_system = '").append(logQuery.getLogSystem()).append("' ");
		}
		return sb.toString();

	}
}
