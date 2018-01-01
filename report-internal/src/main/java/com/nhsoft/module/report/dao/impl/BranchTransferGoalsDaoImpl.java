package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.BranchTransferGoalsDao;
import com.nhsoft.module.report.model.BranchTransferGoals;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BranchTransferGoalsDaoImpl extends DaoImpl implements BranchTransferGoalsDao {

	@Override
	public List<BranchTransferGoals> find(String systemBookCode,
                                          Integer branchNum) {
		Criteria criteria = currentSession().createCriteria(BranchTransferGoals.class, "g")
				.add(Restrictions.eq("g.id.systemBookCode", systemBookCode));
		if(branchNum != null){
			criteria.add(Restrictions.eq("g.id.branchNum", branchNum));
		}
		return criteria.list();
	}



	@Override
	public List<BranchTransferGoals> findByDate(String systemBookCode,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		Criteria criteria = currentSession().createCriteria(BranchTransferGoals.class, "g")
				.add(Restrictions.eq("g.id.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() != 0){
			criteria.add(Restrictions.in("g.id.branchNum", branchNums));
		}
		if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_YEAR)){
			criteria.add(Restrictions.sqlRestriction("{fn LENGTH(branch_transfer_interval)} = 4"));
			if(dateFrom != null){
				criteria.add(Restrictions.ge("g.id.branchTransferInterval", DateUtil.getDateShortStr(dateFrom).subSequence(0, 4)));
			}
			if(dateTo != null){
				criteria.add(Restrictions.le("g.id.branchTransferInterval", DateUtil.getDateShortStr(dateTo).subSequence(0, 4)));
			}
		} else if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)){
			criteria.add(Restrictions.sqlRestriction("{fn LENGTH(branch_transfer_interval)} = 6"));
			if(dateFrom != null){
				criteria.add(Restrictions.ge("g.id.branchTransferInterval", DateUtil.getDateShortStr(dateFrom).subSequence(0, 6)));
			}
			if(dateTo != null){
				criteria.add(Restrictions.le("g.id.branchTransferInterval", DateUtil.getDateShortStr(dateTo).subSequence(0, 6)));
			}
		} else if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_WEEK)){
			dateFrom = DateUtil.getMinOfDate(dateFrom);
			dateTo = DateUtil.getMaxOfDate(dateTo);
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.between("g.branchTransferStart", dateFrom, dateTo))
					.add(Restrictions.between("g.branchTransferEnd", dateFrom, dateTo))
			);
		} else {
			criteria.add(Restrictions.sqlRestriction(" LEN(branch_transfer_interval) = 10 "));
			if(dateFrom != null){
				criteria.add(Restrictions.ge("g.id.branchTransferInterval", DateUtil.getDateStr(dateFrom)));
			}
			if(dateTo != null){
				criteria.add(Restrictions.le("g.id.branchTransferInterval", DateUtil.getDateStr(dateTo)));
			}
		}
		return criteria.list();
	}


	@Override
	public List<Object[]> findSummaryByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		Criteria criteria = currentSession().createCriteria(BranchTransferGoals.class, "g")
				.add(Restrictions.eq("g.id.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() != 0){
			criteria.add(Restrictions.in("g.id.branchNum", branchNums));
		}
		if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_YEAR)){
			criteria.add(Restrictions.sqlRestriction("{fn LENGTH(branch_transfer_interval)} = 4"));
			if(dateFrom != null){
				criteria.add(Restrictions.ge("g.id.branchTransferInterval", DateUtil.getDateShortStr(dateFrom).subSequence(0, 4)));
			}
			if(dateTo != null){
				criteria.add(Restrictions.le("g.id.branchTransferInterval", DateUtil.getDateShortStr(dateTo).subSequence(0, 4)));
			}
		} else if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)){
			criteria.add(Restrictions.sqlRestriction("{fn LENGTH(branch_transfer_interval)} = 6"));
			if(dateFrom != null){
				criteria.add(Restrictions.ge("g.id.branchTransferInterval", DateUtil.getDateShortStr(dateFrom).subSequence(0, 6)));
			}
			if(dateTo != null){
				criteria.add(Restrictions.le("g.id.branchTransferInterval", DateUtil.getDateShortStr(dateTo).subSequence(0, 6)));
			}
		} else if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_WEEK)){
			dateFrom = DateUtil.getMinOfDate(dateFrom);
			dateTo = DateUtil.getMaxOfDate(dateTo);
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.between("g.branchTransferStart", dateFrom, dateTo))
					.add(Restrictions.between("g.branchTransferEnd", dateFrom, dateTo))
			);
		} else {
			criteria.add(Restrictions.sqlRestriction(" LEN(branch_transfer_interval) = 10 "));
			if(dateFrom != null){
				criteria.add(Restrictions.ge("g.id.branchTransferInterval", DateUtil.getDateStr(dateFrom)));
			}
			if(dateTo != null){
				criteria.add(Restrictions.le("g.id.branchTransferInterval", DateUtil.getDateStr(dateTo)));
			}
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("g.id.branchNum"))
				.add(Projections.sum("g.branchTransferValue"))
				.add(Projections.sum("g.branchTransferSaleValue"))
				.add(Projections.sum("g.branchTransferGrossValue"))
				.add(Projections.sum("g.branchTransferDiffValue"))
		);
		return criteria.list();
	}

	@Override
	public List<Object[]> findSaleMoneyGoalsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num,sum(branch_transfer_sale_value), ");
		sb.append("from branch_transfer_goals ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_YEAR)){
			if (dateFrom != null) {
				sb.append("and branch_transfer_interval >= '" + DateUtil.getDateShortStr(dateFrom).substring(0,4) + "' ");
			}
			if (dateTo != null) {
				sb.append("and branch_transfer_interval <= '" + DateUtil.getDateShortStr(dateTo).substring(0,4) + "' ");
			}
		}else if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)){
			if (dateFrom != null) {
				sb.append("and branch_transfer_interval >= '" + DateUtil.getDateShortStr(dateFrom).substring(0,6) + "' ");
			}
			if (dateTo != null) {
				sb.append("and branch_transfer_interval <= '" + DateUtil.getDateShortStr(dateTo).substring(0,6) + "' ");
			}
		}else if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_WEEK)){
			sb.append("and branch_transfer_start between  '"+ DateUtil.getDateShortStr(dateFrom)+"' and  '"+ DateUtil.getDateShortStr(dateTo)+"' ");
			sb.append("and branch_transfer_end between  '"+ DateUtil.getDateShortStr(dateFrom)+"' and  '"+ DateUtil.getDateShortStr(dateTo)+"' ");

		}else {
			if (dateFrom != null) {
				sb.append("and branch_transfer_interval >= '" + DateUtil.getDateStr(dateFrom) + "' ");
			}
			if (dateTo != null) {
				sb.append("and branch_transfer_interval <= '" + DateUtil.getDateStr(dateTo) + "' ");
			}
		}

		sb.append("group by branch_num order by branch_num asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findSaleMoneyGoalsByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_transfer_interval,sum(branch_transfer_sale_value) ");
		sb.append("from branch_transfer_goals ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_YEAR)){
			sb.append("and {fn LENGTH(branch_transfer_interval)} = 4 ");
			if (dateFrom != null) {
				sb.append("and branch_transfer_interval >= '" + DateUtil.getDateShortStr(dateFrom).substring(0,4) + "' ");
			}
			if (dateTo != null) {
				sb.append("and branch_transfer_interval <= '" + DateUtil.getDateShortStr(dateTo).substring(0,4) + "' ");
			}
		}else if(dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)){
			sb.append("and {fn LENGTH(branch_transfer_interval)} = 6 ");
			if (dateFrom != null) {
				sb.append("and branch_transfer_interval >= '" + DateUtil.getDateShortStr(dateFrom).substring(0,6) + "' ");
			}
			if (dateTo != null) {
				sb.append("and branch_transfer_interval <= '" + DateUtil.getDateShortStr(dateTo).substring(0,6) + "' ");
			}

		}else {
			sb.append("and {fn LENGTH(branch_transfer_interval)} = 10 ");
			if (dateFrom != null) {
				sb.append("and branch_transfer_interval >= '" + DateUtil.getDateStr(dateFrom) + "' ");
			}
			if (dateTo != null) {
				sb.append("and branch_transfer_interval <= '" + DateUtil.getDateStr(dateTo) + "' ");
			}
		}

		sb.append("group by branch_transfer_interval order by branch_transfer_interval asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}

	public List<Object[]> findGoalsByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo){
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, branch_transfer_interval, sum(branch_transfer_sale_value) ");
		sb.append("from branch_transfer_goals ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and {fn LENGTH(branch_transfer_interval)} = 10 ");
		if (dateFrom != null) {
			sb.append("and branch_transfer_interval >= '" + DateUtil.getDateStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and branch_transfer_interval <= '" + DateUtil.getDateStr(dateTo) + "' ");
		}

		sb.append("group by branch_num, branch_transfer_interval order by branch_num, branch_transfer_interval asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findDepositGoals(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, branch_transfer_interval, sum(branch_transfer_card_deposit) ");
		sb.append("from branch_transfer_goals ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and {fn LENGTH(branch_transfer_interval)} = 10 ");
		if (dateFrom != null) {
			sb.append("and branch_transfer_interval >= '" + DateUtil.getDateStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and branch_transfer_interval <= '" + DateUtil.getDateStr(dateTo) + "' ");
		}

		sb.append("group by branch_num, branch_transfer_interval order by branch_num, branch_transfer_interval asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findNewCardGoals(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, branch_transfer_interval, sum(branch_transfer_new_card) ");
		sb.append("from branch_transfer_goals ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and {fn LENGTH(branch_transfer_interval)} = 10 ");
		if (dateFrom != null) {
			sb.append("and branch_transfer_interval >= '" + DateUtil.getDateStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and branch_transfer_interval <= '" + DateUtil.getDateStr(dateTo) + "' ");
		}

		sb.append("group by branch_num, branch_transfer_interval order by branch_num, branch_transfer_interval asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}

}