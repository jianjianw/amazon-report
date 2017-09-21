package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.TransferInOrderDao;
import com.nhsoft.report.model.PosItem;
import com.nhsoft.report.model.TransferInOrder;
import com.nhsoft.report.shared.queryBuilder.TransferProfitQuery;
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
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Repository
public class TransferInOrderDaoImpl extends DaoImpl implements TransferInOrderDao {

	@Override
	public List<Object[]> findProfitGroupByItem(String systemBookCode,
												List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom,
												Date dateTo, List<String> categoryCodeList, List<Integer> itemNums) {
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
				.add(Projections.groupProperty("detail.inOrderDetailItemMatrixNum"))
				.add(Projections.sum("detail.inOrderDetailQty"))
				.add(Projections.sum("detail.inOrderDetailSaleSubtotal"))
				.add(Projections.sum("detail.inOrderDetailSubtotal"))
				.add(Projections.sum("detail.inOrderDetailPresentQty"))
		);
		return criteria.list();
	}



	@Override
	public List<Object[]> findMoneyByItemDate(String systemBookCode, List<Integer> transferBranchNums,
											  List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
		transferProfitQuery.setSystemBookCode(systemBookCode);
		transferProfitQuery.setDistributionBranchNums(transferBranchNums);
		transferProfitQuery.setResponseBranchNums(branchNums);
		transferProfitQuery.setDtFrom(dateFrom);
		transferProfitQuery.setDtTo(dateTo);
		transferProfitQuery.setItemNums(itemNums);
		Criteria criteria = createProfitCriteria(transferProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sqlGroupProjection("convert(varchar(12),in_order_audit_time,23) as date", "convert(varchar(12),in_order_audit_time,23)",
						new String[]{"date"}, new Type[]{StandardBasicTypes.STRING}))
				.add(Projections.sum("detail.inOrderDetailSaleSubtotal"))
				.add(Projections.sum("detail.inOrderDetailSubtotal"))
				.add(Projections.sum("detail.inOrderDetailQty"))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, List<Integer> transferBranchNums,
												  Date dateFrom, Date dateTo, List<Integer> itemNums) {
		TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
		transferProfitQuery.setSystemBookCode(systemBookCode);
		transferProfitQuery.setDistributionBranchNums(transferBranchNums);
		transferProfitQuery.setDtFrom(dateFrom);
		transferProfitQuery.setDtTo(dateTo);
		transferProfitQuery.setItemNums(itemNums);
		Criteria criteria = createProfitCriteria(transferProfitQuery);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.sqlGroupProjection("substring(convert(varchar(12), in_order_audit_time, 23),0, 8) as date", "substring(convert(varchar(12), in_order_audit_time, 23),0, 8)",
						new String[]{"date"}, new Type[]{StandardBasicTypes.STRING}))
				.add(Projections.sum("detail.inOrderDetailSaleSubtotal"))
				.add(Projections.sum("detail.inOrderDetailSubtotal"))
		);
		return criteria.list();
	}


	@Override
	public List<Object[]> findProfitGroupByBranch(TransferProfitQuery transferProfitQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.branch_num, t.in_branch_num, sum(detail.in_order_detail_subtotal) as subtotal, sum(detail.in_order_detail_sale_subtotal) as saleSubtotal, sum(detail.in_order_detail_sale_price * detail.in_order_detail_qty) as saleMoney, sum(detail.in_order_detail_qty/p.item_transfer_rate) as transferQty, ");
		sb.append("sum(detail.in_order_detail_use_qty) as useQty, sum(detail.in_order_detail_present_qty/p.item_transfer_rate) as transferPresentQty, ");
		sb.append("sum(detail.in_order_detail_present_use_qty) as presentUseQty, ");
		sb.append("sum(detail.in_order_detail_present_qty * detail.in_order_detail_price) as presentTransferMoney, ");
		sb.append("sum(detail.in_order_detail_present_qty * detail.in_order_detail_cost) as presentCostMoney ");
		sb.append("from in_order_detail as detail with(nolock) inner join transfer_in_order as t with(nolock) on t.in_order_fid = detail.in_order_fid, pos_item as p with(nolock) ");
		sb.append("where p.item_num = detail.item_num and t.system_book_code = :systemBookCode and t.in_order_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if(transferProfitQuery.getDistributionBranchNums() != null && transferProfitQuery.getDistributionBranchNums().size() > 0){
			sb.append("and t.in_branch_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getDistributionBranchNums()));
		}
		if(transferProfitQuery.getResponseBranchNums() != null && transferProfitQuery.getResponseBranchNums().size() > 0){
			sb.append("and t.branch_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getResponseBranchNums()));
		}
		if(transferProfitQuery.getDtFrom() != null){
			sb.append("and t.in_order_audit_time >= :dateFrom ");
		}
		if(transferProfitQuery.getDtTo() != null){
			sb.append("and t.in_order_audit_time <= :dateTo ");
		}
		if(transferProfitQuery.getItemNums() != null && transferProfitQuery.getItemNums().size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getItemNums()));
		}
		if(transferProfitQuery.getCategoryCodes() != null && transferProfitQuery.getCategoryCodes().size() > 0){
			sb.append("and p.item_category_code in " + AppUtil.getStringParmeList(transferProfitQuery.getCategoryCodes()));
		}
		if(transferProfitQuery.getItemDepartments() != null && transferProfitQuery.getItemDepartments().size() > 0){
			sb.append("and p.item_department in " + AppUtil.getStringParmeList(transferProfitQuery.getItemDepartments()));
		}
		if(StringUtils.isNotEmpty(transferProfitQuery.getItemBrand())){
			sb.append("and p.item_brand = '" + transferProfitQuery.getItemBrand() + "' ");
		}
		sb.append("group by t.branch_num, t.in_branch_num ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", transferProfitQuery.getSystemBookCode());

		if(transferProfitQuery.getDtFrom() != null){
			query.setParameter("dateFrom", DateUtil.getMinOfDate(transferProfitQuery.getDtFrom()));
		}
		if(transferProfitQuery.getDtTo() != null){
			query.setParameter("dateTo", DateUtil.getMaxOfDate(transferProfitQuery.getDtTo()));
		}
		return query.list();
	}

	@Override
	public List<Object[]> findBranchSumByDateType(String systemBookCode, Integer inBranchNum, List<Integer> branchNums,
												  Date dateFrom, Date dateTo, String dateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.branch_num, %s, sum(t.in_order_total_money) as money ");
		sb.append("from transfer_in_order as t with(nolock) ");
		sb.append("where t.system_book_code = :systemBookCode and t.in_order_audit_time between :dateFrom and :dateTo ");
		sb.append("and t.in_branch_num = :inBranchNum ");
		sb.append("and t.in_order_state_code = 3 ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and t.branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		}
		sb.append("group by t.branch_num, %s");
		String sql = sb.toString();
		if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)) {
			sql = StringUtils.replace(sql, "%s", "subString(convert(varchar(8), t.in_order_audit_time, 112), 0, 7)");
		} else if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_DATE)) {
			sql = StringUtils.replace(sql, "%s", "convert(varchar(8), t.in_order_audit_time, 112)");
		} else {
			sql = StringUtils.replace(sql, "%s", "subString(convert(varchar(8), t.in_order_audit_time, 112), 0, 5)");
		}
		Query query = currentSession().createSQLQuery(sql);
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("inBranchNum", inBranchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();
	}

	private Criteria createProfitCriteria(TransferProfitQuery transferProfitQuery){
		Criteria criteria = currentSession().createCriteria(TransferInOrder.class, "t")
				.createAlias("t.inOrderDetails", "detail")
				.add(Restrictions.eq("t.systemBookCode", transferProfitQuery.getSystemBookCode()))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if(transferProfitQuery.getDistributionBranchNums() != null && transferProfitQuery.getDistributionBranchNums().size() > 0){
			criteria.add(Restrictions.in("t.inBranchNum", transferProfitQuery.getDistributionBranchNums()));
		}
		if(transferProfitQuery.getResponseBranchNums() != null && transferProfitQuery.getResponseBranchNums().size() > 0){
			criteria.add(Restrictions.sqlRestriction("branch_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getResponseBranchNums())));

		}
		if(transferProfitQuery.getDtFrom() != null){
			criteria.add(Restrictions.ge("t.inOrderAuditTime", DateUtil.getMinOfDate(transferProfitQuery.getDtFrom())));
		}
		if(transferProfitQuery.getDtTo() != null){
			criteria.add(Restrictions.le("t.inOrderAuditTime", DateUtil.getMaxOfDate(transferProfitQuery.getDtTo())));
		}
		if(transferProfitQuery.getItemNums() != null && transferProfitQuery.getItemNums().size() > 0){
			criteria.add(Restrictions.sqlRestriction("item_num in " + AppUtil.getIntegerParmeList(transferProfitQuery.getItemNums())));

		}
		if((transferProfitQuery.getCategoryCodes() != null && transferProfitQuery.getCategoryCodes().size() > 0)
				|| (StringUtils.isNotEmpty(transferProfitQuery.getItemBrand()))
				|| (transferProfitQuery.getItemDepartments() != null && transferProfitQuery.getItemDepartments().size() > 0)){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")

					.add(Restrictions.eq("item.systemBookCode", transferProfitQuery.getSystemBookCode()))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			if(transferProfitQuery.getCategoryCodes() != null && transferProfitQuery.getCategoryCodes().size() > 0){
				subCriteria.add(Restrictions.in("item.itemCategoryCode", transferProfitQuery.getCategoryCodes()));
			}
			if(transferProfitQuery.getItemDepartments() != null && transferProfitQuery.getItemDepartments().size() > 0){
				subCriteria.add(Restrictions.in("item.itemDepartment", transferProfitQuery.getItemDepartments()));
			}
			if(StringUtils.isNotEmpty(transferProfitQuery.getItemBrand())){
				subCriteria.add(Restrictions.eq("item.itemBrand", transferProfitQuery.getItemBrand()));

			}
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		return criteria;
	}

	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(TransferInOrder.class, "t")
				.add(Restrictions.eq("t.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("t.inBranchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("t.inOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("t.inOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	@Override
	public BigDecimal findBalance(String systemBookCode, Integer centerBranchNum, Integer branchNum,
								  Date dtFrom, Date dtTo) {
		Criteria criteria = currentSession().createCriteria(TransferInOrder.class, "t")
				.add(Restrictions.eq("t.systemBookCode", systemBookCode))
				.add(Restrictions.eq("t.branchNum", branchNum))
				.add(Restrictions.eq("t.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if(centerBranchNum != null){
			criteria.add(Restrictions.eq("t.inBranchNum", centerBranchNum));
		}
		if(dtFrom != null){
			criteria.add(Restrictions.ge("t.inOrderAuditTime", DateUtil.getMinOfDate(dtFrom)));
		}
		if(dtTo != null){
			criteria.add(Restrictions.le("t.inOrderAuditTime", DateUtil.getMaxOfDate(dtTo)));
		}
		criteria.setProjection(Projections.sqlProjection("sum(in_order_due_money - in_order_discount_money - in_order_paid_money) as balance",
				new String[]{"balance"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}));
		List<Object[]> objects = criteria.list();
		if(objects != null && objects.size() > 0){
			Object object = objects.get(0);
			return object == null? BigDecimal.ZERO:(BigDecimal)object;
		}

		return BigDecimal.ZERO;
	}
}
