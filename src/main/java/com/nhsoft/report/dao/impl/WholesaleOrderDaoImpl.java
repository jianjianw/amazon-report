package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.WholesaleOrderDao;
import com.nhsoft.report.model.PosItem;
import com.nhsoft.report.model.WholesaleOrder;
import com.nhsoft.report.model.WholesaleOrderDetail;
import com.nhsoft.module.report.query.WholesaleProfitQuery;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Repository
public class WholesaleOrderDaoImpl extends DaoImpl implements WholesaleOrderDao {

	@Override
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum,
									  Date dateFrom, Date dateTo, List<Integer> itemNums, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, branchNum, dateFrom, dateTo, regionNums);
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.orderDetailItemMatrixNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.orderDetailPresentQty"))
		);
		return criteria.list();
	}

	private Criteria createCriteria(String systemBookCode, Integer branchNum,
									Date dateFrom, Date dateTo, List<Integer> regionNums){
		Criteria criteria = currentSession().createCriteria(WholesaleOrder.class, "w")
				.createAlias("w.wholesaleOrderDetails", "detail")
				.add(Restrictions.eq("w.systemBookCode", systemBookCode))
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));

		if(branchNum != null){
			criteria.add(Restrictions.eq("w.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("w.wholesaleOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("w.wholesaleOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if(regionNums != null && regionNums.size() > 0){
			criteria.add(Restrictions.in("w.regionNum", regionNums));
		}
		return criteria;
	}

	@Override
	public List<Object[]> findItemDateSummary(
			String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, branchNum, dateFrom, dateTo, regionNums);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sqlGroupProjection("convert(varchar(12),wholesale_order_audit_time,23) as date", "convert(varchar(12),wholesale_order_audit_time,23)",
						new String[]{"date"}, new Type[]{StandardBasicTypes.STRING}))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode,
												  Integer branchNum, Date dateFrom, Date dateTo,
												  List<Integer> itemNums, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, branchNum, dateFrom, dateTo, regionNums);
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sqlGroupProjection("substring(convert(varchar(12), wholesale_order_audit_time, 23),0, 8) as date", "substring(convert(varchar(12), wholesale_order_audit_time, 23),0, 8)",
						new String[]{"date"}, new Type[]{StandardBasicTypes.STRING}))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findMoneyGroupByClient(WholesaleProfitQuery wholesaleProfitQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select w.clientFid, sum(detail.orderDetailMoney), sum(detail.orderDetailCost * detail.orderDetailQty), ");
		sb.append("sum(detail.orderDetailQty * detail.orderDetailSalePrice), sum(detail.orderDetailQty/p.itemWholesaleRate), sum(detail.orderDetailUseQty), ");
		sb.append("sum(detail.orderDetailQty), sum(detail.orderDetailPresentQty), sum(detail.orderDetailPresentQty/p.itemWholesaleRate), sum(detail.orderDetailCost * detail.orderDetailPresentQty), ");
		sb.append("sum(detail.orderDetailPrice * detail.orderDetailPresentQty) ");
		sb.append("from WholesaleOrder as w inner join w.wholesaleOrderDetails as detail, PosItem as p ");
		sb.append("where p.itemNum = detail.itemNum and p.systemBookCode = w.systemBookCode and w.systemBookCode = :systemBookCode and w.state.stateCode = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");

		if(wholesaleProfitQuery.getBanchNum()!= null){
			sb.append("and w.branchNum = :branchNum ");
		}
		if(wholesaleProfitQuery.getBranchNums() != null && wholesaleProfitQuery.getBranchNums().size() > 0){
			sb.append("and w.branchNum in " + AppUtil.getIntegerParmeList(wholesaleProfitQuery.getBranchNums()));
		}
		if(wholesaleProfitQuery.getDateType() == null){
			wholesaleProfitQuery.setDateType(AppConstants.STATE_AUDIT_TIME);
		}
		if(wholesaleProfitQuery.getDateType().equals(AppConstants.STATE_AUDIT_TIME)){
			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleOrderAuditTime >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleOrderAuditTime <= :dateTo ");
			}

		} else {
			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleOrderDate >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleOrderDate <= :dateTo ");
			}
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			sb.append("and p.itemCategoryCode in (:categorys) ");
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			sb.append("and w.clientFid in (:clientFids) ");
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			sb.append("and w.wholesaleOrderAuditor = :auditor ");
		}
		if(wholesaleProfitQuery.getSellers() != null && wholesaleProfitQuery.getSellers().size() > 0){
			sb.append("and w.wholesaleOrderSeller in " + AppUtil.getStringParmeList(wholesaleProfitQuery.getSellers()));
		}
		sb.append("group by w.clientFid ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", wholesaleProfitQuery.getSystemBookCode());
		if(wholesaleProfitQuery.getBanchNum()!= null){
			query.setInteger("branchNum", wholesaleProfitQuery.getBanchNum());
		}
		if(wholesaleProfitQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom()));
		}
		if(wholesaleProfitQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo()));
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			query.setParameterList("itemNums", wholesaleProfitQuery.getPosItemNums(), StandardBasicTypes.INTEGER);

		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			query.setParameterList("categorys", wholesaleProfitQuery.getCategorys(), StandardBasicTypes.STRING);

		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			query.setParameterList("clientFids", wholesaleProfitQuery.getClientFids(), StandardBasicTypes.STRING);
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			query.setParameterList("regionNums", wholesaleProfitQuery.getRegionNums(), StandardBasicTypes.INTEGER);
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			query.setInteger("storehouseNum", wholesaleProfitQuery.getStorehouseNum());
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			query.setString("auditor", wholesaleProfitQuery.getAuditor());
		}
		query.setLockOptions(LockOptions.READ);
		return query.list();
	}



	@Override
	public Object[] readProfitSummary(WholesaleProfitQuery wholesaleProfitQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(detail.orderDetailMoney), sum(detail.orderDetailCost * (detail.orderDetailQty + detail.orderDetailPresentQty)), sum((detail.orderDetailQty + detail.orderDetailPresentQty) * detail.orderDetailSalePrice), sum((detail.orderDetailQty + detail.orderDetailPresentQty)/p.itemWholesaleRate) ");
		sb.append("from WholesaleOrder as w inner join w.wholesaleOrderDetails as detail, PosItem as p ");
		sb.append("where p.itemNum = detail.itemNum and p.systemBookCode = w.systemBookCode and w.systemBookCode = :systemBookCode and w.state.stateCode = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if(wholesaleProfitQuery.getBanchNum()!= null){
			sb.append("and w.branchNum = :branchNum ");
		}
		if(wholesaleProfitQuery.getBranchNums() != null && wholesaleProfitQuery.getBranchNums().size() > 0){
			sb.append("and w.branchNum in " + AppUtil.getIntegerParmeList(wholesaleProfitQuery.getBranchNums()));
		}
		if(wholesaleProfitQuery.getDateType() == null){
			wholesaleProfitQuery.setDateType(AppConstants.STATE_AUDIT_TIME);
		}
		if(wholesaleProfitQuery.getDateType().equals(AppConstants.STATE_AUDIT_TIME)){

			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleOrderAuditTime >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleOrderAuditTime <= :dateTo ");
			}
		} else {
			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleOrderDate >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleOrderDate <= :dateTo ");
			}
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			sb.append("and p.itemCategoryCode in (:categorys) ");
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			sb.append("and w.clientFid in (:clientFids) ");
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			sb.append("and w.wholesaleOrderAuditor = :auditor ");
		}
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", wholesaleProfitQuery.getSystemBookCode());
		if(wholesaleProfitQuery.getBanchNum()!= null){
			query.setInteger("branchNum", wholesaleProfitQuery.getBanchNum());
		}
		if(wholesaleProfitQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom()));
		}
		if(wholesaleProfitQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo()));
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			query.setParameterList("itemNums", wholesaleProfitQuery.getPosItemNums(), StandardBasicTypes.INTEGER);

		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			query.setParameterList("categorys", wholesaleProfitQuery.getCategorys(), StandardBasicTypes.STRING);

		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			query.setParameterList("clientFids", wholesaleProfitQuery.getClientFids(), StandardBasicTypes.STRING);
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			query.setParameterList("regionNums", wholesaleProfitQuery.getRegionNums(), StandardBasicTypes.INTEGER);
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			query.setInteger("storehouseNum", wholesaleProfitQuery.getStorehouseNum());
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			query.setString("auditor", wholesaleProfitQuery.getAuditor());
		}
		query.setLockOptions(LockOptions.READ);

		return (Object[]) query.uniqueResult();
	}

	private Criteria createWholesaleProfitQuery(WholesaleProfitQuery wholesaleProfitQuery){
		Criteria criteria = currentSession().createCriteria(WholesaleOrder.class, "w")
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("w.systemBookCode", wholesaleProfitQuery.getSystemBookCode()))
				.createAlias("w.wholesaleOrderDetails", "detail");
		if(wholesaleProfitQuery.getBanchNum() != null){
			criteria.add(Restrictions.eq("w.branchNum", wholesaleProfitQuery.getBanchNum()));
		}
		if(wholesaleProfitQuery.getBranchNums() != null && wholesaleProfitQuery.getBranchNums().size() > 0){
			criteria.add(Restrictions.in("w.branchNum", wholesaleProfitQuery.getBranchNums()));
		}
		if(wholesaleProfitQuery.getDateType() == null){
			wholesaleProfitQuery.setDateType(AppConstants.STATE_AUDIT_TIME);
		}
		if(wholesaleProfitQuery.getDateType().equals(AppConstants.STATE_AUDIT_TIME)){
			if(wholesaleProfitQuery.getDateFrom() != null){
				criteria.add(Restrictions.ge("w.wholesaleOrderAuditTime", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom())));
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				criteria.add(Restrictions.le("w.wholesaleOrderAuditTime", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo())));
			}

		} else {
			if(wholesaleProfitQuery.getDateFrom() != null){
				criteria.add(Restrictions.ge("w.wholesaleOrderDate", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom())));
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				criteria.add(Restrictions.le("w.wholesaleOrderDate", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo())));
			}
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", wholesaleProfitQuery.getPosItemNums()));
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			criteria.add(Restrictions.in("w.clientFid", wholesaleProfitQuery.getClientFids()));
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			criteria.add(Restrictions.in("w.regionNum", wholesaleProfitQuery.getRegionNums()));
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.in("item.itemCategoryCode", wholesaleProfitQuery.getCategorys()))
					.add(Restrictions.eq("item.systemBookCode", wholesaleProfitQuery.getSystemBookCode()))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			criteria.add(Restrictions.eq("w.storehouseNum", wholesaleProfitQuery.getStorehouseNum()));
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			criteria.add(Restrictions.eq("w.wholesaleOrderAuditor", wholesaleProfitQuery.getAuditor()));
		}
		if(wholesaleProfitQuery.getSellers() != null && wholesaleProfitQuery.getSellers().size() > 0) {
			criteria.add(Restrictions.in("w.wholesaleOrderSeller", wholesaleProfitQuery.getSellers()));
		}
		return criteria;
	}

	@Override
	public List<Object[]> findMoneyGroupByItemNum(WholesaleProfitQuery wholesaleProfitQuery) {
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost, sum(order_detail_sale_price * order_detail_qty) as sale " ,
						new String[]{"cost", "sale"},
						new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.orderDetailUseQty"))
				.add(Projections.sum("detail.orderDetailPresentQty"))
				.add(Projections.sum("detail.orderDetailPresentUseQty"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_present_qty) as presentCost, sum(order_detail_price * order_detail_present_qty) as presentMoney " ,
						new String[]{"presentCost", "presentMoney"},
						new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findDetail(WholesaleProfitQuery wholesaleProfitQuery) {
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("w.wholesaleOrderFid"))
				.add(Projections.property("w.wholesaleOrderAuditTime"))
				.add(Projections.property("w.wholesaleOrderSeller"))
				.add(Projections.property("w.wholesaleOrderCreator"))
				.add(Projections.property("w.wholesaleOrderAuditor"))
				.add(Projections.property("w.clientFid"))
				.add(Projections.property("detail.orderDetailItemCode"))
				.add(Projections.property("detail.orderDetailItemName"))
				.add(Projections.property("detail.orderDetailItemSpec"))
				.add(Projections.property("detail.orderDetailUseUnit"))
				.add(Projections.property("detail.orderDetailUseQty"))
				.add(Projections.property("detail.orderDetailUsePrice"))
				.add(Projections.property("detail.orderDetailMoney"))
				.add(Projections.property("detail.orderDetailQty"))
				.add(Projections.property("detail.orderDetailCost"))
				.add(Projections.property("detail.orderDetailMemo"))
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("detail.orderDetailItemMatrixNum"))
				.add(Projections.property("detail.orderDetailPresentQty"))
				.add(Projections.property("w.wholesaleOrderInnerNo"))
				.add(Projections.property("detail.orderDetailCost"))
				.add(Projections.property("detail.orderDetailPrice"))
				.add(Projections.property("detail.orderDetailPresentUseQty"))
				.add(Projections.property("detail.orderDetailPresentUnit"))
				.add(Projections.property("detail.orderDetailProductingDate"))

		);
		return criteria.list();
	}


	@Override
	public List<WholesaleOrder> findToPicking(String systemBookCode, Integer centerBranchNum,
											  List<String> clientFids, Integer storehouseNum, List<Integer> regionNums) {
		Date initDate = DateUtil.getDateTimeHMS(AppConstants.INIT_TIME);
		Date dateTo = Calendar.getInstance().getTime();
		Date dateFrom = DateUtil.addDay(dateTo, -30);

		//审核且配货时间、发货时间都为null
		Criteria criteria = currentSession().createCriteria(WholesaleOrder.class, "w")
				.add(Restrictions.between("w.wholesaleOrderAuditTime", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)))
				.add(Restrictions.eq("w.branchNum", centerBranchNum))
				.add(Restrictions.isNull("w.wholesaleOrderPickingTime"))
				.add(Restrictions.disjunction().add(Restrictions.isNull("w.wholesaleOrderSendTime")).add(Restrictions.eq("w.wholesaleOrderSendTime", initDate)))
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("w.systemBookCode", systemBookCode));
		if(clientFids != null && clientFids.size() > 0){
			criteria.add(Restrictions.in("w.clientFid", clientFids));
		}
		if(storehouseNum != null){
			criteria.add(Restrictions.eq("w.storehouseNum", storehouseNum));
		}
		if(regionNums != null && regionNums.size() > 0){
			criteria.add(Restrictions.in("w.regionNum", regionNums));
		}
		return criteria.list();
	}


	@Override
	public List<WholesaleOrder> findToShip(String systemBookCode, Integer centerBranchNum,
										   List<String> clientFids, Integer storehouseNum, List<Integer> regionNums) {
		Date initDate = DateUtil.getDateTimeHMS(AppConstants.INIT_TIME);
		Date dateTo = Calendar.getInstance().getTime();
		Date dateFrom = DateUtil.addDay(dateTo, -30);

		//审核且配货时间不为null、发货时间为null
		Criteria criteria = currentSession().createCriteria(WholesaleOrder.class, "w")
				.add(Restrictions.between("w.wholesaleOrderAuditTime", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)))
				.add(Restrictions.eq("w.branchNum", centerBranchNum))
				.add(Restrictions.isNotNull("w.wholesaleOrderPickingTime"))
				.add(Restrictions.disjunction().add(Restrictions.isNull("w.wholesaleOrderSendTime")).add(Restrictions.eq("w.wholesaleOrderSendTime", initDate)))
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("w.systemBookCode", systemBookCode));
		if(clientFids != null && clientFids.size() > 0){
			criteria.add(Restrictions.in("w.clientFid", clientFids));
		}
		if(storehouseNum != null){
			criteria.add(Restrictions.eq("w.storehouseNum", storehouseNum));
		}
		if(regionNums != null && regionNums.size() > 0){
			criteria.add(Restrictions.in("w.regionNum", regionNums));
		}
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemSumByCategory(String systemBookCode,
												Integer branchNum, Date dateFrom, Date dateTo,
												List<String> categoryCodeList, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, branchNum, dateFrom, dateTo,regionNums);
		if(categoryCodeList != null && categoryCodeList.size() > 0){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.in("item.itemCategoryCode", categoryCodeList))
					.add(Restrictions.eq("item.systemBookCode", systemBookCode))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.orderDetailItemMatrixNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}



	@Override
	public List<Object[]> findMoneyGroupByClientItemNum(WholesaleProfitQuery wholesaleProfitQuery) {
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("w.clientFid"))
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.orderDetailItemMatrixNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost, sum(order_detail_sale_price * order_detail_qty) as sale" ,
						new String[]{"cost", "sale"},
						new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.orderDetailUseQty"))
				.add(Projections.sum("detail.orderDetailPresentQty"))
				.add(Projections.sum("detail.orderDetailPresentUseQty"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_present_qty) as presentCost, sum(order_detail_price * order_detail_present_qty) as presentMoney" ,
						new String[]{"presentCost", "presentMoney"},
						new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}


	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(WholesaleOrder.class, "w")
				.add(Restrictions.eq("w.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("w.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("w.wholesaleOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("w.wholesaleOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}



	@Override
	public List<Object[]> findDueMoney(String systemBookCode,
									   Integer branchNum, List<String> clientFids, Date dateFrom,
									   Date dateTo, List<Integer> regionNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select clientFid, sum(wholesaleOrderDueMoney - wholesaleOrderDiscountMoney - wholesaleOrderPaidMoney), sum(wholesaleOrderDueMoney) ");
		sb.append("from WholesaleOrder where systemBookCode = :systemBookCode and state.stateCode = 3 ");
		if(branchNum != null){
			sb.append("and branchNum = :branchNum  ");
		}
		if(dateFrom != null){
			sb.append("and wholesaleOrderPaymentDate >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and wholesaleOrderPaymentDate <= :dateTo ");
		}
		if(clientFids != null && clientFids.size() > 0){
			sb.append("and clientFid in (:clientFids) ");
		}
		if(regionNums != null && regionNums.size() > 0){
			sb.append("and regionNum in (:regionNums) ");
		}
		sb.append("group by clientFid ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(branchNum != null){
			query.setInteger("branchNum", branchNum);
		}
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		if(clientFids != null && clientFids.size() > 0){
			query.setParameterList("clientFids", clientFids, StandardBasicTypes.STRING);
		}
		if(regionNums != null && regionNums.size() > 0){
			query.setParameterList("regionNums", regionNums, StandardBasicTypes.INTEGER);
		}
		query.setLockOptions(LockOptions.READ);

		return query.list();
	}


	@Override
	public List<Object[]> findMoneyGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes,
												 List<Integer> itemNums, List<String> clients, List<Integer> regionNums, Integer storehouseNum, String auditor, String dateType, List<String> sellers) {
		StringBuffer sb = new StringBuffer();
		sb.append("select w.branchNum, sum(detail.orderDetailMoney), sum(detail.orderDetailCost * detail.orderDetailQty), ");
		sb.append("sum(detail.orderDetailQty * detail.orderDetailSalePrice), sum(detail.orderDetailQty/p.itemWholesaleRate), sum(detail.orderDetailUseQty), ");
		sb.append("sum(detail.orderDetailQty), sum(detail.orderDetailPresentQty), sum(detail.orderDetailPresentUseQty), sum(detail.orderDetailPresentQty * detail.orderDetailPrice), ");
		sb.append("sum(detail.orderDetailPresentQty * detail.orderDetailCost ) ");
		sb.append("from WholesaleOrder as w inner join w.wholesaleOrderDetails as detail, PosItem as p ");
		sb.append("where p.itemNum = detail.itemNum and p.systemBookCode = w.systemBookCode and w.systemBookCode = :systemBookCode and w.state.stateCode = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and w.branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateType == null){
			dateType = AppConstants.STATE_AUDIT_TIME;
		}
		if(dateType.equals(AppConstants.STATE_AUDIT_TIME)){

			if(dateFrom != null){
				sb.append("and w.wholesaleOrderAuditTime >= :dateFrom ");
			}
			if(dateTo != null){
				sb.append("and w.wholesaleOrderAuditTime <= :dateTo ");
			}
		} else {
			if(dateFrom != null){
				sb.append("and w.wholesaleOrderDate >= :dateFrom ");
			}
			if(dateTo != null){
				sb.append("and w.wholesaleOrderDate <= :dateTo ");
			}

		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(categoryCodes != null && categoryCodes.size() > 0){
			sb.append("and p.itemCategoryCode in (:categorys) ");
		}
		if(clients != null && clients.size() > 0){
			sb.append("and w.clientFid in (:clientFids) ");
		}
		if(regionNums != null && regionNums.size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(storehouseNum != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(auditor)){
			sb.append("and w.wholesaleOrderAuditor = :auditor ");
		}
		if(sellers != null && sellers.size() > 0){
			sb.append("and w.wholesaleOrderSeller in " + AppUtil.getStringParmeList(sellers));
		}
		sb.append("group by w.branchNum ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		if(itemNums != null && itemNums.size() > 0){
			query.setParameterList("itemNums", itemNums, StandardBasicTypes.INTEGER);

		}
		if(categoryCodes != null && categoryCodes.size() > 0){
			query.setParameterList("categorys", categoryCodes, StandardBasicTypes.STRING);

		}
		if(clients != null && clients.size() > 0){
			query.setParameterList("clientFids", clients, StandardBasicTypes.STRING);
		}
		if(regionNums != null && regionNums.size() > 0){
			query.setParameterList("regionNums", regionNums, StandardBasicTypes.INTEGER);
		}
		if(storehouseNum != null){
			query.setInteger("storehouseNum", storehouseNum);
		}
		if(StringUtils.isNotEmpty(auditor)){
			query.setString("auditor", auditor);
		}
		query.setLockOptions(LockOptions.READ);

		return query.list();
	}


	@Override
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, List<String> clientFids, Date dateFrom,
									  Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		WholesaleProfitQuery wholesaleProfitQuery = new WholesaleProfitQuery();
		wholesaleProfitQuery.setSystemBookCode(systemBookCode);
		wholesaleProfitQuery.setBanchNum(branchNum);
		wholesaleProfitQuery.setDateFrom(dateFrom);
		wholesaleProfitQuery.setDateTo(dateTo);
		wholesaleProfitQuery.setCategorys(categoryCodes);
		wholesaleProfitQuery.setPosItemNums(itemNums);
		wholesaleProfitQuery.setClientFids(clientFids);
		wholesaleProfitQuery.setRegionNums(regionNums);

		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.max("w.wholesaleOrderAuditTime"))
				.add(Projections.min("w.wholesaleOrderAuditTime"))
				.add(Projections.count("detail.id.wholesaleOrderFid"))
				.add(Projections.sum("detail.orderDetailUseQty"))
				.add(Projections.sum("detail.orderDetailPresentQty"))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findMoneyGroupByBranchItem(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													 Date dateTo, List<Integer> itemNums, List<String> clients, List<Integer> regionNums) {
		Criteria criteria = currentSession().createCriteria(WholesaleOrder.class, "w")
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("w.systemBookCode", systemBookCode))
				.createAlias("w.wholesaleOrderDetails", "detail");
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("w.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("w.wholesaleOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("w.wholesaleOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		if(clients != null && clients.size() > 0){
			criteria.add(Restrictions.in("w.clientFid", clients));
		}
		if(regionNums != null && regionNums.size() > 0){
			criteria.add(Restrictions.in("w.regionNum", regionNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("w.branchNum"))
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.orderDetailItemMatrixNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost, sum(order_detail_sale_price * order_detail_qty) as sale " ,
						new String[]{"cost", "sale"},
						new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}


	@Override
	public List<Integer> findClientSaledItemNums(String systemBookCode, Integer branchNum, String clientFid) {
		Criteria criteria = currentSession().createCriteria(WholesaleOrder.class, "w")
				.createAlias("w.wholesaleOrderDetails", "detail")
				.add(Restrictions.eq("w.systemBookCode", systemBookCode))
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if(branchNum != null){
			criteria.add(Restrictions.eq("w.branchNum", branchNum));
		}
		if(StringUtils.isNotEmpty(clientFid)){
			criteria.add(Restrictions.eq("w.clientFid", clientFid));
		}
		criteria.setProjection(Projections.distinct(Projections.property("detail.itemNum")));
		return criteria.list();
	}



	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select w.branchNum, sum(detail.orderDetailMoney), sum(detail.orderDetailCost * detail.orderDetailQty), sum(detail.orderDetailQty * detail.orderDetailSalePrice), sum(detail.orderDetailQty/p.itemWholesaleRate) ");
		sb.append("from WholesaleOrder as w inner join w.wholesaleOrderDetails as detail , PosItem as p ");
		sb.append("where p.itemNum = detail.itemNum and p.systemBookCode = w.systemBookCode and w.systemBookCode = :systemBookCode and w.state.stateCode = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and w.branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and w.wholesaleOrderAuditTime >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and w.wholesaleOrderAuditTime <= :dateTo ");
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.itemNum in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by w.branchNum");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		query.setLockOptions(LockOptions.READ);

		return query.list();
	}




	@Override
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.itemNum, sum(detail.orderDetailQty), sum(detail.orderDetailMoney), sum(detail.orderDetailCost * detail.orderDetailQty) ");
		sb.append("from WholesaleOrder as w inner join w.wholesaleOrderDetails as detail ");
		sb.append("where w.systemBookCode = :systemBookCode and w.state.stateCode = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and w.branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and w.wholesaleOrderAuditTime >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and w.wholesaleOrderAuditTime <= :dateTo ");
		}
		sb.append("group by detail.itemNum");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(dateFrom != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		query.setLockOptions(LockOptions.READ);

		return query.list();
	}


	@Override
	public List<Object[]> findMoneyGroupByItemMatrix(WholesaleProfitQuery wholesaleProfitQuery) {
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.orderDetailItemMatrixNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost, sum(order_detail_sale_price * order_detail_qty) as sale " ,
						new String[]{"cost", "sale"},
						new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.orderDetailUseQty"))
				.add(Projections.sum("detail.orderDetailPresentQty"))
				.add(Projections.sum("detail.orderDetailPresentUseQty"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_present_qty) as presentCost, sum(order_detail_price * order_detail_present_qty) as presentMoney " ,
						new String[]{"presentCost", "presentMoney"},
						new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}


	@Override
	public List<WholesaleOrderDetail> findDetails(List<String> wholesaleOrderFids) {
		String sql = "select detail.* from wholesale_order_detail as detail with(nolock) where detail.wholesale_order_fid in " + AppUtil.getStringParmeList(wholesaleOrderFids);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity("detail", WholesaleOrderDetail.class);
		return query.list();
	}



	@Override
	public List<Object[]> findOperatorSummary(String systemBookCode,
											  List<Integer> branchNums, Date dateFrom, Date dateTo,
											  List<String> operators, String operatorType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.operator, count(a.wholesale_order_fid) as orderCount, sum(a.wholesale_order_total_money) as orderMoney, sum(a.itemAmount) as itemAmount, ");
		sb.append("sum(a.useQty) as useQty ");
		sb.append("from ");
		sb.append("(select w.wholesale_order_fid, %s as operator, w.wholesale_order_total_money, count(distinct detail.item_num) as itemAmount, ");
		sb.append("sum(detail.order_detail_use_qty) as useQty ");
		sb.append("from wholesale_order_detail as detail with(nolock) inner join wholesale_order as w with(nolock) on detail.wholesale_order_fid = w.wholesale_order_fid ");
		sb.append("where w.system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and w.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and %t between :dateFrom and :dateTo ");
		sb.append("and w.wholesale_order_state_code = 3 ");
		if(operators != null && operators.size() > 0){
			sb.append("and %s in " + AppUtil.getStringParmeList(operators));
		}
		sb.append("group by w.wholesale_order_fid, %s, w.wholesale_order_total_money) as a group by a.operator ");

		String sql = sb.toString();
		if(operatorType.equals(AppConstants.ORDER_OPERATOR_TYPE_CREATOR)){
			sql = sql.replaceAll("%s", "w.wholesale_order_creator");
			sql = sql.replaceAll("%t", "w.wholesale_order_create_time");
		} else if(operatorType.equals(AppConstants.ORDER_OPERATOR_TYPE_AUDITOR)){
			sql = sql.replaceAll("%s", "w.wholesale_order_auditor");
			sql = sql.replaceAll("%t", "w.wholesale_order_audit_time");
		} else if(operatorType.equals(AppConstants.ORDER_OPERATOR_TYPE_PICKER)){
			sql = sql.replaceAll("%s", "w.wholesale_order_picker");
			sql = sql.replaceAll("%t", "w.wholesale_order_picking_time");
		} else if(operatorType.equals(AppConstants.ORDER_OPERATOR_TYPE_SENDER)){
			sql = sql.replaceAll("%s", "w.wholesale_order_sender");
			sql = sql.replaceAll("%t", "w.wholesale_order_send_time");
		}
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}



	@Override
	public List<Object[]> findItemLotSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
											 List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.order_detail_lot_number,");
		sb.append("sum(detail.order_detail_qty) as amount, sum(detail.order_detail_money) as money, ");
		sb.append("sum(detail.order_detail_money - detail.order_detail_cost * order_detail_qty) as profit, ");
		sb.append("sum(detail.order_detail_use_qty) as useQty ");
		sb.append("from wholesale_order_detail as detail with(nolock) inner join wholesale_order as w with(nolock) on detail.wholesale_order_fid = w.wholesale_order_fid ");
		sb.append("where w.system_book_code = :systemBookCode and w.branch_num = :branchNum ");
		sb.append("and w.wholesale_order_audit_time between :dateFrom and :dateTo and w.wholesale_order_state_code = 3 and detail.order_detail_lot_number is not null and detail.order_detail_lot_number != '' ");

		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num, detail.order_detail_lot_number ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}



	@Override
	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums,
											  Date dateFrom, Date dateTo, List<Integer> itemNums, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, null, dateFrom, dateTo, regionNums);
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("branchNum", branchNums));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.supplierNum"))
				.add(Projections.groupProperty("detail.orderDetailItemMatrixNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.orderDetailPresentQty"))
				.add(Projections.sum("detail.orderDetailAssistQty"))
		);
		return criteria.list();
	}



	@Override
	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, null, dateFrom, dateTo, regionNums);
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("branchNum", branchNums));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("branchNum"))
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.supplierNum"))
				.add(Projections.groupProperty("detail.orderDetailItemMatrixNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.orderDetailPresentQty"))
				.add(Projections.sum("detail.orderDetailAssistQty"))
		);
		return criteria.list();
	}




	@Override
	public List<Object[]> findItemSupplierDetail(String systemBookCode,
												 List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, null, dateFrom, dateTo, regionNums);
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("w.branchNum", branchNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("w.wholesaleOrderFid"))
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("w.branchNum"))
				.add(Projections.property("detail.supplierNum"))
				.add(Projections.property("detail.orderDetailQty"))
				.add(Projections.property("detail.orderDetailUsePrice"))
				.add(Projections.property("detail.orderDetailUseQty"))
				.add(Projections.property("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.property("w.wholesaleOrderAuditTime"))
				.add(Projections.property("detail.orderDetailItemName"))
		);
		return criteria.list();
	}



	@Override
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums,
										  Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, null, dateFrom, dateTo, regionNums);
		if(branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("w.branchNum", branchNums));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		if(categoryCodes != null && categoryCodes.size() > 0){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.in("item.itemCategoryCode", categoryCodes))
					.add(Restrictions.eq("item.systemBookCode", systemBookCode))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.supplierNum"))
				.add(Projections.sum("detail.orderDetailQty"))
				.add(Projections.sum("detail.orderDetailMoney"))
				.add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.orderDetailPresentQty"))
				.add(Projections.sum("detail.orderDetailAssistQty"))
		);
		return criteria.list();
	}



	@Override
	public List<WholesaleOrder> find(String systemBookCode, List<Integer> branchNums, String query, Date dateFrom, Date dateTo) {
		if(StringUtils.isEmpty(query)) {
			return new ArrayList<WholesaleOrder>();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select w.* from wholesale_order as w with(nolock) inner join pos_client as p with(nolock) on w.client_fid = p.client_fid where w.system_book_code = '" + systemBookCode + "' and client_del_tag = 0 ");
		if(branchNums != null && branchNums.size() > 0) {
			sb.append("and w.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(query.startsWith(AppConstants.S_Prefix_WO + systemBookCode)) {
			sb.append("and wholesale_order_fid = '" + query + "' ");
		} else {
			if(AppUtil.isValidMobilePhoneNumber(query)){
				sb.append("and p.client_phone = '" + query + "' ");

			} else if(AppUtil.isChinese(query)){
				sb.append("and p.client_name = '" + query + "' ");

			} else if(AppUtil.isNumber(query) && query.length() <= 5){

				sb.append("and wholesale_order_fid like '%" + query + "' ");

			} else {

				sb.append("and (wholesale_order_fid like '%" + query + "' or p.client_name like '%" + query + "%' or p.client_phone like '%" + query + "%') " );
			}
			if(dateFrom != null) {
				sb.append("and wholesale_order_create_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
			}
			if(dateTo != null) {
				sb.append("and wholesale_order_create_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
			}
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.addEntity("w", WholesaleOrder.class);
		return sqlQuery.list();
	}

}
