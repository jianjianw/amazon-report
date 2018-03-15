package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.PosOrderDao;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.queryBuilder.PosOrderQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class PosOrderDaoImpl extends DaoImpl implements PosOrderDao {

	@Override
	public List<Object[]> findSummaryByBizday(CardReportQuery cardReportQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.shift_table_bizday, count(p.order_no) as amount, sum(p.order_payment_money) as paymentMoney, ");
		sb.append("sum(p.order_discount_money) as discount, sum(p.order_point) as point, sum(p.order_mgr_discount_money) as mgr, ");
		sb.append("sum(p.order_coupon_total_money) as couponMoney ");
		sb.append(createByCardReportQuery(cardReportQuery));
		sb.append("group by p.shift_table_bizday ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findCustomReportByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select shift_table_bizday, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, sum(order_gross_profit) as profit, count(order_no) as amount, sum(order_detail_item_count) as itemAmount, ");
		sb.append("sum(case when order_detail_item_count > 0 then 1 when order_detail_item_count is null then 1 else 0 end) as validOrderNo ");
		sb.append("from pos_order with(nolock) where system_book_code = '" + systemBookCode +"' and order_state_code in (5, 7) ");
		if(branchNums != null) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by shift_table_bizday");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findSummaryByBranch(CardReportQuery cardReportQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.branch_num, count(p.order_no) as amount, sum(p.order_payment_money) as paymentMoney, ");
		sb.append("sum(p.order_discount_money) as discount, sum(p.order_point) as point, sum(p.order_mgr_discount_money) as mgr, ");
		sb.append("sum(p.order_coupon_total_money) as couponMoney ");
		sb.append(createByCardReportQuery(cardReportQuery));
		sb.append("group by p.branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
	private String createByCardReportQuery(CardReportQuery cardReportQuery) {

		StringBuffer sb = new StringBuffer();
		if (cardReportQuery.isQueryDetail()) {
			sb.append("from pos_order p join pos_order_detail detail on p.order_no = detail.order_no ");
		} else if (cardReportQuery.isQueryPayment()) {
			sb.append("from pos_order p join payment detail on p.order_no = detail.order_no ");
		} else {
			sb.append("from pos_order as p with(nolock) ");
		}
		sb.append("where p.system_book_code = '" + cardReportQuery.getSystemBookCode() + "' ");
		if (cardReportQuery.getBranchNum() != null) {
			sb.append("and p.branch_num = " + cardReportQuery.getBranchNum() + " ");
		}
		if (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(cardReportQuery.getBranchNums()));
		}
		if (cardReportQuery.getOperateBranch() != null) {
			sb.append("and p.branch_num = " + cardReportQuery.getOperateBranch() + " ");
		}

		sb.append("and p.shift_table_bizday between '" + DateUtil.getDateShortStr(cardReportQuery.getDateFrom()) + "' ");
		sb.append("and '" + DateUtil.getDateShortStr(cardReportQuery.getDateTo()) + "' ");
		sb.append("and p.order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		if (StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())) {
			sb.append("and p.order_printed_num = '" + AppUtil.filterDangerousQuery(cardReportQuery.getCardPrintNum())
					+ "' ");
		}
		if (StringUtils.isNotEmpty(cardReportQuery.getCardTypeName())) {
			sb.append("and p.order_card_type_desc = '"
					+ AppUtil.filterDangerousQuery(cardReportQuery.getCardTypeName()) + "' ");
		}
		sb.append("and p.order_card_user_num > 0 ");

		if (StringUtils.isNotEmpty(cardReportQuery.getCompareType())) {
			String compareType = cardReportQuery.getCompareType();
			if (compareType.equals(">=")) {
				sb.append("and p.order_payment_money >= " + cardReportQuery.getCompareValue() + " ");
			} else if (compareType.equals(">")) {
				sb.append("and p.order_payment_money > " + cardReportQuery.getCompareValue() + " ");

			} else if (compareType.equals("<")) {
				sb.append("and p.order_payment_money < " + cardReportQuery.getCompareValue() + " ");

			} else if (compareType.equals("<=")) {
				sb.append("and p.order_payment_money <= " + cardReportQuery.getCompareValue() + " ");

			} else {

				sb.append("and p.order_payment_money = " + cardReportQuery.getCompareValue() + " ");
			}
		}
		return sb.toString();
	}
	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo, List<Integer> itemNums, boolean queryKit) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("count(detail.item_num) as saleCount ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_bizday between :dateFrom and :dateTo ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		sb.append("and detail.item_num is not null ");

		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.order_detail_branch_num, detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num, detail.item_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("count(detail.item_num) as saleCount ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and detail.order_kit_detail_bizday between :dateFrom and :dateTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if (itemNums != null && itemNums.size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
			}
			sb.append("and detail.item_num is not null ");
			sb.append("group by detail.order_kit_detail_branch_num, detail.item_num");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", systemBookCode);
			query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
			query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
			objects.addAll(query.list());
		}
		return objects;
	}

	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											Date dateTo, String paymentType) {
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
		if (StringUtils.isNotEmpty(paymentType)) {
			criteria.add(Restrictions.in("p.paymentPayBy", paymentType.split(",")));
		}
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("p.branchNum"))
				.add(Projections.sum("p.paymentMoney")).add(Projections.countDistinct("p.orderNo"))
				.add(Projections.sum("p.paymentReceiptMoney")).add(Projections.sum("p.paymentBuyerMoney"))
				.add(Projections.sum("p.paymentReceive")));
		return criteria.list();
	}

	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(order_total_money) as total, sum(order_gross_profit) as profit, ");
		sb.append("sum(order_round) as round, sum(order_mgr_discount_money) as mgr, sum(order_coupon_total_money - order_coupon_payment_money) as couponDiscount ");
		sb.append("from pos_order with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and order_state_code in (5,7) group by branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findClientsMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
										   List<String> clientFids) {
		Criteria criteria = currentSession().createCriteria(Payment.class, "p").add(
				Restrictions.eq("p.systemBookCode", systemBookCode));
		if (branchNum != null) {
			criteria.add(Restrictions.eq("p.branchNum", branchNum));
		}
		if (clientFids != null && clientFids.size() > 0) {
			criteria.add(Restrictions.in("p.clientFid", clientFids));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("p.paymentTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("p.paymentTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.add(Restrictions.eq("p.paymentPayBy", AppConstants.PAYMENT_SIGN));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("p.clientFid"))
				.add(Projections.sum("p.paymentMoney")).add(Projections.sum("p.paymentBalance")));
		return criteria.list();
	}


	@Override
	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													  Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.item_num, order_detail_item_matrix_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else detail.order_detail_amount end) as amount ");
		sb.append("from pos_order_detail as detail with(nolock)  ");
		sb.append("where detail.order_detail_book_code = :systemBookCode and detail.order_detail_bizday between :dateFrom and :dateTo and detail.item_num is not null ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and detail.order_detail_state_code != 8 ");
		sb.append("group by detail.order_detail_branch_num, detail.item_num, order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		return query.list();
	}

	@Override
	public int countActiveBranchs(String systemBookCode, Date dateFrom, Date dateTo) {
		Criteria criteria = createCriteria(systemBookCode, null, dateFrom, dateTo);
		criteria.setProjection(Projections.countDistinct("p.branchNum"));
		return ((Long) criteria.uniqueResult()).intValue();
	}



	@Override
	public List<Object[]> findItemMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											Date dateTo, boolean queryKit) {

		String bizFrom = DateUtil.getDateShortStr(dateFrom);
		String bizTo = DateUtil.getDateShortStr(dateTo);
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.order_detail_item_matrix_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.item_num, detail.order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.item_num,detail.order_kit_detail_item_matrix_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = '" + systemBookCode + "' ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and detail.order_kit_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			sb.append("group by detail.item_num,detail.order_kit_detail_item_matrix_num ");
			query = currentSession().createSQLQuery(sb.toString());
			List<Object[]> kitObjects = query.list();
			objects.addAll(kitObjects);
		}
		return objects;
	}

	@Override
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(order_no) as amount, count(distinct shift_table_bizday) as bizAmount, ");
		sb.append("sum(order_discount_money + order_round + order_mgr_discount_money) as discountMoney, ");
		sb.append("sum(order_gross_profit) as profit, ");
		sb.append("sum(order_round) as round ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = :systemBookCode and branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between :bizFrom and :bizTo ");
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("bizTo", DateUtil.getDateShortStr(dateTo));
		query.setString("systemBookCode", systemBookCode);
		return query.list();
	}


	@Override
	public List<Object[]> findBranchSumByDateType(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												  Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.branch_num, %s, sum(p.order_payment_money + p.order_coupon_total_money - p.order_mgr_discount_money) as money, sum(p.order_gross_profit) as profit ");
		sb.append("from pos_order as p with(nolock) ");
		sb.append("where p.system_book_code = :systemBookCode and p.shift_table_bizday between :dateFrom and :dateTo ");
		sb.append("and p.order_state_code in (5, 7) ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		}
		sb.append("group by p.branch_num, %s");
		String sql = null;
		if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)) {
			sql = String.format(sb.toString(), "subString(p.shift_table_bizday, 0, 7)",
					"subString(p.shift_table_bizday, 0, 7)");

		} else if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_DATE)) {
			sql = String.format(sb.toString(), "p.shift_table_bizday", "p.shift_table_bizday");

		} else {
			sql = String.format(sb.toString(), "subString(p.shift_table_bizday, 0, 5)",
					"subString(p.shift_table_bizday, 0, 5)");

		}
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		return query.list();
	}


	@Override
	public List<Object[]> findItemSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_discount when detail.order_detail_state_code = 4 then -detail.order_detail_discount end) as discount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -(detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as costMoney ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_bizday between :dateFrom and :dateTo and detail.item_num is not null ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "
					+ AppUtil.getStringParmeList(categoryCodes) + " and detail.item_num = item.item_num ) ");
		}
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.order_detail_branch_num, detail.item_num");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num as branchNum, detail.item_num as itemNum, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_discount when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_discount end) as discount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -(detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as costMoney ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			if (itemNums != null && itemNums.size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
			}
			sb.append("and detail.order_kit_detail_bizday between :dateFrom and :dateTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if (categoryCodes != null && categoryCodes.size() > 0) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "
						+ AppUtil.getStringParmeList(categoryCodes) + " and detail.item_num = item.item_num ) ");
			}
			sb.append("group by detail.order_kit_detail_branch_num, detail.item_num ");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", systemBookCode);
			query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
			query.setString("dateTo", DateUtil.getDateShortStr(dateTo));

			// 有double类型需要强制转型
			query.addScalar("branchNum", StandardBasicTypes.INTEGER).addScalar("itemNum", StandardBasicTypes.INTEGER)
					.addScalar("money", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("profit", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("amount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("discount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("costMoney", StandardBasicTypes.BIG_DECIMAL);
			objects.addAll(query.list());
		}
		return objects;

	}

	@Override
	public List<Object[]> findMoneyGroupByBranchAndItemAndBizday(String systemBookCode, List<Integer> branchNums,
																 Date dateFrom, Date dateTo, boolean queryKit) {
		String bizFrom = DateUtil.getDateShortStr(dateFrom);
		String bizTo = DateUtil.getDateShortStr(dateTo);
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.item_num, detail.order_detail_bizday, ");
		sb.append("sum(case when order_detail_state_code = 4 then -order_detail_payment_money when order_detail_state_code = 1 then order_detail_payment_money end) as money,");
		sb.append("sum(case when order_detail_state_code = 4 then -order_detail_gross_profit else order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when order_detail_state_code = 4 then -order_detail_amount else order_detail_amount end) as amount, ");
		sb.append("sum(case when order_detail_state_code = 4 then -order_detail_discount when order_detail_state_code = 1 then order_detail_discount end) as discount ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.order_detail_branch_num, detail.item_num, detail.order_detail_bizday ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num as branchNum, detail.item_num as itemNum ,detail.order_kit_detail_bizday as biz, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -order_kit_detail_discount when detail.order_kit_detail_state_code = 1 then order_kit_detail_discount end) as discount ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = '" + systemBookCode + "' ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and detail.order_kit_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			sb.append("group by detail.order_kit_detail_branch_num, detail.item_num,detail.order_kit_detail_bizday ");
			query = currentSession().createSQLQuery(sb.toString());

			// 有double类型需要强制转型
			query.addScalar("branchNum", StandardBasicTypes.INTEGER).addScalar("itemNum", StandardBasicTypes.INTEGER)
					.addScalar("biz", StandardBasicTypes.STRING).addScalar("money", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("profit", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("amount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("discount", StandardBasicTypes.BIG_DECIMAL);
			List<Object[]> kitObjects = query.list();
			objects.addAll(kitObjects);
		}
		return objects;
	}


	@Override
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												  Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, subString(detail.order_detail_bizday, 0, 7) as biz, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then detail.order_detail_payment_money end) as cancel, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then detail.order_detail_gross_profit end) as cancelProfit ");
		sb.append("from %s as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode and detail.order_detail_bizday between :dateFrom and :dateTo and detail.item_num is not null ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and detail.order_detail_state_code != 8 ");
		sb.append("group by detail.item_num, subString(detail.order_detail_bizday, 0, 7)");
		String sql = String.format(sb.toString(), "pos_order_detail");
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		return query.list();
	}


	@Override
	public List<Object[]> findAbcItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
										 List<String> categoryCodeList) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.order_detail_item_matrix_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then order_detail_payment_money end) as money,");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit ");
		sb.append("from %s as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode and detail.order_detail_bizday between :dateFrom and :dateTo ");
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.order_detail_branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums));
		sb.append("and detail.item_num is not null ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (categoryCodeList != null && categoryCodeList.size() > 0) {
			sb.append("and exists (select 1 from pos_item as item with(nolock) where item.system_book_code = :systemBookCode and item.item_category_code in (:categoryCodes) and detail.item_num = item.item_num ) ");
		}
		sb.append("group by detail.item_num, detail.order_detail_item_matrix_num ");
		String sql = String.format(sb.toString(), "pos_order_detail");
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		if (categoryCodeList != null && categoryCodeList.size() > 0) {
			query.setParameterList("categoryCodes", categoryCodeList, StandardBasicTypes.STRING);
		}
		return query.list();
	}

	@Override
	public List<Object[]> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
									  List<String> categoryCodes, Integer offset, Integer limit, String sortField, String sortType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.branch_num, p.order_time as saleDate, detail.item_num, t.item_name as itemName, t.item_code as itemCode ,");
		sb.append("(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as saleMoney, ");
		sb.append("(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as marginMoney, ");
		sb.append("(case when detail.order_detail_state_code = 8 then 0 when detail.order_detail_state_code = 4 then -(detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as costMoney ");
		sb.append("from pos_order as p join pos_order_detail as detail on p.order_no = detail.order_no ");
		sb.append("join pos_item as t on detail.item_num = t.item_num ");
		sb.append("where p.system_book_code = :systemBookCode and p.shift_table_bizday between :dateFrom and :dateTo and detail.item_num is not null ");

		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and p.order_state_code in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and t.item_category_code in " + AppUtil.getStringParmeList(categoryCodes));
		}
		if (sortField != null) {
			sb.append("order by " + sortField + " " + sortType);
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		if (offset != null && limit != null) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
		return query.list();
	}

	@Override
	public List<PosOrderDetail> findDetails(String orderNo) {
		String sql = "select * from pos_order_detail with(nolock) where order_no = '" + orderNo + "' ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(PosOrderDetail.class);
		return query.list();
	}


	@Override
	public List<PosOrderDetail> findDetails(List<String> orderNos) {
		String sql = "select * from pos_order_detail with(nolock) where order_no in "
				+ AppUtil.getStringParmeList(orderNos);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(PosOrderDetail.class);
		return query.list();
	}


	@Override
	public List<Object[]> findBizmonthItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												  Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select subString(detail.order_detail_bizday, 0, 7) as month, detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then order_detail_payment_money end) as money ");
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
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and detail.item_num is not null ");
		sb.append("group by subString(detail.order_detail_bizday, 0, 7), detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}


	@Override
	public List<Object[]> findItemSupplierMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													Date dateTo, boolean queryKit) {

		String bizFrom = DateUtil.getDateShortStr(dateFrom);
		String bizTo = DateUtil.getDateShortStr(dateTo);
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.supplier_num, detail.order_detail_item_matrix_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_assist_amount else order_detail_assist_amount end) as assistAmount ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.item_num, detail.supplier_num, detail.order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.item_num,detail.supplier_num, detail.order_kit_detail_item_matrix_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, 0.0 ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = '" + systemBookCode + "' ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and detail.order_kit_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			sb.append("group by detail.item_num, detail.supplier_num, detail.order_kit_detail_item_matrix_num ");
			query = currentSession().createSQLQuery(sb.toString());
			List<Object[]> kitObjects = query.list();
			objects.addAll(kitObjects);
		}
		return objects;
	}


	@Override
	public List<Object[]> findItemSupplierSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom,
														Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.item_num, detail.supplier_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_discount when detail.order_detail_state_code = 4 then -detail.order_detail_discount end) as discount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -(detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as costMoney ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode and detail.order_detail_bizday between :dateFrom and :dateTo and detail.item_num is not null ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "
					+ AppUtil.getStringParmeList(categoryCodes) + " and detail.item_num = item.item_num ) ");
		}
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.order_detail_branch_num, detail.item_num, detail.supplier_num ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num as branchNum, detail.item_num as itemNum, detail.supplier_num as suppliernum, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_discount when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_discount end) as discount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -(detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as costMoney ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			if (itemNums != null && itemNums.size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
			}
			sb.append("and detail.order_kit_detail_bizday between :dateFrom and :dateTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if (categoryCodes != null && categoryCodes.size() > 0) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "
						+ AppUtil.getStringParmeList(categoryCodes) + " and detail.item_num = item.item_num ) ");
			}
			sb.append("group by detail.order_kit_detail_branch_num, detail.item_num, detail.supplier_num ");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", systemBookCode);
			query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
			query.setString("dateTo", DateUtil.getDateShortStr(dateTo));

			// 有double类型需要强制转型
			query.addScalar("branchNum", StandardBasicTypes.INTEGER).addScalar("itemNum", StandardBasicTypes.INTEGER)
					.addScalar("supplierNum", StandardBasicTypes.INTEGER)
					.addScalar("money", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("profit", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("amount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("discount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("costMoney", StandardBasicTypes.BIG_DECIMAL);
			objects.addAll(query.list());
		}
		return objects;

	}


	@Override
	public List<Object[]> findOrderDetailWithSupplier(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													  Date dateTo, List<String> categoryCodes, boolean queryKit) {
		if (queryKit) {
			String bizFrom = DateUtil.getDateShortStr(dateFrom);
			String bizTo = DateUtil.getDateShortStr(dateTo);
			StringBuffer sb = new StringBuffer();
			sb.append("select detail.order_detail_branch_num, detail.order_detail_bizday, detail.item_num, detail.supplier_num, ");
			sb.append("detail.order_detail_payment_money, detail.order_detail_cost, detail.order_detail_gross_profit, ");
			sb.append("detail.order_no, detail.order_detail_std_price, detail.order_detail_price, detail.order_detail_amount, ");
			sb.append("detail.order_detail_state_code ");
			sb.append("from pos_order_detail as detail with(nolock) ");
			sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and detail.order_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
			sb.append("and detail.order_detail_order_state in (5, 7) ");
			sb.append("and detail.order_detail_state_code != 8 ");
			if (queryKit) {
				sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
			}
			SQLQuery query = currentSession().createSQLQuery(sb.toString());
			query.setMaxResults(100000);
			List<Object[]> objects = query.list();
			if (queryKit) {
				sb = new StringBuffer();
				sb.append("select detail.order_kit_detail_branch_num, detail.order_kit_detail_bizday, detail.item_num, detail.supplier_num, ");
				sb.append("detail.order_kit_detail_payment_money, detail.order_kit_detail_cost, detail.order_kit_detail_gross_profit, ");
				sb.append("detail.order_no, detail.order_kit_detail_std_price, detail.order_kit_detail_price, detail.order_kit_detail_amount, ");
				sb.append("detail.order_kit_detail_state_code ");
				sb.append("from pos_order_kit_detail as detail with(nolock) ");
				sb.append("where detail.order_kit_detail_book_code = '" + systemBookCode + "' ");
				if (branchNums != null && branchNums.size() > 0) {
					sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
				}
				sb.append("and detail.order_kit_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
				sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
				sb.append("and detail.order_kit_detail_state_code != 8 ");
				query = currentSession().createSQLQuery(sb.toString());
				query.setMaxResults(100000);
				List<Object[]> kitObjects = query.list();
				objects.addAll(kitObjects);
			}
			return objects;

		} else {

			Criteria criteria = createCriteria(systemBookCode, branchNums, dateFrom, dateTo);
			criteria.createAlias("p.posOrderDetails", "detail");
			criteria.add(Restrictions.isNotNull("detail.itemNum"));
			if (categoryCodes != null && categoryCodes.size() > 0) {
				DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
						.add(Restrictions.in("item.itemCategoryCode", categoryCodes))
						.add(Restrictions.eq("item.systemBookCode", systemBookCode))
						.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
				criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));

			}
			criteria.setProjection(Projections.projectionList()
					.add(Projections.property("p.branchNum"))
					.add(Projections.property("p.orderTime"))
					.add(Projections.property("detail.itemNum"))
					.add(Projections.property("detail.supplierNum"))
					.add(Projections.property("detail.orderDetailPaymentMoney"))
					.add(Projections.property("detail.orderDetailCost"))
					.add(Projections.property("detail.orderDetailGrossProfit")).add(Projections.property("p.orderNo"))
					.add(Projections.property("detail.orderDetailStdPrice"))
					.add(Projections.property("detail.orderDetailPrice"))
					.add(Projections.property("detail.orderDetailAmount"))
					.add(Projections.property("detail.orderDetailStateCode")));
			criteria.setLockMode(LockMode.NONE);
			criteria.setMaxResults(100000);
			List<Object[]> objects = criteria.list();
			return objects;
		}
	}


	@Override
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
										  Date dateTo, List<String> categoryCodes, List<Integer> itemNums, boolean queryKit) {

		String bizFrom = DateUtil.getDateShortStr(dateFrom);
		String bizTo = DateUtil.getDateShortStr(dateTo);
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.supplier_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_assist_amount else order_detail_assist_amount end) as assistAmount ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and exists (select 1 from pos_item with(nolock) where item_num = detail.item_num and item_category_code in "
					+ AppUtil.getStringParmeList(categoryCodes) + ") ");
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.supplier_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.supplier_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("0.0 ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = '" + systemBookCode + "' ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and detail.order_kit_detail_bizday between '" + bizFrom + "' and '" + bizTo + "' ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if (categoryCodes != null && categoryCodes.size() > 0) {
				sb.append("and exists (select 1 from pos_item with(nolock) where item_num = detail.item_num and item_category_code in "
						+ AppUtil.getStringParmeList(categoryCodes) + ") ");
			}
			if (itemNums != null && itemNums.size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
			}
			sb.append("group by detail.supplier_num");
			query = currentSession().createSQLQuery(sb.toString());
			List<Object[]> kitObjects = query.list();
			objects.addAll(kitObjects);
		}
		return objects;
	}


	@Override
	public List<Object[]> findItemSupplierInfoByCategory(String systemBookCode, List<Integer> branchNums,
														 Date dateFrom, Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.supplier_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_discount when detail.order_detail_state_code = 4 then -detail.order_detail_discount end) as discount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -(detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as costMoney ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode and detail.order_detail_bizday between :dateFrom and :dateTo and detail.item_num is not null ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "
					+ AppUtil.getStringParmeList(categoryCodes) + " and detail.item_num = item.item_num ) ");
		}
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.item_num, detail.supplier_num ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.item_num as itemNum, detail.supplier_num as supplierNum, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_discount when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_discount end) as discount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -(detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as costMoney ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			if (itemNums != null && itemNums.size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
			}
			sb.append("and detail.order_kit_detail_bizday between :dateFrom and :dateTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if (categoryCodes != null && categoryCodes.size() > 0) {
				sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in "
						+ AppUtil.getStringParmeList(categoryCodes) + " and detail.item_num = item.item_num ) ");
			}
			sb.append("group by detail.item_num, detail.supplier_num ");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", systemBookCode);
			query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
			query.setString("dateTo", DateUtil.getDateShortStr(dateTo));

			// 有double类型需要强制转型
			query.addScalar("itemNum", StandardBasicTypes.INTEGER).addScalar("supplierNum", StandardBasicTypes.INTEGER)
					.addScalar("money", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("profit", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("amount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("discount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("costMoney", StandardBasicTypes.BIG_DECIMAL);
			objects.addAll(query.list());
		}
		return objects;

	}


	@Override
	public List<Object[]> findItemSum(ItemQueryDTO itemQueryDTO) {///////
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -1 else 1 end) as saleCount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode ");
		if (itemQueryDTO.getBranchNums() != null && itemQueryDTO.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getBranchNums()));
		}
		sb.append("and detail.order_detail_bizday between :dateFrom and :dateTo ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 and detail.item_num is not null ");
		if (itemQueryDTO.getItemNums() != null && itemQueryDTO.getItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getItemNums()));
		}
		if (itemQueryDTO.getQueryKit()) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		if(StringUtils.isNotEmpty(itemQueryDTO.getItemMethod())) {
			sb.append("and exists (select 1 from pos_item with(nolock) where item_num = detail.item_num and item_method = '" + itemQueryDTO.getItemMethod() + "') ");
		}
		sb.append("group by detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", itemQueryDTO.getSystemBookCode());
		query.setString("dateFrom", DateUtil.getDateShortStr(itemQueryDTO.getDateFrom()));
		query.setString("dateTo", DateUtil.getDateShortStr(itemQueryDTO.getDateTo()));
		List<Object[]> objects = query.list();
		if (itemQueryDTO.getQueryKit()) {
			sb = new StringBuffer();
			sb.append("select detail.item_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -1 else 1 end) as saleCount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then (-detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as cost ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (itemQueryDTO.getBranchNums() != null && itemQueryDTO.getBranchNums().size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getBranchNums()));
			}
			sb.append("and detail.order_kit_detail_bizday between :dateFrom and :dateTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8  and detail.item_num is not null ");
			if (itemQueryDTO.getItemNums() != null && itemQueryDTO.getItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getItemNums()));
			}
			if(StringUtils.isNotEmpty(itemQueryDTO.getItemMethod())) {
				sb.append("and exists (select 1 from pos_item with(nolock) where item_num = detail.item_num and item_method = '" + itemQueryDTO.getItemMethod() + "') ");
			}
			sb.append("group by detail.item_num");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", itemQueryDTO.getSystemBookCode());
			query.setString("dateFrom", DateUtil.getDateShortStr(itemQueryDTO.getDateFrom()));
			query.setString("dateTo", DateUtil.getDateShortStr(itemQueryDTO.getDateTo()));
			List<Object[]> kitObjects = query.list();
			objects.addAll(kitObjects);
		}
		return objects;
	}

	@Override
	public List<Object[]> findBranchItemSum(ItemQueryDTO itemQueryDTO) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("count(detail.item_num) as saleCount, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + itemQueryDTO.getSystemBookCode() + "' ");
		if (itemQueryDTO.getBranchNums() != null && itemQueryDTO.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getBranchNums()));
		}
		sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(itemQueryDTO.getDateFrom()) + "' and  '" + DateUtil.getDateShortStr(itemQueryDTO.getDateTo()) + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		sb.append("and detail.item_num is not null ");

		if (itemQueryDTO.getItemNums() != null && itemQueryDTO.getItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getItemNums()));
		}
		if (itemQueryDTO.getQueryKit()) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		if(StringUtils.isNotEmpty(itemQueryDTO.getItemMethod())) {
			sb.append("and exists (select 1 from pos_item with(nolock) where item_num = detail.item_num and item_method = '" + itemQueryDTO.getItemMethod() + "') ");
		}
		sb.append("group by detail.order_detail_branch_num, detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (itemQueryDTO.getQueryKit()) {
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num, detail.item_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("count(detail.item_num) as saleCount, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then (-detail.order_kit_detail_amount * detail.order_kit_detail_cost) else (detail.order_kit_detail_amount * detail.order_kit_detail_cost) end) as cost ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = '" + itemQueryDTO.getSystemBookCode() + "' ");
			if (itemQueryDTO.getBranchNums() != null && itemQueryDTO.getBranchNums().size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getBranchNums()));
			}
			sb.append("and detail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(itemQueryDTO.getDateFrom()) + "' and  '" + DateUtil.getDateShortStr(itemQueryDTO.getDateTo()) + "' ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if (itemQueryDTO.getItemNums() != null && itemQueryDTO.getItemNums().size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getItemNums()));
			}
			if(StringUtils.isNotEmpty(itemQueryDTO.getItemMethod())) {
				sb.append("and exists (select 1 from pos_item with(nolock) where item_num = detail.item_num and item_method = '" + itemQueryDTO.getItemMethod() + "') ");
			}
			sb.append("and detail.item_num is not null ");
			sb.append("group by detail.order_kit_detail_branch_num, detail.item_num");
			query = currentSession().createSQLQuery(sb.toString());
			objects.addAll(query.list());
		}
		return objects;
	}
	@Override
	public List<Object[]> findBranchDetailSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											  Date dateTo, List<Integer> itemNums, boolean queryKit) {

		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_bizday between :dateFrom and :dateTo ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.order_detail_branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and detail.order_kit_detail_bizday between :dateFrom and :dateTo ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8 ");
			if (itemNums != null && itemNums.size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
			}
			sb.append("group by detail.order_kit_detail_branch_num");
			query = currentSession().createSQLQuery(sb.toString());
			query.setString("systemBookCode", systemBookCode);
			query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
			query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
			objects.addAll(query.list());
		}
		return objects;
	}


	private Criteria createCriteria(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(PosOrder.class, "p").add(
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
		criteria.add(Restrictions.in("p.orderStateCode", AppUtil.getNormalPosOrderState()));
		return criteria;
	}


	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(PosOrder.class, "p").add(
				Restrictions.eq("p.systemBookCode", systemBookCode));
		if (branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			criteria.add(Restrictions.eq("p.branchNum", branchNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("p.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));

		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("p.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	public List<Object[]> findCustomReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, sum(order_gross_profit) as profit, count(order_no) as amount, sum(order_detail_item_count) as itemAmount, ");
		sb.append("sum(case when order_detail_item_count > 0 then 1 when order_detail_item_count is null then 1 else 0 end) as validOrderNo ");
		sb.append("from pos_order with(nolock) where system_book_code = '" + systemBookCode +"' and order_state_code in (5, 7) ");
		if(branchNums != null) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by branch_num");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public BigDecimal getPosCash(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		StringBuffer sb = new StringBuffer();
		sb.append("select sum(payment_money) ");
		sb.append("from payment with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and payment_pay_by = '现金' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");

		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		Object object = query.uniqueResult();
		return object == null ? BigDecimal.ZERO : (BigDecimal) object;
	}

	@Override
	public Object[] findRepayCountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(order_no) as amount, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money ");
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
		sb.append("and order_state_code = 9  ");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return (Object[]) sqlQuery.uniqueResult();
	}

	@Override
	public Object[] sumBusiDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(case when client_fid is not null and order_card_user_num = 0 and order_discount_money > 0 then 1 end) as clientDiscountAmount, ");
		sb.append("sum(case when client_fid is not null and order_card_user_num = 0 then order_discount_money end) as clientDiscount, ");
		sb.append("sum(case when order_mgr_discount_money > 0 then 1 end) as mgrDiscountAmount, ");
		sb.append("sum(order_mgr_discount_money) as mgrDiscountMoney ");
		sb.append("from pos_order with(nolock) where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and shift_table_bizday between :bizFrom and :bizTo ");
		sb.append("and order_state_code in (5, 7)");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("bizFrom", DateUtil.getDateShortStr(dtFrom));
		query.setString("bizTo", DateUtil.getDateShortStr(dtTo));
		return (Object[])query.uniqueResult();
	}

	@Override
	public List<Object[]> findBusiDiscountAnalysisBranchs(String systemBookCode, Date dtFrom, Date dtTo,
														  List<Integer> branchNums) {

		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(case when client_fid is not null and order_card_user_num = 0 then order_discount_money end) as clientDiscount, ");
		sb.append("sum(case when order_card_user_num > 0 then order_discount_money end) as cardUserDiscount, ");
		sb.append("sum(case when order_card_user_num = 0 and client_fid is null then order_discount_money end) as otherDiscount, ");
		sb.append("sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as posMoney, ");
		sb.append("sum(order_mgr_discount_money) as mgrDiscountMoney, ");
		sb.append("sum(order_round) as orderRound, ");
		sb.append("sum(case when order_card_user_num = 0 and client_fid is null then order_promotion_discount_money end) as policyDiscount, ");
		sb.append("sum(order_online_discount) as onlineDiscount ");
		sb.append("from pos_order with(nolock) where system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and shift_table_bizday between :bizFrom and :bizTo ");
		sb.append("and order_state_code in (5, 7) group by branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("bizFrom", DateUtil.getDateShortStr(dtFrom));
		query.setString("bizTo", DateUtil.getDateShortStr(dtTo));
		return query.list();
	}

	@Override
	public List<Object[]> findPosCashGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												   Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(payment_money) money ");
		sb.append("from payment with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and payment_pay_by = '现金' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");

		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findRepayDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_no as amount, (order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("order_operator, shift_table_bizday, order_time ");
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
		sb.append("and order_state_code = 9  order by order_time desc ");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findClientDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_no, order_payment_money, order_discount_money, order_operator, shift_table_bizday, order_time ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = :systemBookCode and client_fid is not null and order_card_user_num = 0 and order_discount_money > 0 ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dtFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dtFrom) + "' ");
		}
		if (dtTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dtTo) + "' ");
		}
		sb.append("and order_state_code in (5,7)  order by order_time desc ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}


	@Override
	public List<Object[]> findMgrDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_no, order_payment_money, order_mgr_discount_money, order_operator, shift_table_bizday, order_time ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = :systemBookCode and order_mgr_discount_money > 0 ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dtFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dtFrom) + "' ");
		}
		if (dtTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dtTo) + "' ");
		}
		sb.append("and order_state_code in (5,7)  order by order_time desc ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(order_no) as count, sum(order_gross_profit) as profit ");
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
		if(isMember){
			sb.append("and order_card_user_num > 0 ");
		}
		sb.append("group by branch_num order by branch_num asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();

	}

	@Override
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Boolean isMember) {
		StringBuffer sb = new StringBuffer();
		sb.append("select shift_table_bizday ,sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(order_no) as count,sum(order_gross_profit) as profit ");
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
		if(isMember){
			sb.append("and order_card_user_num > 0 ");
		}
		sb.append("group by shift_table_bizday order by shift_table_bizday asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Boolean isMember) {
		StringBuffer sb = new StringBuffer();
		sb.append("select subString(shift_table_bizday, 0, 7) ,sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(order_no) as count, sum(order_gross_profit) as profit ");
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
		if(isMember){
			sb.append("and order_card_user_num > 0 ");
		}
		sb.append("group by subString(shift_table_bizday, 0, 7) order by subString(shift_table_bizday, 0, 7) asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
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
    public List<RetailDetail> findRetailDetails(RetailDetailQueryData queryData, boolean isFm) {

        StringBuffer sb = new StringBuffer();
        sb.append("select p.branch_num, p.order_no, p.order_operator, p.order_machine, p.order_time, ");
        sb.append("detail.item_num, detail.order_detail_amount, detail.order_detail_price, detail.order_detail_payment_money, ");
        sb.append("detail.order_detail_discount, detail.order_detail_item_matrix_num, detail.order_detail_state_code, ");
        sb.append("p.order_sold_by, detail.item_grade_num, p.order_state_code, detail.order_detail_commission, ");
        sb.append("detail.order_detail_memo, detail.order_detail_gross_profit, detail.order_detail_cost ");
        if(isFm) {
        	sb.append(", p.merchant_num, p.stall_num ");
		}
        sb.append("from pos_order_detail as detail with(nolock, forceseek) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
		sb.append("where p.system_book_code = '" + queryData.getSystemBookCode() + "' ");
        if(queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0){
            sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
        }
        if(isFm) {
			if(queryData.getMerchantNum() != null) {
				sb.append("and p.merchant_num = " + queryData.getMerchantNum() + " ");
			} else {
				sb.append("and p.merchant_num is not null ");
			}
			if(queryData.getStallNum() != null) {
				sb.append("and p.stall_num = " + queryData.getStallNum() + " ");
			} else {
				sb.append("and p.stall_num is not null ");
			}
			if(queryData.getPolicy() != null) {
				if(queryData.getPolicy()) {
					sb.append("and detail.order_detail_policy_fid != '' ");
				} else {
					sb.append("and detail.order_detail_policy_fid = '' ");
				}
			}
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
        if(queryData.getPosItemNums() != null && !queryData.getPosItemNums().isEmpty()){
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
            if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
                sb.append("and (p.order_source is null or p.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");


            } else {

                sb.append("and p.order_source = '" + queryData.getSaleType() + "' ");
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
        query.setMaxResults(50000);
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
			if(isFm){
				retailDetail.setMerchantNum((Integer)object[19]);
				retailDetail.setStallNum((Integer)object[20]);
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
			sb.append("from pos_order as p inner join pos_order_detail as detail on p.order_no = detail.order_no ");

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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
            sb.append("from pos_order_detail as detail ");
            if(queryPosItem){
                sb.append("inner join pos_item as item on detail.item_num = item.item_num ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
			if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
		query.setMaxResults(profitAnalysisQueryData.getMax());
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
		query.setMaxResults(profitAnalysisQueryData.getMax());
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

    @Override
    public List<SalerCommission> findSalerCommissions(String systemBookCode, Date dtFrom, Date dtTo,
                                                      List<Integer> branchNums, List<String> salerNums) {

        List<SalerCommission> list = new ArrayList<SalerCommission>();
        StringBuffer sb = new StringBuffer();
        sb.append("select order_sold_by, branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as receiptMoney, ");
        sb.append("count(order_no) as amount, sum(order_payment_money + order_coupon_total_money) as money, sum(order_commission) as commission, ");
        sb.append("sum(order_detail_item_count) as itemCount, ");
        sb.append("sum(case when order_detail_item_count > 0 then 1 when order_detail_item_count is null then 1 else 0 end) as validOrderNo,");
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
    public List<Object[]> findSalerCommissionsByMoney(String systemBookCode,
                                                      Date dtFrom, Date dtTo, List<Integer> branchNums,
                                                      List<String> salerNums, BigDecimal interval) {
        StringBuffer sb = new StringBuffer();
        sb.append("select order_sold_by, case when(LEFT((order_payment_money + order_coupon_total_money)/:interval,1) = '-') then '-1' when charindex('.', (order_payment_money + order_coupon_total_money)/:interval) >= '3' then '10' else LEFT((order_payment_money + order_coupon_total_money)/:interval,1) end, count(order_no) as count ");
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
        sb.append("group by order_sold_by, case when(left((order_payment_money + order_coupon_total_money)/:interval,1) = '-') then '-1' when charindex('.', (order_payment_money + order_coupon_total_money)/:interval) >= '3' then '10' else LEFT((order_payment_money + order_coupon_total_money)/:interval,1) end");
        String sql = sb.toString();
        sql = sql.replaceAll(":interval", interval.toString());
        SQLQuery query = currentSession().createSQLQuery(sql);
        return query.list();
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
        sb.append("select branch_num as branchNum, shift_table_bizday as bizday, sum(order_payment_money) as paymentMoney, count(order_no) as orderNo, ");
        sb.append("sum(order_coupon_total_money) as conponMoney, sum(order_mgr_discount_money) as mgrDiscount ");
        sb.append("from pos_order with(nolock) ");
        sb.append("where system_book_code = '" + systemBookCode + "' ");
        sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
        sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dtFrom) + "' and '" + DateUtil.getDateShortStr(dtTo) + "' ");
        sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));

		if (StringUtils.isNotEmpty(saleType)) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and order_source = '" + saleType + "' ");
			}
		}

        sb.append("group by branch_num, shift_table_bizday ");
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        query.addScalar("branchNum", StandardBasicTypes.INTEGER)
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
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and order_source = '" + saleType + "' ");
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
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and order_source = '" + saleType + "' ");
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
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and order_source = '" + saleType + "' ");
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
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and order_source = '" + saleType + "' ");
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
        if (branchNums != null && branchNums.size() > 0) {
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
			if(queryData.getMerchantNum() != null) {
				sb.append("and p.merchant_num = " + queryData.getMerchantNum() + " ");
			}
			if(queryData.getStallNum() != null) {
				sb.append("and p.stall_num = " + queryData.getStallNum() + " ");
			}
			if(queryData.getPolicy() != null) {
				if(queryData.getPolicy()) {
					sb.append("and detail.order_detail_policy_fid != '' ");
				} else {
					sb.append("and detail.order_detail_policy_fid = '' ");
				}
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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
                if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
	public List<Object[]> findMerchantSaleAnalysisCommon(SaleAnalysisQueryData queryData) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.order_detail_state_code, ");
		sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
		sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
		sb.append("sum(detail.order_detail_discount) as discount, ");
		sb.append("p.merchant_num, p.stall_num ");
		sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
		sb.append("where detail.order_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
		if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
		}
		if(queryData.getMerchantNum() != null) {
			sb.append("and p.merchant_num = " + queryData.getMerchantNum() + " ");
		}
		if(queryData.getStallNum() != null) {
			sb.append("and p.stall_num = " + queryData.getStallNum() + " ");
		}
		if(queryData.getPolicy() != null) {
			if(queryData.getPolicy()) {
				sb.append("and detail.order_detail_policy_fid != '' ");
			} else {
				sb.append("and detail.order_detail_policy_fid = '' ");
			}
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
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
		sb.append("group by detail.item_num, detail.order_detail_state_code, p.merchant_num, p.stall_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
    public List<Object[]> findSaleAnalysisCommonItemMatrix(SaleAnalysisQueryData queryData) {///////
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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
                if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
	public List<Object[]> findMerchantSaleAnalysisCommonItemMatrix(SaleAnalysisQueryData queryData) {////
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.order_detail_item_matrix_num, detail.order_detail_state_code, ");
		sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
		sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
		sb.append("sum(detail.order_detail_discount) as discount, count(distinct detail.order_detail_branch_num) as branchCount, ");
		sb.append("p.merchant_num, p.stall_num ");
		sb.append("from pos_order_detail as detail with(nolock)  inner join pos_order p with(nolock) on p.order_no = detail.order_no ");
		sb.append("where detail.order_detail_book_code = '" + queryData.getSystemBookCode() + "' ");
		if (queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
		}
		sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFrom())
				+ "' and '" + DateUtil.getDateShortStr(queryData.getDtTo()) + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is not null and p.merchant_num is not null and p.stall_num is not null ");
		if (queryData.getPosItemNums() != null && queryData.getPosItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(queryData.getPosItemNums()));
		}
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb.append("and (detail.order_detail_has_kit = 0 or detail.order_detail_has_kit is null) ");
		}
		if (queryData.getIsQueryGrade()) {
			sb.append("and (detail.item_grade_num is null or detail.item_grade_num = 0 ) ");
		}
		if(queryData.getMerchantNum() != null) {
			sb.append("and p.merchant_num = " + queryData.getMerchantNum() + " ");
		}
		if(queryData.getStallNum() != null) {
			sb.append("and p.stall_num = " + queryData.getStallNum() + " ");
		}
		if(queryData.getPolicy() != null) {
			if(queryData.getPolicy()) {
				sb.append("and detail.order_detail_policy_fid != '' ");
			} else {
				sb.append("and detail.order_detail_policy_fid = '' ");
			}
		}
		if (StringUtils.isNotEmpty(queryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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

		sb.append("group by detail.item_num, detail.order_detail_item_matrix_num, detail.order_detail_state_code, p.merchant_num, p.stall_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<PosOrder> findByShiftTable(ShiftTable shiftTable) {
		Criteria criteria = currentSession().createCriteria(PosOrder.class, "p")
				.add(Restrictions.eq("p.systemBookCode", shiftTable.getId().getSystemBookCode()))
				.add(Restrictions.eq("p.branchNum", shiftTable.getId().getBranchNum()))
				.add(Restrictions.in("p.orderStateCode", AppUtil.getNormalPosOrderState()));

		criteria.add(Restrictions.and(Restrictions.eq("p.shiftTableBizday", shiftTable.getId().getShiftTableBizday()),
				Restrictions.eq("p.shiftTableNum", shiftTable.getId().getShiftTableNum())));
		criteria = criteria.setLockMode("p", LockMode.READ);
		return criteria.list();
	}

	@Override
	public List<Payment> findPaymentsByOrderNos(List<String> orderNos) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from payment with(nolock) where order_no in " + AppUtil.getStringParmeList(orderNos) + " ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(Payment.class);
		return query.list();
	}

	@Override
	public List<PosOrder> findOrderByExternalNo(String systemBookCode, Integer branchNum, String orderExternalNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from pos_order with(nolock, forceseek) where system_book_code = '" + systemBookCode + "' ");
		if(branchNum != null) {
			sb.append("and branch_num = " + branchNum + " ");
		}
		if(StringUtils.isNotEmpty(orderExternalNo)) {
			sb.append("and order_external_no = '" + orderExternalNo + "' ");
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(PosOrder.class);
		return query.list();
	}

	@Override
	public List<PosOrder> findByShiftTables(List<ShiftTable> shiftTables) {
		if (shiftTables == null || shiftTables.size() == 0) {
			return new ArrayList<PosOrder>();
		}
		String systemBookCode = shiftTables.get(0).getId().getSystemBookCode();
		Integer branchNum = shiftTables.get(0).getId().getBranchNum();
		Criteria criteria = currentSession().createCriteria(PosOrder.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode))
				.add(Restrictions.eq("p.branchNum", branchNum));
		Disjunction disjunction = Restrictions.disjunction();
		for (int i = 0; i < shiftTables.size(); i++) {
			ShiftTable shiftTable = shiftTables.get(i);
			disjunction.add(Restrictions.and(
					Restrictions.eq("p.shiftTableBizday", shiftTable.getId().getShiftTableBizday()),
					Restrictions.eq("p.shiftTableNum", shiftTable.getId().getShiftTableNum())));
		}
		criteria.add(disjunction);
		criteria.addOrder(Order.asc("p.orderNo"));
		criteria.setMaxResults(10000);
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
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
			if(queryData.getMerchantNum() != null) {
				sb.append("and p.merchant_num = " + queryData.getMerchantNum() + " ");
			}
			if(queryData.getStallNum() != null) {
				sb.append("and p.stall_num = " + queryData.getStallNum() + " ");
			}
			if(queryData.getPolicy() != null) {
				if(queryData.getPolicy()) {
					sb.append("and detail.order_detail_policy_fid != '' ");
				} else {
					sb.append("and detail.order_detail_policy_fid = '' ");
				}
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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
			sb.append("from pos_order p ");
			sb.append("join pos_order_detail detail on p.order_no = detail.order_no ");
			if(queryPosItem){
				sb.append("join pos_item item on detail.item_num = item.item_num ");

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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
			sb.append("from pos_order_detail detail ");
			if(queryPosItem){
				sb.append("join pos_item item  on item.item_num = detail.item_num ");

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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
			sb.append("from pos_order_kit_detail kitDetail ");
			if (queryPosItem) {
				sb.append("join pos_item item on kitDetail.item_num = item.item_num ");
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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
		if (StringUtils.isNotEmpty(queryData.getSaleType())) {/////
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");


			} else {

				sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (kitDetail.order_source is null or kitDetail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");


				} else {

					sb.append("and kitDetail.order_source = '" + queryData.getSaleType() + "' ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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
					|| StringUtils.contains(elecTicketDTO.getCouponType(), AppConstants.TEAM_TYPE_NUOMI)
					|| StringUtils.contains(elecTicketDTO.getCouponType(), AppConstants.TEAM_TYPE_WECHAT)){

				list.add(elecTicketDTO);
			}
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
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and order_source = '" + saleType + "' ");
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
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and detail.order_source = '" + saleType + "' ");
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
	public List<IntChart> findItemRelatedItemRanks(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                   Date dateTo, Integer itemNum, Integer selectCount) {
		StringBuffer sb = new StringBuffer();
		sb.append("select item_num, count(order_no) as orderNo, ");
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
		sb.append("group by item_num  ");
		sb.append("order by count(order_no) desc ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("itemNum", itemNum);
		if(selectCount != null){
			query.setMaxResults(selectCount);
		}
		List<Object[]> objects = query.list();
		int size = objects.size();

		List<IntChart> intCharts = new ArrayList<IntChart>(size);
		for(int i = 0;i < size;i++){
			Object[] object = objects.get(i);
			IntChart intChart = new IntChart();
			intChart.setItemNum((Integer)object[0]);
			intChart.setIntValue((Integer)object[1]);
			intChart.setBigDecimalValue(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
			intChart.setBigDecimalValue2(object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3]);
			intCharts.add(intChart);
		}
		return intCharts;
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
			if(saleAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and detail.order_source = '" + saleAnalysisQueryData.getSaleType() + "' ");
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
				if(saleAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
			if(saleAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
			if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
	public List<Object[]> findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom, Date dateTo,
														  List<Integer> branchNums, String saleType, String timeFrom, String timeTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, count(order_no) as amount ");
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
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and order_source = '" + saleType + "' ");
			}
		}
		sb.append("group by branch_num");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}


	@Override
	public List<Object[]> findCustomerAnalysisTimePeriodsByItems(String systemBookCode, Date dateFrom, Date dateTo,
																 List<Integer> branchNums, List<Integer> itemNums, String saleType, String timeFrom, String timeTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
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
			if(saleType.equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and detail.order_source = '" + saleType + "' ");
			}
		}
		sb.append("group by branch_num");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
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
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){

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
		query.setMaxResults(50000);
		return query.list();
	}


	@Override
	public List<Object[]> findSaleAnalysisByBranchPosItems(String systemBookCode,SaleAnalysisQueryData queryData) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num,detail.item_num,detail.order_detail_state_code, ");
		sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
		sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_, ");
		sb.append("sum(detail.order_detail_discount) as discount  ");
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
		if (queryData.getIsQueryGrade()) {
			sb.append("and (detail.item_grade_num is null or detail.item_grade_num = 0 ) ");
		}
		if (StringUtils.isNotEmpty(queryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");


			} else {

				sb.append("and detail.order_source = '" + queryData.getSaleType() + "' ");
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
		sb.append("group by detail.order_detail_branch_num, detail.item_num, detail.order_detail_state_code ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryData.getIsQueryCF() != null && queryData.getIsQueryCF()) {
			sb = new StringBuffer();
			sb.append("select kitDetail.order_kit_detail_branch_num, kitDetail.item_num,  kitDetail.order_kit_detail_state_code, ");
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
				if(queryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (kitDetail.order_source is null or kitDetail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");


				} else {

					sb.append("and kitDetail.order_source = '" + queryData.getSaleType() + "' ");
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
			sb.append("group by kitDetail.order_kit_detail_branch_num, kitDetail.item_num, kitDetail.order_kit_detail_state_code ");
			query = currentSession().createSQLQuery(sb.toString());
			List<Object[]> subObjects = query.list();
			objects.addAll(subObjects);
		}
		return objects;
	}

	@Override
	public List<Object[]> findBranchCouponSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		StringBuilder sb = new StringBuilder();
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
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findBranchDiscountSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		StringBuilder sb = new StringBuilder();
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

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findMerchantDiscountSummary(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select merchant_num, sum(order_discount_money + order_round + order_mgr_discount_money) as money ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " and merchant_num is not null ");
		if (merchantNum != null) {
			sb.append("and merchant_num = " + merchantNum + " ");
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by merchant_num order by merchant_num asc");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findStallDiscountSummary(String systemBookCode, Integer branchNum, Integer merchantNum, Integer stallNum, Date dateFrom, Date dateTo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select merchant_num, stall_num, sum(order_discount_money + order_round + order_mgr_discount_money) as money ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " and merchant_num is not null ");
		if (merchantNum != null) {
			sb.append("and merchant_num = " + merchantNum + " ");
		}
		if(stallNum != null) {
			sb.append("and stall_num = " + stallNum + " ");
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by merchant_num, stall_num order by merchant_num asc, stall_num asc");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findBranchBizdayCouponSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		StringBuilder sb = new StringBuilder();
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
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());

		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findBranchBizdayDiscountSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		StringBuilder sb = new StringBuilder();
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

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findMerchantBizdayDiscountSummary(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select merchant_num, shift_table_bizday, sum(order_discount_money + order_round + order_mgr_discount_money) as money ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " and merchant_num is not null ");
		if (merchantNum != null) {
			sb.append("and merchant_num = " + merchantNum + " ");
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by merchant_num, shift_table_bizday order by merchant_num asc");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	public List<Object[]> findCouponSummary(String systemBookCode, List<Integer> branchNums,
											Date dateFrom, Date dateTo){

		StringBuilder sb = new StringBuilder();
		sb.append("select p.branch_num, p.shift_table_bizday, p.order_machine, detail.order_detail_item, sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money ");
		sb.append("from pos_order p join pos_order_detail detail on p.order_no = detail.order_no ");
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
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();

	}

	@Override
	public List<Object[]> findBranchShiftTablePaymentSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
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
		return sqlQuery.list();

	}

	@Override
	public List<Object[]> findBranchShiftTablePaymentSummary(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo, String casher) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o.merchant_num, o.shift_table_bizday, o.shift_table_num, p.payment_pay_by, sum(p.payment_money) as money, sum(p.payment_balance) as balance ");
		sb.append("from payment as p with(nolock) inner join pos_order as o with(nolock) on p.order_no = o.order_no ");
		sb.append("where o.system_book_code = '" + systemBookCode + "' and o.branch_num = " + branchNum + " and o.merchant_num is not null ");
		if (merchantNum != null) {
			sb.append("and o.merchant_num = " + merchantNum + " ");
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
		sb.append("group by o.merchant_num, o.shift_table_bizday, o.shift_table_num, p.payment_pay_by ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	public List<Object[]> findBranchShiftTableCouponSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher){

		StringBuilder sb = new StringBuilder();
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
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	public List<Object[]> findBranchOperatorPayByMoneySummary(String systemBookCode,
																	   List<Integer> branchNums, Date dateFrom, Date dateTo){
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
		 return sqlQuery.list();

	}

	@Override
	public List<PosOrder> findSettled(PosOrderQuery posOrderQuery, int offset, int limit) {

		String sql = "select p.* " + createByPosOrderQuery(posOrderQuery);

		if (posOrderQuery.isPage()) {

			if (posOrderQuery.getSortField() != null) {
				if(posOrderQuery.getSortField().equals("orderReceiveMoney")){
					if (posOrderQuery.getSortType().equals("ASC")) {
						sql = sql + "order by (order_payment_money + order_coupon_total_money - order_mgr_discount_money) asc ";
					} else {
						sql = sql + "order by (order_payment_money + order_coupon_total_money - order_mgr_discount_money) desc ";
					}

				} else {
					if (posOrderQuery.getSortType().equals("ASC")) {
						sql = sql + "order by p." + AppUtil.getDBColumnName(posOrderQuery.getSortField()) + " asc ";
					} else {
						sql = sql + "order by p." + AppUtil.getDBColumnName(posOrderQuery.getSortField()) + " desc ";
					}

				}

			} else {
				sql = sql + "order by p.order_no ";
			}
		} else {
			sql = sql + "order by p.order_no ";
		}

		SQLQuery query = currentSession().createSQLQuery(sql);

		if (posOrderQuery.isPage()) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);

		} else {
			query.setMaxResults(posOrderQuery.getMax());
		}
		query.addEntity(PosOrder.class);
		return query.list();
	}

	private String createByPosOrderQuery(PosOrderQuery posOrderQuery){
		StringBuilder sb = new StringBuilder();
		String ip = AppUtil.getServerIp();
		if(ip != null){
			if(ip.equals("121.41.22.86")){
				sb.append("from pos_order as p with(nolock,index=INDEX_20170411_INCLUDE_NO_PAYMENT_MGR_COUPON_DISCOUNT_ROUND_PROFIT_PDIS_CLIENT) ");

			} else if(ip.equals("42.121.65.234")){
				sb.append("from pos_order as p with(nolock,index=INDEX_POS_ORDER_BOOK_BRANCH_BIZ_STATE_CARD_INCLUDE_NO_PAYMENT_MGR_COUPON_DISCOUNT_ROUND_PROFIT) ");

			} else if(ip.equals("116.62.107.203")){
				sb.append("from pos_order as p with(nolock,index=IX_20170615_INCLUDE_PAYMENT_MGR_COUPON_DISCOUNT_ROUND_PROFIT) ");

			} else {
				sb.append("from pos_order as p with(nolock, forceseek) ");

			}
		} else {
			sb.append("from pos_order as p with(nolock, forceseek) ");

		}
		boolean queryPayment = false;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(posOrderQuery.getPaymentType())) {
			if (!posOrderQuery.getPaymentType().equals(AppConstants.PAYMENT_COUPON)) {
				queryPayment = true;
			}
		}
		if(queryPayment){
			sb.append("inner join payment with(nolock) on p.order_no = payment.order_no ");
		}

		sb.append("where p.system_book_code = '" + posOrderQuery.getSystemBookCode() + "' ");
		if (posOrderQuery.getBranchNums() != null && posOrderQuery.getBranchNums().size() > 0) {
			sb.append("and p.branch_num in " + AppUtil.getIntegerParmeList(posOrderQuery.getBranchNums()));
		}
		if(posOrderQuery.getMerchantNum() != null) {
			sb.append("and p.merchant_num = " + posOrderQuery.getMerchantNum() + " ");
		}
		if(posOrderQuery.getStallNum() != null) {
			sb.append("and p.stall_num = " + posOrderQuery.getStallNum() + " ");
		}
		if (posOrderQuery.getDateFrom() != null) {
			sb.append("and p.shift_table_bizday >= '" + DateUtil.getDateShortStr(posOrderQuery.getDateFrom()) + "' ");
		}
		if (posOrderQuery.getDateTo() != null) {
			sb.append("and p.shift_table_bizday <= '" + DateUtil.getDateShortStr(posOrderQuery.getDateTo()) + "' ");
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(posOrderQuery.getOrderNo())) {
			sb.append("and p.order_no like '%" + posOrderQuery.getOrderNo() + "%' ");
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(posOrderQuery.getClientFid())) {

			sb.append("and p.client_fid = '" + posOrderQuery.getClientFid() + "' ");

		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(posOrderQuery.getPaymentType())) {
			if (posOrderQuery.getPaymentType().equals(AppConstants.PAYMENT_COUPON)) {
				sb.append("and p.order_coupon_total_money > 0 ");
			} else if(queryPayment){

				if (posOrderQuery.getPaymentType().equals(AppConstants.PAYMENT_WEIXIN)) {

					sb.append("and payment_pay_by like '" + AppConstants.PAYMENT_WEIXIN + "%' ");

				} else {

					sb.append("and payment_pay_by = '" + posOrderQuery.getPaymentType() + "' ");

				}
			}

		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(posOrderQuery.getSaleType())) {
			int stateCode = 0;

			if (posOrderQuery.getSaleType().equals(AppConstants.POS_ORDER_DETAIL_STATE_SALE_NAME)) {
				stateCode = AppConstants.POS_ORDER_DETAIL_STATE_SALE;

			} else if (posOrderQuery.getSaleType().equals(AppConstants.POS_ORDER_DETAIL_STATE_PRESENT_NAME)) {

				stateCode = AppConstants.POS_ORDER_DETAIL_STATE_PRESENT;

			} else if (posOrderQuery.getSaleType().equals(AppConstants.POS_ORDER_DETAIL_STATE_CANCEL_NAME)) {

				stateCode = AppConstants.POS_ORDER_DETAIL_STATE_CANCEL;

			} else if (posOrderQuery.getSaleType().equals(AppConstants.POS_ORDER_DETAIL_STATE_REMOVE_NAME)) {

				stateCode = AppConstants.POS_ORDER_DETAIL_STATE_REMOVE;

			}

			sb.append("and exists (select 1 from pos_order_detail with(nolock) where ORDER_DETAIL_BOOK_CODE = '" + posOrderQuery.getSystemBookCode() + "' ");
			if (posOrderQuery.getBranchNums() != null && posOrderQuery.getBranchNums().size() > 0) {
				sb.append("and ORDER_DETAIL_BRANCH_NUM in " + AppUtil.getIntegerParmeList(posOrderQuery.getBranchNums()));
			}
			if (posOrderQuery.getDateFrom() != null) {
				sb.append("and ORDER_DETAIL_BIZDAY >= '" + DateUtil.getDateShortStr(posOrderQuery.getDateFrom()) + "' ");
			}
			if (posOrderQuery.getDateTo() != null) {
				sb.append("and ORDER_DETAIL_BIZDAY <= '" + DateUtil.getDateShortStr(posOrderQuery.getDateTo()) + "' ");
			}
			sb.append("and order_detail_state_code = " + stateCode + " and pos_order_detail.order_no = p.order_no ) ");
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(posOrderQuery.getOrderOperator())) {
			sb.append("and p.order_operator = '" + posOrderQuery.getOrderOperator() + "' ");
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(posOrderQuery.getOrderExternalNo())) {
			sb.append("and p.order_external_no = '" + posOrderQuery.getOrderExternalNo() + "' ");

		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(posOrderQuery.getOrderState())) {
			if (posOrderQuery.getOrderState().equals(AppConstants.POS_ORDER_REPAY.getStateName())) {

				sb.append("and exists (select 1 from invoice_change with(nolock) where invoice_change.order_no = p.order_no and system_book_code = '" + posOrderQuery.getSystemBookCode() + "'  ) ");


			} else {
				sb.append("and p.order_state_name like '%" + posOrderQuery.getOrderState() + "' ");

			}
		}

		if( posOrderQuery.getOrderSources() != null &&posOrderQuery.getOrderSources().size()>0 ){
			sb.append("and p.order_source in " + AppUtil.getStringParmeList(posOrderQuery.getOrderSources()));
		}
		return sb.toString();
	}

	@Override
	public List<Object[]> findOrderPaymentMoneys(List<String> orderNos) {
		StringBuilder sb = new StringBuilder();
		sb.append("select order_no, payment_pay_by, payment_money ");
		sb.append("from payment with(nolock) ");
		sb.append("where order_no in " + AppUtil.getStringParmeList(orderNos));
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findOrderPaymentMoneys(PosOrderQuery posOrderQuery) {
		StringBuilder sb = new StringBuilder();
		sb.append("select order_no, payment_pay_by, payment_money ");
		sb.append("from payment with(nolock) ");
		sb.append("where order_no in ( select top(:batchsize) p.order_no ").append(createByPosOrderQuery(posOrderQuery)).append(" order by order_no )");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setInteger("batchsize", posOrderQuery.getMax());
		return query.list();
	}

	@Override
	public Object[] sumSettled(PosOrderQuery posOrderQuery) {

		List<Integer> stateCodes = PosOrder.getNormalPosOrderState();
		StringBuilder sb = new StringBuilder();
//		if (StringUtils.isNotEmpty(posOrderQuery.getOrderState())
//				&& (posOrderQuery.getOrderState().equals(AppConstants.POS_ORDER_REPAY.getStateName())
//						|| posOrderQuery.getOrderState().equals(AppConstants.POS_ORDER_CANCEL.getStateName()))) {

		sb.append("select count(p.order_no) as amount, sum(case when p.order_state_code in "
				+ AppUtil.getIntegerParmeList(stateCodes) + " then p.order_total_money end) as orderTotalMoney, ");
		sb.append("sum(case when p.order_state_code in " + AppUtil.getIntegerParmeList(stateCodes)
				+ " then p.order_coupon_total_money end) as orderCouponTotalMoney, ");
		sb.append("sum(case when p.order_state_code in " + AppUtil.getIntegerParmeList(stateCodes)
				+ " then p.order_discount_money end) as orderDiscountMoney, ");
		sb.append("sum(case when p.order_state_code in " + AppUtil.getIntegerParmeList(stateCodes)
				+ " then p.order_payment_money end) as orderPaymentMoney, ");
		sb.append("sum(case when p.order_state_code in " + AppUtil.getIntegerParmeList(stateCodes)
				+ " then p.order_round end) as orderRound, ");
		sb.append("sum(case when p.order_state_code in " + AppUtil.getIntegerParmeList(stateCodes)
				+ " then p.order_mgr_discount_money end) as orderMgrDiscountMoney, ");
		sb.append("sum(case when p.order_state_code in " + AppUtil.getIntegerParmeList(stateCodes)
				+ " then p.order_coupon_payment_money end) as orderCouponPaymentMoney ");
		sb.append(createByPosOrderQuery(posOrderQuery));


//		} else {
//			sb.append("select count(order_no) as amount, sum(order_total_money) as orderTotalMoney, sum(order_coupon_total_money) as orderCouponTotalMoney, ");
//			sb.append("sum(order_discount_money) as orderDiscountMoney, sum(order_payment_money) as orderPaymentMoney, sum(order_round) as orderRound, ");
//			sb.append("sum(order_mgr_discount_money) as orderMgrDiscountMoney , sum(order_coupon_payment_money) as orderCouponPaymentMoney ");
//			sb.append(createByPosOrderQuery(posOrderQuery));
//
//		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		Object[] objects = (Object[]) query.uniqueResult();
		Integer amount = objects[0] == null ? 0 : (Integer) objects[0];
		objects[0] = Long.valueOf(amount);
		return objects;
	}

	@Override
	public List<Object[]> findSummaryByPrintNum(CardReportQuery cardReportQuery) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p.order_printed_num as printedNum , p.order_card_user as cardUserName , p.order_card_type_desc as cardType, p.order_card_user_num as cardUserNum, sum(p.order_payment_money) as paymentMoney, ");
		sb.append("sum(p.order_discount_money) as discount, sum(p.order_point) as point, sum(p.order_mgr_discount_money) as mgrMoney, ");
		sb.append("sum(p.order_coupon_total_money) as couponMoney ");
		sb.append(createByCardReportQuery(cardReportQuery));
		sb.append("group by p.order_printed_num, p.order_card_user, p.order_card_type_desc, p.order_card_user_num ");

		if(cardReportQuery.isPaging()){
			if (StringUtils.isNotEmpty(cardReportQuery.getSortField())){
				sb.append("order by " + cardReportQuery.getSortField() + " " +cardReportQuery.getSortType());
			}
		}else{
			sb.append("order by printedNum asc");
		}

		Query query = currentSession().createSQLQuery(sb.toString());
		if(cardReportQuery.isPaging()) {
			query.setFirstResult(cardReportQuery.getOffset());
			query.setMaxResults(cardReportQuery.getLimit());
		}
		return query.list();
	}

	@Override
	public Object[] findCountByPrintNum(CardReportQuery cardReportQuery) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) as count_, sum(paymentMoney) as paymentMoney_ , sum(discount) as discount_ , sum(point) as point_ from ( ");
		sb.append("select sum(p.order_payment_money) as paymentMoney, sum(p.order_discount_money) as discount, sum(p.order_point) as point ");
		sb.append(createByCardReportQuery(cardReportQuery));
		sb.append("group by p.order_printed_num, p.order_card_user, p.order_card_type_desc, p.order_card_user_num ");
		sb.append(") as temp");
		Query query = currentSession().createSQLQuery(sb.toString());
		return (Object[])query.uniqueResult();
	}

	@Override
	public List<Object[]> findDaySaleAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, shift_table_bizday, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(shift_table_bizday) as amount, sum(order_gross_profit) as profit,count(distinct shift_table_bizday) as bizAmount, ");
		sb.append("sum(case when order_card_user_num > 0 then (order_payment_money + order_coupon_total_money - order_mgr_discount_money) end) as memberMoney, ");//会员营业额
		sb.append("count(case when order_card_user_num > 0 then shift_table_bizday end) as memberAmount, ");//会员客单量
		sb.append("sum(case when order_card_user_num > 0 then order_gross_profit end) as memberProfit ");//会员毛利

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

		sb.append("group by branch_num, shift_table_bizday order by branch_num asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findMonthSaleAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, subString(shift_table_bizday, 0, 7) as month, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, ");
		sb.append("count(shift_table_bizday) as amount, sum(order_gross_profit) as profit, count(distinct shift_table_bizday) as bizAmount, ");
		sb.append("sum(case when order_card_user_num > 0 then (order_payment_money + order_coupon_total_money - order_mgr_discount_money) end) as memberMoney, ");//会员营业额
		sb.append("count(case when order_card_user_num > 0 then shift_table_bizday end) as memberAmount, ");//会员客单量
		sb.append("sum(case when order_card_user_num > 0 then order_gross_profit end) as memberProfit ");//会员毛利

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
		sb.append("group by branch_num, subString(shift_table_bizday, 0, 7) order by branch_num asc");

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		return sqlQuery.list();
	}

	public String createByCustomerAnalysisQuery(SaleAnalysisQueryData saleAnalysisQueryData){
		StringBuilder sb = new StringBuilder();
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + saleAnalysisQueryData.getSystemBookCode() + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(saleAnalysisQueryData.getBranchNums()));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtFrom()) + "' and '" + DateUtil.getDateShortStr(saleAnalysisQueryData.getDtTo()) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));

		if (StringUtils.isNotEmpty(saleAnalysisQueryData.getSaleType())) {
			List<String> weixinSources = AppUtil.getPosOrderOnlineSource();
			if(saleAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
				sb.append("and (order_source is null or order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
			} else {
				sb.append("and order_source = '" + saleAnalysisQueryData.getSaleType() + "' ");
			}
		}
		return sb.toString();
	}


	@Override
	public List<Object[]> findCustomerAnalysisHistorysByPage(SaleAnalysisQueryData saleAnalysisQueryData) {

		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num as branchNum, shift_table_bizday as bizday, sum(order_payment_money) as paymentMoney, count(order_no) as orderNo, ");
		sb.append("sum(order_coupon_total_money) as conponMoney, sum(order_mgr_discount_money) as mgrDiscount ");
		sb.append(createByCustomerAnalysisQuery(saleAnalysisQueryData));
		sb.append("group by branch_num, shift_table_bizday ");

		if(saleAnalysisQueryData.isPage()){
			if (StringUtils.isNotEmpty(saleAnalysisQueryData.getSortField())){
				sb.append("order by " + saleAnalysisQueryData.getSortField() + " " + saleAnalysisQueryData.getSortType());
			}
		}else{
			sb.append("order by branchNum asc");
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addScalar("branchNum", StandardBasicTypes.INTEGER)
				.addScalar("bizday", StandardBasicTypes.STRING)
				.addScalar("paymentMoney", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("orderNo", StandardBasicTypes.LONG)
				.addScalar("conponMoney", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("mgrDiscount", StandardBasicTypes.BIG_DECIMAL);

		if(saleAnalysisQueryData.isPage()){
			query.setFirstResult(saleAnalysisQueryData.getOffset());
			query.setMaxResults(saleAnalysisQueryData.getLimit());
		}
		return query.list();
	}

	@Override
	public Object[] findCustomerAnalysisHistorysCount(SaleAnalysisQueryData saleAnalysisQueryData) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) as count_, sum(paymentMoney) as paymentMoney_ , sum(orderNo) as orderNo_ from ( ");
		sb.append("select sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money ) as paymentMoney, count(order_no) as orderNo ");
		sb.append(createByCustomerAnalysisQuery(saleAnalysisQueryData));
		sb.append("group by branch_num, shift_table_bizday ");
		sb.append(") temp ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		return (Object[]) query.uniqueResult();
	}

	@Override
	public List<Object[]> findProfitAnalysisByBranchAndItemByPage(ProfitAnalysisQueryData profitAnalysisQueryData) {

		StringBuffer sb = new StringBuffer();

		if (profitAnalysisQueryData.isQueryClient()
				|| (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0)) {

			sb.append("select p.branch_num as branchNum, detail.item_num as itemNum, detail.order_detail_item_matrix_num as matrixNum, ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by p.branch_num, detail.item_num, detail.order_detail_item_matrix_num ");
			sb.append("order by branchNum,itemNum,matrixNum,profit,amount,money,cost ");
		} else {
			sb.append("select detail.order_detail_branch_num as branchNum, detail.item_num as itemNum, detail.order_detail_item_matrix_num as matrixNum, ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and detail.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by detail.order_detail_branch_num, detail.item_num, detail.order_detail_item_matrix_num ");
			sb.append("order by branchNum,itemNum,matrixNum,profit,amount,money,cost ");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", profitAnalysisQueryData.getSystemBookCode());
		query.setString("bizFrom", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableFrom()));
		query.setString("bizTo", DateUtil.getDateShortStr(profitAnalysisQueryData.getShiftTableTo()));
		if(profitAnalysisQueryData.isPage()){
			query.setFirstResult(profitAnalysisQueryData.getOffset());
			query.setMaxResults(profitAnalysisQueryData.getLimit());
		}
		return query.list();

	}

	@Override
	public List<Object> findProfitAnalysisByBranchAndItemCount(ProfitAnalysisQueryData profitAnalysisQueryData) {
		StringBuffer sb = new StringBuffer();

		if (profitAnalysisQueryData.isQueryClient()
				|| (profitAnalysisQueryData.getClientFids() != null && profitAnalysisQueryData.getClientFids().size() > 0)) {

			sb.append("select p.branch_num ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
				}
			}
			if (profitAnalysisQueryData.getOrderSources() != null && profitAnalysisQueryData.getOrderSources().size() > 0) {
				sb.append("and p.order_source in " + AppUtil.getStringParmeList(profitAnalysisQueryData.getOrderSources()));
			}
			sb.append("group by p.branch_num, detail.item_num, detail.order_detail_item_matrix_num ");
		} else {
			sb.append("select detail.order_detail_branch_num ");
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
				if(profitAnalysisQueryData.getSaleType().equals(AppConstants.POS_ORDER_SALE_TYPE_BRANCH)){
					sb.append("and (detail.order_source is null or detail.order_source not in " + AppUtil.getStringParmeList(weixinSources) + ") ");
				} else {
					sb.append("and detail.order_source = '" + profitAnalysisQueryData.getSaleType() + "' ");
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


}