package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.AllocationOrderDao;
import com.nhsoft.report.model.AllocationOrder;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.Date;
import java.util.List;

public class AllocationOrderDaoImpl extends HibernateDaoSupport implements AllocationOrderDao {

	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(AllocationOrder.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.sqlRestriction("in_storehouse_num in (select storehouse_num from branch_storehouse where system_book_code = ? and branch_num = ?)"
					, new Object[]{systemBookCode, branchNum}, new Type[]{StandardBasicTypes.STRING, StandardBasicTypes.INTEGER}));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("a.allocationOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("a.allocationOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}



	@Override
	public List<Object[]> findItemLotSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
											 List<Integer> itemNums){

		StringBuffer sb = new StringBuffer();
		sb.append("select storehouse_num from branch_storehouse where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum);
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Integer> storehouseNums = query.list();

		String storehouseNumsStr = AppUtil.getIntegerParmeList(storehouseNums);

		sb = new StringBuffer();
		sb.append("select detail.item_num, detail.allocation_order_detail_lot_number,");
		sb.append("sum(detail.allocation_order_detail_qty) as amount, sum(detail.allocation_order_detail_subtotal) as money, ");
		sb.append("sum(detail.allocation_order_detail_use_qty) as useQty ");
		sb.append("from allocation_order_detail as detail with(nolock) inner join allocation_order as a with(nolock) on detail.allocation_order_fid = a.allocation_order_fid ");
		sb.append("where a.system_book_code = :systemBookCode and (a.in_storehouse_num in " + storehouseNumsStr + " or a.out_storehouse_num in " + storehouseNumsStr + ")");
		sb.append("and a.allocation_order_audit_time between :dateFrom and :dateTo and a.allocation_order_state_code = 3 and detail.allocation_order_detail_lot_number is not null and detail.allocation_order_detail_lot_number != '' ");

		if(itemNums != null && itemNums.size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num, detail.allocation_order_detail_lot_number ");
		query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return query.list();

	}

}