package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.Stall;

import java.util.List;

public interface StallService {

    public List<Stall> find(List<Integer> stallNums);

}
