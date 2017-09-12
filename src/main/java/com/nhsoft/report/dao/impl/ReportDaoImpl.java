package com.nhsoft.report.dao.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.nhsoft.report.dao.ReportDao;
import com.nhsoft.report.dto.BusinessCollection;
import com.nhsoft.report.dto.BusinessCollectionIncome;
import com.nhsoft.report.model.OtherInout;
import com.nhsoft.report.model.RelatCard;
import com.nhsoft.report.model.ReplaceCard;
import com.nhsoft.report.model.ShiftTable;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yangqin on 2017/9/5.
 */
@Repository
public class ReportDaoImpl extends  DaoImpl implements ReportDao{
	
	@Override
	public Object excuteSql(String sql) {
		SQLQuery query = currentSession().createSQLQuery(sql);
		return query.list();
	}
	
	@Override
	public List<Object[]> findDayWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(shift_table_bizday) as amount, sum(order_gross_profit) as profit ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and order_state_code in (5, 7)  ");
		if(isMember){
			sb.append("and order_card_user_num > 0 ");
		}
		sb.append("group by branch_num, shift_table_bizday order by branch_num asc");
		
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> findMonthWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, subString(shift_table_bizday, 0, 7) as month, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(shift_table_bizday) as amount, sum(order_gross_profit) as profit, count(distinct shift_table_bizday) as bizAmount ");
		sb.append("from pos_order with(nolock) ");
		sb.append(" where system_book_code = :systemBookCode  ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and order_state_code in (5, 7) ");
		if(isMember){
			sb.append("and order_card_user_num > 0 ");
		}
		sb.append("group by branch_num, subString(shift_table_bizday, 0, 7) order by branch_num asc");
		
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}
	
	private BusinessCollectionIncome getBusinessCollectionIncome(
			List<BusinessCollectionIncome> businessCollectionIncomes, String name) {
		for (int i = 0; i < businessCollectionIncomes.size(); i++) {
			BusinessCollectionIncome businessCollectionIncome = businessCollectionIncomes.get(i);
			if (businessCollectionIncome.getName().equals(name)) {
				return businessCollectionIncome;
			}
		}
		return null;
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByBranch(String systemBookCode, List<Integer> branchNums,
	                                                               Date dateFrom, Date dateTo) {
		Map<Integer, BusinessCollection> map = new HashMap<Integer, BusinessCollection>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, payment_pay_by, sum(payment_money) as money , sum(payment_balance) as balance ");
		sb.append("from payment with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by branch_num, payment_pay_by order by branch_num asc, payment_pay_by asc ");
		
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String type = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal unPaidMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getPosIncomes().add(detail);
			if (type.equals(AppConstants.PAYMENT_GIFTCARD)) {
				data.setUnPaidMoney(unPaidMoney);
			}
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		// 补扣金额
		sb = new StringBuffer();
		sb.append("select branch_num, sum(consume_money) ");
		sb.append("from card_consume with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and consume_re_card_flag = 1 ");
		sb.append("group by branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setRePaidMoney(money);
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, deposit_payment_type_name, sum(deposit_cash) ");
		sb.append("from card_deposit with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and deposit_payment_type_name != '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by branch_num, deposit_payment_type_name ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String type = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			if(money.compareTo(BigDecimal.ZERO) == 0 && type.equals(AppConstants.PAYMENT_DEPOSIT_POINT)){
				continue;
			}
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, sum(deposit_money) ");
		sb.append("from card_deposit with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and deposit_payment_type_name = '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by branch_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setOldCardChangeMoney(data.getOldCardChangeMoney().add(money));
			
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, other_inout_payment_type, sum(case when other_inout_flag = 0 then -other_inout_money else other_inout_money end) ");
		sb.append("from other_inout with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and other_inout_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and other_inout_bizday <= :bizTo ");
		}
		sb.append("and other_inout_state_code = 3 ");
		sb.append("group by branch_num, other_inout_payment_type ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String type = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getOtherIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		
		Criteria criteria = currentSession().createCriteria(RelatCard.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("r.branchNum"))
				.add(Projections.groupProperty("r.relatCardPaymentTypeName")).add(Projections.sum("r.relatCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String type = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getRelateCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		//卡回收金额
		sb = new StringBuffer();
		sb.append("select branch_num, sum(card_user_revoke_money) ");
		sb.append("from card_user_log with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("group by branch_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setCardRevokeMoney(data.getCardRevokeMoney().add(money));
			
		}
		
		// 换卡收入
		criteria = currentSession().createCriteria(ReplaceCard.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("r.branchNum"))
				.add(Projections.groupProperty("r.replaceCardPaymentTypeName"))
				.add(Projections.sum("r.replaceCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String type = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getChangeCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		// 消费券
		sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.order_detail_item, sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and detail.order_detail_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and detail.order_detail_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is null ");
		sb.append("and detail.order_detail_state_code = 1 ");
		sb.append("group by detail.order_detail_branch_num, detail.order_detail_item order by detail.order_detail_branch_num asc ");
		query = currentSession().createSQLQuery(sb.toString());
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String type = (String) object[1];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);
			
			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}
		
		sb = new StringBuffer();
		sb.append("select c.branchNum, sum(detail.clientSettlementDetailMoney), sum(detail.clientSettlementDetailDiscount) ");
		sb.append("from ClientSettlement as c inner join c.clientSettlementDetails as detail ");
		sb.append("where c.systemBookCode = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and c.branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and c.clientSettlementAuditTime >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and c.clientSettlementAuditTime <= :dateTo ");
		}
		sb.append("and detail.payment is not null group by c.branchNum");
		query = currentSession().createQuery(sb.toString());
		query.setLockOptions(LockOptions.READ);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
			
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
			
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setSignInMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			data.setSignInDiscountMoney(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			
		}
		
		
		sb = new StringBuffer();
		sb.append("select branch_num, sum(order_discount_money + order_round + order_mgr_discount_money) as money ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by branch_num order by branch_num asc");
		
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setAllDiscountMoney(money);
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, sum(card_change_money) ");
		sb.append("from card_change with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and card_change_type = '" + AppConstants.CARD_CHANGE_TYPE_IN + "' ");
		sb.append("group by branch_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setCardChangeInMoney(data.getCardChangeInMoney().add(money));
			
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, sum(shift_table_actual_money) as actualCash, sum(shift_table_actual_bank_money) as bankMoney ");
		sb.append("from shift_table with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("group by branch_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal bankMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setReceiveCash(money);
			data.setReceiveBankMoney(bankMoney);
		}
		return new ArrayList<BusinessCollection>(map.values());
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchDay(String systemBookCode, List<Integer> branchNums,
	                                                                  Date dateFrom, Date dateTo) {
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, payment_pay_by, sum(payment_money) as money , sum(payment_balance) as balance ");
		sb.append("from payment with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by branch_num, shift_table_bizday, payment_pay_by order by branch_num asc, payment_pay_by asc ");
		
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal unPaidMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getPosIncomes().add(detail);
			if (type.equals(AppConstants.PAYMENT_GIFTCARD)) {
				data.setUnPaidMoney(unPaidMoney);
			}
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
			
		}
		
		// 补扣金额
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, sum(consume_money) ");
		sb.append("from card_consume with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and consume_re_card_flag = 1 ");
		sb.append("group by branch_num, shift_table_bizday");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			data.setRePaidMoney(money);
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, deposit_payment_type_name, sum(deposit_cash) ");
		sb.append("from card_deposit with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and deposit_payment_type_name != '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by branch_num, shift_table_bizday, deposit_payment_type_name ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			
			if(money.compareTo(BigDecimal.ZERO) == 0 && type.equals(AppConstants.PAYMENT_DEPOSIT_POINT)){
				continue;
			}
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, sum(deposit_money) ");
		sb.append("from card_deposit with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and deposit_payment_type_name = '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by branch_num, shift_table_bizday ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			data.setOldCardChangeMoney(data.getOldCardChangeMoney().add(money));
			
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, other_inout_bizday, other_inout_payment_type, sum(case when other_inout_flag = 0 then -other_inout_money else other_inout_money end) ");
		sb.append("from other_inout with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and other_inout_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and other_inout_bizday <= :bizTo ");
		}
		sb.append("and other_inout_state_code = 3 ");
		sb.append("group by branch_num, other_inout_bizday, other_inout_payment_type ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getOtherIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		Criteria criteria = currentSession().createCriteria(RelatCard.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.branchNum"))
				.add(Projections.groupProperty("r.shiftTableBizday"))
				.add(Projections.groupProperty("r.relatCardPaymentTypeName"))
				.add(Projections.sum("r.relatCardMoney"))
		);
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getRelateCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		
		//卡回收金额
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, sum(card_user_revoke_money) ");
		sb.append("from card_user_log with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("group by branch_num, shift_table_bizday ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			data.setCardRevokeMoney(data.getCardRevokeMoney().add(money));
			
		}
		
		// 换卡收入
		criteria = currentSession().createCriteria(ReplaceCard.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.branchNum"))
				.add(Projections.groupProperty("r.shiftTableBizday"))
				.add(Projections.groupProperty("r.replaceCardPaymentTypeName"))
				.add(Projections.sum("r.replaceCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getChangeCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		// 消费券
		sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.order_detail_bizday, detail.order_detail_item, sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and detail.order_detail_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and detail.order_detail_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is null ");
		sb.append("and detail.order_detail_state_code = 1 ");
		sb.append("group by detail.order_detail_branch_num, detail.order_detail_bizday, detail.order_detail_item order by detail.order_detail_branch_num asc ");
		query = currentSession().createSQLQuery(sb.toString());
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);
			
			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}
		
		sb = new StringBuffer();
		sb.append("select c.branch_num, convert(varchar(8), c.client_settlement_audit_time, 112) as biz, sum(detail.client_settlement_detail_money) as money, ");
		sb.append("sum(detail.client_settlement_detail_discount) as discount  ");
		sb.append("from client_settlement_detail as detail with(nolock) inner join client_settlement as c with(nolock) on detail.client_settlement_no = c.client_settlement_no ");
		sb.append("where c.system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and c.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and c.client_settlement_audit_time >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and c.client_settlement_audit_time <= :dateTo ");
		}
		sb.append("and detail.payment_no is not null group by c.branch_num, convert(varchar(8), c.client_settlement_audit_time, 112)");
		query = currentSession().createSQLQuery(sb.toString());
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			data.setSignInMoney(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			data.setSignInDiscountMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			
			
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, sum(order_discount_money + order_round + order_mgr_discount_money) as money ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by branch_num, shift_table_bizday order by branch_num asc");
		
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			data.setAllDiscountMoney(money);
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, sum(card_change_money) as money ");
		sb.append("from card_change with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and card_change_type = '" + AppConstants.CARD_CHANGE_TYPE_IN + "' ");
		sb.append("group by branch_num, shift_table_bizday order by branch_num asc");
		
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			data.setCardChangeInMoney(money);
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, sum(shift_table_actual_money) as actualCash, sum(shift_table_actual_bank_money) as bankMoney ");
		sb.append("from shift_table with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("group by branch_num,shift_table_bizday ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal bankMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			data.setReceiveCash(money);
			data.setReceiveBankMoney(bankMoney);
		}
		return new ArrayList<BusinessCollection>(map.values());
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode, List<Integer> branchNums,
	                                                                 Date dateFrom, Date dateTo) {
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>();
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, payment_machine, payment_pay_by, sum(payment_money) as money , sum(payment_balance) as balance ");
		sb.append("from payment with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by branch_num, shift_table_bizday, payment_machine, payment_pay_by ");
		
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String machineName = object[2] == null?"":(String)object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal unPaidMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				data.setPosMachineName(machineName);
				data.setUnPaidMoney(BigDecimal.ZERO);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			if (type.equals(AppConstants.PAYMENT_GIFTCARD)) {
				data.setUnPaidMoney(data.getUnPaidMoney().add(unPaidMoney));
				
			}
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
			data.getPosIncomes().add(detail);
		}
		
		// 补扣金额
		sb = new StringBuffer();
		sb.append("select consume_machine, branch_num, shift_table_bizday, sum(consume_money) ");
		sb.append("from card_consume with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num  in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and consume_re_card_flag = 1 ");
		sb.append("group by consume_machine, branch_num , shift_table_bizday ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String machineName = object[0] == null?"":(String)object[0];
			Integer branchNum = (Integer) object[1];
			String shiftTableBizday = (String) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				data.setPosMachineName(machineName);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			data.setRePaidMoney(money);
		}
		
		sb = new StringBuffer();
		sb.append("select deposit_machine, branch_num, shift_table_bizday, deposit_payment_type_name, sum(deposit_cash) ");
		sb.append("from card_deposit with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and deposit_payment_type_name != '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by deposit_machine, branch_num, shift_table_bizday, deposit_payment_type_name ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String machineName = object[0] == null?"":(String)object[0];
			Integer branchNum = (Integer) object[1];
			String shiftTableBizday = (String) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			
			if(money.compareTo(BigDecimal.ZERO) == 0 && type.equals(AppConstants.PAYMENT_DEPOSIT_POINT)){
				continue;
			}
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		sb = new StringBuffer();
		sb.append("select deposit_machine, branch_num, shift_table_bizday, sum(deposit_money) ");
		sb.append("from card_deposit with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and deposit_payment_type_name = '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by deposit_machine, branch_num, shift_table_bizday ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String machineName = object[0] == null?"":(String)object[0];
			Integer branchNum = (Integer) object[1];
			String shiftTableBizday = (String) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			data.setOldCardChangeMoney(data.getOldCardChangeMoney().add(money));
			
		}
		
		Criteria criteria = currentSession().createCriteria(OtherInout.class, "o")
				.add(Restrictions.eq("o.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("o.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("o.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("o.otherInoutBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("o.otherInoutBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("o.otherInoutMachine"))
				.add(Projections.groupProperty("o.branchNum"))
				.add(Projections.groupProperty("o.otherInoutBizday"))
				.add(Projections.groupProperty("o.otherInoutPaymentType"))
				.add(Projections.sqlProjection("sum(case when other_inout_flag = 0 then -other_inout_money else other_inout_money end) as money"
						, new String[]{"money"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String machineName = object[0] == null?"":(String)object[0];
			Integer branchNum = (Integer) object[1];
			String shiftTableBizday = (String) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getOtherIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		criteria = currentSession().createCriteria(RelatCard.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("r.relatCardMachine"))
				.add(Projections.groupProperty("r.branchNum")).add(Projections.groupProperty("r.shiftTableBizday"))
				.add(Projections.groupProperty("r.relatCardPaymentTypeName")).add(Projections.sum("r.relatCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String machineName = object[0] == null?"":(String)object[0];
			Integer branchNum = (Integer) object[1];
			String shiftTableBizday = (String) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getRelateCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		// 换卡收入
		criteria = currentSession().createCriteria(ReplaceCard.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.replaceCardMachine"))
				.add(Projections.groupProperty("r.branchNum"))
				.add(Projections.groupProperty("r.shiftTableBizday"))
				.add(Projections.groupProperty("r.replaceCardPaymentTypeName"))
				.add(Projections.sum("r.replaceCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String machineName = object[0] == null?"":(String)object[0];
			Integer branchNum = (Integer) object[1];
			String shiftTableBizday = (String) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getChangeCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		sb = new StringBuffer();
		sb.append("select p.branch_num, p.shift_table_bizday, p.order_machine, detail.order_detail_item, sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
		sb.append("where p.system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and p.shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and p.shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and p.order_state_code in (5, 7) and detail.item_num is null ");
		sb.append("and detail.order_detail_state_code = 1 ");
		sb.append("group by p.branch_num, p.shift_table_bizday, p.order_machine, detail.order_detail_item order by p.branch_num asc ");
		query = currentSession().createSQLQuery(sb.toString());
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String machineName = object[2] == null?"":(String)object[2];
			String type = (String) object[3];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal money = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);
			
			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}
		
		sb = new StringBuffer();
		sb.append("select card_change_machine, branch_num, shift_table_bizday, sum(card_change_money) ");
		sb.append("from card_change with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and card_change_type = '" + AppConstants.CARD_CHANGE_TYPE_IN + "' ");
		sb.append("group by card_change_machine, branch_num, shift_table_bizday ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String machineName = object[0] == null?"":(String)object[0];
			Integer branchNum = (Integer) object[1];
			String shiftTableBizday = (String) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			data.setCardChangeInMoney(money);
			
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, shift_table_terminal_id, sum(shift_table_actual_money) as cash, sum(shift_table_actual_bank_money) as bankMoney ");
		sb.append("from shift_table with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("group by branch_num, shift_table_bizday, shift_table_terminal_id ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String machineName = object[2] == null?"":(String)object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal bankMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			data.setReceiveCash(data.getReceiveCash().add(money));
			data.setReceiveBankMoney(data.getReceiveBankMoney().add(bankMoney));
		}
		return new ArrayList<BusinessCollection>(map.values());
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode, List<Integer> branchNums,
	                                                                   Date dateFrom, Date dateTo, String casher) {
		Criteria criteria = currentSession()
				.createCriteria(ShiftTable.class, "s")
				.add(Restrictions.eq("s.id.systemBookCode", systemBookCode))
				.add(Restrictions.between("s.id.shiftTableBizday", DateUtil.getDateShortStr(dateFrom),
						DateUtil.getDateShortStr(dateTo)));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("s.id.branchNum", branchNums));
		}
		if (StringUtils.isNotEmpty(casher)) {
			criteria.add(Restrictions.in("s.shiftTableUserName", casher.split(",")));
		}
		List<ShiftTable> shiftTables = criteria.list();
		
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select o.branch_num, o.shift_table_bizday, o.shift_table_num, p.payment_pay_by, sum(p.payment_money) as money, sum(p.payment_balance) as balance ");
		sb.append("from payment as p with(nolock) inner join pos_order as o with(nolock) on p.order_no = o.order_no ");
		sb.append("where o.system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and o.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and o.shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and o.shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		if (StringUtils.isNotEmpty(casher)) {
			sb.append("and o.order_operator in " + AppUtil.getStringParmeArray(casher.split(",")));
		}
		sb.append("group by o.branch_num, o.shift_table_bizday, o.shift_table_num, p.payment_pay_by ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal unPaidMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setUnPaidMoney(BigDecimal.ZERO);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			if (type.equals(AppConstants.PAYMENT_GIFTCARD)) {
				data.setUnPaidMoney(data.getUnPaidMoney().add(unPaidMoney));
				
			}
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
			data.getPosIncomes().add(detail);
		}
		
		// 补扣金额
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, shift_table_num, sum(consume_money) ");
		sb.append("from card_consume with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num  in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		if (StringUtils.isNotEmpty(casher)) {
			sb.append("and consume_operator in " + AppUtil.getStringParmeArray(casher.split(",")));
		}
		sb.append("and consume_re_card_flag = 1 ");
		sb.append("group by branch_num, shift_table_bizday, shift_table_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			Integer bizNum = (Integer) object[2];
			if (bizNum == null) {
				bizNum = 1;
			}
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum.toString() + shiftTableBizday + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				data.setShiftTableNum(bizNum);
				map.put(branchNum.toString() + shiftTableBizday + bizNum.toString(), data);
			}
			data.setRePaidMoney(money);
		}
		
		
		//卡回收金额
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, shift_table_num, sum(card_user_revoke_money) ");
		sb.append("from card_user_log with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		if (StringUtils.isNotEmpty(casher)) {
			sb.append("and card_user_log_operator in " + AppUtil.getStringParmeArray(casher.split(",")));
		}
		sb.append("group by branch_num, shift_table_bizday, shift_table_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			Integer bizNum = (Integer) object[2];
			if (bizNum == null) {
				bizNum = 1;
			}
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum.toString() + shiftTableBizday + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				data.setShiftTableNum(bizNum);
				map.put(branchNum.toString() + shiftTableBizday + bizNum.toString(), data);
			}
			data.setCardRevokeMoney(money);
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, shift_table_num, deposit_payment_type_name, sum(deposit_cash) ");
		sb.append("from card_deposit with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		if (StringUtils.isNotEmpty(casher)) {
			sb.append("and deposit_operator in " + AppUtil.getStringParmeArray(casher.split(",")));
		}
		sb.append("and deposit_payment_type_name != '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by branch_num, shift_table_bizday, shift_table_num, deposit_payment_type_name ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			if (bizNum == null) {
				bizNum = 1;
			}
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			
			if(money.compareTo(BigDecimal.ZERO) == 0 && type.equals(AppConstants.PAYMENT_DEPOSIT_POINT)){
				continue;
			}
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, shift_table_num, sum(deposit_money) ");
		sb.append("from card_deposit with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		if (StringUtils.isNotEmpty(casher)) {
			sb.append("and deposit_operator in " + AppUtil.getStringParmeArray(casher.split(",")));
		}
		sb.append("and deposit_payment_type_name = '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by branch_num, shift_table_bizday, shift_table_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			if (bizNum == null) {
				bizNum = 1;
			}
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			data.setOldCardChangeMoney(data.getOldCardChangeMoney().add(money));
			
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, shift_table_num, sum(card_change_money) ");
		sb.append("from card_change with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		if (StringUtils.isNotEmpty(casher)) {
			sb.append("and card_change_operator in " + AppUtil.getStringParmeArray(casher.split(",")));
		}
		sb.append("and card_change_type = '" + AppConstants.CARD_CHANGE_TYPE_IN + "' ");
		sb.append("group by branch_num, shift_table_bizday, shift_table_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			if (bizNum == null) {
				bizNum = 1;
			}
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			data.setCardChangeInMoney(money);
			
		}
		
		criteria = currentSession().createCriteria(OtherInout.class, "o")
				.add(Restrictions.eq("o.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("o.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("o.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("o.otherInoutBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("o.otherInoutBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if (StringUtils.isNotEmpty(casher)) {
			criteria.add(Restrictions.in("o.otherInoutOperator", casher.split(",")));
			
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("o.branchNum"))
				.add(Projections.groupProperty("o.otherInoutBizday"))
				.add(Projections.groupProperty("o.otherInoutShiftTableNum"))
				.add(Projections.groupProperty("o.otherInoutPaymentType"))
				.add(Projections.sqlProjection("sum(case when other_inout_flag = 0 then -other_inout_money else other_inout_money end) as money"
						, new String[]{"money"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getOtherIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		criteria = currentSession().createCriteria(RelatCard.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if (StringUtils.isNotEmpty(casher)) {
			criteria.add(Restrictions.in("r.relatCardOperator", casher.split(",")));
			
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.branchNum"))
				.add(Projections.groupProperty("r.shiftTableBizday"))
				.add(Projections.groupProperty("r.shiftTableNum"))
				.add(Projections.groupProperty("r.relatCardPaymentTypeName"))
				.add(Projections.sum("r.relatCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getRelateCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		// 换卡收入
		
		criteria = currentSession().createCriteria(ReplaceCard.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if (StringUtils.isNotEmpty(casher)) {
			criteria.add(Restrictions.in("r.replaceCardOperator", casher.split(",")));
			
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.branchNum"))
				.add(Projections.groupProperty("r.shiftTableBizday"))
				.add(Projections.groupProperty("r.shiftTableNum"))
				.add(Projections.groupProperty("r.replaceCardPaymentTypeName"))
				.add(Projections.sum("r.replaceCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			data.getChangeCardIncomes().add(detail);
			
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
		}
		
		// 消费券 TODO
		sb = new StringBuffer();
		sb.append("select p.branch_num, p.shift_table_bizday, p.shift_table_num, detail.order_detail_item, sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
		sb.append("where p.system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and p.shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and p.shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		if (StringUtils.isNotEmpty(casher)) {
			sb.append("and p.order_operator in " + AppUtil.getStringParmeArray(casher.split(",")));
			
		}
		sb.append("and p.order_state_code in (5, 7) and detail.item_num is null ");
		sb.append("and detail.order_detail_state_code = 1 ");
		sb.append("group by p.branch_num, p.shift_table_bizday, p.shift_table_num, detail.order_detail_item order by p.branch_num asc ");
		query = currentSession().createSQLQuery(sb.toString());
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal money = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);
			
			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}
		for (int i = 0; i < shiftTables.size(); i++) {
			ShiftTable shiftTable = shiftTables.get(i);
			BusinessCollection data = map.get(shiftTable.getId().getBranchNum().toString()
					+ shiftTable.getId().getShiftTableBizday() + shiftTable.getId().getShiftTableNum().toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(shiftTable.getId().getShiftTableBizday());
				data.setShiftTableNum(shiftTable.getId().getShiftTableNum());
				data.setBranchNum(shiftTable.getId().getBranchNum());
				map.put(shiftTable.getId().getBranchNum().toString() + shiftTable.getId().getShiftTableBizday()
						+ shiftTable.getId().getShiftTableNum().toString(), data);
			}
			data.setCasher(shiftTable.getShiftTableUserName());
			data.setReceiveCash(shiftTable.getShiftTableActualMoney() == null ? BigDecimal.ZERO : shiftTable
					.getShiftTableActualMoney());
			data.setReceiveBankMoney(shiftTable.getShiftTableActualBankMoney() == null ? BigDecimal.ZERO : shiftTable
					.getShiftTableActualBankMoney());
			data.setShiftTableStart(shiftTable.getShiftTableStart());
			data.setShiftTableEnd(shiftTable.getShiftTableEnd());
		}
		return new ArrayList<BusinessCollection>(map.values());
	}
}
