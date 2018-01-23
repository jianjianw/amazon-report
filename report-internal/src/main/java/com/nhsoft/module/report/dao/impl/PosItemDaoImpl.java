package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.PosItemDao;
import com.nhsoft.module.report.model.*;
import com.nhsoft.report.utils.DateUtil;
import com.nhsoft.report.utils.ServiceBizException;
import com.nhsoft.module.report.queryBuilder.PosItemQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


@Repository
@SuppressWarnings("deprecation")
public class PosItemDaoImpl extends DaoImpl implements PosItemDao {

	@Override
	public PosItem read(Integer itemNum) {
		if (itemNum == null) {
			return null;
		}
		return (PosItem) currentSession().get(PosItem.class, itemNum);
	}

	@Override
	public List<Integer> findItemNumsByPosItemQuery(PosItemQuery posItemQuery, int offset, int limit) {
		String sql = "select p.item_num " + createByPosItemQueryString(posItemQuery)+ " order by p.item_code ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		if(posItemQuery.isPaging()) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
		return query.list();
	}

	@Override
	public List<Integer> findItemNums(String systemBookCode, String categoryCodes) {

		StringBuffer sb = new StringBuffer();
		sb.append("select item_num from pos_item with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and item_category_code in " + AppUtil.getStringParmeArray(categoryCodes.split(",")));
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}


	@Override
	public List<PosItem> findByItemNums(List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from pos_item with(nolock) where item_num in " + AppUtil.getIntegerParmeList(itemNums));
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(PosItem.class);
		return query.list();
	}

	@Override
	public List<PosItem> findAll(String systemBookCode) {

		String sql = "select * from pos_item with(nolock) where system_book_code = '" + systemBookCode + "' ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(PosItem.class);
		return query.list();
	}

	@Override
	public List<PosItem> find(String systemBookCode, List<String> categoryCodes, List<Integer> itemNums, String itemMethod) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from pos_item with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(categoryCodes != null && categoryCodes.size() > 0){
			sb.append("and item_category_code in " + AppUtil.getStringParmeList(categoryCodes));
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(StringUtils.isNotEmpty(itemMethod)) {
			sb.append("and item_method = '" + itemMethod + "' ");
		}
		sb.append("order by item_num desc ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(PosItem.class);
		return query.list();
	}

	@Override
	public List<PosItem> find(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(PosItem.class, "p")
				.add(Restrictions.eq("p.itemDelTag", false))
				.add(Restrictions.ne("p.itemPurchaseScope", AppConstants.ITEM_PURCHASE_SCOPE_SELFPRODUCT))
				.add(Restrictions.not(Restrictions.in("p.itemType", AppUtil.getUnStoreTypes())))
				.add(Restrictions.eq("p.itemEliminativeFlag", false))
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		criteria.addOrder(Order.asc("p.itemCode"));
		criteria.setFetchMode("itemMatrixs", FetchMode.JOIN);
		criteria.addOrder(Order.asc("p.itemNum"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = criteria.setLockMode("p", LockMode.READ);
		List<PosItem> posItems = criteria.list();
		return posItems;
	}

	@Override
	public List<PosItemKit> findPosItemKits(Integer itemNum) {
		Criteria criteria = currentSession().createCriteria(PosItemKit.class, "kit")
				.add(Restrictions.eq("kit.id.itemNum", itemNum));
		return criteria.list();
	}

	@Override
	public List<PosItemKit> findPosItemKits(String systemBookCode) {
		String sql = "select * "
				+ "from pos_item_kit with(nolock) "
				+ "where system_book_code = '" + systemBookCode + "' order by item_num asc ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(PosItemKit.class);
		return query.list();
	}


	@Override
	public List<PosItemKit> findPosItemKits(List<Integer> itemNums) {
		//为避免级联查询pos_item  所以用sql查询拼装
		String sql = "select item_num, kit_item_num,pos_item_kit_amount, pos_item_kit_item_matrix_num, system_book_code, pos_item_amount_un_fixed "
				+ "from pos_item_kit with(nolock) "
				+ "where item_num in " + AppUtil.getIntegerParmeList(itemNums);
		Query query = currentSession().createSQLQuery(sql);
		List<Object[]> objects = query.list();
		List<PosItemKit> posItemKits = new ArrayList<PosItemKit>();
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);

			PosItemKit posItemKit = new PosItemKit();
			posItemKit.setId(new PosItemKitId());
			posItemKit.getId().setItemNum((Integer)object[0]);
			posItemKit.getId().setKitItemNum((Integer)object[1]);
			posItemKit.setPosItemKitAmount((BigDecimal)object[2]);
			posItemKit.setPosItemKitItemMatrixNum((Integer)object[3]);
			posItemKit.setSystemBookCode((String)object[4]);
			posItemKit.setPosItemAmountUnFixed((Boolean)object[5]);
			posItemKits.add(posItemKit);
		}
		return posItemKits;
	}

	@Override
	public List<ItemMatrix> findItemMatrixs(String systemBookCode) {
		String sql = "select * from item_matrix with(nolock) where system_book_code = '" + systemBookCode + "' order by item_num asc";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(ItemMatrix.class);
		return query.list();
	}

	@Override
	public List<ItemMatrix> findItemMatrixs(Integer itemNum) {
		String sql = "select * from item_matrix with(nolock) where item_num = " + itemNum;
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(ItemMatrix.class);
		return query.list();
	}

	@Override
	public List<ItemMatrix> findItemMatrixs(List<Integer> itemNums) {
		String sql = "select * from item_matrix with(nolock) where item_num in " + AppUtil.getIntegerParmeList(itemNums) + "order by item_num asc";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(ItemMatrix.class);
		return query.list();
	}


	@Override
	public List<Object[]> findBySuppliers(List<Integer> supplierNums, List<Integer> itemNums,
										  List<String> categoryCodes, Integer branchNum, String systemBookCode) {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.id.supplierNum, p.itemNum, p.itemName, p.itemCode,p.itemSpec,  ");
		sb.append("p.itemCategoryCode, p.itemCategory, p.itemInventoryRate, p.itemTransferRate, p.itemWholesaleRate, ");
		sb.append("p.itemPurchaseRate, s.storeItemSupplierPri, p.itemRegularPrice, s.storeItemSupplierLastestTime ");
		sb.append("from PosItem as p, StoreItemSupplier as s ");
		sb.append("where p.itemNum = s.id.itemNum and p.systemBookCode = :systemBookCode and s.id.branchNum = :branchNum ");
		if (supplierNums != null && supplierNums.size() > 0) {
			sb.append("and s.id.supplierNum in " + AppUtil.getIntegerParmeList(supplierNums));
		}
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and s.id.itemNum in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and p.itemCategoryCode in " + AppUtil.getStringParmeList(categoryCodes));
		}
		sb.append("order by p.itemNum desc, s.storeItemSupplierPri desc, s.storeItemSupplierLastestTime desc ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setLockOptions(LockOptions.READ);


		//一个商品对应多个供应商的情况 代码中过滤一遍 符合要求的只取第一个
		List<Object[]> objects = query.list();
		Integer itemNum = null;
		Object[] object = null;
		List<Integer> existsItemNums = new ArrayList<Integer>();
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			itemNum = (Integer) object[1];

			if(existsItemNums.contains(itemNum)){
				objects.remove(i);
				i--;
				continue;
			}
			existsItemNums.add(itemNum);
		}
		return objects;
	}


	@Override
	public List<Integer> findUnSaleItemNums(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(PosItem.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode)).add(Restrictions.eq("p.itemDelTag", false))
				.add(Restrictions.eq("p.itemEliminativeFlag", false)).add(Restrictions.eq("p.itemSaleCeaseFlag", true));
		criteria.setProjection(Projections.property("p.itemNum"));
		return criteria.list();
	}


	@Override
	public List<PosItemKit> findPosItemKitsWithPosItem(List<Integer> itemNums) {
		//为避免级联查询pos_item  所以用sql查询拼装
		String sql = "select * from pos_item_kit with(nolock) "
				+ "where item_num in " + AppUtil.getIntegerParmeList(itemNums);
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(PosItemKit.class);
		return query.list();
	}


	@Override
	public List<PosItem> findByPosItemQuery(PosItemQuery posItemQuery, String sortField, String sortName, int first,
											int count) {
		String sql = null;
		if(posItemQuery.getQueryProperties() != null && !posItemQuery.getQueryProperties().isEmpty()){
			StringBuilder sb = new StringBuilder();
			sb.append("select ");
			int size = posItemQuery.getQueryProperties().size();
			for(int i = 0;i < size;i++){
				sb.append("p.").append(AppUtil.getDBColumnName(posItemQuery.getQueryProperties().get(i)));
				if(i == size - 1){
					sb.append(" ");
				} else {
					sb.append(",");
				}
			}

			sb.append(createByPosItemQueryString(posItemQuery));
			sql = sb.toString();

		} else {
			sql = "select p.* " + createByPosItemQueryString(posItemQuery);
			if (sortField != null && !sortField.equals("itemBranchRegularPrice")) {
				sql = sql + " order by " +  AppUtil.getDBColumnName(sortField) + " " + sortName;
			} else {
				sql = sql + " order by p.item_code asc ";
			}

		}
		SQLQuery query = currentSession().createSQLQuery(sql);
		if (posItemQuery.isPaging()) {
			query.setFirstResult(first);
			query.setMaxResults(count);
		}
		List<PosItem> posItems = null;
		if(posItemQuery.getQueryProperties() != null && !posItemQuery.getQueryProperties().isEmpty()){
			posItems = new ArrayList<PosItem>();
			List<Object[]> objects = query.list();
			try{
				for(Object[] object : objects){

					PosItem posItem = new PosItem();
					Class c = posItem.getClass();
					for(int j = 0;j < posItemQuery.getQueryProperties().size();j++){

						Field field = c.getDeclaredField(posItemQuery.getQueryProperties().get(j));
						field.setAccessible(true);
						field.set(posItem, object[j]);

					}
					posItems.add(posItem);
				}

			} catch (NoSuchFieldException e){
				throw new ServiceBizException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new ServiceBizException(e.getMessage());
			}
		} else {
			query.addEntity("p", PosItem.class);
			posItems = query.list();

		}
		if(posItemQuery.aliasBars()){

			//过滤重复数据
			List<Integer> itemNums = new ArrayList<Integer>();
			for(int i = posItems.size() - 1;i >= 0;i--){
				PosItem posItem = posItems.get(i);
				if(itemNums.contains(posItem.getItemNum())){
					posItems.remove(i);
					continue;
				}
				itemNums.add(posItem.getItemNum());
			}

		}
		return posItems;

	}



	private String createByPosItemQueryString(PosItemQuery posItemQuery){
		if(posItemQuery.getItemTypes() == null){
			posItemQuery.setItemTypes(new ArrayList<Integer>(0));
		}
		List<Integer> unstoreItemTypes = AppUtil.getUnStoreTypes();
		StringBuffer sb = new StringBuffer();
		sb.append("from pos_item as p with(nolock) ");
		if(posItemQuery.aliasBars()){
			sb.append("left join item_bar as bar with(nolock) on p.item_num = bar.item_num ");
		}
		sb.append("where p.system_book_code = '" + posItemQuery.getSystemBookCode() + "' ");
		if(posItemQuery.getItemNums() != null && posItemQuery.getItemNums().size() > 0){
			sb.append("and p.item_num in " + AppUtil.getIntegerParmeList(posItemQuery.getItemNums()));
		}
		if(posItemQuery.isQueryAll()){
			return sb.toString();
		}
		if(posItemQuery.getIsFindWeedOut() == null || !posItemQuery.getIsFindWeedOut()){
			sb.append("and p.item_eliminative_flag = 0 ");
		}
		if(posItemQuery.getItemLastEditTime() == null){
			sb.append("and p.item_del_tag = 0 ");
		} else {
			sb.append("and p.item_last_edit_time > '" + DateUtil.getLongDateTimeStr(posItemQuery.getItemLastEditTime()) + "' ");
		}
		if (posItemQuery.getIsFindNoStock() != null) {
			if (posItemQuery.getIsFindNoStock()) {
				sb.append("and p.item_type in " + AppUtil.getIntegerParmeList(unstoreItemTypes));
			} else {
				sb.append("and p.item_type not in " + AppUtil.getIntegerParmeList(unstoreItemTypes));
			}
		}
		
		if (StringUtils.isNotEmpty(posItemQuery.getFilterType())) {
			if (posItemQuery.getFilterType().equals(AppConstants.ITEM_TYPE_PURCHASE)) {
				if(posItemQuery.getItemTypes().size() == 0 || !posItemQuery.getItemTypes().contains(AppConstants.C_ITEM_TYPE_KIT)){
					sb.append("and p.item_type != " + AppConstants.C_ITEM_TYPE_KIT + " ");
				}
				
				if(posItemQuery.getItemTypes().size() == 0
						|| !posItemQuery.getItemTypes().contains(AppConstants.C_ITEM_TYPE_ASSEMBLE)){
					sb.append("and p.item_type != " + AppConstants.C_ITEM_TYPE_ASSEMBLE + " ");
					sb.append("and p.item_purchase_scope != '" + AppConstants.ITEM_PURCHASE_SCOPE_SELFPRODUCT + "' ");
				}
				//采购订单过滤停购 AMA-9547
				if(StringUtils.equals(posItemQuery.getOrderType(), AppConstants.POS_ITEM_LOG_PURCHASE_ORDER)){
					
					if(posItemQuery.getBranchNum() != null && posItemQuery.getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
						
						sb.append("and p.item_stock_cease_flag = 0 ");
						
					} else {
						sb.append("and ( ");
						sb.append("(p.item_stock_cease_flag = 0 and not exists (select 1 from store_matrix as s where s.system_book_code = '" + posItemQuery.getSystemBookCode() + "' and s.branch_num = " + posItemQuery.getBranchNum() + " and s.item_num = p.item_num and (s.store_matrix_stock_enabled is null or s.store_matrix_stock_enabled = 1) and s.store_matrix_stock_cease_flag = 1 )) ");
						sb.append("or ");
						sb.append("(p.item_stock_cease_flag = 1 and exists (select 1 from store_matrix as s where s.system_book_code = '" + posItemQuery.getSystemBookCode() + "' and s.branch_num = " + posItemQuery.getBranchNum() + " and s.item_num = p.item_num and s.store_matrix_stock_enabled = 0 and s.store_matrix_stock_cease_flag = 0 )) ");
						sb.append(")");
						
					}
					
				}
				if(posItemQuery.getItemMethod() == null || !posItemQuery.getItemMethod().equals(AppConstants.BUSINESS_TYPE_SHOP)){
					sb.append("and p.item_method != '" + AppConstants.BUSINESS_TYPE_SHOP + "' ");
					
				}
				if(posItemQuery.isRdc()){
					sb.append("and p.item_purchase_scope != '" + AppConstants.ITEM_PURCHASE_SCOPE_BRANCH + "' ");
					
				}
				
			} else if (posItemQuery.getFilterType().equals(AppConstants.ITEM_TYPE_INVENTORY)) {
				if (!posItemQuery.isRdc()) {
					sb.append("and p.item_type != " + AppConstants.C_ITEM_TYPE_ELEMENT + " ");
				}
				if(posItemQuery.getItemTypes().size() == 0 || !posItemQuery.getItemTypes().contains(AppConstants.C_ITEM_TYPE_KIT)){
					sb.append("and p.item_type != " + AppConstants.C_ITEM_TYPE_KIT + " ");
				}
			} else if (posItemQuery.getFilterType().equals(AppConstants.ITEM_TYPE_WHOLESALE)) {
				if(StringUtils.equals(posItemQuery.getOrderType(), AppConstants.POS_ITEM_LOG_WHOLESALE_RETURN_ORDER)){
					posItemQuery.setFilterTypeFilterSleepItem(false);
				}
				//批发销售 门店不过滤休眠
				if(StringUtils.equals(posItemQuery.getOrderType(), AppConstants.POS_ITEM_LOG_WHOLESALE_ORDER_ORDER)){
					if(!posItemQuery.getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
						posItemQuery.setFilterTypeFilterSleepItem(false);
					}
				}
				if(posItemQuery.isFilterTypeFilterSleepItem()){
					sb.append("and (p.item_status is null or p.item_status = " + AppConstants.ITEM_STATUS_NORMAL + " ) ");
				}
				if(posItemQuery.getItemTypes().size() == 0
						|| !posItemQuery.getItemTypes().contains(AppConstants.C_ITEM_TYPE_KIT)){
					sb.append("and p.item_type != " + AppConstants.C_ITEM_TYPE_KIT + " ");
					
				}
			} else if (posItemQuery.getFilterType().equals(AppConstants.ITEM_TYPE_CHAIN)) {
				if(!AppUtil.checkMaoXiongBookCode(posItemQuery.getSystemBookCode())){
					sb.append("and p.item_purchase_scope != '" + AppConstants.ITEM_PURCHASE_SCOPE_SELFPRODUCT + "' ");
					
				}
				sb.append("and p.item_purchase_scope != '" + AppConstants.ITEM_PURCHASE_SCOPE_BRANCH + "' ");
				//调出单门店查询不过滤休眠商品
				if(StringUtils.equals(posItemQuery.getOrderType(), AppConstants.POS_ITEM_LOG_OUT_ORDER)){
					if(!posItemQuery.getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
						posItemQuery.setFilterTypeFilterSleepItem(false);
					}
				}
				if(posItemQuery.isFilterTypeFilterSleepItem()){
					sb.append("and (p.item_status is null or p.item_status = " + AppConstants.ITEM_STATUS_NORMAL + " ) ");
				}
				sb.append("and p.item_type != " + AppConstants.C_ITEM_TYPE_KIT + " ");
				
			} else if (posItemQuery.getFilterType().equals(AppConstants.ITEM_TYPE_ONLINE)) {
				sb.append("and p.item_type != " + AppConstants.C_ITEM_TYPE_MATRIX + " ");
			} else if (posItemQuery.getFilterType().equals(AppConstants.ITEM_TYPE_SALE)) {
				
				if(posItemQuery.getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
					sb.append("and p.item_sale_cease_flag = 0 ");
					
				} else {
					sb.append("and ( ");
					sb.append("(p.item_sale_cease_flag = 0 and not exists (select 1 from store_matrix as s where s.system_book_code = '" + posItemQuery.getSystemBookCode() + "' and s.branch_num = " + posItemQuery.getBranchNum() + " and s.item_num = p.item_num and s.store_matrix_sale_cease_flag = 1 and store_matrix_sale_enabled = 1)) ");
					sb.append("or ");
					sb.append("(p.item_sale_cease_flag = 1 and exists (select 1 from store_matrix as s where s.system_book_code = '" + posItemQuery.getSystemBookCode() + "' and s.branch_num = " + posItemQuery.getBranchNum() + " and s.item_num = p.item_num and s.store_matrix_sale_cease_flag = 0 and store_matrix_sale_enabled = 1)) ");
					sb.append(")");
				}
				
			}
		}
		
		if (StringUtils.isNotEmpty(posItemQuery.getVar())) {
			
			posItemQuery.setVar(AppUtil.filterDangerousQuery(posItemQuery.getVar()));
			sb.append("and (");
			sb.append("p.item_code like '%" + posItemQuery.getVar() + "%' or ");
			sb.append("p.item_name like '%" + posItemQuery.getVar() + "%' or ");
			sb.append("p.store_item_pinyin like '%" + posItemQuery.getVar().toUpperCase() + "%' or ");
			sb.append("bar.item_bar_code like '%" + posItemQuery.getVar() + "%' ");
			
			Integer cardUserNum = null;
			try{
				cardUserNum = Integer.parseInt(posItemQuery.getVar());
			} catch (Exception e) {
			
			}
			if(cardUserNum != null){
				sb.append(" or p.item_num = " + cardUserNum + " ");
			}
			sb.append(") ");
		}
		if (posItemQuery.getCategoryCodes() != null && posItemQuery.getCategoryCodes().size() != 0) {
			sb.append("and p.item_category_code in " + AppUtil.getStringParmeList(posItemQuery.getCategoryCodes()));
		}
		if (posItemQuery.getFilterCategoryCodes() != null && posItemQuery.getFilterCategoryCodes().size() != 0) {
			sb.append("and p.item_category_code not in " + AppUtil.getStringParmeList(posItemQuery.getFilterCategoryCodes()));
		}
		if (posItemQuery.getItemTreeBrand() != null) {
			sb.append("and p.item_brand = '" + posItemQuery.getItemTreeBrand() + "' ");
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemDepartment())) {
			sb.append("and p.item_department = '" + posItemQuery.getItemDepartment() + "' ");
		}
		if(posItemQuery.getItemDepartments() != null && posItemQuery.getItemDepartments().size() > 0) {
			sb.append("and p.item_department in " + AppUtil.getStringParmeList(posItemQuery.getItemDepartments()));
		}
		if (posItemQuery.getSupplierNum() != null) {
			
			StringBuffer supplierSb = new StringBuffer();
			supplierSb.append("select 1 from store_item_supplier as s with(nolock) where s.system_book_code = '" + posItemQuery.getSystemBookCode() + "' ");
			if(posItemQuery.getBranchNum() != null){
				supplierSb.append("and s.branch_num = " + posItemQuery.getBranchNum() + " ");
			}
			if(posItemQuery.getDefaultSupplier() != null){
				supplierSb.append("and s.store_item_supplier_default = 1 ");
				
			}
			supplierSb.append("and s.supplier_num = " + posItemQuery.getSupplierNum() + " and s.item_num = p.item_num ");
			sb.append("and exists (").append(supplierSb.toString()).append(") ");
			
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemCode())) {
			if(posItemQuery.getItemType() != null && posItemQuery.getItemType() == AppConstants.C_ITEM_TYPE_GRADE){
				sb.append("and (p.item_code like '%" + posItemQuery.getItemCode() + "%' ");
				sb.append("or exists (select 1 from pos_item_grade as grade where grade.item_num = p.item_num and item_grade_code like '%" + posItemQuery.getItemCode() + "%' ");
				sb.append(")) ");
			} else {
				sb.append("and p.item_code like '%" + posItemQuery.getItemCode() + "%' ");
				
			}
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemBarCode())) {
			if(posItemQuery.getItemType() != null && posItemQuery.getItemType() == AppConstants.C_ITEM_TYPE_GRADE){
				sb.append("and (bar.item_bar_code like '%" + posItemQuery.getItemBarCode() + "%' ");
				sb.append("or exists (select 1 from pos_item_grade as grade where grade.item_num = p.item_num and item_grade_barcode like '%" + posItemQuery.getItemBarCode() + "%' ");
				sb.append(")) ");
			} else {
				sb.append("and bar.item_bar_code like '%" + posItemQuery.getItemBarCode() + "%' ");
				
			}
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemName())) {
			if(posItemQuery.getItemType() != null && posItemQuery.getItemType() == AppConstants.C_ITEM_TYPE_GRADE){
				sb.append("and (p.item_name like '%" + posItemQuery.getItemName() + "%' ");
				sb.append("or exists (select 1 from pos_item_grade as grade where grade.item_num = p.item_num and item_grade_name like '%" + posItemQuery.getItemName() + "%' ");
				sb.append(")) ");
			} else {
				
				sb.append("and p.item_name like '%" + posItemQuery.getItemName() + "%' ");
			}
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemPinyin())) {
			sb.append("and p.store_item_pinyin like '%" + posItemQuery.getItemPinyin().toUpperCase() + "%' ");
		}
		if (posItemQuery.getItemType() != null) {
			sb.append("and p.item_type = " + posItemQuery.getItemType() + " ");
			
		}
		if (StringUtils.isNotEmpty(posItemQuery.getCategoryCode())) {
			sb.append("and p.item_category_code in " + AppUtil.getStringParmeArray(posItemQuery.getCategoryCode().split(",")));
			
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemBrand())) {
			sb.append("and p.item_brand = '" + posItemQuery.getItemBrand() + "' ");
		}
		if (posItemQuery.getRegularPriceFrom() != null) {
			sb.append("and p.item_regular_price > " + posItemQuery.getRegularPriceFrom() + " ");
		}
		if (posItemQuery.getRegularPriceTo() != null) {
			sb.append("and p.item_regular_price < " + posItemQuery.getRegularPriceTo() + " ");
			
		}
		if (posItemQuery.getMinPriceFrom() != null) {
			sb.append("and p.item_min_price > " + posItemQuery.getMinPriceFrom() + " ");
		}
		if (posItemQuery.getMinPriceTo() != null) {
			sb.append("and p.item_min_price < " + posItemQuery.getMinPriceTo() + " ");
		}
		if (posItemQuery.getTransferPriceFrom() != null) {
			sb.append("and p.item_transfer_price > " + posItemQuery.getTransferPriceFrom() + " ");
		}
		if (posItemQuery.getTransferPriceTo() != null) {
			sb.append("and p.item_transfer_price < " + posItemQuery.getTransferPriceTo() + " ");
		}
		
		if (posItemQuery.getPurchasePriceFrom() != null) {
			sb.append("and p.item_cost_price > " + posItemQuery.getPurchasePriceFrom() + " ");
		}
		if (posItemQuery.getPurchasePriceTo() != null) {
			sb.append("and p.item_cost_price < " + posItemQuery.getPurchasePriceTo() + " ");
		}
		
		if (posItemQuery.getWholePriceFrom() != null) {
			sb.append("and p.item_wholesale_price > " + posItemQuery.getWholePriceFrom() + " ");
		}
		if (posItemQuery.getWholePriceTo() != null) {
			sb.append("and p.item_wholesale_price < " + posItemQuery.getWholePriceTo() + " ");
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemPlace())) {
			sb.append("and p.item_store_place like '%" + posItemQuery.getItemPlace() + "%' ");
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemPuchaseScope())) {
			sb.append("and p.item_purchase_scope = '" + posItemQuery.getItemPuchaseScope() + "' ");
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemMethod()) && StringUtils.isEmpty(posItemQuery.getFilterType())) {
			sb.append("and p.item_method = '" + posItemQuery.getItemMethod() + "' ");
		}
		if(StringUtils.isNotEmpty(posItemQuery.getItemMethods())){
			sb.append("and p.item_method in " + AppUtil.getStringParmeArray(posItemQuery.getItemMethods().split(",")));
		}
		if (StringUtils.isNotEmpty(posItemQuery.getItemCostMode())) {
			sb.append("and p.item_cost_mode in " + AppUtil.getStringParmeArray(posItemQuery.getItemCostMode().split(",")));
		}
		if (posItemQuery.getItemCreateTimeFrom() != null){
			sb.append("and p.item_create_time >= '" + DateUtil.getLongDateTimeStr(posItemQuery.getItemCreateTimeFrom()) + "' ");
		}
		if (posItemQuery.getItemCreateTimeTo() != null){
			sb.append("and p.item_create_time <= '" + DateUtil.getLongDateTimeStr(posItemQuery.getItemCreateTimeTo()) + "' ");
		}
		if (posItemQuery.getFilterItemTypes() != null && posItemQuery.getFilterItemTypes().size() > 0){
			sb.append("and p.item_type not in " + AppUtil.getIntegerParmeList(posItemQuery.getFilterItemTypes()));
		}
		if (posItemQuery.getSaleCrease() != null ) {
			sb.append("and p.item_sale_cease_flag = " + BooleanUtils.toString(posItemQuery.getSaleCrease(), "1", "0") + " ");
		}
		if (posItemQuery.getStockCrease() != null) {
			sb.append("and p.item_stock_cease_flag = " + BooleanUtils.toString(posItemQuery.getStockCrease(), "1", "0") + " ");
		}
		if (posItemQuery.getItemManufactureFlag() != null) {
			sb.append("and (p.item_manufacture_flag = " + BooleanUtils.toString(posItemQuery.getItemManufactureFlag(), "1", "0") + " or p.item_type = " + AppConstants.C_ITEM_TYPE_ASSEMBLE + ") ");
			
		}
		if (posItemQuery.getFilterInElementKit() != null && posItemQuery.getFilterInElementKit()) {
			if(posItemQuery.getItemType() != null && posItemQuery.getItemType() == AppConstants.C_ITEM_TYPE_MATERIAL){
				sb.append("and not exists (select 1 from item_element_kit with(nolock) where system_book_code = '" + posItemQuery.getSystemBookCode() + "' and item_element_kit.element_item_num = p.item_num ) ");
				
			} else {
				sb.append("and not exists (select 1 from item_element_kit with(nolock) where system_book_code = '" + posItemQuery.getSystemBookCode() + "' and item_element_kit.item_num = p.item_num ) ");
				
			}
		}
		if (posItemQuery.getFilterInCategoryProfit() != null) {
			if(posItemQuery.getFilterInCategoryProfit()){
				
				sb.append("and not exists (select 1 from category_profit with(nolock) where system_book_code = '" + posItemQuery.getSystemBookCode() + "' and category_profit.item_num = p.item_num ) ");
				
			} else {
				sb.append("and exists (select 1 from category_profit with(nolock) where system_book_code = '" + posItemQuery.getSystemBookCode() + "' and category_profit.item_num = p.item_num ) ");
				
			}
		}
		StringBuffer orBuffer = new StringBuffer();
		boolean haveOr = false;
		if (posItemQuery.getNewCrease() != null && posItemQuery.getNewCrease() && posItemQuery.getNewDay() != null) {
			int newDay = posItemQuery.getNewDay();
			haveOr = true;
			orBuffer.append("p.item_create_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(DateUtil.addDay(Calendar.getInstance().getTime(), -newDay))) + "' ");
		}
		
		if (posItemQuery.getDromCrease() != null) {
			if(haveOr){
				orBuffer.append("or ");
			}
			orBuffer.append("p.item_status = " + AppConstants.ITEM_STATUS_SLEEP + " ");
			haveOr = true;
		}
		if (posItemQuery.getNormalCrease() != null && posItemQuery.getNormalCrease()){
			if(haveOr){
				orBuffer.append("or ");
			}
			orBuffer.append("(p.item_sale_cease_flag = 0 and p.item_stock_cease_flag = 0 and (p.item_status is null or p.item_status = 0)) ");
		}
		String orString = orBuffer.toString();
		if(StringUtils.isNotEmpty(orString)){
			sb.append("and (" + orString + ") ");
		}
		return sb.toString();
	}


	@Override
	public List<ItemBar> findItemBars(String systemBookCode) {
		String sql = "select * from item_bar with(nolock) where system_book_code = '" + systemBookCode + "' order by item_num asc";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(ItemBar.class);
		return query.list();
	}

	@Override
	public List<ItemBar> findItemBars(List<Integer> itemNums) {
		String sql = "select * from item_bar with(nolock) where item_num in " + AppUtil.getIntegerParmeList(itemNums) + "order by item_num asc";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(ItemBar.class);
		return query.list();
	}

	@Override
	public List<ItemBar> findItemBars(Integer itemNum) {
		String sql = "select * from item_bar with(nolock) where item_num = " + itemNum;
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(ItemBar.class);
		return query.list();
	}

	@Override
	public List<Object[]> findItemLat(String systemBookCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("select item_num, item_category ");
		sb.append("from pos_item where system_book_code = '" + systemBookCode+  "' ");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		return sqlQuery.list();
	}


}