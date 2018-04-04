package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.TransferOutOrderDao;
import com.nhsoft.module.report.model.OutOrderDetail;
import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.TransferInOrder;
import com.nhsoft.module.report.model.TransferOutOrder;
import com.nhsoft.module.report.queryBuilder.TransferProfitQuery;
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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Repository
public class TransferOutOrderDaoImpl extends DaoImpl implements TransferOutOrderDao {

	@Override
	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select convert(varchar(12) , out_order_audit_time, 112), sum(out_order_total_money) as totalMoney from transfer_out_order with(nolock) where system_book_code = '" + systemBookCode
				+ "' and out_order_state_code = 3 ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (centerBranchNums != null && centerBranchNums.size() > 0) {
			sb.append("and out_branch_num in " + AppUtil.getIntegerParmeList(centerBranchNums));
		}
		if (dateFrom != null) {
			sb.append("and out_order_audit_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if (dateTo != null) {
			sb.append("and out_order_audit_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		sb.append("group by convert(varchar(12) , out_order_audit_time, 112)");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findUnTransferedItems(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, List<Integer> storehouseNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num from out_order_detail as d with(nolock)  ");
		sb.append("where d,item_num = i.item_num and exists(select 1 from transfer_out_order as t with(nolock)  ");
		sb.append("where t.system_book_code = '" + systemBookCode + "' ");
		sb.append("and t.out_branch_num = " + outBranchNum + " ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and t.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and t.out_order_state_code = 3) group by branch_num ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
	@Override
	public BigDecimal findBalance(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dtFrom, Date dtTo) {
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t").add(Restrictions.eq("t.systemBookCode", systemBookCode)).add(Restrictions.eq("t.branchNum", branchNum))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if (centerBranchNum != null) {
			criteria.add(Restrictions.eq("t.outBranchNum", centerBranchNum));
		}
		if (dtFrom != null) {
			criteria.add(Restrictions.ge("t.outOrderAuditTime", DateUtil.getMinOfDate(dtFrom)));
		}
		if (dtTo != null) {
			criteria.add(Restrictions.le("t.outOrderAuditTime", DateUtil.getMaxOfDate(dtTo)));
		}
		criteria.setProjection(Projections.sqlProjection("sum(out_order_due_money - out_order_discount_money - out_order_paid_money) as balance", new String[] { "balance" },
				new Type[] { StandardBasicTypes.BIG_DECIMAL }));
		List<Object[]> objects = criteria.list();
		if (objects != null && objects.size() > 0) {
			Object object = objects.get(0);
			return object == null ? BigDecimal.ZERO : (BigDecimal) object;
		}
		return BigDecimal.ZERO;
	}

	private Criteria createProfitCriteria(TransferProfitQuery transferProfitQuery) {
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t").createAlias("t.outOrderDetails", "detail")
				.add(Restrictions.eq("t.systemBookCode", transferProfitQuery.getSystemBookCode())).add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if (transferProfitQuery.getDistributionBranchNums() != null && transferProfitQuery.getDistributionBranchNums().size() > 0) {
			criteria.add(Restrictions.in("t.outBranchNum", transferProfitQuery.getDistributionBranchNums()));
		}
		if (transferProfitQuery.getResponseBranchNums() != null && transferProfitQuery.getResponseBranchNums().size() > 0) {
			criteria.add(Restrictions.sqlRestriction("this_.branch_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getResponseBranchNums())));
		}
		if (transferProfitQuery.getDtFrom() != null) {
			criteria.add(Restrictions.ge("t.outOrderAuditTime", DateUtil.getMinOfDate(transferProfitQuery.getDtFrom())));
		}
		if (transferProfitQuery.getDtTo() != null) {
			criteria.add(Restrictions.le("t.outOrderAuditTime", DateUtil.getMaxOfDate(transferProfitQuery.getDtTo())));
		}
		if (transferProfitQuery.getItemNums() != null && transferProfitQuery.getItemNums().size() > 0) {
			criteria.add(Restrictions.sqlRestriction("item_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getItemNums())));
		}
		if ((transferProfitQuery.getCategoryCodes() != null && transferProfitQuery.getCategoryCodes().size() > 0) || (StringUtils.isNotEmpty(transferProfitQuery.getItemBrand()))
				|| (transferProfitQuery.getItemDepartments() != null && transferProfitQuery.getItemDepartments().size() > 0)) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")

					.add(Restrictions.eq("item.systemBookCode", transferProfitQuery.getSystemBookCode())).add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			if (transferProfitQuery.getCategoryCodes() != null && transferProfitQuery.getCategoryCodes().size() > 0) {
				subCriteria.add(Restrictions.in("item.itemCategoryCode", transferProfitQuery.getCategoryCodes()));
			}
			if (transferProfitQuery.getItemDepartments() != null && transferProfitQuery.getItemDepartments().size() > 0) {
				subCriteria.add(Restrictions.in("item.itemDepartment", transferProfitQuery.getItemDepartments()));
			}
			if (StringUtils.isNotEmpty(transferProfitQuery.getItemBrand())) {
				subCriteria.add(Restrictions.eq("item.itemBrand", transferProfitQuery.getItemBrand()));

			}
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if (transferProfitQuery.getStorehouseNum() != null) {
			criteria.add(Restrictions.eq("t.storehouseNum", transferProfitQuery.getStorehouseNum()));
		}
		return criteria;
	}


	@Override
	public List<Object[]> findProfitGroupByOutBranchAndItem(TransferProfitQuery transferProfitQuery) {
		Criteria criteria = createProfitCriteria(transferProfitQuery);

		if (transferProfitQuery.isFilterPolicyItems()) {
			criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("t.outBranchNum")).add(Projections.groupProperty("detail.itemNum"))
					.add(Projections.groupProperty("detail.outOrderDetailItemMatrixNum"))
					.add(Projections.sqlProjection(
							"sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_qty - out_order_detail_policy_use_qty * out_order_detail_use_rate) else out_order_detail_qty end) as qty, "
									+ "sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_qty - out_order_detail_policy_use_qty * out_order_detail_use_rate) *out_order_detail_cost else out_order_detail_subtotal end) as subtotal, "
									+ "sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_use_qty - out_order_detail_policy_use_qty) * out_order_detail_use_price else out_order_detail_sale_subtotal end) as saleSubtotal, "
									+ "sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_qty - out_order_detail_policy_use_qty * out_order_detail_use_rate) * out_order_detail_sale_price else out_order_detail_sale_price * out_order_detail_qty end) as saleMoney, "
									+ "sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_use_qty - out_order_detail_policy_use_qty) else out_order_detail_use_qty end) as useQty, "
									+ "sum(out_order_detail_present_basic_qty) as presentQty, "
									+ "sum(out_order_detail_present_use_qty) as presentUseQty, " + "sum(out_order_detail_present_basic_qty * out_order_detail_price) as presentTransferMoney, "
									+ "sum(out_order_detail_present_basic_qty * out_order_detail_cost) as presentCostMoney, "
									+ "sum(out_order_detail_use_qty * out_order_detail_tare) as tare ",
							new String[] { "qty", "subtotal", "saleSubtotal", "saleMoney", "useQty", "presentQty", "presentUseQty", "presentTransferMoney", "presentCostMoney", "tare" },
							new Type[] { StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL,
									StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL })));
		} else {

			criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("t.outBranchNum")).add(Projections.groupProperty("detail.itemNum"))
					.add(Projections.groupProperty("detail.outOrderDetailItemMatrixNum")).add(Projections.sum("detail.outOrderDetailQty")).add(Projections.sum("detail.outOrderDetailSubtotal"))
					.add(Projections.sum("detail.outOrderDetailSaleSubtotal"))
					.add(Projections.sqlProjection("sum(out_order_detail_sale_price * out_order_detail_qty) as saleMoney", new String[] { "saleMoney" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
					.add(Projections.sum("detail.outOrderDetailUseQty")).add(Projections.sum("detail.outOrderDetailPresentBasicQty")).add(Projections.sum("detail.outOrderDetailPresentUseQty"))
					.add(Projections.sqlProjection(
							"sum(out_order_detail_present_basic_qty * out_order_detail_price) as presentTransferMoney, "
									+ "sum(out_order_detail_present_basic_qty * out_order_detail_cost) as presentCostMoney, sum(out_order_detail_use_qty * out_order_detail_tare) as tare  ",
							new String[] { "presentTransferMoney", "presentCostMoney", "tare" },
							new Type[] { StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL })));
		}

		return criteria.list();
	}



	@Override
	public List<Object[]> findProfitGroupByOutBranch(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList,
													 List<Integer> itemNums, Integer storehouseNum) {
		TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
		transferProfitQuery.setSystemBookCode(systemBookCode);
		transferProfitQuery.setDistributionBranchNums(transferBranchNums);
		transferProfitQuery.setResponseBranchNums(branchNums);
		transferProfitQuery.setDtFrom(dateFrom);
		transferProfitQuery.setDtTo(dateTo);
		transferProfitQuery.setCategoryCodes(categoryCodeList);
		transferProfitQuery.setItemNums(itemNums);
		transferProfitQuery.setStorehouseNum(storehouseNum);
		Criteria criteria = createProfitCriteria(transferProfitQuery);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("t.outBranchNum")).add(Projections.sum("detail.outOrderDetailQty"))
				.add(Projections.sum("detail.outOrderDetailSubtotal")).add(Projections.sum("detail.outOrderDetailSaleSubtotal"))
				.add(Projections.sqlProjection("sum(out_order_detail_sale_price * out_order_detail_qty) as saleMoney", new String[] { "saleMoney" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
				.add(Projections.sum("detail.outOrderDetailUseQty")));
		return criteria.list();
	}


	@Override
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList,
												List<Integer> itemNums) {
		TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
		transferProfitQuery.setSystemBookCode(systemBookCode);
		transferProfitQuery.setDistributionBranchNums(transferBranchNums);
		transferProfitQuery.setResponseBranchNums(branchNums);
		transferProfitQuery.setDtFrom(dateFrom);
		transferProfitQuery.setDtTo(dateTo);
		transferProfitQuery.setCategoryCodes(categoryCodeList);
		transferProfitQuery.setItemNums(itemNums);
		Criteria criteria = createProfitCriteria(transferProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.outOrderDetailItemMatrixNum"))
				.add(Projections.sum("detail.outOrderDetailQty"))
				.add(Projections.sum("detail.outOrderDetailSaleSubtotal"))
				.add(Projections.sum("detail.outOrderDetailSubtotal"))
				.add(Projections.sum("detail.outOrderDetailPresentBasicQty")));
		return criteria.list();
	}


	@Override
	public List<Object[]> findDetails(TransferProfitQuery transferProfitQuery) {

		Criteria criteria = createProfitCriteria(transferProfitQuery);
		if (transferProfitQuery.isFilterPolicyItems()) {

			criteria.setProjection(Projections.projectionList().add(Projections.property("t.outOrderFid")).add(Projections.property("t.outOrderAuditTime"))
					.add(Projections.property("t.outOrderOperator")).add(Projections.property("t.outOrderCreator")).add(Projections.property("t.outOrderAuditor"))
					.add(Projections.property("t.branchNum")).add(Projections.property("detail.outOrderDetailItemCode")).add(Projections.property("detail.outOrderDetailItemName"))
					.add(Projections.property("detail.outOrderDetailItemSpec")).add(Projections.property("detail.outOrderDetailUseUnit"))
					.add(Projections.sqlProjection(
							"(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_use_qty - out_order_detail_policy_use_qty) else out_order_detail_use_qty end) as useQty",
							new String[] { "useQty" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
					.add(Projections.property("detail.outOrderDetailUsePrice"))
					.add(Projections.sqlProjection(
							"(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_use_qty - out_order_detail_policy_use_qty) * out_order_detail_use_price else out_order_detail_sale_subtotal end) as saleSubtotal",
							new String[] { "saleSubtotal" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
					.add(Projections.sqlProjection(
							"(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_qty - out_order_detail_policy_use_qty * out_order_detail_use_rate) *out_order_detail_cost else out_order_detail_subtotal end) as subtotal",
							new String[] { "subtotal" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
					.add(Projections.property("detail.outOrderDetailMemo")).add(Projections.property("t.outBranchNum")).add(Projections.property("detail.outOrderDetailItemUnit"))
					.add(Projections.property("t.outOrderPickingTime"))
					.add(Projections.sqlProjection(
							"(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_qty - out_order_detail_policy_use_qty * out_order_detail_use_rate) else out_order_detail_qty end) as qty",
							new String[] { "qty" }, new Type[] { StandardBasicTypes.BIG_DECIMAL }))
					.add(Projections.property("detail.outOrderDetailItemMatrixNum")).add(Projections.property("detail.itemNum")).add(Projections.property("detail.outOrderDetailLotNumber"))
					.add(Projections.property("detail.outOrderDetailPresentUseUnit")).add(Projections.property("detail.outOrderDetailPresentBasicQty"))
					.add(Projections.property("detail.outOrderDetailPresentUseQty"))
					.add(Projections.sqlProjection(
							"(out_order_detail_present_basic_qty * out_order_detail_price) as presendTransferMoney,"
									+ "(out_order_detail_present_basic_qty * out_order_detail_cost) as presendCostMoney",
							new String[] { "presendTransferMoney", "presendCostMoney" }, new Type[] { StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL }))
					.add(Projections.property("detail.outOrderDetailProducingDate"))

			);

		} else {

			criteria.setProjection(Projections.projectionList().add(Projections.property("t.outOrderFid")).add(Projections.property("t.outOrderAuditTime"))
					.add(Projections.property("t.outOrderOperator")).add(Projections.property("t.outOrderCreator")).add(Projections.property("t.outOrderAuditor"))
					.add(Projections.property("t.branchNum")).add(Projections.property("detail.outOrderDetailItemCode")).add(Projections.property("detail.outOrderDetailItemName"))
					.add(Projections.property("detail.outOrderDetailItemSpec")).add(Projections.property("detail.outOrderDetailUseUnit")).add(Projections.property("detail.outOrderDetailUseQty"))
					.add(Projections.property("detail.outOrderDetailUsePrice")).add(Projections.property("detail.outOrderDetailSaleSubtotal"))
					.add(Projections.property("detail.outOrderDetailSubtotal")).add(Projections.property("detail.outOrderDetailMemo")).add(Projections.property("t.outBranchNum"))
					.add(Projections.property("detail.outOrderDetailItemUnit")).add(Projections.property("t.outOrderPickingTime")).add(Projections.property("detail.outOrderDetailQty"))
					.add(Projections.property("detail.outOrderDetailItemMatrixNum")).add(Projections.property("detail.itemNum")).add(Projections.property("detail.outOrderDetailLotNumber"))
					.add(Projections.property("detail.outOrderDetailPresentUseUnit")).add(Projections.property("detail.outOrderDetailPresentBasicQty"))
					.add(Projections.property("detail.outOrderDetailPresentUseQty"))
					.add(Projections.sqlProjection(
							"(out_order_detail_present_basic_qty * out_order_detail_price) as presendTransferMoney,"
									+ "(out_order_detail_present_basic_qty * out_order_detail_cost) as presendCostMoney",
							new String[] { "presendTransferMoney", "presendCostMoney" }, new Type[] { StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL }))
					.add(Projections.property("detail.outOrderDetailProducingDate"))

			);
		}
		List<Object[]> objects = criteria.list();
		criteria.setMaxResults(10000);
		return objects;
	}




	@Override
	public List<OutOrderDetail> findDetails(List<String> outOrderFids) {
		String sql = "select detail.* from out_order_detail as detail with(nolock) where detail.out_order_fid in " + AppUtil.getStringParmeList(outOrderFids);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity("detail", OutOrderDetail.class);
		return query.list();
	}



	@Override
	public List<Object[]> findMoneyGroupByItemDate(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
		transferProfitQuery.setSystemBookCode(systemBookCode);
		transferProfitQuery.setDistributionBranchNums(transferBranchNums);
		transferProfitQuery.setResponseBranchNums(branchNums);
		transferProfitQuery.setDtFrom(dateFrom);
		transferProfitQuery.setDtTo(dateTo);

		Criteria criteria = createProfitCriteria(transferProfitQuery);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sqlGroupProjection("convert(varchar(12),out_order_audit_time,23) as date", "convert(varchar(12),out_order_audit_time,23)", new String[] { "date" },
						new Type[] { StandardBasicTypes.STRING }))
				.add(Projections.sum("detail.outOrderDetailSaleSubtotal")).add(Projections.sum("detail.outOrderDetailSubtotal")).add(Projections.sum("detail.outOrderDetailQty")));
		return criteria.list();
	}


	@Override
	public List<Object[]> findOutMoneyGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t").createAlias("t.outOrderDetails", "detail")
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE)).add(Restrictions.eq("t.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("t.branchNum", branchNums));

		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("t.outOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("t.outOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)) {
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("t.branchNum")).add(Projections.sqlGroupProjection("substring(convert(varchar(12), out_order_audit_time, 23),0, 8) as bizday",
							"substring(convert(varchar(12), out_order_audit_time, 23),0, 8)", new String[] { "bizday" }, new Type[] { StandardBasicTypes.STRING }))
					.add(Projections.sum("detail.outOrderDetailSaleSubtotal")));
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_DATE)) {

			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("t.branchNum")).add(Projections.sqlGroupProjection("substring(convert(varchar(12), out_order_audit_time, 23),0, 11) as bizday",
							"substring(convert(varchar(12), out_order_audit_time, 23),0, 11)", new String[] { "bizday" }, new Type[] { StandardBasicTypes.STRING }))
					.add(Projections.sum("detail.outOrderDetailSaleSubtotal")));
		} else {

			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("t.branchNum")).add(Projections.sqlGroupProjection("substring(convert(varchar(12), out_order_audit_time, 23),0, 5) as bizday",
							"substring(convert(varchar(12), out_order_audit_time, 23),0, 5)", new String[] { "bizday" }, new Type[] { StandardBasicTypes.STRING }))
					.add(Projections.sum("detail.outOrderDetailSaleSubtotal")));
		}
		List<Object[]> objects = criteria.list();
		return objects;
	}


	@Override
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, List<Integer> transferBranchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
		transferProfitQuery.setSystemBookCode(systemBookCode);
		transferProfitQuery.setDistributionBranchNums(transferBranchNums);
		transferProfitQuery.setDtFrom(dateFrom);
		transferProfitQuery.setDtTo(dateTo);
		transferProfitQuery.setItemNums(itemNums);
		Criteria criteria = createProfitCriteria(transferProfitQuery);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sqlGroupProjection("substring(convert(varchar(12), out_order_audit_time, 23),0, 8) as date", "substring(convert(varchar(12), out_order_audit_time, 23),0, 8)",
						new String[] { "date" }, new Type[] { StandardBasicTypes.STRING }))
				.add(Projections.sum("detail.outOrderDetailSaleSubtotal")).add(Projections.sum("detail.outOrderDetailSubtotal")));
		return criteria.list();
	}


	@Override
	public List<TransferOutOrder> findToPicking(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Integer storehouseNum) {
		Date initDate = DateUtil.getDateTimeHMS(AppConstants.INIT_TIME);
		Date dateTo = Calendar.getInstance().getTime();
		Date dateFrom = DateUtil.addDay(dateTo, -30);
		DetachedCriteria subCriteria = DetachedCriteria.forClass(TransferInOrder.class, "i").add(Property.forName("i.outOrderFid").eqProperty("t.outOrderFid"))
				.add(Restrictions.isNotNull("i.outOrderFid"));

		// 审核且配货时间、发货时间都为null、未被调入的单据
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t")
				.add(Restrictions.between("t.outOrderAuditTime", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo))).add(Restrictions.eq("t.outBranchNum", centerBranchNum))
				.add(Restrictions.isNull("t.outOrderPickingTime")).add(Restrictions.disjunction().add(Restrictions.isNull("t.outOrderSendTime")).add(Restrictions.eq("t.outOrderSendTime", initDate)))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE)).add(Subqueries.notExists(subCriteria.setProjection(Projections.property("i.outOrderFid"))))
				.add(Restrictions.eq("t.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("t.branchNum", branchNums));
		}
		if (storehouseNum != null) {
			criteria.add(Restrictions.eq("t.storehouseNum", storehouseNum));
		}
		return criteria.list();
	}


	@Override
	public List<TransferOutOrder> findToShip(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Integer storehouseNum) {
		Date initDate = DateUtil.getDateTimeHMS(AppConstants.INIT_TIME);
		Date dateTo = Calendar.getInstance().getTime();
		Date dateFrom = DateUtil.addDay(dateTo, -30);
		DetachedCriteria subCriteria = DetachedCriteria.forClass(TransferInOrder.class, "i").add(Property.forName("i.outOrderFid").eqProperty("t.outOrderFid"))
				.add(Restrictions.isNotNull("i.outOrderFid"));

		// 审核且配货时间不为null、发货时间为null、未被调入的单据
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t")
				.add(Restrictions.between("t.outOrderAuditTime", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo))).add(Restrictions.eq("t.outBranchNum", centerBranchNum))
				.add(Restrictions.isNotNull("t.outOrderPickingTime"))
				.add(Restrictions.disjunction().add(Restrictions.isNull("t.outOrderSendTime")).add(Restrictions.eq("t.outOrderSendTime", initDate)))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE)).add(Subqueries.notExists(subCriteria.setProjection(Projections.property("i.outOrderFid"))))
				.add(Restrictions.eq("t.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("t.branchNum", branchNums));
		}
		if (storehouseNum != null) {
			criteria.add(Restrictions.eq("t.storehouseNum", storehouseNum));
		}
		return criteria.list();
	}




	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t").add(Restrictions.eq("t.systemBookCode", systemBookCode));
		if (branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			criteria.add(Restrictions.eq("t.outBranchNum", branchNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("t.outOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("t.outOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}



	@Override
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(detail.out_order_detail_qty) as qty, sum(detail.out_order_detail_sale_subtotal) as money, ");
		sb.append("sum(detail.out_order_detail_use_qty) as useQty ");
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as r with(nolock) on detail.out_order_fid = r.out_order_fid ");
		sb.append("where r.system_book_code = :systemBookCode ");
		if (outBranchNums != null && outBranchNums.size() > 0) {
			sb.append("and r.out_branch_num in " + AppUtil.getIntegerParmeList(outBranchNums));
		}
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and r.out_order_audit_time >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and r.out_order_audit_time <= :dateTo ");
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and r.out_order_state_code = 3 ");
		sb.append("group by detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);

		if (dateFrom != null) {
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if (dateTo != null) {
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		return query.list();
	}



	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num , sum(out_order_total_money) as totalMoney, sum(out_order_other_fee) as feeMoney  ");
		sb.append("from transfer_out_order with(nolock) where system_book_code = :systemBookCode and out_branch_num = " + centerBranchNum + " ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and out_order_audit_time between :dateFrom and :dateTo and out_order_state_code = 3 ");
		sb.append("group by branch_num ");
		String sql = sb.toString();
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}



	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.branch_num, detail.item_num, sum(detail.out_order_detail_qty) as qty, sum(detail.out_order_detail_sale_subtotal) as money, ");
		sb.append("sum(detail.out_order_detail_use_qty) as useQty, sum(detail.out_order_detail_other_fee) as otherFee ");
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as r with(nolock) on detail.out_order_fid = r.out_order_fid ");
		sb.append("where r.system_book_code = :systemBookCode ");
		if (centerBranchNum != null) {
			sb.append("and r.out_branch_num = :outBranchNum ");
		}
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and r.out_order_audit_time >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and r.out_order_audit_time <= :dateTo ");
		}
		if(itemNums != null && itemNums.size() > 0) {
			sb.append("detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and r.out_order_state_code = 3 ");
		sb.append("group by r.branch_num, detail.item_num ");
		String sql = sb.toString();
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		if (centerBranchNum != null) {
			query.setInteger("outBranchNum", centerBranchNum);
		}
		if (dateFrom != null) {
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if (dateTo != null) {
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		return query.list();
	}



	@Override
	public List<Object[]> findOperatorSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> operators, String operatorType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.operator, count(a.out_order_fid) as orderCount, sum(a.out_order_total_money) as orderMoney, sum(a.itemAmount) as itemAmount, ");
		sb.append("sum(a.useQty) as useQty ");
		sb.append("from ");
		sb.append("(select s.out_order_fid, %s as operator, s.out_order_total_money, count(distinct detail.item_num) as itemAmount, ");
		sb.append("sum(detail.out_order_detail_use_qty) as useQty ");
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as s with(nolock) on detail.out_order_fid = s.out_order_fid ");
		sb.append("where s.system_book_code = :systemBookCode ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and s.out_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and %t between :dateFrom and :dateTo ");
		sb.append("and s.out_order_state_code = 3 ");
		if (operators != null && operators.size() > 0) {
			sb.append("and %s in " + AppUtil.getStringParmeList(operators));
		}
		sb.append("group by s.out_order_fid, %s, s.out_order_total_money) as a group by a.operator ");

		String sql = sb.toString();
		if (operatorType.equals(AppConstants.ORDER_OPERATOR_TYPE_CREATOR)) {
			sql = sql.replaceAll("%s", "s.out_order_creator");
			sql = sql.replaceAll("%t", "s.out_order_create_time");
		} else if (operatorType.equals(AppConstants.ORDER_OPERATOR_TYPE_AUDITOR)) {
			sql = sql.replaceAll("%s", "s.out_order_auditor");
			sql = sql.replaceAll("%t", "s.out_order_audit_time");
		} else if (operatorType.equals(AppConstants.ORDER_OPERATOR_TYPE_PICKER)) {
			sql = sql.replaceAll("%s", "s.out_order_picker");
			sql = sql.replaceAll("%t", "s.OUT_ORDER_PICKING_TIME");
		} else if (operatorType.equals(AppConstants.ORDER_OPERATOR_TYPE_SENDER)) {
			sql = sql.replaceAll("%s", "s.out_order_sender");
			sql = sql.replaceAll("%t", "s.OUT_ORDER_SEND_TIME");
		}
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}



	@Override
	public List<Object[]> findBranchSumByDateType(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType, List<String> categoryCodes,
												  List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		if ((categoryCodes != null && categoryCodes.size() > 0) || (itemNums != null && itemNums.size() > 0)) {

			sb.append("select t.branch_num, %s, sum(detail.out_order_detail_sale_subtotal) as money ");
			sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as t with(nolock) ");
			sb.append("on detail.out_order_fid = t.out_order_fid ");
			sb.append("where t.system_book_code = :systemBookCode and t.out_order_audit_time between :dateFrom and :dateTo ");
			sb.append("and t.out_branch_num = :outBranchNum ");
			sb.append("and t.out_order_state_code = 3 ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and t.branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
			}
			if (categoryCodes != null && categoryCodes.size() > 0) {
				sb.append("and exists (select 1 from pos_item as p with(nolock) where p.item_num = detail.item_num and p.item_category_code in " + AppUtil.getStringParmeList(categoryCodes) + " ) ");

			}
			if (itemNums != null && itemNums.size() > 0) {
				sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
			}
			sb.append("group by t.branch_num, %s");
		} else {
			sb.append("select t.branch_num, %s, sum(t.out_order_total_money) as money ");
			sb.append("from transfer_out_order as t with(nolock) ");
			sb.append("where t.system_book_code = :systemBookCode and t.out_order_audit_time between :dateFrom and :dateTo ");
			sb.append("and t.out_branch_num = :outBranchNum ");
			sb.append("and t.out_order_state_code = 3 ");
			if (branchNums != null && branchNums.size() > 0) {
				sb.append("and t.branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
			}
			sb.append("group by t.branch_num, %s");
		}

		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)) {
			sql = StringUtils.replace(sql, "%s", "subString(convert(varchar(8), t.out_order_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_DATE)) {
			sql = StringUtils.replace(sql, "%s", "convert(varchar(8), t.out_order_audit_time, 112)");
		} else {
			sql = StringUtils.replace(sql, "%s", "subString(convert(varchar(8), t.out_order_audit_time, 112), 0, 5)");
		}
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("outBranchNum", outBranchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}



	@Override
	public List<Object[]> findLineSummary(String systemBookCode, Integer branchNum, List<Integer> transferLineNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o.transfer_line_num, sum(detail.out_order_detail_sale_subtotal) as money, sum(detail.out_order_detail_use_qty) as useQty ");
		sb.append("from ");
		sb.append("out_order_detail as detail with(nolock) inner join transfer_out_order as o with(nolock) on o.out_order_fid = detail.out_order_fid ");
		sb.append("where o.system_book_code = :systemBookCode and o.out_branch_num = :branchNum ");
		sb.append("and o.out_order_audit_time between :dateFrom and :dateTo and o.out_order_state_code = 3 ");
		if (transferLineNums != null && transferLineNums.size() > 0) {
			sb.append("and o.transfer_line_num in " + AppUtil.getIntegerParmeList(transferLineNums));
		}
		sb.append("group by o.transfer_line_num ");
		String sql = sb.toString();
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setInteger("branchNum", branchNum);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}



	@Override
	public List<Object[]> findItemLotSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, detail.out_order_detail_lot_number,");
		sb.append("sum(detail.out_order_detail_qty) as amount, sum(detail.out_order_detail_sale_subtotal) as money, ");
		sb.append("sum(detail.out_order_detail_sale_subtotal - detail.out_order_detail_subtotal) as profit, ");
		sb.append("sum(detail.out_order_detail_use_qty) as useQty ");
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as t with(nolock) on detail.out_order_fid = t.out_order_fid ");
		sb.append("where t.system_book_code = :systemBookCode and t.out_branch_num = :branchNum ");
		sb.append(
				"and t.out_order_audit_time between :dateFrom and :dateTo and t.out_order_state_code = 3 and detail.out_order_detail_lot_number is not null and detail.out_order_detail_lot_number != '' ");

		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num, detail.out_order_detail_lot_number ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}



	@Override
	public List<Object[]> findProfitGroupByBranch(TransferProfitQuery transferProfitQuery) {

		StringBuffer sb = new StringBuffer();
		if (transferProfitQuery.isFilterPolicyItems()) {
			sb.append(
					"select t.branch_num, t.out_branch_num, sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_qty - out_order_detail_policy_use_qty * out_order_detail_use_rate) *out_order_detail_cost else out_order_detail_subtotal end) as subtotal, ");
			sb.append(
					"sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_use_qty - out_order_detail_policy_use_qty) * out_order_detail_use_price else out_order_detail_sale_subtotal end) as saleSubtotal, ");
			sb.append(
					"sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_qty - out_order_detail_policy_use_qty * out_order_detail_use_rate) * out_order_detail_sale_price else out_order_detail_sale_price * out_order_detail_qty end) as saleMoney, ");
			sb.append(
					"sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_qty - out_order_detail_policy_use_qty * out_order_detail_use_rate)/p.item_transfer_rate else out_order_detail_qty/p.item_transfer_rate end) as transferQty, ");
			sb.append("sum(case when out_order_detail_policy_use_qty > 0 then (out_order_detail_use_qty - out_order_detail_policy_use_qty) else out_order_detail_use_qty end) as useQty, ");
			sb.append("sum(detail.out_order_detail_present_basic_qty/p.item_transfer_rate) as presentTransferQty,");
			sb.append("sum(detail.out_order_detail_present_use_qty) as presentUseQty, ");
			sb.append("sum(detail.out_order_detail_present_basic_qty * detail.out_order_detail_price) as presentTransferMoney, ");
			sb.append("sum(detail.out_order_detail_present_basic_qty * detail.out_order_detail_cost) as presentCostMoney ");

		} else {
			sb.append("select t.branch_num, t.out_branch_num, sum(detail.out_order_detail_subtotal) as subtotal, sum(detail.out_order_detail_sale_subtotal) as saleSubtotal, ");
			sb.append("sum(detail.out_order_detail_sale_price * detail.out_order_detail_qty) as saleMoney, sum(detail.out_order_detail_qty/p.item_transfer_rate) as transferQty, ");
			sb.append("sum(detail.out_order_detail_use_qty) as useQty, ");
			sb.append("sum(detail.out_order_detail_present_basic_qty/p.item_transfer_rate) as presentTransferQty,");
			sb.append("sum(detail.out_order_detail_present_use_qty) as presentUseQty, ");
			sb.append("sum(detail.out_order_detail_present_basic_qty * detail.out_order_detail_price) as presentTransferMoney, ");
			sb.append("sum(detail.out_order_detail_present_basic_qty * detail.out_order_detail_cost) as presentCostMoney ");
		}
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as t with(nolock) on t.out_order_fid = detail.out_order_fid, pos_item as p with(nolock) ");
		sb.append("where p.item_num = detail.item_num and p.system_book_code = t.system_book_code and t.system_book_code = '" + transferProfitQuery.getSystemBookCode()
				+ "' and t.out_order_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if (transferProfitQuery.getDistributionBranchNums() != null && transferProfitQuery.getDistributionBranchNums().size() > 0) {
			sb.append("and t.out_branch_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getDistributionBranchNums()));
		}
		if (transferProfitQuery.getResponseBranchNums() != null && transferProfitQuery.getResponseBranchNums().size() > 0) {
			sb.append("and t.branch_num in  " + AppUtil.getIntegerParmeList(transferProfitQuery.getResponseBranchNums()));
		}
		if (transferProfitQuery.getDtFrom() != null) {
			sb.append("and t.out_order_audit_time >=  '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(transferProfitQuery.getDtFrom())) + "' ");
		}
		if (transferProfitQuery.getDtTo() != null) {
			sb.append("and t.out_order_audit_time <=  '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(transferProfitQuery.getDtTo())) + "' ");
		}
		if (transferProfitQuery.getItemNums() != null && transferProfitQuery.getItemNums().size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getItemNums()));
		}
		if (transferProfitQuery.getCategoryCodes() != null && transferProfitQuery.getCategoryCodes().size() > 0) {
			sb.append("and p.item_category_code in " + AppUtil.getStringParmeList(transferProfitQuery.getCategoryCodes()));
		}
		if (transferProfitQuery.getItemDepartments() != null && transferProfitQuery.getItemDepartments().size() > 0) {
			sb.append("and p.item_department in " + AppUtil.getStringParmeList(transferProfitQuery.getItemDepartments()));
		}
		if (transferProfitQuery.getStorehouseNum() != null) {
			sb.append("and t.storehouse_num = " + transferProfitQuery.getStorehouseNum() + " ");
		}
		if (StringUtils.isNotEmpty(transferProfitQuery.getItemBrand())) {
			sb.append("and p.item_brand = '" + transferProfitQuery.getItemBrand() + "' ");
		}
		sb.append("group by t.branch_num, t.out_branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}



	@Override
	public List<Object[]> findItemSummary(List<String> requestOrderFids, Boolean audit) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(detail.out_order_detail_qty) as amount ");
		sb.append("from out_order_detail as detail inner join transfer_out_order as t on detail.out_order_fid = t.out_order_fid ");
		sb.append("and t.out_order_fid in (select out_order_fid from request_order_transfer_out_order where request_order_fid in " + AppUtil.getStringParmeList(requestOrderFids) + ") ");
		if (audit != null) {
			if (audit) {
				sb.append("and t.out_order_state_code = 3 ");
			} else {
				sb.append("and t.out_order_state_code = 1 ");

			}
		}
		sb.append("group by detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}


	@Override
	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.branch_num, detail.item_num, detail.out_order_detail_item_matrix_num, sum(detail.out_order_detail_qty) as qty, sum(detail.out_order_detail_sale_subtotal) as money, ");
		sb.append("sum(detail.out_order_detail_use_qty) as useQty, sum(detail.out_order_detail_other_fee) as otherFee ");
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as r with(nolock) on detail.out_order_fid = r.out_order_fid ");
		sb.append("where r.system_book_code = :systemBookCode ");
		if (centerBranchNum != null) {
			sb.append("and r.out_branch_num = :outBranchNum ");
		}
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and r.out_order_audit_time >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and r.out_order_audit_time <= :dateTo ");
		}
		if(itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and r.out_order_state_code = 3 ");
		sb.append("group by r.branch_num, detail.item_num, detail.out_order_detail_item_matrix_num");
		String sql = sb.toString();
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		if (centerBranchNum != null) {
			query.setInteger("outBranchNum", centerBranchNum);
		}
		if (dateFrom != null) {
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if (dateTo != null) {
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		return query.list();
	}

	@Override
	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(out_order_total_money) as totalMoney from transfer_out_order with(nolock) where system_book_code = '" + systemBookCode + "' and out_order_state_code = 3 ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (centerBranchNums != null && centerBranchNums.size() > 0) {
			sb.append("and out_branch_num in " + AppUtil.getIntegerParmeList(centerBranchNums));
		}
		if (dateFrom != null) {
			sb.append("and out_order_audit_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if (dateTo != null) {
			sb.append("and out_order_audit_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		sb.append("group by branch_num");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num,sum(out_order_total_money) ");
		sb.append("from transfer_out_order with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and out_order_audit_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if (dateTo != null) {
			sb.append("and out_order_audit_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		sb.append("and out_order_state_code = '3' ");
		sb.append("group by branch_num order by branch_num asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		StringBuffer sb = new StringBuffer();
		sb.append("select out_order_audit_bizday,sum(out_order_total_money) ");
		sb.append("from transfer_out_order with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and out_order_audit_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if (dateTo != null) {
			sb.append("and out_order_audit_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		sb.append("and out_order_state_code = '3' ");
		sb.append("group by out_order_audit_bizday order by out_order_audit_bizday asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();

	}

	@Override
	public List<Object[]> findMoneyBymonthSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select subString(out_order_audit_bizday, 0, 7) as bizmonth,sum(out_order_total_money) as money ");
		sb.append("from transfer_out_order with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and out_order_audit_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if (dateTo != null) {
			sb.append("and out_order_audit_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		sb.append("and out_order_state_code = '3' ");
		sb.append("group by subString(out_order_audit_bizday, 0, 7) order by subString(out_order_audit_bizday, 0, 7) asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode",systemBookCode);
		return sqlQuery.list();
	}
	
	@Override
	public List<Integer> findTransferedItems(String systemBookCode, Integer branchNum, Integer outBranchNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct d.item_num from transfer_out_order as t with(nolock) inner join out_order_detail as d with(nolock) on t.out_order_fid = d.out_order_fid ");
		sb.append("where t.system_book_code = '" + systemBookCode + "' and t.branch_num = " + branchNum + " and t.out_branch_num = " + outBranchNum + " and t.out_order_state_code = 3 ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
	
	@Override
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer outBranchNum, Date dateFrom, Date dateTo, List<Integer> branchNums) {
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t").add(Restrictions.eq("t.outBranchNum", outBranchNum))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE)).add(Restrictions.eq("t.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("t.branchNum", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("t.outOrderPaymentDate", dateFrom));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("t.outOrderPaymentDate", dateTo));
			
		}
		criteria.setLockMode(LockMode.NONE);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("t.branchNum")).add(Projections.sum("t.outOrderDueMoney")));
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findDueMoney(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branchNum, sum(outOrderDueMoney - outOrderDiscountMoney - outOrderPaidMoney) ");
		sb.append("from TransferOutOrder where systemBookCode = :systemBookCode and state.stateCode = 3 ");
		if (outBranchNum != null) {
			sb.append("and outBranchNum = :branchNum ");
		}
		if (dateFrom != null) {
			sb.append("and outOrderPaymentDate >= :dateFrom ");
		}
		if (dateTo != null) {
			sb.append("and outOrderPaymentDate <= :dateTo ");
		}
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branchNum in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("group by branchNum ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (outBranchNum != null) {
			query.setInteger("branchNum", outBranchNum);
		}
		if (dateFrom != null) {
			query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if (dateTo != null) {
			query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		return query.list();
	}
	
	@Override
	public BigDecimal readBranchUnPaidMoney(String systemBookCode, Integer branchNum, Integer outBranchNum) {
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t").add(Restrictions.eq("t.systemBookCode", systemBookCode))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE)).add(Restrictions.eq("t.outBranchNum", outBranchNum))
				.add(Restrictions.sqlRestriction("abs(out_order_due_money - out_order_paid_money - out_order_discount_money) > 0.01")).add(Restrictions.eq("t.branchNum", branchNum));
		criteria.setProjection(Projections.sqlProjection("sum(out_order_due_money - out_order_paid_money - out_order_discount_money) as unPaid", new String[] { "unPaid" },
				new Type[] { StandardBasicTypes.BIG_DECIMAL }));
		criteria.setLockMode(LockMode.NONE);
		
		Object object = criteria.uniqueResult();
		if (object != null) {
			return (BigDecimal) object;
		}
		return BigDecimal.ZERO;
	}
	
	@Override
	public List<TransferOutOrder> findBySettleBranch(String systemBookCode, Integer branchNum, Integer outBranchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(TransferOutOrder.class, "t").add(Restrictions.eq("t.systemBookCode", systemBookCode))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE)).add(Restrictions.eq("t.outBranchNum", outBranchNum)).add(Restrictions.eq("t.branchNum", branchNum));
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("t.outOrderPaymentDate", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("t.outOrderPaymentDate", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setLockMode(LockMode.NONE);
		
		return criteria.list();
	}

	@Override
	public List<Object[]> findUnInBranchItemSummary(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t.branch_num, detail.item_num, sum(detail.out_order_detail_qty) as qty, sum(detail.out_order_detail_subtotal) as subtotal, sum(detail.out_order_detail_sale_subtotal) as saleSubtotal ");
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as t with(nolock) on t.out_order_fid = detail.out_order_fid ");
		sb.append("where t.system_book_code = '" + systemBookCode + "' and t.out_branch_num in " + AppUtil.getIntegerParmeList(centerBranchNums));
		if(branchNums != null && !branchNums.isEmpty()){
			sb.append("and t.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and t.out_order_audit_time >=  '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if (dateTo != null) {
			sb.append("and t.out_order_audit_time <=  '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		if(itemNums != null && !itemNums.isEmpty()){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and t.out_order_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		sb.append("group by t.branch_num, detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findDateSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo,String strDate) {
		StringBuilder sb = new StringBuilder();
		sb.append("select %s , sum(out_order_total_money) as totalMoney, sum(out_order_other_fee) as feeMoney  ");
		sb.append("from transfer_out_order with(nolock) where system_book_code = :systemBookCode and out_branch_num = " + centerBranchNum + " ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and out_order_audit_time between :dateFrom and :dateTo and out_order_state_code = 3 ");
		sb.append("group by %s ");
		String sql = sb.toString();

		if(AppConstants.BUSINESS_DATE_DAY.equals(strDate)){//按日
			sql = sql.replaceAll("%s","convert(varchar(8) , out_order_audit_time, 112)");
		}else{
			sql = sql.replaceAll("%s","convert(varchar(6) , out_order_audit_time, 112)");

		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
		sqlQuery.setString("systemBookCode", systemBookCode);
		sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findMoneyAndAmountByBiz(String systemBookCode, Date dateFrom, Date dateTo,List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select convert(varchar(8) , t.out_order_audit_time, 112), sum(detail.out_order_detail_qty) as qty,sum(detail.out_order_detail_subtotal) as subtotal ");
		sb.append("from out_order_detail as detail inner join transfer_out_order as t on detail.out_order_fid = t.out_order_fid ");
		sb.append("and t.system_book_code = '"+ systemBookCode+"' ");
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.item_num in "+ AppUtil.getIntegerParmeList(itemNums));
		}
		if (dateFrom != null) {
			sb.append("and t.out_order_audit_time >=  '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if (dateTo != null) {
			sb.append("and t.out_order_audit_time <=  '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		sb.append("and t.out_order_state_code = 3 ");

		sb.append("group by convert(varchar(8) , t.out_order_audit_time, 112) ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findMoneyAndAmountByItemNum(String systemBookCode, Integer branchNum, List<Integer> storehouseNums, Date dateFrom, Date dateTo, List<Integer> itemNums,String sortField ) {

		StringBuilder sb = new StringBuilder();
		sb.append("select detail.item_num,");
		sb.append("sum(detail.out_order_detail_qty) as transferQty, sum(detail.out_order_detail_sale_subtotal) as transferMoney ");
		sb.append("from out_order_detail as detail with(nolock) inner join transfer_out_order as t with(nolock) on detail.out_order_fid = t.out_order_fid ");
		sb.append("where t.system_book_code = :systemBookCode and t.out_branch_num = :branchNum ");
		sb.append("and t.out_order_audit_time between :dateFrom and :dateTo and t.out_order_state_code = 3 ");
		if(storehouseNums != null && storehouseNums.size()>0){
			sb.append("and t.storehouse_num in " + AppUtil.getIntegerParmeList(storehouseNums));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num ");
		if(StringUtils.isNotBlank(sortField)){
			sb.append("order by " + sortField);
		}

		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();

	}


}
