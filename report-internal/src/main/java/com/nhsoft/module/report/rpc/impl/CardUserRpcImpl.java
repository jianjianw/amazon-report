package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.BranchBizdayCardCountSummary;
import com.nhsoft.module.report.dto.BranchBizdayCardReturnSummary;
import com.nhsoft.module.report.dto.CardUserCount;
import com.nhsoft.module.report.rpc.CardUserRpc;
import com.nhsoft.module.report.service.CardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class CardUserRpcImpl implements CardUserRpc {

    @Autowired private CardUserService cardUserService;

    @Override
    public List<CardUserCount> findCardUserCountByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = cardUserService.findCardUserCountByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        List<CardUserCount> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            CardUserCount cardUserCount = new CardUserCount();
            cardUserCount.setBranchNum((Integer) object[0]);
            cardUserCount.setCount((Integer) object[1]);
            list.add(cardUserCount);
        }
        return list;
    }

    @Override
    public List<BranchBizdayCardCountSummary> findCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {

        List<Object[]> objects = cardUserService.findCardCountByBranchBizday(systemBookCode, branchNums, dateFrom, dateTo, cardUserCardType);
        List<BranchBizdayCardCountSummary> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            BranchBizdayCardCountSummary summary = new BranchBizdayCardCountSummary();
            summary.setBranchNum((Integer) object[0]);
            summary.setBizday((String) object[1]);
            summary.setCount((Long) object[2]);
            list.add(summary);
        }
        return list;
    }

    @Override
    public List<BranchBizdayCardReturnSummary> findRevokeCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {

        List<Object[]> objects = cardUserService.findRevokeCardCountByBranchBizday(systemBookCode, branchNums, dateFrom, dateTo, cardUserCardType);
        List<BranchBizdayCardReturnSummary> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            BranchBizdayCardReturnSummary summary = new BranchBizdayCardReturnSummary();
            summary.setBranchNum((Integer) object[0]);
            summary.setBizday((String) object[1]);
            summary.setReturnCount((Long) object[2]);
            list.add(summary);
        }
        return list;
    }

}
