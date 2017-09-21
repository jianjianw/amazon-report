package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PosClientDao;
import com.nhsoft.report.model.PosClient;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PosClientDaoImpl extends HibernateDaoSupport implements PosClientDao {
	@Override
	public List<PosClient> findAll(String systemBookCode, Integer branchNum) {
		Criteria criteria = currentSession().createCriteria(PosClient.class, "p").add(
				Restrictions.eq("p.systemBookCode", systemBookCode));
		if (branchNum != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("p.branchNum", branchNum))
					.add(Restrictions.eqOrIsNull("p.clientShared", true))
			);
		}
		criteria.addOrder(Order.asc("p.clientCode"));
		return criteria.list();
	}
}
