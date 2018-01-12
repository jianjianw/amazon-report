package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.WholesaleReturnDao;
import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.WholesaleReturn;
import com.nhsoft.module.report.query.WholesaleProfitQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class WholesaleReturnDaoImpl extends DaoImpl implements WholesaleReturnDao {


	@Override
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.itemNum, sum(detail.returnDetailQty), sum(detail.returnDetailMoney), sum(detail.returnDetailCost * detail.returnDetailQty) ");
		sb.append("from WholesaleReturn as w inner join w.wholesaleReturnDetails as detail ");
		sb.append("where w.systemBookCode = :systemBookCode and w.state.stateCode = 3 ");

		if(branchNums != null && branchNums.size() > 0){
			sb.append("and w.branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and w.wholesaleReturnAuditTime >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and w.wholesaleReturnAuditTime <= :dateTo ");
		}
		sb.append("group by detail.itemNum ");
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
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum,
									  List<String> clientFids, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
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
				.add(Projections.groupProperty("detail.returnDetailItemMatrixNum"))
				.add(Projections.sum("detail.returnDetailQty"))
				.add(Projections.sum("detail.returnDetailPresentQty"))
				.add(Projections.sum("detail.returnDetailMoney"))
				.add(Projections.sqlProjection("sum(return_detail_cost * return_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}

	private Criteria createWholesaleProfitQuery(WholesaleProfitQuery wholesaleProfitQuery){
		Criteria criteria = currentSession().createCriteria(WholesaleReturn.class, "w")
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.eq("w.systemBookCode", wholesaleProfitQuery.getSystemBookCode()))
				.createAlias("w.wholesaleReturnDetails", "detail");
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
				criteria.add(Restrictions.ge("w.wholesaleReturnAuditTime", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom())));
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				criteria.add(Restrictions.le("w.wholesaleReturnAuditTime", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo())));
			}

		} else {

			if(wholesaleProfitQuery.getDateFrom() != null){
				criteria.add(Restrictions.ge("w.wholesaleReturnDate", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom())));
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				criteria.add(Restrictions.le("w.wholesaleReturnDate", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo())));
			}
		}

		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", wholesaleProfitQuery.getPosItemNums()));
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			criteria.add(Restrictions.in("w.clientFid", wholesaleProfitQuery.getClientFids()));
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.in("item.itemCategoryCode", wholesaleProfitQuery.getCategorys()))
					.add(Restrictions.eq("item.systemBookCode", wholesaleProfitQuery.getSystemBookCode()))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			criteria.add(Restrictions.in("w.regionNum", wholesaleProfitQuery.getRegionNums()));
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			criteria.add(Restrictions.eq("w.storehouseNum", wholesaleProfitQuery.getStorehouseNum()));
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			criteria.add(Restrictions.eq("w.wholesaleReturnAuditor", wholesaleProfitQuery.getAuditor()));
		}
		if(wholesaleProfitQuery.getSellers() != null && wholesaleProfitQuery.getSellers().size() > 0) {
			criteria.add(Restrictions.in("w.wholesaleReturnSeller", wholesaleProfitQuery.getSellers()));
		}
		return criteria;
	}
	@Override
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums,
										  List<String> clientFids, Date dateFrom, Date dateTo,
										  List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		WholesaleProfitQuery wholesaleProfitQuery = new WholesaleProfitQuery();
		wholesaleProfitQuery.setSystemBookCode(systemBookCode);
		wholesaleProfitQuery.setBranchNums(branchNums);
		wholesaleProfitQuery.setDateFrom(dateFrom);
		wholesaleProfitQuery.setDateTo(dateTo);
		wholesaleProfitQuery.setCategorys(categoryCodes);
		wholesaleProfitQuery.setPosItemNums(itemNums);
		wholesaleProfitQuery.setClientFids(clientFids);
		wholesaleProfitQuery.setRegionNums(regionNums);
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.supplierNum"))
				.add(Projections.sum("detail.returnDetailQty"))
				.add(Projections.sum("detail.returnDetailPresentQty"))
				.add(Projections.sum("detail.returnDetailMoney"))
				.add(Projections.sqlProjection("sum(return_detail_cost * return_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.returnDetailAssistQty"))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums,
											  List<String> clientFids, Date dateFrom, Date dateTo,
											  List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		WholesaleProfitQuery wholesaleProfitQuery = new WholesaleProfitQuery();
		wholesaleProfitQuery.setSystemBookCode(systemBookCode);
		wholesaleProfitQuery.setBranchNums(branchNums);
		wholesaleProfitQuery.setDateFrom(dateFrom);
		wholesaleProfitQuery.setDateTo(dateTo);
		wholesaleProfitQuery.setCategorys(categoryCodes);
		wholesaleProfitQuery.setPosItemNums(itemNums);
		wholesaleProfitQuery.setClientFids(clientFids);
		wholesaleProfitQuery.setRegionNums(regionNums);
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.supplierNum"))
				.add(Projections.groupProperty("detail.returnDetailItemMatrixNum"))
				.add(Projections.sum("detail.returnDetailQty"))
				.add(Projections.sum("detail.returnDetailPresentQty"))
				.add(Projections.sum("detail.returnDetailMoney"))
				.add(Projections.sqlProjection("sum(return_detail_cost * return_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.returnDetailAssistQty"))
		);
		return criteria.list();
	}



	@Override
	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		WholesaleProfitQuery wholesaleProfitQuery = new WholesaleProfitQuery();
		wholesaleProfitQuery.setSystemBookCode(systemBookCode);
		wholesaleProfitQuery.setBranchNums(branchNums);
		wholesaleProfitQuery.setDateFrom(dateFrom);
		wholesaleProfitQuery.setDateTo(dateTo);
		wholesaleProfitQuery.setCategorys(categoryCodes);
		wholesaleProfitQuery.setPosItemNums(itemNums);
		wholesaleProfitQuery.setClientFids(clientFids);
		wholesaleProfitQuery.setRegionNums(regionNums);
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("branchNum"))
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.supplierNum"))
				.add(Projections.groupProperty("detail.returnDetailItemMatrixNum"))
				.add(Projections.sum("detail.returnDetailQty"))
				.add(Projections.sum("detail.returnDetailPresentQty"))
				.add(Projections.sum("detail.returnDetailMoney"))
				.add(Projections.sqlProjection("sum(return_detail_cost * return_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.sum("detail.returnDetailAssistQty"))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		WholesaleProfitQuery wholesaleProfitQuery = new WholesaleProfitQuery();
		wholesaleProfitQuery.setSystemBookCode(systemBookCode);
		wholesaleProfitQuery.setBranchNums(branchNums);
		wholesaleProfitQuery.setDateFrom(dateFrom);
		wholesaleProfitQuery.setDateTo(dateTo);
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("w.wholesaleReturnFid"))
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("w.branchNum"))
				.add(Projections.property("detail.supplierNum"))
				.add(Projections.property("detail.returnDetailQty"))
				.add(Projections.property("detail.returnDetailUsePrice"))
				.add(Projections.property("detail.returnDetailUseQty"))
				.add(Projections.property("detail.returnDetailMoney"))
				.add(Projections.sqlProjection("(return_detail_cost * return_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
				.add(Projections.property("w.wholesaleReturnAuditTime"))
				.add(Projections.property("detail.returnDetailItemName"))
		);
		return criteria.list();
	}



	@Override
	public List<Object[]> findItemDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		WholesaleProfitQuery wholesaleProfitQuery = new WholesaleProfitQuery();
		wholesaleProfitQuery.setSystemBookCode(systemBookCode);
		wholesaleProfitQuery.setBanchNum(branchNum);
		wholesaleProfitQuery.setDateFrom(dateFrom);
		wholesaleProfitQuery.setDateTo(dateTo);
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sqlGroupProjection("convert(varchar(12),wholesale_return_audit_time,23) as date", "convert(varchar(12),wholesale_return_audit_time,23)",
						new String[]{"date"}, new Type[]{StandardBasicTypes.STRING}))
				.add(Projections.sum("detail.returnDetailQty"))
				.add(Projections.sum("detail.returnDetailMoney"))
				.add(Projections.sqlProjection("sum(return_detail_cost * return_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}


	private Criteria createDetailCriteria(String systemBookCode, Integer branchNum,
										  Date dateFrom, Date dateTo, List<Integer> itemNums, List<Integer> regionNums){
		Criteria criteria = currentSession().createCriteria(WholesaleReturn.class, "w")
				.createAlias("w.wholesaleReturnDetails", "detail")
				.add(Restrictions.eq("w.systemBookCode", systemBookCode))
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if(branchNum != null){
			criteria.add(Restrictions.eq("w.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("w.wholesaleReturnAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("w.wholesaleReturnAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if(regionNums != null && regionNums.size() > 0){
			criteria.add(Restrictions.in("w.regionNum", regionNums));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		return criteria;
	}

	@Override
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
												  List<Integer> itemNums, List<Integer> regionNums) {
		Criteria criteria = createDetailCriteria(systemBookCode, branchNum, dateFrom, dateTo, itemNums, regionNums);
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sqlGroupProjection("substring(convert(varchar(12), wholesale_return_audit_time, 23),0, 8) as date", "substring(convert(varchar(12), wholesale_return_audit_time, 23),0, 8)",
						new String[]{"date"}, new Type[]{StandardBasicTypes.STRING}))
				.add(Projections.sum("detail.returnDetailMoney"))
				.add(Projections.sqlProjection("sum(return_detail_cost * return_detail_qty) as cost"
						, new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
		);
		return criteria.list();
	}



	@Override
	public List<Object[]> findMoneyGroupByClient(WholesaleProfitQuery wholesaleProfitQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select w.clientFid, sum(detail.returnDetailMoney), sum(detail.returnDetailCost * detail.returnDetailQty), sum(detail.returnDetailQty * item.itemRegularPrice), ");
		sb.append("sum(detail.returnDetailQty/item.itemWholesaleRate), sum(detail.returnDetailUseQty), sum(detail.returnDetailQty), sum(detail.returnDetailPresentQty), sum(detail.returnDetailPresentQty/item.itemWholesaleRate), ");
		sb.append("sum(detail.returnDetailCost * detail.returnDetailPresentQty), sum(detail.returnDetailPrice * detail.returnDetailPresentQty) ");
		sb.append("from WholesaleReturn as w inner join w.wholesaleReturnDetails as detail, PosItem as item ");
		sb.append("where detail.itemNum = item.itemNum and w.systemBookCode = :systemBookCode and w.state.stateCode = 3 ");

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
				sb.append("and w.wholesaleReturnAuditTime >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnAuditTime <= :dateTo ");
			}

		} else {
			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleReturnDate >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnDate <= :dateTo ");
			}
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			sb.append("and w.clientFid in (:clients) ");
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			sb.append("and item.itemCategoryCode in (:categoryCodes) ");
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			sb.append("and w.wholesaleReturnAuditor = :auditor ");
		}
		if(wholesaleProfitQuery.getSellers() != null && wholesaleProfitQuery.getSellers().size() > 0){
			sb.append("and w.wholesaleReturnSeller in " + AppUtil.getStringParmeList(wholesaleProfitQuery.getSellers()));
		}
		sb.append("group by w.clientFid");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", wholesaleProfitQuery.getSystemBookCode());

		if(wholesaleProfitQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom()));
		}
		if(wholesaleProfitQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo()));
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			query.setParameterList("itemNums", wholesaleProfitQuery.getPosItemNums());
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			query.setParameterList("clients", wholesaleProfitQuery.getClientFids());
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			query.setParameterList("categoryCodes", wholesaleProfitQuery.getCategorys());
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			query.setParameterList("regionNums", wholesaleProfitQuery.getRegionNums(), StandardBasicTypes.INTEGER);
		}
		if(wholesaleProfitQuery.getBanchNum() != null){
			query.setInteger("branchNum", wholesaleProfitQuery.getBanchNum());
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
	public List<Object[]> findMoneyGroupByItemMatrix(WholesaleProfitQuery wholesaleProfitQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.itemNum, detail.returnDetailItemMatrixNum, sum(detail.returnDetailQty), sum(detail.returnDetailMoney), ");
		sb.append("sum(detail.returnDetailCost * detail.returnDetailQty), sum(detail.returnDetailQty * item.itemRegularPrice) ");
		sb.append(", sum(detail.returnDetailUseQty), sum(detail.returnDetailPresentQty), sum(detail.returnDetailPresentUseQty), ");
		sb.append("sum(detail.returnDetailCost * detail.returnDetailPresentQty), sum(detail.returnDetailPrice * detail.returnDetailPresentQty) ");
		sb.append("from WholesaleReturn as w inner join w.wholesaleReturnDetails as detail, PosItem as item ");
		sb.append("where detail.itemNum = item.itemNum and w.systemBookCode = :systemBookCode and w.state.stateCode = 3 ");

		if(wholesaleProfitQuery.getBanchNum() != null){
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
				sb.append("and w.wholesaleReturnAuditTime >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnAuditTime <= :dateTo ");
			}

		} else {
			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleReturnDate >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnDate <= :dateTo ");
			}
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			sb.append("and w.clientFid in (:clients) ");
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			sb.append("and item.itemCategoryCode in (:categoryCodes) ");
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			sb.append("and w.wholesaleReturnAuditor = :auditor ");
		}
		if(wholesaleProfitQuery.getSellers() != null && wholesaleProfitQuery.getSellers().size() > 0) {
			sb.append("and w.wholesaleReturnSeller in " + AppUtil.getStringParmeList(wholesaleProfitQuery.getSellers()));
		}
		sb.append("group by detail.itemNum, detail.returnDetailItemMatrixNum");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", wholesaleProfitQuery.getSystemBookCode());
		if(wholesaleProfitQuery.getBanchNum() != null){
			query.setInteger("branchNum", wholesaleProfitQuery.getBanchNum());

		}
		if(wholesaleProfitQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom()));
		}
		if(wholesaleProfitQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo()));
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			query.setParameterList("itemNums", wholesaleProfitQuery.getPosItemNums());
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			query.setParameterList("clients", wholesaleProfitQuery.getClientFids());
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			query.setParameterList("categoryCodes", wholesaleProfitQuery.getCategorys());
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			query.setParameterList("regionNums", wholesaleProfitQuery.getRegionNums());
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			query.setInteger("storehouseNum", wholesaleProfitQuery.getStorehouseNum());
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			query.setString("auditor", wholesaleProfitQuery.getAuditor());
		}

		return query.list();
	}


	@Override
	public List<Object[]> findDetail(WholesaleProfitQuery wholesaleProfitQuery) {
		Criteria criteria = createWholesaleProfitQuery(wholesaleProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("w.wholesaleReturnFid"))
				.add(Projections.property("w.wholesaleReturnAuditTime"))
				.add(Projections.property("w.wholesaleReturnSeller"))
				.add(Projections.property("w.wholesaleReturnCreator"))
				.add(Projections.property("w.wholesaleReturnAuditor"))
				.add(Projections.property("w.clientFid"))
				.add(Projections.property("detail.returnDetailItemCode"))
				.add(Projections.property("detail.returnDetailItemName"))
				.add(Projections.property("detail.returnDetailItemSpec"))
				.add(Projections.property("detail.returnDetailUseUnit"))
				.add(Projections.property("detail.returnDetailUseQty"))
				.add(Projections.property("detail.returnDetailUsePrice"))
				.add(Projections.property("detail.returnDetailMoney"))
				.add(Projections.property("detail.returnDetailQty"))
				.add(Projections.property("detail.returnDetailCost"))
				.add(Projections.property("detail.returnDetailMemo"))
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("detail.returnDetailItemMatrixNum"))
				.add(Projections.property("detail.returnDetailPresentQty"))
				.add(Projections.property("detail.returnDetailCost"))
				.add(Projections.property("detail.returnDetailPrice"))
				.add(Projections.property("detail.returnDetailPresentUseQty"))
				.add(Projections.property("detail.returnDetailPresentUnit"))
				.add(Projections.property("detail.returnDetailProductingDate"))

		);
		return criteria.list();
	}



	@Override
	public Object[] readProfitSummary(WholesaleProfitQuery wholesaleProfitQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(detail.returnDetailMoney), sum(detail.returnDetailCost * (detail.returnDetailQty + detail.returnDetailPresentQty)), sum((detail.returnDetailQty + detail.returnDetailPresentQty) * item.itemRegularPrice), sum((detail.returnDetailQty + detail.returnDetailPresentQty)/item.itemWholesaleRate) ");
		sb.append("from WholesaleReturn as w inner join w.wholesaleReturnDetails as detail, PosItem as item ");
		sb.append("where detail.itemNum = item.itemNum and w.systemBookCode = :systemBookCode and w.state.stateCode = 3 ");
		if(wholesaleProfitQuery.getBanchNum() != null){
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
				sb.append("and w.wholesaleReturnAuditTime >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnAuditTime <= :dateTo ");
			}

		} else {
			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleReturnDate >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnDate <= :dateTo ");
			}
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			sb.append("and w.clientFid in (:clients) ");
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			sb.append("and item.itemCategoryCode in (:categoryCodes) ");
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			sb.append("and w.wholesaleReturnAuditor = :auditor ");
		}
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", wholesaleProfitQuery.getSystemBookCode());
		if(wholesaleProfitQuery.getBanchNum() != null){
			query.setInteger("branchNum", wholesaleProfitQuery.getBanchNum());

		}
		if(wholesaleProfitQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom()));
		}
		if(wholesaleProfitQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo()));
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			query.setParameterList("itemNums", wholesaleProfitQuery.getPosItemNums());
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			query.setParameterList("clients", wholesaleProfitQuery.getClientFids());
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			query.setParameterList("categoryCodes", wholesaleProfitQuery.getCategorys());
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			query.setParameterList("regionNums", wholesaleProfitQuery.getRegionNums());
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




	@Override
	public List<Object[]> findMoneyGroupByItemNum(WholesaleProfitQuery wholesaleProfitQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.itemNum, sum(detail.returnDetailQty), sum(detail.returnDetailMoney), sum(detail.returnDetailCost * detail.returnDetailQty), ");
		sb.append("sum(detail.returnDetailQty * item.itemRegularPrice), sum(detail.returnDetailUseQty), ");
		sb.append("sum(detail.returnDetailPresentQty), sum(detail.returnDetailPresentUseQty), sum(detail.returnDetailCost * detail.returnDetailPresentQty), ");
		sb.append("sum(detail.returnDetailPrice * detail.returnDetailPresentQty) ");
		sb.append("from WholesaleReturn as w inner join w.wholesaleReturnDetails as detail, PosItem as item ");
		sb.append("where detail.itemNum = item.itemNum and w.systemBookCode = :systemBookCode and w.state.stateCode = 3 ");

		if(wholesaleProfitQuery.getBanchNum() != null){
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
				sb.append("and w.wholesaleReturnAuditTime >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnAuditTime <= :dateTo ");
			}

		} else {
			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleReturnDate >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnDate <= :dateTo ");
			}
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			sb.append("and w.clientFid in (:clients) ");
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			sb.append("and item.itemCategoryCode in (:categoryCodes) ");
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			sb.append("and w.wholesaleReturnAuditor = :auditor ");
		}
		if(wholesaleProfitQuery.getSellers() != null && wholesaleProfitQuery.getSellers().size() > 0) {
			sb.append("and w.wholesaleReturnSeller in " + AppUtil.getStringParmeList(wholesaleProfitQuery.getSellers()));
		}
		sb.append("group by detail.itemNum");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", wholesaleProfitQuery.getSystemBookCode());
		if(wholesaleProfitQuery.getBanchNum() != null){
			query.setInteger("branchNum", wholesaleProfitQuery.getBanchNum());

		}
		if(wholesaleProfitQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom()));
		}
		if(wholesaleProfitQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo()));
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			query.setParameterList("itemNums", wholesaleProfitQuery.getPosItemNums());
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			query.setParameterList("clients", wholesaleProfitQuery.getClientFids());
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			query.setParameterList("categoryCodes", wholesaleProfitQuery.getCategorys());
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			query.setParameterList("regionNums", wholesaleProfitQuery.getRegionNums());
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
	public List<Object[]> findMoneyGroupByClientItemNum(WholesaleProfitQuery wholesaleProfitQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select w.clientFid, detail.itemNum, detail.returnDetailItemMatrixNum, sum(detail.returnDetailQty), sum(detail.returnDetailMoney), ");
		sb.append("sum(detail.returnDetailCost * detail.returnDetailQty), sum(detail.returnDetailQty * item.itemRegularPrice),");
		sb.append("sum(detail.returnDetailUseQty), sum(detail.returnDetailPresentQty), sum(detail.returnDetailPresentUseQty), ");
		sb.append("sum(detail.returnDetailCost * detail.returnDetailPresentQty), sum(detail.returnDetailPrice * detail.returnDetailPresentQty) ");
		sb.append("from WholesaleReturn as w inner join w.wholesaleReturnDetails as detail, PosItem as item ");
		sb.append("where detail.itemNum = item.itemNum and w.systemBookCode = :systemBookCode and w.state.stateCode = 3 ");
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
				sb.append("and w.wholesaleReturnAuditTime >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnAuditTime <= :dateTo ");
			}

		} else {
			if(wholesaleProfitQuery.getDateFrom() != null){
				sb.append("and w.wholesaleReturnDate >= :dateFrom ");
			}
			if(wholesaleProfitQuery.getDateTo() != null){
				sb.append("and w.wholesaleReturnDate <= :dateTo ");
			}
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			sb.append("and w.clientFid in (:clients) ");
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			sb.append("and item.itemCategoryCode in (:categoryCodes) ");
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(wholesaleProfitQuery.getStorehouseNum() != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(wholesaleProfitQuery.getAuditor())){
			sb.append("and w.wholesaleReturnAuditor = :auditor ");
		}
		if(wholesaleProfitQuery.getSellers() != null && wholesaleProfitQuery.getSellers().size() > 0) {
			sb.append("and w.wholesaleReturnSeller in " + AppUtil.getStringParmeList(wholesaleProfitQuery.getSellers()));
		}
		sb.append("group by w.clientFid, detail.itemNum, detail.returnDetailItemMatrixNum ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", wholesaleProfitQuery.getSystemBookCode());

		if(wholesaleProfitQuery.getDateFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(wholesaleProfitQuery.getDateFrom()));
		}
		if(wholesaleProfitQuery.getDateTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(wholesaleProfitQuery.getDateTo()));
		}
		if(wholesaleProfitQuery.getPosItemNums() != null && wholesaleProfitQuery.getPosItemNums().size() > 0){
			query.setParameterList("itemNums", wholesaleProfitQuery.getPosItemNums());
		}
		if(wholesaleProfitQuery.getClientFids() != null && wholesaleProfitQuery.getClientFids().size() > 0){
			query.setParameterList("clients", wholesaleProfitQuery.getClientFids());
		}
		if(wholesaleProfitQuery.getCategorys() != null && wholesaleProfitQuery.getCategorys().size() > 0){
			query.setParameterList("categoryCodes", wholesaleProfitQuery.getCategorys());
		}
		if(wholesaleProfitQuery.getRegionNums() != null && wholesaleProfitQuery.getRegionNums().size() > 0){
			query.setParameterList("regionNums", wholesaleProfitQuery.getRegionNums(), StandardBasicTypes.INTEGER);
		}
		if(wholesaleProfitQuery.getBanchNum() != null){
			query.setInteger("branchNum", wholesaleProfitQuery.getBanchNum());
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
	public List<Object[]> findMoneyGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> itemCategories,
												 List<Integer> itemNums, List<String> clients, List<Integer> regionNums, Integer storehouseNum, String auditor, String dateType, List<String> sellers) {
		StringBuffer sb = new StringBuffer();
		sb.append("select w.branchNum, sum(detail.returnDetailMoney), sum(detail.returnDetailCost * detail.returnDetailQty), sum(detail.returnDetailQty * item.itemRegularPrice), ");
		sb.append("sum(detail.returnDetailQty/item.itemWholesaleRate), sum(detail.returnDetailUseQty),sum(detail.returnDetailQty), ");
		sb.append("sum(detail.returnDetailPresentQty), sum(detail.returnDetailPresentUseQty), sum(detail.returnDetailPresentQty * detail.returnDetailPrice), ");
		sb.append("sum(detail.returnDetailPresentQty * detail.returnDetailCost ) ");
		sb.append("from WholesaleReturn as w inner join w.wholesaleReturnDetails as detail, PosItem as item ");
		sb.append("where detail.itemNum = item.itemNum and w.systemBookCode = :systemBookCode and w.state.stateCode = 3 ");

		if(branchNums != null &&branchNums.size() > 0){
			sb.append("and w.branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		}

		if(dateType == null){
			dateType = AppConstants.STATE_AUDIT_TIME;
		}
		if(dateType.equals(AppConstants.STATE_AUDIT_TIME)){
			if(dateFrom != null){
				sb.append("and w.wholesaleReturnAuditTime >= :dateFrom ");
			}
			if(dateTo != null){
				sb.append("and w.wholesaleReturnAuditTime <= :dateTo ");
			}
		} else {
			if(dateFrom != null){
				sb.append("and w.wholesaleReturnDate >= :dateFrom ");
			}
			if(dateTo != null){
				sb.append("and w.wholesaleReturnDate <= :dateTo ");
			}
		}

		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.itemNum in (:itemNums) ");
		}
		if(clients != null && clients.size() > 0){
			sb.append("and w.clientFid in (:clients) ");
		}
		if(itemCategories != null && itemCategories.size() > 0){
			sb.append("and item.itemCategoryCode in (:categoryCodes) ");
		}
		if(regionNums != null && regionNums.size() > 0){
			sb.append("and w.regionNum in (:regionNums) ");
		}
		if(storehouseNum != null){
			sb.append("and w.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(auditor)){
			sb.append("and w.wholesaleReturnAuditor = :auditor ");
		}
		if(sellers != null && sellers.size() > 0){
			sb.append("and w.wholesaleReturnSeller in " + AppUtil.getStringParmeList(sellers));
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
		if(clients != null && clients.size() > 0){
			query.setParameterList("clients", clients, StandardBasicTypes.STRING);
		}
		if(itemCategories != null && itemCategories.size() > 0){
			query.setParameterList("categoryCodes", itemCategories, StandardBasicTypes.STRING);
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
	public List<Object[]> findDueMoney(String systemBookCode,
									   Integer branchNum, List<String> clientFids, Date dateFrom,
									   Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select clientFid, sum(wholesaleReturnDueMoney - wholesaleReturnDiscountMoney - wholesaleReturnPaidMoney), ");
		sb.append("sum(wholesaleReturnDueMoney) ");
		sb.append("from WholesaleReturn where systemBookCode = :systemBookCode and state.stateCode = 3 ");
		if(branchNum != null){
			sb.append("and branchNum = :branchNum  ");
		}
		if(dateFrom != null){
			sb.append("and wholesaleReturnPaymentDate >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and wholesaleReturnPaymentDate <= :dateTo ");
		}
		if(clientFids != null && clientFids.size() > 0){
			sb.append("and clientFid in (:clientFids) ");
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
			query.setParameterList("clientFids", clientFids);
		}
		query.setLockOptions(LockOptions.READ);

		return query.list();
	}

	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(WholesaleReturn.class, "w")
				.add(Restrictions.eq("w.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("w.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("w.wholesaleReturnCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("w.wholesaleReturnCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

}
