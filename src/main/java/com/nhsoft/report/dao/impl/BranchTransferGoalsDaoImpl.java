package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.BranchTransferGoalsDao;
import com.nhsoft.report.model.BranchTransferGoals;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
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


}