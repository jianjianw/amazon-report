package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.DistributionCentreMatrixDao;
import com.nhsoft.module.report.model.DistributionCentreMatrix;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DistributionCentreMatrixDaoHibernate extends DaoImpl implements DistributionCentreMatrixDao {
	

	@Override
	public List<Integer> findRecommend(String systemBookCode, Integer branchNum) {
		Criteria criteria = currentSession().createCriteria(DistributionCentreMatrix.class, "d")
				.add(Restrictions.eq("d.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("d.id.branchNum", branchNum))
				.add(Restrictions.eq("d.dcmRecommended", true));
		criteria.setProjection(Projections.property("d.id.itemNum"));
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}

}
