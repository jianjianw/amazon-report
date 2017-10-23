package com.nhsoft.module.report.dao;

import org.hibernate.FlushMode;
import org.hibernate.LockMode;

public interface Dao {
	
	public void flush();
	
	public void lock(Object object, LockMode lockMode);
	
	public void setFlushMode(FlushMode flushMode);

}
