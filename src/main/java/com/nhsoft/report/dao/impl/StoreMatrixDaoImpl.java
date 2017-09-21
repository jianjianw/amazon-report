package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.StoreMatrixDao;
import com.nhsoft.report.model.StoreMatrix;
import com.nhsoft.report.model.StoreMatrixDetail;
import com.nhsoft.report.util.AppUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Repository
public class StoreMatrixDaoImpl extends  DaoImpl implements StoreMatrixDao {
	@Override
	public List<StoreMatrix> findByBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from store_matrix with(nolock) where system_book_code = :systemBookCode ");
		if(branchNum != null){
			sb.append("and branch_num = :branchNum ");
			
		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		if(branchNum != null){
			query.setInteger("branchNum", branchNum);
		}
		query.addEntity(StoreMatrix.class);
		return query.list();
	}
	
	@Override
	public List<StoreMatrix> find(String systemBookCode, Integer itemNum) {
		Criteria criteria = currentSession().createCriteria(StoreMatrix.class , "s")
				.add(Restrictions.eq("s.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("s.id.itemNum", itemNum));
		return criteria.list();
	}

	@Override
	public List<Object[]> findItemSaleCeaseFlag(String systemBookCode, Integer branchNum) {
		Criteria criteria = currentSession().createCriteria(StoreMatrix.class , "s")
				.add(Restrictions.eq("s.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("s.id.branchNum", branchNum))
				.add(Restrictions.eq("s.storeMatrixSaleEnabled", true));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("s.id.itemNum"))
				.add(Projections.property("s.storeMatrixSaleCeaseFlag"))
		);
		return criteria.list();
	}

	@Override
	public List<StoreMatrixDetail> findDetails(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
		Criteria criteria = currentSession().createCriteria(StoreMatrixDetail.class, "detail")
				.add(Restrictions.eq("detail.id.systemBookCode", systemBookCode))
				.add(Restrictions.eq("detail.id.branchNum", branchNum));
		if(itemNums != null && itemNums.size() > 0){
			criteria.add(Restrictions.in("detail.id.itemNum", itemNums));
		}
		return criteria.list();
	}
}
