package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.Stall;

import java.util.List;

public interface StallDao {

    public List<Stall> find(List<Integer> stallNums);



}
