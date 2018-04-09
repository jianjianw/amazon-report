package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.InventoryDao;
import com.nhsoft.module.report.model.Inventory;
import com.nhsoft.module.report.model.InventoryLnDetail;
import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.Storehouse;
import com.nhsoft.module.report.util.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class InventoryDaoImpl extends DaoImpl implements InventoryDao {

	@Override
	public List<Object[]> findItemAmountByStorehouse(String systemBookCode, Integer branchNum, List<Integer> itemNums, List<Integer> storehouseNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select i.item_num, sum(i.inventory_amount) as amount, sum(i.inventory_money) as money, sum(i.inventory_assist_amount) as assistAmount ");
		sb.append("from inventory as i with(nolock) inner join storehouse as s with(nolock) on s.storehouse_num = i.storehouse_num ");
		sb.append("where s.storehouse_del_tag = 0 and s.storehouse_actived = 1 ");
		if(storehouseNums != null && storehouseNums.size()>0){
			sb.append("and s.storehouse_num in " + AppUtil.getIntegerParmeList(storehouseNums));
		}else{
			sb.append("and s.storehouse_num in (select storehouse_num from branch_storehouse with(nolock) where system_book_code = '" + systemBookCode + "' ");
			sb.append("and branch_num = " + branchNum + ") ");
		}
		if (itemNums != null && !itemNums.isEmpty()) {
			sb.append("and i.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by i.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, Integer storehouseNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("select i.item_num, sum(i.inventory_amount) as amount, sum(i.inventory_money) as money, sum(i.inventory_assist_amount) as assistAmount ");
		sb.append("from inventory as i with(nolock) inner join storehouse as s with(nolock) on s.storehouse_num = i.storehouse_num ");
		sb.append("where s.storehouse_del_tag = 0 and s.storehouse_actived = 1 ");
		sb.append("and s.storehouse_num in (select storehouse_num from branch_storehouse with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && !branchNums.isEmpty()) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append(") ");
		if (itemNums != null && !itemNums.isEmpty()) {
			sb.append("and i.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(storehouseNum != null){
			sb.append("and i.storehouse_num = " +storehouseNum + " ");

		}
		sb.append("group by i.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}



	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Integer storehouseNum, List<Integer> itemNums) {

		StringBuffer sb = new StringBuffer();
		sb.append("select bs.branch_num, i.item_num, sum(i.inventory_amount) as amount, sum(i.inventory_money) as money ");
		sb.append("from inventory as i with(nolock) inner join storehouse as s with(nolock) on s.storehouse_num = i.storehouse_num ");
		sb.append("inner join branch_storehouse as bs with(nolock) on bs.storehouse_num = s.storehouse_num ");
		sb.append("where s.storehouse_del_tag = 0 and s.storehouse_actived = 1 ");
		sb.append("and bs.system_book_code = '" + systemBookCode + "' ");
		if(storehouseNum != null){
			sb.append("and i.storehouse_num = " + storehouseNum + " ");
		}
		if(branchNums != null && !branchNums.isEmpty()) {
			sb.append("and bs.branch_num in " + AppUtil.getIntegerParmeList(branchNums));

		}
		if (itemNums != null && !itemNums.isEmpty()) {
			sb.append("and i.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by bs.branch_num, i.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}


	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, String itemCategoryCodes,
											List<Integer> itemNums, Integer storehouseNum) {

		StringBuffer sb = new StringBuffer();
		sb.append("select bs.branch_num, sum(i.inventory_amount) as amount, sum(i.inventory_money) as money, ");
		sb.append("sum(i.inventory_amount * item.item_regular_price) as regularMoney, sum(i.inventory_assist_amount) assistAmount ");
		sb.append("from inventory as i with(nolock) inner join storehouse as s with(nolock) on s.storehouse_num = i.storehouse_num ");
		sb.append("inner join branch_storehouse as bs with(nolock) on bs.storehouse_num = s.storehouse_num ");
		sb.append("inner join pos_item as item with(nolock) on item.item_num = i.item_num ");
		sb.append("where s.storehouse_del_tag = 0 and s.storehouse_actived = 1 ");
		sb.append("and bs.system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && !branchNums.isEmpty()) {
			sb.append("and bs.branch_num in " + AppUtil.getIntegerParmeList(branchNums));

		}
		if(storehouseNum != null){
			sb.append("and i.storehouse_num = " + storehouseNum + " ");
		}
		if (itemNums != null && !itemNums.isEmpty()) {
			sb.append("and item.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (StringUtils.isNotEmpty(itemCategoryCodes)) {
			sb.append("and item.item_category_code in " + AppUtil.getStringParmeArray(itemCategoryCodes.split(",")));
		}
		sb.append("group by bs.branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}


	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bs.branch_num, sum(i.inventory_amount) as amount, sum(i.inventory_money) as money, ");
		sb.append("sum(i.inventory_assist_amount) assistAmount ");
		sb.append("from inventory as i with(nolock) inner join storehouse as s with(nolock) on s.storehouse_num = i.storehouse_num ");
		sb.append("inner join branch_storehouse as bs with(nolock) on bs.storehouse_num = s.storehouse_num ");
		sb.append("where s.storehouse_del_tag = 0 and s.storehouse_actived = 1 ");
		sb.append("and bs.system_book_code = '" + systemBookCode + "' ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and bs.branch_num in " + AppUtil.getIntegerParmeList(branchNums));

		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and i.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by bs.branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<Object[]> findInventory(String systemBookCode, Integer branchNum, Integer storehouseNum,
										List<String> itemCategorys) {
		Criteria criteria = createCriteria(systemBookCode, branchNum, storehouseNum, itemCategorys);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("i.itemNum"))
				.add(Projections.sum("i.inventoryAmount")).add(Projections.sum("i.inventoryMoney"))
				.add(Projections.sum("i.inventoryAssistAmount")));
		return criteria.list();
	}


	@Override
	public List<Object[]> findCenterStore(String systemBookCode, Integer branchNum, List<Integer> itemNums) {

		StringBuffer sb = new StringBuffer();
		sb.append("select i.item_num, sum(i.inventory_amount) as amount, sum(i.inventory_money) as money, sum(i.inventory_assist_amount) as assistAmount ");
		sb.append("from inventory as i with(nolock) inner join storehouse as s with(nolock) on s.storehouse_num = i.storehouse_num ");
		sb.append("where s.storehouse_del_tag = 0 and s.storehouse_actived = 1 and s.storehouse_center_tag = 1 ");
		sb.append("and s.storehouse_num in (select storehouse_num from branch_storehouse with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNum != null){
			sb.append("and branch_num = " + branchNum + " ");

		}
		sb.append(") ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and i.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by i.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}


	@Override
	public List<Inventory> findByItemAndBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums,
											   Boolean centerFlag) {
		StringBuffer sb = new StringBuffer();
		sb.append("select i.* from inventory as i with(nolock) inner join storehouse as s with(nolock) on s.storehouse_num = i.storehouse_num ");
		if (branchNum != null) {
			sb.append("inner join branch_storehouse as bs with(nolock) on bs.storehouse_num = s.storehouse_num ");
		}
		sb.append("where s.system_book_code = :systemBookCode and s.storehouse_del_tag = 0 and s.storehouse_actived = 1 ");
		if (centerFlag != null) {
			sb.append("and s.storehouse_center_tag = 1 ");
		}
		if (branchNum != null) {
			sb.append("and bs.branch_num = :branchNum ");
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and i.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}

		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (branchNum != null) {
			query.setInteger("branchNum", branchNum);
		}
		query.addEntity("i", Inventory.class);
		return query.list();
	}


	@Override
	public List<InventoryLnDetail> findExpireLns(String systemBookCode, Integer branchNum, Integer storehouseNum,
												 List<String> categoryCodes, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select ln.* from inventory_ln_detail as ln with(nolock) inner join pos_item as p  with(nolock) on ln.item_num = p.item_num ");
		sb.append("where ");
		if (storehouseNum != null) {
			sb.append("storehouse_num = :storehouseNum ");
		} else {
			sb.append("exists (select 1 from branch_storehouse as bs with(nolock) inner join storehouse as s with(nolock) on s.storehouse_num = bs.storehouse_num ");
			sb.append("where bs.system_book_code = :systemBookCode and bs.branch_num = :branchNum and s.storehouse_del_tag = 0 ");
			sb.append("and bs.storehouse_num = ln.storehouse_num )");
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and ln.item_num in (:itemNums) ");
		}
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and p.item_category_code in (:categoryCodes) ");
		}
		sb.append("and p.item_valid_period is not null and p.item_remind_period is not null ");
		sb.append("and ln.inventory_ln_detail_amount > 0 and ln.inventory_ln_detail_producing_date is not null ");
		sb.append("and ln.inventory_ln_detail_producing_date > '1900-01-01 00:00:00' ");
		sb.append("and (p.item_valid_period - (DATEDIFF(day, CONVERT(varchar(100), ln.inventory_ln_detail_producing_date, 20), getdate()))) <= p.item_remind_period ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		if (storehouseNum != null) {
			query.setInteger("storehouseNum", storehouseNum);
		} else {
			query.setString("systemBookCode", systemBookCode);
			query.setInteger("branchNum", branchNum);
		}
		if (itemNums != null && itemNums.size() > 0) {
			query.setParameterList("itemNums", itemNums);
		}
		if (categoryCodes != null && categoryCodes.size() > 0) {
			query.setParameterList("categoryCodes", categoryCodes);
		}
		query.addEntity("ln", InventoryLnDetail.class);
		return query.list();
	}

	@Override
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select i.item_num, sum(i.inventory_amount) as amount, sum(i.inventory_money) as money from branch_storehouse as bs with(nolock) ");
		sb.append("left join inventory as i with(nolock) on bs.storehouse_num = i.storehouse_num ");
		sb.append("inner join storehouse as s with(nolock) on s.storehouse_num = i.storehouse_num ");
		sb.append("where bs.system_book_code = :systemBookCode  ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and i.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and s.storehouse_del_tag = 0 ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and bs.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("group by i.item_num ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);

		return sqlQuery.list();
	}


	@Override
	public List<InventoryLnDetail> findInventoryLnDetails(String systemBookCode, Integer branchNum,
														  List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(InventoryLnDetail.class, "i");
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Storehouse.class, "s").createAlias("s.branchs", "b")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode))
				.add(Restrictions.eq("b.id.branchNum", branchNum)).add(Restrictions.eq("s.storehouseDelTag", false))
				.add(Restrictions.eq("s.storehouseActived", true))
				.add(Restrictions.eqProperty("s.storehouseNum", "i.id.storehouseNum"));
		criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("s.storehouseNum"))));
		if (itemNums != null && itemNums.size() > 0) {
			criteria.add(Restrictions.in("i.itemNum", itemNums));
		}
		return criteria.list();
	}

	private Criteria createCriteria(String systemBookCode, Integer branchNum, Integer storeHouse, List<String> categorys) {
		Criteria criteria = currentSession()
				.createCriteria(Inventory.class, "i")
				.add(Restrictions
						.sqlRestriction(
								"storehouse_num in (select storehouse_num from branch_storehouse where system_book_code = ? and branch_num = ?)",
								new Object[] { systemBookCode, branchNum }, new Type[] { StandardBasicTypes.STRING,
										StandardBasicTypes.INTEGER }));
		if (storeHouse != null) {
			criteria.add(Restrictions.eq("i.id.storehouseNum", storeHouse));
		}
		if ((categorys != null && categorys.size() > 0)) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(PosItem.class, "p").add(
					Restrictions.eq("p.systemBookCode", systemBookCode));
			subCriteria.add(Restrictions.in("p.itemCategoryCode", categorys));
			subCriteria.add(Property.forName("p.itemNum").eqProperty("i.itemNum"));
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("p.itemNum"))));
		}
		return criteria;
	}


	@Override
	public List<Object[]> findInventoryLostDayCount(String systemBookCode, Integer branchNum, List<Integer> itemNums) {

		StringBuilder sb = new StringBuilder();
		sb.append("select item_num,count() from inventory as i");
		sb.append("inner join branch_storehouse as bs on i.storehouse_num = bs.storehouse_num ");
		sb.append("where inventory_amount = 0 ");
		sb.append("and systemBookCode = :systemBookCode ");
		if (branchNum != null) {
			sb.append("and bs.branch_num = :branchNum ");
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and i.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if (branchNum != null) {
			query.setInteger("branchNum", branchNum);
		}
		return query.list();
	}

	@Override
	public List<Object[]> findInventoryLostCount(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
		return null;
	}


}