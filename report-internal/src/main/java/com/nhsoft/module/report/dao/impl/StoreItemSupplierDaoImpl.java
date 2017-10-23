package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.StoreItemSupplierDao;
import com.nhsoft.module.report.model.StoreItemSupplier;
import com.nhsoft.module.report.model.Supplier;
import com.nhsoft.module.report.util.AppUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class StoreItemSupplierDaoImpl extends DaoImpl implements StoreItemSupplierDao {


	@Override
	public List<StoreItemSupplier> findByItemNums(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(StoreItemSupplier.class , "s")
				.add(Restrictions.eq("s.id.systemBookCode", systemBookCode));
		if(branchNum != null){

			criteria.add(Restrictions.eq("s.id.branchNum", branchNum));
		}
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.sqlRestriction("item_num in " + AppUtil.getIntegerParmeList(itemNums)));


		}
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Supplier.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode))
				.add(Restrictions.eq("p.supplierDelTag", false))
				.add(Restrictions.eq("p.supplierActived", true))
				.add(Restrictions.eqProperty("p.supplierNum", "s.id.supplierNum"));
		if(branchNum != null){
			subCriteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("p.branchNum", branchNum))
					.add(Restrictions.eq("p.supplierShared", true))
			);
		}
		criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("p.supplierNum"))));
		criteria.addOrder(Order.desc("s.storeItemSupplierLastestTime"));
		return criteria.list();
	}


	@Override
	public List<StoreItemSupplier> find(String systemBookCode, Integer branchNum, List<Integer> itemNums,
										List<Integer> supplierNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from store_item_supplier with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNum != null ){
			sb.append("and branch_num = :branchNum ");
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(supplierNums != null && supplierNums.size() > 0){
			sb.append("and supplier_num in " + AppUtil.getIntegerParmeList(supplierNums));
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(StoreItemSupplier.class);
		query.setString("systemBookCode", systemBookCode);
		if(branchNum != null ){
			query.setInteger("branchNum", branchNum);
		}
		return query.list();
	}



	@Override
	public List<StoreItemSupplier> find(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, List<Integer> supplierNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from store_item_supplier with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(supplierNums != null && supplierNums.size() > 0){
			sb.append("and supplier_num in " + AppUtil.getIntegerParmeList(supplierNums));
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(StoreItemSupplier.class);
		query.setString("systemBookCode", systemBookCode);

		return query.list();
	}


	@Override
	public List<StoreItemSupplier> find(String systemBookCode,
										Integer branchNum, List<Integer> supplierNums, boolean orderFlag, List<Integer> itemNums) {

		StringBuffer sb = new StringBuffer();
		sb.append("select * from store_item_supplier with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if(branchNum != null){
			sb.append("and branch_num = " + branchNum);
		}
		if(supplierNums != null && supplierNums.size() > 0){
			sb.append("and supplier_num in " + AppUtil.getIntegerParmeList(supplierNums));
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(orderFlag){
			sb.append("order by store_item_supplier_lastest_time desc ");
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(StoreItemSupplier.class);
		return query.list();
	}


	@Override
	public List<StoreItemSupplier> findDefaults(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from store_item_supplier with(nolock) ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and store_item_supplier_default = 1 ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(StoreItemSupplier.class);
		query.setString("systemBookCode", systemBookCode);

		return query.list();
	}
}
