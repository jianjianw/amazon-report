package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PosMachineDao;
import com.nhsoft.report.model.PosMachine;
import com.nhsoft.report.model.PosMachineId;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PosMachineDaoImpl extends DaoImpl implements PosMachineDao {

	@Override
	public List<PosMachine> findByBranchs(String systemBookCode,
										  List<Integer> branchNums, String queryField) {
		Criteria criteria = currentSession().createCriteria(PosMachine.class, "p")
				.add(Restrictions.eq("p.id.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() >0){
			criteria.add(Restrictions.in("p.id.branchNum", branchNums));
		}
		if(StringUtils.isNotEmpty(queryField)){
			criteria.add(Restrictions.like("p.posMachineName", queryField, MatchMode.ANYWHERE));
		}
		return criteria.list();
	}


	@Override
	public PosMachine read(PosMachineId id) {
		return getHibernateTemplate().get(PosMachine.class, id);
	}

}