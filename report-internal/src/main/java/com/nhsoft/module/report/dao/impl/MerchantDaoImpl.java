package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.MerchantDao;
import com.nhsoft.module.report.model.Merchant;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MerchantDaoImpl extends DaoImpl implements MerchantDao {
    @Override
    public List<Merchant> find(String systemBookCode, Integer branchNum) {
        Criteria criteria = currentSession().createCriteria(Merchant.class, "d");
        criteria.add(Restrictions.eq("d.systemBookCode", systemBookCode));
        criteria.add(Restrictions.eq("d.branchNum", branchNum));
        criteria.add(Restrictions.ne("d.merchantDelTag",true));
        criteria.setLockMode(LockMode.NONE);
        return criteria.list();
    }

    @Override
    public List<Merchant> findActive(String systemBookCode, Integer branchNum) {
        Criteria criteria = currentSession().createCriteria(Merchant.class, "d");
        criteria.add(Restrictions.eq("d.systemBookCode", systemBookCode));
        criteria.add(Restrictions.eq("d.branchNum", branchNum));
        criteria.add(Restrictions.ne("d.merchantDelTag",true));
        criteria.setLockMode(LockMode.NONE);
        return criteria.list();
    }
}
