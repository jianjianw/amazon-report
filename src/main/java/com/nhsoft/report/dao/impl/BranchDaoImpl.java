package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.BranchDao;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public class BranchDaoImpl extends DaoImpl implements BranchDao {

	@Override
	public List<Branch> findAll(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(Branch.class, "b");
		if (systemBookCode != null) {
			criteria.add(Restrictions.eq("b.id.systemBookCode", systemBookCode));

		}
		criteria.addOrder(Order.asc("b.id.branchNum"));
		return criteria.list();
	}

	@Override
	public List<Branch> findActivedRdc(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(Branch.class, "b")
				.add(Restrictions.eq("b.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("b.branchActived", true)).add(Restrictions.eq("b.branchRdc", true));
		return criteria.list();
	}


	@Override
	public Branch readWithNolock(String systemBookCode, Integer branchNum) {
		String sql = "select * from branch with(nolock) where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum;
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(Branch.class);
		return (Branch) query.uniqueResult();
	}

	@Override
	public List<Branch> findAllActived(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(Branch.class, "b")
				.add(Restrictions.eq("b.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("b.branchActived", true)).addOrder(Order.asc("b.id.branchNum"));
		return criteria.list();
	}

	@Override
	public List<BranchRegion> findBranchRegion(String systemBookCode) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from branch_region where system_book_code = '"+systemBookCode+ "'");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.addEntity(BranchRegion.class);
		return sqlQuery.list();
	}

	@Override
	public List<Branch> findBranchByBranchRegionNum(String systemBookCode, Integer branchRegionNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from branch where system_book_code = '"+systemBookCode+ "' and branch_region_num = "+branchRegionNum);
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.addEntity(Branch.class);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findBranchArea(String systemBookCode, List<Integer> branchNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num,sum(branch_area) ");
		sb.append("from branch ");
		sb.append("where system_book_code = :systemBookCode ");
		sb.append("group by branch_num order by branch_num asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		List list = sqlQuery.list();
		return list;
	}
}