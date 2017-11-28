package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.InvoiceChangeDao;
import com.nhsoft.module.report.model.InvoiceChange;
import com.nhsoft.module.report.model.InvoiceChangeId;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.util.AppUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class InvoiceChangeDaoHibernate extends HibernateDaoSupport implements InvoiceChangeDao {

	@Override
	public InvoiceChange readByOrderNo(String systemBookCode, String orderNo) {
		Criteria criteria = currentSession().createCriteria(InvoiceChange.class, "c")
				.add(Restrictions.eq("c.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("c.orderNo", orderNo));
		List<InvoiceChange> invoiceChanges = criteria.list();
		if(invoiceChanges.size() > 0){
			return invoiceChanges.get(0);
		}
		return null;
	}

	@Override
	public List<InvoiceChange> findByShiftTable(ShiftTable shiftTable) {
		Criteria criteria = currentSession().createCriteria(InvoiceChange.class, "c")
				.add(Restrictions.eq("c.id.systemBookCode", shiftTable.getId().getSystemBookCode()))
				.add(Restrictions.eq("c.id.branchNum", shiftTable.getId().getBranchNum()))
				.add(Restrictions.eq("c.id.shiftTableBizday", shiftTable.getId().getShiftTableBizday()))
				.add(Restrictions.eq("c.id.shiftTableNum", shiftTable.getId().getShiftTableNum()));
		return criteria.list();
	}

	@Override
	public InvoiceChange getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday,
			String invoiceChangeMachine) {
		Criteria criteria = currentSession().createCriteria(InvoiceChange.class, "i")
				.add(Restrictions.eq("i.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("i.id.branchNum", branchNum))
				.add(Restrictions.eq("i.id.shiftTableBizday", shiftTableBizday))
				.add(Restrictions.eq("i.invoiceChangeMachine", invoiceChangeMachine));
		criteria.addOrder(Order.desc("i.id.invoiceChangeNo"));
		criteria.setMaxResults(1);
		return (InvoiceChange) criteria.uniqueResult();
	}

	@Override
	public InvoiceChange read(InvoiceChangeId id) {
		return getHibernateTemplate().get(InvoiceChange.class, id);
	}

	@Override
	public void save(InvoiceChange invoiceChange) {
		getHibernateTemplate().save(invoiceChange);
		
	}

	@Override
	public String readContent(String orderNo) {
		String sql = "select * from invoice_change with(nolock) where order_no = '" + orderNo + "' order by invoice_change_date desc";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setMaxResults(1);
		query.addEntity(InvoiceChange.class);
		InvoiceChange invoiceChange = (InvoiceChange) query.uniqueResult();
		if(invoiceChange != null){
			return invoiceChange.getInvoiceChangeContent();
		}
		return null;
	}
	
	@Override
	public List<InvoiceChange> find(List<String> orderNos) {
		String sql = "select * from invoice_change with(nolock) where order_no in "
				+ AppUtil.getStringParmeList(orderNos);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(InvoiceChange.class);
		return query.list();
	}
	
	
}
