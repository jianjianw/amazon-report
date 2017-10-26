package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.ReportDao;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
@Repository
public class ReportDaoImpl extends DaoImpl implements ReportDao {
	
	@Override
	public Object excuteSql(String systemBookCode, String sql) {
		Query query = currentSession().createSQLQuery(sql);
		Object object = query.list();
		return object;
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
			data.setShiftTableTerminalId(shiftTable.getShiftTableTerminalId());
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

	@Override
	public List<Object[]> findCategoryMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" select p.item_category, p.item_category_code,");
		queryStr.append(" sum(case when t.order_detail_state_code = 1 then t.order_detail_payment_money when t.order_detail_state_code = 4 then -t.order_detail_payment_money end) as money ");
		queryStr.append(" from  pos_order_detail as t with(nolock), pos_item as p with(nolock) ");
		queryStr.append(" where t.order_detail_book_code = :systemBookCode ");
		queryStr.append(" and t.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		queryStr.append(" and p.item_num = t.item_num");
		queryStr.append(" and t.order_detail_order_state in (5, 7) ");
		queryStr.append(" and t.order_detail_bizday between :bizFrom and :bizTo");
		queryStr.append(" group by p.item_category, p.item_category_code");
		queryStr.append(" order by p.item_category_code asc");

		Query query = currentSession().createSQLQuery(queryStr.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setParameter("bizFrom", DateUtil.getDateShortStr(dateFrom));
		query.setParameter("bizTo", DateUtil.getDateShortStr(dateTo));
		return query.list();
	}

	@Override
	public List<Object[]> findOrderDetailCompareDatas(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, List<Integer> itemNums) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" select t.item_num, t.order_detail_item_matrix_num, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 4 then -t.order_detail_amount else order_detail_amount end) as amount,");
		queryStr.append(" sum(case when t.order_detail_state_code = 1 then order_detail_payment_money when t.order_detail_state_code = 4 then -t.order_detail_payment_money end) as money, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 4 then -t.order_detail_gross_profit else t.order_detail_gross_profit end) as profit ");
		queryStr.append(" from pos_order_detail as t with(nolock) ");
		queryStr.append(" where t.order_detail_book_code = :systemBookCode and t.order_detail_branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums) + " and t.item_num is not null ");
		queryStr.append(" and t.order_detail_order_state in (5, 7) ");
		queryStr.append(" and t.order_detail_bizday between :bizFrom and :bizTo");
		if (itemNums != null && itemNums.size() > 0) {
			queryStr.append(" and t.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		queryStr.append(" and t.order_detail_state_code != 8 ");
		queryStr.append(" group by t.item_num, t.order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(queryStr.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setParameter("bizFrom", DateUtil.getDateShortStr(dateFrom));
		query.setParameter("bizTo", DateUtil.getDateShortStr(dateTo));
		return query.list();
	}

	@Override
	public List<Object[]> findDepositBizMoneyByDateType(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branchNum, %s, sum(depositMoney) as money ");
		sb.append("from CardDeposit where systemBookCode = :systemBookCode ");
		sb.append("and shiftTableBizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append(" and branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append(" group by branchNum, %s ");
		String hql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			hql = hql.replaceAll("%s", "substring(shiftTableBizday, 0, 7) ");
		} else {
			hql = hql.replaceAll("%s", "shiftTableBizday ");
		}
		Query query = currentSession().createQuery(hql);
		query.setString("systemBookCode", systemBookCode);
		return query.list();
	}

	@Override
	public List<Object[]> findCardBizCountByDateType(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String dateType) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);

		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, %s, count(card_user_register_cust_num) as cardCount ");
		sb.append("from card_user_register with(nolock) where system_book_code = :systemBookCode ");
		sb.append("and card_user_register_date between '" + DateUtil.getDateTimeString(dateFrom) + "' and '"
				+ DateUtil.getDateTimeString(dateTo) + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append(" and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append(" group by branch_num, %s ");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "substring(CONVERT(varchar(8) , card_user_register_date, 112 ), 0, 7) ");
		} else {
			sql = sql.replaceAll("%s", "CONVERT(varchar(8) , card_user_register_date, 112 ) ");
		}
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		return query.list();
	}

	@Override
	public List<RetailDetail> findRetailDetails(RetailDetailQueryData queryData) {

		StringBuffer sb = new StringBuffer();
		sb.append("select p.branch_num, p.order_no, p.order_operator, p.order_machine, p.order_time, ");
		sb.append("detail.item_num, detail.order_detail_amount, detail.order_detail_price, detail.order_detail_payment_money, ");
		sb.append("detail.order_detail_discount, detail.order_detail_item_matrix_num, detail.order_detail_state_code, ");
		sb.append("p.order_sold_by, detail.item_grade_num, p.order_state_code, detail.order_detail_commission, ");
		sb.append("detail.order_detail_memo, detail.order_detail_gross_profit, detail.order_detail_cost ");

		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
		sb.append("where p.system_book_code = '" + queryData.getSystemBookCode() + "' ");
		if(queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0){
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
		}
		sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFromShiftTable()) + "' ");
		sb.append("and '" + DateUtil.getDateShortStr(queryData.getDtToShiftTable()) + "' ");
		
		if(!StringUtils.equals(queryData.getExceptionConditon(), AppConstants.ANTI_SETTLEMENT)){
			sb.append("and p.order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
			
		} else {
			sb.append("and p.order_state_code = " + AppConstants.POS_ORDER_DETAIL_CREATE_REPAY);

		}

		sb.append("and detail.item_num is not null and detail.order_detail_state_code != 8 ");
		if(StringUtils.isNotEmpty(queryData.getOrderNo())){
			sb.append("and p.order_no = '" + queryData.getOrderNo() + "' ");
		}
		if(StringUtils.isNotEmpty(queryData.getCashier())){
			sb.append("and p.order_operator = '" + queryData.getCashier() + "' ");
		}
		if(StringUtils.isNotEmpty(queryData.getPosMachine())){
			sb.append("and p.order_machine = '" + queryData.getPosMachine() + "' ");
		}
		if(queryData.getPosClientFid() != null && queryData.getPosClientFid().size() > 0){
			sb.append("and p.client_fid in " + AppUtil.getStringParmeList(queryData.getPosClientFid()));
		}
		if(queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
		}
		if (StringUtils.isNotEmpty(queryData.getExceptionConditon())) {
			if (queryData.getExceptionConditon().equals(AppConstants.PRESENT_RECORD)) {
	
				sb.append("and detail.order_detail_state_code = " + AppConstants.POS_ORDER_DETAIL_STATE_PRESENT + " ");
			} else if (queryData.getExceptionConditon().equals(AppConstants.RETURN_RECORD)) {
				sb.append("and detail.order_detail_state_code = " + AppConstants.POS_ORDER_DETAIL_STATE_CANCEL + " ");

				
			}
		}
		if (StringUtils.isNotEmpty(queryData.getSaleMoneyFlag())) {
			if (queryData.getSaleMoney() == null) {
				queryData.setSaleMoney(BigDecimal.ZERO);
			}
			String caseSql = "(case when detail.order_detail_state_code = 2 then 0 "
					+ "when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money else detail.order_detail_payment_money end) ";
			BigDecimal saleMoney = queryData.getSaleMoney();
			if (queryData.getSaleMoneyFlag().equals(AppConstants.LESS_THAN)) {
				sb.append("and " + caseSql + " < " + saleMoney + " ");
				
			} else if (queryData.getSaleMoneyFlag().equals(AppConstants.LESS_THAN_OR_EQUAL)) {
				sb.append("and " + caseSql + " <= " + saleMoney + " ");

			} else if (queryData.getSaleMoneyFlag().equals(AppConstants.EQUAL)) {
				sb.append("and " + caseSql + " = " + saleMoney + " ");

			} else if (queryData.getSaleMoneyFlag().equals(AppConstants.MORE_THAN)) {
				sb.append("and " + caseSql + " > " + saleMoney + " ");

			} else if (queryData.getSaleMoneyFlag().equals(AppConstants.MORE_THAN_OR_EQUAL)) {
				sb.append("and " + caseSql + " >= " + saleMoney + " ");

			}
		}
		if (StringUtils.isNotEmpty(queryData.getRetailPriceFlag())) {
			if (queryData.getRetailPrice() == null) {
				queryData.setRetailPrice(BigDecimal.ZERO);
			}
			BigDecimal price = queryData.getRetailPrice();
			if (queryData.getRetailPriceFlag().equals(AppConstants.LESS_THAN)) {
				sb.append("and detail.order_detail_price < " + price + " ");

			} else if (queryData.getRetailPriceFlag().equals(AppConstants.LESS_THAN_OR_EQUAL)) {
				sb.append("and detail.order_detail_price <= " + price + " ");

			} else if (queryData.getRetailPriceFlag().equals(AppConstants.EQUAL)) {
				
				sb.append("and detail.order_detail_price = " + price + " ");

			} else if (queryData.getRetailPriceFlag().equals(AppConstants.MORE_THAN)) {
				
				sb.append("and detail.order_detail_price > " + price + " ");

			} else if (queryData.getRetailPriceFlag().equals(AppConstants.MORE_THAN_OR_EQUAL)) {
				
				sb.append("and detail.order_detail_price >= " + price + " ");

			}
		}
		if (StringUtils.isNotEmpty(queryData.getSaler())) {
			sb.append("and p.order_sold_by = '" + queryData.getSaler() + "' ");

		}
		if (StringUtils.isNotEmpty(queryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		if(queryData.getTimeFrom() != null && queryData.getTimeTo() != null){
			String timeFrom = DateUtil.getHHmmStr2(queryData.getTimeFrom());
			String timeTo = DateUtil.getHHmmStr2(queryData.getTimeTo());
			
			if(timeFrom.compareTo(timeTo) <= 0){
				sb.append("and p.order_time_char between '" + timeFrom + "' and '" + timeTo + "' ");
			} else {
				sb.append("and (p.order_time_char >= '" + timeFrom + "' or p.order_time_char <= '" + timeTo + "' ) ");
				
			}
			
			
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setMaxResults(10000);
		List<Object[]> objects = query.list();
		List<RetailDetail> list = new ArrayList<RetailDetail>();
		List<Integer> normalOrderStates = AppUtil.getNormalPosOrderState();
		Integer orderStateCode = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			RetailDetail retailDetail = new RetailDetail();
			retailDetail.setBranchNum((Integer) object[0]);
			retailDetail.setOrderNo((String) object[1]);
			retailDetail.setCashier((String) object[2]);
			retailDetail.setPosMachine((String) object[3]);
			retailDetail.setPosTime((Date) object[4]);
			retailDetail.setItemNum((Integer) object[5]);
			retailDetail.setAmount((BigDecimal) object[6]);
			retailDetail.setSalePrice((BigDecimal) object[7]);
			retailDetail.setSaleMoney((BigDecimal) object[8]);
			retailDetail.setDiscountMoney((BigDecimal) object[9]);
			retailDetail.setItemMatrixNum((Integer) object[10]);
			retailDetail.setSaleCommission(object[15] == null?BigDecimal.ZERO:(BigDecimal) object[15]);
			retailDetail.setMemo((String)object[16]);
			retailDetail.setSaleProfit((BigDecimal) object[17]);
			retailDetail.setSaleCost(((BigDecimal) object[18]).multiply(retailDetail.getAmount()));

			Integer stateCode = (Integer) object[11];
			retailDetail.setStateCode(stateCode);
			if (stateCode == AppConstants.POS_ORDER_DETAIL_STATE_CANCEL) {
				retailDetail.setAmount(retailDetail.getAmount().negate());
				retailDetail.setSaleMoney(retailDetail.getSaleMoney().negate());
				retailDetail.setDiscountMoney(retailDetail.getDiscountMoney().negate());
				retailDetail.setSaleCommission(retailDetail.getSaleCommission().negate());
				retailDetail.setSaleProfit(retailDetail.getSaleProfit().negate());
				retailDetail.setSaleCost(retailDetail.getSaleCost().negate());

			}
			if (stateCode == AppConstants.POS_ORDER_DETAIL_STATE_PRESENT) {
				retailDetail.setDiscountMoney(BigDecimal.ZERO);
				retailDetail.setSaleMoney(BigDecimal.ZERO);
				retailDetail.setSaleCommission(BigDecimal.ZERO);
			}
			retailDetail.setSaler((String) object[12]);
			retailDetail.setItemGradeNum((Integer) object[13]);
			
			orderStateCode = (Integer) object[14];
			if(!normalOrderStates.contains(orderStateCode)){
				retailDetail.setDiscountMoney(BigDecimal.ZERO);
				retailDetail.setSaleMoney(BigDecimal.ZERO);
			}
			
			list.add(retailDetail);
		}
		return list;
	}

	@Override
	public List<Object[]> findProfitAnalysisDays(ProfitAnalysisQueryData profitAnalysisQueryData) {
		StringBuffer sb = new StringBuffer();
		boolean queryPosItem = false;
		if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
				|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
						.getPosItemTypeCodes().size() > 0)) {
			queryPosItem = true;
		}
		if (profitAnalysisQueryData.isQueryClient()
				|| (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0)) {

			sb.append("select p.branch_num,p.shift_table_bizday, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as payment, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
			if(queryPosItem){
				sb.append("inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");
			}
			sb.append("where p.system_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums())
						+ " ");
			}
			sb.append("and p.shift_table_bizday between :bizFrom and :bizTo ");
			sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if (queryPosItem) {
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
			}
			if (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0) {
				sb.append("and p.client_fid in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getClientFids()));
			}
			if (profitAnalysisQueryData.isQueryClient()) {
				sb.append("and p.client_fid != '' ");
			}
			if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by p.branch_num,p.shift_table_bizday ");
		} else {
			sb.append("select detail.order_detail_branch_num,detail.order_detail_bizday, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as payment, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			if(queryPosItem){
				sb.append("inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");
			}
			sb.append("where detail.order_detail_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
			}
			sb.append("and detail.order_detail_bizday between :bizFrom and :bizTo ");
			sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if (queryPosItem) {
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
			}
			if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (profitAnalysisQueryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by detail.order_detail_branch_num, detail.order_detail_bizday ");	
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
		query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
		query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
		List<Object[]> objects = query.list();
		
		if(profitAnalysisQueryData.getIsQueryCF()){
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num, detail.order_kit_detail_bizday, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then (-detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as cost ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
			}
			sb.append("and detail.order_kit_detail_bizday between :bizFrom and :bizTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_kit_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
					|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
							.getPosItemTypeCodes().size() > 0)) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
				sb.append(") ");
			}

			sb.append("group by detail.order_kit_detail_branch_num, detail.order_kit_detail_bizday ");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
			query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
			query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
			objects.addAll(query.list());
		}
		return objects;
	}

	
	@Override
	public List<Object[]> findProfitAnalysisByClientAndItem(ProfitAnalysisQueryData profitAnalysisQueryData) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select p.client_fid, detail.item_num, detail.order_detail_item_matrix_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
		sb.append("where p.system_book_code = :systemBookCode  ");
		if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums())
					+ " ");
		}
		sb.append("and p.shift_table_bizday between :bizFrom and :bizTo ");
		sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if(profitAnalysisQueryData.isQueryPresent()){
			sb.append("and detail.order_detail_state_code = 2 ");
		}
		if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in "
					+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
		}
		if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
				|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
						.getPosItemTypeCodes().size() > 0)) {
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
			if (profitAnalysisQueryData.getPosItemTypeCodes() != null
					&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
				sb.append("and item.item_category_code in "
						+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
			}
			if (profitAnalysisQueryData.getBrandCodes() != null
					&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
				sb.append("and item.item_brand in "
						+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
			}
			sb.append(") ");
		}
		if (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0) {
			sb.append("and p.client_fid in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getClientFids())
					+ " ");
		}
		if (profitAnalysisQueryData.isQueryClient()) {
			sb.append("and p.client_fid != '' ");
		}
		if (profitAnalysisQueryData.getIsQueryCF()) {
			sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
		}
		if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
			sb.append("and p.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
		}
		sb.append("group by p.client_fid, detail.item_num, detail.order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
		query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
		query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
		return query.list();
	}

	@Override
	public List<Object[]> findProfitAnalysisByBranchAndItem(ProfitAnalysisQueryData profitAnalysisQueryData) {

		StringBuffer sb = new StringBuffer();

		if (profitAnalysisQueryData.isQueryClient()
				|| (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0)) {

			sb.append("select p.branch_num, detail.item_num, detail.order_detail_item_matrix_num, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
			sb.append("where p.system_book_code = :systemBookCode  ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums())
						+ " ");
			}
			sb.append("and p.shift_table_bizday between :bizFrom and :bizTo ");
			sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
					|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
							.getPosItemTypeCodes().size() > 0)) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
				sb.append(") ");
			}
			if (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0) {
				sb.append("and p.client_fid in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getClientFids())
						+ " ");
			}
			if (profitAnalysisQueryData.isQueryClient()) {
				sb.append("and p.client_fid != '' ");
			}
			if (profitAnalysisQueryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by p.branch_num, detail.item_num, detail.order_detail_item_matrix_num ");
		} else {
			sb.append("select detail.order_detail_branch_num, detail.item_num, detail.order_detail_item_matrix_num, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			sb.append("where detail.order_detail_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
			}
			sb.append("and detail.order_detail_bizday between :bizFrom and :bizTo ");
			sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
					|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
							.getPosItemTypeCodes().size() > 0)) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
				sb.append(") ");
			}
			if (profitAnalysisQueryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by detail.order_detail_branch_num, detail.item_num, detail.order_detail_item_matrix_num");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
		query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
		query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
		return query.list();
	}
	
	@Override
	public List<Object[]> findProfitAnalysisByItem(ProfitAnalysisQueryData profitAnalysisQueryData) {
		StringBuilder sb = new StringBuilder();
		boolean queryPosItem = false;
		if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
				|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
				.getPosItemTypeCodes().size() > 0)) {
			queryPosItem = true;
		}
		if (profitAnalysisQueryData.isQueryClient()
				|| (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0)) {
			
			sb.append("select detail.item_num, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as payment, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
			if(queryPosItem){
				sb.append("inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");
			}
			sb.append("where p.system_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums())
						+ " ");
			}
			sb.append("and p.shift_table_bizday between :bizFrom and :bizTo ");
			sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if (queryPosItem) {
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
			}
			if (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0) {
				sb.append("and p.client_fid in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getClientFids()));
			}
			if (profitAnalysisQueryData.isQueryClient()) {
				sb.append("and p.client_fid != '' ");
			}
			if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by detail.item_num ");
		} else {
			sb.append("select detail.item_num, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as payment, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			if(queryPosItem){
				sb.append("inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");
			}
			sb.append("where detail.order_detail_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
			}
			sb.append("and detail.order_detail_bizday between :bizFrom and :bizTo ");
			sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if (queryPosItem) {
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
			}
			if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (profitAnalysisQueryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by detail.item_num ");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
		query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
		query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
		List<Object[]> objects = query.list();
		
		if(profitAnalysisQueryData.getIsQueryCF()){
			sb = new StringBuilder();
			sb.append("select detail.item_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then (-detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as cost ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
			}
			sb.append("and detail.order_kit_detail_bizday between :bizFrom and :bizTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_kit_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
					|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
					.getPosItemTypeCodes().size() > 0)) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
				sb.append(") ");
			}
			
			sb.append("group by detail.item_num ");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
			query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
			query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
			objects.addAll(query.list());
		}
		return objects;
	}
	
	@Override
	public List<Object[]> findKitProfitAnalysisByBranchAndItem(ProfitAnalysisQueryData profitAnalysisQueryData) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_kit_detail_branch_num, detail.item_num, detail.order_kit_detail_item_matrix_num, ");
		sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else detail.order_kit_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_kit_detail_state_code = 4 then (-detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as cost ");
		sb.append("from pos_order_kit_detail as detail with(nolock) ");
		sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
		if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
			sb.append("and detail.order_kit_detail_branch_num in "
					+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
		}
		sb.append("and detail.order_kit_detail_bizday between :bizFrom and :bizTo ");
		sb.append("and detail.order_kit_detail_order_state in (5, 7) and detail.item_num is not null ");
		sb.append("and detail.order_kit_detail_state_code != 8 ");
		if(profitAnalysisQueryData.isQueryPresent()){
			sb.append("and detail.order_kit_detail_state_code = 2 ");
		}
		if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in "
					+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
		}
		if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
				|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
						.getPosItemTypeCodes().size() > 0)) {
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
			if (profitAnalysisQueryData.getPosItemTypeCodes() != null
					&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
				sb.append("and item.item_category_code in "
						+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
			}
			if (profitAnalysisQueryData.getBrandCodes() != null
					&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
				sb.append("and item.item_brand in "
						+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
			}
			sb.append(") ");
		}
		if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
			sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
		}
		sb.append("group by detail.order_kit_detail_branch_num, detail.item_num, detail.order_kit_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
		query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
		query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
		return query.list();
	}
	
	@Override
	public List<SalerCommissionBrand> findSalerCommissionBrands(String systemBookCode, Date dtFrom, Date dtTo,
																List<Integer> branchNums, List<String> salerNums) {
			
		StringBuffer sb = new StringBuffer();
		sb.append("select p.order_sold_by, p.branch_num, item.item_brand, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_commission when detail.order_detail_state_code = 4 then -order_detail_commission end) as commission ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
		sb.append("inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");
		sb.append("where p.system_book_code =  '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and p.order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		if (salerNums != null && salerNums.size() > 0) {
			sb.append("and p.order_sold_by in " + AppUtil.getStringParmeList(salerNums));
		}
		sb.append("and detail.item_num is not null and detail.order_detail_state_code != 8 ");
		sb.append("group by p.order_sold_by, p.branch_num, item.item_brand");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();		
		String saler = null;
		Integer branchNum = null;
		String brand = null;
		List<SalerCommissionBrand> list = new ArrayList<SalerCommissionBrand>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			saler = (String) object[0];
			branchNum = (Integer) object[1];
			brand = object[2] == null?"":(String)object[2];
			
			SalerCommissionBrand salerCommissionBrand = SalerCommissionBrand.find(list, saler, branchNum, brand);
			if(salerCommissionBrand == null){
				salerCommissionBrand = new SalerCommissionBrand();
				salerCommissionBrand.setSaler(saler);
				salerCommissionBrand.setBranchNum(branchNum);
				salerCommissionBrand.setBrand(brand);
				salerCommissionBrand.setSaleMoney(BigDecimal.ZERO);
				salerCommissionBrand.setSaleNums(BigDecimal.ZERO);
				salerCommissionBrand.setSaleCommission(BigDecimal.ZERO);
				list.add(salerCommissionBrand);
			}
			salerCommissionBrand.setSaleNums(salerCommissionBrand.getSaleNums().add(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]));
			salerCommissionBrand.setSaleMoney(salerCommissionBrand.getSaleMoney().add(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]));
			salerCommissionBrand.setSaleCommission(salerCommissionBrand.getSaleCommission().add(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]));
		}
		return list;
	}

	
	private Criteria createBySeller(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNums) {
		Criteria criteria = currentSession()
				.createCriteria(PosOrder.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode))
				.add(Restrictions.between("p.shiftTableBizday", DateUtil.getDateShortStr(dateFrom),
						DateUtil.getDateShortStr(dateTo)))
				.add(Restrictions.in("p.orderStateCode", AppUtil.getNormalPosOrderState()));
		if (branchNums != null && branchNums.size() > 0) {
			if (branchNums.size() == 1) {
				criteria.add(Restrictions.eq("p.branchNum", branchNums.get(0)));
			} else {
				criteria.add(Restrictions.in("p.branchNum", branchNums));

			}
		}
		if (salerNums != null && salerNums.size() > 0) {
			criteria.add(Restrictions.in("p.orderSoldBy", salerNums));
		}
		return criteria;
	}
	
	@Override
	public List<SalerCommission> findSalerCommissions(String systemBookCode, Date dtFrom, Date dtTo,
													  List<Integer> branchNums, List<String> salerNums) {
		
		List<SalerCommission> list = new ArrayList<SalerCommission>();		
		StringBuffer sb = new StringBuffer(); 
		sb.append("select order_sold_by, branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as receiptMoney, ");
		sb.append("count(order_no) as amount, sum(order_payment_money) as money, sum(order_commission) as commission, ");
		sb.append("sum(order_detail_item_count) as itemCount, ");
		sb.append("sum(case when order_detail_item_count > 0 then 1 when order_detail_item_count is null then 1 else 0 end) as validOrderNo ,");
		sb.append("sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money - order_gross_profit) as cost ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		if (salerNums != null && salerNums.size() > 0) {
			sb.append("and order_sold_by in " + AppUtil.getStringParmeList(salerNums));
		}
		sb.append("group by order_sold_by, branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
				
		BigDecimal validSaleOrderCount = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String saler = (String)object[0]; 
			Integer branchNum = (Integer)object[1];
			BigDecimal orderMoney = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
			BigDecimal orderCount = BigDecimal.valueOf((Integer)object[3]);	
			
			SalerCommission salerCommission = new SalerCommission();
			salerCommission.setSaler(saler);
			salerCommission.setBranchNum(branchNum);
			salerCommission.setSaleNums(BigDecimal.ZERO);
			salerCommission.setSaleOrderMoney(orderMoney);
			salerCommission.setSaleOrderCount(orderCount);			
			salerCommission.setSaleMoney(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
			salerCommission.setSaleCommission(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			salerCommission.setSaleItemRelatRate(object[6] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) object[6]));
			
			if(salerCommission.getSaleOrderCount().compareTo(BigDecimal.ZERO) > 0){
				salerCommission.setSaleOrderPrice(salerCommission.getSaleOrderMoney().divide(salerCommission.getSaleOrderCount(), 2, BigDecimal.ROUND_HALF_UP));

			}	
			
			validSaleOrderCount = object[7] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[7]);
			if(validSaleOrderCount.compareTo(BigDecimal.ZERO) > 0){
				salerCommission.setSaleItemRelatRate(salerCommission.getSaleItemRelatRate().divide(validSaleOrderCount, 2, BigDecimal.ROUND_HALF_UP));
				
			}
			salerCommission.setSaleCost(object[8]==null?BigDecimal.ZERO:(BigDecimal) object[8]);
			
			list.add(salerCommission);
		}
		return list;
	}

	@Override
	public List<SalerCommissionDetail> findSalerCommissionDetails(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, List<String> salerNums) {
			
		StringBuffer sb = new StringBuffer();
		sb.append("select p.order_sold_by, p.branch_num, detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_commission when detail.order_detail_state_code = 4 then -order_detail_commission end) as commission, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
		sb.append("where p.system_book_code =  '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and p.order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		if (salerNums != null && salerNums.size() > 0) {
			sb.append("and p.order_sold_by in " + AppUtil.getStringParmeList(salerNums));
		}
		sb.append("and detail.item_num is not null and detail.order_detail_state_code != 8 ");
		sb.append("group by p.order_sold_by, p.branch_num, detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		
		List<SalerCommissionDetail> list = new ArrayList<SalerCommissionDetail>();
		List<Object[]> objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SalerCommissionDetail salerCommission = new SalerCommissionDetail();
			salerCommission.setSaler((String) object[0]);
			salerCommission.setBranchNum((Integer) object[1]);
			salerCommission.setItemNum((Integer) object[2]);
			salerCommission.setSaleNums(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			salerCommission.setSaleMoney(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
			salerCommission.setSaleCommission(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			salerCommission.setSaleCost(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			list.add(salerCommission);
		}
		return list;
	}

	@Override
	public List<Object[]> findCustomerAnalysisHistorys(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, String saleType) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num as branchNum, order_machine as machine, shift_table_bizday as bizday, sum(order_payment_money) as paymentMoney, count(order_no) as orderNo, ");
		sb.append("sum(order_coupon_total_money) as conponMoney, sum(order_mgr_discount_money) as mgrDiscount ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		
		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}		
		sb.append("group by branch_num, order_machine, shift_table_bizday ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addScalar("branchNum", StandardBasicTypes.INTEGER)
			.addScalar("machine", StandardBasicTypes.STRING)
			.addScalar("bizday", StandardBasicTypes.STRING)
			.addScalar("paymentMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("orderNo", StandardBasicTypes.LONG)
			.addScalar("conponMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("mgrDiscount", StandardBasicTypes.BIG_DECIMAL);
		return query.list();
	}


	@Override
	public Object[] findCustomerAnalysisRanges(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums,
			Integer rangeFrom, Integer rangeTo, String saleType) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(order_payment_money) as paymentMoney, count(order_no) as orderNo, ");
		sb.append("sum(order_coupon_total_money) as conponMoney, sum(order_mgr_discount_money) as mgrDiscount ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		if (rangeFrom != null) {
			sb.append("and (order_payment_money + order_coupon_total_money - order_mgr_discount_money) > " + rangeFrom + " ");
		}
		if (rangeTo != null) {
			sb.append("and (order_payment_money + order_coupon_total_money - order_mgr_discount_money) <= " + rangeTo + " ");

		}	
		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addScalar("paymentMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("orderNo", StandardBasicTypes.LONG)
			.addScalar("conponMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("mgrDiscount", StandardBasicTypes.BIG_DECIMAL)
			;
		return (Object[]) query.uniqueResult();
	}

	@Override
	public List<Object[]> findCustomerAnalysisDays(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, String saleType) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select shift_table_bizday as bizday, sum(order_payment_money) as paymentMoney, count(order_no) as orderNo, ");
		sb.append("sum(order_coupon_total_money) as conponMoney, sum(order_mgr_discount_money) as mgrDiscount ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		
		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		sb.append("group by shift_table_bizday ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addScalar("bizday", StandardBasicTypes.STRING)
			.addScalar("paymentMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("orderNo", StandardBasicTypes.LONG)
			.addScalar("conponMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("mgrDiscount", StandardBasicTypes.BIG_DECIMAL);
		return query.list();
	}

	@Override
	public List<Object[]> findCustomerAnalysisBranch(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, String saleType) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num as branchNum, sum(order_payment_money) as paymentMoney, count(order_no) as orderNo, ");
		sb.append("sum(order_coupon_total_money) as conponMoney, sum(order_mgr_discount_money) as mgrDiscount, ");
		sb.append("sum(order_gross_profit) as grossProfit, sum(order_detail_item_count) as itemCount, ");
		sb.append("count(case when order_card_user_num > 0 then order_no end) as userAmount, ");
		sb.append("sum(case when order_card_user_num > 0 then (order_payment_money - order_mgr_discount_money + order_coupon_total_money) end) as userMoney, ");
		sb.append("sum(case when order_detail_item_count > 0 then 1 when order_detail_item_count is null then 1 else 0 end) as validOrderNo ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		
		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		sb.append("group by branch_num ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addScalar("branchNum", StandardBasicTypes.INTEGER)
			.addScalar("paymentMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("orderNo", StandardBasicTypes.LONG)
			.addScalar("conponMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("mgrDiscount", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("grossProfit", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("itemCount", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("userAmount", StandardBasicTypes.INTEGER)
			.addScalar("userMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("validOrderNo", StandardBasicTypes.BIG_DECIMAL)
			;
		return query.list();
	}

	@Override
	public List<Object[]> findCustomerAnalysisTimePeriods(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, Integer space, String saleType) {
		if(space == null){
			space = 60;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select max(order_time), sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, count(order_no) as amount ");
		sb.append("from pos_order with(nolock) where system_book_code = :systemBookCode and shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and order_state_code in (5,7) ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		
		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		
		sb.append(" group by ((DATEDIFF(mi, '00:00:00',convert(varchar(8),order_time,108)))/(:gap) )");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("gap", space);
		return sqlQuery.list();
	}

	@Override
	public Object[] sumCustomerAnalysis(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums,
			String branchType) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, count(order_no) as orderNo, ");
		sb.append("sum(order_gross_profit) as profit, count(distinct shift_table_bizday) as shiftCount ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			
		}
		if (StringUtils.isNotEmpty(branchType)) {
			if (branchType.equals(AppConstants.BRANCH_TYPE_DIRECT)) {
				sb.append("and branch_num in (select branch_num from branch where system_book_code = '" + systemBookCode + "' and (branch_type = '' or branch_type = '" + branchType + "' ))");
			} else {
				sb.append("and branch_num in (select branch_num from branch where system_book_code = '" + systemBookCode + "' and branch_type = '" + branchType + "' )");

			}
		}
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addScalar("money", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("orderNo", StandardBasicTypes.LONG)
			.addScalar("profit", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("shiftCount", StandardBasicTypes.LONG);
		return (Object[]) query.uniqueResult();

	}

	@Override
	public List<Object[]> findSaleAnalysisCommon(SaleAnalysisQueryData queryData) {
		StringBuffer sb = new StringBuffer();
		if(queryData.getIsQueryCardUser()){
			sb.append("select detail.item_num, detail.order_detail_state_code, ");
			sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
			sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
			sb.append("sum(detail.order_detail_discount) as discount, count(distinct p.branch_num) as branchCount ");
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
			sb.append("where p.system_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			sb.append("and p.order_card_user_num > 0 ");
			if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (queryData.getIsQueryGrade()) {
				sb.append("and (detail.item_grade_num is null or detail.item_grade_num = 0 ) ");
			} 
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by detail.item_num, detail.order_detail_state_code ");
		} else {
			
			sb.append("select detail.item_num, detail.order_detail_state_code, ");
			sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
			sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
			sb.append("sum(detail.order_detail_discount) as discount ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			sb.append("where detail.order_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by detail.item_num, detail.order_detail_state_code ");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb = new StringBuffer();
			sb.append("select kitDetail.item_num, kitDetail.order_kit_detail_state_code, ");
			sb.append("sum(kitDetail.order_kit_detail_amount) as amount, sum(kitDetail.order_kit_detail_payment_money) as money, ");
			sb.append("sum(0.00) as assistAmount, count(kitDetail.item_num) as amount_, ");
			sb.append("sum(kitDetail.order_kit_detail_discount) as discount ");
			sb.append("from pos_order_kit_detail as kitDetail with(nolock) ");
			sb.append("where kitDetail.order_kit_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and kitDetail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and kitDetail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and kitDetail.order_kit_detail_order_state in (5, 7) ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and kitDetail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (kitDetail.order_source is null or kitDetail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = kitDetail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by kitDetail.item_num, kitDetail.order_kit_detail_state_code ");
			query = currentSession().createSQLQuery(sb.toString());
			objects.addAll(query.list());
		}
		return objects;
	}

	@Override
	public List<Object[]> findSaleAnalysisCommonItemMatrix(SaleAnalysisQueryData queryData) {////
		StringBuffer sb = new StringBuffer();
		if(queryData.getIsQueryCardUser()){
			sb.append("select detail.item_num, detail.order_detail_item_matrix_num, detail.order_detail_state_code, ");
			sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
			sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
			sb.append("sum(detail.order_detail_discount) as discount, count(distinct p.branch_num) as branchCount ");
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
			sb.append("where p.system_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			sb.append("and p.ORDER_CARD_USER_NUM > 0 ");
			if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (queryData.getIsQueryGrade()) {
				sb.append("and (detail.item_grade_num is null or detail.item_grade_num = 0 ) ");
			} 
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by detail.item_num, detail.order_detail_item_matrix_num, detail.order_detail_state_code ");

		} else {
			sb.append("select detail.item_num, detail.order_detail_item_matrix_num, detail.order_detail_state_code, ");
			sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
			sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
			sb.append("sum(detail.order_detail_discount) as discount, count(distinct detail.order_detail_branch_num) as branchCount ");
			sb.append("from pos_order_detail as detail with(nolock)  ");
			sb.append("where detail.order_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (queryData.getIsQueryGrade()) {
				sb.append("and (detail.item_grade_num is null or detail.item_grade_num = 0 ) ");
			} 
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			
			sb.append("group by detail.item_num, detail.order_detail_item_matrix_num, detail.order_detail_state_code ");
			
		}
		
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb = new StringBuffer();
			sb.append("select kitDetail.item_num, kitDetail.order_kit_detail_item_matrix_num, kitDetail.order_kit_detail_state_code, ");
			sb.append("sum(kitDetail.order_kit_detail_amount) as amount, sum(kitDetail.order_kit_detail_payment_money) as money, ");
			sb.append("sum(0.00) as assistAmount, count(kitDetail.item_num) as amount_, ");
			sb.append("sum(kitDetail.order_kit_detail_discount) as discount, count(distinct kitDetail.order_kit_detail_branch_num) as branchCount ");
			sb.append("from pos_order_kit_detail as kitDetail with(nolock) ");
			sb.append("where kitDetail.order_kit_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and kitDetail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and kitDetail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and kitDetail.order_kit_detail_order_state in (5, 7) ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and kitDetail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (kitDetail.order_source is null or kitDetail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = kitDetail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by kitDetail.item_num, kitDetail.order_kit_detail_item_matrix_num, kitDetail.order_kit_detail_state_code ");
			query = currentSession().createSQLQuery(sb.toString());
			List<Object[]> subObjects = query.list();
			objects.addAll(subObjects);
		}
		return objects;
	}

	@Override
	public List<Object[]> findSaleAnalysisCommonItemGrade(SaleAnalysisQueryData queryData) {
		StringBuffer sb = new StringBuffer();
		if(queryData.getIsQueryCardUser()){
			sb.append("select detail.item_num, detail.item_grade_num, detail.order_detail_state_code, ");
			sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
			sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
			sb.append("sum(detail.order_detail_discount) as discount ");
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
			sb.append("where p.system_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			sb.append("and p.ORDER_CARD_USER_NUM > 0 ");
			sb.append("and detail.item_grade_num is not null and detail.item_grade_num != 0 ");
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by detail.item_num, detail.item_grade_num, detail.order_detail_state_code ");

		} else {
			
			sb.append("select detail.item_num, detail.item_grade_num, detail.order_detail_state_code, ");
			sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
			sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
			sb.append("sum(detail.order_detail_discount) as discount ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			sb.append("where detail.order_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			sb.append("and detail.item_grade_num is not null and detail.item_grade_num != 0 ");
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));
					
				} else {
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
					
				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by detail.item_num, detail.item_grade_num, detail.order_detail_state_code ");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<Object[]> findSaleAnalysisByBranchs(SaleAnalysisQueryData queryData) {
		StringBuffer sb = new StringBuffer();
		boolean queryPosItem = false;
		
		if ((queryData.getBrandCodes() != null && queryData.getBrandCodes().size() > 0)
				|| (queryData.getPosItemTypeCodes() != null && queryData.getPosItemTypeCodes().size() > 0)
				|| StringUtils.isNotEmpty(queryData.getItemDepartments())) {
			queryPosItem = true;
		}
		if(queryData.getIsQueryCardUser()){
			
			sb.append("select p.branch_num as branchNum, detail.order_detail_state_code as stateCode,  ");
			sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
			sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_ ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			sb.append("inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
			if(queryPosItem){
				sb.append("inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");

			}
			sb.append("where p.system_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and p.order_state_code in (5, 7) and p.order_card_user_num > 0 ");
			sb.append("and detail.item_num is not null ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()) + " ");
			}
			if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (queryPosItem) {
			
				if (queryData.getPosItemTypeCodes() != null && queryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(queryData.getPosItemTypeCodes()));
				}
				if (queryData.getBrandCodes() != null && queryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in " + AppUtil.getStringParmeList(queryData.getBrandCodes()));
				}
				if (StringUtils.isNotEmpty(queryData.getItemDepartments())){
					sb.append("and item.item_department in " + AppUtil.getStringParmeArray(queryData.getItemDepartments().split(",")));
				}
			}
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by p.branch_num, detail.order_detail_state_code ");
		} else {
			
			sb.append("select detail.order_detail_branch_num as branchNum, detail.order_detail_state_code as stateCode,  ");
			sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
			sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_ ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			if(queryPosItem){
				sb.append("inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");

			}
			sb.append("where detail.order_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and detail.order_detail_order_state in (5, 7) ");
			sb.append("and detail.item_num is not null ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()) + " ");
			}
			if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			if (queryPosItem) {
			
				if (queryData.getPosItemTypeCodes() != null && queryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(queryData.getPosItemTypeCodes()));
				}
				if (queryData.getBrandCodes() != null && queryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in " + AppUtil.getStringParmeList(queryData.getBrandCodes()));
				}
				if (StringUtils.isNotEmpty(queryData.getItemDepartments())){
					sb.append("and item.item_department in " + AppUtil.getStringParmeArray(queryData.getItemDepartments().split(",")));
				}
			}
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by detail.order_detail_branch_num, detail.order_detail_state_code ");
		}
		
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb = new StringBuffer();
			
			sb.append("select kitDetail.order_kit_detail_branch_num as branchNum, kitDetail.order_kit_detail_state_code as stateCode, ");
			sb.append("sum(kitDetail.order_kit_detail_amount) as amount, sum(kitDetail.order_kit_detail_payment_money) as money, ");
			sb.append("sum(0.00) as assistAmount, count(kitDetail.item_num) as amount_ ");
			sb.append("from pos_order_kit_detail as kitDetail with(nolock) ");
			if (queryPosItem) {
				sb.append("inner join pos_item as item with(nolock) on item.item_num = kitDetail.item_num ");
			}
			sb.append("where kitDetail.order_kit_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and kitDetail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and kitDetail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and kitDetail.order_kit_detail_order_state in (5, 7)  ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and kitDetail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			if (queryPosItem) {

				if (queryData.getPosItemTypeCodes() != null && queryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(queryData.getPosItemTypeCodes()));
				}
				if (queryData.getBrandCodes() != null && queryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in " + AppUtil.getStringParmeList(queryData.getBrandCodes()));
				}
				if (StringUtils.isNotEmpty(queryData.getItemDepartments())){
					sb.append("and item.item_department in " + AppUtil.getStringParmeArray(queryData.getItemDepartments().split(",")));
				}
			}
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (kitDetail.order_source is null or kitDetail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = kitDetail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by kitDetail.order_kit_detail_branch_num, kitDetail.order_kit_detail_state_code ");
			query = currentSession().createSQLQuery(sb.toString());
			objects.addAll(query.list());
		}
		return objects;
	}

	@Override
	public List<Object[]> findSaleAnalysisByCategoryBranchs(SaleAnalysisQueryData queryData) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, item.item_category, item.item_category_code, detail.order_detail_state_code, ");
		sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
		sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_ ");
		sb.append("from pos_order_detail as detail with(nolock), pos_item as item with(nolock) ");
		if(queryData.getIsQueryCardUser()){
			sb.append("inner join pos_order as p on p.order_no = detail.order_no ");
		}
		sb.append("where item.item_num = detail.item_num and detail.order_detail_book_code = '"
				+ queryData.getSystemBookCode() + "' ");
		if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
		}
		sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
				+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
		if(queryData.getIsQueryCardUser()){
			sb.append("and p.order_card_user_num > 0 ");
		}
		if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
		}
		if (queryData.getPosItemTypeCodes() != null && queryData.getPosItemTypeCodes().size() > 0) {
			sb.append("and item.item_category_code in " + AppUtil.getStringParmeList(queryData.getPosItemTypeCodes()));
		}
		if (queryData.getBrandCodes() != null && queryData.getBrandCodes().size() > 0) {
			sb.append("and item.item_brand in " + AppUtil.getStringParmeList(queryData.getBrandCodes()));
		}
		if (StringUtils.isNotEmpty(queryData.getItemDepartments())){
			sb.append("and item.item_department in " + AppUtil.getStringParmeArray(queryData.getItemDepartments().split(",")));
		}
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null ) ");
		}
		if (StringUtils.isNotEmpty(queryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
			sb.append("and detail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
		}
		if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
			sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
			for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
				TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
				if(i > 0){
					sb.append(" or ");
				}
				sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
			}
			
			sb.append(")) ");
		}
		sb.append("group by detail.order_detail_branch_num, item.item_category, item.item_category_code, detail.order_detail_state_code ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb = new StringBuffer();
			sb.append("select kitDetail.order_kit_detail_branch_num, item.item_category, item.item_category_code, kitDetail.order_kit_detail_state_code, ");
			sb.append("sum(kitDetail.order_kit_detail_amount) as amount, sum(kitDetail.order_kit_detail_payment_money) as money, ");
			sb.append("sum(0.00) as assistAmount, count(kitDetail.item_num) as amount_ ");
			sb.append("from pos_order_kit_detail as kitDetail with(nolock), pos_item as item with(nolock) ");
			sb.append("where item.item_num = kitDetail.item_num and kitDetail.order_kit_detail_book_code = '"
					+ queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and kitDetail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and kitDetail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and kitDetail.order_kit_detail_order_state in (5, 7)  ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and kitDetail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			if (queryData.getPosItemTypeCodes() != null && queryData.getPosItemTypeCodes().size() > 0) {
				sb.append("and item.item_category_code in "
						+ AppUtil.getStringParmeList(queryData.getPosItemTypeCodes()));
			}
			if (queryData.getBrandCodes() != null && queryData.getBrandCodes().size() > 0) {
				sb.append("and item.item_brand in " + AppUtil.getStringParmeList(queryData.getBrandCodes()));
			}
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (kitDetail.order_source is null or kitDetail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = kitDetail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by kitDetail.order_kit_detail_branch_num, item.item_category, item.item_category_code, kitDetail.order_kit_detail_state_code ");
			query = currentSession().createSQLQuery(sb.toString());
			objects.addAll(query.list());
		}
		return objects;
	}

	private Criteria createPolicyAllowPriftQuery(PolicyAllowPriftQuery policyAllowPriftQuery) {
		Criteria criteria = currentSession().createCriteria(PosOrderDetail.class, "detail").add(
				Restrictions.eq("detail.orderDetailBookCode", policyAllowPriftQuery.getSystemBookCode()));
		if (policyAllowPriftQuery.getBranchNums() != null && policyAllowPriftQuery.getBranchNums().size() > 0) {
			criteria.add(Restrictions.in("detail.orderDetailBranchNum", policyAllowPriftQuery.getBranchNums()));
		}
		criteria.add(
				Restrictions.between("detail.orderDetailBizday",
						DateUtil.getDateShortStr(policyAllowPriftQuery.getDtFrom()),
						DateUtil.getDateShortStr(policyAllowPriftQuery.getDtTo())))
				.add(Restrictions.isNotNull("detail.itemNum"))
				.add(Restrictions.eq("detail.orderDetailPolicyPromotionFlag", true))
				.add(Restrictions.in("detail.orderDetailOrderState", AppUtil.getNormalPosOrderState()));
		if (policyAllowPriftQuery.getItemNums() != null && policyAllowPriftQuery.getItemNums().size() > 0) {
			criteria.add(Restrictions.in("detail.itemNum", policyAllowPriftQuery.getItemNums()));
		}
		if (StringUtils.isNotEmpty(policyAllowPriftQuery.getProfitType())) {
			if (policyAllowPriftQuery.getProfitType().equals(AppConstants.POLICY_PROMOTION_SUPPLIER)) {
				criteria.add(Restrictions.eq("detail.orderDetailPromotionType", 1));
			} else if (policyAllowPriftQuery.getProfitType().equals(AppConstants.POLICY_PROMOTION_CENTER)) {
				criteria.add(Restrictions.eq("detail.orderDetailPromotionType", 2));
			} else {
				criteria.add(Restrictions.ne("detail.orderDetailPromotionType", 0));
			}
		}
		return criteria;
	}

	@Override
	public List<Object[]> findItemRebates(PolicyAllowPriftQuery policyAllowPriftQuery) {
		Criteria criteria = createPolicyAllowPriftQuery(policyAllowPriftQuery);
		criteria.add(Restrictions.ne("detail.orderDetailStateCode", AppConstants.POS_ORDER_DETAIL_STATE_REMOVE));
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.groupProperty("detail.orderDetailBranchNum"))
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections
						.sqlProjection(
								"sum(case when order_detail_state_code = 1 then order_detail_payment_money when order_detail_state_code = 4 then -order_detail_payment_money end) as money,"
										+ "sum(case when order_detail_state_code = 4 then -order_detail_discount when order_detail_state_code = 1 then order_detail_discount end) as discount, "
										+ "sum(case when order_detail_state_code = 4 then -order_detail_amount else order_detail_amount end) as amount",
								new String[] { "amount", "money", "discount" }, new Type[] {
										StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL,
										StandardBasicTypes.BIG_DECIMAL })));
		criteria.addOrder(Order.asc("detail.orderDetailBranchNum"));
		return criteria.list();
	}

	@Override
	public List<Object[]> findRebatesDetail(PolicyAllowPriftQuery policyAllowPriftQuery) {
		Criteria criteria = createPolicyAllowPriftQuery(policyAllowPriftQuery);
		criteria.add(Restrictions.gt("detail.orderDetailDiscount", BigDecimal.ZERO));
		criteria.setProjection(Projections.projectionList().add(Projections.property("detail.orderDetailBranchNum"))
				.add(Projections.property("detail.orderDetailBizday")).add(Projections.property("detail.id.orderNo"))
				.add(Projections.property("detail.itemNum")).add(Projections.property("detail.orderDetailStdPrice"))
				.add(Projections.property("detail.orderDetailPrice"))
				.add(Projections.property("detail.orderDetailAmount"))
				.add(Projections.property("detail.orderDetailPaymentMoney"))
				.add(Projections.property("detail.orderDetailDiscount"))
				.add(Projections.property("detail.orderDetailPromotionType"))
				.add(Projections.property("detail.orderDetailStateCode")));
		criteria.addOrder(Order.asc("detail.orderDetailBranchNum"));
		criteria.addOrder(Order.asc("detail.itemNum"));
		return criteria.list();
	}

	@Override
	public Object[] findRebatesSum(PolicyAllowPriftQuery policyAllowPriftQuery) {
		Criteria criteria = createPolicyAllowPriftQuery(policyAllowPriftQuery);
		criteria.add(Restrictions.ne("detail.orderDetailStateCode", AppConstants.POS_ORDER_DETAIL_STATE_REMOVE));
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections
						.sqlProjection(
								"sum(case when order_detail_state_code = 1 then order_detail_payment_money when order_detail_state_code = 4 then -order_detail_payment_money end) as money,"
										+ "sum(case when order_detail_state_code = 4 then -order_detail_discount when order_detail_state_code = 1 then order_detail_discount end) as discount, "
										+ "sum(case when order_detail_state_code = 4 then -order_detail_amount else order_detail_amount end) as amount",
								new String[] { "amount", "money", "discount" }, new Type[] {
										StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL,
										StandardBasicTypes.BIG_DECIMAL })));
		return (Object[]) criteria.uniqueResult();
	}

	@Override
	public List<Object[]> findOutOrderCountAndMoneyByDate(String systemBookCode, Integer centerBranchNum,
			Integer branchNum, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s , count(out_order_fid) as amount, sum(out_order_total_money) as money ");
		sb.append("from transfer_out_order with(nolock) where system_book_code = :systemBookCode and out_branch_num = :centerBranchNum ");
		sb.append("and branch_num = :branchNum and out_order_audit_time between :dateFrom and :dateTo and out_order_state_code = 3 ");
		sb.append("group by %s ");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(convert(varchar(12) , out_order_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_MONTH)) {
			sql = sql.replaceAll("%s", "convert(varchar(12) , out_order_audit_time, 112)");
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("centerBranchNum", centerBranchNum);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findReturnCountAndMoneyByDate(String systemBookCode, Integer centerBranchNum,
			Integer branchNum, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s , count(in_order_fid) as amount, sum(in_order_total_money) as money ");
		sb.append("from transfer_in_order with(nolock) where system_book_code = :systemBookCode and branch_num = :branchNum ");
		sb.append("and in_branch_num = :centerBranchNum and in_order_audit_time between :dateFrom and :dateTo and in_order_state_code = 3 ");
		sb.append("group by %s ");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(convert(varchar(12) , in_order_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_MONTH)) {
			sql = sql.replaceAll("%s", "convert(varchar(12) , in_order_audit_time, 112)");
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("centerBranchNum", centerBranchNum);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}

	@Override
	public Object[] findSalerSummary(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums,
			List<String> salerNums) {
		Criteria criteria = createBySeller(systemBookCode, dtFrom, dtTo, branchNums, salerNums);
		criteria.createAlias("p.posOrderDetails", "detail");
		criteria.add(Restrictions.ne("detail.orderDetailStateCode", AppConstants.POS_ORDER_DETAIL_STATE_REMOVE));
		criteria.add(Restrictions.isNotNull("detail.itemNum"));
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections
						.sqlProjection(
								"sum(case when order_detail_state_code = 4 then -order_detail_amount else order_detail_amount end) as amount,"
										+ "sum(case when order_detail_state_code = 1 then order_detail_payment_money when order_detail_state_code = 4 then -order_detail_payment_money end) as money, "
										+ "sum(case when order_detail_state_code = 1 then order_detail_commission end) as commission",
								new String[] { "amount", "money", "commission" }, new Type[] {
										StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL,
										StandardBasicTypes.BIG_DECIMAL })));
		return (Object[]) criteria.uniqueResult();
	}

	@Override
	public List<Object[]> findOutCategoryMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" select p.itemCategory, p.itemCategoryCode,");
		queryStr.append(" sum(detail.outOrderDetailSaleSubtotal) as money ");
		queryStr.append(" from TransferOutOrder as t inner join t.outOrderDetails as detail, PosItem as p ");
		queryStr.append(" where p.itemNum = detail.itemNum ");
		queryStr.append(" and t.outSystemBookCode = :systemBookCode");
		queryStr.append(" and t.outBranchNum in " + AppUtil.getIntegerParmeList(branchNums));
		queryStr.append(" and t.state.stateCode = 3 ");
		queryStr.append(" and t.outOrderDate between :dateFrom and :dateTo");
		queryStr.append(" group by p.itemCategory, p.itemCategoryCode");
		queryStr.append(" order by p.itemCategoryCode asc");

		Query query = currentSession().createQuery(queryStr.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}

	@Override
	public List<Object[]> findWholesaleCategoryMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" select p.itemCategory, p.itemCategoryCode,");
		queryStr.append(" sum(detail.orderDetailMoney) as money ");
		queryStr.append(" from WholesaleOrder as w inner join w.wholesaleOrderDetails as detail, PosItem as p ");
		queryStr.append(" where p.itemNum = detail.itemNum ");
		queryStr.append(" and w.systemBookCode = :systemBookCode");
		queryStr.append(" and w.branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		queryStr.append(" and w.state.stateCode = 3 ");
		queryStr.append(" and w.wholesaleOrderDate between :dateFrom and :dateTo");
		queryStr.append(" group by p.itemCategory, p.itemCategoryCode");
		queryStr.append(" order by p.itemCategoryCode asc");

		Query query = currentSession().createQuery(queryStr.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}

	@Override
	public List<Object[]> findWholesaleOrderCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			String clientFid, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s , count(wholesale_order_fid) as amount, sum(wholesale_order_total_money) as money ");
		sb.append("from wholesale_order with(nolock) where system_book_code = :systemBookCode and client_fid = :clientFid ");
		sb.append("and branch_num = :branchNum and wholesale_order_audit_time between :dateFrom and :dateTo and wholesale_order_state_code = 3 ");
		sb.append("group by %s ");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(convert(varchar(12) , wholesale_order_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_MONTH)) {
			sql = sql.replaceAll("%s", "convert(varchar(12) , wholesale_order_audit_time, 112)");
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setString("clientFid", clientFid);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findWholesaleReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			String clientFid, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s , count(wholesale_return_fid) as amount, sum(wholesale_return_total_money) as money ");
		sb.append("from wholesale_return with(nolock) where system_book_code = :systemBookCode and branch_num = :branchNum ");
		sb.append("and client_fid = :clientFid and wholesale_return_audit_time between :dateFrom and :dateTo and wholesale_return_state_code = 3 ");
		sb.append("group by %s ");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(convert(varchar(12) , wholesale_return_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_MONTH)) {
			sql = sql.replaceAll("%s", "convert(varchar(12) , wholesale_return_audit_time, 112)");
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setString("clientFid", clientFid);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findPosOrderMoneyByBizDay(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(order_no) as amount ");
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
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by %s order by %s asc");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(shift_table_bizday, 0, 7) ");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_MONTH)) {
			sql = sql.replaceAll("%s", "shift_table_bizday ");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_DAY)) {
			sql = sql.replaceAll("%s", "left(order_time_char, 2) ");
		}
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<Object[]> findPurchaseReceiveCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s , count(receive_order_fid) as amount, sum(receive_order_total_money) as money ");
		sb.append("from receive_order with(nolock) where system_book_code = :systemBookCode and branch_num = :branchNum ");
		sb.append("and supplier_num = :supplierNum and receive_order_audit_time between :dateFrom and :dateTo and receive_order_state_code = 3 ");
		sb.append("group by %s ");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(convert(varchar(12) , receive_order_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_MONTH)) {
			sql = sql.replaceAll("%s", "convert(varchar(12) , receive_order_audit_time, 112)");
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("supplierNum", supplierNum);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findPurchaseReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s , count(return_order_fid) as amount, sum(return_order_total_money) as money ");
		sb.append("from return_order with(nolock) where system_book_code = :systemBookCode and branch_num = :branchNum ");
		sb.append("and supplier_num = :supplierNum and return_order_audit_time between :dateFrom and :dateTo and return_order_state_code = 3 ");
		sb.append("group by %s ");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(convert(varchar(12) , return_order_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_MONTH)) {
			sql = sql.replaceAll("%s", "convert(varchar(12) , return_order_audit_time, 112)");
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("supplierNum", supplierNum);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findPurchaseCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s , count(purchase_order_fid) as amount, sum(purchase_order_total_money) as money ");
		sb.append("from purchase_order with(nolock) where system_book_code = :systemBookCode and branch_num = :branchNum ");
		sb.append("and supplier_num = :supplierNum and purchase_order_audit_time between :dateFrom and :dateTo and purchase_order_state_code = 3 ");
		sb.append("group by %s ");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(convert(varchar(12) , purchase_order_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_MONTH)) {
			sql = sql.replaceAll("%s", "convert(varchar(12) , purchase_order_audit_time, 112)");
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("supplierNum", supplierNum);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findCustomerConusmeByCardConsuemAnalysisQuery(
			CardConsuemAnalysisQuery cardConsuemAnalysisQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.payment_cust_num, sum(p.payment_money) ");
		sb.append("from payment as p with(nolock) ");
		sb.append("where p.system_book_code = '" + cardConsuemAnalysisQuery.getSystemBookCode() + "' ");
		if (cardConsuemAnalysisQuery.getBranchNums() != null && cardConsuemAnalysisQuery.getBranchNums().size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(cardConsuemAnalysisQuery.getBranchNums()));

		}
		sb.append("and p.shift_table_bizday between '"
				+ DateUtil.getDateShortStr(cardConsuemAnalysisQuery.getDateFrom()) + "' and '"
				+ DateUtil.getDateShortStr(cardConsuemAnalysisQuery.getDateTo()) + "' ");
		sb.append("and p.payment_pay_by = '储值卡' ");
		sb.append("and convert(varchar(8),p.payment_time,108) between '"
				+ DateUtil.getTimeStr(cardConsuemAnalysisQuery.getTimeFrom()) + "' and '"
				+ DateUtil.getTimeStr(cardConsuemAnalysisQuery.getTimeTo()) + "' ");
		sb.append("group by p.payment_cust_num ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public BigDecimal sumPosMoneyByCardConsuemAnalysisQuery(CardConsuemAnalysisQuery cardConsuemAnalysisQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(p.order_payment_money + p.order_coupon_total_money - p.order_mgr_discount_money) ");
		sb.append("from pos_order as p with(nolock) ");
		sb.append("where p.system_book_code = '" + cardConsuemAnalysisQuery.getSystemBookCode() + "' ");
		if (cardConsuemAnalysisQuery.getBranchNums() != null && cardConsuemAnalysisQuery.getBranchNums().size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(cardConsuemAnalysisQuery.getBranchNums()));

		}
		sb.append("and p.shift_table_bizday between '"
				+ DateUtil.getDateShortStr(cardConsuemAnalysisQuery.getDateFrom()) + "' and '"
				+ DateUtil.getDateShortStr(cardConsuemAnalysisQuery.getDateTo()) + "' ");
		sb.append("and p.order_state_code in (5, 7) ");
		sb.append("and convert(varchar(8),p.order_time,108) between '"
				+ DateUtil.getTimeStr(cardConsuemAnalysisQuery.getTimeFrom()) + "' and '"
				+ DateUtil.getTimeStr(cardConsuemAnalysisQuery.getTimeTo()) + "' ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		Object object = sqlQuery.uniqueResult();
		return object == null ? BigDecimal.ZERO : (BigDecimal) object;
	}

	@Override
	public List<Object[]> findCardConsumeAnalysisDetails(CardConsuemAnalysisQuery cardConsuemAnalysisQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.card_user_num, c.card_user_printed_num, c.card_user_cust_name, sum(p.payment_money) ");
		sb.append("from payment as p with(nolock) , card_user as c with(nolock) ");
		sb.append("where p.payment_cust_num = c.card_user_num and p.system_book_code = '"
				+ cardConsuemAnalysisQuery.getSystemBookCode() + "' ");
		if (cardConsuemAnalysisQuery.getBranchNums() != null && cardConsuemAnalysisQuery.getBranchNums().size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(cardConsuemAnalysisQuery.getBranchNums()));

		}
		sb.append("and p.shift_table_bizday between '"
				+ DateUtil.getDateShortStr(cardConsuemAnalysisQuery.getDateFrom()) + "' and '"
				+ DateUtil.getDateShortStr(cardConsuemAnalysisQuery.getDateTo()) + "' ");
		sb.append("and convert(varchar(8),p.payment_time,108) between '"
				+ DateUtil.getTimeStr(cardConsuemAnalysisQuery.getTimeFrom()) + "' and '"
				+ DateUtil.getTimeStr(cardConsuemAnalysisQuery.getTimeTo()) + "'");
		sb.append("and p.payment_pay_by = '储值卡' ");
		sb.append("group by c.card_user_num, c.card_user_printed_num, c.card_user_cust_name having sum(p.payment_money) >= "
				+ cardConsuemAnalysisQuery.getMoneyFrom() + " ");
		if (cardConsuemAnalysisQuery.getMoneyTo() != null) {
			sb.append(" and sum(p.payment_money) < " + cardConsuemAnalysisQuery.getMoneyTo());
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<PosItemRank> findPosItemRanks(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.itemNum, ");
		sb.append("sum(case when detail.orderDetailStateCode = 4 then -detail.orderDetailAmount else detail.orderDetailAmount end) as mount, ");
		sb.append("sum(case when detail.orderDetailStateCode = 1 then orderDetailPaymentMoney when detail.orderDetailStateCode = 4 then -detail.orderDetailPaymentMoney end) as money ");
		sb.append("from PosOrderDetail as detail ");
		sb.append("where detail.orderDetailBookCode = :systemBookCode and detail.orderDetailBranchNum = :branchNum  ");
		sb.append("and detail.orderDetailBizday between :bizFrom and :bizTo ");
		sb.append("and detail.orderDetailOrderState in (5, 7) and detail.itemNum is not null ");
		sb.append("and detail.orderDetailOrderState != 8 ");
		sb.append("group by detail.itemNum order by mount desc ");
		Query query = currentSession().createQuery(sb.toString());
		query.setFirstResult(0);
		query.setMaxResults(100);
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));

		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));

		}
		
		List<PosItemRank> posItemRanks = new ArrayList<PosItemRank>();
		List<Object[]> objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			PosItemRank posItemRank = new PosItemRank();
			posItemRank.setItemNum((Integer) object[0]);
			posItemRank.setOutOrderDetailUseQty((BigDecimal) object[1]);
			posItemRank.setOutOrderDetailSubtotal((BigDecimal) object[2]);
			posItemRanks.add(posItemRank);
		}
		return posItemRanks;
	}

	@Override
	public List<Object[]> findPosGroupByBranchRegionType(String systemBookCode, Integer branchNum, Date dateFrom,
			Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.branch_region_type, sum(p.order_payment_money + p.order_coupon_total_money - p.order_mgr_discount_money) as money, count(p.order_no) as amount ");
		sb.append("from pos_order as p with(nolock) inner join branch as b with(nolock) on p.system_book_code = b.system_book_code and p.branch_num = b.branch_num ");
		sb.append("and p.system_book_code = '" + systemBookCode + "' ");
		if (branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			branchNum = null;
		}
		if (branchNum != null) {
			sb.append("and p.branch_num = " + branchNum + " ");
		}
		if (dateFrom != null) {
			sb.append("and p.shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and p.shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and p.order_state_code in (5, 7) group by b.branch_region_type");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findPosGroupByHourAndBranchRegionType(String systemBookCode, Integer branchNum,
			Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select cast(substring(order_time_char, 0, 3) as int), b.branch_region_type, sum(p.order_payment_money + p.order_coupon_total_money - p.order_mgr_discount_money) as money, count(order_no) as amount ");
		sb.append("from pos_order as p with(nolock) inner join branch as b with(nolock) on p.system_book_code = b.system_book_code and p.branch_num = b.branch_num where p.system_book_code = '"
				+ systemBookCode + "' ");
		if (branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			branchNum = null;
		}
		if (branchNum != null) {
			sb.append("and p.branch_num = " + branchNum + " ");
		}
		if (dateFrom != null) {
			sb.append("and p.shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and p.shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and p.order_state_code in (5, 7) group by cast(substring(order_time_char, 0, 3) as int), b.branch_region_type order by cast(substring(order_time_char, 0, 3) as int) ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findPosGroupByHour(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();/////
		sb.append("select datepart(hh,p.order_time), sum(p.order_payment_money + p.order_coupon_total_money - p.order_mgr_discount_money) as money, count(order_no) as amount ");
		sb.append("from pos_order as p with(nolock) where p.system_book_code = '" + systemBookCode + "' ");
		if (branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			branchNum = null;
		}
		if (branchNum != null) {
			sb.append("and p.branch_num = " + branchNum + " ");
		}
		if (dateFrom != null) {
			sb.append("and p.shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and p.shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and p.order_state_code in (5, 7) group by datepart(hh,p.order_time)  order by datepart(hh,p.order_time)");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findSummaryGroupByItem(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, List<Integer> itemNums, boolean kitFlag) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.order_detail_item_matrix_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as gross, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_assist_amount else detail.order_detail_assist_amount end) as assistAmount ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode and detail.order_detail_bizday between :bizFrom and :bizTo ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and detail.order_detail_state_code != 8 ");
		if (kitFlag) {
			sb.append("and (detail.order_detail_has_kit is null or detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.item_num, detail.order_detail_item_matrix_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		return query.list();
	}

	@Override
	public List<Object[]> findSummaryGroupByKitItem(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select item_num, order_kit_detail_item_matrix_num, ");
		sb.append("sum(case when order_kit_detail_state_code = 8 then 0.00 when order_kit_detail_state_code = 4 then -order_kit_detail_gross_profit else order_kit_detail_gross_profit end) as gross, ");
		sb.append("sum(case when order_kit_detail_state_code = 8 then 0.00 when order_kit_detail_state_code = 4 then -order_kit_detail_amount else order_kit_detail_amount end) as amount, ");
		sb.append("sum(case when order_kit_detail_state_code = 8 then 0.00 when order_kit_detail_state_code = 4 then -order_kit_detail_payment_money when order_kit_detail_state_code = 1 then order_kit_detail_payment_money end) as money, ");
		sb.append("sum(case when order_kit_detail_state_code = 8 then 0.00 when order_kit_detail_state_code = 4 then (-order_kit_detail_amount * order_kit_detail_cost) else (order_kit_detail_amount * order_kit_detail_cost) end) as cost ");
		sb.append("from pos_order_kit_detail with(nolock) ");
		sb.append("where order_kit_detail_book_code = :systemBookCode and order_kit_detail_bizday between :bizFrom and :bizTo ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and order_kit_detail_order_state in (5, 7) and item_num is not null ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by item_num, order_kit_detail_item_matrix_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		return query.list();
	}

	@Override
	public List<Object[]> findWholesaleOrderLostByClientAndItem(String systemBookCode, Integer branchNum,
			Date dateFrom, Date dateTo, List<String> clientFids, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select book.client_fid as fid, detail.item_num as itemNum, count(detail.wholesale_book_fid) as fidCount, max(book.wholesale_book_audit_time) as maxBookTime ");
		sb.append("from wholesale_book_detail as detail with(nolock) inner join wholesale_book as book with(nolock) on detail.wholesale_book_fid = book.wholesale_book_fid ");
		sb.append("left join wholesale_order as o with(nolock) on o.wholesale_book_fid = book.wholesale_book_fid ");
		sb.append("where book.system_book_code = '" + systemBookCode + "' ");
		if (branchNum != null) {
			sb.append("and book.branch_num = " + branchNum + " ");
		}
		if (dateFrom != null) {
			sb.append("and book.wholesale_book_audit_time >= '"
					+ DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if (dateTo != null) {
			sb.append("and book.wholesale_book_audit_time <= '"
					+ DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		sb.append("and book.wholesale_book_state_code = 3 ");
		if (clientFids != null && clientFids.size() > 0) {
			sb.append("and book.client_fid in " + AppUtil.getStringParmeList(clientFids));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and detail.book_detail_stockout_tag = 1 group by book.client_fid, detail.item_num");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addScalar("fid", StandardBasicTypes.STRING).addScalar("itemNum", StandardBasicTypes.INTEGER)
				.addScalar("fidCount", StandardBasicTypes.INTEGER)
				.addScalar("maxBookTime", StandardBasicTypes.TIMESTAMP);
		return query.list();
	}

	@Override
	public List<Object[]> findProfitAnalysisBranchs(ProfitAnalysisQueryData profitAnalysisQueryData) {
		StringBuffer sb = new StringBuffer();
		boolean queryPosItem = false;
		if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
				|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
				.getPosItemTypeCodes().size() > 0)) {
			queryPosItem = true;
		}
		if (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0) {
			
			sb.append("select p.branch_num, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
			if(queryPosItem){
				sb.append("inner join pos_item as item on item.item_num = detail.item_num ");
			}
			sb.append("where p.system_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums())
						+ " ");
			}
			sb.append("and p.shift_table_bizday between :bizFrom and :bizTo ");
			sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums())
						+ " ");
			}
			if (queryPosItem) {
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
			}		
			sb.append("and p.client_fid in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getClientFids()) + " ");			
			if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by p.branch_num");
		} else {
			sb.append("select detail.order_detail_branch_num, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			if(queryPosItem){
				sb.append("inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");
			}
			sb.append("where detail.order_detail_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums())
						+ " ");
			}
			sb.append("and detail.order_detail_bizday between :bizFrom and :bizTo ");
			sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums())
						+ " ");
			}
			if(queryPosItem){
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
			}		
			
			if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			if (profitAnalysisQueryData.getIsQueryCF()) {
				sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
			}
			sb.append("group by detail.order_detail_branch_num");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
		query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
		query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
		List<Object[]> objects = query.list();
		
		if(profitAnalysisQueryData.getIsQueryCF()){
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then (-detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as cost ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
			}
			sb.append("and detail.order_kit_detail_bizday between :bizFrom and :bizTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_kit_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
					|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
							.getPosItemTypeCodes().size() > 0)) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
				sb.append(") ");
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by detail.order_kit_detail_branch_num ");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
			query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
			query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
			objects.addAll(query.list());
		}
		return objects;
	}

	@Override
	public List<ElecTicketDTO> findElecTicketDTOs(ElecTicketQueryDTO elecTicketQueryDTO) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select p.branch_num, p.order_time, p.order_operator, p.order_no, (p.order_payment_money + p.order_coupon_total_money - p.order_mgr_discount_money), ");
		sb.append("detail.order_detail_item, detail.order_detail_ticket_uuid, detail.order_detail_amount, detail.order_detail_money ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
		sb.append("where p.system_book_code = :systemBookCode ");

		if (StringUtils.isNotEmpty(elecTicketQueryDTO.getOrderNo())) {
			sb.append("and p.order_no = :orderNo ");
		} else if (StringUtils.isNotEmpty(elecTicketQueryDTO.getTicketNo())) {
			sb.append("and detail.order_detail_ticket_uuid = :ticketNo ");
		} else {
			if (elecTicketQueryDTO.getDtFrom() != null) {
				sb.append("and p.shift_table_bizday >= :bizFrom ");
			}
			if (elecTicketQueryDTO.getDtTo() != null) {
				sb.append("and p.shift_table_bizday <= :bizTo ");
			}
			if (elecTicketQueryDTO.getBranchNums() != null && elecTicketQueryDTO.getBranchNums().size() > 0) {
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(elecTicketQueryDTO.getBranchNums()));
			}
		}

		if (StringUtils.isNotEmpty(elecTicketQueryDTO.getUserId())) {
			sb.append("and p.order_operator = :operator ");
		}
		if (StringUtils.isNotEmpty(elecTicketQueryDTO.getCouponType())) {
			sb.append("and detail.order_detail_item in (:couponTypes) ");
		}
		
		sb.append("and p.order_state_code in (5, 7) and detail.item_num is null and detail.order_detail_state_code = 1 ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", elecTicketQueryDTO.getSystemBookCode());
		if (StringUtils.isNotEmpty(elecTicketQueryDTO.getUserId())) {
			query.setString("operator", elecTicketQueryDTO.getUserId());
		}
		if (StringUtils.isNotEmpty(elecTicketQueryDTO.getCouponType())) {
			query.setParameterList("couponTypes", elecTicketQueryDTO.getCouponType().split(","));
		}
		
		if (StringUtils.isNotEmpty(elecTicketQueryDTO.getOrderNo())) {
			query.setString("orderNo", elecTicketQueryDTO.getOrderNo());
		} else if (StringUtils.isNotEmpty(elecTicketQueryDTO.getTicketNo())) {
			query.setString("ticketNo", elecTicketQueryDTO.getTicketNo());
		} else {
			if (elecTicketQueryDTO.getDtFrom() != null) {
				query.setString("bizFrom", DateUtil.getDateShortStr(elecTicketQueryDTO.getDtFrom()));
			}
			if (elecTicketQueryDTO.getDtTo() != null) {
				query.setString("bizTo", DateUtil.getDateShortStr(elecTicketQueryDTO.getDtTo()));
			}
		}
		List<Object[]> objects = query.list();
		List<ElecTicketDTO> list = new ArrayList<ElecTicketDTO>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);

			ElecTicketDTO elecTicketDTO = new ElecTicketDTO();
			elecTicketDTO.setBranchNum((Integer) object[0]);
			elecTicketDTO.setPaymentDate((Date) object[1]);
			elecTicketDTO.setShopUser((String) object[2]);
			elecTicketDTO.setOrderNo((String) object[3]);
			elecTicketDTO.setBillMoney((BigDecimal) object[4]);
			elecTicketDTO.setCouponType(object[5] == null?"":(String) object[5]);
			elecTicketDTO.setTicketNos((String) object[6]);
			elecTicketDTO.setTicketUseNum(((BigDecimal) object[7]).intValue());
			elecTicketDTO.setCouponMoney((BigDecimal) object[8]);
			//只显示电子券
			if(StringUtils.contains(elecTicketDTO.getCouponType(), AppConstants.TEAM_TYPE_MEITUAN) 
					|| StringUtils.contains(elecTicketDTO.getCouponType(), AppConstants.TEAM_TYPE_DIANPING)
					|| StringUtils.contains(elecTicketDTO.getCouponType(), AppConstants.TEAM_TYPE_NUOMI)){
				
				list.add(elecTicketDTO);
			}
		}
		return list;
	}

	@Override
	public List<AlipayDetailDTO> findAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                      Date dateTo, String paymentTypes) {
		Criteria criteria = currentSession().createCriteria(Payment.class, "p").add(
				Restrictions.eq("p.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {

			criteria.add(Restrictions.in("p.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("p.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("p.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}

		criteria.add(Restrictions.in("p.paymentPayBy", paymentTypes.split(",")));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("p.paymentBillNo"))
				.add(Projections.property("p.orderNo"))
				.add(Projections.property("p.branchNum"))
				.add(Projections.property("p.paymentTime"))
				.add(Projections.property("p.paymentAcctNo"))
				.add(Projections.property("p.paymentMoney"))
				.add(Projections.property("p.paymentReceiptMoney"))
				.add(Projections.property("p.paymentBuyerMoney"))
				.add(Projections.property("p.paymentReceive"))
				);
		criteria.setMaxResults(10000);
		List<Object[]> objects = criteria.list();
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
			buyerMoney = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			alipayDetailDTO.setAlipayDiscountMoney(alipayDetailDTO.getConsumeSuccessActualMoney().subtract(buyerMoney));
			alipayDetailDTO.setBranchDiscountMoney((object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8]).subtract(alipayDetailDTO.getConsumeSuccessActualMoney()));
			alipayDetailDTO.setType("POS消费");
			list.add(alipayDetailDTO);
		}
		return list;
	}

	@Override
	public List<Object[]> findCustomerAnalysisShiftTables(String systemBookCode, Date dateFrom, Date dateTo,
			List<Integer> branchNums, String appUserName, String saleType) {

		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num as branchNum, shift_table_bizday as bizday, shift_table_num as biznum,  ");
		sb.append("sum(order_payment_money) as paymentMoney, count(order_no) as orderNo, ");
		sb.append("sum(order_coupon_total_money) as conponMoney, sum(order_mgr_discount_money) as mgrDiscount, ");
		sb.append("count(case when order_card_user_num > 0 then order_no end) as userAmount, ");
		sb.append("sum(case when order_card_user_num > 0 then (order_payment_money - order_mgr_discount_money + order_coupon_total_money) end) as userMoney ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		if (StringUtils.isNotEmpty(appUserName)) {
			sb.append("and order_operator = '" + appUserName + "' ");
		}
		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		sb.append("group by branch_num, shift_table_bizday,shift_table_num ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addScalar("branchNum", StandardBasicTypes.INTEGER)
			.addScalar("bizday", StandardBasicTypes.STRING)
			.addScalar("biznum", StandardBasicTypes.INTEGER)
			.addScalar("paymentMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("orderNo", StandardBasicTypes.LONG)
			.addScalar("conponMoney", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("mgrDiscount", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("userAmount", StandardBasicTypes.INTEGER)
			.addScalar("userMoney", StandardBasicTypes.BIG_DECIMAL)
			;
		return query.list();
	}

	
	@Override
	public List<Object[]> findCustomerAnalysisTimePeriodsByItems(String systemBookCode, Date dateFrom, Date dateTo,
			List<Integer> branchNums, Integer space, List<Integer> itemNums, String saleType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select max(p.order_time) as maxTime, sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("count(distinct detail.order_no) as amount ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
		sb.append("where p.system_book_code = :systemBookCode and p.shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and p.order_state_code in (5,7) ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		sb.append(" group by ((DATEDIFF(mi, '00:00:00',convert(varchar(8),p.order_time,108)))/(:gap) )");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("gap", space);
		List<Object[]> objects = sqlQuery.list();
		return objects;
	}

	@Override
	public List<Object[]> findReportDTOs(ReportDTO queryReportDTO, CustomReport customReport) {
		StringBuffer sb = new StringBuffer();
		List<String> conditionsKeys = new ArrayList<String>(queryReportDTO.getMap().keySet());
		
		String[] sqlArray = customReport.getCustomReportContext().split("\n");
		boolean conditionFlag = false;
		String condition = null;
		for(int i = 0;i < sqlArray.length;i++){
			String sql = sqlArray[i];
			
			if(!conditionFlag){
				sb.append(sql);
			} else {
				for(int j = 0;j < conditionsKeys.size();j++){
					condition = conditionsKeys.get(j);
					if(sql.contains(":" + condition)){
						sb.append(sql);
						break;
					}
				}
			}
			if(sql.equals("where")){
				conditionFlag = true;
			}
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", ((String)queryReportDTO.get("systemBookCode")));
		if(conditionsKeys.contains("branchNums")){
			sqlQuery.setParameterList("branchNums", ((List<String>)queryReportDTO.get("branchNums")));
		}
		if(conditionsKeys.contains("dateFrom")){
			sqlQuery.setParameter("dateFrom", ((Date)queryReportDTO.get("dateFrom")));
			sqlQuery.setParameter("dateTo", ((Date)queryReportDTO.get("dateTo")));
		}
		if(conditionsKeys.contains("bizFrom")){
			sqlQuery.setString("bizFrom", ((String)queryReportDTO.get("bizFrom")));
			sqlQuery.setString("bizTo", ((String)queryReportDTO.get("bizTo")));
		}
		return sqlQuery.list();
	}

	@Override
	public List<TicketUseAnalysisDTO> findTicketUseAnalysisDTOs(ElecTicketQueryDTO elecTicketQueryDTO) {

		StringBuffer sb = new StringBuffer();
		sb.append("select a.branchNum, count(a.orderNo) as orderAmount, sum(a.ticketMoney) as ticketMoney_, ");
		sb.append("sum(case when (a.paymentMoney - a.discountMoney) > 0 then 1 else 0 end) as extraOrderAmount, ");
		sb.append("sum(a.paymentMoney - a.discountMoney) as extraOrderMoney,sum(a.ticketAmount) as ticketAmount_ ");
		sb.append("from ");		
			sb.append("(select p.branch_num as branchNum, p.order_no as orderNo, p.order_coupon_total_money as ticketMoney,  ");
			sb.append("p.order_payment_money as paymentMoney, p.order_mgr_discount_money as discountMoney, sum(detail.order_detail_amount) as ticketAmount ");
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
			sb.append("where p.system_book_code = :systemBookCode ");
			if(elecTicketQueryDTO.getBranchNums() != null && elecTicketQueryDTO.getBranchNums().size() > 0){
				sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(elecTicketQueryDTO.getBranchNums()));
			}
			if(elecTicketQueryDTO.getDtFrom() != null){
				sb.append("and p.shift_table_bizday >= :bizFrom ");
			}
			if(elecTicketQueryDTO.getDtTo() != null){
				sb.append("and p.shift_table_bizday <= :bizTo ");
			}
			if(StringUtils.isNotEmpty(elecTicketQueryDTO.getCouponType())){
				sb.append("and detail.order_detail_item in " + AppUtil.getStringParmeArray(elecTicketQueryDTO.getCouponType().split(",")));
			}
			sb.append("and p.order_state_code in (5, 7) and detail.item_num is null ");
			sb.append("group by p.branch_num, p.order_no, p.order_coupon_total_money, ");
			sb.append("p.order_payment_money, p.order_mgr_discount_money ");
		sb.append(") as a group by a.branchNum ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", elecTicketQueryDTO.getSystemBookCode());
		if(elecTicketQueryDTO.getDtFrom() != null){
			query.setString("bizFrom", DateUtil.getDateShortStr(elecTicketQueryDTO.getDtFrom()));
		}
		if(elecTicketQueryDTO.getDtTo() != null){
			query.setString("bizTo", DateUtil.getDateShortStr(elecTicketQueryDTO.getDtTo()));
		}
		List<TicketUseAnalysisDTO> list = new ArrayList<TicketUseAnalysisDTO>();
		List<Object[]> objects = query.list();
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			Integer branchNum = (Integer)object[0];
			TicketUseAnalysisDTO ticketUseAnalysisDTO = new TicketUseAnalysisDTO();
			ticketUseAnalysisDTO.setBranchNum(branchNum);						
			ticketUseAnalysisDTO.setUseTicketOrders((Integer)object[1]);
			ticketUseAnalysisDTO.setUseTicketMoney((BigDecimal)object[2]);
			ticketUseAnalysisDTO.setPushOrders((Integer)object[3]);
			ticketUseAnalysisDTO.setPushMoney((BigDecimal)object[4]);
			ticketUseAnalysisDTO.setOrderTotalMoney(ticketUseAnalysisDTO.getPushMoney().add(ticketUseAnalysisDTO.getUseTicketMoney()));
			ticketUseAnalysisDTO.setUseTicketQty(((BigDecimal)object[5]).intValue());
			if(ticketUseAnalysisDTO.getUseTicketOrders() > 0){
				ticketUseAnalysisDTO.setPushRate(BigDecimal.valueOf(ticketUseAnalysisDTO.getPushOrders())
						.divide(BigDecimal.valueOf(ticketUseAnalysisDTO.getUseTicketOrders()), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
			if(ticketUseAnalysisDTO.getOrderTotalMoney().compareTo(BigDecimal.ZERO) > 0){
				ticketUseAnalysisDTO.setPushMoneyRate(ticketUseAnalysisDTO.getPushMoney()
						.divide(ticketUseAnalysisDTO.getOrderTotalMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
			list.add(ticketUseAnalysisDTO);
		}
		return list;
	}

	@Override
	public List<PackageSumDTO> findPackageSum(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select item_num, package_log_type, package_log_source, sum(package_log_amount) as amount, sum(package_log_money) as money, ");
		sb.append("sum(package_log_inout_amount) as inoutAmount, sum(package_log_inout_money) as inoutMoney ");
		sb.append("from package_log with(nolock) where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and package_log_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and package_log_audit_time <= :dateTo ");
		}
		sb.append("and package_log_state_code = 3 group by item_num, package_log_type, package_log_source ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		Map<Integer, PackageSumDTO> map = new HashMap<Integer, PackageSumDTO>();
		Integer itemNum = null;
		String type = null;
		String source = null;
		BigDecimal amount = null;
		BigDecimal money = null;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			itemNum = (Integer)object[0];
			type = (String)object[1];
			source = (String)object[2];
			amount = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
			money = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];

			PackageSumDTO packageSumDTO = map.get(itemNum);
			if(packageSumDTO == null){
				packageSumDTO = new PackageSumDTO();
				packageSumDTO.setItemNum(itemNum);
				map.put(itemNum, packageSumDTO);
			}
			if(type.equals(AppConstants.POS_ITEM_LOG_IN_ORDER)){
				packageSumDTO.setPackageOutAmount(packageSumDTO.getPackageOutAmount().add(amount));
				packageSumDTO.setPackageOutMoney(packageSumDTO.getPackageOutMoney().add(money));
			} else if(type.equals(AppConstants.POS_ITEM_LOG_OUT_ORDER)){
				packageSumDTO.setPackageInAmount(packageSumDTO.getPackageInAmount().add(amount));
				packageSumDTO.setPackageInMoney(packageSumDTO.getPackageInMoney().add(money));
			
				if(StringUtils.equals(source, AppConstants.PACKAGE_LOG_SOURCE_SUPPLIER)){
					
					packageSumDTO.setSupplierPackageInAmount(packageSumDTO.getSupplierPackageInAmount().add(amount));
					packageSumDTO.setSupplierPackageInMoney(packageSumDTO.getSupplierPackageInMoney().add(money));
					
				} else 	if(StringUtils.equals(source, AppConstants.PACKAGE_LOG_SOURCE_CENTER)){
					packageSumDTO.setCenterPackageInAmount(packageSumDTO.getCenterPackageInAmount().add(amount));
					packageSumDTO.setCenterPackageInMoney(packageSumDTO.getCenterPackageInMoney().add(money));
				}
			
			} 
			if(type.equals(AppConstants.POS_ITEM_LOG_CHECKORDER)){
				packageSumDTO.setPackageInoutAmount(packageSumDTO.getPackageInoutAmount().add(object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5]));
				packageSumDTO.setPackageInoutMoney(packageSumDTO.getPackageInoutMoney().add(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]));
			}
			
		}
		List<PackageSumDTO> list = new ArrayList<PackageSumDTO>(map.values());
		return list;
	}

	@Override
	public List<IntChart> findItemRelatedItemRanks(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, Integer itemNum, Integer selectCount) {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_detail_item, count(order_no) as orderNo, ");
		sb.append("sum(case when order_detail_state_code = 4 then -order_detail_amount else order_detail_amount end) as amount, ");
		sb.append("sum(case when order_detail_state_code = 4 then -order_detail_payment_money when order_detail_state_code = 1 then order_detail_payment_money end) as money ");
		sb.append("from pos_order_detail with(nolock) ");
		sb.append("where order_no in (");
			sb.append("select order_no from pos_order_detail with(nolock) where order_detail_book_code = :systemBookCode ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and order_detail_bizday between '" + DateUtil.getDateShortStr(dateFrom)
					+ "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");
			sb.append("and order_detail_order_state in (5, 7) and item_num = :itemNum ");
		sb.append(") and item_num != :itemNum and item_num is not null ");
		sb.append("and order_detail_state_code != 8 ");
		sb.append("group by order_detail_item  ");
		sb.append("order by count(order_no) desc ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("itemNum", itemNum);
		if(selectCount != null){
			query.setMaxResults(selectCount);
		}
		List<Object[]> objects = query.list();
		List<IntChart> intCharts = new ArrayList<IntChart>();
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			IntChart intChart = new IntChart();
			intChart.setName((String)object[0]);
			intChart.setIntValue((Integer)object[1]);
			intChart.setBigDecimalValue(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
			intChart.setBigDecimalValue2(object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3]);
			intCharts.add(intChart);
		}
		return intCharts;
	}

	@Override
	public List<CenterBoxReportDTO> findCenterBoxReportDTOs(String systemBookCode, Integer branchNum, Date dateFrom,
			Date dateTo) {
		List<CenterBoxReportDTO> centerBoxReportDTOs = new ArrayList<CenterBoxReportDTO>();
		List<String> packageLogTypes = new ArrayList<String>();
		packageLogTypes.add(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER);
		packageLogTypes.add(AppConstants.POS_ITEM_LOG_RETURN_ORDER);

		StringBuffer sb = new StringBuffer();
		sb.append("select package_log_type, package_log_price, sum(package_log_amount) as amount, sum(package_log_money) as money ");
		sb.append("from package_log with(nolock) where system_book_code = :systemBookCode and branch_num = :branchNum ");
		if(dateFrom != null){
			sb.append("and package_log_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and package_log_time <= :dateTo ");
		}
		sb.append("and package_log_type in " + AppUtil.getStringParmeList(packageLogTypes));
		sb.append("group by package_log_type, package_log_price ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		String type = null;
		BigDecimal price = null;
		BigDecimal amount = null;
		BigDecimal money = null;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			type = (String)object[0];
			price = (BigDecimal)object[1];
			amount = (BigDecimal)object[2];
			money = (BigDecimal)object[3];
			
			CenterBoxReportDTO centerBoxReportDTO = CenterBoxReportDTO.getCenterBoxReportDTO(centerBoxReportDTOs, price);
			if(centerBoxReportDTO == null){
				centerBoxReportDTO = new CenterBoxReportDTO();
				centerBoxReportDTO.setBoxPrice(price);
				centerBoxReportDTOs.add(centerBoxReportDTO);
			}
			if(type.equals(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER)){
				centerBoxReportDTO.setPurchaseBoxQty(amount);
				centerBoxReportDTO.setPurchaseBoxMoney(money);
			} else if(type.equals(AppConstants.POS_ITEM_LOG_RETURN_ORDER)){
				centerBoxReportDTO.setReturnBoxQty(amount);
				centerBoxReportDTO.setReturnBoxMoney(money);
			}
		}
		
		sb = new StringBuffer();
		sb.append("select package_inventory_price, sum(package_inventory_amount) as amount, sum(package_inventory_money) as money ");
		sb.append("from package_inventory with(nolock) where system_book_code = :systemBookCode and branch_num = :branchNum ");	
		sb.append("and item_num is null ");
		sb.append("group by package_inventory_price ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		objects = query.list();
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			price = (BigDecimal)object[0];
			amount = (BigDecimal)object[1];
			money = (BigDecimal)object[2];
			
			CenterBoxReportDTO centerBoxReportDTO = CenterBoxReportDTO.getCenterBoxReportDTO(centerBoxReportDTOs, price);
			if(centerBoxReportDTO == null){
				centerBoxReportDTO = new CenterBoxReportDTO();
				centerBoxReportDTO.setBoxPrice(price);
				centerBoxReportDTOs.add(centerBoxReportDTO);
			}		
			centerBoxReportDTO.setInventoryBoxQty(amount);
			centerBoxReportDTO.setInventoryBoxMoney(money);
			
		}
		return centerBoxReportDTOs;
	}

	@Override
	public List<Object[]> findSaleAnalysisByBranchBizday(SaleAnalysisQueryData saleAnalysisQueryData) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.order_detail_bizday, detail.order_detail_state_code, ");
		sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
		sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_ ");
		sb.append("from pos_order_detail as detail with(nolock) %s ");
		if(saleAnalysisQueryData.getIsQueryCardUser()){
			sb.append("inner join pos_order as p on p.order_no = detail.order_no ");
		}
		sb.append("where detail.order_detail_book_code = '"
				+ saleAnalysisQueryData.getSystemBookCode() + "' ");
		if (saleAnalysisQueryData.getBranchNums() != null && saleAnalysisQueryData.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(saleAnalysisQueryData.getBranchNums()));
		}
		sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtFrom())
				+ "' and '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtTo()) + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
		
		if (saleAnalysisQueryData.getPosItemNums() != null && saleAnalysisQueryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(saleAnalysisQueryData.getPosItemNums()));
		}
		boolean queryPosItem = false;
		if (saleAnalysisQueryData.getPosItemTypeCodes() != null && saleAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
			sb.append("and item.item_category_code in " + AppUtil.getStringParmeList(saleAnalysisQueryData.getPosItemTypeCodes()));
			queryPosItem = true;
		}
		if (saleAnalysisQueryData.getBrandCodes() != null && saleAnalysisQueryData.getBrandCodes().size() > 0) {
			sb.append("and item.item_brand in " + AppUtil.getStringParmeList(saleAnalysisQueryData.getBrandCodes()));
			queryPosItem = true;
		}
		if (StringUtils.isNotEmpty(saleAnalysisQueryData.getItemDepartments())){
			sb.append("and item.item_department in " + AppUtil.getStringParmeArray(saleAnalysisQueryData.getItemDepartments().split(",")));
			queryPosItem = true;
		}
		if (saleAnalysisQueryData.getIsQueryCF() != null && saleAnalysisQueryData.getIsQueryCF()) {
			sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
		}
		if (StringUtils.isNotEmpty(saleAnalysisQueryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		if (saleAnalysisQueryData.getOrderSources() != null && saleAnalysisQueryData.getOrderSources().size() > 0) {
			sb.append("and detail.order_source in " + AppUtil.getStringParmeList(saleAnalysisQueryData.getOrderSources()));
		}
		if(saleAnalysisQueryData.getIsQueryCardUser()){
			sb.append("and p.order_card_user_num > 0 ");
		}
		if(saleAnalysisQueryData.getTwoStringValueDatas() != null && saleAnalysisQueryData.getTwoStringValueDatas().size() > 0){
			sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
			for(int i = 0;i < saleAnalysisQueryData.getTwoStringValueDatas().size();i++){
				TwoStringValueData twoStringValueData = saleAnalysisQueryData.getTwoStringValueDatas().get(i);
				if(i > 0){
					sb.append(" or ");
				}
				sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
			}
			
			sb.append(")) ");
		}
		sb.append("group by detail.order_detail_branch_num, detail.order_detail_bizday, detail.order_detail_state_code ");
		
		//优化 不根据类别、部门、品牌查询的时候不关联pos_item表
		String sql = sb.toString();
		if(queryPosItem){
			sql = StringUtils.replace(sql, "%s", "inner join pos_item as item with(nolock) on item.item_num = detail.item_num ");
		} else {
			sql = StringUtils.replace(sql, "%s", "");
		}		
		Query query = currentSession().createSQLQuery(sql);
		List<Object[]> objects = query.list();
		if (saleAnalysisQueryData.getIsQueryCF() != null && saleAnalysisQueryData.getIsQueryCF()) {
			sb = new StringBuffer();
			sb.append("select kitDetail.order_kit_detail_branch_num, kitDetail.order_kit_detail_bizday, kitDetail.order_kit_detail_state_code, ");
			sb.append("sum(kitDetail.order_kit_detail_amount) as amount, sum(kitDetail.order_kit_detail_payment_money) as money, ");
			sb.append("sum(0.00) as assistAmount, count(kitDetail.item_num) as amount_ ");
			sb.append("from pos_order_kit_detail as kitDetail with(nolock) %s ");
			sb.append("where kitDetail.order_kit_detail_book_code = '"
					+ saleAnalysisQueryData.getSystemBookCode() + "' ");
			if (saleAnalysisQueryData.getBranchNums() != null && saleAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and kitDetail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(saleAnalysisQueryData.getBranchNums()));
			}
			sb.append("and kitDetail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtTo()) + "' ");
			sb.append("and kitDetail.order_kit_detail_order_state in (5, 7) ");
			if (saleAnalysisQueryData.getPosItemNums() != null && saleAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and kitDetail.item_num in " + AppUtil.getIntegerParmeList(saleAnalysisQueryData.getPosItemNums()));
			}
			if (saleAnalysisQueryData.getPosItemTypeCodes() != null && saleAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
				sb.append("and item.item_category_code in "
						+ AppUtil.getStringParmeList(saleAnalysisQueryData.getPosItemTypeCodes()));
			}
			if (saleAnalysisQueryData.getBrandCodes() != null && saleAnalysisQueryData.getBrandCodes().size() > 0) {
				sb.append("and item.item_brand in " + AppUtil.getStringParmeList(saleAnalysisQueryData.getBrandCodes()));
			}
			if (StringUtils.isNotEmpty(saleAnalysisQueryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(saleAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
					
					sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (kitDetail.order_source is null or kitDetail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (saleAnalysisQueryData.getOrderSources() != null && saleAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(saleAnalysisQueryData.getOrderSources()));
			}
			if(saleAnalysisQueryData.getTwoStringValueDatas() != null && saleAnalysisQueryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = kitDetail.item_num and (");
				for(int i = 0;i < saleAnalysisQueryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = saleAnalysisQueryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by kitDetail.order_kit_detail_branch_num, kitDetail.order_kit_detail_bizday, kitDetail.order_kit_detail_state_code ");
			
			//优化 不根据类别、部门、品牌查询的时候不关联pos_item表
			sql = sb.toString();
			if(queryPosItem){
				sql = StringUtils.replace(sql, "%s", "inner join pos_item as item with(nolock) on item.item_num = kitDetail.item_num ");

			} else {
				sql = StringUtils.replace(sql, "%s", "");
			}		
			query = currentSession().createSQLQuery(sql);
			objects.addAll(query.list());
		}
		return objects;
	}

	@Override
	public List<Object[]> findNeedSaleItemRecords(String systemBookCode, Integer branchNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("select recording_fid, item_recording.item_num, max(storehouse_num) AS storehouse_num, ");
		sb.append("max(recording_product_date) AS recording_product_date, max(recording_overdue_date) AS recording_overdue_date,");
		sb.append("max(recording_item_use_unit) AS recording_item_use_unit, max(recording_use_amount) AS recording_use_amount, sum(recording_amount) AS recording_amount,");
		sb.append("max(recording_use_rate) AS recording_use_rate, max(recording_cost) AS recording_cost, sum(recording_out_amount) AS recording_out_amount ");
		sb.append("from item_recording with(nolock) inner join pos_item with(nolock) on pos_item.Item_num = item_recording.item_num ");
		sb.append("WHERE item_recording.system_book_code = :systemBookCode ");
		sb.append("and branch_num = :branchNum and recording_overdue_date > '2000-01-01' ");
		sb.append("and  recording_amount - recording_out_amount > 0.0001 ");
		sb.append("and ((recording_overdue_date < getDate()) or (DATEDIFF (day, getDate(), recording_overdue_date) <= pos_item.ITEM_REMIND_PERIOD)) ");
		sb.append("group By recording_fid, item_recording.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		return query.list();
	}

	@Override
    public List<PackageSumDTO> findPackageSumByBranch(String systemBookCode,
            Integer centerBranchNum, List<Integer> branchNums, Date dateFrom,
            Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, package_log_type, package_log_source, sum(package_log_amount) as amount, sum(package_log_money) as money, ");
		sb.append("sum(package_log_inout_amount) as inoutAmount, sum(package_log_inout_money) as inoutMoney ");
		sb.append("from package_log with(nolock) where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and package_log_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and package_log_audit_time <= :dateTo ");
		}
		sb.append("and package_log_state_code = 3 group by branch_num, package_log_type, package_log_source ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		Map<Integer, PackageSumDTO> map = new HashMap<Integer, PackageSumDTO>();
		Integer branchNum = null;
		String type = null;
		String source = null;
		BigDecimal amount = null;
		BigDecimal money = null;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			type = (String)object[1];
			source = (String)object[2];
			amount = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
			money = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];

			PackageSumDTO packageSumDTO = map.get(branchNum);
			if(packageSumDTO == null){
				packageSumDTO = new PackageSumDTO();
				packageSumDTO.setBranchNum(branchNum);
				map.put(branchNum, packageSumDTO);
			}
			if(type.equals(AppConstants.POS_ITEM_LOG_IN_ORDER)){
				packageSumDTO.setPackageOutAmount(packageSumDTO.getPackageOutAmount().add(amount));
				packageSumDTO.setPackageOutMoney(packageSumDTO.getPackageOutMoney().add(money));
			} else if(type.equals(AppConstants.POS_ITEM_LOG_OUT_ORDER)){
				packageSumDTO.setPackageInAmount(packageSumDTO.getPackageInAmount().add(amount));
				packageSumDTO.setPackageInMoney(packageSumDTO.getPackageInMoney().add(money));
			
				if(StringUtils.equals(source, AppConstants.PACKAGE_LOG_SOURCE_SUPPLIER)){
					
					packageSumDTO.setSupplierPackageInAmount(packageSumDTO.getSupplierPackageInAmount().add(amount));
					packageSumDTO.setSupplierPackageInMoney(packageSumDTO.getSupplierPackageInMoney().add(money));
					
				} else 	if(StringUtils.equals(source, AppConstants.PACKAGE_LOG_SOURCE_CENTER)){
					packageSumDTO.setCenterPackageInAmount(packageSumDTO.getCenterPackageInAmount().add(amount));
					packageSumDTO.setCenterPackageInMoney(packageSumDTO.getCenterPackageInMoney().add(money));
				}
			
			} 
			if(type.equals(AppConstants.POS_ITEM_LOG_CHECKORDER)){
				packageSumDTO.setPackageInoutAmount(packageSumDTO.getPackageInoutAmount().add(object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5]));
				packageSumDTO.setPackageInoutMoney(packageSumDTO.getPackageInoutMoney().add(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]));
			}
			
		}
		List<PackageSumDTO> list = new ArrayList<PackageSumDTO>(map.values());
		return list;
    }

	@Override
    public List<PackageSumDTO> findPackageSumByPrice(String systemBookCode,
            Integer centerBranchNum, List<Integer> branchNums, Date dateFrom,
            Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select package_log_price, package_log_type, package_log_source, sum(package_log_amount) as amount, sum(package_log_money) as money, ");
		sb.append("sum(package_log_inout_amount) as inoutAmount, sum(package_log_inout_money) as inoutMoney ");
		sb.append("from package_log with(nolock) where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and package_log_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and package_log_audit_time <= :dateTo ");
		}
		sb.append("and package_log_state_code = 3 group by package_log_price, package_log_type, package_log_source ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		Map<BigDecimal, PackageSumDTO> map = new HashMap<BigDecimal, PackageSumDTO>();
		BigDecimal price = null;
		String type = null;
		String source = null;
		BigDecimal amount = null;
		BigDecimal money = null;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			price = (BigDecimal)object[0];
			type = (String)object[1];
			source = (String)object[2];
			amount = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
			money = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];

			PackageSumDTO packageSumDTO = map.get(price);
			if(packageSumDTO == null){
				packageSumDTO = new PackageSumDTO();
				packageSumDTO.setPackagePrice(price);
				map.put(price, packageSumDTO);
			}
			if(type.equals(AppConstants.POS_ITEM_LOG_IN_ORDER)){
				packageSumDTO.setPackageOutAmount(packageSumDTO.getPackageOutAmount().add(amount));
				packageSumDTO.setPackageOutMoney(packageSumDTO.getPackageOutMoney().add(money));
			} else if(type.equals(AppConstants.POS_ITEM_LOG_OUT_ORDER)){
				packageSumDTO.setPackageInAmount(packageSumDTO.getPackageInAmount().add(amount));
				packageSumDTO.setPackageInMoney(packageSumDTO.getPackageInMoney().add(money));
			
				if(StringUtils.equals(source, AppConstants.PACKAGE_LOG_SOURCE_SUPPLIER)){
					
					packageSumDTO.setSupplierPackageInAmount(packageSumDTO.getSupplierPackageInAmount().add(amount));
					packageSumDTO.setSupplierPackageInMoney(packageSumDTO.getSupplierPackageInMoney().add(money));
					
				} else 	if(StringUtils.equals(source, AppConstants.PACKAGE_LOG_SOURCE_CENTER)){
					packageSumDTO.setCenterPackageInAmount(packageSumDTO.getCenterPackageInAmount().add(amount));
					packageSumDTO.setCenterPackageInMoney(packageSumDTO.getCenterPackageInMoney().add(money));
				}
			
			} 
			if(type.equals(AppConstants.POS_ITEM_LOG_CHECKORDER)){
				packageSumDTO.setPackageInoutAmount(packageSumDTO.getPackageInoutAmount().add(object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5]));
				packageSumDTO.setPackageInoutMoney(packageSumDTO.getPackageInoutMoney().add(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]));
			}
			
		}
		List<PackageSumDTO> list = new ArrayList<PackageSumDTO>(map.values());
		return list;
    }

	@Override
	public List<Object[]> findSalerCommissionsByMoney(String systemBookCode,
			Date dtFrom, Date dtTo, List<Integer> branchNums,
			List<String> salerNums, BigDecimal interval) {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_sold_by, case when(LEFT(order_payment_money/:interval,1) = '-') then '-1' when charindex('.', order_payment_money/:interval) >= '3' then '10' else LEFT(order_payment_money/:interval,1) end, count(order_no) as count ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		if (salerNums != null && salerNums.size() > 0) {
			sb.append("and order_sold_by in " + AppUtil.getStringParmeList(salerNums));
		}
		sb.append("group by order_sold_by, case when(left(order_payment_money/:interval,1) = '-') then '-1' when charindex('.', order_payment_money/:interval) >= '3' then '10' else LEFT(order_payment_money/:interval,1) end");   
		String sql = sb.toString();
		sql = sql.replaceAll(":interval", interval.toString());
		SQLQuery query = currentSession().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public List<TransferPolicyDTO> findTransferPolicyDTOs(PolicyPosItemQuery policyPosItemQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(detail.out_order_detail_qty) as qty, sum(detail.out_order_detail_sale_subtotal) as money, ");
		sb.append("sum(case when detail.out_order_detail_std_price is null then out_order_detail_sale_subtotal else (detail.out_order_detail_std_price * detail.out_order_detail_qty) end) as stdMoney ");
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as t with(nolock) on detail.out_order_fid = t.out_order_fid ");
		sb.append("where t.system_book_code = '" + policyPosItemQuery.getSystemBookCode() + "' ");
		sb.append("and out_branch_num = " + policyPosItemQuery.getBranchNum() + " ");
		if(policyPosItemQuery.getBranchNums() != null && policyPosItemQuery.getBranchNums().size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(policyPosItemQuery.getBranchNums()));
		}
		sb.append("and t.out_order_audit_time between :dateFrom and :dateTo ");
		sb.append("and t.out_order_state_code = 3 ");
		sb.append("and detail.out_order_detail_std_price is not null ");
		if(policyPosItemQuery.getItemNums() != null && policyPosItemQuery.getItemNums().size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(policyPosItemQuery.getItemNums()));
		}
		if(StringUtils.isNotEmpty(policyPosItemQuery.getPolicyOrderFid())){
			sb.append("and detail.out_order_detail_policy_no = '" + policyPosItemQuery.getPolicyOrderFid() + "' ");
		}
		sb.append("group by detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setParameter("dateFrom", DateUtil.getMinOfDate(policyPosItemQuery.getDtFrom()));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(policyPosItemQuery.getDtTo()));
		List<Object[]> objects = query.list();
		
		List<TransferPolicyDTO> list = new ArrayList<TransferPolicyDTO>();
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			
			TransferPolicyDTO dto = new TransferPolicyDTO();
			dto.setItemNum((Integer) object[0]);
			dto.setPolicyQty((BigDecimal) object[1]);
			dto.setPolicyMoney((BigDecimal) object[2]);
			dto.setPolicyStdMoney((BigDecimal) object[3]);
			dto.setPolicyDiscount(dto.getPolicyStdMoney().subtract(dto.getPolicyMoney()));
			if(dto.getPolicyQty().compareTo(BigDecimal.ZERO) != 0){
				dto.setPolicyPrice(dto.getPolicyMoney().divide(dto.getPolicyQty(), 4, BigDecimal.ROUND_HALF_UP));
				dto.setPolicyStdPrice(dto.getPolicyStdMoney().divide(dto.getPolicyQty(), 4, BigDecimal.ROUND_HALF_UP));
			}
			
			list.add(dto);
		}	
		return list;
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranch(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, payment_pay_by, sum(payment_money) as money ");
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
		sb.append("group by branch_num, payment_pay_by ");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		List<PosReceiveDiffMoneySumDTO> list = new ArrayList<PosReceiveDiffMoneySumDTO>();
		Integer branchNum = null;
		String type = null;
		BigDecimal money = null;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			type = (String)object[1];
			money = (BigDecimal)object[2];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranch(list, branchNum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setTotalSaleMoney(dto.getTotalSaleMoney().add(money));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
			
			TypeAndTwoValuesDTO saleTypeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTotalSaleMoneyDetails(), type);
			if(saleTypeAndTwoValuesDTO == null){
				saleTypeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				saleTypeAndTwoValuesDTO.setType(type);
				dto.getTotalSaleMoneyDetails().add(saleTypeAndTwoValuesDTO);
			}
			saleTypeAndTwoValuesDTO.setAmount(saleTypeAndTwoValuesDTO.getAmount().add(money));
			
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
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			type = (String)object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranch(list, branchNum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setTotalCardDeposit(dto.getTotalCardDeposit().add(money));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
			
			
			TypeAndTwoValuesDTO depositTypeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTotalCardDepositDetails(), type);
			if(depositTypeAndTwoValuesDTO == null){
				depositTypeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				depositTypeAndTwoValuesDTO.setType(type);
				dto.getTotalCardDepositDetails().add(depositTypeAndTwoValuesDTO);
			}
			depositTypeAndTwoValuesDTO.setAmount(depositTypeAndTwoValuesDTO.getAmount().add(money));
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
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			type = (String)object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranch(list, branchNum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setTotalOtherMoney(dto.getTotalOtherMoney().add(money));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
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
			branchNum = (Integer) object[0];
			type = (String) object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranch(list, branchNum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setTotalRelatMoney(dto.getTotalRelatMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
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
			branchNum = (Integer) object[0];
			type = (String) object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranch(list, branchNum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setTotalReplaceMoney(dto.getTotalReplaceMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
		}
		
		
		sb = new StringBuffer();
		sb.append("select branch_num, sum(shift_table_actual_money) as actualCash, sum(shift_table_actual_bank_money) as actualBankMoney ");
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
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		BigDecimal bankMoney = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			bankMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranch(list, branchNum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setTotalReceiveMoney(dto.getTotalReceiveMoney().add(money.add(bankMoney)));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), AppConstants.PAYMENT_CASH);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(AppConstants.PAYMENT_CASH);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));
			
			typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), AppConstants.PAYMENT_YINLIAN);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(AppConstants.PAYMENT_YINLIAN);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(bankMoney));
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_payment_type, sum(shift_table_payment_money) as money ");
		sb.append("from shift_table_payment with(nolock) ");
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
		sb.append("group by branch_num, shift_table_payment_type ");
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			type = (String)object[1];
			money = (BigDecimal)object[2];
			
			//现金和银联卡以  shift_table 中为准
			if(type.equals(AppConstants.PAYMENT_CASH)){
				continue;
			}
			if(type.equals(AppConstants.PAYMENT_YINLIAN)){
				continue;
			}
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranch(list, branchNum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setTotalReceiveMoney(dto.getTotalReceiveMoney().add(money));
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));
		}
		return list;
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranchCasher(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select pos_order.branch_num, order_operator, payment_pay_by, sum(payment_money) as money ");
		sb.append("from payment with(nolock) inner join pos_order on payment.order_no = pos_order.order_no ");
		sb.append("where pos_order.system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and pos_order.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and pos_order.shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and pos_order.shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by pos_order.branch_num, order_operator, payment_pay_by ");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		List<PosReceiveDiffMoneySumDTO> list = new ArrayList<PosReceiveDiffMoneySumDTO>();
		Integer branchNum = null;
		String operator = null;
		String type = null;
		BigDecimal money = null;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			operator = (String) object[1];
			type = (String)object[2];
			money = (BigDecimal)object[3];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranchCashier(list, branchNum, operator);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setCasher(operator);
				list.add(dto);
			}
			dto.setTotalSaleMoney(dto.getTotalSaleMoney().add(money));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
			
			TypeAndTwoValuesDTO saleTypeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTotalSaleMoneyDetails(), type);
			if(saleTypeAndTwoValuesDTO == null){
				saleTypeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				saleTypeAndTwoValuesDTO.setType(type);
				dto.getTotalSaleMoneyDetails().add(saleTypeAndTwoValuesDTO);
			}
			saleTypeAndTwoValuesDTO.setAmount(saleTypeAndTwoValuesDTO.getAmount().add(money));
			
		}
		sb = new StringBuffer();
		sb.append("select branch_num, deposit_operator, deposit_payment_type_name, sum(deposit_cash) ");
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
		sb.append("group by branch_num, deposit_operator, deposit_payment_type_name ");
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			operator = (String) object[1];
			type = (String)object[2];
			money = (BigDecimal)object[3];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranchCashier(list, branchNum, operator);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setCasher(operator);
				list.add(dto);
			}

			dto.setTotalCardDeposit(dto.getTotalCardDeposit().add(money));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
			
			TypeAndTwoValuesDTO depositTypeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTotalCardDepositDetails(), type);
			if(depositTypeAndTwoValuesDTO == null){
				depositTypeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				depositTypeAndTwoValuesDTO.setType(type);
				dto.getTotalCardDepositDetails().add(depositTypeAndTwoValuesDTO);
			}
			depositTypeAndTwoValuesDTO.setAmount(depositTypeAndTwoValuesDTO.getAmount().add(money));
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, other_inout_operator, other_inout_payment_type, sum(case when other_inout_flag = 0 then -other_inout_money else other_inout_money end) ");
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
		sb.append("group by branch_num, other_inout_operator, other_inout_payment_type ");
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			operator = (String) object[1];
			type = (String)object[2];
			money = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranchCashier(list, branchNum, operator);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setCasher(operator);
				list.add(dto);
			}
			dto.setTotalOtherMoney(dto.getTotalOtherMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
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
				.add(Projections.groupProperty("r.relatCardOperator"))
				.add(Projections.groupProperty("r.relatCardPaymentTypeName")).add(Projections.sum("r.relatCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			operator = (String) object[1];
			type = (String)object[2];
			money = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranchCashier(list, branchNum, operator);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setCasher(operator);
				list.add(dto);
			}
			dto.setTotalRelatMoney(dto.getTotalRelatMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
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
				.add(Projections.groupProperty("r.replaceCardOperator"))
				.add(Projections.groupProperty("r.replaceCardPaymentTypeName"))
				.add(Projections.sum("r.replaceCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			operator = (String) object[1];
			type = (String)object[2];
			money = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranchCashier(list, branchNum, operator);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setCasher(operator);
				list.add(dto);
			}
			dto.setTotalReplaceMoney(dto.getTotalReplaceMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
		}
		
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_user_name, sum(shift_table_actual_money) as actualCash, sum(shift_table_actual_bank_money) as actualBankMoney ");
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
		sb.append("group by branch_num, shift_table_user_name ");
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		BigDecimal bankMoney = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			operator = (String) object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			bankMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranchCashier(list, branchNum, operator);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setCasher(operator);
				list.add(dto);
			}
			dto.setTotalReceiveMoney(dto.getTotalReceiveMoney().add(money.add(bankMoney)));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), AppConstants.PAYMENT_CASH);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(AppConstants.PAYMENT_CASH);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));
			
			typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), AppConstants.PAYMENT_YINLIAN);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(AppConstants.PAYMENT_YINLIAN);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(bankMoney));
		}
		
		sb = new StringBuffer();
		sb.append("select shift_table.branch_num, shift_table_user_name, shift_table_payment_type, sum(shift_table_payment_money) as money ");
		sb.append("from shift_table_payment with(nolock) inner join shift_table ");
		sb.append("on shift_table_payment.system_book_code = shift_table.system_book_code ");
		sb.append("and shift_table_payment.branch_num = shift_table.branch_num ");
		sb.append("and shift_table_payment.shift_table_bizday = shift_table.shift_table_bizday ");
		sb.append("and shift_table_payment.shift_table_num = shift_table.shift_table_num ");
		sb.append("where shift_table.system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and shift_table.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table.shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table.shift_table_bizday <= :bizTo ");
		}
		sb.append("group by shift_table.branch_num, shift_table_user_name, shift_table_payment_type ");
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			operator = (String) object[1];
			type = (String)object[2];
			money = (BigDecimal)object[3];
			
			//现金和银联卡以  shift_table 中为准
			if(type.equals(AppConstants.PAYMENT_CASH)){
				continue;
			}
			if(type.equals(AppConstants.PAYMENT_YINLIAN)){
				continue;
			}
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranchCashier(list, branchNum, operator);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setCasher(operator);
				list.add(dto);
			}
			dto.setTotalReceiveMoney(dto.getTotalReceiveMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));
		}
		return list;
	}

	@Override
    public List<Object[]> findSaleAnalysisByBranchDayItem(
            SaleAnalysisQueryData saleAnalysisQueryData) {
    	StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.order_detail_bizday, detail.item_num, detail.order_detail_state_code, ");
		sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
		sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
		sb.append("sum(detail.order_detail_discount) as discount ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + saleAnalysisQueryData.getSystemBookCode() + "' ");
		if (saleAnalysisQueryData.getBranchNums() != null && saleAnalysisQueryData.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(saleAnalysisQueryData.getBranchNums()));
		}
		sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtFrom())
				+ "' and '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtTo()) + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
		if (saleAnalysisQueryData.getPosItemNums() != null && saleAnalysisQueryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(saleAnalysisQueryData.getPosItemNums()));
		}
		if (saleAnalysisQueryData.getIsQueryCF() != null && saleAnalysisQueryData.getIsQueryCF()) {
			sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
		}
		if (StringUtils.isNotEmpty(saleAnalysisQueryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		if (saleAnalysisQueryData.getOrderSources() != null && saleAnalysisQueryData.getOrderSources().size() > 0) {
			sb.append("and detail.order_source in " + AppUtil.getStringParmeList(saleAnalysisQueryData.getOrderSources()));
		}
		if(saleAnalysisQueryData.getTwoStringValueDatas() != null && saleAnalysisQueryData.getTwoStringValueDatas().size() > 0){
			sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
			for(int i = 0;i < saleAnalysisQueryData.getTwoStringValueDatas().size();i++){
				TwoStringValueData twoStringValueData = saleAnalysisQueryData.getTwoStringValueDatas().get(i);
				if(i > 0){
					sb.append(" or ");
				}
				sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
			}
			
			sb.append(")) ");
		}
		sb.append("group by detail.order_detail_branch_num, detail.order_detail_bizday, detail.item_num, detail.order_detail_state_code ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (saleAnalysisQueryData.getIsQueryCF() != null && saleAnalysisQueryData.getIsQueryCF()) {
			sb = new StringBuffer();
			sb.append("select kitDetail.order_kit_detail_branch_num, kitDetail.order_kit_detail_bizday, kitDetail.item_num,  kitDetail.order_kit_detail_state_code, ");
			sb.append("sum(kitDetail.order_kit_detail_amount) as amount, sum(kitDetail.order_kit_detail_payment_money) as money, ");
			sb.append("sum(0.00) as assistAmount, count(kitDetail.item_num) as amount_, ");
			sb.append("sum(kitDetail.order_kit_detail_discount) as discount ");
			sb.append("from pos_order_kit_detail as kitDetail with(nolock) ");
			sb.append("where kitDetail.order_kit_detail_book_code = '" + saleAnalysisQueryData.getSystemBookCode() + "' ");
			if (saleAnalysisQueryData.getBranchNums() != null && saleAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and kitDetail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(saleAnalysisQueryData.getBranchNums()));
			}
			sb.append("and kitDetail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtTo()) + "' ");
			sb.append("and kitDetail.order_kit_detail_order_state in (5, 7) ");
			if (saleAnalysisQueryData.getPosItemNums() != null && saleAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and kitDetail.item_num in " + AppUtil.getIntegerParmeList(saleAnalysisQueryData.getPosItemNums()));
			}
			if (saleAnalysisQueryData.getOrderSources() != null && saleAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(saleAnalysisQueryData.getOrderSources()));
			}
			if(saleAnalysisQueryData.getTwoStringValueDatas() != null && saleAnalysisQueryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = kitDetail.item_num and (");
				for(int i = 0;i < saleAnalysisQueryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = saleAnalysisQueryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}
				
				sb.append(")) ");
			}
			sb.append("group by kitDetail.order_kit_detail_branch_num, kitDetail.order_kit_detail_bizday, kitDetail.item_num,  kitDetail.order_kit_detail_state_code ");
			query = currentSession().createSQLQuery(sb.toString());
			objects.addAll(query.list());
		}
		return objects;
    }

	@Override
    public List<Object[]> findProfitAnalysisByBranchDayItem(
            ProfitAnalysisQueryData profitAnalysisQueryData) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("select detail.order_detail_branch_num, detail.order_detail_bizday, detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode ");
		if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in "
					+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
		}
		sb.append("and detail.order_detail_bizday between :bizFrom and :bizTo ");
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if(profitAnalysisQueryData.isQueryPresent()){
			sb.append("and detail.order_detail_state_code = 2 ");
		}
		if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in "
					+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
		}
		if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
				|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
						.getPosItemTypeCodes().size() > 0)) {
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
			if (profitAnalysisQueryData.getPosItemTypeCodes() != null
					&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
				sb.append("and item.item_category_code in "
						+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
			}
			if (profitAnalysisQueryData.getBrandCodes() != null
					&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
				sb.append("and item.item_brand in "
						+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
			}
			sb.append(") ");
		}
		if (profitAnalysisQueryData.getIsQueryCF()) {
			sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
		}
		if (StringUtils.isNotEmpty(profitAnalysisQueryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
			sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
		}
		sb.append("group by detail.order_detail_branch_num, detail.order_detail_bizday, detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
		query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
		query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
		List<Object[]> objects = query.list();
		if (profitAnalysisQueryData.getIsQueryCF()) {
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num, detail.order_kit_detail_bizday, detail.item_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else detail.order_kit_detail_amount end) as amount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then (-detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as cost ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (profitAnalysisQueryData.getBranchNums() != null && profitAnalysisQueryData.getBranchNums().size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getBranchNums()) + " ");
			}
			sb.append("and detail.order_kit_detail_bizday between :bizFrom and :bizTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) and detail.item_num is not null ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if(profitAnalysisQueryData.isQueryPresent()){
				sb.append("and detail.order_kit_detail_state_code = 2 ");
			}
			if (profitAnalysisQueryData.getPosItemNums() != null && profitAnalysisQueryData.getPosItemNums().size() > 0) {
				sb.append("and detail.item_num in "
						+ AppUtil.getIntegerParmeList(profitAnalysisQueryData.getPosItemNums()) + " ");
			}
			if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
					|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
							.getPosItemTypeCodes().size() > 0)) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_num = detail.item_num ");
				if (profitAnalysisQueryData.getPosItemTypeCodes() != null
						&& profitAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
					sb.append("and item.item_category_code in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getPosItemTypeCodes()) + " ");
				}
				if (profitAnalysisQueryData.getBrandCodes() != null
						&& profitAnalysisQueryData.getBrandCodes().size() > 0) {
					sb.append("and item.item_brand in "
							+ AppUtil.getStringParmeList(profitAnalysisQueryData.getBrandCodes()) + " ");
				}
				sb.append(") ");
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by detail.order_kit_detail_branch_num, detail.order_kit_detail_bizday, detail.item_num ");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
			query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
			query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
			objects.addAll(query.list());
		}
	
		return objects;
    }

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByShiftTable(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		StringBuffer sb = new StringBuffer();
		sb.append("select pos_order.branch_num, pos_order.shift_table_bizday, pos_order.shift_table_num, payment_pay_by, sum(payment_money) as money ");
		sb.append("from payment with(nolock) inner join pos_order on payment.order_no = pos_order.order_no ");
		sb.append("where pos_order.system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and pos_order.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and pos_order.shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and pos_order.shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		if(StringUtils.isNotEmpty(casher)){
			sb.append("and order_operator = '" + casher + "' ");
		}
		sb.append("group by pos_order.branch_num, pos_order.shift_table_bizday, pos_order.shift_table_num, payment_pay_by ");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = sqlQuery.list();
		List<PosReceiveDiffMoneySumDTO> list = new ArrayList<PosReceiveDiffMoneySumDTO>();
		Integer branchNum = null;
		String bizday = null;
		Integer biznum = null;
		String type = null;
		BigDecimal money = null;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			bizday = (String) object[1];
			biznum = (Integer)object[2];
			type = (String)object[3];
			money = (BigDecimal)object[4];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setTotalSaleMoney(dto.getTotalSaleMoney().add(money));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
			
			TypeAndTwoValuesDTO saleTypeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTotalSaleMoneyDetails(), type);
			if(saleTypeAndTwoValuesDTO == null){
				saleTypeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				saleTypeAndTwoValuesDTO.setType(type);
				dto.getTotalSaleMoneyDetails().add(saleTypeAndTwoValuesDTO);
			}
			saleTypeAndTwoValuesDTO.setAmount(saleTypeAndTwoValuesDTO.getAmount().add(money));
			
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
		if(StringUtils.isNotEmpty(casher)){
			sb.append("and deposit_operator = '" + casher + "' ");
		}
		sb.append("and deposit_payment_type_name != '" + AppConstants.PAYMENT_ORI + "' ");
		sb.append("group by branch_num, shift_table_bizday, shift_table_num, deposit_payment_type_name  ");
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			bizday = (String) object[1];
			biznum = object[2] == null?1:(Integer)object[2];
			type = (String) object[3];
			money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setTotalCardDeposit(dto.getTotalCardDeposit().add(money));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
			
			TypeAndTwoValuesDTO depositTypeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTotalCardDepositDetails(), type);
			if(depositTypeAndTwoValuesDTO == null){
				depositTypeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				depositTypeAndTwoValuesDTO.setType(type);
				dto.getTotalCardDepositDetails().add(depositTypeAndTwoValuesDTO);
			}
			depositTypeAndTwoValuesDTO.setAmount(depositTypeAndTwoValuesDTO.getAmount().add(money));
		}
		
		
		sb = new StringBuffer();
		sb.append("select branch_num, other_inout_bizday, other_inout_shift_table_num, other_inout_payment_type, sum(case when other_inout_flag = 0 then -other_inout_money else other_inout_money end) ");
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
		sb.append("group by branch_num, other_inout_bizday, other_inout_shift_table_num, other_inout_payment_type ");
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			bizday = (String) object[1];
			biznum = object[2] == null?1:(Integer)object[2];
			type = (String) object[3];
			money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setTotalOtherMoney(dto.getTotalOtherMoney().add(money));

			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
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
				.add(Projections.groupProperty("r.shiftTableNum"))
				.add(Projections.groupProperty("r.relatCardPaymentTypeName"))
				.add(Projections.sum("r.relatCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			bizday = (String) object[1];
			biznum = object[2] == null?1:(Integer)object[2];
			type = (String) object[3];
			money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setTotalRelatMoney(dto.getTotalRelatMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
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
				.add(Projections.groupProperty("r.shiftTableNum"))
				.add(Projections.groupProperty("r.replaceCardPaymentTypeName"))
				.add(Projections.sum("r.replaceCardMoney")));
		objects = criteria.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			bizday = (String) object[1];
			biznum = object[2] == null?1:(Integer)object[2];
			type = (String) object[3];
			money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setTotalReplaceMoney(dto.getTotalReplaceMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));
		}
		
		
		sb = new StringBuffer();
		sb.append("select * ");
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
		if(StringUtils.isNotEmpty(casher)){
			sb.append("and shift_table_user_name = '" + casher + "' ");
		}
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		sqlQuery.addEntity(ShiftTable.class);
		List<ShiftTable> shiftTables = sqlQuery.list();
		BigDecimal bankMoney = null;
		for (int i = 0; i < shiftTables.size(); i++) {
			ShiftTable shiftTable = shiftTables.get(i);
			branchNum = shiftTable.getId().getBranchNum();
			bizday = shiftTable.getId().getShiftTableBizday();
			biznum = shiftTable.getId().getShiftTableNum();
			money = shiftTable.getShiftTableActualMoney() == null ? BigDecimal.ZERO : shiftTable.getShiftTableActualMoney();
			bankMoney = shiftTable.getShiftTableActualBankMoney() == null ? BigDecimal.ZERO : shiftTable.getShiftTableActualBankMoney();
			
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setBranchInputMemo(shiftTable.getShiftTableMemo());
			dto.setCasher(shiftTable.getShiftTableUserName());
			dto.setTotalReceiveMoney(dto.getTotalReceiveMoney().add(money.add(bankMoney)));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), AppConstants.PAYMENT_CASH);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(AppConstants.PAYMENT_CASH);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));
			
			typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), AppConstants.PAYMENT_YINLIAN);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(AppConstants.PAYMENT_YINLIAN);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(bankMoney));
		}
		
		sb = new StringBuffer();
		sb.append("select shift_table.branch_num, shift_table.shift_table_bizday, shift_table.shift_table_num, shift_table_payment_type, sum(shift_table_payment_money) as money ");
		sb.append("from shift_table_payment with(nolock) inner join shift_table ");
		sb.append("on shift_table_payment.system_book_code = shift_table.system_book_code ");
		sb.append("and shift_table_payment.branch_num = shift_table.branch_num ");
		sb.append("and shift_table_payment.shift_table_bizday = shift_table.shift_table_bizday ");
		sb.append("and shift_table_payment.shift_table_num = shift_table.shift_table_num ");
		sb.append("where shift_table.system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and shift_table.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table.shift_table_bizday >= :bizFrom ");
		}
		if (dateTo != null) {
			sb.append("and shift_table.shift_table_bizday <= :bizTo ");
		}
		if(StringUtils.isNotEmpty(casher)){
			sb.append("and shift_table.shift_table_user_name = '" + casher + "' ");
		}
		sb.append("group by shift_table.branch_num, shift_table.shift_table_bizday, shift_table.shift_table_num, shift_table_payment_type ");
		sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = sqlQuery.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			bizday = (String) object[1];
			biznum = object[2] == null?1:(Integer)object[2];
			type = (String)object[3];
			money = (BigDecimal)object[4];
			
			//现金和银联卡以  shift_table 中为准
			if(type.equals(AppConstants.PAYMENT_CASH)){
				continue;
			}
			if(type.equals(AppConstants.PAYMENT_YINLIAN)){
				continue;
			}
			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if(dto == null){
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setTotalReceiveMoney(dto.getTotalReceiveMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));
		}
		return list;
	}

	@Override
	public List<CardQtySumDTO> findCardQtySumDatasByBranch(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		List<CardQtySumDTO> list = new ArrayList<CardQtySumDTO>();
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		
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
		sb.append("group by branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		Integer branchNum;
		Integer count;
		List<Object[]> objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			count = object[1] == null?0:(Integer)object[1];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setRevokeQty(count);
		}
		
		List<String> types = new ArrayList<String>();
		types.add(AppConstants.REGISTER_TYPE_DELIVER);
		types.add(AppConstants.REGISTER_TYPE_ORI);
		
		sb = new StringBuffer();
		sb.append("select branch_num, card_user_register_type, count(card_user_register_fid) from card_user_register with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and card_user_register_type in " + AppUtil.getStringParmeList(types));
		sb.append("group by branch_num, card_user_register_type");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		String type;
		objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			type = (String)object[1];
			count = object[2] == null?0:(Integer)object[2];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);

			}
			if(type.equals(AppConstants.REGISTER_TYPE_DELIVER)){
				dto.setSendQty(count);

			} else {
				dto.setChangeQty(count);
				
			}
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, count(replace_card_fid) from replace_card with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("group by branch_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}

		objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			count = object[1] == null?0:(Integer)object[1];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			dto.setReplaceQty(count);
		}
		
				
		sb = new StringBuffer();
		sb.append("select branch_num, card_bill_type, sum(card_bill_qty) from card_bill with(nolock) where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and card_bill_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if(dateFrom != null){
			sb.append("and card_bill_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and card_bill_audit_time <= :dateTo ");
		}
		sb.append("group by branch_num, card_bill_type ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", dateFrom);
		}
		if(dateTo != null){
			query.setParameter("dateTo", dateTo);
		}
		objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			type = (String)object[1];
			count = object[2] == null?0:(Integer)object[2];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);

			}
			if(type.equals(AppConstants.CARD_BILL_TYPE_TRANSFER)){
				dto.setInQty(count);

			} else {
				dto.setCheckQty(count);
				
			}
		}
		
		sb = new StringBuffer();
		sb.append("select center_branch_num, sum(card_bill_qty) from card_bill with(nolock) where system_book_code = :systemBookCode ");
		sb.append("and center_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and card_bill_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		sb.append("and card_bill_type = '" + AppConstants.CARD_BILL_TYPE_TRANSFER + "' ");
		if(dateFrom != null){
			sb.append("and card_bill_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and card_bill_audit_time <= :dateTo ");
		}
		sb.append("group by center_branch_num ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", dateFrom);
		}
		if(dateTo != null){
			query.setParameter("dateTo", dateTo);
		}
		objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			count = object[1] == null?0:(Integer)object[1];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);

			}			
			dto.setOutQty(count);

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
		
		BigDecimal money;
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			type = (String) object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);

			}
			dto.setDepositMoney(dto.getDepositMoney().add(money));
			
			TypeAndTwoValuesDTO detailDTO = new TypeAndTwoValuesDTO(type, money);
			dto.getTypeAndTwoValuesDatas().add(detailDTO);
			
		}
		return list;
	}

	@Override
	public List<CardQtySumDTO> findCardQtySumDatasByBranchAndDay(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		List<CardQtySumDTO> list = new ArrayList<CardQtySumDTO>();
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday as bizday, count(card_user_log_fid) from card_user_log with(nolock) ");
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
		sb.append("group by branch_num, shift_table_bizday ");
		sb.append("order by branch_num, shift_table_bizday ");

		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		Integer branchNum;
		String bizday;
		Integer count;
		List<Object[]> objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String)object[1];
			count = object[2] == null?0:(Integer)object[2];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum, bizday);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				dto.setBizDay(bizday);
				list.add(dto);

			}
			dto.setRevokeQty(count);
		}
		
		List<String> types = new ArrayList<String>();
		types.add(AppConstants.REGISTER_TYPE_DELIVER);
		types.add(AppConstants.REGISTER_TYPE_ORI);
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday as bizday, card_user_register_type, count(card_user_register_fid) from card_user_register with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("and card_user_register_type in " + AppUtil.getStringParmeList(types));
		sb.append("group by branch_num, shift_table_bizday, card_user_register_type ");
		sb.append("order by branch_num, shift_table_bizday ");

		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		String type;
		objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String)object[1];
			type = (String)object[2];
			count = object[3] == null?0:(Integer)object[3];
						
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum, bizday);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				dto.setBizDay(bizday);
				list.add(dto);

			}
			if(type.equals(AppConstants.REGISTER_TYPE_DELIVER)){
				dto.setSendQty(count);

			} else {
				dto.setChangeQty(count);
				
			}
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday as bizday, count(replace_card_fid) as amount from replace_card with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= :bizTo ");
		}
		sb.append("group by branch_num, shift_table_bizday ");
		sb.append("order by branch_num, shift_table_bizday ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String)object[1];
			count = object[2] == null?0:(Integer)object[2];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum, bizday);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				dto.setBizDay(bizday);
				list.add(dto);

			}
			dto.setReplaceQty(count);
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, convert(varchar(12) , card_bill_audit_time, 112) as bizday, card_bill_type, sum(card_bill_qty) from card_bill with(nolock) where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and card_bill_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if(dateFrom != null){
			sb.append("and card_bill_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and card_bill_audit_time <= :dateTo ");
		}
		sb.append("group by branch_num,convert(varchar(12) , card_bill_audit_time, 112),  card_bill_type ");
		sb.append("order by branch_num, convert(varchar(12) , card_bill_audit_time, 112) ");

		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", dateFrom);
		}
		if(dateTo != null){
			query.setParameter("dateTo", dateTo);
		}
		objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String)object[1];
			type = (String)object[2];
			count = object[3] == null?0:(Integer)object[3];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum, bizday);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				dto.setBizDay(bizday);
				list.add(dto);

			}
			if(type.equals(AppConstants.CARD_BILL_TYPE_TRANSFER)){
				dto.setInQty(count);

			} else {
				dto.setCheckQty(count);
				
			}
		}
		
		sb = new StringBuffer();
		sb.append("select center_branch_num, convert(varchar(12) , card_bill_audit_time, 112) as bizday, sum(card_bill_qty) from card_bill with(nolock) where system_book_code = :systemBookCode ");
		sb.append("and center_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and card_bill_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		sb.append("and card_bill_type = '" + AppConstants.CARD_BILL_TYPE_TRANSFER + "' ");
		if(dateFrom != null){
			sb.append("and card_bill_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and card_bill_audit_time <= :dateTo ");
		}
		sb.append("group by center_branch_num, convert(varchar(12) , card_bill_audit_time, 112) ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", dateFrom);
		}
		if(dateTo != null){
			query.setParameter("dateTo", dateTo);
		}
		objects = query.list();
		for(int i = 0; i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String)object[1];
			count = object[2] == null?0:(Integer)object[2];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum, bizday);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				dto.setBizDay(bizday);
				list.add(dto);

			}		
			dto.setOutQty(count);

		}
		
		
		sb = new StringBuffer();
		sb.append("select branch_num,shift_table_bizday, deposit_payment_type_name, sum(deposit_cash) ");
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
		sb.append("order by branch_num, shift_table_bizday ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		
		BigDecimal money;
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String)object[1];
			type = (String)object[2];
			money = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
			
			CardQtySumDTO dto = CardQtySumDTO.get(list, branchNum, bizday);
			if(dto == null){
				dto = new CardQtySumDTO();
				dto.setBranchNum(branchNum);
				dto.setBizDay(bizday);
				list.add(dto);

			}
			dto.setDepositMoney(dto.getDepositMoney().add(money));
			
			TypeAndTwoValuesDTO detailDTO = new TypeAndTwoValuesDTO(type, money);
			dto.getTypeAndTwoValuesDatas().add(detailDTO);
			
		}
		return list;
	}

	@Override
	public List<CardDepositCommissionDTO> findCardDepositCommissionDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                                        Date dateTo, Integer groupType, String querySellers) {
		List<CardDepositCommissionDTO> templist = new ArrayList<CardDepositCommissionDTO>();
		List<CardDepositCommissionDTO> list = new ArrayList<CardDepositCommissionDTO>();

		StringBuffer sb = new StringBuffer();
		sb.append("select deposit_seller, branch_num, shift_table_bizday, deposit_cust_num, sum(deposit_cash) as cash ");
		sb.append("from card_deposit ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		sb.append("and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("group by deposit_seller, branch_num, shift_table_bizday, deposit_cust_num order by deposit_seller desc ");
		
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		Object[] object;
		String seller;
		Integer branchNum;
		BigDecimal money;
		Integer cardUserNum;
	    String bizday;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
	
			seller = object[0] == null?"":(String) object[0];
			branchNum = (Integer) object[1];
			bizday = (String) object[2];
			cardUserNum = (Integer) object[3];
			money = (BigDecimal)object[4];
			
			CardDepositCommissionDTO dto = new CardDepositCommissionDTO();			
			dto.setSeller(seller);
			dto.setBranchNum(branchNum);
			dto.setBizday(bizday);
			dto.setCardUserNum(cardUserNum);
			dto.setMoney(money);
			templist.add(dto);
			
		}
		
		sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, consume_cust_num, sum(consume_money) as money ");
		sb.append("from card_consume ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		sb.append("and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("group by branch_num, shift_table_bizday, consume_cust_num ");		
		query = currentSession().createSQLQuery(sb.toString());
		objects = query.list();
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			
			branchNum = (Integer) object[0];
			bizday = (String) object[1];
			cardUserNum = (Integer) object[2];
			money = (BigDecimal)object[3];
			
			CardDepositCommissionDTO dto = CardDepositCommissionDTO.get(templist, branchNum, bizday, cardUserNum);
			if(dto != null){
				
				dto.setMoney(dto.getMoney().subtract(money));
				if(dto.getMoney().compareTo(BigDecimal.ZERO) < 0){
					dto.setMoney(BigDecimal.ZERO);
				}				
			}
		}
		
		List<String> sellers = new ArrayList<String>();
		if(StringUtils.isNotEmpty(querySellers)){
			String[] array = querySellers.split(",");
			for(int i = 0;i < array.length;i++){
				sellers.add(array[i]);
			}
			
		}
		
		for(int i = 0;i < templist.size();i++){
			CardDepositCommissionDTO dto = templist.get(i);
			if(sellers.size() > 0){
				if(!sellers.contains(dto.getSeller())){
					continue;
				}
			}
			
			CardDepositCommissionDTO newDTO;
			if(groupType == 1){
				newDTO = CardDepositCommissionDTO.get(list, dto.getBranchNum());

			} else {
				newDTO = CardDepositCommissionDTO.get(list, dto.getSeller(), dto.getBranchNum());
				
			}
			if(newDTO == null){
				newDTO = new CardDepositCommissionDTO();
				newDTO.setSeller(dto.getSeller());
				newDTO.setBranchNum(dto.getBranchNum());
				newDTO.setMoney(BigDecimal.ZERO);
				list.add(newDTO);
			}
			newDTO.setMoney(newDTO.getMoney().add(dto.getMoney()));
			
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = newDTO.getDetail(dto.getBizday());
			if(typeAndTwoValuesDTO == null){
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(dto.getBizday());
				typeAndTwoValuesDTO.setMoney(BigDecimal.ZERO);
				newDTO.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(dto.getMoney()));
		}		
		
		return list;
	}

	@Override
	public Object[] findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom, Date dateTo,
			List<Integer> branchNums, String saleType, String timeFrom, String timeTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, count(order_no) as amount ");
		sb.append("from pos_order with(nolock) where system_book_code = :systemBookCode and shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and order_state_code in (5,7) ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (timeFrom.compareTo(timeTo) <= 0) {
			sb.append("and order_time_char >= '" + timeFrom + "' and order_time_char < '" + timeTo + "' ");
			
		} else {
			sb.append("and (order_time_char >= '" + timeFrom + "' or order_time_char < '" + timeTo + "') ");

		}
		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return (Object[]) sqlQuery.uniqueResult();
	}

	@Override
	public Object[] findCustomerAnalysisTimePeriodsByItems(String systemBookCode, Date dateFrom, Date dateTo,
			List<Integer> branchNums, List<Integer> itemNums, String saleType, String timeFrom, String timeTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("count(distinct detail.order_no) as amount ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
		sb.append("where p.system_book_code = :systemBookCode and p.shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and p.order_state_code in (5,7) ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (timeFrom.compareTo(timeTo) <= 0) {
			sb.append("and p.order_time_char >= '" + timeFrom + "' and p.order_time_char < '" + timeTo + "' ");
			
		} else {
			sb.append("and (p.order_time_char >= '" + timeFrom + "' or p.order_time_char < '" + timeTo + "') ");

		}

		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return (Object[]) sqlQuery.uniqueResult();
	}

	@Override
	public List<Object[]> findBranchGradeSummary(SaleAnalysisQueryData queryData) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.item_grade_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit ");
		sb.append("from pos_order_detail as detail with(nolock)  ");
		sb.append("where detail.order_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
		if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
		}
		sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
				+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
		if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
		}
		
		sb.append("and detail.item_grade_num > 0  ");
		 
		if (StringUtils.isNotEmpty(queryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){
				
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(weixinSources));
				
			} else {
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				
			}
		}
		sb.append("group by detail.order_detail_branch_num, detail.item_grade_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findCustomerAnalysisBranchItem(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, List<Integer> itemNums) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" select t.order_detail_branch_num, t.item_num, t.order_detail_item_matrix_num, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 4 then -t.order_detail_amount else order_detail_amount end) as amount,");
		queryStr.append(" sum(case when t.order_detail_state_code = 1 then order_detail_payment_money when t.order_detail_state_code = 4 then -t.order_detail_payment_money end) as money, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 4 then -t.order_detail_gross_profit else t.order_detail_gross_profit end) as profit ");
		queryStr.append(" from pos_order_detail as t with(nolock) ");
		queryStr.append(" where t.order_detail_book_code = :systemBookCode and t.order_detail_branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums) + " and t.item_num is not null ");
		queryStr.append(" and t.order_detail_order_state in (5, 7) ");
		queryStr.append(" and t.order_detail_bizday between :bizFrom and :bizTo");
		if (itemNums != null && itemNums.size() > 0) {
			queryStr.append(" and t.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(branchNums != null && branchNums.size() > 0) {
			queryStr.append(" and t.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		queryStr.append(" and t.order_detail_state_code != 8 ");
		queryStr.append(" group by t.order_detail_branch_num, t.item_num, t.order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(queryStr.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setParameter("bizFrom", DateUtil.getDateShortStr(dtFrom));
		query.setParameter("bizTo", DateUtil.getDateShortStr(dtTo));
		return query.list();
	}

	@Override
	public List<Object[]> findSaleAnalysisByBranchPosItems(SaleAnalysisQueryData queryData) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.branch_num,detail.item_num,detail.order_detail_state_code, ");
		sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
		sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
		sb.append("sum(detail.order_detail_discount) as discount, count(distinct p.branch_num) as branchCount ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
		sb.append("where p.system_book_code = '" + queryData.getSystemBookCode() + "' ");
		if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
		}
		sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
				+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
		sb.append("and p.order_state_code in (5, 7) and detail.item_num is not null ");
		if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
		}
		sb.append("and p.ORDER_CARD_USER_NUM > 0 ");
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
		}
		if (queryData.getIsQueryGrade()) {
			sb.append("and (detail.item_grade_num is null or detail.item_grade_num = 0 ) ");
		}
		if (StringUtils.isNotEmpty(queryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){

				sb.append("and p.order_source in " + AppUtil.getStringParmeList(weixinSources));

			} else {
				sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

			}
		}
		if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
			sb.append("and p.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
		}
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
		}
		if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
			sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = detail.item_num and (");
			for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
				TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
				if(i > 0){
					sb.append(" or ");
				}
				sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
			}

			sb.append(")) ");
		}
		sb.append("group by p.branch_num, detail.item_num, detail.order_detail_state_code ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb = new StringBuffer();
			sb.append("select kitDetail.branch_num kitDetail.item_num,kitDetail.order_kit_detail_state_code, ");
			sb.append("sum(kitDetail.order_kit_detail_amount) as amount, sum(kitDetail.order_kit_detail_payment_money) as money, ");
			sb.append("sum(0.00) as assistAmount, count(kitDetail.item_num) as amount_, ");
			sb.append("sum(kitDetail.order_kit_detail_discount) as discount, count(distinct kitDetail.order_kit_detail_branch_num) as branchCount ");
			sb.append("from pos_order_kit_detail as kitDetail with(nolock) ");
			sb.append("where kitDetail.order_kit_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
			if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
				sb.append("and kitDetail.order_kit_detail_branch_num in "
						+ AppUtil.getIntegerParmeList(queryData.getBranchNums()));
			}
			sb.append("and kitDetail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
					+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
			sb.append("and kitDetail.order_kit_detail_order_state in (5, 7) ");
			if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
				sb.append("and kitDetail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
			}
			if (StringUtils.isNotEmpty(queryData.getSaleType())) {
				List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_WCHAT)){

					sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(weixinSources));

				} else {
					sb.append("and (kitDetail.order_source is null or kitDetail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");

				}
			}
			if (queryData.getOrderSources() != null && queryData.getOrderSources().size() > 0) {
				sb.append("and kitDetail.order_source in " + AppUtil.getStringParmeList(queryData.getOrderSources()));
			}
			if(queryData.getTwoStringValueDatas() != null && queryData.getTwoStringValueDatas().size() > 0){
				sb.append("and exists (select 1 from item_extend_attribute with(nolock) where item_extend_attribute.item_num = kitDetail.item_num and (");
				for(int i = 0;i < queryData.getTwoStringValueDatas().size();i++){
					TwoStringValueData twoStringValueData = queryData.getTwoStringValueDatas().get(i);
					if(i > 0){
						sb.append(" or ");
					}
					sb.append("(item_extend_attribute.attribute_name = '" + twoStringValueData.getKey() + "' and item_extend_attribute.attribute_value like '%" + twoStringValueData.getValue() + "%') ");
				}

				sb.append(")) ");
			}
			sb.append("group by kitDetail.branch_num, kitDetail.item_num, kitDetail.order_kit_detail_state_code ");
			query = currentSession().createSQLQuery(sb.toString());
			List<Object[]> subObjects = query.list();
			objects.addAll(subObjects);
		}
		return objects;
	}






}
