package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.MessageBoardDao;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class MessageBoardDaoImpl extends DaoImpl implements MessageBoardDao {
	@Override
	public Integer countByDate(String systemBookCode, Date dateFrom, Date dateTo) {
		String hql = "select count(m.messageBoardId) from MessageBoard as m "
				+ "where (exists (select 1 from AppUser as a where a.appUserNum = m.appUserNum and a.systemBookCode = :systemBookCode) "
				+ "or exists (select 1 from WholesaleUser as w where w.wholesaleUserId = m.wholesaleUserId and w.systemBookCode = :systemBookCode)"
				+ "or exists (select 1 from SupplierUser as s where s.supplierUserNum = m.supplierUserNum and s.systemBookCode = :systemBookCode) )";

		if(dateFrom != null){
			hql = hql + " and m.messageBoardCreateTime >= :dateFrom ";
		}
		if(dateTo != null){
			hql = hql + " and m.messageBoardCreateTime <= :dateTo ";
		}
		Query query = currentSession().createQuery(hql);
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		Object object = query.uniqueResult();
		return ((Long)object).intValue();
	}
}
