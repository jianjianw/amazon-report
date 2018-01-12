package com.nhsoft.report.provider;

import com.nhsoft.report.utils.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceType();
	}
	
}
