package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.PosItemLogDao;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.query.StoreQueryCondition;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.execchain.TunnelRefusedException;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Repository
public class PosItemLogDaoImpl extends ShardingDaoImpl implements PosItemLogDao {
	@Override
	public List<Integer> findItemNum(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Boolean inOut) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));

		criteria.add(Restrictions.between("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom), DateUtil.getDateShortStr(dateTo)));
		if(inOut != null){
			criteria.add(Restrictions.eq("p.posItemLogInoutFlag", inOut));
		}
		criteria.setProjection(Projections.groupProperty("p.itemNum"));
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}

	@Override
	public List<PosItemLog> findByStoreQueryCondition(StoreQueryCondition storeQueryCondition, int offset, int limit) {
		StringBuffer sb = new StringBuffer();
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		sb.append("select l.* ");
		sb.append("from pos_item_log as l with(nolock) ");
		sb.append("where l.system_book_code = :systemBookCode and l.branch_num = :branchNum ");
		if(StringUtils.isNotEmpty(storeQueryCondition.getRefBillNo())){
			sb.append("and l.pos_item_log_bill_no = :refBillNo ");
		}

		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' and '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

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
	public List<Object[]> findItemPrice(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.posItemLogSummary", AppConstants.POS_ITEM_LOG_RECEIVE_ORDER))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNum != null){
			criteria.add(Restrictions.eq("p.branchNum", branchNum));
		}
		criteria.add(Restrictions.between("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom), DateUtil.getDateShortStr(dateTo)));

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
	public List<Object[]> findSumByItemFlag(String systemBookCode,
											List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums,
											Integer storehouseNum, List<String> memos) {
		StringBuffer sb = new StringBuffer();
		sb.append("select l.item_num, l.pos_item_log_item_matrix_num, l.pos_item_log_inout_flag,  ");
		sb.append("sum(l.pos_item_log_item_amount) as amount, sum(l.POS_ITEM_LOG_MONEY) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount,  ");
		sb.append("sum(l.pos_item_log_use_qty) as useAmount, sum(l.pos_item_log_operate_price * l.pos_item_log_item_amount) as saleMoney, ");
		sb.append("min(l.pos_item_log_use_unit) as useUnit ");
		sb.append("from pos_item_log as l with(nolock) ");

		sb.append("where l.system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");

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
		StringBuffer sb = new StringBuffer();
		sb.append("select l.item_num, l.pos_item_log_item_matrix_num, l.pos_item_log_inout_flag , sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount ");
		sb.append("from pos_item_log as l ");
		sb.append("with(nolock) where l.system_book_code = :systemBookCode ");
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		}
		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' and '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

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
	public List<Object[]> findBranchItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		StringBuffer sb = new StringBuffer();
		sb.append("select l.branch_num, l.item_num, l.pos_item_log_inout_flag,  ");
		sb.append("sum(l.pos_item_log_item_amount) as amount, sum(l.POS_ITEM_LOG_MONEY) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount,  ");
		sb.append("sum(l.pos_item_log_use_qty) as useAmount, ");
		sb.append("min(l.pos_item_log_use_unit) as useUnit ");
		if(storeQueryCondition.getQuerySaleMoney() != null && storeQueryCondition.getQuerySaleMoney()){
			sb.append(", sum(l.pos_item_log_item_amount * p.item_regular_price) as saleMoney ");
		}
		sb.append("from pos_item_log as l ");
		if(storeQueryCondition.getQuerySaleMoney() != null && storeQueryCondition.getQuerySaleMoney()){
			sb.append("inner join pos_item as p on l.item_num = p.item_num ");

		}
		sb.append("where l.system_book_code = '" + storeQueryCondition.getSystemBookCode() + "' ");

		if(storeQueryCondition.getBranchNums() != null && !storeQueryCondition.getBranchNums().isEmpty()){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		}

		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' and '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");
		if(storeQueryCondition.getItemNums() != null && !storeQueryCondition.getItemNums().isEmpty()){
			sb.append("and l.item_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getItemNums()));
		}
		if(storeQueryCondition.getStorehouseNum() != null){
			sb.append("and (l.in_storehouse_num = " + storeQueryCondition.getStorehouseNum() + " or l.out_storehouse_num = " + storeQueryCondition.getStorehouseNum() + ") ");
		}
		if(StringUtils.isNotEmpty(storeQueryCondition.getSummaries())){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(storeQueryCondition.getSummaries().split(",")));
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

		sb.append("from pos_item_log with(nolock) ");

		sb.append("where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){

			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");

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
		sb.append("and pos_item_log_date_index between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");

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
	public List<Object[]> findItemBizTypeFlagSummary(StoreQueryCondition storeQueryCondition) {
		StringBuffer sb = new StringBuffer();
		sb.append("select l.item_num, l.pos_item_log_date_index, l.pos_item_log_summary, l.pos_item_log_inout_flag ,  ");
		sb.append("sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount ");
		sb.append("from pos_item_log as l ");
		sb.append("with(nolock) where l.system_book_code = :systemBookCode ");
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		}
		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' and '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

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
		sb.append("group by l.item_num, l.pos_item_log_date_index, l.pos_item_log_summary, l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());

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
	public List<Object[]> findItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		StringBuffer sb = new StringBuffer();
		sb.append("select l.item_num, l.pos_item_log_inout_flag ,  ");
		sb.append("sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount ");
		sb.append("from pos_item_log as l ");
		sb.append("with(nolock) where l.system_book_code = :systemBookCode ");
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		}
		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' and '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

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
		sb.append("group by l.item_num, l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());

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
	public List<Object[]> findBranchFlagSummary(StoreQueryCondition storeQueryCondition) {
		StringBuffer sb = new StringBuffer();
		sb.append("select l.branch_num, l.pos_item_log_inout_flag ,  ");
		sb.append("sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount ");
		if(storeQueryCondition.getQuerySaleMoney() != null && storeQueryCondition.getQuerySaleMoney()){
			sb.append(", sum(l.pos_item_log_item_amount * p.item_regular_price) as saleMoney ");
		}
		sb.append("from pos_item_log as l ");
		if(((storeQueryCondition.getFilterDeleteItem() != null && storeQueryCondition.getFilterDeleteItem()) && (storeQueryCondition.getPosOrderMoney() == null || storeQueryCondition.getPosOrderMoney().compareTo(BigDecimal.ZERO) == 0 ))
				|| (storeQueryCondition.getQuerySaleMoney() != null && storeQueryCondition.getQuerySaleMoney())){
			sb.append("inner join pos_item as p on l.item_num = p.item_num ");

		}
		sb.append("where l.system_book_code = :systemBookCode ");

		if((storeQueryCondition.getFilterDeleteItem() != null && storeQueryCondition.getFilterDeleteItem()) &&
				(storeQueryCondition.getPosOrderMoney() == null || storeQueryCondition.getPosOrderMoney().compareTo(BigDecimal.ZERO) == 0 )){
			sb.append("and p.item_del_tag = 0 ");
		}
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		}
		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' and '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

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

		sb.append("group by l.branch_num, l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());

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
	public List<Object[]> findItemBizFlagSummary(StoreQueryCondition storeQueryCondition) {
		StringBuffer sb = new StringBuffer();
		sb.append("select l.item_num, l.pos_item_log_inout_flag , l.shift_table_bizday, ");
		sb.append("sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount ");
		sb.append("from pos_item_log as l ");
		sb.append("with(nolock) where l.system_book_code = :systemBookCode ");
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		}
		sb.append("and l.pos_item_log_date_index between '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' and '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

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
		sb.append("group by l.item_num, l.pos_item_log_inout_flag,l.shift_table_bizday ");
		sb.append("order by l.shift_table_bizday desc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());

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

	//以下都是从amazonCenter中移过来的
	@Override
	public List<Object[]> findItemOutAmountSummary(String systemBookCode,
											Integer branchNum, Date dateFrom, Date dateTo,
											List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));

		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateTo)));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("p.itemNum", itemNums));
		}
		criteria.add(Restrictions.eq("p.posItemLogInoutFlag", false));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("p.itemNum"))
				.add(Projections.groupProperty("p.posItemLogItemMatrixNum"))
				.add(Projections.sum("p.posItemLogItemAmount"))
		);
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemOutDateSummary(String systemBookCode,
										  Integer branchNum, Date dateFrom, Date dateTo,
										  List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateTo)));
		}
		if(itemNums != null && itemNums.size() > 0 && itemNums.size() < 2000){
			criteria.add(Restrictions.in("p.itemNum", itemNums));
		}
		criteria.add(Restrictions.eq("p.posItemLogInoutFlag", false));
		List<String> fidTypes = new ArrayList<String>();
		fidTypes.add(AppConstants.POS_ITEM_LOG_OUT_ORDER);
		fidTypes.add(AppConstants.POS_ITEM_LOG_POS);
		fidTypes.add(AppConstants.POS_ITEM_LOG_WHOLESALE_ORDER_ORDER);
		criteria.add(Restrictions.in("posItemLogSummary", fidTypes));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("p.itemNum"))
				.add(Projections.max("p.posItemLogDate"))
		);
		return criteria.list();
	}

	@Override
	public List<Integer> findNoticeItemSummary(String systemBookCode, Integer branchNum, Date dateFrom,
										 Date dateTo, String posItemLogType) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.eq("p.posItemLogSummary", posItemLogType))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDate", DateUtil.getMinOfDate(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDate", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.groupProperty("p.itemNum"));
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemInOutQtyAndMoneySummary(
			StoreQueryCondition storeQueryCondition) {
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		StringBuilder sb = new StringBuilder();
		sb.append("select l.item_num, l.pos_item_log_inout_flag , sum(l.pos_item_log_item_amount) as mount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount, ");
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(storeQueryCondition.getPosItemLogSummary().split(",")));
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sb.append("and l.pos_item_log_memo = :adjustReason ");
		}
		if(storeQueryCondition.getCenterStorehouse() != null){
			sb.append("and (exists (select 1 from storehouse where l.in_storehouse_num = storehouse.storehouse_num and system_book_code = :systemBookCode and storehouse_del_tag = 0 and storehouse_center_tag = 1 )"
					+ "or exists (select 1 from storehouse where l.out_storehouse_num = storehouse.storehouse_num and system_book_code = :systemBookCode and storehouse_del_tag = 0 and storehouse_center_tag = 1 ) )");
		}
		sb.append("group by l.item_num, l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		sqlQuery.setInteger("branchNum", storeQueryCondition.getBranchNum());

		if(storeQueryCondition.getStorehouseNum() != null){
			sqlQuery.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}

		return sqlQuery.list();
	}


	@Override
	public List<Object[]> sumByStoreQueryCondition(
			StoreQueryCondition storeQueryCondition) {
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		StringBuilder sb = new StringBuilder();
		sb.append("select l.pos_item_log_inout_flag , sum(l.pos_item_log_item_amount) as amount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount, count(l.item_num) as totalCount, ");
		sb.append("sum(pos_item_log_adjust_money) as adjustMoney ");

		boolean queryHistory = AppUtil.checkYearTable(queryYear);

		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l ");

		} else {
			sb.append("from pos_item_log as l ");
		}
		sb.append("where l.system_book_code = :systemBookCode and l.branch_num = :branchNum ");
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getRefBillNo())){
			sb.append("and l.pos_item_log_bill_no = :refBillNo ");
		}
		if(org.apache.commons.lang.StringUtils.isEmpty(storeQueryCondition.getRefBillNo())){

			if(storeQueryCondition.getDateStart() != null){
				sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
			}
			if(storeQueryCondition.getDateEnd() != null){
				sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

			}
			if(storeQueryCondition.getExactDateEnd() != null){
				sb.append("and l.pos_item_log_date <= '" + DateUtil.getLongDateTimeStr(storeQueryCondition.getExactDateEnd()) + "' ");
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
				sb.append("and l.pos_item_log_summary = :summary ");
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

			if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
				sb.append("and l.pos_item_log_memo = :adjustReason ");
			}
		}
		sb.append("group by l.pos_item_log_inout_flag ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		sqlQuery.setInteger("branchNum", storeQueryCondition.getBranchNum());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getRefBillNo())){
			sqlQuery.setString("refBillNo", storeQueryCondition.getRefBillNo());

		}
		if(org.apache.commons.lang.StringUtils.isEmpty(storeQueryCondition.getRefBillNo())){

			if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
				sqlQuery.setParameterList("itemNums", storeQueryCondition.getItemNums());
			}
			if(storeQueryCondition.getItemCategoryCodes() != null && storeQueryCondition.getItemCategoryCodes().size() > 0){
				sqlQuery.setParameterList("categoryCodes", storeQueryCondition.getItemCategoryCodes());
			}
			if(storeQueryCondition.getStorehouseNum() != null){
				sqlQuery.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
				sqlQuery.setString("summary", storeQueryCondition.getPosItemLogSummary());
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
				sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
			}
		}

		return sqlQuery.list();
	}


	@Override
	public List<PosItemLog> findLastSummary(String systemBookCode, Integer branchNum,
									 Integer storehouseNum, Date endDate) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode))
				.add(Restrictions.eq("p.branchNum", branchNum));
		if(storehouseNum != null){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("p.inStorehouseNum", storehouseNum))
					.add(Restrictions.eq("p.outStorehouseNum", storehouseNum))
			);
		}
		criteria.add(Restrictions.sqlRestriction("pos_item_log_date in (select max(pos_item_log_date) from pos_item_log where system_book_code = ? and branch_num = ? and pos_item_log_date_index < ? group by item_num)",
				new Object[]{systemBookCode, branchNum, DateUtil.getDateShortStr(endDate)}, new Type[]{StandardBasicTypes.STRING, StandardBasicTypes.INTEGER, StandardBasicTypes.STRING}));
		criteria.addOrder(Order.desc("p.posItemLogDate"));
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemSummaryInOutQtyAndMoney(
			StoreQueryCondition storeQueryCondition) {

		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		StringBuilder sb = new StringBuilder();
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(storeQueryCondition.getPosItemLogSummary().split(",")));
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}

		return sqlQuery.list();
	}


	@Override
	public List<Object[]> findBranchSummaryInOutQtyAndMoney(
			StoreQueryCondition storeQueryCondition) {

		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		StringBuilder sb = new StringBuilder();
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(storeQueryCondition.getPosItemLogSummary().split(",")));
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sb.append("and l.pos_item_log_memo = :adjustReason ");
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getItemDepartment())){
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}

		return sqlQuery.list();
	}


	@Override
	public List<Object[]> findBranchInOutSummary(StoreQueryCondition storeQueryCondition) {

		StringBuilder sb = new StringBuilder();
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary = :summary ");
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sqlQuery.setString("summary", storeQueryCondition.getPosItemLogSummary());
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}
		return sqlQuery.list();
	}


	@Override
	public List<Object[]> findItemAmountBySummary(String systemBookCode,
												  Integer branchNum, Date dateFrom, Date dateTo, String summaries) {
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
	public List<Object[]> findBranchAndItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.systemBookCode", storeQueryCondition.getSystemBookCode()));
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){
			criteria.add(Restrictions.in("p.branchNum", storeQueryCondition.getBranchNums()));
		}
		if(storeQueryCondition.getDateStart() != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(storeQueryCondition.getDateStart())));
		}
		if(storeQueryCondition.getDateEnd() != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(storeQueryCondition.getDateEnd())));
		}
		if(storeQueryCondition.getSummaries() != null){
			criteria.add(Restrictions.in("p.posItemLogSummary", storeQueryCondition.getSummaries().split(",")));
		}
		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			criteria.add(Restrictions.in("p.itemNum", storeQueryCondition.getItemNums()));
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
		StringBuilder sb = new StringBuilder();
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
	public Date getFirstDate(String systemBookCode, Integer branchNum) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(!branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			criteria.add(Restrictions.eq("p.branchNum", branchNum));
		}
		criteria.setProjection(Projections.min("p.posItemLogDate"));
		return ((Date)criteria.uniqueResult());
	}


	@Override
	public List<Object[]> findMoneyBranchFlagSummary(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuilder sb = new StringBuilder();
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
	public List<Object[]> findMoneyBranchItemFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
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
	public List<Object[]> findMinPriceAndDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
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
				.add(Projections.groupProperty("p.posItemLogDate"))
				.add(Projections.min("p.posItemLogItemPrice"))
		);
		criteria.addOrder(Order.asc("p.itemNum")).addOrder(Order.asc("p.posItemLogDate"));
		return criteria.list();
	}


	@Override
	public List<Object[]> findMaxPriceAndDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
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
				.add(Projections.groupProperty("p.posItemLogDate"))
				.add(Projections.max("p.posItemLogItemPrice"))
		);
		criteria.addOrder(Order.asc("p.itemNum")).addOrder(Order.desc("p.posItemLogDate"));
		return criteria.list();
	}


	@Override
	public List<Object[]> findItemDetailSummary(StoreQueryCondition storeQueryCondition) {
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		StringBuilder sb = new StringBuilder();
		sb.append("select l.item_num, l.pos_item_log_summary, pos_item_log_date, ");
		sb.append("l.pos_item_log_operate_price, l.pos_item_log_bill_no, l.pos_item_log_item_amount,l.pos_item_log_inout_flag,l.pos_item_log_item_balance ");

		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l with(nolock) ");
		} else {
			sb.append("from pos_item_log as l with(nolock) ");
		}
		sb.append("where l.system_book_code = :systemBookCode and l.branch_num = :branchNum ");

		if(storeQueryCondition.getDateStart() != null){
			sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
		}
		if(storeQueryCondition.getDateEnd() != null){
			sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

		}

		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			sb.append("and l.item_num in (:itemNums) ");
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getSummaries())){
			sb.append("and pos_item_log_summary in (:summaries) ");
		}
		sb.append("order by l.pos_item_log_item_code asc, l.pos_item_log_date asc ");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		query.setInteger("branchNum", storeQueryCondition.getBranchNum());
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){
			query.setParameterList("itemNums", storeQueryCondition.getBranchNums(), StandardBasicTypes.INTEGER);
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getSummaries())){
			query.setParameterList("summaries", storeQueryCondition.getSummaries().split(","));
		}
		if(storeQueryCondition.getOffset() != null && storeQueryCondition.getLimit() != null){
			query.setFirstResult(storeQueryCondition.getOffset());
			query.setMaxResults(storeQueryCondition.getLimit());
		}
		return query.list();
	}


	@Override
	public List<PosItemLog> findByDate(String systemBookCode, Date dateFrom,
									   Date dateTo) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(dateFrom != null){
			criteria.add(Restrictions.ge("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateFrom)));
		}
		if(dateTo != null){
			criteria.add(Restrictions.le("p.posItemLogDateIndex", DateUtil.getDateShortStr(dateTo)));
		}
		criteria.addOrder(Order.asc("p.posItemLogDate"));
		return criteria.list();
	}


	@Override
	public List<PosItemLog> findUnUploadSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset, int limit) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode))
				.add(Restrictions.eq("p.branchNum", branchNum))
				.add(Restrictions.between("p.posItemLogDate", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)))
				.add(Restrictions.eq("p.posItemLogTransferFlag", false));
		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);
		criteria.addOrder(Order.asc("p.posItemLogDate"));
		return criteria.list();
	}


	@Override
	public List<PosItemLog> findRepeatAuditOrderSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select system_book.system_book_code,system_book.book_name, branch.branch_num, branch.branch_name, ");
		sb.append("item_num, pos_item_log_item_matrix_num, pos_item_log_summary,pos_item_log_bill_no, pos_item_log_lot_number, count(pos_item_log.pos_item_log_fid) as repeat_count ");
		sb.append("from pos_item_log with(nolock) inner join branch with(nolock) on pos_item_log.system_book_code = branch.system_book_code and pos_item_log.branch_num = branch.branch_num ");
		sb.append("inner join system_book with(nolock) on system_book.system_book_code = branch.system_book_code ");
		sb.append("where pos_item_log_summary not in('前台销售', '转仓单', '前台销售反结账') ");
		if(dateFrom != null){
			sb.append("and pos_item_log_date_index >= :dateFrom ");
		}
		if(dateTo != null){
			sb.append("and pos_item_log_date_index <= :dateTo ");
		}
		sb.append("and pos_item_log_bill_detail_num > 0 ");
		sb.append("and (pos_item_log_memo is null or pos_item_log_memo not like '%组合商品%') ");
		sb.append("and (pos_item_log_memo is null or pos_item_log_memo != '万商壹站退货单') ");
		sb.append("and (pos_item_log_memo is null or pos_item_log_memo != '日采收货单调整金额') ");
		sb.append("and (pos_item_log_memo is null or pos_item_log_memo not like '%成分商品%') and len(pos_item_log_bill_no) != 32 ");
		sb.append("group by system_book.system_book_code,system_book.book_name, branch.branch_num, branch.branch_name, ");
		sb.append("item_num, pos_item_log_item_matrix_num, pos_item_log_summary, pos_item_log_bill_no, pos_item_log_lot_number ");
		sb.append("having count(*) > 1 order by system_book.system_book_code, pos_item_log_bill_no ");
		Query query = currentSession().createSQLQuery(sb.toString());
		if(dateFrom != null){
			query.setString("dateFrom", DateUtil.getDateShortStr(dateFrom));

		}
		if(dateTo != null){
			query.setString("dateTo", DateUtil.getDateShortStr(dateTo));

		}
		List<Object[]> objects = query.list();
		List<PosItemLog> posItemLogs = new ArrayList<PosItemLog>();
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			PosItemLog posItemLog = new PosItemLog();
			posItemLog.setSystemBookCode((String)object[0]);
			posItemLog.setSystemBookName(posItemLog.getSystemBookCode() + "|" + (String)object[1]);
			posItemLog.setBranchNum((Integer)object[2]);
			posItemLog.setBranchName(posItemLog.getBranchNum() + "|" + (String)object[3]);
			posItemLog.setItemNum((Integer)object[4]);
			posItemLog.setPosItemLogItemMatrixNum((Integer)object[5]);
			posItemLog.setPosItemLogItemCode((String)object[6]);
			posItemLog.setPosItemLogItemName((String)object[7]);
			posItemLog.setPosItemLogSummary((String)object[8]);
			posItemLog.setPosItemLogBillNo((String)object[9]);
			posItemLog.setPosItemLogLotNumber(object[10] == null?"":(String)object[10]);
			posItemLog.setRepeatCount((Integer)object[11]);
			posItemLogs.add(posItemLog);
		}
		return posItemLogs;
	}


	//该接口仅供计算库存汇总数据用
	@Override
	public List<Object[]> findSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {

		String queryYear = DateUtil.getYearString(dateFrom);
		StringBuilder sb = new StringBuilder();
		sb.append("select in_storehouse_num, out_storehouse_num, item_num, pos_item_log_summary, pos_item_log_inout_flag, pos_item_log_item_matrix_num, ");
		sb.append("sum(pos_item_log_item_amount) as amount, sum(pos_item_log_item_assist_amount) as assistAmount, sum(pos_item_log_money) as money ");

		boolean queryHistory = AppUtil.checkYearTable(queryYear);

		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " ");

		} else {
			sb.append("from pos_item_log ");
		}
		sb.append("with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNum != null){

			sb.append("and branch_num = " + branchNum + " ");
		}

		if(dateFrom != null){
			sb.append("and pos_item_log_date_index >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and pos_item_log_date_index <= '" + DateUtil.getDateShortStr(dateTo) + "' ");

		}

		sb.append("group by in_storehouse_num, out_storehouse_num, item_num, pos_item_log_summary, pos_item_log_inout_flag, pos_item_log_item_matrix_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}


	@Override
	public int countUnUpload(String systemBookCode, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(PosItemLog.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode))
				.add(Restrictions.between("p.posItemLogDate", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)))
				.add(Restrictions.eq("p.posItemLogTransferFlag", false));
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}


	@Override
	public List<Object[]> findBranchItemFlagMemoSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
														Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum) {

		StringBuilder sb = new StringBuilder();
		String queryYear = DateUtil.getYearString(dateFrom);
		sb.append("select l.branch_num, l.item_num, l.pos_item_log_inout_flag, l.pos_item_log_memo,  ");
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(summaries)){
			sb.append("and l.pos_item_log_summary in " + AppUtil.getStringParmeArray(summaries.split(",")));
		}
		sb.append("group by l.branch_num, l.item_num, l.pos_item_log_inout_flag, l.pos_item_log_memo ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}

	@Override
	public List<Object[]> findBranchFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo, String posItemLogSummarys,Boolean itemDelTag) {
		StringBuilder sb = new StringBuilder();
		sb.append("select l.branch_num, l.pos_item_log_inout_flag, ");
		sb.append("sum(l.pos_item_log_item_amount) as amount, sum(l.pos_item_log_money) as money, sum(l.pos_item_log_item_assist_amount) as assistAmount ");
		sb.append("from pos_item_log as l inner join pos_item as p on l.item_num = p.item_num ");
		sb.append("with(nolock) where l.system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and l.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(posItemLogSummarys)){
			sb.append("and l.pos_item_log_summary in (:summarys) ");
		}
		if(itemDelTag !=null && itemDelTag.equals(true)){
			sb.append("and p.item_del_tag = 0");
		}
		sb.append("group by l.branch_num, l.pos_item_log_inout_flag ");
		Query query = currentSession().createSQLQuery(sb.toString());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(posItemLogSummarys)){
			query.setParameterList("summarys", posItemLogSummarys.split(","));
		}
		return query.list();
	}

	@Override
	public List<Object[]> findItemInOutSummary(StoreQueryCondition storeQueryCondition) {
		StringBuilder sb = new StringBuilder();
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		sb.append("select l.item_num, l.pos_item_log_inout_flag, sum(l.pos_item_log_item_amount) as amount, ");
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
		if(storeQueryCondition.getExactDateFrom() != null){
			sb.append("and l.pos_item_log_date >= '" + DateUtil.getLongDateTimeStr(storeQueryCondition.getExactDateFrom()) + "' ");
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
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sb.append("and l.pos_item_log_summary = :summary ");
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sb.append("and l.pos_item_log_memo = :adjustReason ");
		}
		sb.append("group by l.item_num, l.pos_item_log_inout_flag order by l.item_num asc ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", storeQueryCondition.getSystemBookCode());
		if(storeQueryCondition.getStorehouseNum() != null){
			sqlQuery.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getPosItemLogSummary())){
			sqlQuery.setString("summary", storeQueryCondition.getPosItemLogSummary());
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getAdjustReason())){
			sqlQuery.setString("adjustReason", storeQueryCondition.getAdjustReason());
		}
		return sqlQuery.list();
	}


	@Override
	public List<PosItemLog> findByBillNoSummary(String systemBookCode, String posItemLogBillNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from pos_item_log with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and pos_item_log_bill_no = '" + posItemLogBillNo + "' ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(PosItemLog.class);
		return query.list();
	}


	@Override
	public int countByBillNo(String systemBookCode, String posItemLogBillNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from pos_item_log with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and pos_item_log_bill_no = '" + posItemLogBillNo + "' ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		Object object = query.uniqueResult();
		return object == null?0:(Integer)object;
	}


	@Override
	public List<Object[]> findDateItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		StringBuilder sb = new StringBuilder();
		sb.append("select pos_item_log_date_index, item_num, pos_item_log_item_matrix_num, pos_item_log_inout_flag, ");
		sb.append("sum(pos_item_log_item_amount) as amount, sum(pos_item_log_money) as money, sum(pos_item_log_item_assist_amount) as assistAmount, ");
		sb.append("sum(pos_item_log_use_qty) as useQty, sum(pos_item_log_operate_price * pos_item_log_item_amount) as saleMoney, ");
		sb.append("min(pos_item_log_use_unit) ");
		sb.append("from pos_item_log ");
		sb.append("with(nolock) where system_book_code = '" + storeQueryCondition.getSystemBookCode() + "' ");
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){

			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		}
		if(storeQueryCondition.getDateStart() != null){
			sb.append("and pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
		}
		if(storeQueryCondition.getDateEnd() != null){
			sb.append("and pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getSummaries())){
			sb.append("and pos_item_log_summary in (:summarys) ");
		}
		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getItemNums()));
		}
		if(storeQueryCondition.getStorehouseNum() !=  null){

			sb.append("and (out_storehouse_num = :storehouseNum or in_storehouse_num = :storehouseNum) ");
		}
		if(storeQueryCondition.getMemos() != null && storeQueryCondition.getMemos().size() > 0){

			sb.append("and (pos_item_log_summary != :adjustmentSummary or (pos_item_log_summary = :adjustmentSummary and pos_item_log_memo in " + AppUtil.getStringParmeList(storeQueryCondition.getMemos()) + ")) ");
		}

		sb.append("group by pos_item_log_date_index, item_num, pos_item_log_item_matrix_num, pos_item_log_inout_flag ");
		Query query = currentSession().createSQLQuery(sb.toString());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getSummaries())){
			query.setParameterList("summarys", storeQueryCondition.getSummaries().split(","));
		}
		if(storeQueryCondition.getStorehouseNum() !=  null){
			query.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
		}
		if(storeQueryCondition.getMemos() != null && storeQueryCondition.getMemos().size() > 0){
			query.setString("adjustmentSummary", AppConstants.POS_ITEM_LOG_ADJUSTMENTORDER);
		}
		return query.list();
	}

	@Override
	public List<Integer> findItemNumSummary(String systemBookCode, Integer branchNum, Integer storehouseNum, Date dateFrom,
									  Date dateTo) {
		String queryYear = DateUtil.getYearString(dateFrom);
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct l.item_num ");
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " as l with(nolock) ");
		} else {
			sb.append("from pos_item_log as l with(nolock) ");
		}
		sb.append("where l.system_book_code = :systemBookCode and l.branch_num = :branchNum ");

		if(dateFrom != null){
			sb.append("and l.pos_item_log_date_index >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if(dateTo != null){
			sb.append("and l.pos_item_log_date_index <= '" + DateUtil.getDateShortStr(dateTo) + "' ");

		}

		if(storehouseNum != null){
			sb.append("and (l.out_storehouse_num = :storehouseNum or l.in_storehouse_num = :storehouseNum) ");
		}

		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		if(storehouseNum != null){
			query.setInteger("storehouseNum", storehouseNum);
		}
		return query.list();
	}


	@Override
	public boolean checkExists(String posItemLogBillNo, Integer posItemLogBillDetailNum, Integer posItemLogSerialNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(pos_item_log_bill_no) from pos_item_log with(nolock) where pos_item_log_bill_no = '" + posItemLogBillNo + "' ");
		sb.append("and pos_item_log_bill_detail_num = " + posItemLogBillDetailNum + " and pos_item_log_serial_number = '" + posItemLogSerialNumber + "' ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		Object object = query.uniqueResult();
		Integer count = object == null?0:(Integer)object;
		return count > 0;
	}


	@Override
	public List<Object[]> findBranchItemMatrixFlagSummary(StoreQueryCondition storeQueryCondition) {
		StringBuilder sb = new StringBuilder();
		sb.append("select branch_num, item_num, pos_item_log_item_matrix_num, pos_item_log_inout_flag, ");
		sb.append("sum(pos_item_log_item_amount) as amount, sum(pos_item_log_money) as money, sum(pos_item_log_item_assist_amount) as assistAmount, ");
		sb.append("sum(pos_item_log_use_qty) as useQty, sum(pos_item_log_operate_price * pos_item_log_item_amount) as saleMoney, ");
		sb.append("min(pos_item_log_use_unit) ");
		String queryYear = DateUtil.getYearString(storeQueryCondition.getDateStart());
		boolean queryHistory = AppUtil.checkYearTable(queryYear);
		if(queryHistory){
			sb.append("from pos_item_log_" + queryYear + " with(nolock) ");
		} else {
			sb.append("from pos_item_log with(nolock) ");
		}

		sb.append("where system_book_code = '" + storeQueryCondition.getSystemBookCode() + "' ");
		if(storeQueryCondition.getBranchNums() != null && storeQueryCondition.getBranchNums().size() > 0){

			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getBranchNums()));
		}

		if(storeQueryCondition.getDateStart() != null){
			sb.append("and pos_item_log_date_index >= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateStart()) + "' ");
		}
		if(storeQueryCondition.getDateEnd() != null){
			sb.append("and pos_item_log_date_index <= '" + DateUtil.getDateShortStr(storeQueryCondition.getDateEnd()) + "' ");

		}

		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getSummaries())){
			sb.append("and pos_item_log_summary in (:summarys) ");
		}
		if(storeQueryCondition.getItemNums() != null && storeQueryCondition.getItemNums().size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(storeQueryCondition.getItemNums()));
		}
		if(storeQueryCondition.getStorehouseNum() !=  null){

			sb.append("and (out_storehouse_num = :storehouseNum or in_storehouse_num = :storehouseNum) ");
		}
		if(storeQueryCondition.getMemos() != null && storeQueryCondition.getMemos().size() > 0){

			sb.append("and (pos_item_log_summary != :adjustmentSummary or (pos_item_log_summary = :adjustmentSummary and pos_item_log_memo in " + AppUtil.getStringParmeList(storeQueryCondition.getMemos()) + ")) ");
		}

		sb.append("group by branch_num, item_num, pos_item_log_item_matrix_num, pos_item_log_inout_flag  ");
		Query query = currentSession().createSQLQuery(sb.toString());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(storeQueryCondition.getSummaries())){
			query.setParameterList("summarys", storeQueryCondition.getSummaries().split(","));
		}
		if(storeQueryCondition.getStorehouseNum() !=  null){
			query.setInteger("storehouseNum", storeQueryCondition.getStorehouseNum());
		}
		if(storeQueryCondition.getMemos() != null && storeQueryCondition.getMemos().size() > 0){
			query.setString("adjustmentSummary", AppConstants.POS_ITEM_LOG_ADJUSTMENTORDER);
		}
		return query.list();
	}


	@Override
	public PosItemLog read(String posItemLogBillNo, Integer posItemLogBillDetailNum) {
		String sql = "select * from pos_item_log with(nolock) where pos_item_log_bill_no = '"
				+ posItemLogBillNo + "' and pos_item_log_bill_detail_num = " + posItemLogBillDetailNum;
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(PosItemLog.class);
		query.setMaxResults(1);
		return (PosItemLog) query.uniqueResult();
	}








}
