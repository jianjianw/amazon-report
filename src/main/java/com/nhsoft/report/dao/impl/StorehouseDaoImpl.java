package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.StorehouseDao;
import com.nhsoft.report.model.Storehouse;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class StorehouseDaoImpl extends DaoImpl implements StorehouseDao {
	@Override
	public List<Storehouse> findByBranchs(String systemBookCode, List<Integer> branchNums) {
		Criteria criteria = currentSession().createCriteria(Storehouse.class, "s")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode))
				.add(Restrictions.eq("s.storehouseDelTag", false))
				.createAlias("s.branchs", "branch");
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("branch.id.branchNum", branchNums));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("s.storehouseCode"));
		return criteria.list();
	}

}
