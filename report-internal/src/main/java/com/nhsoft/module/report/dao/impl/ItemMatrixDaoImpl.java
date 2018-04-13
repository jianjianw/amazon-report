package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.ItemMatrixDao;
import com.nhsoft.module.report.model.ItemMatrix;
import com.nhsoft.module.report.model.ItemMatrixId;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("deprecation")
@Repository
public class ItemMatrixDaoImpl extends DaoImpl implements ItemMatrixDao {


	@Override
	public ItemMatrix read(Integer itemNum, Integer itemMatrixNum) {
		ItemMatrixId id = new ItemMatrixId();
		id.setItemNum(itemNum);
		id.setItemMatrixNum(itemMatrixNum);
		return (ItemMatrix) currentSession().get(ItemMatrix.class, id);
	}

	@Override
	public List<ItemMatrix> findByItemNum(Integer itemNum) {
		Criteria criteria = currentSession().createCriteria(ItemMatrix.class, "i")
				.add(Restrictions.eq("i.id.itemNum", itemNum));
		return criteria.list();
	}


}
