package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.SupplierDao;
import com.nhsoft.report.model.Supplier;
import com.nhsoft.report.util.AppUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public class SupplierDaoImpl extends DaoImpl implements SupplierDao {
	@Override
	public List<Supplier> find(String systemBookCode, Integer branchNum, String supplierCategory, String queryName,
							   Boolean isCenterShare, Boolean isEnable, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from supplier as s with(nolock) where s.system_book_code = '" + systemBookCode + "' and s.supplier_del_tag = 0 ");
		if(isEnable != null){
			sb.append("and s.supplier_actived = " + BooleanUtils.toString(isEnable, "1", "0") + " ");
		}
		if(branchNum != null){
			if(isCenterShare != null && isCenterShare){
				sb.append("and ( s.branch_num = " + branchNum + " or (s.supplier_shared = 1 and exists (select 1 from supplier_share_branch where system_book_code = s.system_book_code and supplier_num = s.supplier_num and (branch_num = " + branchNum + " or branch_num = 0)))) ");
			} else {
				sb.append("and s.branch_num = " + branchNum + " ");
			}
		}
		if(StringUtils.isNotEmpty(supplierCategory)){
			sb.append("and supplier_kind = '" + supplierCategory + "' ");
		}
		if(StringUtils.isNotEmpty(queryName)){
			sb.append("and (supplier_pin like '%" + queryName +"%' or supplier_code like '%" + queryName + "%' or supplier_name like '%" + queryName + "%' ) ");
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and exists (select 1 from store_item_supplier where system_book_code = '" + systemBookCode + "' and item_num in " + AppUtil.getIntegerParmeList(itemNums) + " and supplier_num = s.supplier_num) ");
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(Supplier.class);
		return query.list();

	}


	@Override
	public List<Supplier> find(String systemBookCode, List<Integer> branchNums, String supplierCategory, String queryName, Boolean isCenterShared, Boolean isEnable, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from supplier as s with(nolock) where s.system_book_code = '" + systemBookCode + "' and s.supplier_del_tag = 0 ");
		if(isEnable != null){
			sb.append("and s.supplier_actived = " + BooleanUtils.toString(isEnable, "1", "0") + " ");
		}
		if(branchNums != null && branchNums.size() > 0){
			if(isCenterShared != null && isCenterShared){
				sb.append("and ( s.branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " or (s.supplier_shared = 1 and exists (select 1 from supplier_share_branch where system_book_code = s.system_book_code and supplier_num = s.supplier_num and (branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " or branch_num = 0)))) ");
			} else {
				sb.append("and s.branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
			}
		}
		if(StringUtils.isNotEmpty(supplierCategory)){
			sb.append("and supplier_kind = '" + supplierCategory + "' ");
		}
		if(StringUtils.isNotEmpty(queryName)){
			sb.append("and (supplier_pin like '%" + queryName +"%' or supplier_code like '%" + queryName + "%' or supplier_name like '%" + queryName + "%' ) ");
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and exists (select 1 from store_item_supplier where system_book_code = '" + systemBookCode + "' and item_num in " + AppUtil.getIntegerParmeList(itemNums) + " and supplier_num = s.supplier_num) ");
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(Supplier.class);
		return query.list();
	}


	@Override
	public List<Supplier> findAll(String systemBookCode) {
		Criteria criteria = currentSession().createCriteria(Supplier.class, "s")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode));
		return criteria.list();
	}
}
