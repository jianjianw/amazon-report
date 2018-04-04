package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.StorehouseDao;
import com.nhsoft.module.report.model.Storehouse;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class StorehouseDaoImpl extends DaoImpl implements StorehouseDao {
	@Override
	public List<Storehouse> findByBranchs(String systemBookCode, List<Integer> branchNums) {
		Criteria criteria = currentSession().createCriteria(Storehouse.class, "s")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode))
				.add(Restrictions.eq("s.storehouseDelTag", false))
				.add(Restrictions.eq("s.storehouseActived",true))
				.createAlias("s.branchs", "branch");
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("branch.id.branchNum", branchNums));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("s.storehouseCode"));
		return criteria.list();
	}

	@Override
	public List<Storehouse> findByBranch(String systemBookCode,
										 Integer branchNum) {
		String sql = "storehouse_num in (select storehouse_num from branch_storehouse where system_book_code = ? and branch_num = ?)";
		Criteria criteria = currentSession().createCriteria(Storehouse.class, "s")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode))
				.add(Restrictions.eq("s.storehouseActived",true))
				.add(Restrictions.eq("s.storehouseDelTag", false));

		if(branchNum != null){
			criteria.add(Restrictions.sqlRestriction(sql,
					new Object[]{systemBookCode, branchNum},
					new Type[]{StandardBasicTypes.STRING, StandardBasicTypes.INTEGER}));
		}
		criteria.addOrder(Order.asc("s.storehouseCode"));
		return criteria.list();
	}

}
