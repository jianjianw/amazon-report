package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.WholesaleBookDao;
import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.WholesaleBook;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;

import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
@SuppressWarnings("deprecation")
public class WholesaleBookDaoImpl extends DaoImpl implements WholesaleBookDao {
	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> regionNums) {
		Criteria criteria = currentSession().createCriteria(WholesaleBook.class, "w")
				.add(Restrictions.eq("w.systemBookCode", systemBookCode));
		if(branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("w.branchNum", branchNum));
		}
		if(regionNums != null && regionNums.size() > 0){
			criteria.add(Restrictions.in("w.regionNum", regionNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("w.wholesaleBookCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("w.wholesaleBookCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}




	@Override
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum,
									  String clientFid, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		Criteria criteria = createCriteria(systemBookCode, branchNum, clientFid, dateFrom, dateTo, categoryCodes, itemNums, regionNums);
		criteria.add(Restrictions.disjunction()
				.add(Restrictions.isNull("detail.bookDetailStockoutTag"))
				.add(Restrictions.eq("detail.bookDetailStockoutTag", false))
		);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("detail.itemNum"))
				.add(Projections.groupProperty("detail.bookDetailItemMatrixNum"))
				.add(Projections.sum("detail.bookDetailQty"))
				.add(Projections.sum("detail.bookDetailMoney"))
		);

		return criteria.list();
	}

	private Criteria createCriteria(String systemBookCode,
									Integer branchNum, String clientFid, Date dateFrom,
									Date dateTo, List<String> categorys,List<Integer> itemNums, List<Integer> regionNums){
		Criteria criteria = currentSession().createCriteria(WholesaleBook.class, "w")
				.createAlias("w.wholesaleBookDetails", "detail")
				.add(Restrictions.eq("w.systemBookCode", systemBookCode))
				.add(Restrictions.eq("w.branchNum", branchNum))
				.add(Restrictions.eq("w.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		if(StringUtils.isNotEmpty(clientFid)){
			criteria.add(Restrictions.eq("w.clientFid", clientFid));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("w.wholesaleBookAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("w.wholesaleBookAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", itemNums));
		}
		if(categorys != null && categorys.size() > 0){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "item")
					.add(Restrictions.in("item.itemCategoryCode", categorys))
					.add(Restrictions.eq("item.systemBookCode", systemBookCode))
					.add(Property.forName("item.itemNum").eqProperty("detail.itemNum"));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("item.itemNum"))));
		}
		if(regionNums != null && regionNums.size() > 0){
			criteria.add(Restrictions.in("w.regionNum", regionNums));
		}
		return criteria;
	}
}
