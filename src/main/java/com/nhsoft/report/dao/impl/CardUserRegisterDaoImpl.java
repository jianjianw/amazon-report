package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.CardUserRegisterDao;
import com.nhsoft.report.shared.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
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
