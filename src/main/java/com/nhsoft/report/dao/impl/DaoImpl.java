package com.nhsoft.report.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by yangqin on 2017/9/5.
 */
public class DaoImpl {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	public Session currentSession() {
		
		return sessionFactory.getCurrentSession();
	}

}
