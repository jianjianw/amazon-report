package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.CardUserRegisterDao;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.Query;
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
}
