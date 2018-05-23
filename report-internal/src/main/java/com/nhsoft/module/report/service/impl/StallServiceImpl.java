package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.StallDao;
import com.nhsoft.module.report.model.Stall;
import com.nhsoft.module.report.service.StallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StallServiceImpl implements StallService {

    @Autowired
    private StallDao stallDao;

    @Override
    public List<Stall> find(List<Integer> stallNums) {
        return stallDao.find(stallNums);
    }
}
