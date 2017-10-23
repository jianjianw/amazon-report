package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.module.report.dto.BranchConsumeReport;
import com.nhsoft.module.report.rpc.CardConsumeRpc;
import com.nhsoft.report.service.CardConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class CardConsumeRpcImpl implements CardConsumeRpc {

    @Autowired
    private CardConsumeService cardConsumeService;

    @Override
    public List<BranchConsumeReport> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {


        List<Object[]> objects = cardConsumeService.findBranchSum(systemBookCode,branchNums,dateFrom,dateTo);
        List<BranchConsumeReport> list = new ArrayList<BranchConsumeReport>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            BranchConsumeReport branchConsumeReport = new BranchConsumeReport();
            branchConsumeReport.setBranchNum((Integer)object[0]);
            branchConsumeReport.setConsume((BigDecimal) object[1]);
            list.add(branchConsumeReport);
        }
        return list;
    }

}
