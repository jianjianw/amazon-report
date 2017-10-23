package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.TransferLineDao;
import com.nhsoft.module.report.model.TransferLine;
import com.nhsoft.module.report.model.TransferLineDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@SuppressWarnings("deprecation")
public class TransferLineDaoImpl extends DaoImpl implements TransferLineDao {

	@Override
	public List<TransferLine> find(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(TransferLine.class, "t")
				.add(Restrictions.eq("t.systemBookCode", systemBookCode));
		criteria.addOrder(Order.asc("t.transferLineCode"));
		return criteria.list();
	}


	@Override
	public List<TransferLineDetail> findDetails(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(TransferLineDetail.class, "t")
				.add(Restrictions.eq("t.id.systemBookCode", systemBookCode));
		return criteria.list();
	}


	@Override
	public List<TransferLine> findByBranch(String systemBookCode, Integer branchNum) {
		Criteria criteria = currentSession().createCriteria(TransferLine.class, "t")
				.add(Restrictions.eq("t.systemBookCode", systemBookCode))
				.add(Restrictions.eq("t.branchNum", branchNum));
		criteria.addOrder(Order.asc("t.transferLineCode"));
		return criteria.list();
	}
}
