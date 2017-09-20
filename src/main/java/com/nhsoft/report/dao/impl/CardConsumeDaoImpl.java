package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.CardConsumeDao;
import com.nhsoft.report.model.CardConsume;
import com.nhsoft.report.shared.queryBuilder.CardReportQuery;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class CardConsumeDaoImpl extends  DaoImpl implements CardConsumeDao {
	
	@Override
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardConsume.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() != 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.consumeCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.branchNum"))
				.add(Projections.sum("c.consumeMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(CardConsume.class, "c")
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
				.add(Projections.sum("c.consumeMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public Integer countReward(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria  criteria = currentSession().createCriteria(CardConsume.class, "c")
				.add(Restrictions.eq("c.consumeWinningFlag", true))
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNum != null){
			criteria.add(Restrictions.eq("c.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}
	
	@Override
	public List<Object[]> findUserDateMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select consume_cust_num, consume_date, consume_money from card_consume with(nolock) ");
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
	public List<Object[]> findBranchCategorySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardConsume.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() != 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.consumeCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.branchNum"))
				.add(Projections.groupProperty("c.consumeCategory"))
				.add(Projections.sum("c.consumeMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findBizdayCategorySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardConsume.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() != 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.consumeCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.shiftTableBizday"))
				.add(Projections.groupProperty("c.consumeCategory"))
				.add(Projections.sum("c.consumeMoney"))
		);
		return criteria.list();
	}
	
	private String createByCardReportQuery(CardReportQuery cardReportQuery){
		StringBuffer sb = new StringBuffer();
		sb.append("from card_consume with(nolock) where system_book_code = '" + cardReportQuery.getSystemBookCode() + "' ");
		if (cardReportQuery.getOperateBranch() != null) {
			sb.append("and branch_num = " + cardReportQuery.getOperateBranch() + " ");
		}
		if (cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(cardReportQuery.getOperateBranchNums()));
		}
		if(cardReportQuery.getBranchNum() != null
				|| (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0)){
			
			sb.append("and exists (select 1 from card_user with(nolock) where card_user.card_user_num = card_consume.consume_cust_num ");
			if(cardReportQuery.getBranchNum() != null){
				sb.append("and card_user_enroll_shop = " + cardReportQuery.getBranchNum() + " ");
			}
			if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
				sb.append("and card_user_enroll_shop in " + AppUtil.getIntegerParmeList(cardReportQuery.getBranchNums()));
			}
			sb.append(") ");
			
		}
		if(cardReportQuery.isQueryDate()){
			if (cardReportQuery.getDateFrom() != null) {
				sb.append("and consume_date >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(cardReportQuery.getDateFrom())) + "' ");
			}
			if (cardReportQuery.getDateTo() != null) {
				sb.append("and consume_date <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(cardReportQuery.getDateTo())) + "' ");
				
			}
		} else {
			if (cardReportQuery.getDateFrom() != null) {
				sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(cardReportQuery.getDateFrom()) + "' ");
			}
			if (cardReportQuery.getDateTo() != null) {
				sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(cardReportQuery.getDateTo()) + "' ");
				
			}
		}
		if (cardReportQuery.getPrizeCard() != null) {
			sb.append("and consume_winning_flag = " + BooleanUtils.toString(cardReportQuery.getPrizeCard(), "1", "0") + " ");
		}
		if (StringUtils.isNotEmpty(cardReportQuery.getOperator())) {
			sb.append("and consume_operator = '" + cardReportQuery.getOperator() + "' ");
		}
		if( cardReportQuery.getCardUserCardType() != null){
			sb.append("and consume_card_type = " + cardReportQuery.getCardUserCardType() + " ");
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
			sb.append("and consume_printed_num = '" + cardReportQuery.getCardPrintNum() + "' ");
		}
		if(cardReportQuery.getCategory() != null){
			if(cardReportQuery.getCategory() == 1){
				sb.append("and consume_category in (1, 3) ");
				
			} else {
				sb.append("and consume_category = " + cardReportQuery.getCategory() + " ");
				
			}
		}
		return sb.toString();
	}
	
	@Override
	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(consume_fid) as amount, sum(consume_money) as money, sum(consume_point) as point, ");
		sb.append("sum(consume_invoice) as invoice, sum(consume_balance) as balance, sum(consume_balance - consume_money) as afterMoney ");
		sb.append(createByCardReportQuery(cardReportQuery));
		Query query = currentSession().createSQLQuery(sb.toString());
		Object[] objects = (Object[]) query.uniqueResult();
		Integer amount = objects[0] == null?0:(Integer)objects[0];
		objects[0] = Long.valueOf(amount);
		return objects;
	}
	
	@Override
	public List<Object[]> findBranchCardTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardConsume.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() != 0){
			criteria.add(Restrictions.in("c.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		if(cardUserCardType != null){
			criteria.add(Restrictions.eq("c.consumeCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.branchNum"))
				.add(Projections.groupProperty("c.consumeCardType"))
				.add(Projections.sum("c.consumeMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findSalerSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> salerNames) {
		StringBuffer sb = new StringBuffer();
		sb.append("select consume_seller, branch_num, sum(consume_money) from card_consume with(nolock) ");
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
			sb.append("and consume_seller in :salerNames ");
		}
		sb.append("group by consume_seller, branch_num");
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
}
