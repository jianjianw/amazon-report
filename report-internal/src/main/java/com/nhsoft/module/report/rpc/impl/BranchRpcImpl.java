package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.BranchArea;
import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.dto.BranchRegionDTO;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.service.BranchService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class BranchRpcImpl implements BranchRpc {

    @Autowired
    private BranchService branchService;

    @Override
    public List<BranchDTO> findInCache(String systemBookCode) {
        return CopyUtil.toList(branchService.findInCache(systemBookCode), BranchDTO.class);
    }

    @Override
    public List<BranchRegionDTO> findBranchRegion(String systemBookCode) {
        return CopyUtil.toList(branchService.findBranchRegion(systemBookCode), BranchRegionDTO.class);
    }

    @Override
    @Cacheable(value = "serviceCache")
    public List<BranchDTO> findBranchByBranchRegionNum(String systemBookCode, Integer branchRegionNum) {
        return CopyUtil.toList(branchService.findBranchByBranchRegionNum(systemBookCode,branchRegionNum), BranchDTO.class);
    }

    @Override
    @Cacheable(value = "serviceCache")
    public List<BranchArea> findBranchArea(String systemBookCode, List<Integer> branchNums) {
        List<Object[]> objects = branchService.findBranchArea(systemBookCode, branchNums);
        int size = objects.size();
        List<BranchArea> list = new ArrayList<BranchArea>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            BranchArea branchArea = new BranchArea();
            branchArea.setBranchNum((Integer) object[0]);
            branchArea.setArea((BigDecimal) object[1]);
            list.add(branchArea);
        }
        return list;
    }

    @Override
    @Cacheable(value = "serviceCache")
    public BranchDTO readWithNolock(String systemBookCode, Integer branchNum) {
        return CopyUtil.to(branchService.readWithNolock(systemBookCode,branchNum),BranchDTO.class);
    }


}
