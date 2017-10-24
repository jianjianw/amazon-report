package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.AlipayLogDao;
import com.nhsoft.module.report.dto.AlipayDetailDTO;
import com.nhsoft.module.report.model.AlipayLog;
import com.nhsoft.module.report.query.LogQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class AlipayLogDaoImpl extends ShardingDaoImpl implements AlipayLogDao {
	
	
	private Criteria createByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery){
		Criteria criteria = currentSession().createCriteria(AlipayLog.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
		if(StringUtils.isNotEmpty(logQuery.getLogItem())){
			criteria.add(Restrictions.like("a.alipayLogOrderNo", logQuery.getLogItem(), MatchMode.ANYWHERE));
			return criteria;
		}
		if(branchNum != null){
			criteria.add(Restrictions.eq("a.branchNum", branchNum));
		}
		if(StringUtils.isNotEmpty(logQuery.getOperator())){
			criteria.add(Restrictions.eq("a.alipayLogOperator", logQuery.getOperator()));
		}
		if(StringUtils.isNotEmpty(logQuery.getOperateType())){
			criteria.add(Restrictions.eq("a.alipayLogType", logQuery.getOperateType()));
		}
		criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(logQuery.getDateFrom()), DateUtil.getMaxOfDate(logQuery.getDateTo())));
		criteria.setLockMode(LockMode.NONE);
		return criteria;
	}
	
	@Override
	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery) {
		Criteria criteria = createByLogQuery(systemBookCode, branchNum, logQuery);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}
	
	@Override
	public List<AlipayLog> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset,
	                                      int limit) {
		Criteria criteria = createByLogQuery(systemBookCode, branchNum, logQuery);
		if(logQuery.isPaging()){
			criteria.setFirstResult(offset);
			criteria.setMaxResults(limit);
		}
		if(logQuery.getSortField() != null){
			if(logQuery.getSortType().equals("ASC")){
				criteria.addOrder(Order.asc(logQuery.getSortField()));
			} else {
				criteria.addOrder(Order.desc(logQuery.getSortField()));
			}
		} else {
			criteria.addOrder(Order.asc("a.alipayLogStart"));
		}
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findBranchSummaryPayFail(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													Date dateTo, boolean isDeposit, String alipayLogTypes){

		StringBuffer sb = new StringBuffer();
		sb.append("select sum(alipay_log_money),count(alipay_log_id) from alipay_log where system_book_code = '"+systemBookCode+"'"+" and alipay_log_trade_state ="+false+" ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in (");
			for (int i = 0; i <branchNums.size() ; i++) {
				if(i==branchNums.size()-1)
				{
					sb.append(branchNums.get(i)+") ");
				}
				else {
					sb.append(branchNums.get(i));
					sb.append(",");
				}
			}
		}
		
		sb.append("and alipay_log_start between '"+DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");

		if(isDeposit){
			sb.append("and alipay_log_order_no like 'DEP%' ");
		}else {
			sb.append("and alipay_log_order_no not like 'DEP%' ");
		}
		if(StringUtils.isNotEmpty(alipayLogTypes)){
			sb.append("and alipay_log_type in ("+alipayLogTypes+") ");
		}
		sb.append("group by branch_num");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findBranchSummaryRePay(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												  Date dateTo, String alipayLogTypes) {

		StringBuffer sb = new StringBuffer();
		sb.append("select sum(alipay_log_money),count(alipay_log_id) from alipay_log system_book_code = '"+systemBookCode+"' and alipay_log_buyer_id is not null ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in (");
			for (int i = 0; i <branchNums.size() ; i++) {
				if(i==branchNums.size()-1)
				{
					sb.append(branchNums.get(i)+") ");
				}
				else {
					sb.append(branchNums.get(i));
					sb.append(",");
				}
			}
		}
		sb.append("and alipay_log_start between '"+DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");

		if(StringUtils.isNotEmpty(alipayLogTypes)){
			sb.append("and alipay_log_type in ("+alipayLogTypes+") ");
		}
		sb.append("group by branch_num");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<AlipayDetailDTO> findAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													   Date dateTo, String orderNoPres, String alipayLogTypes){
		StringBuffer sb = new StringBuffer();
		sb.append("select alipay_log_trade_no,alipay_log_order_no,branch_num," +
				"alipay_log_start,alipay_log_buyer_id,alipay_log_money,alipay_log_receipt_money,alipay_log_buyer_money from alipay_log where ");
		sb.append("system_book_code = '"+systemBookCode+"' ");
		sb.append("and alipay_log_start between '"+DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		sb.append("and alipay_log_trade_state = 1 and alipay_log_trade_valid = 1 ");
		sb.append("and alipay_log_type in ("+alipayLogTypes+") ");
		if (branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in (");
			for(int i = 0; i<branchNums.size();i++){
				if(i==branchNums.size()-1){
					sb.append(branchNums.get(i)+") ");
				}else{
					sb.append(branchNums.get(i)+",");
				}
			}
		}
		if(StringUtils.isNotEmpty(orderNoPres)){
			String[] array = orderNoPres.split(",");
			for(int i=0;i<array.length;i++){
				sb.append("and alipay_log_order_no like '"+array[i]+"%' ");
			}
			if(orderNoPres.contains("member")){
				sb.append("and alipay_log_order_no = '"+""+"'");
			}
		}

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		List<AlipayDetailDTO> list = new ArrayList<AlipayDetailDTO>();
		BigDecimal buyerMoney = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			AlipayDetailDTO alipayDetailDTO = new AlipayDetailDTO();
			alipayDetailDTO.setOrderFid((String) object[0]);
			alipayDetailDTO.setOrderNo((String) object[1]);
			alipayDetailDTO.setBranchNum((Integer) object[2]);
			alipayDetailDTO.setOrderTime((Date) object[3]);
			alipayDetailDTO.setCustomerId((String) object[4]);
			alipayDetailDTO.setConsumeSuccessMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			alipayDetailDTO.setConsumeSuccessActualMoney(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);

			//微会员存款没有OrderNo
			if(alipayDetailDTO.getOrderNo().startsWith("DEP")){

				alipayDetailDTO.setType("POS存款");

			} else if(StringUtils.isEmpty(alipayDetailDTO.getOrderNo())){

				alipayDetailDTO.setType("微会员存款");

			} else if(alipayDetailDTO.getOrderNo().startsWith(AppConstants.S_Prefix_WD)) {
				alipayDetailDTO.setType("微店消费");

			} else {
				alipayDetailDTO.setType("POS消费");

			}
			buyerMoney = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			alipayDetailDTO.setAlipayDiscountMoney(alipayDetailDTO.getConsumeSuccessActualMoney().subtract(buyerMoney));
			alipayDetailDTO.setBranchDiscountMoney(alipayDetailDTO.getConsumeSuccessMoney().subtract(alipayDetailDTO.getConsumeSuccessActualMoney()));
			list.add(alipayDetailDTO);
		}
		return list;
	}

	@Override
	public List<AlipayDetailDTO> findCancelAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums,
															 Date dateFrom, Date dateTo, String orderNoPres, String alipayLogTypes){
		StringBuffer sb = new StringBuffer();
		sb.append("select alipay_log_trade_no,alipay_log_order_no,branch_num," +
				"alipay_log_start,alipay_log_buyer_id,alipay_log_money,alipay_log_receipt_money,alipay_log_buyer_money from alipay_log where ");
		sb.append("system_book_code = '"+systemBookCode+"' ");
		sb.append("and alipay_log_start between '"+DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		sb.append("and alipay_log_trade_state = 1 and alipay_log_trade_valid = 1 ");
		sb.append("and alipay_log_type in ("+alipayLogTypes+") ");
		if (branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in (");
			for(int i = 0; i<branchNums.size();i++){
				if(i==branchNums.size()-1){
					sb.append(branchNums.get(i)+") ");
				}else{
					sb.append(branchNums.get(i)+",");
				}
			}
		}
		if(StringUtils.isNotEmpty(orderNoPres)){
			if(orderNoPres.equals("POS")){
				sb.append(" and alipay_log_order_no not like 'DEP%' ");
				sb.append(" and alipay_log_order_no not like '"+AppConstants.S_Prefix_WD+"%' ");
			}else{
				String[] array = orderNoPres.split(",");
				for(int i = 0; i<array.length; i++){
					sb.append(" and alipay_log_order_no like '"+array[i]+"%' ");
				}
			}

			if(orderNoPres.contains("member")){
				sb.append(" and alipay_log_order_no = '' ");
			}
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		List<AlipayDetailDTO> list = new ArrayList<AlipayDetailDTO>();
		BigDecimal buyerMoney = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);

			AlipayDetailDTO alipayDetailDTO = new AlipayDetailDTO();
			alipayDetailDTO.setOrderFid((String) object[0]);
			alipayDetailDTO.setOrderNo((String) object[1]);
			alipayDetailDTO.setBranchNum((Integer) object[2]);
			alipayDetailDTO.setOrderTime((Date) object[3]);
			alipayDetailDTO.setCustomerId((String) object[4]);
			alipayDetailDTO.setConsumeSuccessMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			alipayDetailDTO.setConsumeSuccessActualMoney(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);

			//微会员存款没有orderNo
			if(alipayDetailDTO.getOrderNo().startsWith("DEP") || StringUtils.isEmpty(alipayDetailDTO.getOrderNo())){

				alipayDetailDTO.setType("卡存款");

			} else if(alipayDetailDTO.getOrderNo().startsWith(AppConstants.S_Prefix_WD)) {
				alipayDetailDTO.setType("微店消费");

			} else {
				alipayDetailDTO.setType("POS消费");

			}
			buyerMoney = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			alipayDetailDTO.setAlipayDiscountMoney(alipayDetailDTO.getConsumeSuccessActualMoney().subtract(buyerMoney));
			alipayDetailDTO.setBranchDiscountMoney(alipayDetailDTO.getConsumeSuccessMoney().subtract(alipayDetailDTO.getConsumeSuccessActualMoney()));
			alipayDetailDTO.setValid(false);
			list.add(alipayDetailDTO);
		}
		return list;
	}

	@Override
	public List<Object[]> findDepositSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String alipayLogTypes){
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(alipay_log_money),count(alipay_log_id) from alipay_log where system_book_code = '"+systemBookCode+"' ");
		sb.append("and alipay_log_start between '"+DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		sb.append("and alipay_log_trade_state = 1 and alipay_log_trade_valid = 1 ");
		sb.append("and alipay_log_type in ("+alipayLogTypes+") ");
		if (branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in (");
			for(int i = 0; i<branchNums.size();i++){
				if(i==branchNums.size()-1){
					sb.append(branchNums.get(i)+") ");
				}else{
					sb.append(branchNums.get(i)+",");
				}
			}
		}
		sb.append("and alipay_log_order_no like 'DEP%' ");
		sb.append("and alipay_log_order_no ='' " );
		sb.append("group by branch_num");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

}
