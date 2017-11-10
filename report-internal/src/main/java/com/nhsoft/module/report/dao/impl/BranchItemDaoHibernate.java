package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.BranchItemDao;
import com.nhsoft.module.report.model.BranchItem;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BranchItemDaoHibernate extends DaoImpl implements BranchItemDao {

	

	@Override
	public List<Integer> findItemNums(String systemBookCode, Integer branchNum) {
		Criteria criteria = currentSession().createCriteria(BranchItem.class, "b")
				.add(Restrictions.eq("b.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("b.id.branchNum", branchNum));
		criteria.setProjection(Projections.property("b.id.itemNum"));
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}

	

}
