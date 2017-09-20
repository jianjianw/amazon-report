package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.CheckOrderDao;
import com.nhsoft.report.model.CheckOrder;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class CheckOrderDaoImpl extends DaoImpl implements CheckOrderDao {
	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(CheckOrder.class, "c")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.sqlRestriction("storehouse_num in (select storehouse_num from branch_storehouse where system_book_code = ? and branch_num = ?)"
					, new Object[]{systemBookCode, branchNum}, new Type[]{StandardBasicTypes.STRING, StandardBasicTypes.INTEGER}));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("c.checkOrderDate", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("c.checkOrderDate", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}
	
}
