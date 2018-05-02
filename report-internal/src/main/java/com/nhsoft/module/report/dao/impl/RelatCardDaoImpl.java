package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.RelatCardDao;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.model.RelatCard;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")
@Repository
public class RelatCardDaoImpl extends DaoImpl implements RelatCardDao {

	
	@Override
	public List<RelatCard> findByCardUserNum(Integer cardUserNum) {
		Criteria criteria = currentSession().createCriteria(RelatCard.class, "c")
				.add(Restrictions.eq("c.relatCardCustNum", cardUserNum));
		criteria.addOrder(Order.asc("c.relatCardOperateTime"));
		return criteria.list();
	}

	@Override
	public List<RelatCard> findByShiftTables(List<ShiftTable> shiftTables) {
		if(shiftTables.size() == 0){
			return new ArrayList<RelatCard>();
		}
		String systemBookCode = shiftTables.get(0).getId().getSystemBookCode();
		Integer branchNum = shiftTables.get(0).getId().getBranchNum();
		
		Criteria criteria = currentSession().createCriteria(RelatCard.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode))
				.add(Restrictions.eq("c.branchNum", branchNum));
		Disjunction disjunction = Restrictions.disjunction();
		for (int i = 0; i < shiftTables.size(); i++) {
			ShiftTable shiftTable = shiftTables.get(i);
			disjunction.add(Restrictions.and(Restrictions.eq("c.shiftTableBizday", shiftTable.getId().getShiftTableBizday()),
					Restrictions.eq("c.shiftTableNum", shiftTable.getId().getShiftTableNum())));
		}
		criteria.add(disjunction);
		return criteria.list();
	}

	private Criteria createByCardReportQuery(CardReportQuery cardReportQuery){
		
		Criteria criteria = currentSession().createCriteria(RelatCard.class, "c")
				.add(Restrictions.eq("c.systemBookCode", cardReportQuery.getSystemBookCode()));
		if(cardReportQuery.getOperateBranch() != null){
			criteria.add(Restrictions.eq("c.branchNum", cardReportQuery.getOperateBranch()));
		}
		if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0) {
			criteria.add(Restrictions.in("c.branchNum", cardReportQuery.getOperateBranchNums()));
		}
		if(cardReportQuery.getBranchNum() != null 
				|| StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())
				|| (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0)){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(CardUser.class, "u")
					.add(Restrictions.eqProperty("u.cardUserNum", "c.relatCardCustNum"))
					.add(Restrictions.eq("u.systemBookCode", cardReportQuery.getSystemBookCode()));
			if(cardReportQuery.getBranchNum() != null){
				subCriteria.add(Restrictions.eq("u.cardUserEnrollShop", cardReportQuery.getBranchNum()));
			}
			if(StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
				subCriteria.add(Restrictions.eq("u.cardUserPrintedNum", cardReportQuery.getCardPrintNum()));
			}
			if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
				subCriteria.add(Restrictions.in("u.cardUserEnrollShop", cardReportQuery.getBranchNums()));
			}
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("u.cardUserNum"))));
		}
		if(cardReportQuery.getDateFrom() != null){
			criteria.add(Restrictions.ge("c.relatCardOperateTime", DateUtil.getMinOfDate(cardReportQuery.getDateFrom())));
		}
		if(cardReportQuery.getDateTo() != null){
			criteria.add(Restrictions.le("c.relatCardOperateTime", DateUtil.getMaxOfDate(cardReportQuery.getDateTo())));
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			criteria.add(Restrictions.eq("c.relatCardOperator", cardReportQuery.getOperator()));
		}
		return criteria;
	}
	
	@Override
	public List<RelatCard> findByCardReportQuery(
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
				criteria.addOrder(Order.asc("c.relatCardOperateTime"));
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
		Criteria criteria = currentSession().createCriteria(RelatCard.class, "r")
				.add(Restrictions.eq("r.relatCardPaymentTypeName", AppConstants.PAYMENT_CASH))
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
		String sql = "select sum(relat_card_money) as cash " +
				" from relat_card with(nolock) where system_book_code = '" + systemBookCode + "'  " +
				" and branch_num in " + AppUtil.getIntegerParmeList(branchNums) +
				" and relat_card_payment_type_name = '现金' " + 
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
				.add(Projections.sum("r.relatCardMoney"))
				);
		return criteria.list();
	}
	
	private Criteria createByShiftTables(String systemBookCode, Integer branchNum, List<ShiftTable> shiftTables){
		
		Criteria criteria = currentSession().createCriteria(RelatCard.class, "r")
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
	public Object[] sumByByShiftTables(List<ShiftTable> shiftTables) {
    	if(shiftTables.size() == 0){
			return null;
		}
		String systemBookCode = shiftTables.get(0).getId().getSystemBookCode();
		Integer branchNum = shiftTables.get(0).getId().getBranchNum();
		Criteria criteria = createByShiftTables(systemBookCode, branchNum, shiftTables);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.count("r.relatCardFid"))
				.add(Projections.sum("r.relatCardMoney"))
				);
		return (Object[]) criteria.uniqueResult();
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
				.add(Projections.groupProperty("r.relatCardPaymentTypeName"))
				.add(Projections.sum("r.relatCardMoney"))
				);
		return criteria.list();
	}

	@Override
	public List<RelatCard> findToLog(Date dateFrom, Date dateTo) {
		SQLQuery query = currentSession().createSQLQuery("select * from relat_card with(nolock) where relat_card_operate_time > '" + DateUtil.getLongDateTimeStr(dateFrom) + "' and relat_card_operate_time <= '" + DateUtil.getLongDateTimeStr(dateTo) + "' order by relat_card_operate_time ");
		query.addEntity(RelatCard.class);
		return query.list();
	}
	
	

}
