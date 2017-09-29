package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.AlipayLogDao;
import com.nhsoft.report.dto.AlipayDetailDTO;
import com.nhsoft.report.model.AlipayLog;
import com.nhsoft.report.shared.queryBuilder.LogQuery;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AlipayLogDaoImpl extends DaoImpl implements AlipayLogDao {


	private StringBuffer createByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from alipay_log where system_book_code = '"+systemBookCode+"' ");
		if(StringUtils.isNotEmpty(logQuery.getLogItem())){
			sb.append("and alipay_log_order_no like '%"+logQuery.getLogItem()+"%'");
			SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
			return sb;
		}
		if(branchNum != null){
			sb.append("and branch_num = "+branchNum+" ");
		}
		if(StringUtils.isNotEmpty(logQuery.getOperator())){
			sb.append("and alipay_log_operator = '"+logQuery.getOperator()+"' ");
		}
		if(StringUtils.isNotEmpty(logQuery.getOperateType())){
			sb.append("and alipay_log_type = '"+logQuery.getOperateType()+"' ");
		}
		sb.append("and alipay_log_start BETWEEN '"+DateUtil.getLongDateTimeStr(logQuery.getDateFrom()) + "' and '" + DateUtil.getLongDateTimeStr(logQuery.getDateTo()) + "' ");

		return sb;
	}

	@Override
	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery){
		StringBuffer sb = createByLogQuery(systemBookCode, branchNum, logQuery);
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		int size = sqlQuery.list().size();
		return size;
	}

	@Override
	public List<AlipayLog> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset,
										  int limit) {

		StringBuffer sb = createByLogQuery(systemBookCode, branchNum, logQuery);

		if(logQuery.getSortField() != null){
			if(logQuery.getSortType().equals("ASC")){
				sb.append("order by "+ logQuery.getSortField() +" ASC ");
			}else{
				sb.append("order by "+ logQuery.getSortField() +" DESC ");
			}

		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());

		if(logQuery.isPaging()){
			sqlQuery.setFirstResult(offset);
			sqlQuery.setMaxResults(limit);
		}
		sqlQuery.addEntity(AlipayLog.class);
		return sqlQuery.list();

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

		sb.append("and alipay_log_start BETWEEN '"+DateUtil.getLongDateTimeStr(dateFrom) + "' and '" + DateUtil.getLongDateTimeStr(dateTo) + "' ");

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
		sb.append("and alipay_log_start BETWEEN '"+DateUtil.getLongDateTimeStr(dateFrom) + "' and '" + DateUtil.getLongDateTimeStr(dateTo) + "' ");

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
		sb.append("and alipay_log_start BETWEEN '"+DateUtil.getLongDateTimeStr(dateFrom) + "' and '" + DateUtil.getLongDateTimeStr(dateTo) + "' ");
		sb.append("and alipay_log_trade_state = "+true+" and alipay_log_trade_valid = "+true+" ");
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
		sb.append("and alipay_log_start BETWEEN '"+DateUtil.getLongDateTimeStr(dateFrom) + "' and '" + DateUtil.getLongDateTimeStr(dateTo) + "' ");
		sb.append("and alipay_log_trade_state = "+true+" and alipay_log_trade_valid = "+true+" ");
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
		sb.append("and alipay_log_start BETWEEN '"+DateUtil.getLongDateTimeStr(dateFrom) + "' and '" + DateUtil.getLongDateTimeStr(dateTo) + "' ");
		sb.append("and alipay_log_trade_state = "+true+" and alipay_log_trade_valid = "+true+" ");
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
