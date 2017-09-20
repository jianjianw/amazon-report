package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.ItemExtendAttributeDao;
import com.nhsoft.report.model.ItemExtendAttribute;
import com.nhsoft.report.util.AppUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ItemExtendAttributeDaoImpl extends DaoImpl implements ItemExtendAttributeDao {

	@Override
	public List<ItemExtendAttribute> find(String systemBookCode) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from item_extend_attribute with(nolock) where system_book_code = '" + systemBookCode + "' ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(ItemExtendAttribute.class);
		return query.list();
	}



	@Override
	public List<ItemExtendAttribute> findByItem(Integer itemNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from item_extend_attribute with(nolock) where item_num = " + itemNum + " ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(ItemExtendAttribute.class);
		return query.list();
	}



	@Override
	public List<ItemExtendAttribute> findByItems(List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from item_extend_attribute with(nolock) where item_num in " + AppUtil.getIntegerParmeList(itemNums));
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.addEntity(ItemExtendAttribute.class);
		return query.list();
	}

}