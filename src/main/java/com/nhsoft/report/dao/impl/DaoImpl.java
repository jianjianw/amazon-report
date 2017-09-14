package com.nhsoft.report.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by yangqin on 2017/9/5.
 */
public class DaoImpl {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session currentSession() {
		
		return sessionFactory.getCurrentSession();
	}

}
