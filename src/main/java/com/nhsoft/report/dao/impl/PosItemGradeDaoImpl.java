package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.PosItemGradeDao;
import com.nhsoft.report.model.PosItemGrade;
import com.nhsoft.report.util.AppUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Repository
public class PosItemGradeDaoImpl extends  DaoImpl implements PosItemGradeDao {
	@Override
	public List<PosItemGrade> find(String systemBookCode, Integer itemNum) {
		Criteria criteria = currentSession().createCriteria(PosItemGrade.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode))
				.add(Restrictions.eq("p.itemNum", itemNum));
		return criteria.list();
	}
	
	@Override
	public List<PosItemGrade> find(String systemBookCode, List<Integer> itemNums) {
		if(itemNums == null || itemNums.size() == 0){
			return new ArrayList<PosItemGrade>();
		}
		String hql = "from PosItemGrade where systemBookCode = :systemBookCode and itemNum in " + AppUtil.getIntegerParmeList(itemNums);
		Query query = currentSession().createQuery(hql);
		query.setString("systemBookCode", systemBookCode);
		return query.list();
	}
	
	@Override
	public List<PosItemGrade> find(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(PosItemGrade.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		return criteria.list();
	}
	
	@Override
	public List<PosItemGrade> findByIds(List<Integer> itemGradeNums) {
		String sql = "select * from pos_item_grade with(nolock) where item_grade_num in " + AppUtil.getIntegerParmeList(itemGradeNums);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(PosItemGrade.class);
		return query.list();
	}
	
	@Override
	public List<PosItemGrade> findByItemNums(List<Integer> itemNums) {
		String sql = "select * from pos_item_grade with(nolock) where item_num in " + AppUtil.getIntegerParmeList(itemNums);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(PosItemGrade.class);
		return query.list();
	}
}
