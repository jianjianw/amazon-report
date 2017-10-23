package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.ItemVersionDao;
import com.nhsoft.module.report.model.ItemVersion;
import com.nhsoft.module.report.util.DateUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public class ItemVersionDaoImpl extends DaoImpl implements ItemVersionDao {
	@Override
	public List<ItemVersion> findFirst(String systemBookCode, Date dateFrom, Date dateTo, String itemVersionType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from item_version with(nolock) where system_book_code = :systemBookCode and item_version_type = :itemVersionType and exists (select 1 from (select item_version_ref as ref, min(item_version_time) as minTime from item_version with(nolock)  ");
		sb.append("where system_book_code = :systemBookCode and item_version_type = :itemVersionType group by item_version_ref ");
		sb.append("having min(item_version_time) between :dateFrom and :dateTo) as a where item_version.item_version_ref = a.ref and item_version.item_version_time = a.minTime )  ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		query.setString("systemBookCode", systemBookCode);
		query.setString("itemVersionType", itemVersionType);
		query.addEntity(ItemVersion.class);
		return query.list();
	}
}
