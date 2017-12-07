package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.azure.model.Branch;
import com.nhsoft.module.azure.model.BranchLat;
import com.nhsoft.module.report.dto.BranchArea;
import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.dto.BranchRegionDTO;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.service.BranchService;
import com.nhsoft.module.report.util.CopyUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    public List<BranchDTO> findBranchByBranchRegionNum(String systemBookCode, Integer branchRegionNum) {
        return CopyUtil.toList(branchService.findBranchByBranchRegionNum(systemBookCode,branchRegionNum), BranchDTO.class);
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

    @Override
    public BranchDTO readWithNolock(String systemBookCode, Integer branchNum) {
        return CopyUtil.to(branchService.readWithNolock(systemBookCode,branchNum),BranchDTO.class);
    }

    @Override
    public List<Branch> findBranch(String systemBookCode) {
        List<Object[]> objects = branchService.findBranch(systemBookCode);
        List<Branch> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            Branch branch = new Branch();
            branch.setBranchNum((Integer) object[0]);
            branch.setBranchCode((String) object[1]);
            branch.setBranchName((String) object[2]);
            if((int)object[3] == 1){
                branch.setBranchActived(true);
            }else{
                branch.setBranchActived(false);
            }
            if((int)object[4] == 1){
                branch.setRanchRdc(true);
            }else{
                branch.setRanchRdc(false);
            }
            branch.setBranchType((String) object[5]);
            branch.setBranchArea((BigDecimal) object[6]);
            branch.setBranchEmployeeCount((Integer) object[7]);
            branch.setBranchCreateTime(DateUtil.getDateTimeHMS((String)object[8]));
            list.add(branch);
        }
        return list;
    }


}
