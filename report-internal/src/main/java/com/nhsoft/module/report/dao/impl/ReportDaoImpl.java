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
		//////
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
		// 补扣金额
		StringBuffer sb = new StringBuffer();
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
		List<Object[]> objects = query.list();
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
				bizNum = 0;
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
				bizNum = 0;
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
	public List<TransferPolicyDTO> findTransferPolicyDTOs(PolicyPosItemQuery policyPosItemQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(detail.out_order_detail_qty) as qty, sum(detail.out_order_detail_sale_subtotal) as money, ");
		sb.append("sum(case when detail.out_order_detail_std_price is null then out_order_detail_sale_subtotal else (detail.out_order_detail_std_price * detail.out_order_detail_qty) end) as stdMoney ");
		sb.append("from out_order_detail as detail with(nolock, forceseek) inner join transfer_out_order as t with(nolock, forceseek) on detail.out_order_fid = t.out_order_fid ");
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
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		Integer branchNum = null;
		String operator = null;
		String type = null;
		BigDecimal money = null;
		List<Object[]> objects = sqlQuery.list();
		List<PosReceiveDiffMoneySumDTO> list = new ArrayList<PosReceiveDiffMoneySumDTO>();
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
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByShiftTable(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		StringBuffer sb = new StringBuffer();
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

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if (dateTo != null) {
			sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		}
		List<Object[]> objects = sqlQuery.list();
		List<PosReceiveDiffMoneySumDTO> list = new ArrayList<PosReceiveDiffMoneySumDTO>();
		Integer branchNum = null;
		String bizday = null;
		Integer biznum = null;
		String type = null;
		BigDecimal money = null;
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

}
