package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.AccountMoveDao;
import com.nhsoft.module.report.service.AccountMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountMoveServiceImpl implements AccountMoveService {

    @Autowired
    private AccountMoveDao accountMoveDao;

    @Override
    public List<Object[]> findAccountMoveInMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        return accountMoveDao.findAccountMoveInMoney(systemBookCode, branchNums, dateFrom, dateTo, Stream.of("branch_num").collect(Collectors.toList()));
    }

    @Override
    public List<Object[]> findAccountMoveInMoneyByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        return accountMoveDao.findAccountMoveInMoney(systemBookCode, branchNums, dateFrom, dateTo, Stream.of("branch_num", "account_move_bizday").collect(Collectors.toList()));
    }
}
