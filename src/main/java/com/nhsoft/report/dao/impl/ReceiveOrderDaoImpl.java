package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.ReceiveOrderDao;
import com.nhsoft.report.model.PosItem;
import com.nhsoft.report.model.ReceiveOrder;
import com.nhsoft.report.model.ReceiveOrderDetail;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceiveOrderDaoImpl extends DaoImpl implements ReceiveOrderDao {


	@Override
	public ReceiveOrder read(String fid) {
		return (ReceiveOrder) getHibernateTemplate().get(ReceiveOrder.class, fid);
	}


	@Override
	public List<Object[]> findDetailBySupplierNum(String systemBookCode, List<Integer> branchNums, Integer supplierNum,
												  Date dateFrom, Date dateTo, List<Integer> selectItemNums) {
		Criteria criteria = currentSession().createCriteria(ReceiveOrder.class, "r")
				.createAlias("r.receiveOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (supplierNum != null) {
			criteria.add(Restrictions.eq("r.supplier.supplierNum", supplierNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.receiveOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.receiveOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if (selectItemNums != null && selectItemNums.size() > 0) {
			criteria.add(Restrictions.in("detail.itemNum", selectItemNums));
		}
		criteria.setProjection(Projections.projectionList().add(Projections.property("r.receiveOrderFid"))
				.add(Projections.property("r.supplier.supplierNum")).add(Projections.property("r.branchNum"))
				.add(Projections.property("r.receiveOrderAuditTime"))
				.add(Projections.property("detail.receiveOrderDetailItemCode"))
				.add(Projections.property("detail.receiveOrderDetailItemName"))
				.add(Projections.property("detail.receiveOrderDetailItemSpec"))
				.add(Projections.property("detail.receiveOrderDetailUseUnit"))
				.add(Projections.property("detail.receiveOrderDetailUsePrice"))
				.add(Projections.property("detail.receiveOrderDetailUseQty"))
				.add(Projections.property("detail.receiveOrderDetailSubtotal"))
				.add(Projections.property("detail.receiveOrderDetailItemUnit"))
				.add(Projections.property("detail.receiveOrderDetailQty"))
				.add(Projections.property("detail.receiveOrderDetailPresentUnit"))
				.add(Projections.property("detail.receiveOrderDetailPresentUseQty"))
				.add(Projections.property("detail.itemNum")));
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemAmountAndMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(ReceiveOrder.class, "r")
				.createAlias("r.receiveOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.branchNum", branchNum))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.receiveOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.receiveOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sum("detail.receiveOrderDetailQty"))
				.add(Projections.sum("detail.receiveOrderDetailSubtotal"))

		);
		return criteria.list();
	}

	private Criteria createQuery(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
								 List<Integer> itemNums, List<String> itemCategoryCodes, List<Integer> supplierNums, String operator,
								 Integer storehouseNum) {
		Criteria criteria = currentSession().createCriteria(ReceiveOrder.class, "r")
				.createAlias("r.receiveOrderDetails", "detail")
				.add(Restrictions.eq("r.systemBookCode", systemBookCode))
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.in("r.branchNum", branchNums));
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.receiveOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.receiveOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if (supplierNums != null && supplierNums.size() > 0) {
			criteria.add(Restrictions.in("r.supplier.supplierNum", supplierNums));
		}
		if (itemNums != null && itemNums.size() > 0) {
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		if (itemCategoryCodes != null && itemCategoryCodes.size() > 0) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.in("item.itemCategoryCode", itemCategoryCodes))
					.add(Restrictions.eq("item.systemBookCode", systemBookCode))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if (StringUtils.isNotEmpty(operator)) {
			criteria.add(Restrictions.eq("r.receiveOrderOperator", operator));
		}
		if (storehouseNum != null) {
			criteria.add(Restrictions.eq("r.storehouseNum", storehouseNum));
		}
		return criteria;
	}

	@Override
	public List<Object[]> findQueryDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
										   List<Integer> itemNums, List<String> itemCategoryCodes, List<Integer> supplierNums, String operator,
										   Integer storehouseNum) {
		Criteria criteria = createQuery(systemBookCode, branchNums, dateFrom, dateTo, itemNums, itemCategoryCodes,
				supplierNums, operator, storehouseNum);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("r.receiveOrderFid"))
				.add(Projections.property("r.supplier"))
				.add(Projections.property("r.receiveOrderAuditTime"))
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("detail.receiveOrderDetailQty"))
				.add(Projections.property("detail.receiveOrderDetailUsePrice"))
				.add(Projections.property("detail.receiveOrderDetailSubtotal"))
				.add(Projections.property("detail.receiveOrderDetailPresentUnit"))
				.add(Projections.property("detail.receiveOrderDetailPresentUseQty"))
				.add(Projections.property("detail.receiveOrderDetailPresentCount"))
				.add(Projections.property("detail.receiveOrderDetailProducingDate"))
				.add(Projections.property("detail.receiveOrderDetailCost"))
				.add(Projections.property("detail.receiveOrderDetailItemName"))
				.add(Projections.property("detail.receiveOrderDetailItemMatrixNum"))
				.add(Projections.property("r.receiveOrderDate"))
				.add(Projections.property("r.receiveOrderMemo"))
				.add(Projections.property("detail.receiveOrderDetailUseQty"))
				.add(Projections.property("detail.receiveOrderDetailPresentUnit"))
				.add(Projections.property("detail.receiveOrderDetailUseUnit"))
				.add(Projections.property("detail.receiveOrderDetailOtherMoney"))
				.add(Projections.property("r.branchNum"))
				.add(Projections.property("detail.receiveOrderDetailSupplierMoney"))
				.add(Projections.property("r.receiveOrderRepealFlag"))
				.add(Projections.property("r.receiveOrderAntiFlag"))
				.add(Projections.property("r.receiveOrderOperator"))
				.add(Projections.property("detail.receiveOrderDetailUseRate"))
				.add(Projections.property("detail.receiveOrderDetailPrice"))

		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findQueryItems(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
										 List<Integer> itemNums, List<String> itemCategoryCodes, List<Integer> supplierNums, String operator,
										 Integer storehouseNum) {
		Criteria criteria = createQuery(systemBookCode, branchNums, dateFrom, dateTo, itemNums, itemCategoryCodes,
				supplierNums, operator, storehouseNum);
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.groupProperty("r.supplier"))
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.receiveOrderDetailItemMatrixNum"))
				.add(Projections.sum("detail.receiveOrderDetailQty"))
				.add(Projections.sum("detail.receiveOrderDetailSubtotal"))
				.add(Projections.sum("detail.receiveOrderDetailPresentCount"))
				.add(Projections.sqlProjection(
						"sum(receive_order_detail_cost * receive_order_detail_present_count) as presentMoney",
						new String[] { "presentMoney" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
				.add(Projections.sum("detail.receiveOrderDetailUseQty"))
				.add(Projections.sum("detail.receiveOrderDetailPresentUseQty"))
				.add(Projections.sum("detail.receiveOrderDetailOtherMoney"))
				.add(Projections
						.sqlProjection(
								"sum(case when receive_order_detail_other_money > 0 then receive_order_detail_use_qty end) as otherQty",
								new String[] { "otherQty" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
				.add(Projections.groupProperty("r.branchNum"))
				.add(Projections.sum("detail.receiveOrderDetailSupplierMoney"))
				.add(Projections.sqlProjection("sum(receive_order_detail_price*receive_order_detail_qty) as saleMoney",
						new String[] {"saleMoney"}, new Type[] {StandardBasicTypes.BIG_DECIMAL})));
		return criteria.list();
	}

	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(ReceiveOrder.class, "r").add(
				Restrictions.eq("r.systemBookCode", systemBookCode));
		if (branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			criteria.add(Restrictions.eq("r.branchNum", branchNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.receiveOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.receiveOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}


	@Override
	public List<Object[]> findItemMatrixMaxProducingDates(String systemBookCode, Integer branchNum,
														  List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.receive_order_detail_item_matrix_num, max(detail.receive_order_detail_producing_date) ");
		sb.append("from receive_order_detail as detail with(nolock) inner join receive_order as r with(nolock) ");
		sb.append("on r.receive_order_fid = detail.receive_order_fid where r.system_book_code = :systemBookCode ");
		sb.append("and branch_num = :branchNum and receive_order_state_code = 3 ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}

		sb.append("group by detail.item_num, detail.receive_order_detail_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Integer> findFirstReceiveItem(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
											  List<String> categoryCodes) {
		Criteria criteria = currentSession().createCriteria(ReceiveOrder.class, "r")
				.createAlias("r.receiveOrderDetails", "detail")
				.add(Restrictions.eq("r.systemBookCode", systemBookCode))
				.add(Restrictions.eq("r.branchNum", branchNum))
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if (categoryCodes != null && categoryCodes.size() > 0) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "p")
					.add(Restrictions.eqProperty("p.itemNum", "detail.itemNum"))
					.add(Restrictions.in("p.itemCategoryCode", categoryCodes));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("p.itemNum"))));
		}

		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.min("r.receiveOrderAuditTime")));
		List<Object[]> objects = criteria.list();
		List<Integer> itemNums = new ArrayList<Integer>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			Date minDate = (Date) object[1];
			if (!DateUtil.betweenDate(minDate, DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo))) {
				continue;
			}
			itemNums.add(itemNum);
		}
		return itemNums;
	}

	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.branch_num, sum(detail.receive_order_detail_qty) as qty, sum(detail.receive_order_detail_subtotal) as money, ");
		sb.append("sum(receive_order_detail_tax_money) as taxMoney ");
		sb.append("from receive_order_detail as detail with(nolock) inner join receive_order as r with(nolock) ");
		sb.append("on r.receive_order_fid = detail.receive_order_fid where r.system_book_code = :systemBookCode ");
		sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums)
				+ " and r.receive_order_state_code = 3 ");
		if (dateFrom != null) {
			sb.append("and r.receive_order_audit_time >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and r.receive_order_audit_time <= :dateTo ");
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by r.branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if (dateTo != null) {
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.branch_num, detail.item_num, sum(detail.receive_order_detail_qty) as qty, sum(detail.receive_order_detail_subtotal) as money ");
		sb.append("from receive_order_detail as detail with(nolock) inner join receive_order as r with(nolock) ");
		sb.append("on r.receive_order_fid = detail.receive_order_fid where r.system_book_code = :systemBookCode ");
		sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums)
				+ " and r.receive_order_state_code = 3 ");
		if (dateFrom != null) {
			sb.append("and r.receive_order_audit_time >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and r.receive_order_audit_time <= :dateTo ");
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by r.branch_num,detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if (dateTo != null) {
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<ReceiveOrderDetail> findDetails(List<String> receiveOrderFids) {
		String sql = "select detail.* from receive_order_detail as detail with(nolock) where detail.receive_order_fid in "
				+ AppUtil.getStringParmeList(receiveOrderFids);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity("detail", ReceiveOrderDetail.class);
		return query.list();
	}


	@Override
	public List<Object[]> findOperatorItemLotSummary(String systemBookCode, Integer branchNum, Date dateFrom,
													 Date dateTo, List<String> employees, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.receive_order_operator, detail.item_num, detail.receive_order_detail_lot_number, r.receive_order_audit_time, r.receive_order_fid, r.supplier_num, ");
		sb.append("sum(detail.receive_order_detail_qty) as amount, sum(detail.receive_order_detail_subtotal) as money, ");
		sb.append("sum(detail.receive_order_detail_use_qty) as useQty ");
		sb.append("from receive_order_detail as detail with(nolock) inner join receive_order as r with(nolock) on detail.receive_order_fid = r.receive_order_fid ");
		sb.append("where r.system_book_code = :systemBookCode and r.branch_num = :branchNum ");
		sb.append("and r.receive_order_audit_time between :dateFrom and :dateTo and receive_order_state_code = 3 ");
		if (employees != null && employees.size() > 0) {
			sb.append("and r.receive_order_operator in " + AppUtil.getStringParmeList(employees));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by r.receive_order_operator, detail.item_num, detail.receive_order_detail_lot_number, r.receive_order_audit_time, r.receive_order_fid, r.supplier_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}


	@Override
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
										  List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(detail.receive_order_detail_qty) as qty, sum(detail.receive_order_detail_subtotal) as money ");
		sb.append("from receive_order_detail as detail with(nolock) inner join receive_order as r with(nolock) ");
		sb.append("on r.receive_order_fid = detail.receive_order_fid where r.system_book_code = :systemBookCode ");
		sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums)
				+ " and r.receive_order_state_code = 3 ");
		if (dateFrom != null) {
			sb.append("and r.receive_order_audit_time >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and r.receive_order_audit_time <= :dateTo ");
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if (dateTo != null) {
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(ReceiveOrder.class, "r")
				.createAlias("r.receiveOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.receiveOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.receiveOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
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
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("r.supplier.supplierNum"))
				.add(Projections.sum("detail.receiveOrderDetailQty"))
				.add(Projections.sum("detail.receiveOrderDetailSubtotal"))
				.add(Projections.sum("detail.receiveOrderDetailAssistQty"))

		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(ReceiveOrder.class, "r")
				.createAlias("r.receiveOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.receiveOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.receiveOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
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
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("r.supplier.supplierNum"))
				.add(Projections.sum("detail.receiveOrderDetailQty"))
				.add(Projections.sum("detail.receiveOrderDetailSubtotal"))
				.add(Projections.sum("detail.receiveOrderDetailAssistQty"))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findBranchItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(ReceiveOrder.class, "r")
				.createAlias("r.receiveOrderDetails", "detail")
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("r.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("r.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.receiveOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.receiveOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
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
				.add(Projections.sum("detail.receiveOrderDetailQty"))
				.add(Projections.sum("detail.receiveOrderDetailSubtotal"))
				.add(Projections.sum("detail.receiveOrderDetailAssistQty"))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findBizSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select convert(varchar(8), r.receive_order_audit_time, 112) as biz, count(r.receive_order_fid) as amount, sum(r.receive_order_total_money) as money ");
		sb.append("from receive_order as r with(nolock) ");
		sb.append("where r.system_book_code = :systemBookCode ");
		sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums)
				+ " and r.receive_order_state_code = 3 ");
		if (dateFrom != null) {
			sb.append("and r.receive_order_audit_time >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and r.receive_order_audit_time <= :dateTo ");
		}
		sb.append("group by convert(varchar(8), r.receive_order_audit_time, 112) ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (dateFrom != null) {
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if (dateTo != null) {
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Object[]> findOperatorItemLotSummary(String systemBookCode, Integer branchNum, List<String> lotNumbers, List<String> employees) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.receive_order_operator, detail.item_num, detail.receive_order_detail_lot_number, r.receive_order_audit_time, r.receive_order_fid, r.supplier_num, ");
		sb.append("sum(detail.receive_order_detail_qty) as amount, sum(detail.receive_order_detail_subtotal) as money, ");
		sb.append("sum(detail.receive_order_detail_use_qty) as useQty ");
		sb.append("from receive_order_detail as detail with(nolock) inner join receive_order as r with(nolock) on detail.receive_order_fid = r.receive_order_fid ");
		sb.append("where r.system_book_code = :systemBookCode and r.branch_num = :branchNum ");
		sb.append("and receive_order_state_code = 3 ");
		if (employees != null && employees.size() > 0) {
			sb.append("and r.receive_order_operator in " + AppUtil.getStringParmeList(employees));
		}
		if (lotNumbers != null && lotNumbers.size() > 0) {
			sb.append("and detail.receive_order_detail_lot_number in " + AppUtil.getStringParmeList(lotNumbers));
		}
		sb.append("group by r.receive_order_operator, detail.item_num, detail.receive_order_detail_lot_number, r.receive_order_audit_time, r.receive_order_fid, r.supplier_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		return query.list();
	}

}