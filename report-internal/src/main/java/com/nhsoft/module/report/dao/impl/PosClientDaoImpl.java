package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.PosClientDao;
import com.nhsoft.module.report.model.PosClient;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PosClientDaoImpl extends DaoImpl implements PosClientDao {
	@Override
	public List<PosClient> findAll(String systemBookCode, Integer branchNum,List<String> clientFids) {
		Criteria criteria = currentSession().createCriteria(PosClient.class, "p").add(
				Restrictions.eq("p.systemBookCode", systemBookCode));
		if(clientFids != null){
			criteria.add(Restrictions.in("p.clientFid",clientFids));
		}
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
