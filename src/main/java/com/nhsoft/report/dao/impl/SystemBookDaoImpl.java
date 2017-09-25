package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.SystemBookDao;
import com.nhsoft.report.model.SystemBook;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SystemBookDaoImpl extends DaoImpl implements SystemBookDao {



	@Override
	public SystemBook read(String systemBookCode) {
		return (SystemBook)currentSession().get(SystemBook.class, systemBookCode);
	}

	@Override
	public List<SystemBook> findAll() {

		String sql = "select * from system_book with(nolock) order by system_book_code asc";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(SystemBook.class);
		return query.list();
	}
	

	@Override
	public List<SystemBook> findAllActiveBooks() {

		String sql = "select * from system_book with(nolock) where book_actived = 1 order by system_book_code asc";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(SystemBook.class);
		return query.list();
	}


	@Override
	public List<SystemBook> findByParent(String systemBookCode) {
		String sql = "select * from system_book with(nolock) where parent_book_code = '" + systemBookCode + "' ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(SystemBook.class);
		return query.list();
	}

	
	
}
