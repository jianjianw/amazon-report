package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.GroupCustomerDao;
import com.nhsoft.module.report.model.GroupCustomer;
import com.nhsoft.module.report.util.AppConstants;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangqin on 2017/10/11.
 */
@Repository
public class GroupCustomerDaoImpl  extends  DaoImpl implements GroupCustomerDao {
	@Override
	public List<GroupCustomer> findByBranch(String systemBookCode, Integer branchNum, String groupCustomerType) {
		Criteria criteria = currentSession().createCriteria(GroupCustomer.class, "g")
				.add(Restrictions.eq("g.systemBookCode", systemBookCode))
				.add(Restrictions.eq("g.branchNum", branchNum));
		
		if(groupCustomerType == null || groupCustomerType.equals(AppConstants.GROUP_CUSTOMER_TYPE_CARD)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("g.groupCustomerType", AppConstants.GROUP_CUSTOMER_TYPE_CARD))
					.add(Restrictions.isNull("g.groupCustomerType")));
			
		} else {
			criteria.add(Restrictions.eq("g.groupCustomerType", AppConstants.GROUP_CUSTOMER_TYPE_POS_CLIENT));
		}
		return criteria.list();
	}
	
	@Override
	public List<GroupCustomer> findDefault(String systemBookCode, Integer branchNum) {
		Criteria criteria = currentSession().createCriteria(GroupCustomer.class, "g")
				.add(Restrictions.eq("g.systemBookCode", systemBookCode))
				.add(Restrictions.eq("g.branchNum", branchNum))
				.add(Restrictions.eq("g.groupCustomerProperty", AppConstants.GROUP_CUSTOMER_PROPERTY_DEFAULT));
		return criteria.list();
	}

	@Override
	public List<GroupCustomer> findBybranch(String systemBookCode,
											Integer branchNum, String groupCustomerType) {
		Criteria criteria = currentSession().createCriteria(GroupCustomer.class, "g")
				.add(Restrictions.eq("g.systemBookCode", systemBookCode))
				.add(Restrictions.eq("g.branchNum", branchNum));

		if(groupCustomerType == null || groupCustomerType.equals(AppConstants.GROUP_CUSTOMER_TYPE_CARD)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("g.groupCustomerType", AppConstants.GROUP_CUSTOMER_TYPE_CARD))
					.add(Restrictions.isNull("g.groupCustomerType")));

		} else {
			criteria.add(Restrictions.eq("g.groupCustomerType", AppConstants.GROUP_CUSTOMER_TYPE_POS_CLIENT));
		}
		return criteria.list();
	}
}
