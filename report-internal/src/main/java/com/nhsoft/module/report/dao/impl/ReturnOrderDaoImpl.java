package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.ReturnOrderDao;
import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.ReturnOrder;
import com.nhsoft.module.report.model.ReturnOrderDetail;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
public class ReturnOrderDaoImpl extends DaoImpl implements ReturnOrderDao {


	@Override
	public List<Object[]> findDetailBySupplierNum(String systemBookCode,
												  List<Integer> branchNums, Integer supplierNum, Date dateFrom, Date dateTo,
												  List<Integer> selectItemNums) {
		Criteria criteria = currentSession().createCriteria(ReturnOrder.class, "r")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.createAlias("r.returnOrderDetails", "detail")
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (supplierNum != null) {
			criteria.add(Restrictions.eq("r.supplier.supplierNum", supplierNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.returnOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.returnOrderAuditTime",DateUtil.getMaxOfDate(dateTo)));
		}
		if (selectItemNums != null && selectItemNums.size() > 0) {
			criteria.add(Restrictions.in("detail.itemNum", selectItemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("r.returnOrderFid"))
				.add(Projections.property("r.supplier.supplierNum"))
				.add(Projections.property("r.branchNum"))
				.add(Projections.property("r.returnOrderAuditTime"))
				.add(Projections.property("detail.returnOrderDetailItemCode"))
				.add(Projections.property("detail.returnOrderDetailItemName"))
				.add(Projections.property("detail.returnOrderDetailItemSpec"))
				.add(Projections.property("detail.returnOrderDetailUseUnit"))
				.add(Projections.property("detail.returnOrderDetailUsePrice"))
				.add(Projections.property("detail.returnOrderDetailUseQty"))
				.add(Projections.property("detail.returnOrderDetailSubtotal"))
				.add(Projections.property("detail.returnOrderDetailItemUnit"))
				.add(Projections.property("detail.returnOrderDetailQty"))
				.add(Projections.property("detail.returnOrderDetailPresentUnit"))
				.add(Projections.property("detail.returnOrderDetailPresentUseQty"))
				.add(Projections.property("detail.itemNum"))
		);
		criteria.setMaxResults(100000);
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemAmountAndMoney(String systemBookCode,
												 Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(ReturnOrder.class, "r")
				.createAlias("r.returnOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.branchNum", branchNum))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("r.returnOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("r.returnOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sum("detail.returnOrderDetailQty"))
				.add(Projections.sum("detail.returnOrderDetailSubtotal"))

		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findQueryDetails(String systemBookCode,
										   List<Integer> branchNums, Date dateFrom, Date dateTo,
										   List<Integer> itemNums, List<String> itemCategoryCodes,
										   List<Integer> supplierNums, String operator, Integer storehouseNum) {
		Criteria criteria = createQuery(systemBookCode, branchNums, dateFrom, dateTo, itemNums, itemCategoryCodes,
				supplierNums, operator, storehouseNum);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("r.returnOrderFid"))
				.add(Projections.property("r.supplier"))
				.add(Projections.property("r.returnOrderAuditTime"))
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("detail.returnOrderDetailQty"))
				.add(Projections.property("detail.returnOrderDetailUsePrice"))
				.add(Projections.property("detail.returnOrderDetailSubtotal"))
				.add(Projections.property("detail.returnOrderDetailPresentUnit"))
				.add(Projections.property("detail.returnOrderDetailPresentUseQty"))
				.add(Projections.property("detail.returnOrderDetailPresentCount"))
				.add(Projections.property("detail.returnOrderDetailProducingDate"))
				.add(Projections.property("detail.returnOrderDetailPrice"))
				.add(Projections.property("detail.returnOrderDetailItemName"))
				.add(Projections.property("detail.returnOrderDetailItemMatrixNum"))
				.add(Projections.property("detail.returnOrderDetailUseQty"))
				.add(Projections.property("r.returnOrderDate"))
				.add(Projections.property("r.returnOrderMemo"))
				.add(Projections.property("detail.returnOrderDetailPresentUnit"))
				.add(Projections.property("detail.returnOrderDetailUseUnit"))
				.add(Projections.property("r.branchNum"))
				.add(Projections.property("r.returnOrderOperator"))
				.add(Projections.property("detail.returnOrderDetailUseRate"))

		);
		criteria.setMaxResults(500000);
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}


	@Override
	public List<Object[]> findQueryItems(String systemBookCode,
										 List<Integer> branchNums, Date dateFrom, Date dateTo,
										 List<Integer> itemNums, List<String> itemCategoryCodes,
										 List<Integer> supplierNums, String operator, Integer storehouseNum) {
		Criteria criteria = createQuery(systemBookCode, branchNums, dateFrom, dateTo, itemNums, itemCategoryCodes,
				supplierNums, operator, storehouseNum);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.supplier"))
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.returnOrderDetailItemMatrixNum"))
				.add(Projections.sum("detail.returnOrderDetailQty"))
				.add(Projections.sum("detail.returnOrderDetailSubtotal"))
				.add(Projections.sum("detail.returnOrderDetailPresentCount"))
				.add(Projections.sqlProjection("sum(return_order_detail_price * return_order_detail_present_count) as presentMoney", new String[]{"presentMoney"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.returnOrderDetailUseQty"))
				.add(Projections.sum("detail.returnOrderDetailPresentUseQty"))
				.add(Projections.groupProperty("r.branchNum"))
		);
		return criteria.list();
	}


	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(ReturnOrder.class, "r")
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("r.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("r.returnOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("r.returnOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}


	@Override
	public List<ReturnOrderDetail> findDetails(List<String> returnOrderFids) {
		String sql = "select detail.* from return_order_detail as detail with(nolock) where detail.return_order_fid in " + AppUtil.getStringParmeList(returnOrderFids);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity("detail", ReturnOrderDetail.class);
		return query.list();
	}



	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.branch_num, sum(detail.return_order_detail_qty) as qty, sum(detail.return_order_detail_subtotal) as money ");
		sb.append("from return_order_detail as detail with(nolock) inner join return_order as r with(nolock) ");
		sb.append("on r.return_order_fid = detail.return_order_fid where r.system_book_code = :systemBookCode ");
		sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " and r.return_order_state_code = 3 ");
		if(dateFrom != null){
			sb.append("and r.return_order_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and r.return_order_audit_time <= :dateTo ");
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by r.branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.branch_num, detail.item_num, sum(detail.return_order_detail_qty) as qty, sum(detail.return_order_detail_subtotal) as money ");
		sb.append("from return_order_detail as detail with(nolock) inner join return_order as r with(nolock) ");
		sb.append("on r.return_order_fid = detail.return_order_fid where r.system_book_code = :systemBookCode ");
		sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " and r.return_order_state_code = 3 ");
		if(dateFrom != null){
			sb.append("and r.return_order_audit_time >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and r.return_order_audit_time <= :dateTo ");
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by r.branch_num,detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		return objects;
	}



	@Override
	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode,
													 List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(ReturnOrder.class, "r")
				.createAlias("r.returnOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("r.returnOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("r.returnOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if(categoryCodes !=null && categoryCodes.size() > 0) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.eq("item.systemBookCode", systemBookCode))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"))
					.add(Restrictions.in("item.itemCategoryCode", categoryCodes));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if(itemNums != null && itemNums.size() > 0) {
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("r.supplier.supplierNum"))
				.add(Projections.sum("detail.returnOrderDetailQty"))
				.add(Projections.sum("detail.returnOrderDetailSubtotal"))
				.add(Projections.sum("detail.returnOrderDetailAssistQty"))
		);
		return criteria.list();
	}



	@Override
	public List<Object[]> findItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(ReturnOrder.class, "r")
				.createAlias("r.returnOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("r.returnOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("r.returnOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if(categoryCodes !=null && categoryCodes.size() > 0) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.eq("item.systemBookCode", systemBookCode))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"))
					.add(Restrictions.in("item.itemCategoryCode", categoryCodes));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if(itemNums != null && itemNums.size() > 0) {
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("r.supplier.supplierNum"))
				.add(Projections.sum("detail.returnOrderDetailQty"))
				.add(Projections.sum("detail.returnOrderDetailSubtotal"))
				.add(Projections.sum("detail.returnOrderDetailAssistQty"))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findBranchItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(ReturnOrder.class, "r")
				.createAlias("r.returnOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("r.returnOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("r.returnOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if(categoryCodes !=null && categoryCodes.size() > 0) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.eq("item.systemBookCode", systemBookCode))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"))
					.add(Restrictions.in("item.itemCategoryCode", categoryCodes));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if(itemNums != null && itemNums.size() > 0) {
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("r.branchNum"))
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("r.supplier.supplierNum"))
				.add(Projections.sum("detail.returnOrderDetailQty"))
				.add(Projections.sum("detail.returnOrderDetailSubtotal"))
				.add(Projections.sum("detail.returnOrderDetailAssistQty"))
		);
		return criteria.list();
	}


	private Criteria createQuery(String systemBookCode,
								 List<Integer> branchNums, Date dateFrom, Date dateTo,
								 List<Integer> itemNums, List<String> itemCategoryCodes,
								 List<Integer> supplierNums, String operator, Integer storehouseNum){
		Criteria criteria = currentSession().createCriteria(ReturnOrder.class, "r")
				.createAlias("r.returnOrderDetails", "detail")
				.add(Restrictions.eq("r.systemBookCode", systemBookCode))
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.between("r.returnOrderAuditTime", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)))
				.add(Restrictions.in("r.branchNum", branchNums));
		if(supplierNums != null && supplierNums.size() > 0){
			criteria.add(Restrictions.in("r.supplier.supplierNum", supplierNums));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		if(itemCategoryCodes != null && itemCategoryCodes.size() > 0){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.in("item.itemCategoryCode", itemCategoryCodes))
					.add(Restrictions.eq("item.systemBookCode", systemBookCode))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if(StringUtils.isNotEmpty(operator)){
			criteria.add(Restrictions.eq("r.returnOrderOperator", operator));
		}
		if(storehouseNum != null){
			criteria.add(Restrictions.eq("r.storehouseNum", storehouseNum));
		}
		return criteria;
	}





}