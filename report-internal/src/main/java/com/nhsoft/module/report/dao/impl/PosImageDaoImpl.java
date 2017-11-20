package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.PosImageDao;
import com.nhsoft.module.report.model.PosImage;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PosImageDaoImpl extends DaoImpl implements PosImageDao {

	
	@Override
	public List<PosImage> find(String systemBookCode, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(PosImage.class, "i")
				.add(Restrictions.eq("i.systemBookCode", systemBookCode));
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("i.itemNum", itemNums));
		}
		criteria.setLockMode(LockMode.NONE);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("i.posImageId"))
				.add(Projections.property("i.itemNum"))
				.add(Projections.property("i.posImageType"))
				.add(Projections.property("i.posImageDefault"))
				.add(Projections.property("i.posImageUrl"))
				.add(Projections.property("i.posImageWidth"))
				.add(Projections.property("i.posImageHeight"))
				.add(Projections.property("i.systemBookCode"))
				.add(Projections.property("i.posImageCreationDate"))
		);
		List<Object[]> objects = criteria.list();
		List<PosImage> list = new ArrayList<PosImage>();
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			PosImage posImage =  new PosImage();
			posImage.setPosImageId((String)object[0]);
			posImage.setItemNum((Integer)object[1]);
			posImage.setPosImageType((String)object[2]);
			posImage.setPosImageDefault((Boolean)object[3]);
			posImage.setPosImageUrl((String)object[4]);
			posImage.setPosImageWidth((Integer)object[5]);
			posImage.setPosImageHeight((Integer)object[6]);
			posImage.setSystemBookCode((String)object[7]);
			posImage.setPosImageCreationDate((Date)object[8]);
			list.add(posImage);
		}
		return list;
	}

	
	

}
