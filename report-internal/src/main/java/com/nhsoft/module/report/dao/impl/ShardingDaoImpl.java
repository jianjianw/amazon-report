package com.nhsoft.module.report.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ShardingDaoImpl {
	
	public static final Logger logger = LoggerFactory.getLogger(ShardingDaoImpl.class);
	@Autowired
	@Qualifier("shardingSessionFactory")
	private SessionFactory sessionFactory;
	
	public Session currentSession() {
		
		return sessionFactory.getCurrentSession();
	}
}
