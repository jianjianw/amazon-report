package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.ReplaceCardDao;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.model.ReplaceCard;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class ReplaceCardDaoImpl extends DaoImpl implements ReplaceCardDao {


	
	@Override
	public List<ReplaceCard> findByCardUserNum(Integer cardUserNum) {
		Criteria criteria = currentSession().createCriteria(ReplaceCard.class, "r")
				.add(Restrictions.eq("r.replaceCardCustNum", cardUserNum));
		criteria.addOrder(Order.asc("r.replaceCardOperateTime"));
		return criteria.list();
	}

	private Criteria createByCardReportQuery(CardReportQuery cardReportQuery){
		Criteria criteria = currentSession().createCriteria(ReplaceCard.class, "c")
				.add(Restrictions.eq("c.systemBookCode", cardReportQuery.getSystemBookCode()));
		if(cardReportQuery.getOperateBranch() != null){
			criteria.add(Restrictions.eq("c.branchNum", cardReportQuery.getOperateBranch()));
		}
		if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0) {
			criteria.add(Restrictions.in("c.branchNum", cardReportQuery.getOperateBranchNums()));
		}
		if(cardReportQuery.getBranchNum() != null 
				|| (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0)){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(CardUser.class, "u")
					.add(Restrictions.eqProperty("u.cardUserNum", "c.replaceCardCustNum"))
					.add(Restrictions.eq("u.systemBookCode", cardReportQuery.getSystemBookCode()));
			if(cardReportQuery.getBranchNum() != null){
				subCriteria.add(Restrictions.eq("u.cardUserEnrollShop", cardReportQuery.getBranchNum()));
			}
			if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
				subCriteria.add(Restrictions.in("u.cardUserEnrollShop", cardReportQuery.getBranchNums()));
			}
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("u.cardUserNum"))));
		}
		if(cardReportQuery.getDateFrom() != null){
			criteria.add(Restrictions.ge("c.replaceCardOperateTime", DateUtil.getMinOfDate(cardReportQuery.getDateFrom())));
		}
		if(cardReportQuery.getDateTo() != null){
			criteria.add(Restrictions.le("c.replaceCardOperateTime", DateUtil.getMaxOfDate(cardReportQuery.getDateTo())));
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			criteria.add(Restrictions.eq("c.replaceCardOperator", cardReportQuery.getOperator()));
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("c.replaceCardOldPrintedNum", cardReportQuery.getCardPrintNum()))
					.add(Restrictions.eq("c.replaceCardNewPrintedNum", cardReportQuery.getCardPrintNum()))
					);
		}
		return criteria;
	}
	
	@Override
	public List<ReplaceCard> findByCardReportQuery(
			CardReportQuery cardReportQuery, int offset, int limit) {
		Criteria criteria = createByCardReportQuery(cardReportQuery);
		if(cardReportQuery.isPaging()){
			criteria.setFirstResult(offset);
			criteria.setMaxResults(limit);
			if(cardReportQuery.getSortField() != null){
				if(cardReportQuery.getSortType().equals("ASC")){
					criteria.addOrder(Order.asc(cardReportQuery.getSortField()));
				} else {
					criteria.addOrder(Order.desc(cardReportQuery.getSortField()));
				}
			} else {
				criteria.addOrder(Order.asc("c.replaceCardOperateTime"));
			}
		}	
		return criteria.list();
	}

	@Override
	public int countByCardReportQuery(CardReportQuery cardReportQuery) {
		Criteria criteria = createByCardReportQuery(cardReportQuery);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	private Criteria createByCash(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo){
		Criteria criteria = currentSession().createCriteria(ReplaceCard.class, "r")
				.add(Restrictions.eq("r.replaceCardPaymentTypeName", AppConstants.PAYMENT_CASH))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if(dateFrom != null) {
			criteria.add(Restrictions.ge("r.shiftTableBizday", DateUtil.getDateShortStr(dateFrom)));

		}
		if(dateTo != null) {
			criteria.add(Restrictions.le("r.shiftTableBizday", DateUtil.getDateShortStr(dateTo)));
		}
		return criteria;
	}
	
	@Override
	public BigDecimal getCashMoney(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo) {		
		String sql = "select sum(replace_card_money) as cash " +
				" from replace_card with(nolock) where system_book_code = '" + systemBookCode + "'  " +
				" and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + 
				" and replace_card_payment_type_name = '现金' " + 
				" and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ";
		Query query = currentSession().createSQLQuery(sql);
		Object object = query.uniqueResult();
		return object == null?BigDecimal.ZERO:(BigDecimal)object;
	}

	@Override
	public List<Object[]> findCashGroupByBranch(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo) {
		Criteria criteria = createByCash(systemBookCode, branchNums, dateFrom, dateTo);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.branchNum"))
				.add(Projections.sum("r.replaceCardMoney"))
				);
		return criteria.list();
	}

	@Override
	public int count(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum) {
		Criteria criteria = currentSession().createCriteria(ReplaceCard.class, "r")
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNum != null){
			criteria.add(Restrictions.eq("r.branchNum", branchNum));
		}
		if(StringUtils.isNotEmpty(shiftTableBizday)){
			criteria.add(Restrictions.eq("r.shiftTableBizday", shiftTableBizday));
		}
		if(shiftTableNum != null){
			criteria.add(Restrictions.eq("r.shiftTableNum", shiftTableNum));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	private Criteria createByShiftTables(String systemBookCode, Integer branchNum, List<ShiftTable> shiftTables){
		
		Criteria criteria = currentSession().createCriteria(ReplaceCard.class, "r")
				.add(Restrictions.eq("r.systemBookCode", systemBookCode))
				.add(Restrictions.eq("r.branchNum", branchNum));
		Disjunction disjunction = Restrictions.disjunction();
		for (int i = 0; i < shiftTables.size(); i++) {
			ShiftTable shiftTable = shiftTables.get(i);
			disjunction.add(Restrictions.and(Restrictions.eq("r.shiftTableBizday", shiftTable.getId().getShiftTableBizday()),
					Restrictions.eq("r.shiftTableNum", shiftTable.getId().getShiftTableNum())));
		}
		criteria.add(disjunction);
		return criteria;
    }
	
	@Override
	public BigDecimal sumByByShiftTables(List<ShiftTable> shiftTables) {
    	if(shiftTables.size() == 0){
			return null;
		}
		String systemBookCode = shiftTables.get(0).getId().getSystemBookCode();
		Integer branchNum = shiftTables.get(0).getId().getBranchNum();
		Criteria criteria = createByShiftTables(systemBookCode, branchNum, shiftTables);
		criteria.setProjection(Projections.sum("r.replaceCardMoney"));
		Object object = criteria.uniqueResult();
		return object == null?BigDecimal.ZERO:(BigDecimal)object;
	}

	@Override
	public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables) {
		if(shiftTables.size() == 0){
			return null;
		}
		String systemBookCode = shiftTables.get(0).getId().getSystemBookCode();
		Integer branchNum = shiftTables.get(0).getId().getBranchNum();
		Criteria criteria = createByShiftTables(systemBookCode, branchNum, shiftTables);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.replaceCardPaymentTypeName"))
				.add(Projections.sum("r.replaceCardMoney"))
				);
		return criteria.list();
	}


	@Override
	public List<Object[]> findBranchCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select branch_num, count(replace_card_fid) from replace_card with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(dateFrom != null){
			sb.append("and shift_table_bizday >= :bizFrom ");
		}
		if(dateTo != null){
			sb.append("and shift_table_bizday <= :bizTo ");
		}
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

	@Override
	public ReplaceCard getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday,
			String replaceCardMachine) {
		Criteria criteria = currentSession().createCriteria(ReplaceCard.class, "r")
				.add(Restrictions.eq("r.systemBookCode", systemBookCode))
				.add(Restrictions.eq("r.branchNum", branchNum))
				.add(Restrictions.eq("r.shiftTableBizday", shiftTableBizday))
				.add(Restrictions.eq("r.replaceCardMachine", replaceCardMachine));
		criteria.addOrder(Order.desc("r.replaceCardFid"));
		criteria.setMaxResults(1);
		return (ReplaceCard) criteria.uniqueResult();
	}

	@Override
	public void deleteByBook(String systemBookCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from replace_card where system_book_code = '" + systemBookCode + "' ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.executeUpdate();
	}

	@Override
	public List<ReplaceCard> findToLog(Date dateFrom, Date dateTo) {
		SQLQuery query = currentSession().createSQLQuery("select * from replace_card with(nolock) where replace_card_operate_time > '" + DateUtil.getLongDateTimeStr(dateFrom) + "' and replace_card_operate_time <= '" + DateUtil.getLongDateTimeStr(dateTo) + "' order by replace_card_operate_time ");
		query.addEntity(ReplaceCard.class);
		return query.list();
	}
	
	

}
