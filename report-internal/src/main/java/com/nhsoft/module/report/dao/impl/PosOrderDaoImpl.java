package com.nhsoft.module.report.dao.impl;



import com.nhsoft.module.report.dao.PosOrderDao;
import com.nhsoft.module.report.dto.ItemQueryDTO;
import com.nhsoft.module.report.model.Payment;
import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.PosOrder;
import com.nhsoft.module.report.model.PosOrderDetail;
import com.nhsoft.module.report.shared.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
			sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
		} else if (cardReportQuery.isQueryPayment()) {
			sb.append("from payment as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no ");
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
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (queryKit) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		sb.append("and detail.item_num is not null ");
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
		sb.append("from  %s as detail with(nolock)");
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
		if (itemQueryDTO.getItemNums() != null && itemQueryDTO.getItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemQueryDTO.getItemNums()));
		}
		if (itemQueryDTO.getQueryKit()) {
			sb.append("and (detail.order_detail_has_kit is null or  detail.order_detail_has_kit = 0) ");
		}
		if(StringUtils.isNotEmpty(itemQueryDTO.getItemMethod())) {
			sb.append("and exists (select 1 from pos_item with(nolock) where item_num = detail.item_num and item_method = '" + itemQueryDTO.getItemMethod() + "') ");
		}
		sb.append("and detail.item_num is not null ");
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


}