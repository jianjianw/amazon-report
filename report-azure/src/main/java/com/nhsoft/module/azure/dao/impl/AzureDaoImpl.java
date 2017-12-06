package com.nhsoft.module.azure.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AzureDaoImpl {

    @Autowired
    @Qualifier("azureSessionFactory")
    private SessionFactory sessionFactory;

    public Session currentSession() {

        return sessionFactory.getCurrentSession();
    }
}
