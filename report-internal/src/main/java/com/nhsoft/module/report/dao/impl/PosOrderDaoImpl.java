package com.nhsoft.module.report.dao.impl;



import com.nhsoft.module.report.dao.PosOrderDao;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.query.ProfitAnalysisQueryData;
import com.nhsoft.module.report.query.RetailDetailQueryData;
import com.nhsoft.module.report.query.SaleAnalysisQueryData;
import com.nhsoft.module.report.query.TwoStringValueData;
import com.nhsoft.module.report.shared.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
		sb.append("count(detail.item_num) as saleCount, ");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_commission when detail.order_detail_state_code = 4 then -order_detail_commission end) as commission ");
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
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, boolean queryKit) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("count(detail.item_num) as saleCount ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and detail.order_detail_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and  '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and detail.order_detail_order_state in (5, 7) ");
		sb.append("and detail.order_detail_state_code != 8 and detail.item_num is not null ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("group by detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		if (queryKit) {
			sb = new StringBuffer();
			sb.append("select detail.item_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("count(detail.item_num) as saleCount ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = '" + systemBookCode + "' ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			}
			sb.append("and detail.order_kit_detail_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and  '" + DateUtil.getDateShortStr(dateTo) + "' ");
			sb.append("and detail.order_kit_detail_order_state in (5, 7) ");
			sb.append("and detail.order_kit_detail_state_code != 8  and detail.item_num is not null ");
			if (itemNums != null && itemNums.size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
			}
			sb.append("group by detail.item_num");
			query = currentSession().createSQLQuery(sb.toString());
			List<Object[]> kitObjects = query.list();
			objects.addAll(kitObjects);
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
		sb.append("select p.branch_num, p.order_date as saleDate, detail.item_num, t.item_name as itemName, t.item_code as itemCode ,");
		sb.append("(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as saleMoney, ");
		sb.append("(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as marginMoney, ");
		sb.append("(case when detail.order_detail_state_code = 8 then 0 when detail.order_detail_state_code = 4 then -(detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as costMoney ");
		sb.append("from pos_order as p with(nolock) inner join pos_order_detail as detail with(nolock) on p.order_no = detail.order_no ");
		sb.append("inner join pos_item as t with(nolock) on t.item_num = detail.item_num ");
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
	public List<Object[]> findItemSum(ItemQueryDTO itemQueryDTO) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount,");
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
		sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
		sb.append("count(detail.item_num) as saleCount ");
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
			sb.append("count(detail.item_num) as saleCount ");
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
		sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_commission when detail.order_detail_state_code = 4 then -order_detail_commission end) as commission ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = :systemBookCode ");
		if (itemQueryDTO.getBranchNums() != null && itemQueryDTO.getBranchNums().size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getBranchNums()));
		}
		sb.append("and detail.order_detail_bizday between :dateFrom and :dateTo ");
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
		query.setString("systemBookCode", itemQueryDTO.getSystemBookCode());
		query.setString("dateFrom", DateUtil.getDateShortStr(itemQueryDTO.getDateFrom()));
		query.setString("dateTo", DateUtil.getDateShortStr(itemQueryDTO.getDateTo()));
		List<Object[]> objects = query.list();
		if (itemQueryDTO.getQueryKit()) {
			sb = new StringBuffer();
			sb.append("select detail.order_kit_detail_branch_num, detail.item_num, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_amount else order_kit_detail_amount end) as amount,");
			sb.append("sum(case when detail.order_kit_detail_state_code = 1 then detail.order_kit_detail_payment_money when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_payment_money end) as money, ");
			sb.append("sum(case when detail.order_kit_detail_state_code = 4 then -detail.order_kit_detail_gross_profit else detail.order_kit_detail_gross_profit end) as profit, ");
			sb.append("count(detail.item_num) as saleCount ");
			sb.append("from pos_order_kit_detail as detail with(nolock) ");
			sb.append("where detail.order_kit_detail_book_code = :systemBookCode ");
			if (itemQueryDTO.getBranchNums() != null && itemQueryDTO.getBranchNums().size() > 0) {
				sb.append("and detail.order_kit_detail_branch_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getBranchNums()));
			}
			sb.append("and detail.order_kit_detail_bizday between :dateFrom and :dateTo ");
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
			query.setString("systemBookCode", itemQueryDTO.getSystemBookCode());
			query.setString("dateFrom", DateUtil.getDateShortStr(itemQueryDTO.getDateFrom()));
			query.setString("dateTo", DateUtil.getDateShortStr(itemQueryDTO.getDateTo()));
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
		sb.append("select branch_num, sum(payment_money) ");
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
    public List<RetailDetail> findRetailDetails(RetailDetailQueryData queryData) {

        StringBuffer sb = new StringBuffer();
        sb.append("select p.branch_num, p.order_no, p.order_operator, p.order_machine, p.order_time, ");
        sb.append("detail.item_num, detail.order_detail_amount, detail.order_detail_price, detail.order_detail_payment_money, ");
        sb.append("detail.order_detail_discount, detail.order_detail_item_matrix_num, detail.order_detail_state_code, ");
        sb.append("p.order_sold_by, detail.item_grade_num, p.order_state_code, detail.order_detail_commission, ");
        sb.append("detail.order_detail_memo, detail.order_detail_gross_profit, detail.order_detail_cost ");

        sb.append("from pos_order_detail as detail with(nolock, forceseek) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
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
//			else if(queryData.getExceptionConditon().equals(AppConstants.ANTI_SETTLEMENT)){

//				sb.append("and exists (select 1 from invoice_change as i with(nolock) where i.order_no = p.order_no and i.system_book_code  = '" + queryData.getSystemBookCode() + "' ");
//				if(queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0){
//					sb.append("and i.branch_num in " + AppUtil.getIntegerParmeList(queryData.getBranchNums()));
//				}
//				sb.append("and i.shift_table_bizday between '" + DateUtil.getDateShortStr(queryData.getDtFromShiftTable()) + "' ");
//				sb.append("and '" + DateUtil.getDateShortStr(queryData.getDtToShiftTable()) + "' )");

//			}
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

}