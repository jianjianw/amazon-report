package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.PosItemLogDao;
import com.nhsoft.module.report.model.PosItemLog;
import com.nhsoft.module.report.shared.queryBuilder.StoreQueryCondition;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Repository
public class PosItemLogDaoImpl extends DaoImpl implements PosItemLogDao {
	@Override
	public List<Integer> findItemNum(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Boolean inOut) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDate", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDate", DateUtil.getMaxOfDate(dateTo)));
		}
		if(inOut != null){
			criteria.add(Restrictions.eq("p.posItemLogInoutFlag", inOut));
		}
		criteria.setProjection(Projections.groupProperty("p.itemNum"));
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findItemLatestPriceDate(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums, String posItemLogSummary) {
		StringBuffer sb = new StringBuffer();
		sb.append("select item_num, pos_item_log_item_price, pos_item_log_date ");
		sb.append("from pos_item_log with(nolock), ");
		sb.append("(select item_num as itemNum, max(pos_item_log_date) as maxDate from pos_item_log with(nolock) where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " ");
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(StringUtils.isNotEmpty(posItemLogSummary)){
			sb.append("and pos_item_log_summary in (:summaries) ");
		}
		sb.append("group by item_num) as sub ");
		sb.append("where sub.itemNum = item_num and pos_item_log_date = sub.maxDate ");
		sb.append("and branch_num = " + branchNum + " and system_book_code = '" + systemBookCode + "' ");
		if(dateFrom != null){
			sb.append("and pos_item_log_date_index >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and pos_item_log_date_index <= :dateTo ");
		}
		if(StringUtils.isNotEmpty(posItemLogSummary)){
			sb.append("and pos_item_log_summary in (:summaries) ");
		}
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		if(StringUtils.isNotEmpty(posItemLogSummary)){
			sqlQuery.setParameterList("summaries", posItemLogSummary.split(","));
			
		}
		if(dateFrom != null){
			sqlQuery.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			sqlQuery.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		}
		return sqlQuery.list();
	}
	
	@Override
	public List<PosItemLog> findByStoreQueryCondition(StoreQueryCondition storeQueryCondition, int offset, int limit) {
		StringBuffer sb = new StringBuffer();
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		sb.append("select l.* ");
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l with(nolock) ");
		} else {
			sb.append("from pos_item_log as l with(nolock) ");
		}
		sb.append("where l.system_book_code = :systemBookCode and l.branch_num = :branchNum ");
		if(StringUtils.isNotEmpty(storeQueryCondition.getRefBillNo())){
			sb.append("and l.pos_item_log_bill_no = :refBillNo ");
		}
		if(StringUtils.isEmpty(storeQueryCondition.getRefBillNo())){
			if(storeQueryCondition.getDateStart() != null && storeQueryCondition.getDateEnd() != null){
				sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' and '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");
				
			} else {
				if(storeQueryCondition.getDateStart() != null){
					sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
				}
				if(storeQueryCondition.getDateEnd() != null){
					sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");
					
				}
				
			}
			if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
				sb.append("and l.item_num in (:itemNums) ");
			}
			if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogLotNumber())){
				sb.append("and l.pos_item_log_lot_number = :lotNumber ");
			}
			
			if(storeQueryCondition.getItemCategoryCodes() != null && storeQueryCondition.getItemCategoryCodes().size() > 0){
				sb.append("and l.pos_item_log_item_category in (:categoryCodes) ");
			}
			if(storeQueryCondition.getStorehouseNum() != null){
				sb.append("and (l.in_storehouse_num = :storehouseNum or l.out_storehouse_num = :storehouseNum) ");
			}
			if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
				sb.append("and l.pos_item_log_summary = :summary ");
			}
			if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
				sb.append("and l.pos_item_log_memo = :adjustReason ");
			}
		}
		if(storeQueryCondition.getSortField() == null){
			storeQueryCondition.setSortField("posItemLogDate");
			storeQueryCondition.setSortType("asc");
		}
		sb.append("order by l." + AppUtil.getDBColumnName(storeQueryCondition.getSortField()) + " " + storeQueryCondition.getSortType());
		
		//AMA-7439 pos_item_log_date 一样的时候再按pos_item_log_item_balance逆序
		if(storeQueryCondition.getSortField().equals("posItemLogDate")){
			if(storeQueryCondition.getSortType().equalsIgnoreCase("asc")){
				sb.append(", pos_item_log_item_balance desc ");
				
			} else {
				sb.append(", pos_item_log_item_balance asc ");
			}
			
		}
		
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		sqlQuery.setInteger("branchNum", storeQueryCondition.getBranchNum());
		if(StringUtils.isEmpty(storeQueryCondition.getRefBillNo())){
			
			if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
				sqlQuery.setParameterList("itemNums", storeQueryCondition.getItemNums());
			}
			if(storeQueryCondition.getItemCategoryCodes() != null && storeQueryCondition.getItemCategoryCodes().size() > 0){
				sqlQuery.setParameterList("categoryCodes", storeQueryCondition.getItemCategoryCodes());
			}
			if(storeQueryCondition.getStorehouseNum() != null){
				sqlQuery.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
			}
			if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
				sqlQuery.setString("summary", storeQueryCondition.getPosItemLogSummary());
			}
			if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
				sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
			}
			if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogLotNumber())){
				sqlQuery.setString("lotNumber", storeQueryCondition.getPosItemLogLotNumber());
			}
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getRefBillNo())){
			sqlQuery.setString("refBillNo", storeQueryCondition.getRefBillNo());
		}
		sqlQuery.addEntity("l", PosItemLog.class);
		if(storeQueryCondition.isPaging()){
			sqlQuery.setFirstResult(offset);
			sqlQuery.setMaxResults(limit);
			
		}
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> findItemSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition) {
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		StringBuffer sb = new StringBuffer();
		sb.append("select l.item_num, l.pos_item_log_inout_flag, l.pos_item_log_summary, l.pos_item_log_memo,  ");
		sb.append("sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount, ");
		sb.append("sum(pos_item_log_adjust_money) as adjustMoney ");
		
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l ");
			
		} else {
			sb.append("from pos_item_log as l ");
		}
		sb.append("with(nolock) where l.system_book_code = :systemBookCode and l.branch_num = :branchNum ");
		if(storeQueryCondition.getDateStart() != null){
			sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
		}
		if(storeQueryCondition.getDateEnd() != null){
			sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");
			
		}
		if(storeQueryCondition.getExactDateEnd() != null){
			sb.append("and l.pos_item_log_date <= '" + DateUtil.getLongDateTimeStr(storeQueryCondition.getExactDateEnd()) + "' ");
		}
		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			sb.append("and l.item_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getItemNums()));
		}
		if(storeQueryCondition.getItemCategoryCodes() != null && storeQueryCondition.getItemCategoryCodes().size() > 0){
			sb.append("and l.pos_item_log_item_category in " + AppUtil.getStringParmeList(storeQueryCondition.getItemCategoryCodes()));
		}
		if(storeQueryCondition.getStorehouseNum() != null){
			sb.append("and (l.in_storehouse_num = :storehouseNum or l.out_storehouse_num = :storehouseNum) ");
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(storeQueryCondition.getPosItemLogSummary().split(",")));
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sb.append("and l.pos_item_log_memo = :adjustReason ");
		}
		if(storeQueryCondition.getCenterStorehouse() != null){
			sb.append("and (exists (select 1 from storehouse where l.in_storehouse_num = storehouse.storehouse_num and system_book_code = :systemBookCode and storehouse_del_tag = 0 and storehouse_center_tag = 1 )"
					+ "or exists (select 1 from storehouse where l.out_storehouse_num = storehouse.storehouse_num and system_book_code = :systemBookCode and storehouse_del_tag = 0 and storehouse_center_tag = 1 ) )");
		}
		sb.append("group by l.item_num, l.pos_item_log_inout_flag, l.pos_item_log_summary, l.pos_item_log_memo ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		sqlQuery.setInteger("branchNum", storeQueryCondition.getBranchNum());
		if(storeQueryCondition.getStorehouseNum() != null){
			sqlQuery.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}
		
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> findBranchSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition) {
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		StringBuffer sb = new StringBuffer();
		sb.append("select l.branch_num, l.pos_item_log_inout_flag, l.pos_item_log_summary, l.pos_item_log_memo,  ");
		sb.append("sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount, ");
		sb.append("sum(pos_item_log_adjust_money) as adjustMoney ");
		
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l ");
			
		} else {
			sb.append("from pos_item_log as l ");
		}
		sb.append("with(nolock) where l.system_book_code = :systemBookCode ");
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
			
		} else {
			sb.append("and l.branch_num = " + storeQueryCondition.getBranchNum() + " ");
		}
		
		
		if(storeQueryCondition.getDateStart() != null){
			sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
		}
		if(storeQueryCondition.getDateEnd() != null){
			sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");
			
		}
		if(storeQueryCondition.getExactDateEnd() != null){
			sb.append("and l.pos_item_log_date <= '" + DateUtil.getLongDateTimeStr(storeQueryCondition.getExactDateEnd()) + "' ");
		}
		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			sb.append("and l.item_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getItemNums()));
		}
		if(storeQueryCondition.getItemCategoryCodes() != null && storeQueryCondition.getItemCategoryCodes().size() > 0){
			sb.append("and l.pos_item_log_item_category in " + AppUtil.getStringParmeList(storeQueryCondition.getItemCategoryCodes()));
		}
		if(storeQueryCondition.getStorehouseNum() != null){
			sb.append("and (l.in_storehouse_num = :storehouseNum or l.out_storehouse_num = :storehouseNum) ");
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(storeQueryCondition.getPosItemLogSummary().split(",")));
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sb.append("and l.pos_item_log_memo = :adjustReason ");
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getItemDepartment())){
			sb.append("and exists (select 1 from pos_item as p with(nolock) where p.item_num = l.item_num and p.item_department = '" + storeQueryCondition.getItemDepartment() + "' ) ");
		}
		if(storeQueryCondition.getCenterStorehouse() != null){
			sb.append("and (exists (select 1 from storehouse where l.in_storehouse_num = storehouse.storehouse_num and system_book_code = :systemBookCode and storehouse_del_tag = 0 and storehouse_center_tag = 1 )"
					+ "or exists (select 1 from storehouse where l.out_storehouse_num = storehouse.storehouse_num and system_book_code = :systemBookCode and storehouse_del_tag = 0 and storehouse_center_tag = 1 ) )");
		}
		sb.append("group by l.branch_num, l.pos_item_log_inout_flag, l.pos_item_log_summary, l.pos_item_log_memo ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		
		if(storeQueryCondition.getStorehouseNum() != null){
			sqlQuery.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}
		
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> findBranchInOutSummary(StoreQueryCondition storeQueryCondition) {
		StringBuffer sb = new StringBuffer();
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		sb.append("select l.branch_num, l.pos_item_log_inout_flag, sum(l.pos_item_log_item_amount) as amount, ");
		sb.append("sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount, ");
		sb.append("sum(l.pos_item_log_adjust_money) as adjustMoney ");
		
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l ");
			
		} else {
			sb.append("from pos_item_log as l ");
		}
		sb.append("with(nolock) ");
		sb.append("where l.system_book_code = :systemBookCode and l.branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		
		
		if(storeQueryCondition.getDateStart() != null){
			sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
		}
		if(storeQueryCondition.getDateEnd() != null){
			sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");
			
		}
		if(storeQueryCondition.getExactDateEnd() != null){
			sb.append("and l.pos_item_log_date <= '" + DateUtil.getLongDateTimeStr(storeQueryCondition.getExactDateEnd()) + "' ");
		}
		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			sb.append("and l.item_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getItemNums()));
		}
		if(storeQueryCondition.getItemCategoryCodes() != null && storeQueryCondition.getItemCategoryCodes().size() > 0){
			sb.append("and l.pos_item_log_item_category in " + AppUtil.getStringParmeList(storeQueryCondition.getItemCategoryCodes()));
		}
		if(storeQueryCondition.getStorehouseNum() != null){
			sb.append("and (l.in_storehouse_num = :storehouseNum or l.out_storehouse_num = :storehouseNum) ");
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary = :summary ");
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sb.append("and l.pos_item_log_memo = :adjustReason ");
		}
		if(storeQueryCondition.getFilterElement() != null && storeQueryCondition.getFilterElement()){
			sb.append("and l.item_num not in (select item_num from pos_item with(nolock) where system_book_code = :systemBookCode and item_type in " + AppUtil.getIntegerParmeList(AppUtil.getUnStoreTypes()) + ") ");
		}
		sb.append("group by l.branch_num, l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		
		if(storeQueryCondition.getStorehouseNum() != null){
			sqlQuery.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sqlQuery.setString("summary", storeQueryCondition.getPosItemLogSummary());
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> findItemAmountBySummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String summaries) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateTo)));
		}
		if(summaries != null){
			criteria.add(Restrictions.in("p.posItemLogSummary", summaries.split(",")));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("p.itemNum"))
				.add(Projections.groupProperty("p.posItemLogItemMatrixNum"))
				.add(Projections.sum("p.posItemLogItemAmount"))
		);
		return criteria.list();
	}
	
	@Override
	public BigDecimal findItemAmountBySummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String summaries, Integer itemNum, Boolean inoutFlag) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.itemNum", itemNum))
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateTo)));
		}
		if(summaries != null){
			criteria.add(Restrictions.in("p.posItemLogSummary", summaries.split(",")));
		}
		if(inoutFlag != null){
			criteria.add(Restrictions.eq("p.posItemLogInoutFlag", inoutFlag));
		}
		criteria.setProjection(Projections.sum("p.posItemLogItemAmount"));
		Object object = criteria.uniqueResult();
		return object == null?BigDecimal.ZERO:(BigDecimal)object;
	}
	
	@Override
	public List<Object[]> findSumByBranchAndItemFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("p.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateTo)));
		}
		if(summaries != null){
			criteria.add(Restrictions.in("p.posItemLogSummary", summaries.split(",")));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("p.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("p.branchNum"))
				.add(Projections.groupProperty("p.itemNum"))
				.add(Projections.groupProperty("p.posItemLogItemMatrixNum"))
				.add(Projections.groupProperty("p.posItemLogInoutFlag"))
				.add(Projections.sum("p.posItemLogItemAmount"))
				.add(Projections.sum("p.posItemLogMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public int countByBranch(String systemBookCode, Integer branchNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(system_book_code) from pos_item_log with(nolock) where system_book_code = :systemBookCode ");
		if(!branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			sb.append("and branch_num = :branchNum ");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(!branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			query.setInteger("branchNum", branchNum);
		}
		Object object = query.uniqueResult();
		if(object != null){
			return (Integer)object;
		}
		return 0;
	}
	
	@Override
	public List<Object[]> findMoneyByBranchFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer storehouseNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.branchNum, p.posItemLogInoutFlag, sum(p.posItemLogMoney), sum(p.posItemLogItemAmount * item.itemRegularPrice) ");
		sb.append("from PosItemLog as p, PosItem as item ");
		sb.append("where p.itemNum = item.itemNum and p.systemBookCode = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and p.branchNum in (:branchNums) ");
			
		}
		if(dateFrom != null){
			sb.append("and p.posItemLogDateIndex >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and p.posItemLogDateIndex <= :dateTo ");
			Date now  = Calendar.getInstance().getTime();
			if(DateUtil.isSameDate(now, dateTo)){
				sb.append("and p.posItemLogDate <= '" + DateUtil.getLongDateTimeStr(dateTo) + "' ");
			}
		}
		if(storehouseNum != null){
			sb.append("and (p.inStorehouseNum = " + storehouseNum + " or p.outStorehouseNum = " + storehouseNum + ") ");
			
		}
		sb.append("group by p.branchNum, p.posItemLogInoutFlag ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(branchNums != null && branchNums.size() > 0){
			query.setParameterList("branchNums", branchNums);
		}
		if(dateFrom != null){
			query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		}
		if(dateTo != null){
			query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		}
		return query.list();
	}
	
	@Override
	public List<Object[]> findMoneyByBranchItemFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("p.branchNum", branchNums));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("p.branchNum"))
				.add(Projections.groupProperty("p.itemNum"))
				.add(Projections.groupProperty("p.posItemLogInoutFlag"))
				.add(Projections.sum("p.posItemLogMoney"))
				.add(Projections.sum("p.posItemLogItemAmount"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findItemPrice(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.posItemLogSummary", AppConstants.POS_ITEM_LOG_RECEIVE_ORDER))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNum != null){
			criteria.add(Restrictions.eq("p.branchNum", branchNum));
		}
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateTo)));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("p.itemNum", itemNums));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("p.itemNum"))
				.add(Projections.max("p.posItemLogItemPrice"))
				.add(Projections.min("p.posItemLogItemPrice"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findSumByItemFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum, List<String> memos) {
		StringBuffer sb = new StringBuffer();
		String queryYear = DateUtil.getYearString(dateFrom);
		sb.append("select l.item_num, l.pos_item_log_item_matrix_num, l.pos_item_log_inout_flag,  ");
		sb.append("sum(l.pos_item_log_item_amount) as amount, sum(l.POS_ITEM_LOG_MONEY) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount,  ");
		sb.append("sum(l.pos_item_log_use_qty) as useAmount, sum(l.pos_item_log_operate_price * l.pos_item_log_item_amount) as saleMoney, ");
		sb.append("min(l.pos_item_log_use_unit) as useUnit ");
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l with(nolock) ");
		} else {
			sb.append("from pos_item_log as l with(nolock) ");
		}
		sb.append("where l.system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null && dateTo != null){
			
			sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");
			
			
		} else {
			if(dateFrom != null){
				sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
			}
			if(dateTo != null){
				sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
				
			}
		}
		
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and l.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(storehouseNum != null){
			sb.append("and (l.in_storehouse_num = " + storehouseNum + " or l.out_storehouse_num = " + storehouseNum + ") ");
		}
		if(StringUtils.isNotEmpty(summaries)){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(summaries.split(",")));
		}
		if(memos != null && memos.size() > 0){
			sb.append("and ((l.pos_item_log_summary = '调整单' and l.pos_item_log_memo in " + AppUtil.getStringParmeList(memos) + ") or l.pos_item_log_summary != '调整单') ");
			
		}
		
		sb.append("group by l.item_num, l.pos_item_log_item_matrix_num, l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> findItemMatrixInOutQtyAndMoney(StoreQueryCondition storeQueryCondition) {
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		StringBuffer sb = new StringBuffer();
		sb.append("select l.item_num, l.pos_item_log_item_matrix_num, l.pos_item_log_inout_flag , sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount ");
		
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l ");
			
		} else {
			sb.append("from pos_item_log as l ");
		}
		sb.append("with(nolock) where l.system_book_code = :systemBookCode and l.branch_num = :branchNum ");
		
		if(storeQueryCondition.getDateStart() != null){
			sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
		}
		if(storeQueryCondition.getDateEnd() != null){
			sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");
			
		}
		if(storeQueryCondition.getExactDateEnd() != null){
			sb.append("and l.pos_item_log_date <= '" + DateUtil.getLongDateTimeStr(storeQueryCondition.getExactDateEnd()) + "' ");
		}
		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			sb.append("and l.item_num in (:itemNums) ");
		}
		if(storeQueryCondition.getItemCategoryCodes() != null && storeQueryCondition.getItemCategoryCodes().size() > 0){
			sb.append("and l.pos_item_log_item_category in (:categoryCodes) ");
		}
		if(storeQueryCondition.getStorehouseNum() != null){
			sb.append("and (l.in_storehouse_num = :storehouseNum or l.out_storehouse_num = :storehouseNum) ");
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary in (:summaries) ");
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sb.append("and l.pos_item_log_memo = :adjustReason ");
		}
		if(storeQueryCondition.getCenterStorehouse() != null){
			sb.append("and (exists (select 1 from storehouse where l.in_storehouse_num = storehouse.storehouse_num and system_book_code = :systemBookCode and storehouse_del_tag = 0 and storehouse_center_tag = 1 )"
					+ "or exists (select 1 from storehouse where l.out_storehouse_num = storehouse.storehouse_num and system_book_code = :systemBookCode and storehouse_del_tag = 0 and storehouse_center_tag = 1 ) )");
		}
		sb.append("group by l.item_num, l.pos_item_log_item_matrix_num, l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		sqlQuery.setInteger("branchNum", storeQueryCondition.getBranchNum());
		
		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			sqlQuery.setParameterList("itemNums", storeQueryCondition.getItemNums());
		}
		if(storeQueryCondition.getItemCategoryCodes() != null && storeQueryCondition.getItemCategoryCodes().size() > 0){
			sqlQuery.setParameterList("categoryCodes", storeQueryCondition.getItemCategoryCodes());
		}
		if(storeQueryCondition.getStorehouseNum() != null){
			sqlQuery.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sqlQuery.setParameterList("summaries", storeQueryCondition.getPosItemLogSummary().split(","));
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}
		
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> findBranchItemFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum) {
		StringBuffer sb = new StringBuffer();
		String queryYear = DateUtil.getYearString(dateFrom);
		sb.append("select l.branch_num, l.item_num, l.pos_item_log_inout_flag,  ");
		sb.append("sum(l.pos_item_log_item_amount) as amount, sum(l.POS_ITEM_LOG_MONEY) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount,  ");
		sb.append("sum(l.pos_item_log_use_qty) as useAmount, sum(l.pos_item_log_operate_price * l.pos_item_log_item_amount) as saleMoney, ");
		sb.append("min(l.pos_item_log_use_unit) as useUnit ");
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l with(nolock) ");
		} else {
			sb.append("from pos_item_log as l with(nolock) ");
		}
		sb.append("where l.system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		
		if(dateFrom != null){
			sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
			
		}
		
		
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and l.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(storehouseNum != null){
			sb.append("and (l.in_storehouse_num = " + storehouseNum + " or l.out_storehouse_num = " + storehouseNum + ") ");
		}
		if(StringUtils.isNotEmpty(summaries)){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(summaries.split(",")));
		}
		sb.append("group by l.branch_num, l.item_num, l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> findSumByBranchDateItemFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum, List<String> memos) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, pos_item_log_date_index, item_num, pos_item_log_item_matrix_num, pos_item_log_inout_flag, ");
		sb.append("sum(pos_item_log_item_amount) as amount, sum(pos_item_log_money) as money, sum(pos_item_log_item_assist_amount) as assistAmount, ");
		sb.append("sum(pos_item_log_use_qty) as useQty, sum(pos_item_log_operate_price * pos_item_log_item_amount) as saleMoney, ");
		sb.append("min(pos_item_log_use_unit) ");
		String queryYear = DateUtil.getYearString(dateFrom);
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " with(nolock) ");
		} else {
			sb.append("from pos_item_log with(nolock) ");
		}
		
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		
		if(dateFrom != null){
			sb.append("and pos_item_log_date_index >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and pos_item_log_date_index <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
			
		}
		if(StringUtils.isNotEmpty(summaries)){
			sb.append("and pos_item_log_summary in (:summarys) ");
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(storehouseNum !=  null){
			
			sb.append("and (out_storehouse_num = :storehouseNum or in_storehouse_num = :storehouseNum) ");
		}
		if(memos != null && memos.size() > 0){
			
			sb.append("and (pos_item_log_summary != :adjustmentSummary or (pos_item_log_summary = :adjustmentSummary and pos_item_log_memo in " + AppUtil.getStringParmeList(memos) + ")) ");
		}
		
		sb.append("group by branch_num, pos_item_log_date_index, item_num, pos_item_log_item_matrix_num, pos_item_log_inout_flag ");
		Query query = currentSession().createSQLQuery(sb.toString());
		if(StringUtils.isNotEmpty(summaries)){
			query.setParameterList("summarys", summaries.split(","));
		}
		if(storehouseNum !=  null){
			query.setInteger("storehouseNum", storehouseNum);
		}
		if(memos != null && memos.size() > 0){
			query.setString("adjustmentSummary", AppConstants.POS_ITEM_LOG_ADJUSTMENTORDER);
		}
		return query.list();
	}
	
	@Override
	public List<Object[]> findItemLotSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> summaries) {
		StringBuffer sb = new StringBuffer();
		sb.append("select item_num, pos_item_log_lot_number,");
		sb.append("sum(case when pos_item_log_inout_flag = 1 then pos_item_log_item_amount else -pos_item_log_item_amount end) as amount, ");
		sb.append("sum(case when pos_item_log_inout_flag = 1 then pos_item_log_money else -pos_item_log_money end) as money, ");
		sb.append("sum(case when pos_item_log_inout_flag = 1 then pos_item_log_use_qty else -pos_item_log_use_qty end) as useQty ");
		sb.append("from pos_item_log with(nolock) ");
		sb.append("where system_book_code = :systemBookCode and branch_num = :branchNum and pos_item_log_lot_number is not null and pos_item_log_lot_number != '' ");
		sb.append("and pos_item_log_date_index between :dateFrom and :dateTo  ");
		
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(summaries != null && summaries.size() > 0){
			sb.append("and pos_item_log_summary in " + AppUtil.getStringParmeList(summaries));
		}
		sb.append("group by item_num, pos_item_log_lot_number ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));
		query.setString("dateTo", DateUtil.getDateShortStr(dateTo));
		return query.list();
	}
}
