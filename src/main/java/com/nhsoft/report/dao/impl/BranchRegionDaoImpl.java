package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.BranchRegionDao;
import com.nhsoft.report.model.BranchRegion;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class BranchRegionDaoImpl extends DaoImpl implements BranchRegionDao{
	@Override
	public List<BranchRegion> findAll(String systemBookCode) {
		String sql = "select * from branch_region with(nolock) where system_book_code = '" + systemBookCode + "' ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(BranchRegion.class);
		return query.list();
	}
}
