package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.StallDao;
import com.nhsoft.module.report.model.Stall;
import com.nhsoft.module.report.util.AppUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StallDaoImpl extends DaoImpl implements StallDao {


    @Override
    public List<Stall> find(List<Integer> stallNums) {
        String sql = "select * from stall with(nolock) where stall_num in " + AppUtil.getIntegerParmeList(stallNums);
        SQLQuery query = currentSession().createSQLQuery(sql);
        query.addEntity(Stall.class);
        return query.list();
    }
}
