package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.BranchBizdayConsumeSummary;
import com.nhsoft.module.report.dto.BranchConsumeReport;
import com.nhsoft.module.report.rpc.CardConsumeRpc;
import com.nhsoft.module.report.service.CardConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "serviceCache")
    public List<BranchConsumeReport> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {


        List<Object[]> objects = cardConsumeService.findBranchSum(systemBookCode,branchNums,dateFrom,dateTo);
        int size = objects.size();
        List<BranchConsumeReport> list = new ArrayList<BranchConsumeReport>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            BranchConsumeReport branchConsumeReport = new BranchConsumeReport();
            branchConsumeReport.setBranchNum((Integer)object[0]);
            branchConsumeReport.setConsume((BigDecimal) object[1]);
            list.add(branchConsumeReport);
        }
        return list;
    }

    @Override
    public List<BranchBizdayConsumeSummary> findBranchBizdaySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {

        List<Object[]> objects = cardConsumeService.findBranchBizdaySum(systemBookCode, branchNums, dateFrom, dateTo, cardUserCardType);
        int size = objects.size();
        List<BranchBizdayConsumeSummary> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size; i++) {
            Object[] object = objects.get(i);
            BranchBizdayConsumeSummary summary = new BranchBizdayConsumeSummary();
            summary.setBranchNum((Integer) object[0]);
            summary.setBizday((String) object[1]);
            summary.setMoney((BigDecimal) object[2]);
            list.add(summary);
        }
        return list;
    }

}
