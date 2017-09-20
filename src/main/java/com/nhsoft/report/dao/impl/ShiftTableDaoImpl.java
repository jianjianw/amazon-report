package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.ShiftTableDao;
import com.nhsoft.report.model.ShiftTable;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
public class ShiftTableDaoImpl extends DaoImpl implements ShiftTableDao {

	@Override
	public List<ShiftTable> find(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(ShiftTable.class, "s")
				.add(Restrictions.eq("s.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("s.id.branchNum", branchNum))
				.add(Restrictions.between("s.id.shiftTableBizday", DateUtil.getDateShortStr(dateFrom), DateUtil.getDateShortStr(dateTo)));
		criteria.addOrder(Order.asc("s.id.shiftTableBizday")).addOrder(Order.asc("s.id.shiftTableNum"));
		return criteria.list();
	}


	@Override
	public List<ShiftTable> find(String systemBookCode, List<Integer> branchNums, List<String> shiftTableBizdays) {
		Criteria criteria = currentSession().createCriteria(ShiftTable.class, "s")
				.add(Restrictions.eq("s.id.systemBookCode", systemBookCode))
				.add(Restrictions.in("s.id.branchNum", branchNums))
				.add(Restrictions.in("s.id.shiftTableBizday", shiftTableBizdays));
		return criteria.list();
	}

}
