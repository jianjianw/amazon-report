package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.CardBillDao;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class CardBillDaoImpl extends DaoImpl implements CardBillDao {
	@Override
	public List<Object[]> findBranchBalance(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(card_bill_qty) from card_bill with(nolock) where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and card_bill_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if(dateFrom != null){
			sb.append("and card_bill_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and card_bill_audit_time <= :dateTo ");
		}
		sb.append("group by branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		
		
		sb = new StringBuffer();
		sb.append("select center_branch_num, sum(-card_bill_qty) from card_bill with(nolock) where system_book_code = :systemBookCode ");
		sb.append("and center_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and card_bill_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		sb.append("and card_bill_type = '" + AppConstants.CARD_BILL_TYPE_TRANSFER + "' ");
		if(dateFrom != null){
			sb.append("and card_bill_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and card_bill_audit_time <= :dateTo ");
		}
		sb.append("group by center_branch_num");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> subObjects = query.list();
		boolean find = false;
		for(int i = 0;i < subObjects.size();i++){
			Object[] localObject = subObjects.get(i);
			
			find = false;
			for(int j = 0;j < objects.size();j++){
				Object[] object = objects.get(j);
				if(object[0].equals(localObject[0])){
					object[1] = ((Integer)object[1]) + ((Integer)localObject[1]);
					find = true;
					break;
				}
			}
			if(!find){
				objects.add(localObject);
			}
		}
		return objects;
	}
	
	@Override
	public int getBalance(String systemBookCode, Integer branchNum) {
		Integer balance = 0;
		String sql = "select sum(card_bill_qty) from card_bill with(nolock) where system_book_code = :systemBookCode and branch_num = :branchNum and "
				+ "card_bill_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE;
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		Object object = query.uniqueResult();
		if(object != null){
			balance = (Integer) object;
		}
		
		sql = "select sum(-card_bill_qty) from card_bill with(nolock) where system_book_code = :systemBookCode and center_branch_num = :branchNum "
				+ "and card_bill_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " and card_bill_type = '" + AppConstants.CARD_BILL_TYPE_TRANSFER + "' ";
		query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		object = query.uniqueResult();
		if(object != null){
			balance = balance + (Integer) object;
		}
		return balance;
	}
}
