package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.CardDepositDao;
import com.nhsoft.module.report.model.CardDeposit;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.shared.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class CardDepositDaoImpl extends  DaoImpl implements CardDepositDao {
	@Override
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardDeposit.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.depositCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.branchNum"))
				.add(Projections.sum("c.depositCash"))
				.add(Projections.sum("c.depositMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(CardDeposit.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.shiftTableBizday"))
				.add(Projections.sum("c.depositCash"))
				.add(Projections.sum("c.depositMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findUserDateMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select deposit_cust_num, deposit_date, deposit_cash, deposit_money from card_deposit with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
	
	@Override
	public List<Object[]> findBranchPaymentTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardDeposit.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.depositCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.branchNum"))
				.add(Projections.groupProperty("c.depositPaymentTypeName"))
				.add(Projections.sum("c.depositCash"))
				.add(Projections.sum("c.depositMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findBizdayPaymentTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardDeposit.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.depositCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.shiftTableBizday"))
				.add(Projections.groupProperty("c.depositPaymentTypeName"))
				.add(Projections.sum("c.depositCash"))
				.add(Projections.sum("c.depositMoney"))
		);
		return criteria.list();
	}
	
	private Criteria createByCardReportQuery(CardReportQuery cardReportQuery){
		Criteria criteria = currentSession().createCriteria(CardDeposit.class, "c")
				.add(Restrictions.eq("c.systemBookCode", cardReportQuery.getSystemBookCode()));
		if(cardReportQuery.getOperateBranch() != null){
			criteria.add(Restrictions.eq("c.branchNum", cardReportQuery.getOperateBranch()));
		}
		if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0){
			criteria.add(Restrictions.in("c.branchNum", cardReportQuery.getOperateBranchNums()));
		}
		if(cardReportQuery.getBranchNum() != null
				
				|| (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0)){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(CardUser.class, "u")
					.add(Restrictions.eqProperty("u.cardUserNum", "c.depositCustNum"))
					.add(Restrictions.eq("u.systemBookCode", cardReportQuery.getSystemBookCode()));
			if(cardReportQuery.getBranchNum() != null){
				subCriteria.add(Restrictions.eq("u.cardUserEnrollShop", cardReportQuery.getBranchNum()));
			}
			if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
				subCriteria.add(Restrictions.in("u.cardUserEnrollShop", cardReportQuery.getBranchNums()));
			}
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("u.cardUserNum"))));
		}
		if(cardReportQuery.isQueryDate()){
			if(cardReportQuery.getDateFrom() != null){
				criteria.add(Restrictions.ge("c.depositDate", DateUtil.getMinOfDate(cardReportQuery.getDateFrom())));
			}
			if(cardReportQuery.getDateTo() != null){
				criteria.add(Restrictions.le("c.depositDate", DateUtil.getMaxOfDate(cardReportQuery.getDateTo())));
			}
			
		} else {
			
			if(cardReportQuery.getDateFrom() != null){
				criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(cardReportQuery.getDateFrom())));
			}
			if(cardReportQuery.getDateTo() != null){
				criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(cardReportQuery.getDateTo())));
			}
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			criteria.add(Restrictions.eq("c.depositOperator", cardReportQuery.getOperator()));
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getPaymentType())){
			criteria.add(Restrictions.eq("c.depositPaymentTypeName", cardReportQuery.getPaymentType()));
		}
		if(cardReportQuery.getFirstDeposit() != null){
			if(cardReportQuery.getFirstDeposit()){
				criteria.add(Restrictions.eq("c.depositCount", 1));
			} else {
				criteria.add(Restrictions.gt("c.depositCount", 1));
			}
		}
		if(cardReportQuery.getCardUserCardType() != null){
			criteria.add(Restrictions.eq("c.depositCardType", cardReportQuery.getCardUserCardType()));
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
			criteria.add(Restrictions.eq("c.depositPrintedNum", cardReportQuery.getCardPrintNum()));
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getSeller())){
			criteria.add(Restrictions.eq("c.depositSeller", cardReportQuery.getSeller()));
		}
		return criteria;
	}
	
	@Override
	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery) {
		Criteria criteria = createByCardReportQuery(cardReportQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.count("c.depositFid"))
				.add(Projections.sum("c.depositCash"))
				.add(Projections.sum("c.depositMoney"))
				.add(Projections.sum("c.depositPoint"))
				.add(Projections.sum("c.depositInvoice"))
				.add(Projections.sum("c.depositBalance"))
		);
		return (Object[]) criteria.uniqueResult();
	}
	
	@Override
	public List<Object[]> findBranchCardTypePaymentTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardDeposit.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.depositCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.branchNum"))
				.add(Projections.groupProperty("c.depositCardType"))
				.add(Projections.groupProperty("c.depositPaymentTypeName"))
				.add(Projections.sum("c.depositCash"))
				.add(Projections.sum("c.depositMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findSalerSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> salerNames) {
		StringBuffer sb = new StringBuffer();
		sb.append("select deposit_seller, branch_num, sum(deposit_cash) as deposit_cash_sum, sum(deposit_money) as deposit_money_sum from card_deposit with(nolock) ");
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
			sb.append("and deposit_seller in :salerNames ");
		}
		sb.append("group by deposit_seller, branch_num");
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
	public List<Object[]> findBranchBizdayPaymentTypeSum(String systemBookCode, List<Integer> branchNums,
														 Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardDeposit.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.depositCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.branchNum"))
				.add(Projections.groupProperty("c.shiftTableBizday"))
				.add(Projections.groupProperty("c.depositPaymentTypeName"))
				.add(Projections.sum("c.depositCash"))
				.add(Projections.sum("c.depositMoney"))
		);
		return criteria.list();
	}


	@Override
	public BigDecimal getCashMoney(String systemBookCode,
								   List<Integer> branchNums, Date dateFrom, Date dateTo, String type) {
		String sql = "select sum(deposit_cash) as cash " +
				" from card_deposit with(nolock) where system_book_code = '" + systemBookCode + "'  " +
				" and branch_num in " + AppUtil.getIntegerParmeList(branchNums) +
				" and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ";
		if(StringUtils.isNotEmpty(type)){
			sql = sql + "and deposit_payment_type_name = '" + type + "' ";
		}
		Query query = currentSession().createSQLQuery(sql);
		Object object = query.uniqueResult();
		return object == null?BigDecimal.ZERO:(BigDecimal)object;
	}

	@Override
	public List<Object[]> findMoneyByType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select deposit_payment_type_name, sum(deposit_cash) as cash, sum(deposit_money) as money ");
		sb.append("from card_deposit with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by deposit_payment_type_name ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findDateSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
										  String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select %s, sum(deposit_money) as money ");
		sb.append("from card_deposit with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by %s ");

		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			sql = sql.replaceAll("%s", "subString(shift_table_bizday, 0, 7) ");
		} else {
			sql = sql.replaceAll("%s", "shift_table_bizday ");
		}
		Query query = currentSession().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public List<Object[]> findCashGroupByBranch(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo, String type) {
		Criteria criteria = createByType(systemBookCode, branchNums, dateFrom, dateTo, type);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("d.branchNum"))
				.add(Projections.sum("d.depositCash"))
				.add(Projections.count("d.depositFid"))
				.add(Projections.sum("d.depositMoney"))
		);
		return criteria.list();
	}

	@Override
	public List<Object[]> findSumByBizdayBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num,shift_table_bizday, sum(deposit_cash) as cash, sum(deposit_money) as money from card_deposit with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by branch_num, shift_table_bizday ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	private Criteria createByType(String systemBookCode,
								  List<Integer> branchNums, Date dateFrom, Date dateTo, String type){
		Criteria criteria = currentSession().createCriteria(CardDeposit.class, "d")
				.add(Restrictions.eq("d.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("d.branchNum", branchNums));
		}
		if(dateFrom != null) {
			criteria.add(Restrictions.ge("d.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));

		}
		if(dateTo != null) {
			criteria.add(Restrictions.le("d.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(StringUtils.isNotEmpty(type)){
			criteria.add(Restrictions.in("d.depositPaymentTypeName", type.split(",")));
		}
		return criteria;
	}


}
