package com.nhsoft.report.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ShardingDaoImpl {
	
	@Autowired
	@Qualifier("shardingSessionFactory")
	private SessionFactory sessionFactory;
	
	public Session currentSession() {
		
		return sessionFactory.getCurrentSession();
	}
}
