package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.BranchResourceDao;
import com.nhsoft.module.report.model.BranchResource;
import com.nhsoft.module.report.model.BranchResourceId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class BranchResourceDaoImpl extends DaoImpl implements BranchResourceDao {

    private Map<String, Integer> map = new HashMap<String, Integer>();

    @Override
    public BranchResource read(String systemBookCode, Integer branchNum,
                               String name) {
        Integer count = map.get(name);
        if(count == null){
            count = 0;
        }
        count = count + 1;
        map.put(name, count);

        BranchResourceId id = new BranchResourceId();
        id.setBranchNum(branchNum);
        id.setSystemBookCode(systemBookCode);
        id.setBranchResourceName(name);

        return (BranchResource)currentSession().get(BranchResource.class, id);
    }
}
