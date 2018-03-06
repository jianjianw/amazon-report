package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.BranchBizItemSummary;
import com.nhsoft.module.report.dto.BranchItemRecoredDTO;
import com.nhsoft.module.report.rpc.BranchItemRecoredRpc;
import com.nhsoft.module.report.service.BranchItemRecoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class BranchItemRecoredRpcImpl implements BranchItemRecoredRpc {

    @Autowired
    private BranchItemRecoredService branchItemRecoredService;

    @Override
    public List<BranchItemRecoredDTO> findItemAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum, List<Integer> itemNums, List<String> branchItemRecoredTypes) {

        List<Object[]> objects = branchItemRecoredService.findItemAuditDate(systemBookCode, branchNum, storehouseNum, itemNums, branchItemRecoredTypes);
        int size = objects.size();
        List<BranchItemRecoredDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            BranchItemRecoredDTO dto = new BranchItemRecoredDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setAuditDate((String) object[1]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<BranchItemRecoredDTO> findItemMinAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum, List<Integer> itemNums, List<String> branchItemRecoredTypes) {

        List<Object[]> objects = branchItemRecoredService.findItemMinAuditDate(systemBookCode, branchNum, storehouseNum, itemNums, branchItemRecoredTypes);
        int size = objects.size();
        List<BranchItemRecoredDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            BranchItemRecoredDTO dto = new BranchItemRecoredDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setAuditDate((String) object[1]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<BranchItemRecoredDTO> findItemReceiveDate(String systemBookCode, List<Integer> branchNums, Integer storehouseNum, List<Integer> itemNums, List<String> branchItemRecoredTypes) {

        List<Object[]> objects = branchItemRecoredService.findItemReceiveDate(systemBookCode, branchNums, storehouseNum, itemNums, branchItemRecoredTypes);

        int size = objects.size();
        List<BranchItemRecoredDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            BranchItemRecoredDTO dto = new BranchItemRecoredDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setReceiveDate((Date) object[1]);
            list.add(dto);
        }
        return list;
    }


}
