package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.Dao;
import com.nhsoft.report.dao.PurchaseOrderDao;
import com.nhsoft.report.model.PurchaseOrder;
import com.nhsoft.report.model.PurchaseOrderDetail;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Repository
public class PurchaseOrderDaoImpl extends DaoImpl implements PurchaseOrderDao {



	@Override
	public PurchaseOrder read(String fid) {
		return (PurchaseOrder)getHibernateTemplate().get(PurchaseOrder.class, fid);
	}


	//不统计直配采购单在订量
	@Override
	public List<Object[]> findUnReceivedItemAmount(String systemBookCode,
												   Integer branchNum, List<Integer> itemNums) {

		Criteria criteria = currentSession().createCriteria(PurchaseOrder.class, "p")
				.createAlias("p.purchaseOrderDetails", "detail")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode))
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.ge("p.purchaseOrderDeadline", DateUtil.getMinOfDate(Calendar.getInstance().getTime())))
				.add(Restrictions.eq("p.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.sqlRestriction("storehouse_num in (select storehouse_num from branch_storehouse where system_book_code = ? and branch_num = ?)"
						,new Object[]{systemBookCode, branchNum}, new Type[]{StandardBasicTypes.STRING, StandardBasicTypes.INTEGER}));
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.purchaseOrderDetailPostItemMatrixNum"))
				.add(Projections.sqlProjection("sum(purchase_order_detail_qty - purchase_order_detail_recieved_amount) as amount",
						new String[]{"amount"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}


	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(PurchaseOrder.class, "t")
				.add(Restrictions.eq("t.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("t.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("t.purchaseOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("t.purchaseOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	@Override
	public Date getLastDate(String systemBookCode, Integer branchNum, Integer supplierNum) {
		Criteria criteria = currentSession().createCriteria(PurchaseOrder.class, "t")
				.add(Restrictions.eq("t.systemBookCode", systemBookCode))
				.add(Restrictions.eq("t.branchNum", branchNum));
		if(supplierNum != null){
			criteria.add(Restrictions.eq("t.supplier.supplierNum", supplierNum));
		}
		criteria.setProjection(Projections.property("t.purchaseOrderAuditTime"));
		criteria.addOrder(Order.desc("t.purchaseOrderAuditTime"));
		criteria.setMaxResults(1);
		return (Date) criteria.uniqueResult();
	}


	@Override
	public List<PurchaseOrderDetail> findDetails(List<String> purchaseOrderFids) {
		String sql = "select detail.* from purchase_order_detail as detail with(nolock) where detail.purchase_order_fid in " + AppUtil.getStringParmeList(purchaseOrderFids);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity("detail", PurchaseOrderDetail.class);
		return query.list();
	}

}