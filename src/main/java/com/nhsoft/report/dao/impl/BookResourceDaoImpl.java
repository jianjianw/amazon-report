package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.BookResourceDao;
import com.nhsoft.report.model.BookResource;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class BookResourceDaoImpl extends DaoImpl implements BookResourceDao {

	@Override
	public BookResource read(String systemBookCode, String bookResourceName) {
		Criteria criteria = currentSession().createCriteria(BookResource.class, "b")
				.add(Restrictions.eq("b.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("b.id.bookResourceName", bookResourceName));
		List<BookResource> list = criteria.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}		
		return null;
	}



}
