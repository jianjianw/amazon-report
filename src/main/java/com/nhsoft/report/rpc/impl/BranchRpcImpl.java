package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dto.BranchArea;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import com.nhsoft.report.rpc.BranchRpc;
import com.nhsoft.report.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
@Component
public class BranchRpcImpl implements BranchRpc {

    @Autowired
    private BranchService branchService;

    @Override
    public List<Branch> findAll(String systemBookCode) {
        return branchService.findAll(systemBookCode);
    }

    @Override
    public List<BranchRegion> findBranchRegion(String systemBookCode) {
        return branchService.findBranchRegion(systemBookCode);
    }

    @Override
    public List<Branch> findBranchByBranchRegionNum(String systemBookCode, Integer branchRegionNum) {
        return branchService.findBranchByBranchRegionNum(systemBookCode,branchRegionNum);
    }

    @Override
    public List<BranchArea> findBranchArea(String systemBookCode, List<Integer> branchNums) {
        List<Object[]> objects = branchService.findBranchArea(systemBookCode, branchNums);
        List<BranchArea> list = new ArrayList<BranchArea>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            BranchArea branchArea = new BranchArea();
            branchArea.setBranchNum((Integer) object[0]);
            branchArea.setArea((BigDecimal) object[1]);
            list.add(branchArea);
        }
        return list;
    }
}
