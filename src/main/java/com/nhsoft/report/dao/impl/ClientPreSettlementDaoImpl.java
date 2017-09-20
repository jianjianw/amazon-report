package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.ClientPreSettlementDao;
import com.nhsoft.report.model.ClientPreSettlement;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public class ClientPreSettlementDaoImpl extends DaoImpl implements ClientPreSettlementDao {


	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(ClientPreSettlement.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("p.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.preSettlementCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.preSettlementCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	@Override
	public List<Object[]> findDueMoney(String systemBookCode,
									   Integer branchNum, List<String> clientFids, Date dateFrom,
									   Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select clientFid, sum(preSettlementPaid - preSettlementMoney ), sum(preSettlementPaid) ");
		sb.append("from ClientPreSettlement where systemBookCode = :systemBookCode and branchNum = :branchNum ");
		sb.append("and state.stateCode = 3");
		if(dateFrom != null){
			sb.append("and preSettlementDate >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and preSettlementDate <= :dateTo ");
		}
		if(clientFids != null && clientFids.size() > 0){
			sb.append("and clientFid in (:clientFids) ");
		}
		sb.append("group by clientFid ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		if(clientFids != null && clientFids.size() > 0){
			query.setParameterList("clientFids", clientFids);
		}
		return query.list();
	}
}