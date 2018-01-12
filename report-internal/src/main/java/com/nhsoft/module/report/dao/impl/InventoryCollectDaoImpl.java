package com.nhsoft.module.report.dao.impl;



import com.nhsoft.module.report.dao.InventoryCollectDao;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@SuppressWarnings("deprecation")
public class InventoryCollectDaoImpl extends DaoImpl implements InventoryCollectDao {

	@Override
	public List<Object[]> findSummaryByItemFlag(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
												boolean centerStorehouse) {
		StringBuffer sb = new StringBuffer();
		sb.append("select i.itemNum, i.collectDetailInoutFlag, sum(i.collectDetailAmount), sum(i.collectDetailMoney), sum(i.collectDetailAssistAmount)  ");
		sb.append("from InventoryCollectDetail as i ");
		sb.append("where i.systemBookCode = :systemBookCode ");
		if(branchNum != null){
			sb.append("and i.branchNum = :branchNum ");

		}
		if(dateFrom != null){
			sb.append("and i.collectDetailDate >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and i.collectDetailDate <= :dateTo ");
		}
		if(centerStorehouse){
			sb.append("and exists (select 1 from Storehouse as s where i.storehouseNum = s.storehouseNum and s.systemBookCode = :systemBookCode and s.storehouseDelTag = 0 and s.storehouseCenterTag = 1 )");
		}
		sb.append("group by i.itemNum, i.collectDetailInoutFlag ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(branchNum != null){
			query.setInteger("branchNum", branchNum);
		}
		if(dateFrom != null){
			query.setString("dateFrom", DateUtil.getDateTimeString(DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			query.setString("dateTo", DateUtil.getDateTimeString(DateUtil.getMaxOfDate(dateTo)));
		}
		return query.list();
	}


	@Override
	public List<Object[]> findSummaryByItemMatrixFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													  Date dateTo, String summary, List<Integer> itemNums, Integer storehouseNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("select i.itemNum, i.collectDetailItemMatrixNum, i.collectDetailInoutFlag, sum(i.collectDetailAmount), sum(i.collectDetailMoney), sum(i.collectDetailAssistAmount)  ");
		sb.append("from InventoryCollectDetail as i ");
		sb.append("where i.systemBookCode = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and i.branchNum in (:branchNums) ");

		}
		if(dateFrom != null){
			sb.append("and i.collectDetailDate >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and i.collectDetailDate <= :dateTo ");
		}
		if(storehouseNum != null){
			sb.append("and i.storehouseNum = :storehouseNum ");
		}
		if(StringUtils.isNotEmpty(summary)){
			sb.append("and i.collectDetailSummary in (:summarys) ");
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and i.itemNum in (:itemNums) ");
		}
		sb.append("group by i.itemNum, i.collectDetailItemMatrixNum, i.collectDetailInoutFlag ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(branchNums != null && branchNums.size() > 0){
			query.setParameterList("branchNums", branchNums);
		}
		if(dateFrom != null){
			query.setString("dateFrom", DateUtil.getDateTimeString(DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			query.setString("dateTo", DateUtil.getDateTimeString(DateUtil.getMaxOfDate(dateTo)));
		}
		if(storehouseNum != null){
			query.setInteger("storehouseNum", storehouseNum);
		}
		if(StringUtils.isNotEmpty(summary)){
			query.setParameterList("summarys", summary.split(","));
		}
		if(itemNums != null && itemNums.size() > 0){
			query.setParameterList("itemNums", itemNums);
		}
		return query.list();
	}



	@Override
	public List<Object[]> findItemLatestDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
											 List<Integer> itemNums, String collectDetailSummary) {

		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);
		boolean queryPosItemLog = false;
		if(dateTo == null){
			dateTo = now;
		}
		if(dateTo.compareTo(now) >= 0){
			queryPosItemLog = true;
		}
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotEmpty(collectDetailSummary)){

			if(!queryPosItemLog){
				sb.append("select item_num, max(collect_detail_date) from inventory_collect_detail with(nolock) ");
				sb.append("where system_book_code = :systemBookCode ");
				if(branchNums != null && branchNums.size() > 0){
					sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
				}
				if(itemNums != null && itemNums.size() > 0){
					sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
				}
				if(dateFrom != null){
					sb.append("and collect_detail_date >= :dateFrom ");
				}
				sb.append("and collect_detail_date <= :dateTo ");
				sb.append("and collect_detail_summary in (:summaries) ");
				sb.append("group by item_num");
			} else {
				sb.append("select a.item_num, max(a.logDate) from ");
				sb.append("((select item_num, collect_detail_date as logDate from inventory_collect_detail with(nolock) ");
				sb.append("where system_book_code = :systemBookCode ");
				if(branchNums != null && branchNums.size() > 0){
					sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
				}
				if(itemNums != null && itemNums.size() > 0){
					sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
				}
				if(dateFrom != null){
					sb.append("and collect_detail_date >= :dateFrom ");
				}
				sb.append("and collect_detail_date < '" + DateUtil.getLongDateTimeStr(now) + "' ");
				sb.append("and collect_detail_summary in (:summaries)) ");

				sb.append("union ");

				sb.append("(select item_num, pos_item_log_date as logDate from pos_item_log with(nolock) ");
				sb.append("where system_book_code = :systemBookCode ");
				if(branchNums != null && branchNums.size() > 0){
					sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
				}
				if(itemNums != null && itemNums.size() > 0){
					sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
				}
				sb.append("and pos_item_log_date >= '" + DateUtil.getLongDateTimeStr(now) + "' ");
				sb.append("and pos_item_log_date <= :dateTo ");
				sb.append("and pos_item_log_summary in (:summaries)) ) as a ");
				sb.append("group by a.item_num ");

			}

		} else {

			if(!queryPosItemLog){
				sb.append("select item_num, max(inventory_collect_date) from inventory_collect with(nolock) ");
				sb.append("where system_book_code = :systemBookCode ");
				if(branchNums != null && branchNums.size() > 0){
					sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
				}
				if(itemNums != null && itemNums.size() > 0){
					sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
				}
				if(dateFrom != null){
					sb.append("and inventory_collect_date >= :dateFrom ");
				}
				sb.append("and inventory_collect_date <= :dateTo ");
				sb.append("group by item_num");
			} else {
				sb.append("select a.item_num, max(a.logDate) from ");
				sb.append("((select item_num, inventory_collect_date as logDate from inventory_collect with(nolock) ");
				sb.append("where system_book_code = :systemBookCode ");
				if(branchNums != null && branchNums.size() > 0){
					sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
				}
				if(itemNums != null && itemNums.size() > 0){
					sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
				}
				if(dateFrom != null){
					sb.append("and inventory_collect_date >= :dateFrom ");
				}
				sb.append("and inventory_collect_date < '" + DateUtil.getLongDateTimeStr(now) + "' ");
				sb.append("and collect_detail_summary in (:summaries)) ");

				sb.append("union ");

				sb.append("(select item_num, pos_item_log_date as logDate from pos_item_log with(nolock) ");
				sb.append("where system_book_code = :systemBookCode ");
				if(branchNums != null && branchNums.size() > 0){
					sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
				}
				if(itemNums != null && itemNums.size() > 0){
					sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
				}
				sb.append("and pos_item_log_date >= '" + DateUtil.getLongDateTimeStr(now) + "' ");
				if(dateTo != null){
					sb.append("and pos_item_log_date <= :dateTo ");
				}
				sb.append("group by a.item_num ");

			}
		}

		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		if(StringUtils.isNotEmpty(collectDetailSummary)){
			sqlQuery.setParameterList("summaries", collectDetailSummary.split(","));
		}
		if(dateFrom != null){
			sqlQuery.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		}
		if(dateTo != null){
			sqlQuery.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		}
		return sqlQuery.list();
	}

}